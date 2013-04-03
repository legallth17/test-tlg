package tlg.test.paas.domain;

public class RuntimeService {

	private String name;
	public RuntimeService(String name) {
		this.setName(name);
	}

	public String toString() {
		return "RuntimeService: "+getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
