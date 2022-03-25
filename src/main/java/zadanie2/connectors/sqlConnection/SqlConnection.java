package zadanie2.connectors.sqlConnection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.interfaces.DataConnection;
import zadanie2.model.RateData;
import zadanie2.model.Request;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class SqlConnection implements DataConnection {

	@Override
	public RateData getRateData(Request request) throws ReadingRateDataException {
		try {
			RateData rateData = findRate(request, request.getDate());
			return rateData;
		} catch (Exception e) {
			throw new ReadingRateDataException("Blad zczytywania sql", e);
		}

	}

	@Override
	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException {
		try {
			RateData rateData = findRate(request, date);
			return rateData;
		} catch (Exception e) {
			throw new ReadingRateDataException("Blad zczytywania sql", e);
		}
	}

	@Override
	public void saveRateData(RateData rateData) {
		try {
			Currency currency = getCurrency(rateData.getCurrencyCode());
			if (currency == null) {
				return;
			}
			Rate oldRate = findRate(rateData.getDate(), rateData.getCurrencyCode());
			if (oldRate != null) {
				return;
			}

			Session session = createSession();

			Rate rate = new Rate(rateData.getRate(), rateData.getDate(), currency);
			session.save(rate);

			closeSession(session);
		} catch (Exception e) {

		}
	}

	private Session createSession() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		return session;
	}

	private void closeSession(Session session) {
		session.getTransaction().commit();
		session.close();
	}

	private RateData findRate(Request request, LocalDate date) throws Exception {
		Session session = createSession();
		String code = request.getCurrencyCodeString().toUpperCase();
		Query query = session.createSQLQuery("select r.value,  c.currency_code from Rate r "
				+ " join currency as c on c.currency_id = r.currency_id "
				+ " where r.date= :date AND  c.currency_code = :code");
		query.setParameter("date", date);
		query.setParameter("code", code);

		List<Object[]> results = query.getResultList();
		RateData rateData = null;

		if (results.size() != 0) {
			Object[] tab = results.get(0);
			BigDecimal newRate = new BigDecimal(tab[0].toString());
			CurrencyCode newCurrency = CurrencyCode.valueOf(tab[1].toString());
			rateData = new RateData(date, newRate, newCurrency);
		}

		closeSession(session);
		return rateData;
	}

	public Currency getCurrency(CurrencyCode currencycode) throws Exception {
		Session session = createSession();

		Query query = session.createQuery("from Currency where currency_code = :code");
		String code = currencycode.getCode().toUpperCase();
		query.setParameter("code", code);
		Currency currency = null;
		List<Currency> list = query.list();
		if (list.size() > 0) {
			currency = list.get(0);
		}
		closeSession(session);

		return currency;
	}

	public Rate findRate(LocalDate date, CurrencyCode currencyCode) throws Exception {
		Currency currency = getCurrency(currencyCode);
		Session session = createSession();

		Query query = session.createQuery("from Rate where currency_id = :id AND date = :date");

		query.setParameter("id", currency.getId());
		query.setParameter("date", date);

		List<Rate> list = query.list();
		Rate rate = null;
		if (list.size() > 0) {
			rate = list.get(0);
		}
		closeSession(session);
		return rate;
	}

}
