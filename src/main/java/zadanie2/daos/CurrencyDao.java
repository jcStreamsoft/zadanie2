package zadanie2.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.CurrencyDaoException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.exceptions.daoExceptions.RateDaoException;
import zadanie2.interfaces.daos.Dao;
import zadanie2.model.hibernate.Currency;

public class CurrencyDao implements Dao<Currency> {

	@Override
	public Currency get(long id) throws DaoException {
		try {
			Session session = SessionCreator.createSession();
			Query query = session.createQuery("from Currency where currency_id = :id ");
			query.setParameter("id", id);
			Currency currency = (Currency) query.uniqueResult();
			SessionCreator.closeSession(session);
			return currency;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Currency po id", e);
		}
	}

	@Override
	public List<Currency> getAll() throws DaoException {
		try {
			Session session = SessionCreator.createSession();
			List<Currency> list = session.createQuery("from Currency").list();
			SessionCreator.closeSession(session);
			return list;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy odczycie tabeli Currency", e);
		}
	}

	@Override
	public void save(Currency t) throws DaoException {
		try {
			Session session = SessionCreator.createSession();
			session.save(t);
			SessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy zapisie Currency", e);
		}
	}

	@Override
	public void update(long id, Currency t) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(long id) throws DaoException {
		try {
			Session session = SessionCreator.createSession();
			Currency currency = get(id);
			session.delete(currency);
			SessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy usuwaniu Rate", e);
		}

	}

	public Currency getByCurrencyCode(CurrencyCode currencyCode) throws DaoException {
		try {
			Session session = SessionCreator.createSession();
			Query query = session.createQuery("from Currency where currency_code = :code");
			String code = currencyCode.getCode().toUpperCase();
			query.setParameter("code", code);
			Currency currency = null;
			List<Currency> list = query.list();
			if (list.size() > 0) {
				currency = list.get(0);
			}
			SessionCreator.closeSession(session);
			return currency;
		} catch (CreatingSessionException e) {
			throw new CurrencyDaoException("Blad przy wyszukiwaniu Currency po currencyCode", e);
		}
	}
}
