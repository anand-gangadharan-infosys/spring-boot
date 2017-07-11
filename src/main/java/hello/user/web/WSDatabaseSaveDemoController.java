package hello.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import hello.user.domain.Greeting;
import hello.user.domain.User;
import hello.user.domain.UserRepository;

@Controller
public class WSDatabaseSaveDemoController {

	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@MessageMapping("/adduser")
	@SendTo("/topic/user")
	public Greeting saveUser(User user) throws Exception {
		userRepository.save(user);
		return new Greeting("New User Added");

	}
}
