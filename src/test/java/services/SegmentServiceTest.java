
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Parade;
import domain.Segment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SegmentServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private SegmentService	segmentService;

	@Autowired
	private ParadeService	paradeService;


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
	 * a) Requirement: Brotherhood manage paths of their parades : Create
	 * 
	 * b) Negative cases:
	 * 1. The origin pattern is wrong
	 * 2. The origin is null
	 * 3. The destination pattern is wrong
	 * 4. The destination is null
	 * 
	 * c) Sentence coverage
	 * -save(): 1 passed cases / 5 total cases = 20%
	 * -create(): 1 passed cases / 1 total cases = 100%
	 * 
	 * d) Data coverage
	 * -Segment: 2 passed cases / 4 total cases = 50%
	 */

	@Test
	public void driverCreateSegment() {

		final Object testingData[][] = {

			{
				"12,-8", "32,4", "2019/12/23 11:30", "2019/12/23 12:30", "parade5", null
			}, //All fine
			{
				"ert,89", "32,4", "2019/12/23 11:30", "2019/12/23 12:30", "parade5", ConstraintViolationException.class
			}, //The origin pattern is wrong
			{
				null, "32,4", "2019/12/23 11:30", "2019/12/23 12:30", "parade5", ConstraintViolationException.class
			}, //Origin = null
			{
				"12,-8", "32,400", "2019/12/23 11:30", "2019/12/23 12:30", "parade5", ConstraintViolationException.class
			}, //The destination pattern is wrong
			{
				"12,-8", null, "2019/12/23 11:30", "2019/12/23 12:30", "parade5", ConstraintViolationException.class
			}, //Destination = null

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSegment((String) testingData[i][0], (String) testingData[i][1], this.convertStringToDate((String) testingData[i][2]), this.convertStringToDate((String) testingData[i][3]), (String) testingData[i][4],
				(Class<?>) testingData[i][5]);
	}

	protected void templateCreateSegment(final String origin, final String destination, final Date timeOrigin, final Date timeDestination, final String paradeBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			final Segment segment = this.segmentService.create();

			final Parade parade = this.paradeService.findOne(super.getEntityId(paradeBean));

			segment.setOrigin(origin);
			segment.setDestination(destination);
			segment.setTimeOrigin(timeOrigin);
			segment.setTimeDestination(timeDestination);
			segment.setContiguous(null);
			segment.setParade(parade);

			this.startTransaction();

			this.segmentService.save(segment);
			this.segmentService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * a) Requirement: Brotherhood manage paths of their parades : Edit
	 * 
	 * b) Negative cases:
	 * 1. The time origin before contiguous time origin
	 * 2. The time origin before organisation moment
	 * 3. The time origin pattern is wrong
	 * 4. Time origin = null
	 * 
	 * c) Sentence coverage
	 * -save(): 2 passed cases / 5 total cases = 40%
	 * -findOne: 1 passed cases / 1 total cases = 100%
	 * 
	 * d) Data coverage
	 * -Segment: 1 passed cases / 4 total cases = 25%
	 */

	@Test
	public void driverEditSegment() {

		final Object testingData[][] = {

			{
				"segment2", "-90.0, 180.0", "90.0, 180.0", "2019/08/08 17:01", "2019/08/08 17:30", "segment1", "parade1", null
			}, //All fine
			{
				"segment2", "-90.0, 180.0", "90.0, 180.0", "2019/08/08 15:00", "2019/08/08 17:30", "segment1", "parade1", IllegalArgumentException.class
			}, //The time origin before contiguous time origin
			{
				"segment2", "-90.0, 180.0", "90.0, 180.0", "2019/08/08 12:00", "2019/08/08 17:30", "segment1", "parade1", IllegalArgumentException.class
			}, //The time origin before organisation moment
			{
				"segment2", "-90.0, 180.0", "90.0, 180.0", "08/08/2019 17:01", "2019/08/08 17:30", "segment1", "parade1", IllegalArgumentException.class
			}, //The time origin pattern is wrong
			{
				"segment2", "-90.0, 180.0", "90.0, 180.0", null, "2019/08/08 17:30", "segment1", "parade1", NullPointerException.class
			}, //Time origin = null

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditSegment((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], this.convertStringToDate((String) testingData[i][3]), this.convertStringToDate((String) testingData[i][4]),
				(String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);
	}

	protected void templateEditSegment(final String segmentBean, final String origin, final String destination, final Date timeOrigin, final Date timeDestination, final String contiguousBean, final String paradeBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			final Segment segment = this.segmentService.findOne(super.getEntityId(segmentBean));

			final Segment contiguous = this.segmentService.findOne(super.getEntityId(contiguousBean));

			final Parade parade = this.paradeService.findOne(super.getEntityId(paradeBean));

			segment.setOrigin(origin);
			segment.setDestination(destination);
			segment.setTimeOrigin(timeOrigin);
			segment.setTimeDestination(timeDestination);
			segment.setContiguous(contiguous);
			segment.setParade(parade);

			this.startTransaction();

			this.segmentService.save(segment);
			this.segmentService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	protected Date convertStringToDate(final String dateString) {
		Date date = null;

		if (dateString != null) {
			final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			try {
				date = df.parse(dateString);
			} catch (final Exception ex) {
				System.out.println(ex);
			}
		}

		return date;
	}

	/*
	 * a) Requirement: Brotherhood manage paths of their parades : Edit
	 * 
	 * b) Negative cases:
	 * 1. Not last segment
	 * 
	 * c) Sentence coverage
	 * -delete(): 1 passed cases / 1 total cases = 100%
	 * -findOne: 1 passed cases / 1 total cases = 100%
	 * 
	 * d) Data coverage
	 * -Segment: 0 passed cases / 4 total cases = 0%
	 */

	@Test
	public void driverDeleteSegment() {

		final Object testingData[][] = {

			{
				"segment2", null
			}, //Last segment
			{
				"segment1", IllegalArgumentException.class
			}, //Not last segment

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteSegment((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateDeleteSegment(final String segmentBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			final Segment segment = this.segmentService.findOne(super.getEntityId(segmentBean));

			this.startTransaction();

			this.segmentService.delete(segment);
			this.segmentService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * -------Coverage SegmentService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * save() = 60%
	 * findOne() = 100%
	 * create() = 100%
	 * delete() = 100%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Segment = 75%
	 */
}
