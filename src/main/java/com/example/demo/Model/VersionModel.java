package com.example.demo.Model;

import java.util.Date;

public class VersionModel {
	
	private int cd_idx;						//코드 번호 (FK)
	private String ver_name;				//코드 이름(함수이름) 				VARCHAR2(50)
	private String ver_code;				//코드(함수코드)					VARCHAR2(2000)
	private String ver_maker;				//코드 작성자						VARCHAR2(50)
	private String ver_contents;			//코드 설명						VARCHAR2(1000)
	private Date ver_upload_date;			//코드 업데이트 일자					DATE
	private String ver_open;				//코드 사용가능 확인 default = Y     CHAR(1)  (Y,N) Y = 사용 가능, N = 사용 불가능
	private double ver_version;				//버전 번호						double
	private int ver_use_cnt;				//사용횟수 						int
	
	public VersionModel() {}
	
	public VersionModel(int cd_idx, String ver_name, String ver_code, String ver_maker, String ver_contents,
			Date ver_upload_date, String ver_open, double ver_version, int ver_use_cnt) {
		super();
		this.cd_idx = cd_idx;
		this.ver_name = ver_name;
		this.ver_code = ver_code;
		this.ver_maker = ver_maker;
		this.ver_contents = ver_contents;
		this.ver_upload_date = ver_upload_date;
		this.ver_open = ver_open;
		this.ver_version = ver_version;
		this.ver_use_cnt = ver_use_cnt;
	}


	public int getCd_idx() {
		return cd_idx;
	}


	public void setCd_idx(int cd_idx) {
		this.cd_idx = cd_idx;
	}


	public String getVer_name() {
		return ver_name;
	}


	public void setVer_name(String ver_name) {
		this.ver_name = ver_name;
	}


	public String getVer_code() {
		return ver_code;
	}


	public void setVer_code(String ver_code) {
		this.ver_code = ver_code;
	}


	public String getVer_maker() {
		return ver_maker;
	}


	public void setVer_maker(String ver_maker) {
		this.ver_maker = ver_maker;
	}


	public String getVer_contents() {
		return ver_contents;
	}


	public void setVer_contents(String ver_contents) {
		this.ver_contents = ver_contents;
	}


	public Date getver_upload_date() {
		return ver_upload_date;
	}


	public void setver_upload_date(Date ver_upload_date) {
		this.ver_upload_date = ver_upload_date;
	}


	public String getVer_open() {
		return ver_open;
	}


	public void setVer_open(String ver_open) {
		this.ver_open = ver_open;
	}


	public double getVer_version() {
		return ver_version;
	}


	public void setVer_version(double ver_version) {
		this.ver_version = ver_version;
	}


	public int getVer_use_cnt() {
		return ver_use_cnt;
	}


	public void setVer_use_cnt(int ver_use_cnt) {
		this.ver_use_cnt = ver_use_cnt;
	}
	
	
	
	
	
}

