
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private SponsorService	sponsorService;


	/*
	 * a) Requirement 15.1 : Register as a sponsor
	 * b) Negative cases:
	 * 2. El surname introducido es incorrecto (null)
	 * c)99%?
	 * d)9,1%
	 */
	@Test
	public void driverRegisterSponsor() {
		final Object testingData[][] = {
			{
				"name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter56", "chapter56", null
			},//1.Todo bien
			{
				"name1", "middleName1", null, "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter57", "chapter57", ConstraintViolationException.class
			},//2.Title null
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterSponsor((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void templateRegisterSponsor(final String name, final String middleName, final String surname, final String photo, final String email, final String phone, final String address, final String username, final String password,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Sponsor sponsor = this.sponsorService.create();

			sponsor.setName(name);
			sponsor.setMiddleName(middleName);
			sponsor.setSurname(surname);
			sponsor.setPhoto(photo);
			sponsor.setEmail(email);
			sponsor.setPhone(phone);
			sponsor.setAddress(address);

			sponsor.getUserAccount().setUsername(username);
			sponsor.getUserAccount().setPassword(password);

			this.startTransaction();

			this.sponsorService.save(sponsor);
			this.sponsorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * a) Requirement: Actor manage his/her profile
	 * b) Negative cases:
	 * 2. El usuario que está logeado, no es el mismo que el que está editando
	 * c)99%?
	 * d)27,27%
	 */
	@Test
	public void driverEditSponsor() {
		final Object testingData[][] = {

			{
				"sponsor1", "sponsor1", "calle 13", "a@a.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", null
			}, {
				"brotherhood1", "sponsor1", "calle 13", "a@a.com", "+34 333 3333", "middleName", "surname", "name", "http://www.photo.com", "title", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditSponsor((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);

	}

	protected void templateEditSponsor(final String username, final int sponsorId, final String address, final String email, final String phone, final String middleName, final String surname, final String name, final String photo, final String title,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Sponsor sponsor = this.sponsorService.findOne(sponsorId);

			sponsor.setAddress(address);
			sponsor.setEmail(email);
			sponsor.setMiddleName(middleName);
			sponsor.setName(surname);
			sponsor.setName(name);
			sponsor.setPhoto(photo);

			this.startTransaction();

			this.sponsorService.save(sponsor);
			this.sponsorService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();
	}
}
