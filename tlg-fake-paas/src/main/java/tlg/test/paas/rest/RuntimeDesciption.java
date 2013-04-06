package tlg.test.paas.rest;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import tlg.test.paas.domain.RuntimeService;



@XmlRootElement(name="runtime")
public class RuntimeDesciption {
	
	public RuntimeDesciption() {
	}
	
	public RuntimeDesciption(String runtimeName) {
		this.runtimeName = runtimeName;
	}
	@XmlAttribute(name="name")
	String runtimeName;
	
	@XmlElement(name="service")
	List<RuntimeService> services = new Vector<RuntimeService>();
	
	public void addService(RuntimeService runtimeService) {
		services.add(runtimeService);
	}

	public List<RuntimeService> getServices() {
		return this.services;
	}

	public String getName() {
		return this.runtimeName;
	}
	
	
}
