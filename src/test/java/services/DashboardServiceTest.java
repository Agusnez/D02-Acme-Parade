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
import domain.Sponsor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private AreaService			areaService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


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

	@Test
	public void valueTest() {
		final Object valueTest[][] = {
			{//2 histories with a total of 9 record = 4.5
				"avgRecordPerHistory", 4.5, null
			}, {//Negative
				"avgRecordPerHistory", 0.0, IllegalArgumentException.class
			}, {//brotherhood 1 has 8 record in total
				"maxRecordPerHistory", 8.0, null
			}, {//Negative
				"maxRecordPerHistory", 0.0, IllegalArgumentException.class
			}, {//brotherhood 2 has only 1 record (the mandatory one)
				"minRecordPerHistory", 1.0, null
			}, {//Negative
				"minRecordPerHistory", 0.0, IllegalArgumentException.class
			}, {//sqrt(((8-4.5)^2+(1-4.5)^2)/2) = 3.5
				"stddevRecordPerHistory", 3.5, null
			}, {//Negative
				"stddevRecordPerHistory", 111.111, IllegalArgumentException.class
			}, {
				"brotherhoodsMoreThanAverage", 111.111, null
			}, {//Negative
				"brotherhoodsMoreThanAverage", 111.111, IllegalArgumentException.class
			}, {
				"avgParadesCoordinatedByChapters", 0.93333, null
			}, {//Negative
				"avgParadesCoordinatedByChapters", 111.111, IllegalArgumentException.class
			}, {
				"minParadesCoordinatedByChapters", 1.0, null
			}, {//Negative
				"minParadesCoordinatedByChapters", 111.111, IllegalArgumentException.class
			}, {
				"maxParadesCoordinatedByChapters", 13.0, null
			}, {//Negative
				"maxParadesCoordinatedByChapters", 111.111, IllegalArgumentException.class
			}, {
				"stddevParadesCoordinatedByChapters", 6.966801508530774, null
			}, {//Negative
				"stddevParadesCoordinatedByChapters", 111.111, IllegalArgumentException.class
			}, {
				"chaptersCoordinatesMoreThan10Percent", 1.0, null
			}, {//Negative
				"chaptersCoordinatesMoreThan10Percent", 111.111, IllegalArgumentException.class
			}, {
				"ratioAreasNotCoordinatedAnyChapters", 0.33333, null
			}, {//Negative
				"ratioAreasNotCoordinatedAnyChapters", 111.111, IllegalArgumentException.class
			}, {
				"ratioDraftFinalModeParade", 0.33333, null
			}, {//Negative
				"ratioDraftFinalModeParade", 111.111, IllegalArgumentException.class
			}, {
				"ratioAccepted", 0.33333, null
			}, {//Negative
				"ratioAccepted", 0.33333, 111.111, IllegalArgumentException.class
			}, {
				"ratioRejected", 0.33333, null
			}, {//Negative
				"ratioRejected", 0.33333, 111.111, IllegalArgumentException.class
			}, {
				"ratioSubmitted", 0.33333, null
			}, {//Negative
				"ratioSubmitted", 0.33333, 111.111, IllegalArgumentException.class
			}, {
				"ratioOfActiveSponsorships", 111.111, null
			}, {//Negative
				"ratioOfActiveSponsorships", 111.111, IllegalArgumentException.class
			}, {
				"averageActiveSponsorshipsPerSponsor", 111.111, null
			}, {//Negative
				"averageActiveSponsorshipsPerSponsor", 111.111, IllegalArgumentException.class
			}, {
				"minActiveSponsorshipsPerSponsor", 111.111, null
			}, {//Negative
				"minActiveSponsorshipsPerSponsor", 111.111, IllegalArgumentException.class
			}, {
				"maxActiveSponsorshipsPerSponsor", 111.111, null
			}, {//Negative
				"maxActiveSponsorshipsPerSponsor", 111.111, IllegalArgumentException.class
			}, {
				"standartDeviationOfActiveSponsorshipsPerSponsor", 111.111, null
			}, {//Negative
				"standartDeviationOfActiveSponsorshipsPerSponsor", 111.111, IllegalArgumentException.class
			}, {
				"avgMemberPerBrotherhood", 111.111, null
			}, {//Negative
				"avgMemberPerBrotherhood", 111.111, IllegalArgumentException.class
			}, {
				"minMemberPerBrotherhood", 111.111, null
			}, {//Negative
				"minMemberPerBrotherhood", 111.111, IllegalArgumentException.class
			}, {
				"maxMemberPerBrotherhood", 111.111, null
			}, {//Negative
				"maxMemberPerBrotherhood", 111.111, IllegalArgumentException.class
			}, {
				"stddevMemberPerBrotherhood", 111.111, null
			}, {//Negative
				"stddevMemberPerBrotherhood", 111.111, IllegalArgumentException.class
			}, {
				"membersTenPerCent", 111.111, null
			}, {//Negative
				"membersTenPerCent", 111.111, IllegalArgumentException.class
			}, {
				"ratioBrotherhoodPerArea", 111.111, null
			}, {//Negative
				"ratioBrotherhoodPerArea", 111.111, IllegalArgumentException.class
			}, {
				"countBrotherhoodPerArea", 111.111, null
			}, {//Negative
				"countBrotherhoodPerArea", 111.111, IllegalArgumentException.class
			}, {
				"minBrotherhoodPerArea", 111.111, null
			}, {//Negative
				"minBrotherhoodPerArea", 111.111, IllegalArgumentException.class
			}, {
				"maxBrotherhoodPerArea", 111.111, null
			}, {//Negative
				"maxBrotherhoodPerArea", 111.111, IllegalArgumentException.class
			}, {
				"avgBrotherhoodPerArea", 111.111, null
			}, {//Negative
				"avgBrotherhoodPerArea", 111.111, IllegalArgumentException.class
			}, {
				"stddevBrotherhoodPerArea", 111.111, null
			}, {//Negative
				"stddevBrotherhoodPerArea", 111.111, IllegalArgumentException.class
			}, {
				"minResultPerFinder", 111.111, null
			}, {//Negative
				"minResultPerFinder", 111.111, IllegalArgumentException.class
			}, {
				"maxResultPerFinder", 111.111, null
			}, {//Negative
				"maxResultPerFinder", 111.111, IllegalArgumentException.class
			}, {
				"avgResultPerFinder", 111.111, null
			}, {//Negative
				"avgResultPerFinder", 111.111, IllegalArgumentException.class
			}, {
				"stddevResultPerFinder", 111.111, null
			}, {//Negative
				"stddevResultPerFinder", 111.111, IllegalArgumentException.class
			}, {
				"ratioEmptyFinders", 111.111, null
			}, {//Negative
				"ratioEmptyFinders", 111.111, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < valueTest.length; i++)
			this.ValueTemplate((String) valueTest[i][0], (Double) valueTest[i][1], (Class<?>) valueTest[i][2]);
	}

	protected void ValueTemplate(final String method, final Double value, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("admin");

			Double test = 0.0;

			if (method == "avgRecordPerHistory")
				test = this.historyService.avgRecordPerHistory();
			else if (method == "maxRecordPerHistory")
				test = this.historyService.maxRecordPerHistory();
			else if (method == "minRecordPerHistory")
				test = this.historyService.minRecordPerHistory();
			else if (method == "stddevRecordPerHistory")
				test = this.historyService.stddevRecordPerHistory();
			else if (method == "brotherhoodsMoreThanAverage")
				test = this.historyService.brotherhoodsMoreThanAverage().size() * 1.0;
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
			else if (method == "ratioDraftFinalModeParade")
				test = this.paradeService.ratioDraftFinalModeParade();
			else if (method == "ratioAccepted")
				test = this.paradeService.ratioAccepted();
			else if (method == "ratioRejected")
				test = this.paradeService.ratioRejected();
			else if (method == "ratioSubmitted")
				test = this.paradeService.ratioSubmitted();
			else if (method == "ratioOfActiveSponsorships")
				test = this.sponsorshipService.ratioOfActiveSponsorships();
			else if (method == "averageActiveSponsorshipsPerSponsor")
				test = this.sponsorshipService.averageActiveSponsorshipsPerSponsor();
			else if (method == "minActiveSponsorshipsPerSponsor")
				test = this.sponsorshipService.minActiveSponsorshipsPerSponsor() * 1.0;
			else if (method == "maxActiveSponsorshipsPerSponsor")
				test = this.sponsorshipService.maxActiveSponsorshipsPerSponsor() * 1.0;
			else if (method == "standartDeviationOfActiveSponsorshipsPerSponsor")
				test = this.sponsorshipService.standartDeviationOfActiveSponsorshipsPerSponsor();
			else if (method == "avgMemberPerBrotherhood")
				test = Double.valueOf(this.brotherhoodService.avgMemberPerBrotherhood());
			else if (method == "minMemberPerBrotherhood")
				test = Double.valueOf(this.brotherhoodService.minMemberPerBrotherhood());
			else if (method == "maxMemberPerBrotherhood")
				test = Double.valueOf(this.brotherhoodService.maxMemberPerBrotherhood());
			else if (method == "stddevMemberPerBrotherhood")
				test = Double.valueOf(this.brotherhoodService.stddevMemberPerBrotherhood());
			else if (method == "membersTenPerCent")
				test = this.memberService.membersTenPerCent().size() * 1.0;

			else if (method == "ratioBrotherhoodPerArea")
				test = Double.valueOf(this.areaService.ratioBrotherhoodPerArea());
			else if (method == "countBrotherhoodPerArea")
				test = Double.valueOf(this.areaService.countBrotherhoodPerArea());
			else if (method == "minBrotherhoodPerArea")
				test = Double.valueOf(this.areaService.minBrotherhoodPerArea());
			else if (method == "maxBrotherhoodPerArea")
				test = Double.valueOf(this.areaService.maxBrotherhoodPerArea());
			else if (method == "avgBrotherhoodPerArea")
				test = Double.valueOf(this.areaService.avgBrotherhoodPerArea());
			else if (method == "stddevBrotherhoodPerArea")
				test = Double.valueOf(this.areaService.stddevBrotherhoodPerArea());

			else if (method == "minResultPerFinder")
				test = Double.valueOf(this.finderService.minResultPerFinder());
			else if (method == "maxResultPerFinder")
				test = Double.valueOf(this.finderService.maxResultPerFinder());
			else if (method == "avgResultPerFinder")
				test = Double.valueOf(this.finderService.avgResultPerFinder());
			else if (method == "stddevResultPerFinder")
				test = Double.valueOf(this.finderService.stddevResultPerFinder());

			else if (method == "ratioEmptyFinders")
				test = this.finderService.ratioEmptyFinders();

			Assert.isTrue(test.equals(value));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void theSmallestBrotherhoodsTest() {
		final Object theSmallestBrotherhoodsTest[][] = {
			{//only the admin can summon this services
				"admin", null
			}, {
				"member1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < theSmallestBrotherhoodsTest.length; i++)
			this.theSmallestBrotherhoodsTemplate(super.getEntityId((String) theSmallestBrotherhoodsTest[i][0]), (Class<?>) theSmallestBrotherhoodsTest[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void theSmallestBrotherhoodsTemplate(final int brotherhoodId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Brotherhood b = this.brotherhoodService.findOne(brotherhoodId);

			final Collection<Brotherhood> bs = this.brotherhoodService.theSmallestBrotherhoods();

			Assert.isTrue(bs.contains(b));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void theLargestBrotherhoodsTest() {
		final Object theLargestBrotherhoodsTest[][] = {
			{//only the admin can summon this services
				"admin", null
			}, {
				"member1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < theLargestBrotherhoodsTest.length; i++)
			this.theLargestBrotherhoodsTemplate(super.getEntityId((String) theLargestBrotherhoodsTest[i][0]), (Class<?>) theLargestBrotherhoodsTest[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void theLargestBrotherhoodsTemplate(final int brotherhoodId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Brotherhood b = this.brotherhoodService.findOne(brotherhoodId);

			final Collection<Brotherhood> bs = this.brotherhoodService.theLargestBrotherhoods();

			Assert.isTrue(bs.contains(b));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void largestBrotherhoodsTest() {
		final Object largestBrotherhoodsTest[][] = {
			{//only the admin can summon this services
				"admin", null
			}, {
				"member1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < largestBrotherhoodsTest.length; i++)
			this.theLargestBrotherhoodsTemplate(super.getEntityId((String) largestBrotherhoodsTest[i][0]), (Class<?>) largestBrotherhoodsTest[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void largestBrotherhoodsTemplate(final int brotherhoodId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Brotherhood b = this.brotherhoodService.findOne(brotherhoodId);

			final Collection<Brotherhood> bs = this.historyService.largestBrotherhood();

			Assert.isTrue(bs.contains(b));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void top5Test() {
		final Object top5Test[][] = {
			{//only the admin can summon this services
				"admin", null
			}, {
				"member1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < top5Test.length; i++)
			this.top5Template(super.getEntityId((String) top5Test[i][0]), (Class<?>) top5Test[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void top5Template(final int sponsorId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Sponsor s = this.sponsorService.findOne(sponsorId);

			final Collection<String> cs = this.sponsorshipService.top5SporsorsActivedSponsorships();

			Assert.isTrue(cs.contains(s.getName()));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
