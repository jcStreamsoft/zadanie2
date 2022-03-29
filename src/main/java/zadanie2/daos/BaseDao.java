package zadanie2.daos;

import java.util.List;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.interfaces.daos.Dao;

public abstract class BaseDao<T> implements Dao<T> {
	protected SessionCreator sessionCreator;

	public BaseDao(SessionCreator sessionCreator) {
		this.sessionCreator = sessionCreator;
	}

	@Override
	public T get(long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(T t) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long id, T t) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(long id) throws DaoException {
		// TODO Auto-generated method stub

	}

}
