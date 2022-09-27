package com.test.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TestDTO {
	
	private String id; 
	private String pw;
	private String name;
	private String email;
	private String gender;
	private Timestamp reg;	


}
