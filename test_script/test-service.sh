#!/bin/bash
 # test-services.sh
echo "=== 测试微服务拆分 ==="
 # 1. 测试user目录服务 - 创建user
echo -e "\n1. 测试user目录服务 - 创建user"
 curl -X POST http://localhost:8081/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "hx",
    "password": "hx114514",
    "phone": "12345678901",
    "address": "address1",
    "userrole": "ADMIN"
  }'
 # 2. 获取所有user
echo -e "\n2. 获取所有user"
 curl http://localhost:8081/api/users
 # 3. 测试dish服务 - 创建dish
echo -e "\n3. 测试dish服务 - 创建dish"
 curl -X POST http://localhost:8082/api/dishes \
  -H "Content-Type: application/json" \
  -d '{
    "dishname": "dish1",
    "description": "discription1",
    "price": 20.9,
    "imageUrl": "-",
    "spicy": false,
    "category": "test",
    "stock": 100
  }'
 # 4. 获取所有dish
echo -e "\n4. 获取所有dish"
 curl http://localhost:8082/api/dishes
 # 5. 测试order（验证服务间通信）
echo -e "\n5. 测试order"
 USER_ID=$(curl -s http://localhost:8081/api/users | jq -r '.data[0].id')
 ORDER_JSON=$(cat <<EOF
{
  "userId": $USER_ID,
  "items": [
    {
      "dishId": 1,
      "quantity": 1
    }
  ],
  "remark":""
}
EOF
)

echo "order:\n$ORDER_JSON"

 curl -X POST http://localhost:8083/api/orders \
   -H "Content-Type: application/json" \
   -d "$ORDER_JSON"

 # 6. 查询order记录
echo -e "\n6. 查询order记录"
 curl http://localhost:8083/api/orders/admin/list
 # 7. 测试user不存在的情况
echo -e "\n7. 测试order失败（user不存在）"
USER_ID=114514
ORDER_JSON=$(cat <<EOF
{
  "userId": $USER_ID,
  "items": [
    {
      "dishId": 1,
      "quantity": 1
    }
  ]
}
EOF
)

curl -X POST http://localhost:8083/api/orders \
   -H "Content-Type: application/json" \
   -d "$ORDER_JSON"

echo -e "\n=== 测试完成 ==="
