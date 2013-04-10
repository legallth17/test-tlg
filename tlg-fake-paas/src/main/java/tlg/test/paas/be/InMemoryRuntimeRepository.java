package tlg.test.paas.be;

import java.util.List;

import org.springframework.stereotype.Service;

import tlg.test.paas.domain.RuntimeService;

@Service
public class InMemoryRuntimeRepository implements RuntimeRepository {

	public void updateStatus(String appRuntimeName, String statusMessage) {
		// TODO Auto-generated method stub

	}

	public String registerRuntime(String string, List<RuntimeService> services) {
		// TODO Auto-generated method stub
		return null;
	}

}
