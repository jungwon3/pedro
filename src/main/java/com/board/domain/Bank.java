package com.board.domain;

import lombok.Data;

@Data
public class Bank {

	private int accountNo; 	// 계좌번호 
	private String name; 	// 계좌주명
	private String bankName; // 은행명
	
}
