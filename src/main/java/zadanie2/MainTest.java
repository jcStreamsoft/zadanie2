package zadanie2;

import org.hibernate.SessionFactory;

import zadanie2.connectors.apiConnection.ApiConnection;
import zadanie2.connectors.sqlConnection.SqlConnection;
import zadanie2.daos.CountryDao;
import zadanie2.daos.CurrencyDao;
import zadanie2.daos.RateDao;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.hibernate.Currency;
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
		CountryDao couDao = new CountryDao(sessionFactory);
		Currency currency = curDao.get(2);
		// rateDao.save(new Rate(new BigDecimal("10.0"), LocalDate.parse("2022-03-31"),
		// currency));

		// List<Rate> rates = rateDao.getBottomRates(5, currency);
		couDao.findCountriesWithMoreThanTwoCurrencies();
		System.out.println("wow");
		// curDao.findMostChangedCurrencyBetweenDates(LocalDate.parse("2012-01-01"),
		// LocalDate.parse("2012-01-05"));
		// System.out.println(rates.toString());
	}
}
