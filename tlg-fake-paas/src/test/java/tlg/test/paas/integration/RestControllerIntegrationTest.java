package tlg.test.paas.integration;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.integration.annotation.Header;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.rest.PaasFrontendController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/application-context.xml")
public class RestControllerIntegrationTest {
	
	@Autowired
	PaasFrontendController frontEndController;
	

	private MockMvc mockMvc;
	String id = "";
	
	@Before
	public void setup() throws Exception {
		
		this.mockMvc = standaloneSetup(frontEndController).build();
	}

	private String registerRuntime(String runtimeName) throws RuntimeAlreadyExistsError {
		return frontEndController.getPaasFrontEnd().createRuntime(runtimeName, Arrays.asList(new RuntimeService("s1")));
	}

	@Test
	public void getStatus() throws Exception {
		id = registerRuntime("test-status");
		this.mockMvc.perform(get("/paas/runtimes/"+id+"/status"))
		.andExpect(status().isOk());
	}

	@Test
	@Ignore("not fully implemented")
	public void start() throws Exception {
		id = registerRuntime("test-start");
		this.mockMvc.perform(put("/paas/runtimes/"+id+"/start"))
		.andExpect(status().isOk());
	}

	@Test
	@Ignore("not fully implemented")
	public void stop() throws Exception {
		id = registerRuntime("test-stop");
		this.mockMvc.perform(put("/paas/runtimes/"+id+"/stop"))
		.andExpect(status().isOk());
	}

	@Test
	public void create() throws Exception {
		String message = "<runtime name=\"test\"/>";
		this.mockMvc.perform(post("/paas/runtimes").contentType(MediaType.APPLICATION_XML).body(message.getBytes()))
		.andExpect(status().isCreated())
		.andExpect(header().string("Location", Matchers.startsWith("http://localhost/paas/runtimes/")));
	}


}
