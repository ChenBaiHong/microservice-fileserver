package com.baihoo.microservice.fileserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baihoo.microservice.fileserver.domain.File;
 

/**
 * File 存储库.<br>
 * 		采用MongoDB操作
 * 	
 * @author Administrator
 *
 */
public interface FileRepository extends MongoRepository<File, String> {
}
