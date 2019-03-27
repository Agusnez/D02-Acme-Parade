
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
import domain.Enrolment;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EnrolmentServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private MessageService		messageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private PositionService		positionService;


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
	 * ACME-MADRUGA
	 * a)(Level C)Requirement 10.3 : Brotherhood manage his/her enrolments (list news)
	 * 
	 * b)Negative cases: Wrong result
	 * 
	 * c) Sentence coverage:
	 * -findEnrolmentsByBrotherhoodIdNoPosition()= 1 passed cases / 2 total cases = 50%
	 * 
	 * 
	 * d) Data coverage:
	 * 0%
	 */
	@Test
	public void newEnrolmentsList() {
		final Object testingData[][] = {
			{
				"brotherhood1", 0, null
			},//1.All fine
			{
				"brotherhood1", 14, IllegalArgumentException.class
			},//1.Wrong result

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateNewEnrolmentsList((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateNewEnrolmentsList(final String brotherhood, final Integer size, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			super.authenticate(brotherhood);

			final Collection<Enrolment> enrolments = this.enrolmentService.findEnrolmentsByBrotherhoodIdNoPosition(super.getEntityId(brotherhood));

			Assert.isTrue(enrolments.size() == size);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

		super.unauthenticate();

		this.rollbackTransaction();

	}

	/*
	 * ACME-MADRUGA
	 * a)(Level C)Requirement 10.3 : Brotherhood manage his/her enrolments (list news)
	 * 
	 * b)Negative cases: Wrong result
	 * 
	 * c) Sentence coverage:
	 * -findEnrolmentsByBrotherhoodId()= 1 passed cases / 2 total cases = 50%
	 * 
	 * 
	 * d) Data coverage:
	 * 0%
	 */
	@Test
	public void EnrolmentsList() {
		final Object testingData[][] = {
			{
				"brotherhood1", 2, null
			},//1.All fine
			{
				"brotherhood1", 14, IllegalArgumentException.class
			},//1.Wrong result

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEnrolmentsList((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateEnrolmentsList(final String brotherhood, final Integer size, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			super.authenticate(brotherhood);

			final Collection<Enrolment> enrolments = this.enrolmentService.findEnrolmentsByBrotherhoodId(super.getEntityId(brotherhood));

			Assert.isTrue(enrolments.size() == size);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

		super.unauthenticate();

		this.rollbackTransaction();

	}

	/*
	 * ACME-MADRUGA
	 * a)(Level C)Requirement 10.3 : Brotherhood manage his/her enrolments (edit)
	 * 
	 * b)Negative cases: Wrong result
	 * 
	 * c) Sentence coverage:
	 * -save() = 2 passed cases / 8 total cases= 25%
	 * 
	 * 
	 * d) Data coverage:
	 * 0%
	 */
	@Test
	public void EnrolmentEdit() {
		final Object testingData[][] = {
			{
				"brotherhood1", "enrolment2", null
			},//1.All fine
			{
				"brotherhood1", "enrolment14", IllegalArgumentException.class
			},//1.Wrong brotherhood

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEnrolmentEdit((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateEnrolmentEdit(final String brotherhood, final String enrolment, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			super.authenticate(brotherhood);

			final Enrolment enrolmentFind = this.enrolmentService.findOne(super.getEntityId(enrolment));

			final Position position = this.positionService.findOne(super.getEntityId("position1"));

			enrolmentFind.setPosition(position);

			this.enrolmentService.save(enrolmentFind);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

		super.unauthenticate();

		this.rollbackTransaction();

	}

	/*
	 * ACME-MADRUGA
	 * a)(Level C)Requirement 10.3 : Brotherhood manage his/her enrolments (dropOut)
	 * 
	 * b)Negative cases: Wrong result
	 * 
	 * c) Sentence coverage:
	 * -save() = 2 passed cases / 8 total cases= 25%
	 * 
	 * 
	 * d) Data coverage:
	 * 0%
	 */
	@Test
	public void EnrolmentDropOut() {
		final Object testingData[][] = {
			{
				"brotherhood1", "enrolment2", null
			},//1.All fine
			{
				"brotherhood1", "enrolment14", IllegalArgumentException.class
			},//1.Wrong brotherhood

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEnrolmentDropOut((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateEnrolmentDropOut(final String brotherhood, final String enrolment, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();

			super.authenticate(brotherhood);

			final Enrolment enrolmentFind = this.enrolmentService.findOne(super.getEntityId(enrolment));

			final Position position = this.positionService.findOne(super.getEntityId("position1"));

			enrolmentFind.setPosition(position);

			this.enrolmentService.save(enrolmentFind);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

		super.unauthenticate();

		this.rollbackTransaction();

	}
}
