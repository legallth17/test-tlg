package tlg.test.paas.fe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tlg.test.paas.be.PaasBackend;
import tlg.test.paas.be.RuntimeRepository;
import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.RuntimeStatus;

@RunWith(MockitoJUnitRunner.class)
public class PaasFrontendTest {

	@InjectMocks
	PaasFrontend paasFrontend = new PaasFrontend();
	
	@Mock
	PaasBackend paasBackend;
	
	@Mock
	RuntimeRepository runtimeRepository;

	
	@Test
	public void createRuntime_delegates_activation_to_backend() throws Exception {
		String name = "myApp";
		List<RuntimeService> services = generateSomeServices();
		
		paasFrontend.createRuntime(name, services);
		
		verify(paasBackend).activateRuntime("myApp", services);
	}


	@Test
	public void createRuntime_stores_runtime_in_repository() throws Exception {
		String name = "myApp";
		List<RuntimeService> services = generateSomeServices();
		
		paasFrontend.createRuntime(name, services);
		
		verify(runtimeRepository).registerRuntime("myApp", services);
	}	
	
	@Test
	public void createRuntime_returns_runtime_id_generated_by_repository() throws Exception {
		String name = "myApp";
		List<RuntimeService> services = generateSomeServices();

		when(runtimeRepository.registerRuntime("myApp", services)).thenReturn("AZE001");

		String id = paasFrontend.createRuntime(name, services);
		
		assertEquals("AZE001", id);
	}	

	@Test
	public void createRuntime_initializes_runtime_status_before_activation()  throws Exception {
		when(runtimeRepository.registerRuntime("myApp", null)).thenReturn("AZE001");
		
		paasFrontend.createRuntime("myApp", null);
		
		InOrder inOrder = Mockito.inOrder(runtimeRepository, paasBackend);
		inOrder.verify(runtimeRepository).updateStatus("AZE001", RuntimeStatus.ACTIVATING);
		inOrder.verify(runtimeRepository).updateStatusInfo("myApp", "runtime creation registered");
		inOrder.verify(paasBackend).activateRuntime("myApp", null);
	}
	
	@Test
	public void createRuntime_should_not_activate_and_update_status_when_already_exist()  throws Exception {
		String name = "myApp";
		List<RuntimeService> services = generateSomeServices();

		when(runtimeRepository.registerRuntime(anyString(), anyList())).thenThrow(new RuntimeAlreadyExistsError("test"));
		
		try {
			paasFrontend.createRuntime(name, null);
		} catch(RuntimeAlreadyExistsError e) {
			// We expect this exception
		}
		verify(runtimeRepository).registerRuntime("myApp", null);
		verifyNoMoreInteractions(runtimeRepository);
		verifyZeroInteractions(paasBackend);
	}

	@Test(expected=RuntimeAlreadyExistsError.class)
	public void createRuntime_throws_exception_when_already_exist()  throws Exception {
		String name = "myApp";
		List<RuntimeService> services = generateSomeServices();

		when(runtimeRepository.registerRuntime(anyString(), anyList())).thenThrow(new RuntimeAlreadyExistsError("test"));
		
		paasFrontend.createRuntime(name, null);
	}

	@Test
	public void getStatus_uses_runtime_repository() throws Exception {
		when(runtimeRepository.getRuntimeName("12345")).thenReturn("myApp");
		when(runtimeRepository.getCurrentStatusInfo("myApp")).thenReturn("app is running");
		
		assertEquals("app is running", paasFrontend.getStatus("12345"));
		
	}

	@Test(expected=RuntimeNotFound.class)
	public void getStatus_throws_exception_when_id_is_invalid() throws Exception {
		when(runtimeRepository.getRuntimeName("invalid-id")).thenThrow(new RuntimeNotFound("not found"));
		paasFrontend.getStatus("invalid-id");
		
	}
	
	private List<RuntimeService> generateSomeServices() {
		RuntimeService jeeService = new RuntimeService("jee");
		RuntimeService dbService = new RuntimeService("db");
		RuntimeService monitoringService = new RuntimeService("monitoring");
		
		List<RuntimeService> services = Arrays.asList(jeeService, dbService, monitoringService);
		return services;
	}	


}
