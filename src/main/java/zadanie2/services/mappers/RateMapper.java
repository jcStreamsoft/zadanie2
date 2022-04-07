package zadanie2.services.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zadanie2.model.dto.RateDto;
import zadanie2.model.hibernate.Rate;

@Component
public class RateMapper {
	@Autowired
	private CurrencyMapper currencyMapper;

	public Rate fromRateDtoToRate(RateDto rateDto) {
		Rate rate = new Rate();
		rate.setDate(rateDto.getDate());
		rate.setValue(rateDto.getValue());
		rate.setCurrency(null);
		return rate;
	}

	public RateDto fromRateToRateDto(Rate rate) {
		RateDto rateDto = new RateDto();
		rateDto.setId(rate.getId());
		rateDto.setCurrencyDto(currencyMapper.fromCurrencyToCurrencyDto(rate.getCurrency()));
		rateDto.setDate(rate.getDate());
		rateDto.setValue(rate.getValue());
		return rateDto;
	}
}
