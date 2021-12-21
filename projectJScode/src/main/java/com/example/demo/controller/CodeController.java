package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.CodeModel;
import com.example.demo.mapper.CodeMapper;
import com.example.demo.mapper.VersionMapper;
//cros sop 위반 오류 방지
@CrossOrigin
@RestController
@RequestMapping("/code/*")
public class CodeController {
	
	private CodeMapper mapper;
	
	private VersionMapper Vmapper;
	
	public CodeController(CodeMapper mapper, VersionMapper Vmapper) {
		this.mapper = mapper;
		this.Vmapper = Vmapper;
	}
	//코드 출력
	@GetMapping("all")
	public List<CodeModel> codeAll(@RequestParam(value="page" , defaultValue = " ") String _page,
									@RequestParam(value="amount", defaultValue = "10")String _amount){
		
		int amount = Integer.valueOf(_amount);
		int page = _page.equals(" ")? -1 : (Integer.valueOf(_page) -1 )* amount;
		if(page == -1) return this.mapper.codeAll();
		
		return this.mapper.codeAllPaging(page,amount);
	}
	
	@GetMapping("/{name}/{maker}")
	public int codeIdxNum(@PathVariable("name") String name, @PathVariable("maker") String maker) {
			
		System.out.println(name+", " + maker);
		return this.mapper.idxCk(name,maker);
	}
	
	
	// 코드추가
	@PostMapping("add")
	public String codeAdd(@RequestParam(value="name", defaultValue = "") String name,
						  @RequestParam(value="code", defaultValue = "") String code,
						  @RequestParam(value="maker", defaultValue = "") String maker,
						  @RequestParam(value="contents", defaultValue = "") String contents) {
		if(name.isEmpty() || code.isEmpty() || maker.isEmpty() || contents.isEmpty()) {
		return "값이 누락되었습니다";	
		}
		System.out.println(name+", " + maker);
	    this.mapper.addCode(name, code, contents, maker);
		int idx = this.mapper.idxCk(name, maker);
		int cnt = this.mapper.getCode(idx).getCd_use_cnt();
		this.Vmapper.addCode(idx,name, code, contents, maker,cnt);
		return "추가 완료";
	}
	
	
	
	//검색(함수이름, 일련번호 , 작성자)
	@GetMapping("search")
	public List<CodeModel> codeSearch(@RequestParam(value = "sch", defaultValue = " ")String schID,
									@RequestParam(value = "keyword", defaultValue = " ") String keyword,
									@RequestParam(value = "page", defaultValue = " ")String _page,
									@RequestParam(value = "amount", defaultValue = " ")String _amount){
		
		int amount = Integer.valueOf(_amount);
		int page = _page.equals(" ")? -1 : (Integer.valueOf(_page) -1 )* amount;
		return mapper.searchCode(schID, keyword, page, amount);
	}
	
	@GetMapping("getCode")
	public String codeScrin(@RequestParam(value = "idx", defaultValue = "") String id){
		int idx = Integer.valueOf(id);
		CodeModel cm = this.mapper.getCode(idx);
		if(cm.getCd_open().equals("Y")) {
			double version = this.Vmapper.versionNum(idx);
			this.mapper.useFunction(idx);
			this.Vmapper.useFunction(idx, version);
			return "<script>"+cm.getCd_code()+"</script>";
			}
		return "오픈되지 않은 함수 입니다.";
	}
	
	@PostMapping("update")
	public String codeUpdate(@RequestParam(value="id", defaultValue = "") String id,
							 @RequestParam(value="name", defaultValue = "") String name,
							 @RequestParam(value="code", defaultValue = "") String code,
							 @RequestParam(value="maker", defaultValue = "") String maker,
							 @RequestParam(value="contents", defaultValue = "") String contents,
							 @RequestParam(value="open", defaultValue = "") String open) {
		 
		System.out.print(code);
		int idx = Integer.valueOf(id);
		if(id.isEmpty() || maker.isEmpty()) {
			return "값이 누락되었습니다";
		}else if(this.mapper.makerCk(idx,maker).isEmpty()){
			return "작성자만 수정할 수 있습니다.";
		}else{
			CodeModel code_info = this.mapper.getCode(idx);
			
			if(name.isEmpty()) name = code_info.getCd_name();
			if(contents.isEmpty()) contents = code_info.getCd_contents();
			if(open.isEmpty()) open = code_info.getCd_open();
			
			this.mapper.codeUpdate(idx,code,contents,name,open);
			double version = this.Vmapper.versionNum(idx);
			this.Vmapper.updateCode(idx,code,name,maker,contents,version+0.01,code_info.getCd_use_cnt());
				return "수정 완료";
		}
	}
	@GetMapping("remove")
	public String deleteCode(@RequestParam(value="id",defaultValue = "") String _idx) {
		if(_idx.isEmpty()) {
			return "삭제하는데 실패하였습니다.(코드번호 누락)";
		}else{
			int idx = Integer.valueOf(_idx);
			if(!Vmapper.deleteCode(idx)) return "삭제하는데 실패하였습니다.(검색된 코드번호 없음(v))";
			if(!mapper.deleteCode(idx)) return "삭제하는데 실패하였습니다.(검색된 코드 없음(c))";
		}
		return "삭제되었습니다.";
	}
	
}
