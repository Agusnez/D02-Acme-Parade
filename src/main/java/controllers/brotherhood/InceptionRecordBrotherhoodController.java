
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.HistoryService;
import services.InceptionRecordService;
import controllers.AbstractController;
import domain.History;
import domain.InceptionRecord;

@Controller
@RequestMapping("/inceptionRecord/brotherhood")
public class InceptionRecordBrotherhoodController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private InceptionRecordService	inceptionRecordService;

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private ConfigurationService	configurationService;


	// Create -------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		InceptionRecord inceptionRecord;
		Boolean security;

		security = this.historyService.securityHistory();

		if (!security) {

			final String banner = this.configurationService.findConfiguration().getBanner();

			inceptionRecord = this.inceptionRecordService.create();
			result = this.create2ModelAndView(inceptionRecord);
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save1")
	public ModelAndView save1(@Valid final InceptionRecord inceptionRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.create2ModelAndView(inceptionRecord);
		else
			try {
				final InceptionRecord ir = this.inceptionRecordService.save(inceptionRecord);
				final History history;
				history = this.historyService.create(ir);
				this.historyService.save(history);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.create2ModelAndView(inceptionRecord, "inceptionRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save2")
	public ModelAndView save2(@Valid final InceptionRecord inceptionRecord, final BindingResult binding, @RequestParam final int historyId) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(inceptionRecord, historyId);
		else
			try {
				this.inceptionRecordService.save(inceptionRecord);
				result = new ModelAndView("redirect:/brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(inceptionRecord, "inceptionRecord.commit.error", historyId);
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final InceptionRecord inceptionRecord, final int historyId) {
		ModelAndView result;

		result = this.createEditModelAndView(inceptionRecord, null, historyId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final InceptionRecord inceptionRecord, final String message, final int historyId) {

		ModelAndView result;
		result = new ModelAndView("inceptionRecord/brotherhood/edit");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("historyId", historyId);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView create2ModelAndView(final InceptionRecord inceptionRecord) {
		ModelAndView result;

		result = this.create2ModelAndView(inceptionRecord, null);

		return result;
	}

	protected ModelAndView create2ModelAndView(final InceptionRecord inceptionRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("inceptionRecord/brotherhood/edit");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
