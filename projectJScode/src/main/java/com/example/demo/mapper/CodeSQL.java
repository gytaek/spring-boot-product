package com.example.demo.mapper;

import org.apache.ibatis.jdbc.SQL;

public class CodeSQL {
	
	
	public SQL query = null;

	public String UpdateCode( int id,String code,String contents,String name,String open) {
		 query = new SQL() {
			{
			UPDATE("js_code_1705052");
			}
		};
		
		
		if(!code.toString().isEmpty()) query.SET("cd_code = '" + code + "'");
		
		if(!name.toString().isEmpty()) query.SET("cd_name = '" + name + "'");
		
		if(!open.toString().isEmpty()) query.SET("cd_open = '" + open + "'");
		
		if(!contents.toString().isEmpty()) query.SET("cd_contents = '" + contents + "'");
		
		query.WHERE("cd_idx = " + id);
		return query.toString();
	}
	
	public String searchCode(String key, String values,int page, int amount){
		query = new SQL() {
			{
			SELECT("*");
			FROM("js_code_1705052");
			}
		};
		
		switch(key) {
		case "i": query.WHERE("cd_idx = "+Integer.valueOf(values));
			break;
		case "n": query.WHERE("cd_name LIKE '%"+values+"%' ");
			break;
		case "m": query.WHERE("cd_maker LIKE '%"+values+"%' ");
			break;
		}
			query.WHERE("cd_open = 'Y' ");
			
			System.out.println(query.toString());
		if(page ==-1) {
			return query.toString();
		}
		return query.toString() + " LIMIT "+ page+ ", " + amount;
	}
	
	
}
