package tlg.test.paas.be;

import java.util.List;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;

public interface RuntimeRepository {

	public void updateStatus(String appRuntimeName, String statusMessage) throws IllegalStateException;

	public String registerRuntime(String string, List<RuntimeService> services) throws RuntimeAlreadyExistsError;

	public String getCurrentStatus(String runtimeName);

	public String getRuntimeName(String rurntimeId) throws RuntimeNotFound; 

}
