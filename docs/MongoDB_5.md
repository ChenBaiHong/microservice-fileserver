## mongodb 远程数据库的连接 以及备份导入导出数据
#### 1. 远程连接远程mongodb
  > mongo -u username -p pwd host:post/database(数据库名)
  ```bash
 mongo -u admin -p fileserver54188 10.15.2.11:27017/fileserver
 ```
 > 当该用户有相应权限时,可以查看 collection==> 查看集合命令: show collections
 
#### 2. mongodb数据库迁移
  > mongodump -host IP --port 端口 -u 用户名 -p 密码 -d 数据库 -o 文件存在路径
   ```text
    详细解释：
            -h：mongodb所在的服务器地址（必须指定端口），不指定的话就是本地的127.0.0.1:27017
    　　　　-u：用户名
    　　　　-p：密码
    　　　　-d：需要备份的数据库（导出整个mongodb就去掉）
    　　　　-o：备份的数据存放的位置
   ```
  ```bash
  # mongodump --host 10.15.2.11 --port 27017  -u admin  -p fileserver54188 -d fileserver -o /web_server/file/mongodb_repos/backup_data
  ./mongodump --host 10.15.2.11 --port 27017  -u admin  -p fileserver54188 -d fileserver -o /web_server/file/mongodb_repos/backup_data
 ```
#### 3. mongodump备份还原数据库
  > mongorestore -h IP --port 端口 -u 用户名 -p 密码 -d 数据库 --drop 文件存在路径
   ```text
    详细解释：
            -h：mongodb所在的服务器地址（必须指定端口），不指定的话就是本地的127.0.0.1:27017
    　　　　-u：用户名
    　　　　-p：密码
    　　　　-d：需要备份的数据库（导出整个mongodb就去掉）
    　　　　-o：备份的数据存放的位置
   ```
  ```bash
  # mongorestore -h 10.15.2.101:27017 -u 'admin' -p 'fileserver!@%&^&&%**' -d fileserver --drop /web_server/file/mongodb_repos/backup_data/fileserver
  ./mongorestore -h 10.15.2.101:27017 -u 'admin' -p 'fileserver!@%&^&&%**' -d fileserver --drop /web_server/file/mongodb_repos/backup_data/fileserver
 ```