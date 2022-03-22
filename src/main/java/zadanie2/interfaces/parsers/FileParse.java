package zadanie2.interfaces.parsers;

import java.util.List;

import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.model.fileModel.RatesTable;

public interface FileParse extends Parse {
	@Override
	public String getFormatType();

	@Override
	public List<RatesTable> getRateFromString(String inputString) throws ParsingException;
}
