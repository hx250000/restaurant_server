餐厅管理系统微服务后端代码

db/：数据库建表语句（包含部分初始数据）

* 用户表已有初始用户admin，登录密码adminpassword

test\_script/：测试脚本

微服务代码：

dish\_service/

order\_service/

user\_service/

gateway\_service/：网关

应用需在nacos启动的环境下运行，nacos版本为2.3.2

部分application.yml配置写在nacos配置中心上，需要先在nacos中导入配置nacos\_config\_newest.zip

