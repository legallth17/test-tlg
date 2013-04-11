package tlg.test.paas.be;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;

@Service
public class InMemoryRuntimeRepository implements RuntimeRepository {

	private Map<String,String> status = new HashMap<String, String>();
	private Map<String,String> names = new HashMap<String, String>();
	
	public void updateStatus(String appRuntimeName, String statusMessage)  {
		if(!status.containsKey(appRuntimeName))
			throw new IllegalStateException("runtime with name "+appRuntimeName+" has not been registered");
		status.put(appRuntimeName, statusMessage);
	}

	public String registerRuntime(String runtimeName, List<RuntimeService> services) throws RuntimeAlreadyExistsError {
		if(status.containsKey(runtimeName)) throw new RuntimeAlreadyExistsError("A runtime with name "+runtimeName+ " is already resgistered");
		status.put(runtimeName, "registered");
		String id = Integer.toString(runtimeName.hashCode());
		names.put(id, runtimeName);
		return id;
	}

	public String getCurrentStatus(String runtimeName) {
		return status.get(runtimeName);
	}

	public String getRuntimeName(String runtimeId) throws RuntimeNotFound {
		if(!names.containsKey(runtimeId)) throw new RuntimeNotFound("runtime with id "+runtimeId+"  does not exist");
		return names.get(runtimeId);
	}

}
