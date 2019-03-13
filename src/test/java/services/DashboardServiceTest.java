/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private HistoryService	historyService;


	// Tests ------------------------------------------------------------------

	@Test
	public void authorityTest() {
		final Object authorityTest[][] = {
			{
				"admin", null
			}, {
				"member1", IllegalArgumentException.class
			}, {
				"brotherhood1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < authorityTest.length; i++)
			this.AuthorityTemplate((String) authorityTest[i][0], (Class<?>) authorityTest[i][1]);
	}

	@Test
	public void valueTest() {
		final Object valueTest[][] = {
			{
				"avg", 4.5, null
			}, {
				"max", 8.0, null
			}, {
				"min", 1.0, null
			}, {
				"stddev", 3.5, null
			}, {
				"avg", 0.0, IllegalArgumentException.class
			}, {
				"max", 0.0, IllegalArgumentException.class
			}, {
				"min", 0.0, IllegalArgumentException.class
			}, {
				"stddev", 0.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < valueTest.length; i++)
			this.ValueTemplate((String) valueTest[i][0], (Double) valueTest[i][1], (Class<?>) valueTest[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void AuthorityTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final Double avg = this.historyService.avgRecordPerHistory();

			Assert.isTrue(avg != null);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void ValueTemplate(final String method, final Double value, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("admin");

			Double test = 0.0;

			if (method == "avg")
				test = this.historyService.avgRecordPerHistory();
			else if (method == "max")
				test = this.historyService.maxRecordPerHistory();
			else if (method == "min")
				test = this.historyService.minRecordPerHistory();
			else if (method == "stddev")
				test = this.historyService.stddevRecordPerHistory();

			Assert.isTrue(test.equals(value));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
