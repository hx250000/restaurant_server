**餐厅微服务后端 API 说明**

简要说明：本项目后端由多个微服务组成，统一响应格式为 `ApiResponse<T>`：

- **code**: HTTP 风格的状态码（200 成功，201 创建成功，4xx/5xx 错误）
- **message**: 描述信息
- **data**: 返回的数据主体（可为对象、数组或 null）
- **timestamp**: 响应时间

本文档覆盖如下服务的公开 REST 接口：
- 用户服务：`/api/users`
- 菜品服务：`/api/dishes`
- 订单服务：`/api/orders`

**用户服务 (`/api/users`)**

- **GET** `/api/users`
  - 描述：查询所有用户
  - 请求示例：无
  - 响应示例：

```json
{
  "code": 200,
  "message": "Success",
  "data": [
    {"id": 12, "username": "alice", "phone": "13800001111", "address": "北京市海淀区", "role": "CUSTOMER"}
  ],
  "timestamp": "2026-01-08T10:00:00"
}
```

- **GET** `/api/users/{id}`
  - 描述：根据 ID 查询用户
  - 路径参数：`id` (Long)
  - 请求示例：GET `/api/users/12`
  - 响应示例：

```json
{
  "code": 200,
  "message": "Success",
  "data": {"id": 12, "username": "alice", "phone": "13800001111", "address": "北京市海淀区", "role": "CUSTOMER"},
  "timestamp": "2026-01-08T10:00:00"
}
```

- Endpoint: **GET** `/api/users/user/{username}`
  - 描述：根据用户名查询（找不到时 `data` 为 null）
  - 请求示例：GET `/api/users/user/alice`
  - 响应示例（存在）：

```json
{
  "code": 200,
  "message": "Success",
  "data": {"id": 12, "username": "alice", "phone": "13800001111", "address": "北京市海淀区"},
  "timestamp": "2026-01-08T10:00:00"
}
```

- **GET** `/api/users/search?keyword=...`
  - 描述：用户名模糊查询
  - 请求示例：GET `/api/users/search?keyword=ali`
  - 响应示例：同 `/api/users` 返回数组

- **POST** `/api/users/register`
  - 描述：注册或恢复被软删除用户
  - 请求示例：

```json
{
  "username": "bob",
  "password": "password123",
  "phone": "13800002222",
  "address": "上海市浦东新区",
  "userrole": "CUSTOMER"
}
```

  - 响应示例：创建的用户对象被放在 `data` 中（code=200 或 201）

- **PUT** `/api/users/{id}`
  - 描述：更新用户信息
  - 请求示例：PUT `/api/users/34` body:

```json
{ "id": 34, "username": "bob", "phone": "13800003333", "address": "上海市静安区" }
```

  - 响应示例：更新后的用户对象

- **POST** `/api/users/login`
  - 描述：用户登录，返回JWT
  - 请求示例：

```json
{ "username": "bob", "password": "password123" }
```

  - 响应示例：

```json
{
  "code": 200,
  "message": "Success",
  "data": { "token": "eyJhbGci...", "username": "bob", "userid": 34 },
  "timestamp": "2026-01-08T10:05:00"
}
```

- **POST** `/api/users/adminlogin`
  - 描述：管理员登录（管理前端使用）
  - 请求示例：与普通登录相同
  - 响应示例：`{token, username}` 放在 `data` 中

- **PUT** `/api/users/reset`
  - 描述：重置密码
  - 请求示例：

```json
{ "userName": "bob", "newPassword": "newpass456" }
```

  - 响应示例：操作结果（message 或 data）

- **DELETE** `/api/users/{id}`
  - 描述：软删除用户（标记为已删除）
  - 请求示例：DELETE `/api/users/34`
  - 响应示例：被软删除的用户对象或操作成功消息

**菜品服务 (`/api/dishes`)**

- **GET** `/api/dishes/test`
  - 描述：Nacos 配置测试（返回配置字符串）
  - 请求示例：GET `/api/dishes/test`
  - 响应示例：`data` 为字符串配置

- **GET** `/api/dishes/all`
  - 描述：查询所有上架菜品
  - 请求示例：GET `/api/dishes/all`
  - 响应示例：

```json
{
  "code": 200,
  "message": "Success",
  "data": [
    {"id":7, "dishname":"宫保鸡丁", "description":"微辣", "price":32.5, "imageUrl":"https://...", "category":"热菜", "spicy":true, "stock":50}
  ],
  "timestamp": "2026-01-08T09:30:00"
}
```

- **GET** `/api/dishes/{id}`
  - 描述：根据 ID 查询菜品
  - 请求示例：GET `/api/dishes/7`
  - 响应示例：单个菜品对象（参见上方 `Dish` 示例）

