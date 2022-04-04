package zadanie2;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie2.connectors.apiConnection.ApiConnection;
import zadanie2.connectors.sqlConnection.SqlConnection;
import zadanie2.daos.CurrencyDao;
import zadanie2.daos.RateDao;
import zadanie2.daos.SessionCreator;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;
import zadanie2.parsers.apiParsers.ApiJsonParser;

public class MainTest {

	public static void main(String[] args) throws Exception {
		test1();
	}

	public static void test1() throws Exception {
		Exchanger.saveRatesFormApiToSql(new ApiConnection(new ApiJsonParser()), new SqlConnection());
	}

	public static void test2() throws CreatingSessionException, DaoException {
		RateDao rateDao = new RateDao(new SessionCreator());
		CurrencyDao curDao = new CurrencyDao(new SessionCreator());
		Currency currency = curDao.get(1);
		rateDao.save(new Rate(new BigDecimal("10.0"), LocalDate.parse("2022-03-31"), currency));
//		Rate rate = rateDao.findMaxRateBetweenDates(LocalDate.parse("2012-01-01"), LocalDate.parse("2012-01-05"),
//				currency);
		// System.out.println(rate.toString());
	}
}
