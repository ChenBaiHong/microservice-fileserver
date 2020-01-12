## ContOS 压缩包安装配置 MongoDB 版本 4.0.4
#### 1. 切换为root用户
  ```bash
 sudo root
 ```
 
#### 2. 从官网获得最新版本下载链接，使用用 wget 令下载压缩包到本地
  ```bash
  wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-4.0.4.tgz
 ```
##### 3. 使用tar命令解压压缩包，并重命名为更简短到文件名
  ```bash
   tar xvf mongodb-linux-x86_64-4.0.4.tgz
   mv mongodb-linux-x86_64-4.0.4 mongodb-4.0.4
  ```
#### 4. 打开/etc/profile文件，将其添加到环境变量，使mongod命令可以全局访问
  ```bash
  # 打开profile文件
  vim /etc/profile
  # 添加环境变量
  export MONGODB_HOME=/web_server/file/mongodb-4.0.4
  export PATH=$PATH:$MONGODB_HOME/bin
  ```
##### 5.创建存放数据文件夹和日志文件夹
  ```bash
  cd /web_server/file/
 mkdir -p mongodb_repos/data
 sudo chmod 777 mongodb_repos/data
 mkdir mongodb_repos/log
 cd log
 touch mongod.log
 ```
##### 6.创建配置文件
  ```bash
    # mongod.conf
    
    # for documentation of all options, see:
    #   http://docs.mongodb.org/manual/reference/configuration-options/
    
    # Where and how to store data.
    storage:
      dbPath: /web_server/file/mongodb_repos/data
      journal:
        enabled: true
    #  engine:
    #  mmapv1:
    #  wiredTiger:
    
    # where to write logging data.
    systemLog:
      destination: file
      logAppend: true
      path: /web_server/file/mongodb_repos/log/mongod.log
    
    # network interfaces
    net:
      port: 27017
      bindIp: 0.0.0.0
    
    processManagement:
      fork: true
    
    #security:
    #  authorization: enabled #注意缩进，参照其他的值来改，若是缩进不对可能导致后面服务不能重启
    
    #operationProfiling:
    
    #replication:
    
    #sharding:
    
    #Enterprise-Only Options:
    
    #auditLog:
    
    #snmp:
  ```
##### 7.编写自定义 MongoDB 服务
 >在 /lib/systemd/system/ 目录下新建 mongodb.service 文件，内容如下
   ```bash
    [Unit]  
      
    Description=mongodb   
    After=network.target remote-fs.target nss-lookup.target  
      
    [Service]  
    Type=forking  
    ExecStart=/web_server/file/mongodb-4.0.4/bin/mongod --config /web_server/file/mongodb-4.0.4/bin/mongod.conf
    ExecReload=/bin/kill -s HUP $MAINPID  
    ExecStop=/web_server/file/mongodb-4.0.4/bin/mongod --shutdown --config /web_server/file/mongodb-4.0.4/bin/mongod.conf
    PrivateTmp=true
        
    [Install]  
    WantedBy=multi-user.target 
   ```
 > 设置mongodb.service权限
  ```bash
  chmod 754 mongodb.service
  ```
##### 5. 系统 mongodb.service 的操作命令如下
  ```bash
    # 刷新服务配置，特殊情况下执行
    systemctl daemon-reload 
    #启动服务  
    systemctl start mongodb.service  
    #关闭服务  
    systemctl stop mongodb.service  
    #开机启动  
    systemctl enable mongodb.service 
  ```