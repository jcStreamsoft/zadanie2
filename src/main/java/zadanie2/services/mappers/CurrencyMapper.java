package zadanie2.services.mappers;

import org.springframework.stereotype.Component;

import zadanie2.model.dto.CurrencyDto;
import zadanie2.model.hibernate.Currency;

@Component
public class CurrencyMapper {
	public Currency fromCurrencyDtoToCurrency(CurrencyDto currencyDto) {
		Currency currency = new Currency();
		currency.setCode(currencyDto.getCode());
		return currency;
	}

	public CurrencyDto fromCurrencyToCurrencyDto(Currency currency) {
		CurrencyDto currencyDto = new CurrencyDto(currency.getId(), currency.getCode());
		return currencyDto;
	}
}
