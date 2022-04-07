package zadanie2.interfaces;

import java.time.LocalDate;

import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.exceptions.dataConnectionExceptions.SavingRateDataException;
import zadanie2.model.exchangerModels.RateData;
import zadanie2.model.exchangerModels.Request;

public interface DataConnection {

	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException;

	default public void saveRateData(RateData rateData) throws SavingRateDataException {
	}
}
