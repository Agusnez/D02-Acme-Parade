
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

	@Autowired
	private AreaService			areaService;


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
}
