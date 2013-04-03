package tlg.test.paas.fe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tlg.test.paas.be.PaasBackend;
import tlg.test.paas.domain.RuntimeService;

public class PaasFrontend {
	
	private PaasBackend paasBackend;
	
	private Map<String,String> appRuntTimeStatus = new HashMap<String, String>();

	public void createRuntime(String appRuntimeName, List<RuntimeService> services) {
		paasBackend.createRuntime(appRuntimeName, services);
		this.appRuntTimeStatus.put(appRuntimeName, "runtime creation registered");
	}

	public PaasBackend getPaasBackend() {
		return paasBackend;
	}

	public void setPaasBackend(PaasBackend paasBackend) {
		this.paasBackend = paasBackend;
	}

	public String getCurrentStatus(String appRuntimeName) {
		return appRuntTimeStatus.get(appRuntimeName);
	}

	public void updateRuntimeStatus(String appRuntimeName, String statusMessage) {
		this.appRuntTimeStatus.put(appRuntimeName,statusMessage);
	}

}
