GATEWAY_URL="http://localhost:8900"

TOKEN=$(curl -s -X POST "$GATEWAY_URL/api/users/login" \
  -H "Content-Type: application/json" \
  -d '{
        "username": "admin",
        "password": "adminpassword"
      }' | jq -r '.data.token')

echo "TOKEN=$TOKEN"

echo -e "\n测试order"
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

 curl -X POST http://localhost:8900/api/orders \
   -H "Content-Type: application/json" \
   -H "Authorization: Bearer $TOKEN" \
   -d "$ORDER_JSON"

