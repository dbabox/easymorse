package util.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDaoImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements Dao<T, PK> {

	public void setType(Class<T> type) {
		this.type = type;
	}

	private static HibernateHandler EMPTY_HANDLER = new HibernateHandler() {

		@Override
		public void setCriteria(Criteria criteria) {
		}
	};

	protected Class<T> type;

	@SuppressWarnings("unchecked")
	@Override
	public void browse(final Pagination<T> pagination) {
		pagination.setResults(Collections.EMPTY_LIST);

		final HibernateHandler paginationHandler = (pagination.getConditon() != null && pagination
				.getConditon() instanceof HibernateHandler) ? (HibernateHandler) pagination
				.getConditon()
				: EMPTY_HANDLER;

		pagination.setRecordSum((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(type);
						paginationHandler.setCriteria(criteria);
						Integer count = (Integer) criteria.setProjection(
								Projections.count(Projections.id().toString()))
								.uniqueResult();
						return count;
					}
				}));

		if (pagination.getRecordSum() > 0) {
			pagination.setResults(this.getHibernateTemplate().executeFind(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							Criteria criteria = session.createCriteria(type);

							paginationHandler.setCriteria(criteria);

							if (pagination.getOrderFieldName() != null
									&& !pagination.getOrderFieldName().trim()
											.isEmpty()) {
								criteria.addOrder(pagination.isDesc() ? Order
										.desc(pagination.getOrderFieldName())
										: Order.asc(pagination
												.getOrderFieldName()));
							}

							criteria.setFirstResult(pagination.getSize()
									* (pagination.getNo() - 1));
							criteria.setMaxResults(pagination.getSize());
							return criteria.list();
						}
					}));
		}

	}

	@Override
	public void create(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public T findById(Serializable id) {
		return (T) this.getHibernateTemplate().get(type, id);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return this.getHibernateTemplate().find("from " + this.type.getName());
	}

}
