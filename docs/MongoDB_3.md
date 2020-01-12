## ContOS 安装 MongoDB 版本 4.0.10 
#### 1. 切换为root用户
  ```bash
 sudo root
 ```
 
#### 2. 将mongodb的repo加入到yum的repo中,并粘贴配置文本,仅针对4.0版本.
  ```bash
  vim /etc/yum.repos.d/mongodb-org.repo
 ```
 ```text
  [mongodb-org-4.0]
  name=MongoDB Repository
  baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/4.0/x86_64/
  gpgcheck=1
  enabled=1
  gpgkey=https://www.mongodb.org/static/pgp/server-4.0.asc
 ```
 
##### 3. yum 安装
  ```bash
   sudo yum install mongodb-org
  ```
  ```text
  安装mongodb（会安装mongodb-org包及其依赖包mongodb-org-server、mongodb-org-mongos、mongodb-org-shell、mongodb-org-tools）
　数据库实例默认在 /var/lib/mongo 路径下，日志默认在 /var/log/mongodb 路径下，也可以通过修改 /etc/mongod.conf 文件的 storage.dbPath 和 systemLog.path 配置
  ```
  
  
##### 4. 开启mongodb服务,并设定为开机允许启动
  ```bash
  #sudo systemctl start mongod
  #sudo systemctl enable mongod
  ```
  > 编写自定义 MongoDB 服务
   ```bash
    [Unit]  
      
    Description=mongodb   
    After=network.target remote-fs.target nss-lookup.target  
      
    [Service]  
    Type=forking  
    ExecStart=/usr/local/mongodb/bin/mongod --config /usr/local/mongodb/mongodb.conf  
    ExecReload=/bin/kill -s HUP $MAINPID  
    ExecStop=/usr/local/mongodb/bin/mongod --shutdown --config /usr/local/mongodb/mongodb.conf  
    PrivateTmp=true  
        
    [Install]  
    WantedBy=multi-user.target 
   ```
##### 5. 验证安装
  ```bash
  mongod
  ```
##### 6. 卸载 mongoDB 
  ```bash
  sudo systemctl stop mongod
  sudo yum erase $(rpm -qa | grep mongodb-org)
  ```