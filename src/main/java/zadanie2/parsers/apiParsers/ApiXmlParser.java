package zadanie2.parsers.apiParsers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.exceptions.parserExceptions.ReadingCurrencyRateException;
import zadanie2.interfaces.parsers.ApiParse;
import zadanie2.model.apiModel.Rate;
import zadanie2.model.apiModel.RatesTable;

public class ApiXmlParser implements ApiParse {

	private final static String formatType = "xml";

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
		} catch (Exception e) {
			throw new ParsingException("Błłd parsowania danych", e);
		}
	}

	private RatesTable parseData(String inputString) throws StreamReadException, DatabindException, IOException {
		return new XmlMapper().readValue(inputString, RatesTable.class);
	}

	private Rate extractRate(RatesTable ratesTable) throws ReadingCurrencyRateException {
		return ratesTable.getRates().get(0);
	}

	@Override
	public List<Rate> getRateList(String inputString) throws ParsingException {
		try {
			RatesTable ratesTable = parseData(inputString);
			return ratesTable.getRates();
		} catch (Exception e) {
			throw new ParsingException("Błłd parsowania danych", e);
		}
	}

}