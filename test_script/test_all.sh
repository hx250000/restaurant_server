#!/bin/bash
set -uo pipefail

# 综合自动化测试脚本：注册 / 登录 / 新增菜品 / 下单 / 网关鉴权 / Nacos / 负载测试
# 依赖：curl, jq

TEST_USERNAME="testadmin"
TEST_PASSWORD="123456"

ADMIN_USERNAME="admin"
ADMIN_PASSWORD="adminpassword"

GATEWAY_URL="http://localhost:8900"
USER_URL="http://localhost:8081"
DISH_URL="http://localhost:8082"
ORDER_URL="http://localhost:8083"
NACOS_URL="http://localhost:8848"

echo "===registering admin==="
curl -s -X POST "$USER_URL/api/users/register" \
  -H 'Content-Type: application/json' \
  -d "{\"username\": \"$ADMIN_USERNAME\", \"password\": \"$ADMIN_PASSWORD\", \"phone\": \"19999999909\", \"address\": \"自动化测试地址\", \"userrole\": \"ADMIN\" }" 

REQ_TIMEOUT=5

need_cmds=(curl jq)
for cmd in "${need_cmds[@]}"; do
  if ! command -v "$cmd" >/dev/null 2>&1; then
    echo "请先安装 $cmd（例如: sudo apt install $cmd 或 choco install $cmd）" >&2
    exit 2
  fi
done

usage() {
  cat <<EOF
Usage: $0 [--gateway URL] [--user URL] [--dish URL] [--order URL]
示例: $0
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --gateway) GATEWAY_URL="$2"; shift 2;;
    --user) USER_URL="$2"; shift 2;;
    --dish) DISH_URL="$2"; shift 2;;
    --order) ORDER_URL="$2"; shift 2;;
    -h|--help) usage; exit 0;;
    *) echo "未知参数: $1"; usage; exit 1;;
  esac
done

wait_for() {
  local name="$1" url="$2" timeout=${3-60}
  echo "等待 $name 可用：$url"
  local start=$(date +%s)
  while true; do
    if curl -s --max-time $REQ_TIMEOUT "$url" >/dev/null; then
      echo "$name 可用"
      return 0
    fi
    if (( $(date +%s) - start >= timeout )); then
      echo "等待 $name 超时（${timeout}s）" >&2
      return 1
    fi
    sleep 1
  done
}

echo ""
echo "===== 开始综合测试脚本 ====="

echo "--> 检查 Nacos 控制台"
if curl -s --max-time $REQ_TIMEOUT "$NACOS_URL/nacos/" | grep -q "Nacos"; then
  echo "Nacos 控制台可达: $NACOS_URL/nacos/"
else
  echo "无法确认 Nacos 控制台可达（非阻塞）"
fi

echo "--> 检查网关白名单接口（无需 token）"
curl -s --max-time $REQ_TIMEOUT "$GATEWAY_URL/api/dishes/all" | jq -r '.message' || echo "请求失败或返回非 JSON"

USERNAME=$TEST_USERNAME
PASSWORD=$TEST_PASSWORD

echo "--> 注册测试用户: $USERNAME"
REGISTER_RESP=$(curl -s -X POST "$USER_URL/api/users/register" \
  -H 'Content-Type: application/json' \
  -d "{\"username\": \"$USERNAME\", \"password\": \"$PASSWORD\", \"phone\": \"13900010001\", \"address\": \"自动化测试地址\", \"userrole\": \"ADMIN\" }" )
  
echo "注册返回: $REGISTER_RESP"
  
USER_ID=$(echo "$REGISTER_RESP" | jq -r '.data.id // empty')
echo "USER_ID=${USER_ID}"

echo "等待用户服务稳定后查询用户 ID"
wait_for "user-service" "$USER_URL/api/users/user/$USERNAME" 20 || true
#USER_ID=$(curl -s "$USER_URL/api/users/user/$USERNAME" | jq -r '.data.id // .data' 2>/dev/null || echo "null")
if [ "$USER_ID" = "null" ] || [ -z "$USER_ID" ]; then
  echo "未能获取到用户 ID，继续尝试通过 /api/users 列表抓取"
  USER_ID=$(curl -s "$USER_URL/api/users" | jq -r '.data[0].id // empty') || true
fi
echo "获取到 USER_ID=$USER_ID"

