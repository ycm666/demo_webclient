package com.mywebclient.demo_webclient.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mywebclient.demo_webclient.model.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller - Example of WebClient CRUD
 */
@RestController
@RequestMapping("/webclient")
public class SpringBootWebClientController {

	@Autowired
	WebClient createWebClient;
	
	
	

	// Mono : 0..1
	// Flux : 0..n

	
    //@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/v1/posts")
	public List<Post>  getPosts() {
	
		Flux<Post> postFlux = createWebClient.get()
				.uri("/posts")
				.retrieve()
				.bodyToFlux(Post.class);
		
		//ArrayList로 추출하기
        List<Post> list = postFlux.toStream().collect(Collectors.toList());
		System.out.println("----------------------------------------");
        for(Post post : list){
			System.out.printf("%d : %s\n", post.getId(),post.getTitle());
		}
		System.out.println("----------------------------------------");


		return list;

		//return new ResponseEntity(postFlux, HttpStatus.OK);
	}

    //@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/v1/post/{id}")
	public Post getPost(@PathVariable String id) throws InterruptedException, ExecutionException {
	
		Mono<Post> postMono = createWebClient.get()
				.uri("/posts/" + id)
				.retrieve()
				.bodyToMono(Post.class);
				
		// 객체 얻어오기
        Post post = postMono.toFuture().get();
		System.out.println("---------------------------");
		System.out.println(post.getUserId());
		System.out.println(post.getId());
		System.out.println(post.getTitle());
		System.out.println("---------------------------");
		
		return post;

		//return new ResponseEntity(postMono, HttpStatus.OK);
	}
	
	@PostMapping(path="/v1/post", consumes=MediaType.APPLICATION_JSON_VALUE, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Mono<Post> createPost(@RequestBody Post post) {
		
		return createWebClient.post()
				 .uri("/posts")
				 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				 .body(BodyInserters.fromValue(post))
				 .retrieve()
				 .bodyToMono(Post.class);
	}
	
	@PutMapping(path="/v1/post", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Mono<Post> updatePost(@RequestBody Post post) {
		
		return createWebClient.put()
				 .uri("/posts/1")
				 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				 .body(BodyInserters.fromValue(post))
				 .retrieve()
				 .bodyToMono(Post.class);
	}
	
	@DeleteMapping(path="/v1/post/{id}")
	public Mono<Post> deletePost(@PathVariable String id) {
		
		return createWebClient.delete()
				 .uri("/posts/" + id)
				 .retrieve()
				 .bodyToMono(Post.class);
	}	
}