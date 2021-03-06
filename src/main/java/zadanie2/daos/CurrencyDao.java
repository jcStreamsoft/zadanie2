package zadanie2.daos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import zadanie2.connectors.sqlConnection.HibernateFactory;
import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.interfaces.daos.Dao;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

@Repository
public class CurrencyDao implements Dao<Currency> {
	protected SessionFactory sessionFactory;

	public CurrencyDao(HibernateFactory hibernateFactory) {
		this.sessionFactory = hibernateFactory.getSessionFactory();
	}

	@Override
	public Currency get(long id) throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.createQuery("from Currency where currency_id = :id ");
			query.setParameter("id", id);
			Currency currency = (Currency) query.uniqueResult();
			session.getTransaction().commit();
			return currency;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Currency> getAll() throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			List<Currency> list = session.createQuery("from Currency").list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Currency save(Currency t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Long id = (Long) session.save(t);
			System.out.println(t.toString());
			t.setId(id);
			session.getTransaction().commit();
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		}
	}

	@Override
	public Currency update(long id, Currency t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("update Currency set currency_code =:currency_code  where currency_id = :id");
			query.setParameter("currency_code", t.getCode());
			query.setParameter("id", id);
			query.executeUpdate();
			session.getTransaction().commit();
			return get(id);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		}
	}

	@Override
	public Currency deleteById(long id) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Currency currency = get(id);
			session.delete(currency);
			session.getTransaction().commit();
			return currency;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		}
	}

	public Currency getByCurrencyCode(CurrencyCode currencyCode) throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.createQuery("from Currency where currency_code = :code");
			String code = currencyCode.getCode().toUpperCase();
			query.setParameter("code", code);
			Currency currency = null;
			List<Currency> list = query.list();
			if (list.size() > 0) {
				currency = list.get(0);
			}
			session.getTransaction().commit();
			return currency;
		} catch (Exception e) {
			return null;
		}
	}

	public void findMostChangedCurrencyBetweenDates(LocalDate dateStart, LocalDate dateEnd) throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			Query query = session.getNamedQuery(Rate.GET_MOST_CHANGED_CURRENCY_ID_BETWEEN_DATES);
			query.setMaxResults(1);
			query.setParameter("dateStart", dateStart);
			query.setParameter("dateEnd", dateEnd);
			Rate rate = (Rate) query.uniqueResult();
			System.out.println(rate.toString());
			query = session.getNamedQuery(Currency.GET_CURRENCY_BY_ID);
			query.setParameter("id", rate.getCurrency().getId());
			Currency result = (Currency) query.uniqueResult();
			System.out.println(result.toString());
//			for (Currency x : result) {
//				System.out.println(x.toString());
//			}

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			// throw new DaoException("Blad przy wyszukiwaniu kursu o najwiekszej roznicy",
			// e);
		}
	}
}
