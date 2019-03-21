
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

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


	@Test
	public void driverCreateSegment() {

		final Object testingData[][] = {

			{
				"12,-8", "32,4", "2019/07/23 11:30", "2019/07/23 12:30", "parade5", null
			},

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

	public Date convertStringToDate(final String dateString) {
		Date date = null;
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			date = df.parse(dateString);
		} catch (final Exception ex) {
			System.out.println(ex);
		}
		return date;
	}
}
