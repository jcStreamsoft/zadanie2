package zadanie2;

import java.time.LocalDate;

import zadanie2.connectors.apiConnection.ApiConnection;
import zadanie2.connectors.sqlConnection.HibernateFactory;
import zadanie2.connectors.sqlConnection.SqlConnection;
import zadanie2.daos.CountryDao;
import zadanie2.daos.CurrencyDao;
import zadanie2.daos.RateDao;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.exchanger.Exchanger;
import zadanie2.model.hibernate.Currency;
import zadanie2.parsers.apiParsers.ApiJsonParser;

public class MainTest {

	public static void main(String[] args) throws Exception {
		test1();
	}

	public static void test1() throws Exception {
		Exchanger.saveRatesFormApiToSql(new ApiConnection(new ApiJsonParser()),
				new SqlConnection(new HibernateFactory()));
	}

	public static void test2() throws CreatingSessionException, DaoException {
		HibernateFactory hibernateFactory = new HibernateFactory();
		RateDao rateDao = new RateDao(hibernateFactory);
		CurrencyDao curDao = new CurrencyDao(hibernateFactory);
		CountryDao couDao = new CountryDao(hibernateFactory);
		Currency currency = curDao.get(1);
		// rateDao.save(new Rate(new BigDecimal("10.0"), LocalDate.parse("2022-03-31"),
		// currency));

		// List<Rate> rates = rateDao.getBottomRates(5, currency);
		// couDao.findCountriesWithMoreThanTwoCurrencies();
		curDao.findMostChangedCurrencyBetweenDates(LocalDate.parse("2009-01-01"), LocalDate.parse("2015-01-01"));

		// System.out.println(rates.toString());
	}
}
