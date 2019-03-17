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

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private AreaService		areaService;


	// Tests ------------------------------------------------------------------

	@Test
	public void authorityTest() {
		final Object authorityTest[][] = {
			{//only the admin can summon this services
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
			{//2 histories with a total of 9 record = 4.5
				"avg", 4.5, null
			}, {//brotherhood 1 has 8 record in total
				"max", 8.0, null
			}, {//brotherhood 2 has only 1 record (the mandatory one)
				"min", 1.0, null
			}, {//sqrt(((8-4.5)^2+(1-4.5)^2)/2) = 3.5
				"stddev", 3.5, null
			}, {
				"avg", 0.0, IllegalArgumentException.class
			}, {
				"max", 0.0, IllegalArgumentException.class
			}, {
				"min", 0.0, IllegalArgumentException.class
			}, {
				"stddev", 0.0, IllegalArgumentException.class
			},
			//En los siguientes escenarios no hacemos uso de
			//test negativos porque el error va ser siempre el mismo
			//(IllegalArgumentException)
			{
				"avgParadesCoordinatedByChapters", 0.93333, null
			}, {
				"minParadesCoordinatedByChapters", 1.0, null
			}, {
				"maxParadesCoordinatedByChapters", 13.0, null
			}, {
				"stddevParadesCoordinatedByChapters", 6.966801508530774, null
			}, {
				"chaptersCoordinatesMoreThan10Percent", 1.0, null
			}, {
				"ratioAreasNotCoordinatedAnyChapters", 0.33333, null
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
			else if (method == "avgParadesCoordinatedByChapters")
				test = this.paradeService.avgParadesCoordinatedByChapters();
			else if (method == "minParadesCoordinatedByChapters")
				test = this.paradeService.minParadesCoordinatedByChapters() * 1.0;
			else if (method == "maxParadesCoordinatedByChapters")
				test = this.paradeService.maxParadesCoordinatedByChapters() * 1.0;
			else if (method == "stddevParadesCoordinatedByChapters")
				test = this.paradeService.stddevParadesCoordinatedByChapters();
			else if (method == "chaptersCoordinatesMoreThan10Percent")
				test = this.paradeService.chaptersCoordinatesMoreThan10Percent().size() * 1.0;
			else if (method == "ratioAreasNotCoordinatedAnyChapters")
				test = this.areaService.ratioAreasNotCoordinatedAnyChapters();
			Assert.isTrue(test.equals(value));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
