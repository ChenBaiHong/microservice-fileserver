package com.baihoo.microservice.fileserver;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @project: baigle-components
 * @author: chen.baihoo
 * @date: 2019/1/24 16:55
 * @Description: TODO 细化类的查询类别
 * version 0.1
 */
@Data
public class ExprType  implements Serializable {
	
	/** 
	 * @param _opEnum; 查询方式
	 * @param _op;		查询方式
	 * @param _min;		最小值
	 * @param _max;		最大值
	 * @param _likeRuleEnum;	模糊查询
	 * @param _likeRule;		模糊查询
	 * @param _dateRuleEnum;	日期规则
	 * @param _dateRule;		日期规则
	 * @param _pattern;			日期格式
	 * @param _year;			年分
	 * @param _quarter;			季度
	 * @param _month;			月份
	 * @param _opPropertyEnum;	查询匹配字段
	 * @param _opProperty;		查询匹配字段
	 * @param _ref;				
	 * @param is_processNullValue;	是否空值
	 * @param _property;		查询匹配字段
	 * @param _value;			查询匹配字段value
	 * 
	 * 
	 */ 
	

	ExprType.OP _opEnum;
	
	String _op;
	
	String _min;

	String _max;

	ExprType.LIKERULE _likeRuleEnum;
	
	String _likeRule;
	
	ExprType.DATERULE _dateRuleEnum;
	
	String _dateRule;

	String _pattern;

	int _year;
	
	int _quarter;
	
	int _month;
	
	ExprType.OP _opPropertyEnum;
	
	String _opProperty;
	
	String _ref;
	
	boolean is_processNullValue;
	
	String _property;
	
	String _value;

	/**
	 * @project: baigle-components
	 * @author: chen.baihoo
	 * @date: 2019/1/24 16:56
	 * @Description: TODO 日期规则定义
	 * version 0.1
	 */
	public static enum DATERULE {
		Y("year"),
		Y_Q("year,quarter"),
		Y_M("year,month");

		private String value;
		private static Map<String, ExprType.DATERULE> ENUMS = new HashMap();

		private DATERULE(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}

		public static ExprType.DATERULE toEnum(String value) {
			ExprType.DATERULE ENUM = (ExprType.DATERULE)ENUMS.get(value);
			if (ENUM == null) {
				throw new IllegalArgumentException("no enum with value ( " + value + " )");
			} else {
				return ENUM;
			}
		}

		static {
			ExprType.DATERULE[] values = values();
			ExprType.DATERULE[] arr$ = values;
			int len$ = values.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				ExprType.DATERULE value = arr$[i$];
				ENUMS.put(value.value(), value);
				ENUMS.put(value.value(), value);
				ENUMS.put(value.value(), value);
			}

		}
	}
	/**
	 * @project: baigle-components
	 * @author: chen.baihoo
	 * @date: 2019/1/24 16:57
	 * @Description: TODO 模糊匹配规则
	 * version 0.1
	 */
	public static enum LIKERULE {
		START("start"),
		END("end"),
		ALL("all"),
		NONE("none");

		private String value;
		private static Map<String, ExprType.LIKERULE> ENUMS = new HashMap();

		private LIKERULE(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}

		public static ExprType.LIKERULE toEnum(String value) {
			ExprType.LIKERULE ENUM = (ExprType.LIKERULE)ENUMS.get(value);
			if (ENUM == null) {
				throw new IllegalArgumentException("no enum with value ( " + value + " )");
			} else {
				return ENUM;
			}
		}

		static {
			ExprType.LIKERULE[] values = values();
			ExprType.LIKERULE[] arr$ = values;
			int len$ = values.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				ExprType.LIKERULE value = arr$[i$];
				ENUMS.put(value.value(), value);
				ENUMS.put(value.value(), value);
				ENUMS.put(value.value(), value);
			}

		}
	}
	/**
	 * @project: baigle-components
	 * @author: chen.baihoo
	 * @date: 2019/1/24 16:58
	 * @Description: TODO 操作方式
	 * version 0.1
	 */
	public static enum OP {
		LIKE("like"),
		EQ("="),
		NOT_EQ("<>"),
		GT(">"),
		LT("<"),
		GE(">="),
		LE("<="),
		NULL("null"),
		NOT_NULL("notnull"),
		BETWEEN("between"),
		IN("in");

		private String value;

		private static Map<String, ExprType.OP> ENUMS = new HashMap();

		private OP(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}

		public static ExprType.OP toEnum(String value) {
			ExprType.OP ENUM = (ExprType.OP)ENUMS.get(value);
			if (ENUM == null) {
				throw new IllegalArgumentException("no enum with value ( " + value + " )");
			} else {
				return ENUM;
			}
		}

		static {
			ExprType.OP[] values = values();
			ExprType.OP[] arr$ = values;
			int len$ = values.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				ExprType.OP value = arr$[i$];
				ENUMS.put(value.value(), value);
				ENUMS.put(value.value(), value);
				ENUMS.put(value.value(), value);
			}
		}
	}

	public static void main(String[] args){
		if(ExprType.OP.toEnum("like").value().equals("like")){
			System.out.print("dddd");
		}
	}
}
