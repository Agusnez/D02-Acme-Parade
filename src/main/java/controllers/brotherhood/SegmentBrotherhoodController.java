
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ConfigurationService;
import services.ParadeService;
import services.SegmentService;
import domain.Brotherhood;
import domain.Parade;
import domain.Segment;
import forms.ContiguousSegmentForm;
import forms.FirstSegmentForm;

@Controller
@RequestMapping("/segment/brotherhood")
public class SegmentBrotherhoodController {

	@Autowired
	private SegmentService			segmentService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/path", method = RequestMethod.GET)
	public ModelAndView path(@RequestParam final int paradeId) {
		final Brotherhood member = this.brotherhoodService.findByPrincipal();
		final Parade parade = this.paradeService.findOne(paradeId);
		ModelAndView result;

		if (parade.getBrotherhood().equals(member)) {
			final Collection<Segment> segments = this.segmentService.findByParade(paradeId);

			final String banner = this.configurationService.findConfiguration().getBanner();
			result = new ModelAndView("segment/path");
			result.addObject("segments", segments);
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int paradeId) {
		final ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		final Boolean exist = this.paradeService.exist(paradeId);

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (exist) {

			if (brotherhood.getArea() == null)
				result = new ModelAndView("misc/noArea");
			else {

				final Boolean security = this.paradeService.paradeBrotherhoodSecurity(paradeId);

				if (security) {
					final Collection<Segment> segments = this.segmentService.segmentsPerParade(paradeId);

					if (segments.isEmpty()) {

						final FirstSegmentForm segment = new FirstSegmentForm();
						segment.setParadeId(paradeId);
						result = this.createEditModelAndView(segment);
					} else {
						final ContiguousSegmentForm segment = new ContiguousSegmentForm();
						segment.setParadeId(paradeId);
						result = this.createEditModelAndView(segment);
					}
				} else
					result = new ModelAndView("redirect:/welcome/index.do");

			}
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;

	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int segmentId) {
	//		ModelAndView result;
	//		Segment segment;
	//		Boolean security;
	//
	//		final Brotherhood b;
	//		b = this.brotherhoodService.findByPrincipal();
	//
	//		if (b.getArea() == null)
	//			result = new ModelAndView("misc/noArea");
	//		else {
	//			segment = this.segmentService.findOne(segmentId);
	//			security = this.paradeService.paradeBrotherhoodSecurity(segment.getParade().getId());
	//
	//			if (security)
	//				result = this.createEditModelAndView(segment, null);
	//			else
	//				result = new ModelAndView("redirect:/welcome/index.do");
	//
	//		}
	//
	//		return result;
	//	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final FirstSegmentForm segment) {
		ModelAndView result;

		result = this.createEditModelAndView(segment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FirstSegmentForm segment, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("segment/edit");
		result.addObject("segment", segment);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ContiguousSegmentForm segment) {
		ModelAndView result;

		result = this.createEditModelAndView(segment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ContiguousSegmentForm segment, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("segment/edit");
		result.addObject("segment", segment);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}

}
