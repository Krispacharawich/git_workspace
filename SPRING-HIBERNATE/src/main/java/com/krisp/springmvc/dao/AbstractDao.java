package com.krisp.springmvc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
public abstract class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistenceClass;
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistenceClass = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		
	}
	@Autowired
	private SessionFactory sessionFactory;
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistenceClass, key);
	}
	public void persist(T entiry) {
		getSession().persist(entiry);
	}
	public void delete(T entity) {
		getSession().delete(entity);
	}
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistenceClass);
	}
}
