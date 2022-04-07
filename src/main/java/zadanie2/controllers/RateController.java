package zadanie2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.dto.RateDto;
import zadanie2.services.RateService;

@RestController
@RequestMapping("/rates")
public class RateController {

	@Autowired
	RateService rateService;

	@RequestMapping(method = RequestMethod.GET, value = "/{rateId}")
	@ResponseBody
	public String getRateById(@PathVariable long rateId) throws DaoException {
		return rateService.get(rateId).toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	@ResponseBody
	public String saveRateDto(@RequestBody RateDto rate) throws DaoException {
		return rateService.save(rate).toString();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{rateId}")
	@ResponseBody
	public String updateRateDto(@ModelAttribute(name = "rateDto") RateDto rateDto, @PathVariable long rateId)
			throws DaoException {
		return rateService.update(rateId, rateDto).toString();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{rateId}")
	@ResponseBody
	public String deleteRateById(@PathVariable long rateId) throws DaoException {
		rateService.deleteById(rateId);
		return "deleted " + rateId;
	}
}