package tlg.test.paas.be;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;

@Service
public class InMemoryRuntimeRepository implements RuntimeRepository {

	private Map<String,String> status = new HashMap<String, String>();
	
	public void updateStatus(String appRuntimeName, String statusMessage)  {
		if(!status.containsKey(appRuntimeName))
			throw new IllegalStateException("runtime with name "+appRuntimeName+" has not been registered");
		status.put(appRuntimeName, statusMessage);
	}

	public String registerRuntime(String runtimeName, List<RuntimeService> services) throws RuntimeAlreadyExistsError {
		if(status.containsKey(runtimeName)) throw new RuntimeAlreadyExistsError("A runtime with name "+runtimeName+ " is already resgistered");
		status.put(runtimeName, "registered");
		return Integer.toString(runtimeName.hashCode());
	}

	public String getCurrentStatus(String runtimeName) {
		return status.get(runtimeName);
	}

	public String getRuntimeName(String rurntimeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
