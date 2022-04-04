package zadanie2.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.hibernate.Currency;

public class CurrencyDao extends BaseDao<Currency> {
	public CurrencyDao(SessionFactory sessionFactory) {
		super(sessionFactory);
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
	public void save(Currency t) throws DaoException {
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
	public void update(long id, Currency t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("update Currency set currency_code =:currency_code  where currency_id = :id");
			query.setParameter("currency_code", t.getCode());
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
			Currency currency = get(id);
			session.delete(currency);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public Currency getByCurrencyCode(CurrencyCode currencyCode) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
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
}
