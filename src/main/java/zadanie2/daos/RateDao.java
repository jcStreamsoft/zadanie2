package zadanie2.daos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class RateDao extends BaseDao<Rate> {

	public RateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Rate get(long id) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.createNamedQuery(Rate.GET_BY_ID, Rate.class);
			query.setParameter("id", id);
			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Rate> getAll() {
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
	public void update(long id, Rate t) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Rate updatedRate = new Rate();
			updatedRate.setId(id);
			updatedRate.setValue(t.getValue());
			updatedRate.setDate(t.getDate());
			updatedRate.setCurrency(t.getCurrency());
			session.update(updatedRate);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void deleteById(long id) {
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

	public Rate getRateByDateAndCurrencyCode(Currency currency, LocalDate date) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.getNamedQuery(Rate.GET_BY_DATE_AND_CURRENCY_ID);

			query.setParameter("id", currency.getId());
			query.setParameter("date", date);

			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {
			return null;
		}
	}

	public void saveRateList(List<Rate> rateList) {

		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			for (int i = 0; i < rateList.size(); i++) {
				session.save(rateList.get(i));
				if (i % 20 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Rate findMaxRateBetweenDates(LocalDate dateStart, LocalDate dateEnd, Currency currency) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			Query query = session.getNamedQuery(Rate.GET_MAX_BETWEEN_DATES);
			query.setMaxResults(1);
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);
			query.setParameter("id", currency.getId());
			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {
			return null;
		}
	}

	public Rate findMinRateBetweenDates(LocalDate dateStart, LocalDate dateEnd, Currency currency) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.getNamedQuery(Rate.GET_MIN_BETWEEN_DATES);
			query.setMaxResults(1);
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);
			query.setParameter("id", currency.getId());
			Rate rate = (Rate) query.uniqueResult();
			session.getTransaction().commit();
			return rate;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Rate> getBottomRates(int amount, Currency currency) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.getNamedQuery(Rate.GET_NUMBER_BOTTOM_RATES_FOR_CURRENCY);
			query.setMaxResults(amount);
			query.setParameter("id", currency.getId());

			List<Rate> rates = query.getResultList();
			session.getTransaction().commit();
			return rates;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Rate> getTopRates(int amount, Currency currency) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.getNamedQuery(Rate.GET_NUMBER_TOP_RATES_FOR_CURRENCY);

			query.setMaxResults(amount);
			query.setParameter("id", currency.getId());

			List<Rate> rates = query.getResultList();
			session.getTransaction().commit();
			return rates;
		} catch (Exception e) {
			return null;
		}
	}
}
