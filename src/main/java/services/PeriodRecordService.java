
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;

@Service
@Transactional
public class PeriodRecordService {

	// Managed Repository ------------------------
	@Autowired
	private PeriodRecordRepository	periodRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods -----------------------

	public PeriodRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		PeriodRecord result;

		result = new PeriodRecord();

		return result;

	}

	public Collection<PeriodRecord> findAll() {

		Collection<PeriodRecord> result;

		result = this.periodRecordRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public PeriodRecord findOne(final int periodRecordId) {

		PeriodRecord result;

		result = this.periodRecordRepository.findOne(periodRecordId);

		return result;
	}

	public PeriodRecord save(final PeriodRecord periodRecord) {

		Assert.notNull(periodRecord);
		PeriodRecord result;

		result = this.periodRecordRepository.save(periodRecord);

		if (periodRecord.getId() == 0) {

			final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			Assert.notNull(brotherhood);

			final History history = this.historyService.findByBrotherhoodId(brotherhood.getId());
			Assert.notNull(history);
			final Collection<PeriodRecord> pr = history.getPeriodRecords();
			pr.add(result);
			history.setPeriodRecords(pr);
			this.historyService.save(history);
		}

		return result;
	}

	// Other business methods -----------------------

	public Boolean securityPeriod(final int periodId) {

		Boolean res = false;
		Collection<PeriodRecord> loginPeriod = null;

		final PeriodRecord owner = this.findOne(periodId);

		final Brotherhood login = this.brotherhoodService.findByPrincipal();
		final History loginHistory = this.historyService.findByBrotherhoodId(login.getId());

		if (loginHistory != null)
			loginPeriod = loginHistory.getPeriodRecords();

		if (loginPeriod != null && owner != null)
			if (loginPeriod.contains(owner))
				res = true;

		return res;
	}
}
