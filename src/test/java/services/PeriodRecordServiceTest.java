
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import domain.PeriodRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PeriodRecordServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private PeriodRecordService	periodRecordService;


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
	 * a)(Level C)Requirement 3 :An actor who is authenticated as a brotherhood must be able to:
	 * 1. Manage their history ... (create)
	 * Negative cases:
	 * b)2,3,4,5,6,7
	 * c) Sentence coverage
	 * -create(): 3 tested cases / 3 total cases = 100%
	 * 
	 * 
	 * d) Data coverage
	 * -PeriodRecord: 2 tested cases / 5 total cases = 40%
	 */

	@Test
	public void driverCreatePeriodRecord() {
		final Object testingData[][] = {
			{//1.All fine
				"brotherhood1", "title1", "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", null
			}, {//2.Title = null
				"brotherhood1", null, "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", ConstraintViolationException.class
			}, {//3.Description = null
				"brotherhood1", "title1", null, "1812/10/12", "1817/11/15", "photo1", "photo2", ConstraintViolationException.class
			}, {//4.Description = ""
				"brotherhood1", "title1", "", "1812/10/12", "1817/11/15", "photo1", "photo2", ConstraintViolationException.class
			}, {//5.Title = ""
				"brotherhood1", "", "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", ConstraintViolationException.class
			}, {//6.Not authority
				null, "title1", "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", IllegalArgumentException.class
			}, {//7.Not a Brotherhood
				"member1", "title1", "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePeriodRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], this.convertStringToDate((String) testingData[i][3]), this.convertStringToDate((String) testingData[i][4]),
				(String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	protected void templateCreatePeriodRecord(final String username, final String title, final String description, final Date startYear, final Date endYear, final String photo1, final String photo2, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final PeriodRecord periodRecord = this.periodRecordService.create();

			final Collection<String> photos = new HashSet<String>();
			photos.add(photo1);
			photos.add(photo2);

			periodRecord.setTitle(title);
			periodRecord.setDescription(description);
			periodRecord.setStartYear(startYear);
			periodRecord.setEndYear(endYear);
			periodRecord.setPhotos(photos);

			this.periodRecordService.save(periodRecord);
			this.periodRecordService.flush();

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
	 * -save(): 3 tested cases / 9 total cases = 33.34%
	 * 
	 * 
	 * d) Data coverage
	 * -None
	 */

	@Test
	public void driverEditPeriodRecord() {
		final Object testingData[][] = {
			{//1.All fine
				"brotherhood1", "periodRecord1", "title1", "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", null
			}, {//2.Not authority
				null, "periodRecord1", "title1", "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", IllegalArgumentException.class
			}, {//3.Not a Brotherhood
				"member1", "periodRecord1", "title1", "descrption1", "1812/10/12", "1817/11/15", "photo1", "photo2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPeriodRecord((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], this.convertStringToDate((String) testingData[i][4]),
				this.convertStringToDate((String) testingData[i][5]), (String) testingData[i][6], (String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void templateEditPeriodRecord(final String username, final int periodRecordId, final String title, final String description, final Date startYear, final Date endYear, final String photo1, final String photo2, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);

			final Collection<String> photos = new HashSet<String>();
			photos.add(photo1);
			photos.add(photo2);

			periodRecord.setTitle(title);
			periodRecord.setDescription(description);
			periodRecord.setStartYear(startYear);
			periodRecord.setEndYear(endYear);
			periodRecord.setPhotos(photos);

			this.periodRecordService.save(periodRecord);
			this.periodRecordService.flush();

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
	public void driverListPeriodRecord() {
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
			this.templateListPeriodRecord((Integer) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateListPeriodRecord(final Integer expectedInt, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Integer result = this.periodRecordService.findAll().size();
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
	public void driverDeletePeriodRecord() {
		final Object testingData[][] = {

			{
				"brotherhood1", "periodRecord1", null
			//1. All fine
			}, {
				null, "periodRecord1", IllegalArgumentException.class
			//2. Not Authority
			}, {
				"member2", "periodRecord1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeletePeriodRecord((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);

	}

	protected void templateDeletePeriodRecord(final String username, final int periodRecordId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.startTransaction();

			super.authenticate(username);

			final PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);

			this.periodRecordService.delete(periodRecord);
			this.periodRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.unauthenticate();

		super.checkExceptions(expected, caught);

		this.rollbackTransaction();

	}

	protected Date convertStringToDate(final String dateString) {
		Date date = null;

		if (dateString != null) {
			final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			try {
				date = df.parse(dateString);
			} catch (final Exception ex) {
				System.out.println(ex);
			}
		}

		return date;
	}

}