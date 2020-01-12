package com.baihoo.microservice.fileserver.criteria;

import java.util.List;

import com.baihoo.microservice.fileserver.criteria.type.ExprType;
import com.baihoo.microservice.fileserver.criteria.type.OrderByType;
import com.baihoo.microservice.fileserver.criteria.type.OrderType;

/**
 * 组织的类型，定义成自己的标准查询
 * @author Administrator
 *
 */
public class MeCriteria {
	/**
	 * @param _distinct 是否去重
	 * @param _entity 实体
	 * @param _expr	操作字段
	 * @param _and    
	 * @param order
	 * @param orderBy 
	 */
	private Boolean _distinct;
	private String _entity;
	private List<ExprType> _expr ;
	private List<OrderType> order ;
	private List<OrderByType> orderBy ;
	
	
	public MeCriteria() {
		super();
	}
	public Boolean get_distinct() {
		return _distinct;
	}
	public void set_distinct(Boolean _distinct) {
		this._distinct = _distinct;
	}
	public String get_entity() {
		return _entity;
	}
	public void set_entity(String _entity) {
		this._entity = _entity;
	}
	public List<ExprType> get_expr() {
		return _expr;
	}
	public void set_expr(List<ExprType> _expr) {
		this._expr = _expr;
	}
	public List<OrderType> getOrder() {
		return order;
	}
	public void setOrder(List<OrderType> order) {
		this.order = order;
	}
	public List<OrderByType> getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(List<OrderByType> orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
