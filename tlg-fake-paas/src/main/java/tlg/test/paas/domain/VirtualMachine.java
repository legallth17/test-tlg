package tlg.test.paas.domain;

public class VirtualMachine {

	private String name;
	private String ip;
	private VmConfiguration vmConfiguration;
	
	public VmConfiguration getVmConfiguration() {
		return vmConfiguration;
	}
	
	public void setVmConfiguration(VmConfiguration vmConfiguration) {
		this.vmConfiguration = vmConfiguration;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+": "+name;
	}
}
