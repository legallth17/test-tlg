package tlg.test.paas.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.server.MockMvc;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.fe.PaasFrontend;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;

@RunWith(MockitoJUnitRunner.class)
public class PaasFrontendControllerSpringTest {

	@Mock
	PaasFrontendController paasFrontEndController = new PaasFrontendController();
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		
		this.mockMvc = standaloneSetup(paasFrontEndController).build();
	}
	
	@Test
	public void getStatus_valid_resource() throws Exception {		
		when(paasFrontEndController.getApplicationRuntimeStatus("12345")).thenReturn("test status");
		
		this.mockMvc.perform(get("/paas/runtimes/12345/status"))
		.andExpect(status().isOk())
		.andExpect(content().string("test status"));
		
		verify(paasFrontEndController).getApplicationRuntimeStatus("12345");
	}

	@Test
	public void getStatus_resource_not_found() throws Exception {		
		when(paasFrontEndController.getApplicationRuntimeStatus("12345")).thenThrow(new RuntimeNotFound("invalid resource"));
		
		this.mockMvc.perform(get("/paas/runtimes/12345/status"))
		.andExpect(status().isNotFound());
		
		verify(paasFrontEndController).getApplicationRuntimeStatus("12345");
	}

	@Test
	public void start_valid_resource() throws Exception {
		this.mockMvc = standaloneSetup(paasFrontEndController).build();
		this.mockMvc.perform(put("/paas/runtimes/12345/start"))
		.andExpect(status().isOk());
		
		verify(paasFrontEndController).startRuntime("12345");
	}
	
	@Test
	public void stop_valid_resource() throws Exception {		
		this.mockMvc = standaloneSetup(paasFrontEndController).build();
		this.mockMvc.perform(put("/paas/runtimes/12345/stop"))
		.andExpect(status().isOk());

		verify(paasFrontEndController).stopRuntime("12345");
	}


}
