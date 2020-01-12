## MongoDB 版本 4.0.10 
#### 1. MongoDB 修改 文件数据库中,添加 file 表字段
```sybase
> use fileserver
> db.getCollection('file').update({},{$set:{compressContent:null}},{multi:true})

```

    
	