
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	private ChapterService	chapterService;

	@Autowired
	private ParadeService	paradeService;


	/*
	 * a)(Level B)Requirement 2.2:Manage the parades that are published by the brotherhoods in the area that they co-ordinate.
	 * This includes listing them grouped by status and making decisions on the parades that have status
	 * submitted. When a parade is rejected by a chapter, the chapter must jot down the reason why.
	 * Negative cases:
	 * b) 2. Un cabildo que no coordina una procession, decide sobre ella.
	 * c)
	 * d)
	 */
	@Test
	public void driverDecideParade() {
		final Object testingData[][] = {

			{
				"chapter1", "parade1", "ACCEPTED", null
			}, {
				"chapter2", "parade1", "REJECTED", IllegalArgumentException.class
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

			this.paradeService.save(parade);
			this.paradeService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
	}

}
