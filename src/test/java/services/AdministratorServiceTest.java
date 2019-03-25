
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//The SUT--------------------------------------------------
	@Autowired
	private AdministratorService	adminService;


	/*
	 * ----CALCULATE SENTENCE COVERAGE----
	 * To calculate the sentence coverage, we have to look at each "service's method"
	 * we are testing and we have to analyse its composition (if, for, ...) and Asserts.
	 * Then, we calculate the number of total cases which our code can execute. The equation will be:
	 * 
	 * (n� passed cases / n� total cases)*100 = coverage(%)
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
	 * (n� proven attributes/ n� total attributes)*100 = coverage(%)
	 * 
	 * ----Note:
	 * It's clear that if we have tested all cases about a method in a test
	 * and now It have already had a 100% of coverage, we don't have to
	 * mention its coverage in other test.
	 */

	/*
	 * ACME-MADRUG�
	 * a)(Level C)Requirement 9: Actor who is authenticated
	 * 2.Edit personal data
	 * b) Negative cases: 2
	 * 
	 * c) Sentence coverage
	 * -save(): 2 passed cases / 5 total cases = 40%
	 * -findOne(): 1 passed cases / 2 total cases = 50%
	 * 
	 * d) Data coverage
	 * 0%
	 */
	@Test
	public void driverEditAdmin() {
		final Object testingData[][] = {

			{
				"admin", "administrator1", "name1", null
			}, //1.All fine
			{
				"chapter1", "administrator1", "name1", IllegalArgumentException.class
			}, //2. The user who is logged, It's not the same as the user who is being edited
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAdmin((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void templateEditAdmin(final String username, final int adminId, final String name, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Administrator admin = this.adminService.findOne(adminId);

			admin.setName(name);

			this.startTransaction();

			this.adminService.save(admin);
			this.adminService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		super.checkExceptions(expected, caught);
		this.rollbackTransaction();
	}

	/*
	 * ACME-MADRUG�
	 * a)(Level C)Requirement 8 :An actor who is authenticated as an administrator must be able to:
	 * 1. Create user accounts for new administrators.
	 * b)Negative cases: 2, 3 ,4
	 * c) Sentence coverage:
	 * -save(): 1 probado / 5 totales = 20%
	 * -create(): 2 probado/ 3 totales = 33.3%
	 * 
	 * d) Data coverage:
	 * -Administrator: 1 probado / 7 totales = 14,28%
	 */

	@Test
	public void driverRegisterAdmin() {
		final Object testingData[][] = {
			{
				"admin", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "admin55", "admin55", null
			},//1.Todo bien
			{
				"admin", "", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "admin55", "admin55", ConstraintViolationException.class
			},//2.Name = blank
			{
				"admin", "<script>alert('hola')</script>", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "admin55", "admin55", ConstraintViolationException.class
			},//3.name = html
			{
				"brotherhood1", "name1", "middleName1", "surname1", "https://google.com", "email1@gmail.com", "672195205", "address1", "admin55", "admin55", IllegalArgumentException.class
			},//4.The actor who is authenticated is not an Administrator

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterAdmin((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}
	protected void templateRegisterAdmin(final String usernameLogin, final String name, final String middleName, final String surname, final String photo, final String email, final String phone, final String address, final String username,
		final String password, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			super.authenticate(usernameLogin);

			final Administrator admin = this.adminService.create();

			admin.setName(name);
			admin.setMiddleName(middleName);
			admin.setSurname(surname);
			admin.setPhoto(photo);
			admin.setEmail(email);
			admin.setPhone(phone);
			admin.setAddress(address);

			admin.getUserAccount().setUsername(username);
			admin.getUserAccount().setPassword(password);

			this.adminService.save(admin);
			this.adminService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		super.checkExceptions(expected, caught);

	}

}