package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.VersionModel;
import com.example.demo.mapper.VersionMapper;

@CrossOrigin
@RestController
@RequestMapping("/version/*")
public class VersionController {

	private VersionMapper mapper;
	
	public VersionController(VersionMapper mapper) {
		this.mapper = mapper;
	}
	
	@GetMapping("all")
	public List<VersionModel> codeAll(@RequestParam(value="page" , defaultValue = "") String _page,
										@RequestParam(value="amount", defaultValue = "10")String _amount){
		
		System.out.println(_page+""+_amount);
		int amount = Integer.valueOf(_amount);
		int page = _page.isEmpty()? -1 : (Integer.valueOf(_page) -1 )* amount;
		if(page == -1)return this.mapper.codeVersionAll();
		
		return mapper.codeVersionAllPaging(page,amount);
	}
	
	@GetMapping("list/{id}")
	public List<VersionModel> codeVersionGet(@PathVariable("id") String id){
		int idx = Integer.valueOf(id);
		return this.mapper.idVersionGet(idx);
	}
	
	
	
	@GetMapping("getCode")
	public String getCodeVersionData(@RequestParam(value="id",defaultValue = "") String id,
										   @RequestParam(value="ver",defaultValue = "") String version) {
		if(!id.isEmpty() && !version.isEmpty()) {
		int idx = Integer.valueOf(id);
		double ver = Double.valueOf(version);
		this.mapper.useFunction(idx, ver);
		return "<script>"+this.mapper.versionDataGet(idx, ver).getVer_code() + "</script>";
		}else {
			return "값이 일치하는 함수가 없습니다.";
		}
	}
	
	@GetMapping("remove")
	public String deleteCode(@RequestParam(value="id",defaultValue = "") String _idx,
							@RequestParam(value="ver",defaultValue = "") String _ver) {
		if(_idx.isEmpty()) {
			return "삭제하는데 실패하였습니다.(코드번호 누락)";
		}else if(_ver.isEmpty()) {
			return "삭제하는데 실패하였습니다.(버전번호 누락)";
		}else{
			int idx = Integer.valueOf(_idx);
			double ver = Double.valueOf(_ver);
			if(mapper.versionCnt(idx) == 1) return "삭제하는데 실패하였습니다.(1개 이상의 버전 필요)";
			if(!mapper.deleteVersion(idx,ver)) return "삭제하는데 실패하였습니다.(검색된 버전 없음)";
		}
		return "삭제되었습니다.";
	}
	
	@GetMapping("upgrade")
	public String upgradeVersion(@RequestParam(value="id",defaultValue = "") String _idx,
							@RequestParam(value="ver",defaultValue = "") String _ver,
							@RequestParam(value="maker",defaultValue = "") String _maker) {
		if(_idx.isEmpty()) {
			return "버전 업그래이드에 실패하였습니다.(코드번호 누락)";
		}else if(_ver.isEmpty()) {
			return "버전 업그래이드에 실패하였습니다.(버전번호 누락)";
		}else if(_maker.isEmpty()){
			return "버전 업그래이드에 실패하였습니다.(작성자 누락)";
		}else{
			int idx = Integer.valueOf(_idx);
			double ver = Double.valueOf(_ver);
			if(mapper.makerCk(idx,_maker).isEmpty()) return "버전 업그래이드에 실패하였습니다.(작성자 불일치)";
			double upVer = Math.floor(ver)+1.01;
			System.out.println(ver);
			if(!mapper.upgradeVersion(idx,ver,upVer)) return "버전 업그래이드에 실패하였습니다.(검색된 버전 없음)";
		}
		return "버전이 업그레이드 되었습니다.";
	}
	
}
