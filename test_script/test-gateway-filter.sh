#!/bin/bash

GATEWAY_URL="http://localhost:8900"

echo "=============================="
echo "1️⃣ 测试白名单接口（不带 token）"
echo "=============================="

curl "$GATEWAY_URL/api/dishes/all"

echo
echo "=============================="
echo "2️⃣ 登录获取 token"
echo "=============================="

TOKEN=$(curl -s -X POST "$GATEWAY_URL/api/users/login" \
  -H "Content-Type: application/json" \
  -d '{
        "username": "admin",
        "password": "adminpassword"
      }' | jq -r '.data.token')

echo "TOKEN=$TOKEN"

if [ "$TOKEN" = "null" ] || [ -z "$TOKEN" ]; then
  echo "❌ 未获取到 token，停止测试"
  exit 1
fi

echo
echo "=============================="
echo "3️⃣ 测试受保护接口（不带 token）"
echo "=============================="

curl -i -X GET "$GATEWAY_URL/api/orders/admin/list"

echo
echo "=============================="
echo "4️⃣ 测试受保护接口（带 token）"
echo "=============================="

curl -X GET "$GATEWAY_URL/api/dishes/test" \
  -H "Authorization: Bearer $TOKEN"

echo
echo "=============================="
echo "5️⃣ 测试 OPTIONS 预检请求"
echo "=============================="

curl -i -X OPTIONS "$GATEWAY_URL/api/orders/admin/list"

echo
echo "✅ 测试完成"

