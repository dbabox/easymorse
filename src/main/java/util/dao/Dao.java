package util.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, PK extends Serializable> {
	/**
	 * 创建对象
	 * 
	 * @param entity
	 */
	public void create(T entity);

	/**
	 * 更新对象
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 通过id得到对象
	 * 
	 * @param id
	 * @return
	 */
	public T findById(PK id);

	/**
	 * 查找全部对象
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * 保存对象（无论是新建还是修改）
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity);

	/**
	 * 分页浏览
	 * 
	 * @param pagination
	 */
	public void browse(Pagination<T> pagination);
}
