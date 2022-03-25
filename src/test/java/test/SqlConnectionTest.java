package test;

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie2.connectors.sqlConnection.SqlConnection;
import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.model.RateData;
import zadanie2.model.Request;

public class SqlConnectionTest {

	@Test
	public void shouldRetrunRate_whenGivenRateExistInSql() throws ReadingRateDataException {
		// given
		SqlConnection sqlConnection = new SqlConnection();
		Request request = Request.getBuilder(new BigDecimal("3"), CurrencyCode.EUR).date(LocalDate.parse("2002-01-02"))
				.build();
		RateData expected = new RateData(LocalDate.parse("2002-01-02"), new BigDecimal("3.54960000000000000000"),
				CurrencyCode.EUR);
		// when
		RateData result = sqlConnection.getRateData(request);
		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldRetrunRate_whenGivenRateExistInSqlwithDate() throws ReadingRateDataException {
		// given
		SqlConnection sqlConnection = new SqlConnection();
		LocalDate date = LocalDate.parse("2002-01-02");
		Request request = Request.getBuilder(new BigDecimal("3"), CurrencyCode.EUR).build();
		RateData expected = new RateData(LocalDate.parse("2002-01-02"), new BigDecimal("3.54960000000000000000"),
				CurrencyCode.EUR);
		// when
		RateData result = sqlConnection.getRateData(request, date);
		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldRetrunNull_whenGivenRateThatDontExistInSql() throws ReadingRateDataException {
		// given
		SqlConnection sqlConnection = new SqlConnection();
		Request request = Request.getBuilder(new BigDecimal("3"), CurrencyCode.EUR).date(LocalDate.now().plusDays(1))
				.build();
		RateData expected = null;
		// when
		RateData result = sqlConnection.getRateData(request);
		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldRetrunNull_whenGivenRateThatDontExistInSqlwithDate() throws ReadingRateDataException {
		// given
		SqlConnection sqlConnection = new SqlConnection();
		LocalDate date = LocalDate.now().plusDays(1);
		Request request = Request.getBuilder(new BigDecimal("3"), CurrencyCode.EUR).build();
		RateData expected = null;
		// when
		RateData result = sqlConnection.getRateData(request, date);
		// then
		assertEquals(result, expected);
	}

}
