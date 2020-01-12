## MongoDB 版本 4.0.10 
#### 1. windows 配置 MongoDB 系统环境
```text
我的电脑
    右击-我的属性
    点击-高级系统设置
    点击-环境变量
    系统变量
        path: D:\Program Files\MongoDB\Server\4.0\bin

```
#### 2. Windows 安装 MongoDB 注意
```text
1. 安装时选择下一步时，选择 custom(自定义)
2. 安装路径默认即可，我一般只改盘符
3. 配置 DB 路径 和 Log 路径，可以配置自定义
4. 可以默认安装 MongoDB Compress
```
#### 3. Windows 配置  MongoDB 数据库(注意：如果按上面的按部就班配置了第 3 小节可以略过)
```text
C:\Users\Administrator> mongod --dbpath D:\RepositoryFiles\MongoDB\Server\4.0\db
C:\Users\Administrator> mongod --dbpath D:\RepositoryFiles\MongoDB\Server\4.0\log
```
#### 4. MongoDB 运行命令
```sbtshell
启动MongoDB服务
    net start MongoDB
    
关闭MongoDB服务
    net stop MongoDB
```
#### 5. 常见 MongoDB 命令
```text
1.show dbs　　　　        ----列出所有数据库

2.db.getName()            ----列出当前数据库名

3.use dbname　　          ----切换到某个数据库

4.db.createUser()　       ----创建用户角色

5.db.auth(username , password)               ---- 验证用户到数据库
```
#### 6. Windows 下 MongoDB 远程连接，配置文档 mongod.cfg（位置：安装目录\bin 下）
```text
# network interfaces
net:
  port: 27017
  bindIp: 0.0.0.0
```
#### 7. Windows 下 MongoDB 用户名密码认证登陆配置

* ##### 7.1. 打开 cmd 输入 mongo , 进入 mongodb 的命令页面
    ```text
    > use admin #进入admin数据库
    > db.createUser({user:'admin' , pwd:'admin54188' , roles:[{role:'readWrite' , db:'admin'}]})
    ```
    ```text
    > use fileserver #进入admin数据库
    > db.createUser({user:'admin' , pwd:'fileserver54188' , roles:[{role:'readWrite' , db:'fileserver'}]})
    ```
* ##### 7.2. 修改配置文档mongod.cfg（位置：安装目录\bin 下）
    ```text
    security:
      authorization: enabled #注意缩进，参照其他的值来改，若是缩进不对可能导致后面服务不能重启
    ```
* ##### 7.2. 重启服务
    ```text
    重启MongoDB Server服务，启用认证！
    ```
#### 8. Windows 下 mongod.cfg
```sbtshell
# mongod.conf

# for documentation of all options, see:
#   http://docs.mongodb.org/manual/reference/configuration-options/

# Where and how to store data.
storage:
  dbPath: D:\RepositoryFiles\MongoDB\Server\4.0\data
  journal:
    enabled: true
#  engine:
#  mmapv1:
#  wiredTiger:

# where to write logging data.
systemLog:
  destination: file
  logAppend: true
  path:  D:\RepositoryFiles\MongoDB\Server\4.0\log\mongod.log

# network interfaces
net:
  port: 27017
  bindIp: 0.0.0.0


#processManagement:

security:
  authorization: enabled #注意缩进，参照其他的值来改，若是缩进不对可能导致后面服务不能重启

#operationProfiling:

#replication:

#sharding:

#Enterprise-Only Options:

#auditLog:

#snmp:
```
    
	