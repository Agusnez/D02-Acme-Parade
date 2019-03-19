
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
import forms.SponsorshipForm;

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

	@Autowired
	private ParadeService			paradeService;


	public SponsorshipForm create() {

		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		Assert.isTrue(sponsor.getUserAccount().getAuthorities().contains(authority));

		final SponsorshipForm sponsorshipForm = new SponsorshipForm();

		return sponsorshipForm;

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

	public Sponsorship reconstruct(final SponsorshipForm sponsorship, final BindingResult binding) {

		final Sponsorship result = new Sponsorship();

		result.setBanner(sponsorship.getBanner());
		result.setParade(this.paradeService.findOne(sponsorship.getParadeId()));
		result.setCreditCard(sponsorship.getCreditCard());
		result.setTargetUrl(sponsorship.getTargetUrl());

		if (sponsorship.getId() == 0) {

			result.setActivated(true);
			result.setSponsor(this.sponsorService.findByPrincipal());

		} else {

			final Sponsorship theOldOne = this.findOne(sponsorship.getId());

			result.setId(theOldOne.getId());
			result.setVersion(theOldOne.getVersion());
			result.setActivated(theOldOne.getActivated());
			result.setSponsor(theOldOne.getSponsor());

		}

		this.validator.validate(result, binding);

		return result;
	}

}
