package zadanie2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.services.RateService;

@RestController
public class RateController {

	@Autowired
	RateService service;

	@GetMapping("/rates")
	public String getFirstTenRecords() throws DaoException {
		return service.get().toString();

	}

}