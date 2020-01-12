package com.baihoo.microservice.fileserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.baihoo.microservice.fileserver.criteria.MeCriteria;
import com.baihoo.microservice.fileserver.criteria.type.ExprType;
import com.baihoo.microservice.fileserver.domain.File;
import com.baihoo.microservice.fileserver.repository.FileRepository;
import com.baihoo.microservice.fileserver.utils.DateUtils;

/**
 * File 服务.
 * @author Administrator
 *
 */
@Service
public class FileServiceImpl implements FileService {
	/**
	 * @param fileRepository 
	 * @param mongoTemplate 
	 */
	@Autowired
	public FileRepository fileRepository;
	@Autowired
    protected MongoTemplate mongoTemplate;
	
	/**
	 *保存文件
	 */
	@Override
	public File saveFile(File file) throws Exception {
		return fileRepository.save(file);
	}
	/**
	 * 删除文件
	 */
	@Override
	public void removeFile(String id) throws Exception {
		fileRepository.deleteById(id);
	}
	/**
	 * 根据id获取文件
	 */
	@Override
	public Optional<File> getFileById(String id) throws Exception {
		return fileRepository.findById(id);
	}
	/**
	 * 分页查询，按上传时间降序
	 */
	@Override
	public List<File> listFilesByPage(int pageIndex, int pageSize) throws Exception {
		Page<File> page = null;
		List<File> list = null;
		
		Sort sort = new Sort(Direction.DESC,"uploadDate"); 
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		
		page = fileRepository.findAll(pageable);
		list = page.getContent();
		return list;
	}

	@Override
	public Page<File> listFilesByPage(Pageable pageable) throws Exception {
		Page<File> page = fileRepository.findAll(pageable);
		return page;
	}
	/**
	 *分页条件查询，按上传时间降序
	 * @throws Exception 
	 */
	@Override
	public Page<File> listFilesByPage(Pageable pageable, MeCriteria meCriteria) throws Exception {
		
		List<ExprType> expr= meCriteria.get_expr();
		
		//定义查询Criteria链
		 List<Criteria> criteriaChain = new ArrayList<Criteria>();;
		for (ExprType exprType : expr) {
			String property = exprType.get_property();
			if(exprType.get_value()!=null) {
				Object value = exprType.get_value();
				//mongoDB模糊查询
				Pattern pattern = Pattern.compile("^.*" + value + ".*$");
				if("name".equals(property)) {
					criteriaChain.add(Criteria.where("name").regex(pattern));
				}else if("contentType".equals(property)) {
					criteriaChain.add(Criteria.where("contentType").regex(pattern));
				}
			}else if( exprType.get_min() !=null &&  exprType.get_max()!=null){
				Object min = exprType.get_min();
				Object max = exprType.get_max();
				if(exprType.get_property().equals("uploadDate")) {
					if(min !=null && max !=null && !"".equals(min) && !"".equals(max)) { 
						//大于最小时间,小于最大时间
						criteriaChain.add(
								Criteria.where("uploadDate").gt(DateUtils.parseDate((String) min, "yyyy-MM-dd HH:mm"))
										.lt(DateUtils.parseDate((String) max, "yyyy-MM-dd HH:mm")));
					}
				}else if(exprType.get_property().equals("size")){
					if(min !=null && max !=null && !"".equals(min) && !"".equals(max)) { 
						//大于最小size,小于最大size
						criteriaChain.add(Criteria.where("size").gt(Long.parseLong((String)min)).lt(Long.parseLong((String)max)));
					}
				}
				
			}
		}
		Criteria criteria =new Criteria();
		criteria.andOperator(criteriaChain.toArray(new Criteria[criteriaChain.size()]));
		//查询，加入criteria组成条件
		Query query=new Query(criteria);
		//分页查询
		List<File>  list = mongoTemplate.find(query.with(pageable), File.class);
		//返回分页查询的对象
		Page<File> page = new PageImpl<File>(list , pageable, mongoTemplate.count(query, File.class));
		return page;
	}
}
