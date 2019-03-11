
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chapter;
import forms.RegisterChapterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChapterServiceTest extends AbstractTest {

	//The SUT----------------------------------------------------
	@Autowired
	private ChapterService	chapterService;


	//a)Requirement 5 : Register new actor Chapter
	@Test
	public void testRegisterChapter() {

		final RegisterChapterForm chapterForm = new RegisterChapterForm();

		chapterForm.setTitle("chapter2");
		chapterForm.setName("Michael");
		chapterForm.setMiddleName("Jeffrey");
		chapterForm.setSurname("Jordan");
		chapterForm.setPhoto("https://es.wikipedia.org/wiki/Michael_Jordan");
		chapterForm.setEmail("jordan@gmail.com");
		chapterForm.setPhone("672195205");
		chapterForm.setAddress("Reina Mercedes 34");
		chapterForm.setUsername("michaeljordan");
		chapterForm.setPassword("michaeljordan");

		final Chapter chapter = this.chapterService.reconstruct(chapterForm, null);

		this.chapterService.save(chapter);
	}

}
