package zadanie2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.dto.CurrencyDto;
import zadanie2.services.CurrencyService;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
	@Autowired
	CurrencyService currencyService;

	@RequestMapping(method = RequestMethod.GET, value = "/{currencyId}")
	@ResponseBody
	public String getCurrencyById(@PathVariable long currencyId) throws DaoException {
		return currencyService.get(currencyId).toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	@ResponseBody
	public String saveCurrencyDto(@ModelAttribute(name = "currencyDto") CurrencyDto currencyDto) throws DaoException {
		System.out.println(currencyDto.toString());
		return currencyService.save(currencyDto).toString();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{currencyId}")
	@ResponseBody
	public String updateCurrencyDto(@ModelAttribute(name = "currencyDto") CurrencyDto currencyDto,
			@PathVariable long currencyId) throws DaoException {
		System.out.println(currencyDto.toString() + " -- " + currencyId);
		return currencyService.update(currencyId, currencyDto).toString();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{currencyId}")
	@ResponseBody
	public String deleteCurrencyById(@PathVariable long currencyId) throws DaoException {
		currencyService.deleteById(currencyId);
		return "deleted " + currencyId;
	}
}
