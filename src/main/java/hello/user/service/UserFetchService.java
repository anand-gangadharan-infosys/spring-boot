package hello.user.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import hello.user.domain.User;
import hello.user.domain.UserRepository;

@Service
public class UserFetchService {

	private static final Logger logger = LoggerFactory.getLogger(UserFetchService.class);

	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle
	// the data
	private UserRepository userRepository;
	

	@Async
	public CompletableFuture<Iterable<User>> findUsers() throws InterruptedException {
		logger.info("Looking up users .... ");
		// Artificial delay of 1s for demonstration purposes
		Thread.sleep(3000L);
		logger.info("Nearly done");
		return CompletableFuture.completedFuture(userRepository.findAll());
	}

}