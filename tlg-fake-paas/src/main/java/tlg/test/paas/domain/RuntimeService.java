package tlg.test.paas.domain;

public class RuntimeService {

	String name;
	public RuntimeService(String name) {
		this.name = name;
	}

	public String toString() {
		return "RuntimeService: "+name;
	}
}
