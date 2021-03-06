package tlg.test.paas.fe;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tlg.test.paas.be.PaasBackend;
import tlg.test.paas.be.RuntimeRepository;
import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.RuntimeStatus;

@Service
public class PaasFrontend {
	
	@Autowired
	private PaasBackend paasBackend;
	
	@Autowired
	private RuntimeRepository runtimeRepository;
	
	public String createRuntime(String appRuntimeName, List<RuntimeService> services) throws RuntimeAlreadyExistsError {
		String id = runtimeRepository.registerRuntime(appRuntimeName, services);
		try {
			runtimeRepository.updateStatus(id, RuntimeStatus.ACTIVATING);
		} catch (RuntimeNotFound e) {
			throw new IllegalStateException("internal application error: operation can not complete", e);
		}
		runtimeRepository.updateStatusInfo(appRuntimeName, "runtime creation registered");
		paasBackend.activateRuntime(appRuntimeName, services);
		return id;
	}

	public PaasBackend getPaasBackend() {
		return paasBackend;
	}

	public void setPaasBackend(PaasBackend paasBackend) {
		this.paasBackend = paasBackend;
	}

	public RuntimeRepository getRuntimeRepository() {
		return runtimeRepository;
	}

	public void setRuntimeRepository(RuntimeRepository runtimeRepository) {
		this.runtimeRepository = runtimeRepository;
	}

	public String getStatusInfo(String runtimeId) throws RuntimeNotFound {
		String name = runtimeRepository.getRuntimeName(runtimeId);
		return runtimeRepository.getCurrentStatusInfo(name); 
	}

	public void startRuntime(String runtimeId) throws RuntimeNotFound {
		throw new RuntimeException("not implemented");
	}

	public void stopRuntime(String runtimeId) throws RuntimeNotFound {
		throw new RuntimeException("not implemented");
	}

}
