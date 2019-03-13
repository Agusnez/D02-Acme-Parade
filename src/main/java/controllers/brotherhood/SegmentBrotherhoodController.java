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
		Brotherhood member = this.brotherhoodService.findByPrincipal();
		Parade parade = this.paradeService.findOne(paradeId);
		ModelAndView result;
		
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

}
