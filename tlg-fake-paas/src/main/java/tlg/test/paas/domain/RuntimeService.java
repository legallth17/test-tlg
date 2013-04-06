package tlg.test.paas.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class RuntimeService {

	public RuntimeService() {
	}

	private String name;
	public RuntimeService(String name) {
		this.setName(name);
	}

	public String toString() {
		return "RuntimeService: "+getName();
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
