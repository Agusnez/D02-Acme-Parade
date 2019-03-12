
package services;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChapterServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired(required = false)
	private ChapterService	chapterService;


	//a)Requirement 5 : Register new actor Chapter
	@Test
	public void testRegisterChapter() {

		final Chapter chapter = this.chapterService.create();

		chapter.setTitle("chapter40");
		chapter.setName("Michael");
		chapter.setMiddleName("Jeffrey");
		chapter.setSurname("Jordan");
		chapter.setPhoto("https://es.wikipedia.org/wiki/Michael_Jordan");
		chapter.setEmail("jordan@gmail.com");
		chapter.setPhone("672195205");
		chapter.setAddress("Reina Mercedes 34");

		chapter.getUserAccount().setUsername("chapter40");
		chapter.getUserAccount().setPassword("chapter40");

		final Chapter chapterSaved = this.chapterService.save(chapter);

		Assert.notNull(chapterSaved);
	}

	@Test
	public void testEditChapter() {
		super.authenticate("chapter1");

		final Chapter chapter1 = this.chapterService.findOne(super.getEntityId("chapter1"));

		chapter1.setName("AAAA");

		final Chapter chapterSaved = this.chapterService.save(chapter1);

		Assert.isTrue(chapterSaved.getName().equals("AAAA"));

	}

	//a)Requirement 5 : Register new actor Chapter
	//b)All data wrong
	@Test
	public void driver() {
		final Object testingData[][] = {
			//{
			//	"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter55", "chapter55", null
			//},//Todo bien
			{
				null, "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter55", "chapter55", ConstraintViolationException.class
			},//Title null
				//			{
				//				"title1", null, "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Name null
				//			{
				//				"title1", "name1", null, "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter55", "chapter55", null
				//			},//Middle name null
				//			{
				//				"title1", "name1", "middleName1", null, "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Surname null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "hola", "email1@gmail.com", "672195205", "address1", "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Photo no URL
				//			{
				//				"title1", "name1", "middleName1", "surname1", null, "email1@gmail.com", "672195205", "address1", "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Photo null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", null, "672195205", "address1", "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Email null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "123455666", "672195205", "address1", "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Email no pattern
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", null, "address1", "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Phone null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", null, "chapter55", "chapter55", ConstraintViolationException.class
				//			},//Address null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", null, "chapter55", ConstraintViolationException.class
				//			},//Username null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter55", null, ConstraintViolationException.class
				//			},//Password null

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void template(final String title, final String name, final String middleName, final String surname, final String photo, final String email, final String phone, final String address, final String username, final String password,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			final Chapter chapter = this.chapterService.create();

			chapter.setTitle(title);
			chapter.setName(name);
			chapter.setMiddleName(middleName);
			chapter.setSurname(surname);
			chapter.setPhoto(photo);
			chapter.setEmail(email);
			chapter.setPhone(phone);
			chapter.setAddress(address);

			chapter.getUserAccount().setUsername(username);
			chapter.getUserAccount().setPassword(password);

			this.chapterService.save(chapter);

		} catch (final Throwable oops) {
			caught = oops.getClass();
			System.out.println(caught);
		}

		super.checkExceptions(expected, caught);
	}

}