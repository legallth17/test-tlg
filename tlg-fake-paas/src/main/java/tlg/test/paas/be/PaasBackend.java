package tlg.test.paas.be;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.VirtualMachine;
import tlg.test.paas.domain.VmConfiguration;

@Service
public class PaasBackend {

	@Autowired
	private RuntimeServiceActivator serviceActivator;
	
	@Autowired
	private RuntimeRepository runtimeRepository;
	
	public void activateRuntime(String appRuntimeName, List<RuntimeService> services) {
		VmConfiguration vmConfiguration = new VmConfiguration();
		runtimeRepository.updateStatus(appRuntimeName, "creating virtual machine");
		VirtualMachine vm = serviceActivator.createVm(appRuntimeName, vmConfiguration);
		
		for(RuntimeService service:services) {
			runtimeRepository.updateStatus(appRuntimeName, "deploying service "+service.getName());
			serviceActivator.deployService(appRuntimeName, vm, service);
		}
		
		runtimeRepository.updateStatus(appRuntimeName, "application environment is started");
		
	}

	public RuntimeServiceActivator getServiceActivator() {
		return serviceActivator;
	}

	public void setServiceActivator(RuntimeServiceActivator serviceActivator) {
		this.serviceActivator = serviceActivator;
	}

	public RuntimeRepository getRuntimeRepository() {
		return runtimeRepository;
	}

	public void setRuntimeRepository(RuntimeRepository runtimeRepository) {
		this.runtimeRepository = runtimeRepository;
	}

}
