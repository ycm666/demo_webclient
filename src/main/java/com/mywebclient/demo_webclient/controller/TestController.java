package com.mywebclient.demo_webclient.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.mywebclient.demo_webclient.vo.PersonTotalVo;
import com.mywebclient.demo_webclient.vo.PersonVo;

import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @Autowired
	WebClient createTestWebClient;



    @GetMapping("/test.do")
	public String getPersons() throws InterruptedException, ExecutionException {
	
		Mono<PersonTotalVo> postMono = createTestWebClient.get()
				.uri("/test.json")
				.retrieve()
				.bodyToMono(PersonTotalVo.class);
				
		// 객체 얻어오기
        PersonTotalVo personTotal = postMono.toFuture().get();
		System.out.println("---------------------------");
		for(PersonVo p : personTotal.getList()){
            System.out.println(p);
        }
		System.out.println("---------------------------");
		
		return "success";

	}


}
