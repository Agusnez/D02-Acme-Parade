
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

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
	 * (nº passed cases / nº total cases)*100 = coverage(%)
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
	 * (nº proven attributes/ nº total attributes)*100 = coverage(%)
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
	 * ACME-MADRUGÁ
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
			final Date date = new Date("1997/03/05");
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

}
