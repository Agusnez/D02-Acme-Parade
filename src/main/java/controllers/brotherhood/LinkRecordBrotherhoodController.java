
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
import services.LinkRecordService;
import controllers.AbstractController;
import domain.LinkRecord;

@Controller
@RequestMapping("/linkRecord/brotherhood")
public class LinkRecordBrotherhoodController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private LinkRecordService		linkRecordService;

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
			final LinkRecord linkRecord;
			linkRecord = this.linkRecordService.create();

			result = this.createEditModelAndView(linkRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int linkRecordId) {
		ModelAndView result;
		LinkRecord linkRecord;

		Boolean security;
		security = this.linkRecordService.securityLink(linkRecordId);

		if (security) {

			linkRecord = this.linkRecordService.findOne(linkRecordId);
			Assert.notNull(linkRecord);
			result = this.createEditModelAndView(linkRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LinkRecord linkRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(linkRecord);
		else
			try {
				this.linkRecordService.save(linkRecord);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(linkRecord, "linkRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final LinkRecord linkRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(linkRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LinkRecord linkRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("linkRecord/brotherhood/edit");
		result.addObject("linkRecord", linkRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
