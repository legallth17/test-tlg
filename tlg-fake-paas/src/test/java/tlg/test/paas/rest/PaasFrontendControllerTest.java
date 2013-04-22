package tlg.test.paas.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Provider.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.fe.PaasFrontend;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;


@RunWith(MockitoJUnitRunner.class)
public class PaasFrontendControllerTest {

	@InjectMocks
	PaasFrontendController paasFrontEndController = new PaasFrontendController();
	
	@Mock
	PaasFrontend paasFrontend;
	
	@Mock
	UriBuilder uriBuilder;
	
	@Test
	public void createApplicationRuntime_delegates_to_paasFrontEnd() throws Exception {
		when(uriBuilder.getCurrentRequestUriString()).thenReturn("http://myApp/runtimes");
		
		RuntimeDesciption runtimeDescription = new RuntimeDesciption("myApp");
		runtimeDescription.addService(new RuntimeService("jee"));
		runtimeDescription.addService(new RuntimeService("database"));
		
		paasFrontEndController.createNewApplicationRuntime(runtimeDescription);
		
		verify(paasFrontend).createRuntime("myApp", runtimeDescription.getServices());
	}

	@Test
	public void createApplicationRuntime_set_http_location_header() throws Exception {
		when(uriBuilder.getCurrentRequestUriString()).thenReturn("http://xxxx/runtimes");
		when(paasFrontend.createRuntime(eq("myApp"), anyListOf(RuntimeService.class))).thenReturn("12345");
		
		RuntimeDesciption runtimeDescription = new RuntimeDesciption("myApp");
		runtimeDescription.addService(new RuntimeService("jee"));
		runtimeDescription.addService(new RuntimeService("database"));
		
		ResponseEntity<RuntimeDesciption> responseEntity = paasFrontEndController.createNewApplicationRuntime(runtimeDescription);
		
		String location = responseEntity.getHeaders().getLocation().toString();
		assertEquals("http://xxxx/runtimes/12345", location );
	}

	@Test
	public void createApplicationRuntime_returns_http_status_CONFLICT_when_duplicate() throws Exception {
		when(uriBuilder.getCurrentRequestUriString()).thenReturn("http://xxxx/runtimes");
		
		RuntimeDesciption runtimeDescription = new RuntimeDesciption("myApp");
		runtimeDescription.addService(new RuntimeService("jee"));
		runtimeDescription.addService(new RuntimeService("database"));
		
		doThrow(new RuntimeAlreadyExistsError()).when(paasFrontend).createRuntime(anyString(), anyList());
		
		ResponseEntity<RuntimeDesciption> responseEntity = paasFrontEndController.createNewApplicationRuntime(runtimeDescription);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode() );
	}

	@Test
	public void getApplicationRuntimeStatus_returns_status() throws Exception {
		when(paasFrontend.getStatusInfo("12345")).thenReturn("test status");
		
		String status = paasFrontEndController.getApplicationRuntimeStatus("12345" );
		
		assertEquals("test status", status);
	}
	
	@Test
	public void startRuntime_delegates_to_frontend() throws RuntimeNotFound {
		paasFrontEndController.startRuntime("12345");
		verify(paasFrontend).startRuntime("12345");
	}

	@Test(expected=RuntimeNotFound.class)
	public void startRuntime_forwards_run_time_not_found_exception() throws RuntimeNotFound {
		doThrow(new RuntimeNotFound()).when(paasFrontend).startRuntime("12345");
		paasFrontEndController.startRuntime("12345");
	}

	@Test
	public void stopRuntime_delegates_to_frontend() throws RuntimeNotFound {
		paasFrontEndController.stopRuntime("12345");
		verify(paasFrontend).stopRuntime("12345");
	}
	
	@Test(expected=RuntimeNotFound.class)
	public void stopRuntime_forwards_run_time_not_found_exception() throws RuntimeNotFound {
		doThrow(new RuntimeNotFound()).when(paasFrontend).stopRuntime("12345");
		paasFrontEndController.stopRuntime("12345");
	}

}
