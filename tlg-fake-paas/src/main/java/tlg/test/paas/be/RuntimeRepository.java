package tlg.test.paas.be;

import java.util.List;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.RuntimeStatus;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;

public interface RuntimeRepository {

	public void updateStatusInfo(String appRuntimeName, String statusMessage) throws IllegalStateException;

	public String registerRuntime(String runtimeName, List<RuntimeService> services) throws RuntimeAlreadyExistsError;

	public String getCurrentStatusInfo(String runtimeName);

	public String getRuntimeName(String runtimeId) throws RuntimeNotFound;

	public void updateStatus(String runtimeId, RuntimeStatus status) throws RuntimeNotFound; 
	
	public RuntimeStatus getCurrentStatus(String runtimeId) throws RuntimeNotFound;

}
