package tlg.test.paas.be;

import java.util.List;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.domain.VirtualMachine;
import tlg.test.paas.domain.VmConfiguration;

public class PaasBackend {

	private RuntimeServiceActivator serviceActivator;
	
	public void createRuntime(String appRuntimeName, List<RuntimeService> services) {
		VmConfiguration vmConfiguration = new VmConfiguration();
		VirtualMachine vm = serviceActivator.createVm(appRuntimeName, vmConfiguration);
		
		for(RuntimeService service:services) {
			serviceActivator.deployService(appRuntimeName, vm, service);
		}
		
	}

	public RuntimeServiceActivator getServiceActivator() {
		return serviceActivator;
	}

	public void setServiceActivator(RuntimeServiceActivator serviceActivator) {
		this.serviceActivator = serviceActivator;
	}

}
