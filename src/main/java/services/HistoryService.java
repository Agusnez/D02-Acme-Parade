
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Service
@Transactional
public class HistoryService {

	//Managed repository---------------------------------
	@Autowired
	private HistoryRepository		historyRepository;

	//Suporting services---------------------------------
	@Autowired
	private InceptionRecordService	inceptionRecordService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	//	@Autowired
	//	private Validator				validator;

	//Simple CRUD methods--------------------------------
	public History create(final InceptionRecord inceptionRecord) {

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Assert.notNull(brotherhood);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(brotherhood.getUserAccount().getAuthorities().contains(authority));

		final History h = new History();

		h.setBrotherhood(brotherhood);
		h.setInceptionRecord(inceptionRecord);

		h.setLegalRecords(new ArrayList<LegalRecord>());
		h.setLinkRecords(new ArrayList<LinkRecord>());
		h.setPeriodRecords(new ArrayList<PeriodRecord>());
		h.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());

		return h;
	}

	public Collection<History> findAll() {
		Collection<History> result;
		result = this.historyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public History findOne(final int historyId) {
		History h;
		h = this.historyRepository.findOne(historyId);
		Assert.notNull(h);
		return h;
	}

	public History save(final History history) {
		Assert.notNull(history);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Actor owner = history.getBrotherhood();
		Assert.isTrue(actor.getId() == owner.getId());
		if (history.getId() == 0)
			this.inceptionRecordService.save(history.getInceptionRecord());

		final History h = this.historyRepository.save(history);

		return h;
	}

	//Other business methods----------------------------

	public History findByBrotherhoodId(final int brotherhoodId) {

		final History result = this.historyRepository.findByBrotherhoodId(brotherhoodId);

		return result;
	}

	public Boolean securityHistory() {

		Boolean res = false;

		final Brotherhood login = this.brotherhoodService.findByPrincipal();

		final History owner = this.historyRepository.findByBrotherhoodId(login.getId());

		if (owner != null)
			res = true;

		return res;

	}

	//	public History reconstruct(final History history, final BindingResult binding) {
	//
	//		History result;
	//
	//		if (history.getId() == 0 || history == null) {
	//
	//			final InceptionRecord ir = this.inceptionRecordService.create();
	//
	//			final History historyNew = this.create(ir);
	//
	//			history.setBrotherhood(historyNew.getBrotherhood());
	//
	//			this.validator.validate(history, binding);
	//
	//			result = history;
	//		} else {
	//
	//			final History historyBBDD = this.findOne(history.getId());
	//
	//			history.setBrotherhood(historyBBDD.getBrotherhood());
	//
	//			this.validator.validate(history, binding);
	//
	//			result = history;
	//		}
	//
	//		return result;
	//
	//	}
}
