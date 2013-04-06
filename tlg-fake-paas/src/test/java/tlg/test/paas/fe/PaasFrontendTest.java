package tlg.test.paas.fe;

import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tlg.test.paas.be.PaasBackend;
import tlg.test.paas.be.RuntimeStatusMgr;
import tlg.test.paas.domain.RuntimeService;

@RunWith(MockitoJUnitRunner.class)
public class PaasFrontendTest {

	@InjectMocks
	PaasFrontend paasFrontend = new PaasFrontend();
	
	@Mock
	PaasBackend paasBackend;
	
	@Mock
	RuntimeStatusMgr runtimeStatusMgr;

	
	@Test
	public void createRuntime_delegates_to_backend() throws Exception {
		String name = "myApp";
		RuntimeService jeeService = new RuntimeService("jee");
		RuntimeService dbService = new RuntimeService("db");
		RuntimeService monitoringService = new RuntimeService("monitoring");
		
		List<RuntimeService> services = Arrays.asList(jeeService, dbService, monitoringService);
		
		paasFrontend.createRuntime(name, services);
		
		verify(paasBackend).createRuntime("myApp", services);
	}	
	
	@Test
	public void createRuntime_initializes_runtime_status()  throws Exception {
		String name = "myApp";
		
		paasFrontend.createRuntime(name, null);
		
		verify(runtimeStatusMgr).updateStatus("myApp", "runtime creation registered");
	}
	
	


}