echo "--> 用户登录获取 token"
TOKEN=$(curl -s -X POST "$USER_URL/api/users/login" -H 'Content-Type: application/json' -d "{\"username\":\"$USERNAME\",\"password\":\"$PASSWORD\"}" | jq -r '.data.token // empty')
if [ -z "$TOKEN" ]; then
  echo "登录未返回 token，尝试管理员登录获取 admin token"
  ADMIN_TOKEN=$(curl -s -X POST "$USER_URL/api/users/adminlogin" -H 'Content-Type: application/json' -d "{\"username\":\"$ADMIN_USERNAME\",\"password\":\"$ADMIN_PASSWORD\"}" | jq -r '.data.token // empty') || true
  TOKEN=${ADMIN_TOKEN-}
fi
echo "TOKEN=${TOKEN:-<空>}"

echo "--> 新增测试菜品"
DISH_NAME="auto_dish_$(date +%s | sha1sum | cut -c1-6)"
DISH_PAYLOAD=$(cat <<EOF
{
  "dishname": "$DISH_NAME",
  "description": "自动化测试菜品",
  "price": 9.9,
  "imageUrl": "-",
  "category": "测试",
  "spicy": false,
  "stock": 100
}
EOF
)

DISH_RESP=$(curl -s -X POST "$GATEWAY_URL/api/dishes" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$DISH_PAYLOAD")
DISH_ID=$(echo "$DISH_RESP" | jq -r '.data.id // empty')
echo "DISH_ID=${DISH_ID}"

sleep 1

if [ -z "$DISH_ID" ]; then
  # 尝试从 /api/dishes/all 查找最近的
  DISH_ID=$(curl -s "$GATEWAY_URL/api/dishes/all" | jq -r --arg name "$DISH_NAME" '.data[] | select(.dishname==$name) | .id' | head -n1)
fi
echo "DISH_ID=${DISH_ID:-<未找到>}"

if [ -z "$USER_ID" ] || [ -z "$DISH_ID" ]; then
  echo "关键资源缺失（USER_ID 或 DISH_ID），停止创建订单步骤" >&2
else
  echo "--> 创建订单（userId=$USER_ID, dishId=$DISH_ID）"
  ORDER_PAYLOAD=$(cat <<EOF
{
  "userId": $USER_ID,
  "items": [ { "dishId": $DISH_ID, "quantity": 1 } ],
  "remark": "自动化测试下单"
}
EOF
)
  curl -s -X POST "$GATEWAY_URL/api/orders" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d "$ORDER_PAYLOAD" | jq -r '.message, .data'
fi

echo "--> 测试网关受保护接口（不带 token）"
curl -i -s -X GET "$GATEWAY_URL/api/orders/admin/list" || true

if [ -n "${TOKEN-}" ]; then
  echo "--> 测试网关受保护接口（带 token）"
  curl -i -s -X GET "$GATEWAY_URL/api/orders/admin/list" -H "Authorization: Bearer $TOKEN" || true

  echo "--> 进行负载/轮询测试（10 次）"
  for i in {1..10}; do
    echo "请求 $i"
    curl -s GET "$GATEWAY_URL/api/dishes/all" 
    sleep 0.2
  done
else
  echo "无需 token，跳过带 token 的受保护接口测试"
fi

echo "--> 测试 OPTIONS 预检请求"
curl -i -s -X OPTIONS "$GATEWAY_URL/api/orders/admin/list" || true

echo "--> 测试菜品库存相关接口（增加/减少/设置）"
if [ -n "$DISH_ID" ]; then
  curl -s -X PUT "$GATEWAY_URL/api/dishes/$DISH_ID/stock/add?addStock=5" -H "Authorization: Bearer $TOKEN" | jq -r '.message, .data' || true
  curl -s -X PUT "$GATEWAY_URL/api/dishes/$DISH_ID/stock/reduce?reduceStock=2" -H "Authorization: Bearer $TOKEN" | jq -r '.message, .data' || true
  curl -s -X PUT "$GATEWAY_URL/api/dishes/$DISH_ID/stock?stock=50" -H "Authorization: Bearer $TOKEN" | jq -r '.message, .data' || true
fi

#echo "--> 清理：尝试删除测试数据（非强制成功）"
#if [ -n "$USER_ID" ]; then
#  curl -s -X DELETE "$USER_URL/api/users/$USER_ID" | jq -r '.message // empty' || true
#fi
#if [ -n "$DISH_ID" ]; then
#  curl -s -X DELETE "$DISH_URL/api/dishes?id=$DISH_ID" | jq -r '.message // empty' || true
#fi

echo "===== 综合测试完成 ====="

echo "提示：如需将脚本设为可执行："
echo "  chmod +x restaurant_server_microservice/test_script/test_all.sh"
