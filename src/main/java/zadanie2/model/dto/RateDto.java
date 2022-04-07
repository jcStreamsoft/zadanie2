package zadanie2.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RateDto {

	private long id;
	private BigDecimal value;
	private LocalDate date;
	private CurrencyDto currencyDto;

	public RateDto() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public CurrencyDto getCurrencyDto() {
		return currencyDto;
	}

	public void setCurrencyDto(CurrencyDto currencyDto) {
		this.currencyDto = currencyDto;
	}

	@Override
	public String toString() {
		return "RateDto [id=" + id + ", value=" + value + ", date=" + date + ", currencyDto=" + currencyDto + "]";
	}

}
