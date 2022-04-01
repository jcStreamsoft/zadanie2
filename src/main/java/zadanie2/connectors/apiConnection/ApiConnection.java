package zadanie2.connectors.apiConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.dataConnectionExceptions.CreatingURLException;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.interfaces.DataConnection;
import zadanie2.interfaces.parsers.ApiParse;
import zadanie2.model.RateData;
import zadanie2.model.Request;
import zadanie2.model.apiModel.Rate;

public class ApiConnection implements DataConnection {

	private HttpURLConnection connection;
	private UrlCreator urlCreator;
	private ApiParse parser;

	public ApiConnection(ApiParse parser) {
		super();
		this.parser = parser;
	}

	@Override
	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException {
		this.urlCreator = new UrlCreator(request.getCurrencyCodeString(), parser.getFormatType());
		try {
			RateData rateData = null;
			if (connectionExitst(createURL(date))) {
				String result = createStringFromStream(connection.getInputStream());
				connection.disconnect();

				Rate rate = parser.getRateFromString(result);
				rateData = new RateData(date, rate.getMid(), request.getCurrencyCode());
			}
			return rateData;
		} catch (IOException | CreatingURLException e) {
			throw new ReadingRateDataException("Blad przy połczeniu z NBP ", e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("Blad przy parsowaniu danych z NBP ", e);
		}
	}

	private URL createURL(LocalDate localDate) throws CreatingURLException, IOException {
		return urlCreator.createDateRateUrl(localDate);
	}

	private boolean connectionExitst(URL url) throws IOException {
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		if (connection.getResponseCode() != 200) {
			return false;
		} else {
			return true;
		}
	}

	private String createStringFromStream(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining());
	}

	public List<RateData> getListOfRatesForCurrency(CurrencyCode code, LocalDate dateStart, LocalDate dateEnd)
			throws ReadingRateDataException {
		this.urlCreator = new UrlCreator(code.getCode(), parser.getFormatType());
		try {
			List<RateData> rateDataList = new LinkedList<>();
			LocalDate currentDateStart = dateEnd;
			LocalDate currentDateEnd = dateStart;
			for (int i = 0; dateStart.compareTo(currentDateStart) < 0; i++) {
				currentDateStart = dateEnd.minusMonths(i * 8);
				currentDateEnd = dateEnd.minusMonths((i + 1) * 8);

				if (connectionExitst(urlCreator.createUrlForRatesInterval(currentDateEnd, currentDateStart))) {
					String result = createStringFromStream(connection.getInputStream());
					connection.disconnect();

					List<Rate> rateList = parser.getRateList(result);

					if (rateList.size() > 0) {
						System.out.println(rateList.size());
						for (Rate r : rateList) {
							rateDataList.add(new RateData(r.getEffectiveDate(), r.getMid(), code));
						}
					}

				}
			}
			System.out.println(rateDataList.size() + " -- " + code.getCode());
			return rateDataList;
		} catch (IOException | CreatingURLException e) {
			throw new ReadingRateDataException("Blad przy połczeniu z NBP ", e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("Blad przy parsowaniu danych z NBP ", e);
		}
	}

}
