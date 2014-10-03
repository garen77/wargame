package com.wg.dao.spi;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wg.model.User;

@Service
public class UserDao extends GenericAbstractDao<User> {

	

	@Override
	public User findByKey(long key) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where idUser = :id");
		query.setParameter("id", (int)key);
		List ls = query.list() ;
		if(ls != null && ls.size()>0)
			return (User) ls.get(0);
		return null;
	}
	
}
