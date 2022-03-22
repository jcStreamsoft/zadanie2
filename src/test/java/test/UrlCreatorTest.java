package test;


import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie2.connectors.UrlCreator;
import zadanie2.enums.Currency;
import zadanie2.exceptions.dataConnectionExceptions.CreatingURLException;

public class UrlCreatorTest {

	@Test
	public void shouldReturnCorrectURL_whenCorrectUrlreturned() throws CreatingURLException, MalformedURLException {
		// given
		String code = Currency.EUR.getCode();
		UrlCreator creator = new UrlCreator(code, "json");
		LocalDate date = LocalDate.parse("2020-01-07");
		URL expected = new URL("http://api.nbp.pl/api/exchangerates/rates/a/eur/2020-01-07/?format=json");
		// when
		URL url = creator.createDateRateUrl(date);
		// then
		assertEquals(url, expected);
	}
}
