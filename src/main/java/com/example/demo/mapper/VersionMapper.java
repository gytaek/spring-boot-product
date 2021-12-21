package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.Model.VersionModel;

@Mapper
public interface VersionMapper {
	
	@Select("SELECT * FROM js_version_1705052 WHERE ver_open = 'Y' ORDER BY cd_idx , ver_version")
	public List<VersionModel> codeVersionAll();
	
	@Select("SELECT * FROM js_version_1705052 LIMIT #{page} ,#{amount} ")
	public List<VersionModel> codeVersionAllPaging(@Param("page") int page,
			   										@Param("amount") int amount);
	
	@Select("SELECT * FROM js_version_1705052 WHERE cd_idx = ${idx} AND ver_open = 'Y'ORDER BY ver_version")
	public List<VersionModel> idVersionGet(@Param("idx") int idx);
	
	@Select("SELECT * FROM js_version_1705052 WHERE cd_idx = ${idx} AND ver_version = ${ver} AND ver_open = 'Y'")
	public VersionModel versionDataGet(@Param("idx") int idx,
									   @Param("ver") double ver);
	
	@Insert("INSERT INTO js_version_1705052 (cd_idx,ver_name, ver_code, ver_maker, ver_contents, ver_upload_date, ver_use_cnt)"
										+ " VALUES(${idx},'${name}','${code}','${maker}','${contents}',NOW(), ${cnt})")
	public boolean addCode(	@Param("idx") int idx,
							@Param("name") String name,
							@Param("code") String code,
							@Param("maker") String maker,
							@Param("contents") String contents,
							@Param("cnt") int cnt);
	
	@Insert("INSERT INTO js_version_1705052 (cd_idx,ver_name , ver_code, ver_maker, ver_contents, ver_upload_date, ver_version,ver_use_cnt)"
								 + " VALUES (${idx},'${name}','${code}','${maker}','${contents}',		NOW()	, ${version}, ${cnt})")
	public boolean updateCode(	@Param("idx") int idx,
								@Param("code") String code,
								@Param("name") String name,
								@Param("maker") String maker,
								@Param("contents") String contents,
								@Param("version") double version,
								@Param("cnt") int cnt);
	
	@Select("SELECT MAX(ver_version) FROM js_version_1705052 WHERE cd_idx = #{idx}")
	public double versionNum(@Param("idx") int idx);
	
	@Update("UPDATE js_version_1705052 SET ver_use_cnt = ver_use_cnt+1 WHERE cd_idx = #{idx} AND ver_version = #{version}")
	public void useFunction(@Param("idx") int idx,
							@Param("version") double version);
	
	
	@Select("SELECT ver_maker FROM js_version_1705052 WHERE cd_idx = ${idx} AND ver_maker = '${maker}'")
	String makerCk(	@Param("idx") int idx,
					@Param("maker") String maker);
	
	@Update("UPDATE js_version_1705052 SET ver_open ='N' WHERE cd_idx = #{idx}")
	public void offFunction(@Param("idx") int idx);
	
	@Select("SELECT COUNT(*) FROM js_version_1705052 WHERE cd_idx = ${idx}")
	public int versionCnt(@Param("idx") int idx);
	
	@Delete("DELETE FROM js_version_1705052 WHERE cd_idx = #{idx} ")
	public boolean deleteCode(@Param("idx") int idx);
	
	@Delete("DELETE FROM js_version_1705052 WHERE cd_idx = #{idx} AND ver_version = #{version}")
	public boolean deleteVersion(@Param("idx") int idx,
								 @Param("version") double version);
	
	@Update("UPDATE js_version_1705052 SET ver_version = #{upVer} WHERE cd_idx = #{idx} AND ver_version = #{version}")
	public boolean upgradeVersion(@Param("idx") int idx,
			 					@Param("version") double version,
			 					@Param("upVer") double upVer);
	
}
