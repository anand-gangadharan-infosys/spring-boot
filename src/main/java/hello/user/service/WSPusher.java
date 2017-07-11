package hello.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import hello.user.domain.User;

@Service
public class WSPusher {

	@Autowired
	private SimpMessagingTemplate template;

	public Iterable<User> websocketNotify(Iterable<User> users) {
		template.convertAndSend("/ws-topic/user", users);
		return users;
	}
}
