package zadanie2.daos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.exceptions.daoExceptions.RateDaoException;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class RateDao extends BaseDao<Rate> {

	public RateDao(SessionCreator sessionCreator) {
		super(sessionCreator);
	}

	@Override
	public Rate get(long id) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Query query = session.createQuery("from Rate where rate_id = :id ");
			query.setParameter("id", id);
			Rate rate = (Rate) query.uniqueResult();
			sessionCreator.closeSession(session);
			return rate;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Rate po id", e);
		}
	}

	@Override
	public List<Rate> getAll() throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			List<Rate> list = session.createQuery("from Rate").list();
			sessionCreator.closeSession(session);
			return list;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy odczycie tabeli Rate", e);
		}
	}

	@Override
	public void save(Rate t) throws DaoException {
		try {
			System.out.println("1");
			Session session = sessionCreator.createSession();
			System.out.println("2");
			session.save(t);
			System.out.println("3");
			sessionCreator.closeSession(session);
			System.out.println("4");
		} catch (Exception e) {
			throw new RateDaoException("Blad przy zapisie Rate", e);
		}
	}

	@Override
	public void update(long id, Rate t) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Query query = session.createQuery(
					"update Rate set value=:value , date = :date , currency_id = :currency_id where rate_id = :id");
			query.setParameter("value", t.getValue());
			query.setParameter("date", t.getValue());
			query.setParameter("currency_id", t.getCurrency().getId());
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
			Rate rate = get(id);
			session.delete(rate);
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy usuwaniu Rate", e);
		}
	}

	public Rate getRateByDateAndCurrencyCode(Currency currency, LocalDate date) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Query query = session.createQuery("from Rate where currency_id = :id AND date = :date");

			query.setParameter("id", currency.getId());
			query.setParameter("date", date);

			List<Rate> list = query.list();
			Rate rate = null;
			if (list.size() > 0) {
				rate = list.get(0);
			}
			sessionCreator.closeSession(session);
			return rate;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Rate po dacie i Currency", e);
		}
	}

}
