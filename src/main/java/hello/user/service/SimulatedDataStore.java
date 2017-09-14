package hello.user.service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SimulatedDataStore {

	private static final Logger logger = LoggerFactory.getLogger(SimulatedDataStore.class);

	public String syncfetchFromFarAwayPlace(){
		return realFromFarAwayPlace();
	}

	@Async
	public  CompletableFuture<String> asyncfetchFromFarAwayPlace() {
		return CompletableFuture.completedFuture(realFromFarAwayPlace());
	}
	
	private String realFromFarAwayPlace(){
		//10 percent of work is expensive
		boolean expensiveFetch = new Random().nextInt(10)==0;
		if(expensiveFetch){
			try {
				Thread.sleep(3000L);
				logger.info("Done expensive opp");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Found what you where looking for";
		
	}

}
