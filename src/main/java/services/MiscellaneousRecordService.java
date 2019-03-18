
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private HistoryService					historyService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private BrotherhoodService				brotherhoodService;


	// Simple CRUD methods -----------------------

	public MiscellaneousRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final MiscellaneousRecord result;

		result = new MiscellaneousRecord();

		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;

		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		if (miscellaneousRecord.getId() == 0) {

			final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			Assert.notNull(brotherhood);

			final History history = this.historyService.findByBrotherhoodId(brotherhood.getId());
			Assert.notNull(history);
			final Collection<MiscellaneousRecord> mr = history.getMiscellaneousRecords();
			mr.add(result);
			history.setMiscellaneousRecords(mr);
			this.historyService.save(history);
		}

		return result;
	}

	// Other business methods -----------------------

	public Boolean securityMiscellaneous(final int miscellaneousId) {

		Boolean res = false;
		Collection<MiscellaneousRecord> loginMiscellaneous = null;

		final MiscellaneousRecord owner = this.findOne(miscellaneousId);

		final Brotherhood login = this.brotherhoodService.findByPrincipal();
		final History loginHistory = this.historyService.findByBrotherhoodId(login.getId());

		if (loginHistory != null)
			loginMiscellaneous = loginHistory.getMiscellaneousRecords();

		if (loginMiscellaneous != null && owner != null)
			if (loginMiscellaneous.contains(owner))
				res = true;

		return res;
	}
}