package hello.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.user.domain.User;
import hello.user.domain.UserRepository;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/sync") // This means URL's start with /demo (after
								// Application path)
public class SimpleDBAccessDemoController {

	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle
	// the data
	private UserRepository userRepository;

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@GetMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		System.out.println("User repo size before " + userRepository.count());
		userRepository.save(n);
		return "New User Added";
	}

}
