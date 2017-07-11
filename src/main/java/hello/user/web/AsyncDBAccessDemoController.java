package hello.user.web;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.user.domain.User;
import hello.user.domain.UserRepository;
import hello.user.service.UserFetchService;
import hello.user.service.WSPusher;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/async") // This means URL's start with /demo (after
									// Application path)
public class AsyncDBAccessDemoController {

	@Autowired
	private ApplicationContext ctx;

	
	@Autowired
	private UserFetchService userFetchService;
	
	

	@GetMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email)
			throws InterruptedException {
		// @ResponseBody means the returned String is the response, not a view
		// name
		// @RequestParam means it is a parameter from the GET or POST request

		JSONObject job = new JSONObject();
		try {
			job.put("name", name);
			job.put("email", email);
			StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
			CountDownLatch latch = ctx.getBean(CountDownLatch.class);
			template.convertAndSend("jobs", job.toString());
			latch.await();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Save Initiated";
	}

	
	
	@GetMapping(path = "/all")
	public @ResponseBody String getAllUsersAsync() throws InterruptedException {
		// This returns a JSON or XML with the users
		CompletableFuture<Iterable<User>> future = userFetchService.findUsers();
		future.thenApply(this::websocketNotify);
		return "Request Taken. Check Websocket at /topic/users";
	}
	
	@Autowired
    private WSPusher pusher;
	
	private Iterable<User> websocketNotify(Iterable<User> users){
		pusher.websocketNotify(users);
		return users;
	}
}