package tlg.test.paas.fe;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tlg.test.paas.be.PaasBackend;
import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.VirtualMachine;
import tlg.test.paas.domain.VmConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class PaasFrontendTest {

	@InjectMocks
	PaasFrontend paasFrontend = new PaasFrontend();
	
	@Mock
	PaasBackend paasBackend;

	
	@Test
	public void createRuntime_delegates_to_backend() {
		String name = "myApp";
		RuntimeService jeeService = new RuntimeService("jee");
		RuntimeService dbService = new RuntimeService("db");
		RuntimeService monitoringService = new RuntimeService("monitoring");
		
		List<RuntimeService> services = Arrays.asList(jeeService, dbService, monitoringService);
		
		paasFrontend.createRuntime(name, services);
		
		verify(paasBackend).createRuntime("myApp", services);
	}	
	
	@Test
	public void createRuntime_initializes_runtime_status() {
		String name = "myApp";
		
		paasFrontend.createRuntime(name, null);
		
		assertEquals("runtime creation registered", paasFrontend.getCurrentStatus("myApp"));
	}
	
	@Test
	public void updateStatus_updates_current_status() {
		String name = "myApp";
		
		paasFrontend.updateRuntimeStatus(name, "creation resgistered");
		paasFrontend.updateRuntimeStatus(name, "creating virtual machine");
		paasFrontend.updateRuntimeStatus(name, "deploying jee service");
		paasFrontend.updateRuntimeStatus(name, "runtime ready");

		assertEquals("runtime ready", paasFrontend.getCurrentStatus("myApp"));
	}
	


}
