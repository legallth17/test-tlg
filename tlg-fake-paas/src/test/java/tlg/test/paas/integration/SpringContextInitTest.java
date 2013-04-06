package tlg.test.paas.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tlg.test.paas.rest.PaasFrontendController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/application-context.xml")
public class SpringContextInitTest {
	
	@Autowired
	PaasFrontendController frontEndController;
	
	@Test
	public void test() {
		assertNotNull(frontEndController);
	}

}
