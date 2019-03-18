
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	 * ----CALCULO DE COBERTURA DE SENTENCIAS----
	 * A la hora de realizar el cálculo, nos fijamos en el "método" del servicio
	 * que estamos probando y analizamos su composición (if, for, ...) y calculamos
	 * el número de casos total que se pueden dar. Entonces la ecuación sería:
	 * 
	 * (nº casos probados / nº casos totales)*100 = cobertura(%)
	 * 
	 * Alfinal del archivo concluimos la cobertura total de los métodos y
	 * por consiguiente del servicio
	 * 
	 * 
	 * ----CALCULO DE COBERTURA DE DATOS----
	 * A la hora de realizar el cálculo, nos fijamos en cada una
	 * de las propiedades del objeto, fijándonos tanto en las
	 * restricciones por parte del "dominio" como por parte de las
	 * "reglas de negocio" y calculamos el total de comprobaciones.
	 * Si realizamos todas las comprobaciones sobre una propiedad del objeto
	 * que estamos probando,decimos que esta es un "propiedad probada".
	 * 
	 * (nº propiedades probadas / nº propiedades totales)*100 = cobertura(%)
	 * 
	 * ----Nota:
	 * Cabe destacar que si en un test del servicio ya hemos podido
	 * probar casos de un determinado método del servicio, podemos
	 * obviar la cobertura de este.
	 */

	/*
	 * a) Requirement: Actor manage his/her profile
	 * 
	 * b) Negative cases:
	 * 2. El usuario que está logeado, no es el mismo que el que está editando
	 * 3. El email no sigue el patrón especificado
	 * 4. El atributo email está a null
	 * 
	 * c) Covertura sentencias
	 * -save(): 2 probado / 4 totales = 50%
	 * -findOne(): 1 probado / 1 total = 100%
	 * 
	 * d) Covertura datos
	 * -Chapter: 1 probado / 9 totales = 11,1%
	 */
	@Test
	public void driverEditChapter() {
		final Object testingData[][] = {

			{
				"chapter1", "chapter1", "calle 13", "a@a.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", null
			}, {
				"brotherhood1", "chapter1", "calle 13", "a@a.com", "+34 333 3333", "middleName", "surname", "name", "http://www.photo.com", "title", IllegalArgumentException.class
			}, {
				"chapter1", "chapter1", "calle 13", "aa.com", "3333", "middleName", "surname", "name", "http://www.photo.com", "title", IllegalArgumentException.class
			}, {
				"chapter1", "chapter1", "calle 13", null, "3333", "middleName", "surname", "name", "http://www.photo.com", "title", NullPointerException.class
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
			chapter.setSurname(surname);
			chapter.setName(name);
			chapter.setPhoto(photo);
			chapter.setTitle(title);

			this.startTransaction();

			this.chapterService.save(chapter);
			this.chapterService.flush();

			this.rollbackTransaction();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
	}
	/*
	 * a) Requirement 7 : Register new actor Chapter
	 * b) Negative cases:
	 * 2. El title introducido es incorrecto(Null)
	 * c)99%?
	 * d)9,1%
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
			{
				"title1", null, "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "chapter58", "chapter58", ConstraintViolationException.class
			},//Name null
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

			this.startTransaction();

			this.chapterService.save(chapter);
			this.chapterService.flush();

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * 
	 * a)(Level B)Requirement 2.1: Self-assing an area to co-ordinate. Once an area is
	 * self-assigned, it cannot be changed.
	 * b)Negative cases:
	 * 2. Chapter se asigna un area cuando ya tiene una
	 * c)0%
	 * d)? ¿Hay que tomar los datos que interfieren en ese caso de uso en concreto?
	 */

	@Test
	public void driverAssignArea() {
		final Object testingData[][] = {

			{
				"chapter3", "area3", null
			}, {
				"chapter1", null, AssertionError.class
			}, {
				"chapter1", "area3", IllegalArgumentException.class
			}
		//		final COMENTAR PROBLEMA DE SET/SAVE BBDD
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

			this.rollbackTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	/*
	 * -------COBERTURA ChapterServiceTest-------
	 * 
	 * ----Cobertura Total Sentencias:
	 * save()=50%
	 * findOne()=100%
	 * 
	 * ----Cobertura Total Datos:
	 * Chapter=11,1%
	 */

}
