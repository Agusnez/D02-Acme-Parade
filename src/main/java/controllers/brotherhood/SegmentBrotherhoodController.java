package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Brotherhood;
import domain.Parade;
import domain.Segment;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.ParadeService;
import services.SegmentService;

@Controller
@RequestMapping("/segment/brotherhood")
public class SegmentBrotherhoodController {
	
	@Autowired
	private SegmentService segmentService;
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	@Autowired
	private ParadeService paradeService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping(value = "/path", method = RequestMethod.GET)
	public ModelAndView path(@RequestParam final int paradeId) {
		ModelAndView result;
		Brotherhood member = this.brotherhoodService.findByPrincipal();
		Parade parade = this.paradeService.findOne(paradeId);
		
		if (parade == null)
			return new ModelAndView("redirect:/welcome/index.do");
		
		if (parade.getBrotherhood().equals(member)) {
			Collection<Segment> segments = this.segmentService.findByParade(paradeId);

			final String banner = this.configurationService.findConfiguration().getBanner();
			result = new ModelAndView("segment/path");
			result.addObject("segments", segments);
			result.addObject("banner", banner);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int paradeId) {
		ModelAndView result;
		Brotherhood member = this.brotherhoodService.findByPrincipal();
		
		Segment segment = this.segmentService.findOne(paradeId);
		
		if (segment == null)
			return new ModelAndView("redirect:/welcome/index.do");
		
		if (this.paradeService.paradeBrotherhoodSecurity(segment.getParade().getId())) {
			
			final String banner = this.configurationService.findConfiguration().getBanner();
			result = new ModelAndView("segment/display");
			result.addObject("segment", segment);
			result.addObject("banner", banner);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}

}
