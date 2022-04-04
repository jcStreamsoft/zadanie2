package zadanie2.daos;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.exceptions.daoExceptions.RateDaoException;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class RateDao extends BaseDao<Rate> {

	public RateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Rate get(long id) throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.createNamedQuery("Rate_findById", Rate.class);
			query.setParameter("id", id);
			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Rate> getAll() throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			List<Rate> list = session.createQuery("from Rate").list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void save(Rate t) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void update(long id, Rate t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Query query = session.createQuery(
					"update Rate set value=:value , date = :date , currency_id = :currency_id where rate_id = :id");
			query.setParameter("value", t.getValue());
			query.setParameter("date", t.getValue());
			query.setParameter("currency_id", t.getCurrency().getId());
			query.setParameter("id", id);
			query.executeUpdate();

			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void deleteById(long id) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Rate rate = get(id);
			session.delete(rate);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public Rate getRateByDateAndCurrencyCode(Currency currency, LocalDate date) throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.createNamedQuery("Rate_findByCurrencyIdAndDate", Rate.class);

			query.setParameter("id", currency.getId());
			query.setParameter("date", date);

			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {
			return null;
		}
	}

	public void saveRateList(Set<Rate> rateList) throws RateDaoException {
		try {
			int count = 0;
			for (Rate rate : rateList) {
				save(rate);
				count++;
				if (count % 1_000 == 0) {
					System.out.println(count + " -- zapisano ");
				}
			}
		} catch (Exception e) {
			throw new RateDaoException("Blad przy zapisie Rate", e);
		}
	}

	public void findMostChangedRateBetweenDates(LocalDate dateStart, LocalDate dateEnd) throws RateDaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			Query query = session.getNamedNativeQuery("Rate_findMostChangedRateBetweenDates");
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);

			Object[] result = (Object[]) query.uniqueResult();
			System.out.println(result[1] + " -- " + result[0]);
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Kursu o najwiekszej różnicy", e);
		}
	}

	public Rate findMaxRateBetweenDates(LocalDate dateStart, LocalDate dateEnd, Currency currency)
			throws RateDaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			Query query = session.getNamedNativeQuery("Rate_findMaxRateBetweenDates");
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);
			query.setParameter("id", currency.getId());
			query.setParameter("dateStart1", dateStart);
			query.setParameter("dateEnd1", dateEnd);
			query.setParameter("id1", currency.getId());

			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Kursu o maksimum w okresie", e);
		}
	}

	public Rate findMinRateBetweenDates(LocalDate dateStart, LocalDate dateEnd, Currency currency)
			throws RateDaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			Query query = session.getNamedNativeQuery("Rate_findMinRateBetweenDates");
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);
			query.setParameter("id", currency.getId());
			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {

			throw new RateDaoException("Blad przy wyszukiwaniu Kursu o minimum w okresie", e);
		}
	}
}
