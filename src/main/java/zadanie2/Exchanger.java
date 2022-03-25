package zadanie2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import zadanie2.exceptions.ExchangerException;
import zadanie2.exceptions.RateNotFoundException;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.exceptions.dataConnectionExceptions.SavingRateDataException;
import zadanie2.exceptions.inputExceptions.DateAfterTodayException;
import zadanie2.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie2.exceptions.inputExceptions.InputValueNullException;
import zadanie2.exceptions.inputExceptions.NegativeValueException;
import zadanie2.interfaces.DataConnection;
import zadanie2.model.RateData;
import zadanie2.model.Request;

public class Exchanger {
	private List<DataConnection> dataConnections;
	private CurrencyCalculator currencyCalc;
	private static final int MAX_ATTEMPTS = 7;

	public Exchanger(List<DataConnection> dataConnections) {
		this.currencyCalc = new CurrencyCalculator();
		this.dataConnections = dataConnections;

	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			checkValidation(request);

			RateData rateData = findRate(request);
			if (rateData == null) {
				throw new RateNotFoundException("Nie znaleziono kursu dla danej daty");
			}
			saveRateData(rateData);
			return currencyCalc.calculateToPln(request.getValue(), rateData.getRate());
		} catch (Exception e) {
			throw new ExchangerException("Wystapil blad.", e);
		}
	}

	public BigDecimal exchangeFromPln(Request request) {
		try {
			checkValidation(request);

			RateData rateData = findRate(request);
			if (rateData == null) {
				throw new RateNotFoundException("Nie znaleziono kursu dla danej daty");
			}
			saveRateData(rateData);
			return currencyCalc.calculateFromPln(request.getValue(), rateData.getRate());
		} catch (Exception e) {
			throw new ExchangerException("Wystapil blad.", e);
		}
	}

	public RateData findRate(Request request) throws ReadingRateDataException {
		RateData rateData = findRateForPreciseDate(request);
		if (rateData == null) {
			for (int i = 0; i < MAX_ATTEMPTS; i++) {
				LocalDate olderDate = request.getDate().minusDays(i);
				rateData = findOlderRate(request, olderDate);
			}
		}
		return rateData;
	}

	private RateData findRateForPreciseDate(Request request) {
		for (DataConnection dataConnection : dataConnections) {
			try {
				RateData rateData = dataConnection.getRateData(request);
				if (rateData != null) {
					return rateData;
				}
			} catch (ReadingRateDataException e) {
			}
		}
		return null;
	}

	private RateData findOlderRate(Request request, LocalDate date) {
		for (DataConnection dataConnection : dataConnections) {
			try {
				RateData rateData = null;
				rateData = dataConnection.getRateData(request, date);
				if (rateData != null) {
					return rateData;
				}

			} catch (ReadingRateDataException e) {
			}
		}
		return null;
	}

	private void saveRateData(RateData rateData) {
		for (DataConnection dataConnection : dataConnections) {
			try {
				dataConnection.saveRateData(rateData);
			} catch (SavingRateDataException e) {
				// saving error
			}
		}
	}

	private void checkValidation(Request request)
			throws NegativeValueException, InputValueNullException, DateBeforeFirstException, DateAfterTodayException {
		InputValidator.checkDate(request.getDate());
		InputValidator.checkValue(request.getValue());
		InputValidator.checkCurrency(request.getCurrencyCode());
	}
}
