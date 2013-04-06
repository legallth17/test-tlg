package tlg.test.paas.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UriBuilder {

	public String getCurrentRequestUriString() {
		return ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();

	}

}
