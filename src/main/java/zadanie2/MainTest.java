package zadanie2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import zadanie2.connectors.sqlConnection.HibernateFactory;
import zadanie2.enums.CurrencyCode;
import zadanie2.model.RateData;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class MainTest {

	public static void main(String[] args) throws Exception {
		testSave(new RateData(LocalDate.parse("2018-01-03"), new BigDecimal("4.1673"), CurrencyCode.EUR));
	}

	public static void res() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		LocalDate date = LocalDate.parse("2012-01-04");
		String code = "EUR";
		Query query = session.createSQLQuery(
				"select r.value, r.date, c.currency_code from Rate r  join currency as c on c.currency_id = r.currency_id  where r.date= :date"
						+ " AND  c.currency_code = :code");
		query.setParameter("date", date);
		query.setParameter("code", code);
		List<Object[]> results = query.getResultList();
		Object[] res = (Object[]) query.uniqueResult();

		Object[] tab = results.get(0);
		// Result result = results.get(0);
		System.out.println(tab[0] + " " + tab[1] + " " + tab[2]);
		// System.out.println(result.toString());
		session.getTransaction().commit();
		session.close();
	}

	public static void testSave(RateData rateData) throws Exception {
		Currency currency = getCurrency(rateData.getCurrencyCode());
		if (currency == null) {
			return;
		}
		Rate oldRate = ifRateDataExist(rateData.getDate(), rateData.getCurrencyCode());
		if (oldRate != null) {
			return;
		}

		try {
			HibernateFactory factory = new HibernateFactory();
			SessionFactory sessionFactory;
			sessionFactory = factory.factory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Rate rate = new Rate(rateData.getRate(), rateData.getDate(), currency);
			session.save(rate);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Rate ifRateDataExist(LocalDate date, CurrencyCode currencyCode) {
		Currency currency = getCurrency(currencyCode);
		try {
			HibernateFactory factory = new HibernateFactory();
			SessionFactory sessionFactory;
			sessionFactory = factory.factory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Query query = session.createQuery("from Rate where currency_id = :id AND date = :date");

			query.setParameter("id", currency.getId());
			query.setParameter("date", date);

			List<Rate> list = query.list();
			Rate rate = null;
			if (list.size() > 0) {
				System.out.println(list.get(0));
				rate = list.get(0);
			}
			session.getTransaction().commit();
			session.close();
			return rate;
		} catch (Exception e) {
			return null;
		}

	}

	public static void test1() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Rate where date = '2012-01-04'");// here persistent class name is Emp
		List list = query.list();
		System.out.println(list);
	}

	public static Currency getCurrency(CurrencyCode currencycode) {
		try {
			HibernateFactory factory = new HibernateFactory();
			SessionFactory sessionFactory;
			sessionFactory = factory.factory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Currency where currency_code = :code");
			String code = currencycode.getCode().toUpperCase();
			query.setParameter("code", code);
			Currency currency = null;
			List<Currency> list = query.list();
			if (list.size() > 0) {
				System.out.println(list.get(0));
				currency = list.get(0);
			}
			session.getTransaction().commit();
			session.close();
			return currency;
		} catch (Exception e) {
			return null;
		}
	}

}
