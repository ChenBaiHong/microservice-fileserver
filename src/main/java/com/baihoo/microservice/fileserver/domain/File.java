package com.baihoo.microservice.fileserver.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * File 文档类.
 * @author Administrator
 * <br>
 *注意：这里是非关系数据库 NoSQL 建立映射实体的对象
 */
@Document
@Getter
@Setter
public class File implements Serializable {
	/**
	 * @param id 				主键
	 * @param name				文件名称
	 * @param contentType	    文件类型
	 * @param size 				文件大小
	 * @param uploadDate 		上载日期
	 * @param md5	md5 		加密
	 * @param content 			文件内容
     * @param compressContent   压缩文件内容
	 * @param path 				文件路径
	 */
	
	@Id
	private String id;
    private String name; 
    private String contentType; 
    private long size;
    private Date uploadDate;
    private String md5;
    private Binary content;
    private Binary compressContent;
    private String path; 

    protected File() {
    }
    
    public File(String name, String contentType, long size,Binary content) {
    	this.name = name;
    	this.contentType = contentType;
    	this.size = size;
    	this.uploadDate = new Date();
    	this.content = content;
    }
   /**
    * 重写equals 方法
    */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        File fileInfo = (File) object;
        return java.util.Objects.equals(size, fileInfo.size)
                && java.util.Objects.equals(name, fileInfo.name)
                && java.util.Objects.equals(contentType, fileInfo.contentType)
                && java.util.Objects.equals(uploadDate, fileInfo.uploadDate)
                && java.util.Objects.equals(md5, fileInfo.md5)
                && java.util.Objects.equals(id, fileInfo.id);
    }
    /**
     * 重写hashCode
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, contentType, size, uploadDate, md5, id);
    }

    @Override
    public String toString() {
        return "File{"
                + "name='" + name + '\''
                + ", contentType='" + contentType + '\''
                + ", size=" + size
                + ", uploadDate=" + uploadDate
                + ", md5='" + md5 + '\''
                + ", id='" + id + '\''
                + '}';
    }
}