- **GET** `/api/dishes/name/{name}`
  - 描述：根据菜名查询单个菜品
  - 请求示例：GET `/api/dishes/name/宫保鸡丁`

- **GET** `/api/dishes/search?keyword=...`
  - 描述：按关键字模糊查询
  - 请求示例：GET `/api/dishes/search?keyword=宫保`

- **POST** `/api/dishes`
  - 描述：新增菜品
  - 请求示例：

```json
{
  "dishname": "宫保鸡丁",
  "description": "微辣，经典川菜",
  "price": 32.5,
  "imageUrl": "https://.../gongbao.jpg",
  "category": "热菜",
  "spicy": true,
  "stock": 50
}
```

  - 响应示例：创建后的菜品对象放在 `data` 中

- **GET** `/api/dishes/category/{category}`
  - 描述：根据分类查询菜品
  - 请求示例：GET `/api/dishes/category/热菜`

- **PUT** `/api/dishes/{id}`
  - 描述：全量更新菜品
  - 请求示例：PUT `/api/dishes/7` body 与 `DishAdd` 相同

- **DELETE** `/api/dishes` (使用查询参数 `id`)
  - 描述：逻辑删除菜品
  - 请求示例：DELETE `/api/dishes?id=7`
  - 响应示例：删除结果或被删除菜品对象

- **PUT** `/api/dishes/{id}/stock?stock=...`
  - 描述：设置库存为指定值
  - 请求示例：PUT `/api/dishes/7/stock?stock=100`

- **PUT** `/api/dishes/{id}/stock/add?addStock=...`
  - 描述：增加库存
  - 请求示例：PUT `/api/dishes/7/stock/add?addStock=10`

- **PUT** `/api/dishes/{id}/stock/reduce?reduceStock=...`
  - 描述：减少库存
  - 请求示例：PUT `/api/dishes/7/stock/reduce?reduceStock=5`

**订单服务 (`/api/orders`)**

- **GET** `/api/orders/admin/list`
  - 描述：查询所有订单（管理员专用）
  - 请求示例：GET `/api/orders/admin/list`
  - 响应示例：`data` 为订单数组

- **POST** `/api/orders`
  - 描述：创建订单（下单）
  - 请求示例：

```json
{
  "userId": 34,
  "items": [
    {"dishId": 7, "quantity": 2},
    {"dishId": 10, "quantity": 1}
  ],
  "remark": "尽量快送"
}
```

  - 响应示例（创建成功，`data` 为 Order 对象）：

```json
{
  "code": 200,
  "message": "Success",
  "data": {"id":123, "orderNo":"20260108100000123", "userId":34, "totalAmount":97.5, "status":"CREATED", "createTime":"2026-01-08T10:00:00"},
  "timestamp": "2026-01-08T10:00:01"
}
```

- **GET** `/api/orders/id/{orderId}`
  - 描述：根据订单 ID 查询订单
  - 请求示例：GET `/api/orders/id/123`
  - 响应示例：返回 Order 对象

- **PUT** `/api/orders/admin/{orderId}/finish`
  - 描述：管理员确认订单完成（标记完成）
  - 请求示例：PUT `/api/orders/admin/123/finish`
  - 响应示例：更新后的 Order 对象（status=FINISHED）

- **GET** `/api/orders/user/list/{userId}`
  - 描述：查询某用户的所有订单
  - 请求示例：GET `/api/orders/user/list/34`

- **GET** `/api/orders/admin/status/{status}`
  - 描述：按订单状态查询（商家接口），`status` 对应 `OrderStatus` 枚举（CREATED/FINISHED）
  - 请求示例：GET `/api/orders/admin/status/CREATED`

- **GET** `/api/orders/orderno/{orderNo}`
  - 描述：根据订单号查询订单详情
  - 请求示例：GET `/api/orders/orderno/20260108100000123`
  - 响应示例（OrderDetailDTO）:

```json
{
  "code":200,
  "message":"Success",
  "data":{
    "orderNo":"20260108100000123",
    "userId":34,
    "address":"上海市浦东新区...",
    "remark":"尽量快送",
    "totalAmount":97.5,
    "status":"CREATED",
    "createTime":"2026-01-08T10:00:00",
    "items":[{"dishId":7,"dishName":"宫保鸡丁","price":32.5,"quantity":2,"subtotal":65.0},{"dishId":10,"dishName":"青菜炒香菇","price":32.5,"quantity":1,"subtotal":32.5}]
  },
  "timestamp":"2026-01-08T10:00:02"
}
```

**统一响应示例**

成功返回样例：

```json
{
  "code": 200,
  "message": "Success",
  "data": { ... },
  "timestamp": "2026-01-08T12:00:00"
}
```

错误返回样例：

```json
{
  "code": 404,
  "message": "Not Found",
  "data": null,
  "timestamp": "2026-01-08T12:00:00"
}
```
