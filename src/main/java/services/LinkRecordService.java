
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LinkRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	// Managed Repository ------------------------
	@Autowired
	private LinkRecordRepository	linkRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods -----------------------

	public LinkRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		LinkRecord result;

		result = new LinkRecord();

		return result;

	}

	public Collection<LinkRecord> findAll() {

		Collection<LinkRecord> result;

		result = this.linkRecordRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public LinkRecord findOne(final int linkRecordId) {

		LinkRecord result;

		result = this.linkRecordRepository.findOne(linkRecordId);

		return result;
	}

	public LinkRecord save(final LinkRecord linkRecord) {

		Assert.notNull(linkRecord);
		LinkRecord result;

		result = this.linkRecordRepository.save(linkRecord);

		if (linkRecord.getId() == 0) {

			final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			Assert.notNull(brotherhood);

			final History history = this.historyService.findByBrotherhoodId(brotherhood.getId());
			Assert.notNull(history);
			final Collection<LinkRecord> lr = history.getLinkRecords();
			lr.add(result);
			history.setLinkRecords(lr);
			this.historyService.save(history);
		}

		return result;
	}

	// Other business methods -----------------------

	public Boolean securityLink(final int linkId) {

		Boolean res = false;
		Collection<LinkRecord> loginLink = null;

		final LinkRecord owner = this.findOne(linkId);

		final Brotherhood login = this.brotherhoodService.findByPrincipal();
		final History loginHistory = this.historyService.findByBrotherhoodId(login.getId());

		if (loginHistory != null)
			loginLink = loginHistory.getLinkRecords();

		if (loginLink != null && owner != null)
			if (loginLink.contains(owner))
				res = true;

		return res;
	}
}