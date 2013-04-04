package tlg.test.paas.be;

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

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.VirtualMachine;
import tlg.test.paas.domain.VmConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class PaasBackendTest {

	@InjectMocks
	PaasBackend paasBackend = new PaasBackend();
	
	@Mock
	RuntimeServiceActivator runtimeServiceActivator;

	@Mock
	RuntimeStatusMgr frontendNotifier;
	
	@Test
	public void createRuntime_creates_vm_and_activates_all_services() {
		String name = "myApp";
		RuntimeService jeeService = new RuntimeService("jee");
		RuntimeService dbService = new RuntimeService("db");
		RuntimeService monitoringService = new RuntimeService("monitoring");
		
		VirtualMachine vm = new VirtualMachine();
		when(runtimeServiceActivator.createVm(eq("myApp"), any(VmConfiguration.class))).thenReturn(vm );
		
		List<RuntimeService> services = Arrays.asList(jeeService, dbService, monitoringService);
		
		paasBackend.createRuntime(name, services);
		
		// Verify vm is created before services are deployed
		InOrder inOrder = inOrder(runtimeServiceActivator);
		inOrder.verify(runtimeServiceActivator).createVm(eq(name), any(VmConfiguration.class));
		inOrder.verify(runtimeServiceActivator,times(3)).deployService(eq("myApp"), eq(vm), any(RuntimeService.class));

		// Verify each service is actually activated
		verify(runtimeServiceActivator).deployService("myApp", vm, jeeService);
		verify(runtimeServiceActivator).deployService("myApp", vm, dbService);
		verify(runtimeServiceActivator).deployService("myApp", vm, monitoringService);
	}

	@Test
	public void createRuntime_update_status_for_each_operation() {
		String name = "myApp";
		RuntimeService jeeService = new RuntimeService("jee");
		RuntimeService dbService = new RuntimeService("db");
		RuntimeService monitoringService = new RuntimeService("monitoring");
		
		VirtualMachine vm = new VirtualMachine();
		when(runtimeServiceActivator.createVm(eq("myApp"), any(VmConfiguration.class))).thenReturn(vm );
		
		List<RuntimeService> services = Arrays.asList(jeeService, dbService, monitoringService);
		
		paasBackend.createRuntime(name, services);
		
		verify(frontendNotifier).updateStatus("myApp","creating virtual machine");
		verify(frontendNotifier).updateStatus("myApp","deploying service jee");
		verify(frontendNotifier).updateStatus("myApp","deploying service db");
		verify(frontendNotifier).updateStatus("myApp","deploying service monitoring");
		verify(frontendNotifier).updateStatus("myApp","application environment is started");
	}

}
