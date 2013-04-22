package tlg.test.paas.be;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.RuntimeStatus;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;

@Service
public class InMemoryRuntimeRepository implements RuntimeRepository {

	private Map<String,RuntimeStatus> status = new HashMap<String, RuntimeStatus>();
	private Map<String,String> statusInfo = new HashMap<String, String>();
	private Map<String,String> names = new HashMap<String, String>();
	
	public void updateStatusInfo(String appRuntimeName, String statusMessage)  {
		if(!statusInfo.containsKey(appRuntimeName))
			throw new IllegalStateException("runtime with name "+appRuntimeName+" has not been registered");
		statusInfo.put(appRuntimeName, statusMessage);
	}

	public String registerRuntime(String runtimeName, List<RuntimeService> services) throws RuntimeAlreadyExistsError {
		if(statusInfo.containsKey(runtimeName)) throw new RuntimeAlreadyExistsError("A runtime with name "+runtimeName+ " is already resgistered");
		statusInfo.put(runtimeName, "registered");
		String id = Integer.toString(runtimeName.hashCode());
		names.put(id, runtimeName);
		status.put(id, RuntimeStatus.REGISTERED);
		return id;
	}

	public String getCurrentStatusInfo(String runtimeName) {
		return statusInfo.get(runtimeName);
	}

	public String getRuntimeName(String runtimeId) throws RuntimeNotFound {
		if(!names.containsKey(runtimeId)) throw new RuntimeNotFound("runtime with id "+runtimeId+"  does not exist");
		return names.get(runtimeId);
	}

	public void updateStatus(String runtimeId, RuntimeStatus status) throws RuntimeNotFound {
		if(!this.status.containsKey(runtimeId)) throw new RuntimeNotFound("runtime with id "+runtimeId+"  does not exist");
		this.status.put(runtimeId, status);
	}

	public RuntimeStatus getCurrentStatus(String runtimeId)
			throws RuntimeNotFound {
		if(!this.status.containsKey(runtimeId)) throw new RuntimeNotFound("runtime with id "+runtimeId+"  does not exist");
		return this.status.get(runtimeId);
	}

}
