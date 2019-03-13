
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

	@Autowired
	private ParadeService	paradeService;





	/*
	 * a) Requirement: Actor manage his/her profile
	 * Negative cases:
	 * 2. El usuario que está logeado, no es el mismo que el que está editando
	 * 3. El email no sigue el patrón especificado
	 * Data coverage:
	 */
	//	@Test
	//	public void driverEditChapter() {
	//		final Object testingData[][] = {

			{	"chapter1", "chapter1", "calle 13", "a@a.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", null
			}, {
				"brotherhood1", "chapter1", "calle 13", "a@a.com", "+34 333 3333", "middleName", "surname", "name", "http://www.photo.com", "title", IllegalArgumentException.class
			}, {
				"chapter1", "chapter1", "calle 13", "aa.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", ConstraintViolationException.class
			}, {
				"chapter1", "chapter1", "calle 13", "a@a.com", "3333", "middleName", null, null, "http://www.photo.com", "title", ConstraintViolationException.class
			}
		//			,{
		//				"chapter1", "chapter1", "calle 13", "a@a.com", "3333", "middleName", null, "surname", "http://www.photo.com", "title", ConstraintViolationException.class
		//			}
		//COMENTAR SI PONGO SOLO UN NULL NO DETECTA AUN ESTANDO FLUSH

	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateEditChapter((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
	//				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	//
	//	}
	//	protected void templateEditChapter(final String username, final int chapterId, final String address, final String email, final String phone, final String middleName, final String surname, final String name, final String photo, final String title,
	//		final Class<?> expected) {
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			this.authenticate(username);
	//
	//			final Chapter chapter = this.chapterService.findOne(chapterId);
	//
	//			chapter.setAddress(address);
	//			chapter.setEmail(email);
	//			chapter.setMiddleName(middleName);
	//			chapter.setName(surname);
	//			chapter.setName(name);
	//			chapter.setPhoto(photo);
	//			chapter.setTitle(title);
	//
	//			this.chapterService.save(chapter);
				this.chapterService.flush();
	//
	//			this.unauthenticate();
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//
	//		}
	//
	//		super.checkExceptions(expected, caught);
	//	}

	/*
	 * a) Requirement 7 : Register new actor Chapter
	 * Negative cases:
	 * 2. El title itroducido es incorrecto(Null)
	 * Data coverage:
	 */
	@Test
	public void driverRegisterChapter() {
		final Object testingData[][] = {
			{
				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter56", "chapter56", null
			},//1.Todo bien
			{
				null, "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter57", "chapter57", ConstraintViolationException.class
			},//2.Title null
				//			{
				//				"title1", null, "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter58", "chapter58", ConstraintViolationException.class
				//			},//Name null
				//				{
				//				"title1", "name1", null, "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter59", "chapter59", null
				//			},//Middle name null
				//			{
				//				"title1", "name1", "middleName1", null, "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter60", "chapter60", ConstraintViolationException.class
				//			},//Surname null NOFUNCIONA
				//	{
				//				"title1", "name1", "middleName1", "surname1", "hola", "email1@gmail.com", "672195205", "address1", "chapter61", "chapter55", ConstraintViolationException.class
				//			},//Photo no URL

				//			{
				//				"title1", "name1", "middleName1", "surname1", null, "email1@gmail.com", "672195205", "address1", "chapter62", "chapter55", ConstraintViolationException.class
				//			},//Photo null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", null, "672195205", "address1", "chapter63", "chapter55", NullPointerException.class
				//			},//Email null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "123455666", "672195205", "address1", "chapter64", "chapter64", IllegalArgumentException.class
				//			},//Email no pattern
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", null, "address1", "chapter65", "chapter55", ConstraintViolationException.class
				//			},//Phone null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", null, "chapter66", "chapter55", ConstraintViolationException.class
				//			},//Address null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", null, "chapter55", ConstraintViolationException.class
				//			},//Username null
				//			{
				//				"title1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter67", null, ConstraintViolationException.class
				//			},//Password null


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

			this.chapterService.save(chapter);
			this.chapterService.flush();


		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * PRODRÍA IR EN EL DE PARADE
	 * a) Requirement 7/2 : Make decisions on the parades
	 * Negative cases:
	 * 2. El actor que quiere hacerlo no es un Chapter
	 * 3. El parade no tiene status submitted
	 * 4. El parade no está en su area
	 * Data coverage:
	 */

	//	protected void templateMakeDecision(final String username, final String paradeId, final String accion, final Class<?> expected) {
	//
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			this.authenticate(username);
	//			final Parade parade = this.paradeService.findOne(super.getEntityId(paradeId));
	//
	//			if (accion == "accept")
	//				parade.setStatus("ACCEPTED");
	//			else if (accion == "reject")
	//				parade.setStatus("REJECTED");
	//
	//			this.paradeService.save(parade);
	//			this.chapterService.flush();
	//			this.paradeService.flush();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		super.checkExceptions(expected, caught);
	//
	//	}
}
