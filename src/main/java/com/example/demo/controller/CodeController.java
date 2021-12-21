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
	//코드 멥퍼
	private CodeMapper mapper;
	
	// 버전 멥퍼
	private VersionMapper Vmapper;
	
	public CodeController(CodeMapper mapper, VersionMapper Vmapper) {
		this.mapper = mapper;
		this.Vmapper = Vmapper;
	}
	//전체 코드, 출력징 가능
	@GetMapping("all")
	public List<CodeModel> codeAll(@RequestParam(value="page" , defaultValue = " ") String _page,
									@RequestParam(value="amount", defaultValue = "10")String _amount){
		
		int amount = Integer.valueOf(_amount);
		//페이지 번호를 받지 않으면 -1을 저장
		int page = _page.equals(" ")? -1 : (Integer.valueOf(_page) -1 )* amount;
		//page가 -1이면 전체 출력 실행
		if(page == -1) return this.mapper.codeAll();
		//-1이 아니면 입력된 값에 맞게 페이징해서 출력
		return this.mapper.codeAllPaging(page,amount);
	}
	//코드 넘버 
	@GetMapping("/{name}/{maker}")
	public int codeIdxNum(@PathVariable("name") String name, @PathVariable("maker") String maker) {
			
		System.out.println(name+", " + maker);
		//함수 이름(cd_name)과 작성자(cd_maker)를 확인하여 해당하는 함수번호(cd_idx)를 출력
		return this.mapper.idxCk(name,maker);
	}
	
	
	//코드 추가 
	@PostMapping("add")
	public String codeAdd(@RequestParam(value="name", defaultValue = "") String name,
						  @RequestParam(value="code", defaultValue = "") String code,
						  @RequestParam(value="maker", defaultValue = "") String maker,
						  @RequestParam(value="contents", defaultValue = "") String contents) {
		//값이 1개라도 누락이 있으면 실행하지 않도록 설정
		if(name.isEmpty() || code.isEmpty() || maker.isEmpty() || contents.isEmpty()) {
		return "값이 누락되었습니다";	
		}
		System.out.println(name+", " + maker);
		//js_code_1705052 테이블에 데이터 저장
	    this.mapper.addCode(name, code, contents, maker);
	    //js_code_1705052 테이블에서 함수번호(cd_idx)를 받아옴
		int idx = this.mapper.idxCk(name, maker);
		//js_code_1705052 테이블에서 함수번호(cd_idx)에 해당하는 사용횟수(cd_use_cnt)를 받아옴
		int cnt = this.mapper.codeSearchID(idx).get(0).getCd_use_cnt();
		//js_version_1705052 테이블에 js_code_1705052 테이블에 저장된 내용을 똑같이 저장 
		this.Vmapper.addCode(idx,name, code, contents, maker,cnt);
		return "추가 완료";
	}
	
	
	
	//검색(함수이름, 일련번호 , 작성자) // 페이징 가능
	@GetMapping("search")
	public List<CodeModel> codeSearch(@RequestParam(value = "sch", defaultValue = " ")String schID,
									@RequestParam(value = "keyword", defaultValue = " ") String keyword,
									@RequestParam(value = "page", defaultValue = " ")String _page,
									@RequestParam(value = "amount", defaultValue = " ")String _amount){
		// 위랑 같이 페이징 기능
		int amount = Integer.valueOf(_amount);
		int page = _page.equals(" ")? -1 : (Integer.valueOf(_page) -1 )* amount;
		//js_code_1705052 테이블에서 검색 코드(schID)에 해당하는 검색어(keyword)를 탐색하여 출력
		return mapper.searchCode(schID, keyword, page, amount);
	}
	
	// 코드 사용가능하도록 <script>태그를 포함해서 출력
	@GetMapping("getCode")
	public String codeScrin(@RequestParam(value = "idx", defaultValue = "") String id){
		int idx = Integer.valueOf(id);
		CodeModel cm =this.mapper.codeSearchID(idx).get(0);
		//cd_open을 확인하여 값이 Y일 경우에만 사용가능
		if(cm.getCd_open().equals("Y")) {
			// 마지막 버전 번를 받아오고 각각의 사용횟수에 +1
			double version = this.Vmapper.versionNum(idx);
			this.mapper.useFunction(idx);
			this.Vmapper.useFunction(idx, version);
			return "<script>"+cm.getCd_code()+"</script>";
			}
		return "오픈되지 않은 함수 입니다.";
	}
	//저장된 코드 업그레이드 
	@PostMapping("update")
	public String codeUpdate(@RequestParam(value="id", defaultValue = "") String id,
							 @RequestParam(value="name", defaultValue = "") String name,
							 @RequestParam(value="code", defaultValue = "") String code,
							 @RequestParam(value="maker", defaultValue = "") String maker,
							 @RequestParam(value="contents", defaultValue = "") String contents,
							 @RequestParam(value="open", defaultValue = "") String open) {
		 //확인에 필요한 값이 비어있지 않은지 확인
		System.out.print(code);
		int idx = Integer.valueOf(id);
		if(id.isEmpty() || maker.isEmpty()) {
			return "값이 누락되었습니다";
			//작성자가 맞는지 확인
		}else if(this.mapper.makerCk(idx,maker).isEmpty()){
			return "작성자만 수정할 수 있습니다.";
		}else{
			// 코드정보를 받아옴
			CodeModel code_info = this.mapper.codeSearchID(idx).get(0);
			//각각의 정보를 확인하여 비어있으면 기존의 정보르 받아옴
			if(name.isEmpty()) name = code_info.getCd_name();
			if(contents.isEmpty()) contents = code_info.getCd_contents();
			if(open.isEmpty()) open = code_info.getCd_open();
			//받은 정보를 테이블에 idx에 맞는 데이터를 수정 
			this.mapper.codeUpdate(idx,code,contents,name,open);
			// 가장 최신 버전의 정보를 받아옴
			double version = this.Vmapper.versionNum(idx);
			//버전 테이블에 바뀐 내용을 추가  
			this.Vmapper.updateCode(idx,code,name,maker,contents,version+0.01,code_info.getCd_use_cnt());
				return "수정 완료";
		}
	}
	//코드 삭제
	@GetMapping("remove")
	public String deleteCode(@RequestParam(value="id",defaultValue = "") String _idx) {
		//코드번호를 확인 번호가 있는지 확인
		if(_idx.isEmpty()) {
			return "삭제하는데 실패하였습니다.(코드번호 누락)";
		}else{
			int idx = Integer.valueOf(_idx);
			//코드, 버전 테이블에서 해당하는 데이터들을 삭제함
			if(!Vmapper.deleteCode(idx)) return "삭제하는데 실패하였습니다.(검색된 코드번호 없음(v))";
			if(!mapper.deleteCode(idx)) return "삭제하는데 실패하였습니다.(검색된 코드 없음(c))";
		}
		return "삭제되었습니다.";
	}
	
}
