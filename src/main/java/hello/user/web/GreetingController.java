package hello.user.web;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import hello.user.domain.Greeting;
import hello.user.service.ReactiveService;
import hello.user.service.SimulatedDataStore;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	private SimulatedDataStore simulatedDataStore;

	@Autowired
	private ReactiveService reactiveService;

	@RequestMapping(method = RequestMethod.GET, value = "/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getMeSomethinguseful")
	public String getSomethingUseful(@RequestParam(value = "aParam", defaultValue = "Tadaaaa") String name)
			throws InterruptedException {
		return simulatedDataStore.syncfetchFromFarAwayPlace();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getMeSomethingusefulNB")
	public String getMeSomethingUseful(@RequestParam(value = "aParam", defaultValue = "Tadaaaa") String name)
			throws InterruptedException {
		CompletableFuture<String> future = simulatedDataStore.asyncfetchFromFarAwayPlace();
		future.thenApplyAsync(this::sendMessage);
		return "A promise";
	}
	
	private String sendMessage(String msg){
		return msg;
	}

}
