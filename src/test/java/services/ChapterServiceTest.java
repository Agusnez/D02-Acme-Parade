
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChapterServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private ChapterService	chapterService;


	//	//a)Requirement 5 : Register new actor Chapter
	//	@Test
	//	public void testRegisterChapter() {
	//
	//		final Chapter chapter = this.chapterService.create();
	//
	//		chapter.setTitle("chapter40");
	//		chapter.setName("Michael");
	//		chapter.setMiddleName("Jeffrey");
	//		chapter.setSurname("Jordan");
	//		chapter.setPhoto("https://es.wikipedia.org/wiki/Michael_Jordan");
	//		chapter.setEmail("jordan@gmail.com");
	//		chapter.setPhone("672195205");
	//		chapter.setAddress("Reina Mercedes 34");
	//
	//		chapter.getUserAccount().setUsername("chapter40");
	//		chapter.getUserAccount().setPassword("chapter40");
	//
	//		final Chapter chapterSaved = this.chapterService.save(chapter);
	//
	//		Assert.notNull(chapterSaved);
	//	}

	/*
	 * a) Requirement: Actor manage his/her profile
	 * Negative cases:
	 * 2. El usuario que está logeado, no es el mismo que el que está editando
	 * 3. El email no sigue el patrón especificado
	 * Data coverage:
	 */
	@Test
	public void driverEditChapter() {
		final Object testingData[][] = {
			{
				"chapter1", "chapter1", "calle 13", "a@a.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", null
			}, {
				"brotherhood1", "chapter1", "calle 13", "a@a.com", "+34 333 3333", "middleName", "surname", "name", "http://www.photo.com", "title", IllegalArgumentException.class
			}, {
				"chapter1", "chapter1", "calle 13", "aa.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditChapter((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);

	}
	protected void templateEditChapter(final String username, final int chapterId, final String address, final String email, final String phone, final String middleName, final String surname, final String name, final String photo, final String title,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Chapter chapter = this.chapterService.findOne(chapterId);

			chapter.setAddress(address);
			chapter.setEmail(email);
			chapter.setMiddleName(middleName);
			chapter.setName(surname);
			chapter.setName(name);
			chapter.setPhoto(photo);
			chapter.setTitle(title);

			this.chapterService.save(chapter);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
	}

}
