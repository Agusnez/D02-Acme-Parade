
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.HistoryService;
import services.LegalRecordService;
import controllers.AbstractController;
import domain.LegalRecord;

@Controller
@RequestMapping("/legalRecord/brotherhood")
public class LegalRecordBrotherhoodController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private LegalRecordService		legalRecordService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private HistoryService			historyService;


	// Creation ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Boolean security;

		security = this.historyService.securityHistory();

		if (security) {
			final LegalRecord legalRecord;
			legalRecord = this.legalRecordService.create();

			result = this.createEditModelAndView(legalRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalRecordId) {
		ModelAndView result;
		LegalRecord legalRecord;

		Boolean security;
		security = this.legalRecordService.securityLegal(legalRecordId);

		if (security) {

			legalRecord = this.legalRecordService.findOne(legalRecordId);
			Assert.notNull(legalRecord);
			result = this.createEditModelAndView(legalRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalRecord legalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalRecord);
		else
			try {
				this.legalRecordService.save(legalRecord);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(legalRecord, "legalRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final LegalRecord legalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(legalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalRecord legalRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("legalRecord/brotherhood/edit");
		result.addObject("legalRecord", legalRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
