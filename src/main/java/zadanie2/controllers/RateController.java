package zadanie2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.services.RateService;

@RestController
public class RateController {

	@Autowired
	RateService rateService;

	@GetMapping("/rates/{RateId}")
	public String getRateById(@PathVariable Integer rateId) throws DaoException {
		return rateService.get(rateId).toString();
	}

	@PostMapping("/rates")
	public String getFirstTenRecords(@PathVariable Integer rateId) throws DaoException {
		return rateService.get(rateId).toString();
	}
}