package tlg.test.paas.fe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tlg.test.paas.be.PaasBackend;
import tlg.test.paas.be.RuntimeStatusMgr;
import tlg.test.paas.domain.RuntimeService;

public class PaasFrontend {
	
	private PaasBackend paasBackend;
	private RuntimeStatusMgr runtimeStatusMgr;
	
	public void createRuntime(String appRuntimeName, List<RuntimeService> services) {
		paasBackend.createRuntime(appRuntimeName, services);
		this.runtimeStatusMgr.updateStatus(appRuntimeName, "runtime creation registered");
	}

	public PaasBackend getPaasBackend() {
		return paasBackend;
	}

	public void setPaasBackend(PaasBackend paasBackend) {
		this.paasBackend = paasBackend;
	}

	public RuntimeStatusMgr getRuntimeStatusMgr() {
		return runtimeStatusMgr;
	}

	public void setRuntimeStatusMgr(RuntimeStatusMgr runtimeStatusMgr) {
		this.runtimeStatusMgr = runtimeStatusMgr;
	}

}
