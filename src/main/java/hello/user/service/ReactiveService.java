package hello.user.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class ReactiveService {

	SseEmitter sseEmitter;
	
	@Autowired
	private SimulatedDataStore simulatedDataStore;
	
	
	public SseEmitter fetchFromFarAwayPlace() {
		sseEmitter = new SseEmitter();
		CompletableFuture<String> future = simulatedDataStore.asyncfetchFromFarAwayPlace();
		future.thenApplyAsync(this::sendMessage);
		return sseEmitter;
	}

	private String sendMessage(String message) {
        try {
            sseEmitter.send(message, MediaType.APPLICATION_JSON);
            sseEmitter.complete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		return message;
    }
}
