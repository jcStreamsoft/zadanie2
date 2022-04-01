package zadanie2.parsers.fileParsers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.interfaces.parsers.FileParse;
import zadanie2.model.fileModel.RatesTable;

public class FileXmlParser implements FileParse {
	private final static String formatType = "xml";

	@Override
	public String getFormatType() {
		return formatType;
	}

	@Override
	public List<RatesTable> getRateFromString(String inputString) throws ParsingException {
		try {
			List<RatesTable> lista = parseData(inputString);
			// RatesTable ratesTable = parseDataX(inputString);
			// BigDecimal result = extractRate(ratesTable);
			return lista;
		} catch (IOException e) {
			throw new ParsingException("Bład parsowania danych", e);
		}
	}

	private BigDecimal extractRate(RatesTable ratesTable) {
		return ratesTable.getRates().get(0).getMid();
	}

	private List<RatesTable> parseData(String inputString) throws JsonMappingException, JsonProcessingException {
		return new XmlMapper().readValue(inputString, new TypeReference<List<RatesTable>>() {
		});
	}

}
