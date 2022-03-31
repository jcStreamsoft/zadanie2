package zadanie2;

import zadanie2.connectors.apiConnection.ApiConnection;
import zadanie2.connectors.sqlConnection.SqlConnection;
import zadanie2.parsers.apiParsers.ApiJsonParser;

public class MainTest {

	public static void main(String[] args) throws Exception {
		test1();
	}

	public static void test1() throws Exception {
		Exchanger.saveRatesFormApiToSql(new ApiConnection(new ApiJsonParser()), new SqlConnection());
	}

}
