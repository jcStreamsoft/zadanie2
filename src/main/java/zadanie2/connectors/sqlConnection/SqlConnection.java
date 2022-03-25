package zadanie2.connectors.sqlConnection;

import java.time.LocalDate;

import zadanie2.daos.CurrencyDao;
import zadanie2.daos.RateDao;
import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.DaoException.DaoException;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.exceptions.dataConnectionExceptions.SavingRateDataException;
import zadanie2.interfaces.DataConnection;
import zadanie2.model.RateData;
import zadanie2.model.Request;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class SqlConnection implements DataConnection {
	private RateDao rateDao;
	private CurrencyDao CurrencyDao;

	public SqlConnection() {
		this.rateDao = new RateDao();
		this.CurrencyDao = new CurrencyDao();
	}

	@Override
	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException {
		try {
			RateData rateData = null;
			Rate rate = findRate(date, request.getCurrencyCode());
			if (rate != null) {
				rateData = new RateData(date, rate.getValue(), request.getCurrencyCode());
			}
			return rateData;
		} catch (Exception e) {
			throw new ReadingRateDataException("Blad zczytywania sql", e);
		}
	}

	@Override
	public void saveRateData(RateData rateData) throws SavingRateDataException {
		try {
			saveRateDataToSql(rateData);
		} catch (Exception e) {
			throw new SavingRateDataException("Blad przy zapisie Rate do sql", e);
		}
	}

	private Rate findRate(LocalDate date, CurrencyCode currencyCode) throws DaoException {
		Currency currency2 = CurrencyDao.getByCurrencyCode(currencyCode);
		return rateDao.getRateByDateAndCurrencyCode(currency2, date);
	}

	private void saveRateDataToSql(RateData rateData) throws DaoException {
		Currency currency = CurrencyDao.getByCurrencyCode(rateData.getCurrencyCode());
		if (currency != null) {
			Rate oldRate = rateDao.getRateByDateAndCurrencyCode(currency, rateData.getDate());
			if (oldRate == null) {
				rateDao.save(new Rate(rateData.getRate(), rateData.getDate(), currency));
			}
		}
	}
}
