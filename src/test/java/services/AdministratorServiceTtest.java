
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTtest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


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

	@Test
	public void SpammerTest() {
		final Object testingData[][] = {
			{
				"admin", null
			},//1. All fine
			{
				"member1", IllegalArgumentException.class
			},//2. Invalid authority
		};

		for (int i = 0; i < testingData.length; i++)
			this.AuthoritySpammerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void AuthoritySpammerTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			this.administratorService.spammer();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void ScoreTest() {
		final Object testingData[][] = {
			{
				"admin", null
			},//1. All fine
			{
				"member1", IllegalArgumentException.class
			},//2. Invalid authority
		};

		for (int i = 0; i < testingData.length; i++)
			this.AuthorityScoreTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void AuthorityScoreTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			this.administratorService.calculateScore();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
