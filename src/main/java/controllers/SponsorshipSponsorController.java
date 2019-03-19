
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.SponsorService;
import services.SponsorshipService;
import domain.Sponsor;
import domain.Sponsorship;

public class SponsorshipSponsorController {

	//Services -----------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private SponsorService			sponsorService;


	//List----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findAll();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorships/sponsor/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);

		return result;

	}

	//Display------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int sponsorshipId) {
		final ModelAndView result;
		final Sponsorship sponsorship;
		final Sponsor login;

		final String banner = this.configurationService.findConfiguration().getBanner();
		final Sponsorship sponsorshipNotFound = this.sponsorshipService.findOne(sponsorshipId);

		if (sponsorshipNotFound == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			login = this.sponsorService.findByPrincipal();

			sponsorship = this.sponsorshipService.findOne(sponsorshipId);

			if (login == sponsorship.getSponsor()) {

				result = new ModelAndView("sponsorship/display");
				result.addObject("sponsorship", sponsorship);
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
