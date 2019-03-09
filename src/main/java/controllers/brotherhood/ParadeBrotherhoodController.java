
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ConfigurationService;
import services.MessageService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Float;
import domain.Parade;

@Controller
@RequestMapping("/parade/brotherhood")
public class ParadeBrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MessageService			messageService;


	//List---------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Parade> parades;
		final Brotherhood b;

		b = this.brotherhoodService.findByPrincipal();

		parades = this.paradeService.findParadeByBrotherhoodId(b.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("parade/list");
		result.addObject("parades", parades);
		result.addObject("requestURI", "parade/brotherhood/list.do");
		result.addObject("pagesize", 5);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		result.addObject("autoridad", "brotherhood");

		return result;

	}

	@RequestMapping(value = "/listByParade", method = RequestMethod.GET)
	public ModelAndView listByParade(@RequestParam final int paradeId) {

		final ModelAndView result;
		final Collection<Float> floats;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Parade parade = this.paradeService.findOne(paradeId);
		if (parade == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {
			floats = parade.getFloats();

			result = new ModelAndView("float/list");
			result.addObject("floats", floats);
			result.addObject("requestURI", "parade/brotherhood/listByParade.do");
			result.addObject("pagesize", 5);
			result.addObject("banner", banner);
		}

		return result;

	}
	//Display------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int paradeId) {
		final ModelAndView result;
		final Brotherhood login;
		final Brotherhood owner;

		final String banner = this.configurationService.findConfiguration().getBanner();
		final Parade paradeFound = this.paradeService.findOne(paradeId);

		if (paradeFound == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			login = this.brotherhoodService.findByPrincipal();
			owner = paradeFound.getBrotherhood();

			if (login.getId() == owner.getId()) {
				result = new ModelAndView("parade/display");
				result.addObject("parade", paradeFound);
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	//Create-----------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Brotherhood b;
		b = this.brotherhoodService.findByPrincipal();

		if (b.getArea() == null)
			result = new ModelAndView("misc/noArea");
		else {
			final Parade parade = this.paradeService.create();
			final String banner = this.configurationService.findConfiguration().getBanner();

			result = new ModelAndView("parade/edit");
			result.addObject("parade", parade);
			result.addObject("banner", banner);
		}
		return result;

	}
	//Editar-------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;
		Boolean security;

		final Brotherhood b;
		b = this.brotherhoodService.findByPrincipal();
		final Parade paradeFind = this.paradeService.findOne(paradeId);
		final String banner = this.configurationService.findConfiguration().getBanner();

		if (b.getArea() == null) {
			result = new ModelAndView("misc/noArea");
			result.addObject("banner", banner);
		} else if (paradeFind == null) {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		} else {

			parade = this.paradeService.findOne(paradeId);
			security = this.paradeService.paradeBrotherhoodSecurity(paradeId);

			if (security)
				result = this.createEditModelAndView(parade, null);
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "parade") Parade parade, final BindingResult binding) {
		ModelAndView result;

		final Brotherhood b;
		b = this.brotherhoodService.findByPrincipal();

		if (b.getArea() == null)
			result = new ModelAndView("misc/noArea");
		else {

			parade = this.paradeService.reconstruct(parade, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(parade, null);
			else
				try {
					this.paradeService.save(parade);

					if (parade.getFinalMode())
						this.messageService.NotificationNewParade(parade);

					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(parade, "parade.commit.error");

				}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Parade parade, final BindingResult binding) {
		ModelAndView result;

		parade = this.paradeService.findOne(parade.getId());

		try {
			this.paradeService.delete(parade);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error");
		}

		return result;
	}

	//Other business methods------------------------------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Parade parade, final String messageCode) {
		final ModelAndView result;

		final Collection<Float> floats = parade.getFloats();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("parade/edit");
		result.addObject("parade", parade);
		result.addObject("floats", floats);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		return result;
	}
}