
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


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
	 * a)(Level C)Requirement 3 :An actor who is authenticated as a brotherhood must be able to:
	 * 1. Manage their history, which includes listing, displaying, creating, updating, and deleting its records.
	 * Negative cases:
	 * b)2, 3
	 * c) Sentence coverage
	 * -save(): 1 probado / 1 totales = 100%
	 * 
	 * 
	 * d) Data coverage
	 * -MiscellaneousRecord: 2 probado / 2 totales = 100%
	 */

	@Test
	public void driverCreateMiscellaneousRecord() {
		final Object testingData[][] = {
			{
				"title1", "descrption1", null
			},//1.Todo bien
			{
				null, "descrption1", ConstraintViolationException.class
			},//2.Title = null
			{
				"title1", null, ConstraintViolationException.class
			},//3.Description = null

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateMiscellaneousRecord((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateCreateMiscellaneousRecord(final String title, final String description, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();

			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setDescription(description);

			this.startTransaction();

			this.miscellaneousRecordService.save(miscellaneousRecord);
			this.miscellaneousRecordService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
