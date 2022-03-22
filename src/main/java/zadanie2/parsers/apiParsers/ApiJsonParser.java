package zadanie2.parsers.apiParsers;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.interfaces.parsers.ApiParse;
import zadanie2.model.apiModel.Rate;
import zadanie2.model.apiModel.RatesTable;

public class ApiJsonParser implements ApiParse {

	private final static String formatType = "json";

	@Override
	public String getFormatType() {
		return formatType;
	}

	@Override
	public Rate getRateFromString(String inputString) throws ParsingException {
		try {
			RatesTable ratesTable = parseData(inputString);
			Rate result = extractRate(ratesTable);
			return result;
		} catch (IOException e) {
			throw new ParsingException("Błłd parsowania danych", e);
		}
	}

	private RatesTable parseData(String inputString) throws StreamReadException, DatabindException, IOException {
		return new ObjectMapper().readValue(inputString, RatesTable.class);
	}

	private Rate extractRate(RatesTable ratesTable) {
		return ratesTable.getRates().get(0);
	}

}