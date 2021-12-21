package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.example.demo.Model.CodeModel;

@Mapper
public interface CodeMapper {

	@Select("SELECT * FROM js_code_1705052 WHERE cd_open = 'Y'")
	List<CodeModel> codeAll();
	
	@Select("SELECT * FROM js_code_1705052 WHERE cd_open = 'Y' LIMIT #{page} , #{amount}")
	List<CodeModel> codeAllPaging(@Param("page") int page,
								  @Param("amount") int amount);
	
	@Select("SELECT * FROM js_code_1705052 WHERE cd_idx = ${idx} AND cd_open = 'Y'")
	List<CodeModel> codeSearchID(@Param("idx") int idx);
	
	@Select("SELECT * FROM js_code_1705052 WHERE cd_idx = ${idx} AND cd_open = 'Y'" )
	CodeModel getCode(@Param("idx") int idx);
	
	@Select("SELECT * FROM js_code_1705052 WHERE cd_name LIKE '%${keyword}%' AND cd_open = 'Y'")
	List<CodeModel> codeSearchNM(@Param("keyword") String keyword);
	
	@Select("SELECT * FROM js_code_1705052 WHERE cd_name LIKE '%${keyword}%' AND cd_open = 'Y' LIMIT #{page} , #{amount}")
	List<CodeModel> codeSearchNMPaging(@Param("keyword") String keyword,
										@Param("page") int page,
										@Param("amount") int amount);
	
	@Select("SELECT * FROM js_code_1705052 WHERE cd_maker LIKE '%${keyword}%' AND cd_open = 'Y' ")
	List<CodeModel> codeSearchMK(@Param("keyword") String keyword);

	@Select("SELECT * FROM js_code_1705052 WHERE cd_maker LIKE '%${keyword}%' AND cd_open = 'Y' LIMIT #{page} , #{amount} ")
	List<CodeModel> codeSearchMKPaging(@Param("keyword") String keyword,
										@Param("page") int page,
										@Param("amount") int amount);
	
	@Insert("INSERT INTO js_code_1705052 (cd_name, cd_code, cd_contents, cd_maker)"
							   + " VALUES('${name}','${code}','${contents}','${maker}')")
	boolean addCode(@Param("name") String name,
					@Param("code") String code,
					@Param("contents") String contents,
					@Param("maker") String maker);
	
	@Update("UPDATE js_code_1705052 SET cd_use_cnt = cd_use_cnt+1 WHERE cd_idx = #{idx}")
	public void useFunction(@Param("idx") int idx);
	
	@Select("SELECT cd_idx FROM js_code_1705052 WHERE cd_name LIKE '${name}' AND  cd_maker LIKE '${maker}' AND cd_open='Y'")
	int idxCk(@Param("name") String name,
			  @Param("maker") String maker);
	
	@Select("SELECT cd_maker FROM js_code_1705052 WHERE cd_idx = ${idx} AND cd_maker = '${maker}'")
	String makerCk(	@Param("idx") int idx,
					@Param("maker") String maker);
	
	@UpdateProvider(type = CodeSQL.class, method = "UpdateCode")
	boolean codeUpdate(@Param("id") int idx,
						@Param("code") String code,
						@Param("contents") String contents,
						@Param("name") String name,
						@Param("open") String open);
	
	@SelectProvider(type = CodeSQL.class, method = "searchCode")
	List<CodeModel> searchCode(@Param("key") String key,
						@Param("values") String values,
						@Param("page") int page,
						@Param("amount") int amount);
	
	@Delete("DELETE FROM js_code_1705052 WHERE cd_idx = #{idx} ")
	public boolean deleteCode(@Param("idx") int idx);
	
}
