package zadanie2.interfaces.parsers;

import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.model.apiModel.Rate;

public interface ApiParse extends Parse {

	@Override
	public String getFormatType();

	@Override
	public Rate getRateFromString(String inputString) throws ParsingException;
}
