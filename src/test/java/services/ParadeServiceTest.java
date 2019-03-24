
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Parade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ParadeServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------

	@Autowired
	private ParadeService	paradeService;


	/*
	 * ----CALCULATE SENTENCE COVERAGE----
	 * To calculate the sentence coverage, we have to look at each "service's method"
	 * we are testing and we have to analyse its composition (if, for, Assert...) and Asserts.
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
	 * a)(Level B)Requirement 2.2:Manage the parades that are published by the brotherhoods in the area that they co-ordinate.
	 * This includes listing them grouped by status and making decisions on the parades that have status
	 * submitted. When a parade is rejected by a chapter, the chapter must jot down the reason why.
	 * Negative cases: 2
	 * c) Sentence coverage:
	 * -save(): 4 passed cases /13 total cases = 30,77%
	 * d) Data coverage: 0%
	 */
	@Test
	public void driverDecideParade() {
		final Object testingData[][] = {

			{
				"chapter1", "parade1", "ACCEPTED", null
			}, {
				"chapter2", "parade1", "REJECTED", IllegalArgumentException.class
			//2. Un cabildo que no coordina una procession, decide sobre ella.
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDecideParade((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	protected void templateDecideParade(final String username, final int paradeId, final String decision, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Parade parade = this.paradeService.findOne(paradeId);

			parade.setStatus(decision);

			this.startTransaction();
			this.paradeService.save(parade);
			this.paradeService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();
	}

	/*
	 * a)(Level A)Requirement 14 :An actor who is not authenticated must be able to:
	 * 1. Navigate to the parades that a brotherhood organise
	 * Negative cases:
	 * b)2. El parade no pertenece a la brotherhood
	 * 3. El parade es del brotherhood pero no está en estado ACCEPTED
	 * c) Sentence coverage
	 * findParadeCanBeSeenOfBrotherhoodId()=100%
	 * 
	 * d) Data coverage
	 * 0%
	 */

	@Test
	public void driverListParadeByBrotherhood() {
		final Object testingData[][] = {

			{
				"parade5", "brotherhood1", null
			//1. Todo bien

			}, {
				"parade2", "brotherhood1", IllegalArgumentException.class
			//2. El parade no pertenece a la brotherhood
			}, {
				"parade1", "brotherhood1", IllegalArgumentException.class
			//3. El parade es del brotherhood pero no está en estado ACCEPTED
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListParadeByBrotherhood((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateListParadeByBrotherhood(final String paradeId, final String brotherhoodId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			final Integer paradeIdInteger = super.getEntityId(paradeId);
			final Integer brotherhoodIdInteger = super.getEntityId(brotherhoodId);

			final Parade parade = this.paradeService.findOne(paradeIdInteger);

			final Collection<Parade> parades = this.paradeService.findParadeCanBeSeenOfBrotherhoodId(brotherhoodIdInteger);

			Assert.isTrue(parades.contains(parade));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * a)(Level B)Requirement 2 :An actor who is not authenticated must be able to:
	 * 2. Listing parades that are published by the brotherhoods in the area that they co-ordinate
	 * Negative cases:
	 * b)2. El parade no pertenece a la brotherhood
	 * c) Sentence coverage
	 * findParadeCanBeSeenOfBrotherhoodIdForChapter()=100%
	 * 
	 * d) Data coverage
	 * 0%
	 */

	@Test
	public void driverListParadeByBrotherhoodForChapter() {
		final Object testingData[][] = {

			{
				"parade5", "brotherhood1", null
			//1. Todo bien

			}, {
				"parade2", "brotherhood1", IllegalArgumentException.class
			//2. El parade no pertenece a la brotherhood
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListParadeByBrotherhoodForChapter((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateListParadeByBrotherhoodForChapter(final String paradeId, final String brotherhoodId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			final Integer paradeIdInteger = super.getEntityId(paradeId);
			final Integer brotherhoodIdInteger = super.getEntityId(brotherhoodId);

			final Parade parade = this.paradeService.findOne(paradeIdInteger);

			final Collection<Parade> parades = this.paradeService.findParadeCanBeSeenOfBrotherhoodId(brotherhoodIdInteger);

			Assert.isTrue(parades.contains(parade));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * -------Coverage ChapterService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * save()=30,77%
	 * findParadeCanBeSeenOfBrotherhoodIdForChapter()=100%
	 * findParadeCanBeSeenOfBrotherhoodId()=100%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Parade=0%
	 */

}
