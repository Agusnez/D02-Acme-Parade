
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.Authority;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private SponsorService			sponsorService;


	public Sponsorship create() {

		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		Assert.isTrue(sponsor.getUserAccount().getAuthorities().contains(authority));

		final Sponsorship sponsorship = new Sponsorship();

		return sponsorship;

	}

	public Sponsorship findOne(final int sponsorshipId) {

		final Sponsorship sponsorship;
		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(sponsorship);
		return sponsorship;

	}

	public Collection<Sponsorship> findAll() {

		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Sponsorship save(final Sponsorship sponsorship) {

		final Sponsorship result = this.sponsorshipRepository.save(sponsorship);

		return result;

	}

	public Collection<Sponsorship> findAllBySponsorId(final int id) {

		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAllBySponsorId(id);

		return sponsorships;
	}

	public Boolean sponsorshipSponsorSecurity(final int sponsorhipId) {
		Boolean res = false;

		final Sponsorship sponsorhip = this.findOne(sponsorhipId);

		final Sponsor login = this.sponsorService.findByPrincipal();

		if (sponsorhip.getSponsor().equals(login))
			res = true;

		return res;
	}

	public void deactivate(final int sponsorshipId) {

		final Sponsorship sponsorship = this.findOne(sponsorshipId);

		sponsorship.setActivated(false);

		this.sponsorshipRepository.save(sponsorship);

	}

	public void reactivate(final int sponsorshipId) {

		final Sponsorship sponsorship = this.findOne(sponsorshipId);

		sponsorship.setActivated(true);

		this.sponsorshipRepository.save(sponsorship);

	}

	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {

		final Sponsorship result = sponsorship;

		if (sponsorship.getId() == 0) {

			sponsorship.setActivated(true);
			sponsorship.setSponsor(this.sponsorService.findByPrincipal());

		} else {

			final Sponsorship theOldOne = this.findOne(sponsorship.getId());

			sponsorship.setActivated(theOldOne.getActivated());
			sponsorship.setSponsor(theOldOne.getSponsor());

		}

		this.validator.validate(result, binding);

		return result;
	}

}
