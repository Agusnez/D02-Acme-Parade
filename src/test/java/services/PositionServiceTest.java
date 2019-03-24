
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private PositionService	positionService;


	@Test
	public void driverListPosition() {

		final Object testingData[][] = {

			{
				"7", null
			}, //All fine
			{
				"8", IllegalArgumentException.class
			}, //

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListPosition((int) Integer.valueOf((String) testingData[i][0]), (Class<?>) testingData[i][1]);
	}

	protected void templateListPosition(final int size, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			this.startTransaction();

			final Collection<Position> positions = this.positionService.findAll();
			this.positionService.flush();

			Assert.isTrue(positions.size() == size);

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreatePosition() {

		final Object testingData[][] = {

			{
				"Example", "Example", null
			}, //All fine
			{
				null, "Example", ConstraintViolationException.class
			}, //English name = null
			{
				"		", "Example", ConstraintViolationException.class
			}, //English name = blank

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePosition((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateCreatePosition(final String englishName, final String spanishName, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			final Position position = this.positionService.create();

			position.setEnglishName(englishName);
			position.setSpanishName(spanishName);

			this.startTransaction();

			this.positionService.save(position);
			this.positionService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditPosition() {

		final Object testingData[][] = {

			{
				"position6", "Fundraiser", "Example", null
			}, //All fine
			{
				"position6", "Fundraiser", null, ConstraintViolationException.class
			}, //Spanish name = null
			{
				"position6", "Fundraiser", "		", ConstraintViolationException.class
			}, //Spanish name = blank

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPosition((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateEditPosition(final String positionBean, final String englishName, final String spanishName, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			final Position position = this.positionService.findOne(super.getEntityId(positionBean));

			position.setEnglishName(englishName);
			position.setSpanishName(spanishName);

			this.startTransaction();

			this.positionService.save(position);
			this.positionService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeletePosition() {

		final Object testingData[][] = {

			{
				"position6", null
			}, //Not used
			{
				"position2", IllegalArgumentException.class
			}, //Used

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeletePosition((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateDeletePosition(final String positionBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			final Position position = this.positionService.findOne(super.getEntityId(positionBean));

			this.startTransaction();

			this.positionService.delete(position);
			this.positionService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
