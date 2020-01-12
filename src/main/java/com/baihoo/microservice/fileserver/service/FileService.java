package com.baihoo.microservice.fileserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baihoo.microservice.fileserver.criteria.MeCriteria;
import com.baihoo.microservice.fileserver.domain.File;

/**
 * File 服务接口.
 * @author Administrator
 *
 */
public interface FileService {
	/**
	 * 保存文件
	 * @param File
	 * @return
	 */
	File saveFile(File file) throws Exception ;
	
	/**
	 * 删除文件
	 * @param File
	 * @return
	 */
	void removeFile(String id)throws Exception ;
	
	/**
	 * 根据id获取文件
	 * @param File
	 * @return
	 */
	Optional<File> getFileById(String id)throws Exception ;

	/**
	 * 分页查询，按上传时间降序
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<File> listFilesByPage(int pageIndex, int pageSize) throws Exception ;
	/**
	 * 分页查询，按上传时间降序
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<File> listFilesByPage(Pageable pageable) throws Exception ;
	/**
	 * 分页条件查询，按上传时间降序
	 * @param page
	 * @param meCriteria
	 * @return
	 */
	Page<File> listFilesByPage(Pageable pageable , MeCriteria meCriteria) throws Exception ;
}
