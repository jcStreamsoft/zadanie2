package zadanie2.connectors.apiConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import zadanie2.exceptions.dataConnectionExceptions.CreatingURLException;

public class UrlCreator {

	private final static String NBP_ADRESS = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private String currencyCode;
	private String responseType;
	private String newUrl;

	public UrlCreator(String currencyCode, String reponseType) {
		super();
		this.currencyCode = currencyCode;
		this.responseType = reponseType;
	}

	public URL createDateRateUrl(LocalDate localDate) throws CreatingURLException {
		try {
			newUrl = new String(NBP_ADRESS + currencyCode + "/" + localDate + "/?format=" + responseType);
			return new URL(newUrl);
		} catch (MalformedURLException e) {
			throw new CreatingURLException("Blad tworzenia linku", e);
		}
	}

	public URL createUrlForRatesInterval(LocalDate dateStart, LocalDate dateEnd) throws CreatingURLException {
		try {
			newUrl = new String(
					NBP_ADRESS + currencyCode + "/" + dateStart + "/" + dateEnd + "/?format=" + responseType);
			// System.out.println(newUrl);
			return new URL(newUrl);
		} catch (MalformedURLException e) {
			throw new CreatingURLException("Blad tworzenia linku", e);
		}
	}
}
