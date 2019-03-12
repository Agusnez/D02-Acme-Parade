
package services;

import javax.transaction.Transactional;

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
	@Autowired
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
}
