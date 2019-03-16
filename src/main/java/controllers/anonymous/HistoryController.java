
package controllers.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.HistoryService;
import controllers.AbstractController;
import domain.History;

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private ConfigurationService	configurationService;


	// Display ----------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		History history;
		Boolean security;

		security = this.historyService.securityHistory();

		if (security) {

			history = this.historyService.findByBrotherhoodId(brotherhoodId);

			final String banner = this.configurationService.findConfiguration().getBanner();

			result = new ModelAndView("history/display");
			result.addObject("history", history);
			result.addObject("banner", banner);
			result.addObject("requestURI", "history/display.do");
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;

	}

	//Auxuliar

	//	@RequestMapping(value = "/aux", method = RequestMethod.GET)
	//	public ModelAndView aux() {
	//		ModelAndView result;
	//		Boolean existHistory;
	//
	//		existHistory = this.historyService.securityHistory();
	//
	//		if (existHistory)
	//			result = new ModelAndView("redirect:display.do");
	//		else
	//			result = new ModelAndView("redirect:/inceptionRecord/brotherhood/create.do");
	//
	//		return result;
	//	}
}
