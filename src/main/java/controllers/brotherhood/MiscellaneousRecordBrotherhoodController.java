
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/brotherhood")
public class MiscellaneousRecordBrotherhoodController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private HistoryService				historyService;


	// Creation ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Boolean security;

		security = this.historyService.securityHistory();

		if (security) {
			final MiscellaneousRecord miscellaneousRecord;
			miscellaneousRecord = this.miscellaneousRecordService.create();

			result = this.createEditModelAndView(miscellaneousRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		Boolean security;
		security = this.miscellaneousRecordService.securityMiscellaneous(miscellaneousRecordId);

		if (security) {

			miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			Assert.notNull(miscellaneousRecord);
			result = this.createEditModelAndView(miscellaneousRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		final MiscellaneousRecord miscellaneousRecordFind = this.miscellaneousRecordService.findOne(miscellaneousRecord.getId());
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (miscellaneousRecordFind == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else
			try {
				this.miscellaneousRecordService.delete(miscellaneousRecordFind);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecordFind, "miscellaneousRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("miscellaneousRecord/brotherhood/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
