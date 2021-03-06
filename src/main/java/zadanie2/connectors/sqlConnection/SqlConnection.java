package zadanie2.connectors.sqlConnection;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import zadanie2.daos.CurrencyDao;
import zadanie2.daos.RateDao;
import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.exceptions.dataConnectionExceptions.SavingRateDataException;
import zadanie2.interfaces.DataConnection;
import zadanie2.model.exchangerModels.RateData;
import zadanie2.model.exchangerModels.Request;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class SqlConnection implements DataConnection {
	private RateDao rateDao;
	private CurrencyDao currencyDao;
	@Autowired
	private HibernateFactory hibernateFactory;

	public SqlConnection(HibernateFactory hibernateFactory) {
		this.hibernateFactory = hibernateFactory;
		this.currencyDao = new CurrencyDao(hibernateFactory);
		this.rateDao = new RateDao(hibernateFactory);
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

	private Rate findRate(LocalDate date, CurrencyCode currencyCode) throws DaoException, CreatingSessionException {

		Currency currency2 = currencyDao.getByCurrencyCode(currencyCode);
		return rateDao.getRateByDateAndCurrencyCode(currency2, date);
	}

	private void saveRateDataToSql(RateData rateData) throws DaoException, CreatingSessionException {

		Currency currency = currencyDao.getByCurrencyCode(rateData.getCurrencyCode());
		if (currency != null) {
			Rate oldRate = rateDao.getRateByDateAndCurrencyCode(currency, rateData.getDate());
			if (oldRate == null) {
				rateDao.save(new Rate(rateData.getRate(), rateData.getDate(), currency));
			}
		}
	}

	public void saveRateDataListToSql(Set<RateData> rateDataList, CurrencyCode currencyCode)
			throws DaoException, CreatingSessionException {

		List<Rate> rateList = new LinkedList<>();
		Currency currency = currencyDao.getByCurrencyCode(currencyCode);
		for (RateData r : rateDataList) {
			if (currency != null) {
				rateList.add(new Rate(r.getRate(), r.getDate(), currency));
			}
		}
		if (rateList != null) {
			System.out.println(rateList.size() + " ---  przekonwertowane ");
			rateDao.saveRateList(rateList);
		}

	}
}