package tlg.test.paas.fe;

public class RuntimeAlreadyExistsError extends Exception {

	private static final long serialVersionUID = 4326849929029755935L;

	public RuntimeAlreadyExistsError() {
		super();
	}
	
	public RuntimeAlreadyExistsError(String messsage) {
		super(messsage);
	}

}
