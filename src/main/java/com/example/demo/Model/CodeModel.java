package com.example.demo.Model;

import java.util.Date;

public class CodeModel {

	private int cd_idx;				//코드 고유번호 auto_increment		int
	private String cd_name;			//코드 이름(함수이름) 				VARCHAR2(50)
	private String cd_code;			//코드(함수코드)					VARCHAR2(2000)
	private String cd_maker;		//코드 작성자						VARCHAR2(50)
	private String cd_contents;		//코드 설명						VARCHAR2(1000)
	private Date cd_update;			//코드 업데이트 일자					DATE
	private String cd_open;			//코드 사용가능 확인 default = Y     CHAR(1)  (Y,N) Y = 사용 가능, N = 사용 불가능
	private int cd_use_cnt;			//사용횟수 						int
	
	
	
	
	public CodeModel(int cd_idx, String cd_name, String cd_code, Date cd_update, String cd_open, String cd_contents,
			String cd_maker,int cd_use_cnt) {
		super();
		this.cd_idx = cd_idx;				
		this.cd_name = cd_name;				
		this.cd_code = cd_code;				
		this.cd_update = cd_update;			
		this.cd_open = cd_open;			
		this.cd_contents = cd_contents;		
		this.cd_maker = cd_maker;
		this.cd_use_cnt = cd_use_cnt;
	}


	public int getCd_idx() {
		return cd_idx;
	}


	public void setCd_idx(int cd_idx) {
		this.cd_idx = cd_idx;
	}


	public String getCd_name() {
		return cd_name;
	}


	public void setCd_name(String cd_name) {
		this.cd_name = cd_name;
	}


	public String getCd_code() {
		return cd_code;
	}


	public void setCd_code(String cd_code) {
		this.cd_code = cd_code;
	}


	public Date getCd_update() {
		return cd_update;
	}


	public void setCd_update(Date cd_update) {
		this.cd_update = cd_update;
	}


	public String getCd_open() {
		return cd_open;
	}


	public void setCd_open(String cd_open) {
		this.cd_open = cd_open;
	}


	public String getCd_contents() {
		return cd_contents;
	}


	public void setCd_contents(String cd_contents) {
		this.cd_contents = cd_contents;
	}


	public String getCd_maker() {
		return cd_maker;
	}


	public void setCd_maker(String cd_maker) {
		this.cd_maker = cd_maker;
	}


	public int getCd_use_cnt() {
		return cd_use_cnt;
	}


	public void setCd_use_cnt(int cd_use_cnt) {
		this.cd_use_cnt = cd_use_cnt;
	}
	
	
	
	
}
