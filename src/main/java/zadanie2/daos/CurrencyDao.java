package zadanie2.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.CurrencyDaoException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.exceptions.daoExceptions.RateDaoException;
import zadanie2.model.hibernate.Currency;

public class CurrencyDao extends BaseDao<Currency> {
	public CurrencyDao(SessionCreator sessionCreator) {
		super(sessionCreator);
	}

	@Override
	public Currency get(long id) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Query query = session.createQuery("from Currency where currency_id = :id ");
			query.setParameter("id", id);
			Currency currency = (Currency) query.uniqueResult();
			sessionCreator.closeSession(session);
			return currency;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Currency po id", e);
		}
	}

	@Override
	public List<Currency> getAll() throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			List<Currency> list = session.createQuery("from Currency").list();
			sessionCreator.closeSession(session);
			return list;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy odczycie tabeli Currency", e);
		}
	}

	@Override
	public void save(Currency t) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			session.save(t);
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy zapisie Currency", e);
		}
	}

	@Override
	public void update(long id, Currency t) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Query query = session
					.createQuery("update Currency set currency_code =:currency_code  where currency_id = :id");
			query.setParameter("currency_code", t.getCode());
			query.setParameter("id", id);
			query.executeUpdate();
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy update Rate", e);
		}
	}

	@Override
	public void deleteById(long id) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Currency currency = get(id);
			session.delete(currency);
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy usuwaniu Currency", e);
		}
	}

	public Currency getByCurrencyCode(CurrencyCode currencyCode) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Query query = session.createQuery("from Currency where currency_code = :code");
			String code = currencyCode.getCode().toUpperCase();
			query.setParameter("code", code);
			Currency currency = null;
			List<Currency> list = query.list();
			if (list.size() > 0) {
				currency = list.get(0);
			}
			sessionCreator.closeSession(session);
			return currency;
		} catch (CreatingSessionException e) {
			throw new CurrencyDaoException("Blad przy wyszukiwaniu Currency po currencyCode", e);
		}
	}
}
