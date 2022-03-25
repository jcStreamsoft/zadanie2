package zadanie2.connectors;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.interfaces.DataConnection;
import zadanie2.model.RateData;
import zadanie2.model.Request;

public class CachedConnection implements DataConnection {

	private static final Map<String, RateData> data = new HashMap<>();

	@Override
	public void saveRateData(RateData rateData) {
		String key = rateData.getDate() + "/" + rateData.getCurrencyCode();

		data.put(key, rateData);
	}

	@Override
	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException {
		String key = date + "/" + request.getCurrencyCode();
		RateData rateData = data.get(key);
		return rateData;
	}

	public void print() {
		for (Map.Entry entry : data.entrySet()) {
			System.out.println("key: " + entry.toString());
		}
	}
}
