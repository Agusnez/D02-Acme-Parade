
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

	//	@Autowired
	//	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ConfigurationService	configurationService;


	public SponsorshipForm create(final int paradeId) {

		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		Assert.isTrue(sponsor.getUserAccount().getAuthorities().contains(authority));

		final SponsorshipForm sponsorshipForm = new SponsorshipForm();

		sponsorshipForm.setParadeId(paradeId);

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

	public Double ratioOfActiveSponsorships() {

		final Double result = this.sponsorshipRepository.ratioOfActiveSponsorships();

		return result;
	}

	public Double averageActiveSponsorshipsPerSponsor() {

		final Double result = this.sponsorshipRepository.averageActiveSponsorshipsPerSponsor();

		return result;
	}

	public Integer minActiveSponsorshipsPerSponsor() {
		Integer min = 0;

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		for (final Sponsor s : sponsors) {
			final Integer res = this.activeSponsorshipsPerSponsorId(s.getId()).size();
			if (min == 0 || res < min)
				min = res;
		}

		return min;
	}

	public Integer maxActiveSponsorshipsPerSponsor() {
		Integer max = 0;

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		for (final Sponsor s : sponsors) {
			final Integer res = this.activeSponsorshipsPerSponsorId(s.getId()).size();
			if (res > max)
				max = res;
		}

		return max;
	}

	public Double standartDeviationOfActiveSponsorshipsPerSponsor() {

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		Double sum = 0.0;
		Double div = 0.0;
		final Double med = this.sponsorshipRepository.averageActiveSponsorshipsPerSponsor();
		Double med2 = 0.0;
		Double res = 0.0;
		Double result = 0.0;
		if (!sponsors.isEmpty()) {
			for (final Sponsor s : sponsors)
				sum = sum + (this.activeSponsorshipsPerSponsorId(s.getId()).size()) * (this.activeSponsorshipsPerSponsorId(s.getId()).size());
			div = sum / sponsors.size();

			med2 = med * med;

			res = div - med2;

			result = Math.sqrt(res);
		}

		return result;

	}

	public Collection<Sponsorship> activeSponsorshipsPerSponsorId(final int sponsorId) {

		Assert.notNull(sponsorId);

		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.activeSponsorshipsPerSponsorId(sponsorId);

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
		result.setCreditCard(sponsorship.getCreditCard());
		result.setTargetUrl(sponsorship.getTargetUrl());

		if (sponsorship.getId() == 0) {

			result.setActivated(true);
			result.setSponsor(this.sponsorService.findByPrincipal());
			result.setParade(this.paradeService.findOne(sponsorship.getParadeId()));

		} else {

			final Sponsorship theOldOne = this.findOne(sponsorship.getId());

			result.setId(theOldOne.getId());
			result.setVersion(theOldOne.getVersion());
			result.setActivated(theOldOne.getActivated());
			result.setSponsor(theOldOne.getSponsor());
			result.setParade(theOldOne.getParade());
			result.setRecollect(theOldOne.getRecollect());

		}

		this.validator.validate(result, binding);

		return result;
	}

	public SponsorshipForm editForm(final Sponsorship sponsorship) {

		final SponsorshipForm result = new SponsorshipForm();

		result.setBanner(sponsorship.getBanner());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setId(sponsorship.getId());
		result.setParadeId(sponsorship.getParade().getId());
		result.setTargetUrl(sponsorship.getTargetUrl());
		result.setVersion(sponsorship.getVersion());

		return result;
	}

	public Sponsorship ramdomSponsorship(final int paradeId) {

		Sponsorship result = null;
		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAllByParadeId(paradeId);

		final Double vatTax = this.configurationService.findConfiguration().getVatTax();
		final Double fare = this.configurationService.findConfiguration().getFare();
		if (!sponsorships.isEmpty()) {

			final int M = 0;
			final int N = sponsorships.size();
			final int limit = (int) (Math.random() * (N - M + 1) + M);

			int i = 0;

			for (final Sponsorship s : sponsorships) {

				if (i == limit) {
					result = s;

					Double recollect = result.getRecollect();

					if (fare != null)
						if (vatTax == null)
							recollect = recollect + fare;
						else
							recollect = recollect + ((1 - vatTax) * fare);

					result.setRecollect(recollect);
					break;
				}

				i++;

			}

		}

		return result;
	}
}
