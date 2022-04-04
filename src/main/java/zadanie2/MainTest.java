package zadanie2;

import java.time.LocalDate;

import org.hibernate.SessionFactory;

import zadanie2.connectors.apiConnection.ApiConnection;
import zadanie2.connectors.sqlConnection.SqlConnection;
import zadanie2.daos.CurrencyDao;
import zadanie2.daos.RateDao;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;
import zadanie2.parsers.apiParsers.ApiJsonParser;

public class MainTest {

	public static void main(String[] args) throws Exception {
		test2();
	}

	public static void test1() throws Exception {
		Exchanger.saveRatesFormApiToSql(new ApiConnection(new ApiJsonParser()), new SqlConnection());
	}

	public static void test2() throws CreatingSessionException, DaoException {
		SessionFactory sessionFactory = SqlConnection.createSessionFactory();
		RateDao rateDao = new RateDao(sessionFactory);
		CurrencyDao curDao = new CurrencyDao(sessionFactory);
		Currency currency = curDao.get(1);
		// rateDao.save(new Rate(new BigDecimal("10.0"), LocalDate.parse("2022-03-31"),
		// currency));
		Rate rate = rateDao.findMinRateBetweenDates(LocalDate.parse("2012-01-01"), LocalDate.parse("2012-01-05"),
				currency);
		curDao.findMostChangedCurrencyBetweenDates(LocalDate.parse("2012-01-01"), LocalDate.parse("2012-01-05"));
		System.out.println(rate.toString());
	}
}
