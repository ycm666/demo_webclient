package com.mywebclient.demo_webclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	private int userId;
	
	private int id;
	
	private String title;
	
	private String body;
}