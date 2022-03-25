package zadanie2.interfaces.cruds;

import java.util.List;

import zadanie2.exceptions.DaoException.DaoException;

public interface Dao<T> {

	T get(long id) throws DaoException;

	List<T> getAll() throws DaoException;

	void save(T t) throws DaoException;

	void update(T t, String[] params) throws DaoException;

	void delete(T t) throws DaoException;
}
