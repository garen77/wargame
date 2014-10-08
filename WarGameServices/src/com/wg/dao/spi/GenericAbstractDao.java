package com.wg.dao.spi;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericAbstractDao<T> implements Serializable{
	
	@Autowired
	protected SessionFactory sessionFactory;

	public abstract T findByKey(long key);
	
	public List<T> findByCriteria(T criteria) throws IllegalArgumentException, IllegalAccessException 
	{
		Criteria queryCriteria = sessionFactory.getCurrentSession().createCriteria(criteria.getClass());
		if(criteria != null)
		{
			Field[] fields = criteria.getClass().getDeclaredFields();
			for(Field field: fields)
			{
				if(field != null)
				{
					field.setAccessible(true);
					Object obj = field.get(criteria);
					if(obj != null)
					{
						if((!(obj instanceof Number) || obj instanceof Number && ((Number)obj).longValue()>0) && ! (obj instanceof Set))
						{
							queryCriteria.add(Restrictions.eq(field.getName(), field.get(criteria)));							
						}
					}
				}
			}
		}
		return queryCriteria.list();

	}
	
	public List<T> findAll(T model)
	{
		Criteria queryCriteria = sessionFactory.getCurrentSession().createCriteria(model.getClass());
		List<T> res = queryCriteria.list();
		return res;

	}
	
	public void save(T model) throws HibernateException
	{		
		sessionFactory.getCurrentSession().save(model);
	}

	public void update(T model) throws HibernateException
	{		
		sessionFactory.getCurrentSession().update(model);
	}

}
