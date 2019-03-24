
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Area;
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

	@Autowired
	private AreaService		areaService;


	/*
	 * ----CALCULATE SENTENCE COVERAGE----
	 * To calculate the sentence coverage, we have to look at each "service's method"
	 * we are testing and we have to analyse its composition (if, for, Assert...) and Asserts.
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
	 * a) Requirement: Actor manage his/her profile
	 * 
	 * b) Negative cases: 2,3,4
	 * 
	 * c) Sentence coverage
	 * -save(): 2 passed cases / 8 total cases = 25%
	 * -findOne(): 1 passed cases / 1 total cases = 100%
	 * 
	 * d) Data coverage
	 * -Chapter: 1 passed cases / 9 total cases = 11,1%
	 */
	@Test
	public void driverEditChapter() {
		final Object testingData[][] = {

			{
				"chapter1", "chapter1", "calle 13", "a@a.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", null
			}, //1.All fine
			{
				"brotherhood1", "chapter1", "calle 13", "a@a.com", "+34 333 3333", "middleName", "surname", "name", "http://www.photo.com", "title", IllegalArgumentException.class
			}, //2. The user who is logged, It's not the same as the user who is being edited
			{
				"chapter1", "chapter1", "calle 13", "aa.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", IllegalArgumentException.class
			}, //3. The email pattern is wrong
			{
				"chapter1", "chapter1", "calle 13", null, "3333", "middleName", "surname", "name", "http://www.photo.com", "title", NullPointerException.class
			}
		//4. The email attribute is null
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
			chapter.setSurname(surname);
			chapter.setName(name);
			chapter.setPhoto(photo);
			chapter.setTitle(title);

			this.startTransaction();

			this.chapterService.save(chapter);
			this.chapterService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();
	}

	/*
	 * a)(Level B)Requirement 7 :An actor who is not authenticated must be able to:
	 * 1. Register to the system as a chapter.
	 * b)Negative cases: 2, 3, 4, 5, 6
	 * c) Sentence coverage:
	 * -save(): 1 probado / 8 totales = 12,5%
	 * 
	 * d) Data coverage:
	 * -Chapter: 5 probado / 10 totales = 50% NO ES ASI, PREGUNTAME
	 */
	@Test
	public void driverRegisterChapter() {
		final Object testingData[][] = {
			{
				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter56", "chapter56", null
			},//1.Todo bien
			{
				null, "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter57", "chapter57", ConstraintViolationException.class
			},//2.Title = null
			{
				"title1", null, "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter58", "chapter58", ConstraintViolationException.class
			},//3.Name = null
			{
				"title1", "name1", null, "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter59", "chapter59", null
			},//4.Middle name = null
			{
				"title1", "name1", "middleName1", "surname1", "hola", "email1@gmail.com", "672195205", "address1", "chapter61", "chapter55", ConstraintViolationException.class
			},//5.Photo = no URL

			{
				"title1", "name1", "middleName1", "surname1", "https://google.com", "123455666", "672195205", "address1", "chapter64", "chapter64", IllegalArgumentException.class
			},
		//6.Email = no pattern

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterChapter((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}
	protected void templateRegisterChapter(final String title, final String name, final String middleName, final String surname, final String photo, final String email, final String phone, final String address, final String username,
		final String password, final Class<?> expected) {

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

			this.startTransaction();

			this.chapterService.save(chapter);
			this.chapterService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

	/*
	 * 
	 * a)(Level B)Requirement 2.1: Self-assing an area to co-ordinate. Once an area is
	 * self-assigned, it cannot be changed.
	 * b)Negative cases: 2
	 * c) Sentence coverage:
	 * 
	 * -save(): 1 passed cases / 8 total cases = 12,5%
	 * 
	 * d) Data coverage:
	 * 0%
	 */

	@Test
	public void driverAssignArea() {
		final Object testingData[][] = {

			{
				"chapter3", "area3", null
			}, {
				"chapter1", "area3", IllegalArgumentException.class
			//2. A Chapter who have an area, self-assing another area.
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAssignArea((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void templateAssignArea(final String username, final String areaBean, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Area area = this.areaService.findOne(super.getEntityId(areaBean));

			final Chapter chapter = this.chapterService.findOne(super.getEntityId(username));

			chapter.setArea(area);

			this.startTransaction();

			super.authenticate(username);
			this.chapterService.save(chapter);
			this.chapterService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

	/*
	 * a)(Level A)Requirement 14 :An actor who is not authenticated must be able to:
	 * 1. List the chapters that are registered in the system.
	 * 
	 * b)Negative cases: 2
	 * c) Sentence coverage:
	 * -findAll()= 2 passed cases / 2 total cases = 100%
	 * 
	 * d) Data coverage:
	 * 0%
	 */

	@Test
	public void driverListChapters() {
		final Object testingData[][] = {

			{
				3, null
			//1. Todo bien
			}, {
				28, IllegalArgumentException.class
			//2. Esperamos un resultado incorrecto
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListChapters((Integer) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateListChapters(final Integer expectedInt, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			final Chapter chapter = this.chapterService.create();

			chapter.setTitle("title1");
			chapter.setName("name1");
			chapter.setMiddleName("middleName1");
			chapter.setSurname("surname1");
			chapter.setPhoto("https://google.com");
			chapter.setEmail("email1@gmail.com");
			chapter.setPhone("672195205");
			chapter.setAddress("address1");

			chapter.getUserAccount().setUsername("chapter56");
			chapter.getUserAccount().setPassword("chapter56");

			this.startTransaction();

			this.chapterService.save(chapter);
			this.chapterService.flush();

			final Integer result = this.chapterService.findAll().size();
			Assert.isTrue(expectedInt + 1 == result);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();

	}

	/*
	 * -------Coverage ChapterService-------
	 * 
	 * ----TOTAL SENTENCE COVERAGE:
	 * save()=50%
	 * findOne()=100%
	 * findAll()=100%
	 * 
	 * ----TOTAL DATA COVERAGE:
	 * Chapter=11,1%
	 */

}
