
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import repositories.ParadeRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.Finder;
import domain.Float;
import domain.Parade;
import domain.Parade;
import domain.Request;

@Service
@Transactional
public class ParadeService {

	// Managed repository

	@Autowired
	private ParadeRepository	paradeRepository;

	// Suporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private Validator				validator;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private RequestService			requestService;


	// Simple CRUD methods

	public Parade create() {

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Assert.notNull(brotherhood);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(brotherhood.getUserAccount().getAuthorities().contains(authority));

		final Parade result = new Parade();

		final Collection<Float> floatt = new HashSet<>();
		result.setFloats(floatt);
		result.setBrotherhood(brotherhood);

		result.setFinalMode(false);

		return result;

	}
	private String generateTicker(final Date moment) {

		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final String dateString = dateFormat.format(moment);

		final String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final StringBuilder salt = new StringBuilder();
		final Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * alphaNumeric.length());
			salt.append(alphaNumeric.charAt(index));
		}
		final String randomAlphaNumeric = salt.toString();

		final String ticker = dateString + "-" + randomAlphaNumeric;

		final int paradeSameTicker = this.paradeRepository.countParadeWithTicker(ticker);

		//nos aseguramos que que sea �nico
		while (paradeSameTicker > 0)
			this.generateTicker(moment);

		return ticker;

	}

	public Collection<Parade> findAll() {

		final Collection<Parade> parades = this.paradeRepository.findAll();

		Assert.notNull(parades);

		return parades;
	}

	public Parade findOne(final int paradeId) {

		final Parade parade = this.paradeRepository.findOne(paradeId);

		return parade;

	}

	public Parade save(final Parade parade) {
		//hasta que no tenga el brotherhood area no pueden organizarse parades
		Assert.notNull(parade.getBrotherhood().getArea());

		Assert.notNull(parade);

		Parade result = parade;
		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Assert.notNull(brotherhood);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(brotherhood.getUserAccount().getAuthorities().contains(authority));

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		Assert.isTrue(parade.getOrganisationMoment().after(currentMoment));

		result = this.paradeRepository.save(parade);

		return result;

	}

	//Esto es una soluci�n debido a que si editamos una brotherhood, hay que editar
	//las parades. Si hay parades que ya pasaron dar�a fallo en el de arriba
	//por el Assert de fechas. Aqui no lo tenemos. Solo es empleado el metodo cuando se edita Brotherhood
	public Parade saveByEditBrotherhood(final Parade parade) {
		//hasta que no tenga el brotherhood area no pueden organizarse parades
		Assert.notNull(parade.getBrotherhood().getArea());

		Assert.notNull(parade);

		Parade result = parade;
		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Assert.notNull(brotherhood);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(brotherhood.getUserAccount().getAuthorities().contains(authority));

		result = this.paradeRepository.save(parade);

		return result;

	}

	public void delete(final Parade parade) {

		Assert.notNull(parade);
		Assert.isTrue(parade.getId() != 0);

		final Actor brotherhood = this.actorService.findByPrincipal();
		Assert.notNull(brotherhood);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(brotherhood.getUserAccount().getAuthorities().contains(authority));
		final Collection<Parade> parades = this.paradeRepository.findParadeByBrotherhoodId(brotherhood.getId());
		Assert.isTrue(parades.contains(parade));

		//Finder
		Collection<Finder> findersByParade = this.finderService.findFindersByParadeId(parade.getId());
		if (!findersByParade.isEmpty())
			for (final Finder f : findersByParade) {

				f.getParades().remove(parade);
				this.finderService.save(f);
			}
		findersByParade = this.finderService.findFindersByParadeId(parade.getId());
		Assert.isTrue(findersByParade.isEmpty());

		this.paradeRepository.delete(parade);
	}

	public void deleteAll(final int actorId) {

		final Collection<Parade> parades = this.paradeRepository.findParadeByBrotherhoodId(actorId);

		if (!parades.isEmpty())
			for (final Parade p : parades) {

				final Collection<Finder> findersByParade = this.finderService.findFindersByParadeId(p.getId());
				if (!findersByParade.isEmpty())
					for (final Finder f : findersByParade) {

						f.getParades().remove(p);
						this.finderService.save(f);
					}

				final Collection<Request> requests = this.requestService.requestPerParadeId(p.getId());

				if (!requests.isEmpty())
					for (final Request r : requests)
						this.requestService.delete(r);

				this.paradeRepository.delete(p);

			}

	}

	//Other business methods-----------------------------------

	public Collection<Parade> findParadeByBrotherhoodId(final int brotherhoodId) {

		final Collection<Parade> parades = this.paradeRepository.findParadeByBrotherhoodId(brotherhoodId);

		return parades;
	}

	public Collection<Parade> findMemberParades(final int memberId) {

		final Collection<Parade> parades = this.paradeRepository.findMemberParades(memberId);

		return parades;
	}

	public Collection<Parade> findParadesByFloatId(final int floatId) {

		final Collection<Parade> parades = this.paradeRepository.findParadesByFloatId(floatId);

		return parades;
	}

	public Collection<Parade> findParadeCanBeSeen() {

		return this.paradeRepository.findParadeCanBeSeen();
	}

	public Collection<Parade> findParadeCannotBeSeenOfBrotherhoodId(final int brotherhoodId) {

		return this.paradeRepository.findParadeCannotBeSeenOfBrotherhoodId(brotherhoodId);
	}

	public Collection<Parade> findParadeCanBeSeenOfBrotherhoodId(final int brotherhoodId) {

		final Collection<Parade> result = this.paradeRepository.findParadeCanBeSeenOfBrotherhoodId(brotherhoodId);

		return result;
	}

	public Collection<String> findParadesLessThirtyDays() {

		final Collection<String> result = new HashSet<>();

		final Collection<Parade> parades = this.paradeRepository.findAll();

		if (!parades.isEmpty())
			for (final Parade p : parades) {
				final Date moment = p.getOrganisationMoment();

				final Date now = new Date(System.currentTimeMillis() - 1000);

				final Interval interval = new Interval(now.getTime(), moment.getTime());
				if (interval.toDuration().getStandardDays() <= 30)
					result.add(p.getTitle());
			}

		return result;
	}

	public Parade reconstruct(final Parade parade, final BindingResult binding) {

		Parade result = parade;
		final Parade paradeNew = this.create();

		if (parade.getId() == 0 || parade == null) {

			parade.setBrotherhood(paradeNew.getBrotherhood());
			parade.setFloats(paradeNew.getFloats());

			if (parade.getOrganisationMoment() != null) {

				final String ticker = this.generateTicker(parade.getOrganisationMoment());
				parade.setTicker(ticker);

			} else
				parade.setTicker(null);

			this.validator.validate(parade, binding);

			result = parade;
		} else {

			final Parade paradeBBDD = this.findOne(parade.getId());

			parade.setBrotherhood(paradeBBDD.getBrotherhood());
			parade.setFloats(paradeBBDD.getFloats());

			if (parade.getOrganisationMoment() != null) {

				final String ticker = this.generateTicker(parade.getOrganisationMoment());
				parade.setTicker(ticker);

			} else
				parade.setTicker(null);

			this.validator.validate(parade, binding);

		}

		return result;

	}

	public Boolean paradeBrotherhoodSecurity(final int paradeId) {
		Boolean res = false;
		final Parade parade = this.findOne(paradeId);

		final Brotherhood owner = parade.getBrotherhood();

		final Brotherhood login = this.brotherhoodService.findByPrincipal();

		if (login.equals(owner))
			res = true;

		return res;
	}

}