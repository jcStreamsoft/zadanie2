package zadanie2.interfaces.daos;

import java.util.List;

import zadanie2.exceptions.daoExceptions.DaoException;

public interface Dao<T> {

	T get(long id) throws DaoException;

	List<T> getAll() throws DaoException;

	T save(T t) throws DaoException;

	T update(long id, T t) throws DaoException;

	T deleteById(long id) throws DaoException;
}
