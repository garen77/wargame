package com.wg.dao.spi;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.wg.model.Feature;

@Service
public class FeatureDao extends GenericAbstractDao<Feature> {

	@Override
	public Feature findByKey(long key) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Feature where id = :id");
		query.setParameter("id", key);
		List ls = query.list() ;
		if(ls != null && ls.size()>0)
			return (Feature) ls.get(0);
		return null;
	}

}
