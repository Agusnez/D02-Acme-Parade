
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.LegalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LegalRecordServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private LegalRecordService	legalRecordService;


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
	 * 1. Manage their history ... (create)
	 * Negative cases:
	 * b)2,3,4,5,6,7
	 * c) Sentence coverage
	 * -create(): 3 tested cases / 3 total cases = 100%
	 * 
	 * 
	 * d) Data coverage
	 * -LegalRecord: 2 tested cases / 5 total cases = 40%
	 */

	@Test
	public void driverCreateLegalRecord() {
		final Object testingData[][] = {
			{//1.All fine
				"brotherhood1", "title1", "descrption1", 21.0, "legalName1", "law1", "law2", null
			}, {//2.Title = null
				"brotherhood1", null, "descrption1", 21.0, "legalName1", "law1", "law2", ConstraintViolationException.class
			}, {//3.Description = null
				"brotherhood1", "title1", null, 21.0, "legalName1", "law1", "law2", ConstraintViolationException.class
			}, {//4.Description = ""
				"brotherhood1", "title1", "", 21.0, "legalName1", "law1", "law2", ConstraintViolationException.class
			}, {//5.Title = ""
				"brotherhood1", "", "descrption1", 21.0, "legalName1", "law1", "law2", ConstraintViolationException.class
			}, {//6.Not authority
				null, "title1", "descrption1", 21.0, "legalName1", "law1", "law2", IllegalArgumentException.class
			}, {//7.Not a Brotherhood
				"member1", "title1", "descrption1", 21.0, "legalName1", "law1", "law2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateLegalRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	protected void templateCreateLegalRecord(final String username, final String title, final String description, final Double VATNumber, final String legalName, final String law1, final String law2, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final LegalRecord legalRecord = this.legalRecordService.create();

			final Collection<String> laws = new HashSet<String>();
			laws.add(law1);
			laws.add(law2);

			legalRecord.setTitle(title);
			legalRecord.setDescription(description);
			legalRecord.setVATNumber(VATNumber);
			legalRecord.setLegalName(legalName);
			legalRecord.setLaws(laws);

			this.legalRecordService.save(legalRecord);
			this.legalRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		super.checkExceptions(expected, caught);

		this.rollbackTransaction();

	}

	/*
	 * a)(Level C)Requirement 3 :An actor who is authenticated as a brotherhood must be able to:
	 * 1. Manage their history ... (edit)
	 * Negative cases:
	 * b)2,3
	 * c) Sentence coverage
	 * -save(): 3 tested cases / 3 total cases = 100%
	 * 
	 * 
	 * d) Data coverage
	 * -None
	 */

	@Test
	public void driverEditLegalRecord() {
		final Object testingData[][] = {
			{//1.All fine
				"brotherhood1", "legalRecord1", "title1", "descrption1", 21.0, "legalName1", "law1", "law2", null
			}, {//2.Not authority
				null, "legalRecord1", "title1", "descrption1", 21.0, "legalName1", "law1", "law2", IllegalArgumentException.class
			}, {//3.Not a Brotherhood
				"member1", "legalRecord1", "title1", "descrption1", 21.0, "legalName1", "law1", "law2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditLegalRecord((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void templateEditLegalRecord(final String username, final int legalRecordId, final String title, final String description, final Double VATNumber, final String legalName, final String law1, final String law2, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final LegalRecord legalRecord = this.legalRecordService.findOne(legalRecordId);

			final Collection<String> laws = new HashSet<String>();
			laws.add(law1);
			laws.add(law2);

			legalRecord.setTitle(title);
			legalRecord.setDescription(description);
			legalRecord.setVATNumber(VATNumber);
			legalRecord.setLegalName(legalName);
			legalRecord.setLaws(laws);

			this.legalRecordService.save(legalRecord);
			this.legalRecordService.flush();

			this.legalRecordService.save(legalRecord);
			this.legalRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		super.checkExceptions(expected, caught);

		this.rollbackTransaction();

	}

	/*
	 * a)(Level C)Requirement 3 :An actor who is authenticated as a brotherhood must be able to:
	 * 1. Manage their history ... (list)
	 * Negative cases:
	 * b)2
	 * c) Sentence coverage
	 * -findAll(): 1 tested case / 1 total case = 100%
	 * 
	 * 
	 * d) Data coverage
	 * -None
	 */

	@Test
	public void driverListLegalRecord() {
		final Object testingData[][] = {

			{
				1, null
			//1. All fine
			}, {
				1651, IllegalArgumentException.class
			//2. Incorrect result
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListLegalRecord((Integer) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateListLegalRecord(final Integer expectedInt, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Integer result = this.legalRecordService.findAll().size();
			Assert.isTrue(expectedInt == result);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * a)(Level C)Requirement 3 :An actor who is authenticated as a brotherhood must be able to:
	 * 1. Manage their history ... (delete)
	 * Negative cases:
	 * b)2,3
	 * c) Sentence coverage
	 * -delete(): 3 tested cases / 5 total cases = 60%
	 * 
	 * 
	 * d) Data coverage
	 * -None
	 */

	@Test
	public void driverDeleteLegalRecord() {
		final Object testingData[][] = {

			{
				"brotherhood1", "legalRecord1", null
			//1. All fine
			}, {
				null, "legalRecord1", IllegalArgumentException.class
			//2. Not Authority
			}, {
				"member2", "legalRecord1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteLegalRecord((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);

	}

	protected void templateDeleteLegalRecord(final String username, final int legalRecordId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final LegalRecord legalRecord = this.legalRecordService.findOne(legalRecordId);

			this.legalRecordService.delete(legalRecord);
			this.legalRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		super.checkExceptions(expected, caught);

		this.rollbackTransaction();

	}

}
