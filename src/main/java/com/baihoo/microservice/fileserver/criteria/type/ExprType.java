package com.baihoo.microservice.fileserver.criteria.type;

/**
 * 细化类的查询类别
 * @author Administrator
 *
 */
public class ExprType {
	/**
	 * @param  _property 类字段
	 * @param  _op 查询条件
	 * @param  _value 值
	 * @param  _min 最小值
	 * @param  _max 最大值
	 */
	private String _property;
	private String _op;
	private Object _value;
	private Object _min;
	private Object _max;
	
	public String get_property() {
		return _property;
	}
	public void set_property(String _property) {
		this._property = _property;
	}
	public String get_op() {
		return _op;
	}
	public void set_op(String _op) {
		this._op = _op;
	}
	public Object get_value() {
		return _value;
	}
	public void set_value(Object _value) {
		this._value = _value;
	}
	public Object get_min() {
		return _min;
	}
	public void set_min(Object _min) {
		this._min = _min;
	}
	public Object get_max() {
		return _max;
	}
	public void set_max(Object _max) {
		this._max = _max;
	}
	
	
}
