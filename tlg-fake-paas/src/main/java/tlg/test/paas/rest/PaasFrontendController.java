package tlg.test.paas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import tlg.test.paas.domain.RuntimeService;
import tlg.test.paas.fe.PaasFrontend;
import tlg.test.paas.fe.RuntimeAlreadyExistsError;
import tlg.test.paas.fe.RuntimeNotFound;

@Controller
public class PaasFrontendController {

	@Autowired
	private	PaasFrontend paasFrontEnd;
	
	@Autowired
	private UriBuilder uriBuilder;
	
	public PaasFrontend getPaasFrontEnd() {
		return paasFrontEnd;
	}

	public void setPaasFrontEnd(PaasFrontend paasFrontEnd) {
		this.paasFrontEnd = paasFrontEnd;
	}

	public UriBuilder getUriBuilder() {
		return uriBuilder;
	}

	public void setUriBuilder(UriBuilder uriBuilder) {
		this.uriBuilder = uriBuilder;
	}

	@RequestMapping(value="/paas/test/{id}", method=RequestMethod.GET)
	public @ResponseBody String test(@PathVariable String id) {
		return "Sucess: "+id;
	}

	@RequestMapping(value="/paas/test", method=RequestMethod.GET)
	public @ResponseBody String test() {
		return "Sucess (no id provided)";
	}


	@RequestMapping(value="/paas/runtimes", method=RequestMethod.POST)
	public ResponseEntity<RuntimeDesciption> createNewApplicationRuntime(@RequestBody RuntimeDesciption runtimeDescription) {

		String id = "";
		try {
			id = paasFrontEnd.createRuntime(runtimeDescription.getName(), runtimeDescription.getServices());
		} catch (RuntimeAlreadyExistsError e) {
			return new ResponseEntity<RuntimeDesciption>(HttpStatus.CONFLICT);
		}
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Location", uriBuilder.getCurrentRequestUriString()+"/"+id);
		return new ResponseEntity<RuntimeDesciption>(runtimeDescription, headers, HttpStatus.CREATED);
		
	}

	@RequestMapping(value="/paas/runtimes/sample", method=RequestMethod.GET)
	public @ResponseBody RuntimeDesciption getRuntimeDescriptionSample() {
		RuntimeDesciption runtimeDescription = new RuntimeDesciption("test");
		
		runtimeDescription.addService(new RuntimeService("jee"));
		runtimeDescription.addService(new RuntimeService("database"));
		runtimeDescription.addService(new RuntimeService("monitoring"));
		
		return runtimeDescription;
	}

	@RequestMapping(value="/paas/runtimes/{id}/status")
	public @ResponseBody  String getApplicationRuntimeStatus(@PathVariable("id") String applicationRuntimeId) throws RuntimeNotFound {
		return paasFrontEnd.getStatusInfo(applicationRuntimeId);
	}

	@RequestMapping(value="/paas/runtimes/{id}/start",method=RequestMethod.PUT)
	public void startRuntime(@PathVariable("id") String applicationRuntimeId) throws RuntimeNotFound {
		paasFrontEnd.startRuntime(applicationRuntimeId);
	}

	@RequestMapping(value="/paas/runtimes/{id}/stop",method=RequestMethod.PUT)
	public void stopRuntime(@PathVariable("id") String applicationRuntimeId) throws RuntimeNotFound {
		paasFrontEnd.stopRuntime(applicationRuntimeId);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody String handle(RuntimeNotFound e) {
		return e.getLocalizedMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public @ResponseBody String handle(IllegalStateException e) {
		return e.getLocalizedMessage();
	}

}
