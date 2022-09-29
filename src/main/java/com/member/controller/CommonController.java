package com.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.member.domain.MemberVO;
import com.member.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/common/*")
@Log4j
public class CommonController {

	@Autowired
	private MemberService service;
	
	@GetMapping("main")
	public void main() {
		log.info("************** main!!!! *************");
	}
	
	// 로그인 폼 요청 (처리는 시큐리티가) 
	@GetMapping("login")
	public void login(String error, HttpServletRequest request) {
		log.info("************** login!!!! *************");
		log.info("error : " + error);
		
		// 접근제한때문에 로그인폼으로 강제 이동되었다면 직전 url 뽑아서 session 임시저장 
		String referrer = request.getHeader("Referer");
		request.getSession().setAttribute("prevPage", referrer);
		
	}
	
	// 회원가입 폼 
	@GetMapping("signup")
	public void signup() {
		log.info("************** signup!!!! *************");
	}
	// 회원가입 처리 
	@PostMapping("signup")
	public String signupPro(MemberVO member, String au, RedirectAttributes rttr) {
		
		log.info("*********** signupPro MemberVO : " + member);
		log.info("*********** signupPro au : " + au);
		
		int result = service.addMember(member);  // 회원 추가 
		service.addAuth(au, member.getId());	// 권한 추가 
		
		if(result == 1) {
			rttr.addFlashAttribute("msg", "success");
		}
		
		return "redirect:/common/main";
	}
	
	// 접근 제한시 보여줄 페이지 경로 매핑 
	@GetMapping("accessError")
	public void accessError(Authentication auth) {
		log.info("*********** 접근 제한 : " + auth);
	}
	
	
	
	
	
	
}
