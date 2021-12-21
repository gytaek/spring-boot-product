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

	//전체코드를 검색
	@Select("SELECT * FROM js_code_1705052 WHERE cd_open = 'Y'")
	List<CodeModel> codeAll();
	//전체코드를 페이징하여 검색 
	@Select("SELECT * FROM js_code_1705052 WHERE cd_open = 'Y' LIMIT #{page} , #{amount}")
	List<CodeModel> codeAllPaging(@Param("page") int page,
								  @Param("amount") int amount);
	//idx에 맞는 코드를 출력(검색의 리턴값을 맞추기 위해 LIST 사용) 
	@Select("SELECT * FROM js_code_1705052 WHERE cd_idx = ${idx} AND cd_open = 'Y'")
	List<CodeModel> codeSearchID(@Param("idx") int idx);
	//함수 이름 검색
	@Select("SELECT * FROM js_code_1705052 WHERE cd_name LIKE '%${keyword}%' AND cd_open = 'Y'")
	List<CodeModel> codeSearchNM(@Param("keyword") String keyword);
	//함수 이름 검색 페이징
	@Select("SELECT * FROM js_code_1705052 WHERE cd_name LIKE '%${keyword}%' AND cd_open = 'Y' LIMIT #{page} , #{amount}")
	List<CodeModel> codeSearchNMPaging(@Param("keyword") String keyword,
										@Param("page") int page,
										@Param("amount") int amount);
	//작성자 검색
	@Select("SELECT * FROM js_code_1705052 WHERE cd_maker LIKE '%${keyword}%' AND cd_open = 'Y' ")
	List<CodeModel> codeSearchMK(@Param("keyword") String keyword);
	//작성자 검색 페이징
	@Select("SELECT * FROM js_code_1705052 WHERE cd_maker LIKE '%${keyword}%' AND cd_open = 'Y' LIMIT #{page} , #{amount} ")
	List<CodeModel> codeSearchMKPaging(@Param("keyword") String keyword,
										@Param("page") int page,
										@Param("amount") int amount);
	// 함수 추가
	@Insert("INSERT INTO js_code_1705052 (cd_name, cd_code, cd_contents, cd_maker)"
							   + " VALUES('${name}','${code}','${contents}','${maker}')")
	boolean addCode(@Param("name") String name,
					@Param("code") String code,
					@Param("contents") String contents,
					@Param("maker") String maker);
	//함수 사용 횟수 추가
	@Update("UPDATE js_code_1705052 SET cd_use_cnt = cd_use_cnt+1 WHERE cd_idx = #{idx}")
	public void useFunction(@Param("idx") int idx);
	// 해당하는 함수 번호 출력
	@Select("SELECT cd_idx FROM js_code_1705052 WHERE cd_name LIKE '${name}' AND  cd_maker LIKE '${maker}' AND cd_open='Y'")
	int idxCk(@Param("name") String name,
			  @Param("maker") String maker);
	// 해당하는 메이커 이름 출력
	@Select("SELECT cd_maker FROM js_code_1705052 WHERE cd_idx = ${idx} AND cd_maker = '${maker}'")
	String makerCk(	@Param("idx") int idx,
					@Param("maker") String maker);
	//함수 업그레이동 동적 쿼리
	@UpdateProvider(type = CodeSQL.class, method = "UpdateCode")
	boolean codeUpdate(@Param("id") int idx,
						@Param("code") String code,
						@Param("contents") String contents,
						@Param("name") String name,
						@Param("open") String open);
	//코드 검색 동적 쿼리
	@SelectProvider(type = CodeSQL.class, method = "searchCode")
	List<CodeModel> searchCode(@Param("key") String key,
						@Param("values") String values,
						@Param("page") int page,
						@Param("amount") int amount);
	// 함수 삭제
	@Delete("DELETE FROM js_code_1705052 WHERE cd_idx = #{idx} ")
	public boolean deleteCode(@Param("idx") int idx);
	
}
