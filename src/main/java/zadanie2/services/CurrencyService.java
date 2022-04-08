package zadanie2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zadanie2.daos.CurrencyDao;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.dto.CurrencyDto;
import zadanie2.model.hibernate.Currency;
import zadanie2.services.mappers.CurrencyMapper;

@Service
@Transactional
public class CurrencyService {
	@Autowired
	private CurrencyDao currencyDao;
	@Autowired
	private CurrencyMapper mapper;

	@Transactional
	public CurrencyDto get(long id) throws DaoException {
		Currency currency = currencyDao.get(id);
		CurrencyDto currencyDto = mapper.fromCurrencyToCurrencyDto(currency);
		return currencyDto;
	}

	@Transactional
	public CurrencyDto deleteById(long id) throws DaoException {
		Currency currency = currencyDao.deleteById(id);
		CurrencyDto currencyDto = mapper.fromCurrencyToCurrencyDto(currency);
		return currencyDto;
	}

	@Transactional
	public CurrencyDto save(CurrencyDto currencyDto) throws DaoException {
		Currency savedCurrency = currencyDao.save(mapper.fromCurrencyDtoToCurrency(currencyDto));
		CurrencyDto savedCurrencyDto = mapper.fromCurrencyToCurrencyDto(savedCurrency);
		return savedCurrencyDto;
	}

	@Transactional
	public CurrencyDto update(long id, CurrencyDto currencyDto) throws DaoException {
		Currency updatedCurrency = currencyDao.update(id, mapper.fromCurrencyDtoToCurrency(currencyDto));
		CurrencyDto updatedCurrencyDto = mapper.fromCurrencyToCurrencyDto(updatedCurrency);
		return updatedCurrencyDto;
	}
}
