package tlg.test.paas.be;

import static org.junit.Assert.*;

import org.junit.Test;

import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;

public class InMemoryRuntimeRepositoryTest {

	RuntimeRepository runtimeRepository = new InMemoryRuntimeRepository();
	
	@Test(expected=IllegalStateException.class)
	public void updateStatus_throws_exception_when_name_is_not_known() throws Exception {
		runtimeRepository.updateStatus("myApp", "status message");
	}

	@Test(expected=RuntimeAlreadyExistsError.class)
	public void registerRuntime_throws_exception_when_already_exist() throws Exception {
		runtimeRepository.registerRuntime("myApp", null);
		runtimeRepository.registerRuntime("myApp", null);
	}

	@Test
	public void registerRuntime_does_not_throws_exception_when_name_is_not_already_registered() throws Exception {
		runtimeRepository.registerRuntime("myApp1", null);
		runtimeRepository.registerRuntime("myApp2", null);
	}

	@Test
	public void registerRuntime_generates_uniq_ids() throws Exception {
		String id1 = runtimeRepository.registerRuntime("myApp1", null);
		String id2 = runtimeRepository.registerRuntime("myApp2", null);
		
		assertNotNull("id is null", id1);
		assertNotNull("id is null",id2);
		assertFalse("ids should be different", id1.equals(id2));
	}

	@Test
	public void getCurrentStatus_returns_last_updated_status() throws Exception {
		runtimeRepository.registerRuntime("myApp", null);
		runtimeRepository.updateStatus("myApp", "status 1");
		runtimeRepository.updateStatus("myApp", "status 2");
		
		assertEquals("status 2", runtimeRepository.getCurrentStatus("myApp"));
	}
	
	@Test
	public void getRuntimeName_returns_name_when_runtime_has_been_registered()  throws Exception {
		String id = runtimeRepository.registerRuntime("myApp", null);
		assertEquals("myApp", runtimeRepository.getRuntimeName(id));
	}
	@Test(expected=RuntimeNotFound.class)
	public void getRuntimeName_throws_exception_when_id_is_not_valid() throws Exception {
		runtimeRepository.getRuntimeName("invalid-id");
	}
	
}
