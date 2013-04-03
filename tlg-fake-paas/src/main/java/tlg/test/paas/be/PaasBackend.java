package tlg.test.paas.be;

import java.util.List;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.VirtualMachine;
import tlg.test.paas.domain.VmConfiguration;

public class PaasBackend {

	private RuntimeServiceActivator serviceActivator;
	private PaasFrontendNotifier frontendNotifier;
	
	public void createRuntime(String appRuntimeName, List<RuntimeService> services) {
		VmConfiguration vmConfiguration = new VmConfiguration();
		frontendNotifier.updateStatus(appRuntimeName, "creating virtual machine");
		VirtualMachine vm = serviceActivator.createVm(appRuntimeName, vmConfiguration);
		
		for(RuntimeService service:services) {
			frontendNotifier.updateStatus(appRuntimeName, "deploying service "+service.getName());
			serviceActivator.deployService(appRuntimeName, vm, service);
		}
		
		frontendNotifier.updateStatus(appRuntimeName, "application environment is started");
		
	}

	public RuntimeServiceActivator getServiceActivator() {
		return serviceActivator;
	}

	public void setServiceActivator(RuntimeServiceActivator serviceActivator) {
		this.serviceActivator = serviceActivator;
	}

	public PaasFrontendNotifier getFrontendNotifier() {
		return frontendNotifier;
	}

	public void setFrontendNotifier(PaasFrontendNotifier frontendNotifier) {
		this.frontendNotifier = frontendNotifier;
	}

}
