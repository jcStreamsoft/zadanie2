package zadanie2.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.DaoException.CurrencyDaoException;
import zadanie2.exceptions.DaoException.DaoException;
import zadanie2.interfaces.cruds.Dao;
import zadanie2.model.hibernate.Currency;

public class CurrencyDao implements Dao<Currency> {

	@Override
	public Currency get(long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Currency> getAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Currency t) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Currency t, String[] params) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Currency t) throws DaoException {
		// TODO Auto-generated method stub

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
