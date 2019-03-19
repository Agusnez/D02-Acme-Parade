
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
import services.PeriodRecordService;
import controllers.AbstractController;
import domain.PeriodRecord;

@Controller
@RequestMapping("/periodRecord/brotherhood")
public class PeriodRecordBrotherhoodController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private PeriodRecordService		periodRecordService;

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
			final PeriodRecord periodRecord;
			periodRecord = this.periodRecordService.create();

			result = this.createEditModelAndView(periodRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int periodRecordId) {
		ModelAndView result;
		PeriodRecord periodRecord;

		Boolean security;
		security = this.periodRecordService.securityPeriod(periodRecordId);

		if (security) {

			periodRecord = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(periodRecord);
			result = this.createEditModelAndView(periodRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PeriodRecord periodRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(periodRecord);
		else
			try {
				this.periodRecordService.save(periodRecord);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(periodRecord, "periodRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PeriodRecord periodRecord) {
		ModelAndView result;

		final PeriodRecord periodRecordFind = this.periodRecordService.findOne(periodRecord.getId());
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (periodRecordFind == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else
			try {
				this.periodRecordService.delete(periodRecordFind);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(periodRecordFind, "periodRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PeriodRecord periodRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(periodRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PeriodRecord periodRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("periodRecord/brotherhood/edit");
		result.addObject("periodRecord", periodRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
