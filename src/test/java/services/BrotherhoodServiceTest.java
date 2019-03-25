
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Brotherhood;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BrotherhoodServiceTest extends AbstractTest {

	//The SUT--------------------------------------------------
	@Autowired
	private BrotherhoodService	brotherhoodService;


	/*
	 * ----CALCULATE SENTENCE COVERAGE----
	 * To calculate the sentence coverage, we have to look at each "service's method"
	 * we are testing and we have to analyse its composition (if, for, ...) and Asserts.
	 * Then, we calculate the number of total cases which our code can execute. The equation will be:
	 * 
	 * (n� passed cases / n� total cases)*100 = coverage(%)
	 * 
	 * In the end of the class, we conclude with the total coverage of the service's methods
	 * which means the service's coverage.
	 * 
	 * 
	 * ----CALCULATE DATA COVERAGE----
	 * To calculate the data coverage, we have look at
	 * each object's attributes, we analyse in each one of them
	 * the domain's restrictions and the business rules
	 * about the attribute. If we have tested all types of cases
	 * in a attribute, that is called "proven attribute".
	 * 
	 * (n� proven attributes/ n� total attributes)*100 = coverage(%)
	 * 
	 * ----Note:
	 * It's clear that if we have tested all cases about a method in a test
	 * and now It have already had a 100% of coverage, we don't have to
	 * mention its coverage in other test.
	 */

	/*
	 * a)(Level A)Requirement 14 :An actor who is not authenticated must be able to:
	 * 1. Navigate to the brotherhood that have settle in an area
	 * Negative cases:
	 * b)2. Area no contiene ese brotherhood
	 * c) Sentence coverage
	 * -findBrotherhoodsByAreaId(areaId)=100%
	 * 
	 * d) Data coverage
	 * 0%
	 */

	@Test
	public void driverListBrotherhoodsByArea() {
		final Object testingData[][] = {

			{
				"area1", "brotherhood1", null
			//1. Todo bien
			}, {
				"area1", "brotherhood3", IllegalArgumentException.class
			//2. Area no contiene ese brotherhood
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListBrotherhoodsByArea((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void templateListBrotherhoodsByArea(final String areaId, final String brotherhoodId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			final Integer areaIdInteger = super.getEntityId(areaId);
			final Integer brotherhoodIdInteger = super.getEntityId(brotherhoodId);
			final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodIdInteger);

			final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findBrotherhoodsByAreaId(areaIdInteger);

			Assert.isTrue(brotherhoods.contains(brotherhood));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * ACME-MADRUG�
	 * a)(Level C)Requirement 8 :An actor who is not authenticated must be able to:
	 * 2. List the brotherhoods in the system.
	 * 
	 * Requirement 9 :An actor who is authenticated must be able to:
	 * 1. Do the same as an actor who is not authenticated, but register to the system.
	 * 
	 * b)Negative cases: 2
	 * c) Sentence coverage:
	 * -findAll()= 1 passed cases / 2 total cases = 50%
	 * 
	 * d) Data coverage:
	 * 0%
	 */

	@Test
	public void driverListBrotherhoods() {
		final Object testingData[][] = {

			{
				7, null, null
			//1. Todo bien
			}, {
				28, null, IllegalArgumentException.class
			//2. Esperamos un resultado incorrecto
			}, {
				7, "brotherhood1", null
			//1. Todo bien con brotherhood autenticado
			}, {
				7, "sponsor1", null
			//1. Todo bien con sponsor autenticado
			}, {
				7, "chapter1", null
			//1. Todo bien con chapter autenticado
			}, {
				7, "member1", null
			//1. Todo bien con member autenticado
			}, {
				7, "admin", null
			//1. Todo bien con admin autenticado
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListBrotherhoods((Integer) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void templateListBrotherhoods(final Integer expectedInt, final String user, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			if (user != null)
				super.authenticate(user);

			final Brotherhood brotherhood = this.brotherhoodService.create();

			brotherhood.setTitle("title1");
			final Collection<String> pictures = new ArrayList<>();
			pictures.add("https://docs.google.com/document/d/1mAOEp0duzbBYUXV0/edit");
			brotherhood.setPictures(pictures);
			final DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			final Date date = format.parse("1997/03/05");
			brotherhood.setEstablishment(date);
			brotherhood.setName("name1");
			brotherhood.setMiddleName("middleName1");
			brotherhood.setSurname("surname1");
			brotherhood.setPhoto("https://google.com");
			brotherhood.setEmail("email1@gmail.com");
			brotherhood.setPhone("672195205");
			brotherhood.setAddress("address1");

			brotherhood.getUserAccount().setUsername("brotherhood56");
			brotherhood.getUserAccount().setPassword("brotherhood56");

			this.startTransaction();

			this.brotherhoodService.save(brotherhood);
			this.brotherhoodService.flush();

			final Integer result = this.brotherhoodService.findAll().size();
			super.unauthenticate();
			Assert.isTrue(expectedInt + 1 == result);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

	/*
	 * ACME-MADRUG�
	 * a)(Level C)Requirement 8 :An actor who is not authenticated must be able to:
	 * 1. Register to the system as a Brotherhood.
	 * b)Negative cases: 2, 3
	 * c) Sentence coverage:
	 * -save(): 1 probado / 6 totales = 16,67%
	 * -create(): 1 probado/1 totales = 100%
	 * 
	 * d) Data coverage:
	 * -Brotherhood: 1 probado / 10 totales = 10%
	 */
	@Test
	public void driverRegisterBrotherhood() {
		final Object testingData[][] = {
			{
				"title1", "https://docs.google.com/document/d/1mAOEp0duzbBYUXV0/edit", "1997/03/05", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter56", "chapter56", null
			},//1.Todo bien
			{
				"", "https://docs.google.com/document/d/1mAOEp0duzbBYUXV0/edit", "1997/03/05", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter56", "chapter56",
				ConstraintViolationException.class
			},//2.Title = blank
			{
				"<script>alert('hola')</script>", "https://docs.google.com/document/d/1mAOEp0duzbBYUXV0/edit", "1997/03/05", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter56", "chapter56",
				ConstraintViolationException.class
			},//3.Title = html

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterBrotherhood((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (Class<?>) testingData[i][12]);
	}
	protected void templateRegisterBrotherhood(final String title, final String picture, final String date, final String name, final String middleName, final String surname, final String photo, final String email, final String phone, final String address,
		final String username, final String password, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Brotherhood brotherhood = this.brotherhoodService.create();

			brotherhood.setTitle(title);
			final Collection<String> pictures = new ArrayList<>();
			pictures.add(picture);
			brotherhood.setPictures(pictures);
			final Date datee = new Date(date);
			brotherhood.setEstablishment(datee);
			brotherhood.setName(name);
			brotherhood.setMiddleName(middleName);
			brotherhood.setSurname(surname);
			brotherhood.setPhoto(photo);
			brotherhood.setEmail(email);
			brotherhood.setPhone(phone);
			brotherhood.setAddress(address);

			brotherhood.getUserAccount().setUsername(username);
			brotherhood.getUserAccount().setPassword(password);

			this.startTransaction();

			this.brotherhoodService.save(brotherhood);
			this.brotherhoodService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

	/*
	 * ACME-MADRUG�
	 * a)(Level C)Requirement 9: Actor who is authenticated
	 * 2.Edit personal data
	 * b) Negative cases: 2
	 * 
	 * c) Sentence coverage
	 * -save(): 2 passed cases / 8 total cases = 25%
	 * -findOne(): 1 passed cases / 2 total cases = 50%
	 * 
	 * d) Data coverage
	 * 0%
	 */
	@Test
	public void driverEditBrotherhood() {
		final Object testingData[][] = {

			{
				"brotherhood1", "brotherhood1", "title1", null
			}, //1.All fine
			{
				"chapter1", "brotherhood1", "title1", IllegalArgumentException.class
			}, //2. The user who is logged, It's not the same as the user who is being edited
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditBrotherhood((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void templateEditBrotherhood(final String username, final int brotherhoodId, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);

			brotherhood.setTitle(title);

			this.startTransaction();

			this.brotherhoodService.save(brotherhood);
			this.brotherhoodService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();
	}

}
