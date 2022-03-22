package zadanie2;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import zadanie2.connectors.ApiConnection;
import zadanie2.connectors.CachedConnection;
import zadanie2.connectors.FileConnection;
import zadanie2.enums.Currency;
import zadanie2.interfaces.DataConnection;
import zadanie2.model.Request;
import zadanie2.parsers.apiParsers.ApiJsonParser;
import zadanie2.parsers.fileParsers.FileJsonParser;

public class Main {
	public static void main(String[] args) throws IOException {
		test1();

	}

	public static void test1() throws IOException {
		BigDecimal value = new BigDecimal(2);
		LocalDate date = LocalDate.parse("2002-01-02");

		List<DataConnection> connections = List.of(new CachedConnection(),
				new FileConnection(new FileJsonParser(), "fileOldArrayJson.txt"),
				new ApiConnection(new ApiJsonParser()));
		Exchanger nbp = new Exchanger(connections);
		Request request = Request.getBuilder(value, Currency.EUR).date(date).build();

		BigDecimal result = nbp.exchangeToPln(request);
		System.out.println(result);

		Request request2 = Request.getBuilder(value, Currency.EUR).date(date.plusDays(1)).build();
		nbp.exchangeToPln(request2);
		nbp.exchangeToPln(request);
		// BigDecimal result2 = nbp1.exchangeToPln(request2);
		// System.out.println(result2);

		// nbp1.printCache();
	}
}