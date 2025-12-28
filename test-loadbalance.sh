#!/bin/bash

echo "启动所有服务..."
#docker compose up --scale r-order-service=3 -d

echo "等待服务启动..."
#sleep 30

TOKEN=$(curl -s -X POST "http://localhost:8900/api/users/login" \
  -H "Content-Type: application/json" \
  -d '{
        "username": "hx",
        "password": "hbcpyx11"
      }' | jq -r '.data.token')

echo "TOKEN=$TOKEN"

#echo "检查 Nacos 控制台..."
#curl http://localhost:8848/nacos/

echo ""
echo "检查服务注册情况..."
#curl -X GET "http://localhost:8848/nacos/v1/ns/instance/list?serviceName=catalog-service"
curl -X GET "http://localhost:8848/nacos/v1/ns/instance/list?serviceName=r-order-service&groupName=RESTAURANT_GROUP&namespaceId=public"

echo ""
echo "测试服务（order-service）..."
for i in {1..10}; do
  echo "第 $i 次请求:"
  curl -X GET "http://localhost:8900/api/orders/admin/list" \
   -H "Authorization: Bearer $TOKEN"
done

echo ""
echo "查看容器状态..."
docker compose ps

