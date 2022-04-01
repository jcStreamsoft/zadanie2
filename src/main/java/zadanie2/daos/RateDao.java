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
			Query query = session.createNamedQuery("Rate_findById", Rate.class);
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
			Session session = sessionCreator.createSession();
			session.save(t);
			sessionCreator.closeSession(session);
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
			Query query = session.createNamedQuery("Rate_findByCurrencyIdAndDate", Rate.class);

			query.setParameter("id", currency.getId());
			query.setParameter("date", date);

			Rate rate = (Rate) query.uniqueResult();
			sessionCreator.closeSession(session);
			return rate;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Rate po dacie i Currency id", e);
		}
	}

	public void saveRateList(List<Rate> rateList) throws RateDaoException {
		try {
			Session session = sessionCreator.createSession();
			Currency currency = rateList.get(0).getCurrency();
			for (Rate rate : rateList) {
				Query query = session.createNamedQuery("Rate_findByCurrencyIdAndDate", Rate.class);
				query.setParameter("id", currency.getId());
				query.setParameter("date", rate.getDate());

				Rate dbRate = (Rate) query.uniqueResult();
				if (dbRate == null) {
					session.save(rate);
				}
			}
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy zapisie Rate", e);
		}
	}

	public void findMostChangedRateBetweenDates(LocalDate dateStart, LocalDate dateEnd) throws RateDaoException {
		try {
			Session session = sessionCreator.createSession();

			Query query = session.getNamedNativeQuery("Rate_findMostChangedRateBetweenDates");
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);

			Object[] result = (Object[]) query.uniqueResult();
			System.out.println(result[1] + " -- " + result[0]);
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy odczycie Rate o najwiekszych zmianach w okresie", e);
		}
	}

	public Rate findMaxRateBetweenDates(LocalDate dateStart, LocalDate dateEnd, Currency currency)
			throws RateDaoException {
		try {
			Session session = sessionCreator.createSession();

			Query query = session.getNamedNativeQuery("Rate_findMaxRateBetweenDates");
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);
			query.setParameter("id", currency.getId());
			query.setParameter("dateStart1", dateStart);
			query.setParameter("dateEnd1", dateEnd);
			query.setParameter("id1", currency.getId());

			Rate rate = (Rate) query.uniqueResult();
			sessionCreator.closeSession(session);
			return rate;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy odczycie max Rate w okresie", e);
		}
	}

	public Rate findMinRateBetweenDates(LocalDate dateStart, LocalDate dateEnd, Currency currency)
			throws RateDaoException {
		try {
			Session session = sessionCreator.createSession();

			Query query = session.getNamedNativeQuery("Rate_findMinRateBetweenDates");
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);
			query.setParameter("id", currency.getId());
			Rate rate = (Rate) query.uniqueResult();
			sessionCreator.closeSession(session);
			return rate;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy odczycie min Rate w okresie", e);
		}
	}
}
