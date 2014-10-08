package com.wg.dao.spi;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.wg.model.Character;

@Service
public class CharacterDao extends GenericAbstractDao<com.wg.model.Character> {

	
	@Override
	public com.wg.model.Character findByKey(long key) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Character where id = :id");
		query.setParameter("id", key);
		List ls = query.list() ;
		if(ls != null && ls.size()>0)
			return (Character) ls.get(0);
		return null;
	}

}
