package com.baihoo.microservice.fileserver.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 自定义字符串去空处理
 * 		spring是默认不会把空值转化为null
 * @author Administrator
 *
 */
@Component
public class CustomTrimString implements Converter<String , String>{

	@Override
	public String convert(String source) {
		try {
			if(source !=null) {
				source = source.trim();
				if(source.equals("")) {
					source = null;
				}
			}
			return source;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
