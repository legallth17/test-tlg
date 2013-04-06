package tlg.test.paas.fe;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tlg.test.paas.be.PaasBackend;
import tlg.test.paas.be.RuntimeStatusMgr;
import tlg.test.paas.domain.RuntimeService;

@Service
public class PaasFrontend {
	
	@Autowired
	private PaasBackend paasBackend;
	
	@Autowired
	private RuntimeStatusMgr runtimeStatusMgr;
	
	public void createRuntime(String appRuntimeName, List<RuntimeService> services) throws RuntimeAlreadyExistsError {
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
