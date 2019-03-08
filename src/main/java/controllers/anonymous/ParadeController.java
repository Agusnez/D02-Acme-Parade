
package controllers.anonymous;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ConfigurationService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Parade;

@Controller
@RequestMapping("/parade")
public class ParadeController extends AbstractController {

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Brotherhood notFound = this.brotherhoodService.findOne(brotherhoodId);

		if (notFound == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			final Collection<Parade> parades;

			parades = this.paradeService.findParadeCanBeSeenOfBrotherhoodId(brotherhoodId);
			final int finderResult = this.configurationService.findConfiguration().getFinderResult();

			result = new ModelAndView("parade/listAnonimo");
			result.addObject("parades", parades);
			result.addObject("requestURI", "parade/list.do");
			result.addObject("pagesize", finderResult);
			result.addObject("AreInFinder", false);
			result.addObject("banner", banner);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
			result.addObject("autoridad", "");
		}
		return result;

	}
}
