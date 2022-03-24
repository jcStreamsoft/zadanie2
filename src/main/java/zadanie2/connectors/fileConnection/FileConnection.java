package zadanie2.connectors.fileConnection;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.interfaces.DataConnection;
import zadanie2.interfaces.parsers.FileParse;
import zadanie2.model.RateData;
import zadanie2.model.Request;
import zadanie2.model.fileModel.Rate;
import zadanie2.model.fileModel.RatesTable;

public class FileConnection implements DataConnection {

	private FileParse parser;
	private FileReader fileReader;
	private String filePath;

	public FileConnection(FileParse parser, String filePath) {
		this.fileReader = new FileReader(filePath);
		this.filePath = filePath;
		this.parser = parser;

	}

	@Override
	public RateData getRateData(Request request) throws ReadingRateDataException {
		try {

			List<RatesTable> rates = parser.getRateFromString(fileReader.getStringFromFile());
			Rate rate = findRate(rates, request);
			RateData rateData = null;
			if (rate != null) {
				rateData = new RateData(request.getDate(), rate.getMid(), request.getCurrencyCode());
			}
			return rateData;
		} catch (IOException e) {
			throw new ReadingRateDataException("Nie znaleziono pliku o sciezce : " + filePath, e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("Blad przy parsowaniu : " + filePath, e);
		}
	}

	@Override
	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException {
		try {
			List<RatesTable> rates = parser.getRateFromString(fileReader.getStringFromFile());

			Rate rate = findRate(rates, request, date);
			RateData rateData = null;
			if (rate != null) {
				rateData = new RateData(date, rate.getMid(), request.getCurrencyCode());
			}
			return rateData;
		} catch (IOException e) {
			throw new ReadingRateDataException("Nie znaleziono pliku o sciezce : " + filePath, e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("Blad przy parsowaniu : " + filePath, e);
		}
	}

	private Rate findRate(List<RatesTable> ratesTable, Request request) {
		for (RatesTable rateTable : ratesTable) {
			if (dateEquals(rateTable, request.getDate())) {
				for (Rate rate : rateTable.getRates()) {
					if (currencyCodeEquals(rate, request)) {
						return rate;
					}
				}
			}
		}
		return null;
	}

	private Rate findRate(List<RatesTable> ratesTable, Request request, LocalDate date) {
		for (RatesTable rateTable : ratesTable) {
			if (dateEquals(rateTable, date)) {
				for (Rate rate : rateTable.getRates()) {
					if (currencyCodeEquals(rate, request)) {
						return rate;
					}
				}
			}
		}
		return null;
	}

	public void printCurrencies() throws ParsingException, IOException {
		List<RatesTable> rates = parser.getRateFromString(fileReader.getStringFromFile());

		RatesTable rateTable = rates.get(0);
		List<Rate> rateList = rateTable.getRates();
		for (Rate r : rateList) {
			System.out.println(r.getCode().toUpperCase() + "(\"" + r.getCode().toLowerCase() + "\"),");
		}
	}

	private boolean dateEquals(RatesTable rateTable, LocalDate date) {
		return rateTable.getEffectiveDate().isEqual(date);
	}

	private boolean currencyCodeEquals(Rate rate, Request request) {
		return rate.getCode().equals(request.getCurrencyCodeString().toUpperCase());
	}
}
