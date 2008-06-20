package com.easymorse.dbunit.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {

	private Class<T> persistentClass;

   
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

   
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return super.getHibernateTemplate().loadAll(this.persistentClass);
    }
    
   
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }
    
   
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);

        if (entity == null) {
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);
        return entity != null;
    }

   
    @SuppressWarnings("unchecked")
    public T save(T object) {
        return (T) super.getHibernateTemplate().merge(object);
    }

  
    public void remove(PK id) {
        super.getHibernateTemplate().delete(this.get(id));
    }
    
  
   @SuppressWarnings("unchecked")
   public List<T> findByNamedQuery(
       String queryName, 
       Map<String, Object> queryParams) {
       String []params = new String[queryParams.size()];
       Object []values = new Object[queryParams.size()];
       int index = 0;
       Iterator<String> i = queryParams.keySet().iterator();
       while (i.hasNext()) {
           String key = i.next();
           params[index] = key;
           values[index++] = queryParams.get(key);
       }
       return getHibernateTemplate().findByNamedQueryAndNamedParam(
           queryName, 
           params, 
           values);
   }
}
