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

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	@Test
	public void CorrectAuthorityTest() {

		super.authenticate("admin");

		final Double avg = this.historyService.avgRecordPerHistory();

		Assert.isTrue(avg != null);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void IncorrectAuthorityTest() {

		super.authenticate("brotherhood1");

		final Double avg = this.historyService.avgRecordPerHistory();

		Assert.isTrue(avg != null);

		super.unauthenticate();
	}

	@Test
	public void CorrectValueTest() {

		super.authenticate("admin");

		final Double stddev = this.historyService.stddevRecordPerHistory();

		Assert.isTrue(stddev == 3.5);

		super.unauthenticate();
	}

}
