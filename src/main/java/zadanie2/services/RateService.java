package zadanie2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zadanie2.daos.RateDao;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.dto.RateDto;
import zadanie2.model.hibernate.Rate;
import zadanie2.services.mappers.RateMapper;

@Service
public class RateService {

	@Autowired
	private RateDao rateDao;
	@Autowired
	private RateMapper mapper;

	@Transactional
	public RateDto get(long id) throws DaoException {
		Rate rate = rateDao.get(id);
		RateDto rateDto = mapper.fromRateToRateDto(rate);
		return rateDto;
	}

	@Transactional
	public RateDto deleteById(long id) throws DaoException {
		Rate rate = rateDao.deleteById(id);
		RateDto rateDto = mapper.fromRateToRateDto(rate);
		return rateDto;
	}

	@Transactional
	public RateDto save(RateDto rateDto) throws DaoException {
		Rate savedRate = rateDao.save(mapper.fromRateDtoToRate(rateDto));
		RateDto savedRateDto = mapper.fromRateToRateDto(savedRate);
		return savedRateDto;
	}

	@Transactional
	public RateDto update(long id, RateDto rateDto) throws DaoException {
		Rate updatedRate = rateDao.update(id, mapper.fromRateDtoToRate(rateDto));
		RateDto updatedRateDto = mapper.fromRateToRateDto(updatedRate);
		return updatedRateDto;
	}
}