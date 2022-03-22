package zadanie2.interfaces.parsers;

import zadanie2.exceptions.parserExceptions.ParsingException;

public interface Parse {

	public String getFormatType();

	public <T> T getRateFromString(String inputString) throws ParsingException;
}
