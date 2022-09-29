package com.board.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.BoardVO;
import com.board.domain.Criteria;
import com.board.domain.PageDTO;
import com.board.service.BoardService;
import com.google.gson.JsonObject;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

	@Autowired
	private BoardService service; 
	
	@GetMapping("list")
	public void list(Model model, Criteria cri) {
		log.info("list!!!!!!!");
		// model로 list jsp에 뿌려줄 글 목록 전달 
		model.addAttribute("list", service.getList(cri)); 
		log.info("************ cri : " + cri);
		
		int total = service.getTotal(cri); // 게시글 개수 가져오기 
		log.info("******************* total : " + total);
		model.addAttribute("pager", new PageDTO(cri, total)); 
		
	}
	
	@GetMapping("read")
	public void read(Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		log.info("read!!!!!!!");
		model.addAttribute("board", service.get(bno));
	}
	
	@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능하게 
	@GetMapping("modify")
	public void modifyForm(Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		log.info("modifyForm!!!!!!!");
		model.addAttribute("board", service.get(bno));
	}
	
	@PreAuthorize("principal.useranme == #board.writer") // 작성자와 로그인한 사람이 같은지 확인 
	@PostMapping("modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		// 수정처리 
		if(service.modify(board)) {
			log.info("************ 수정성공!!!!!!!!!!!!! ************");
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@PreAuthorize("principal.useranme == #writer") // 작성자와 로그인한 사람이 같은지 확인 
	@PostMapping("delete")
	public String delete(Long bno, String writer, Criteria cri, RedirectAttributes rttr) {
		// 삭제 처리 
		if(service.delete(bno)) {
			log.info("*********** 삭제 성공!!!!!!! ************");
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}
	
	// 글 등록 폼 
	@GetMapping("write")
	@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능하게 
	public void write() {
	}
	// 글 등록 처리 
	@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능하게 
	@PostMapping("write")
	public String writeBoard(BoardVO board, RedirectAttributes rttr) {
		log.info("write 처리 : " + board);
		
		service.register(board);
		// RedirectAttributes : Model처럼 스프링MVC가 자동으로 전달해주는 객체 
		// addFlashAttribute(key, value) : url뒤에 데이터가 붙지 않고, 
		//		일회성 데이터로 페이지를 새로고침하면 데이터 날라감.
		//		값 1개만 전달가능, 2개이상은 데이터가 소멸하므로 Map 이용해 한번에 보내야함.
		// addAttribute(key, value)
		// 위와 같은 메서드를 이용하여 리다이렉트되는 jsp 페이지에 데이터 전달할수 있다. 
		
		rttr.addFlashAttribute("result", board.getBno());
		// 등록처리후 글 고유번호 속성으로 추가해서 전달 (list에서 사용) 
		
		return "redirect:/board/list"; 
	}
	
	
	@GetMapping("test")
	public void test() {
		
	}

	@GetMapping("form")
	public void form() {
		
	}

	// 이미지 업로드
    @RequestMapping(value="food/imageUpload.do", method = RequestMethod.POST)
    public void imageUpload(HttpServletRequest request,
    		HttpServletResponse response, MultipartHttpServletRequest multiFile
    		, @RequestParam MultipartFile upload) throws Exception{
    	// 랜덤 문자 생성
    	UUID uid = UUID.randomUUID();
    	
    	OutputStream out = null;
    	PrintWriter printWriter = null;
    	
    	//인코딩
    	response.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	try{
    		//파일 이름 가져오기
    		String fileName = upload.getOriginalFilename();
    		byte[] bytes = upload.getBytes();
    		
    		//이미지 경로 생성
    		String path = "C:\\Users\\wowo1\\Pictures\\Saved Pictures" + "ckImage/";	// 이미지 경로 설정(폴더 자동 생성)
    		String ckUploadPath = path + uid + "_" + fileName;
    		File folder = new File(path);
    		System.out.println("path:"+path);	// 이미지 저장경로 console에 확인
    		//해당 디렉토리 확인
    		if(!folder.exists()){
    			try{
    				folder.mkdirs(); // 폴더 생성
    		}catch(Exception e){
    			e.getStackTrace();
    		}
    	}
    	
    	out = new FileOutputStream(new File(ckUploadPath));
    	out.write(bytes);
    	out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화
    	
    	String callback = request.getParameter("CKEditorFuncNum");
    	printWriter = response.getWriter();
    	String fileUrl = "/food/ckImgSubmit.do?uid=" + uid + "&fileName=" + fileName; // 작성화면
    	
    	// 업로드시 메시지 출력
    	printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
    	printWriter.flush();
    	
    	}catch(IOException e){
    		e.printStackTrace();
    	} finally {
    		try {
    		if(out != null) { out.close(); }
    		if(printWriter != null) { printWriter.close(); }
    	} catch(IOException e) { e.printStackTrace(); }
    	}
    	return;
    }
	
	
 // 서버로 전송된 이미지 뿌려주기
    @RequestMapping(value="/food/ckImgSubmit.do")
    public void ckSubmit(@RequestParam(value="uid") String uid
    		, @RequestParam(value="fileName") String fileName
    		, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    	
    	//서버에 저장된 이미지 경로
    	String path = "C:\\Users\\wowo1\\Pictures\\Saved Pictures" + "ckImage/";	// 저장된 이미지 경로
    	System.out.println("path:"+path);
    	String sDirPath = path + uid + "_" + fileName;
    	
    	File imgFile = new File(sDirPath);
    	
    	//사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
    	if(imgFile.isFile()){
    		byte[] buf = new byte[1024];
    		int readByte = 0;
    		int length = 0;
    		byte[] imgBuf = null;
    		
    		FileInputStream fileInputStream = null;
    		ByteArrayOutputStream outputStream = null;
    		ServletOutputStream out = null;
    		
    		try{
    			fileInputStream = new FileInputStream(imgFile);
    			outputStream = new ByteArrayOutputStream();
    			out = response.getOutputStream();
    			
    			while((readByte = fileInputStream.read(buf)) != -1){
    				outputStream.write(buf, 0, readByte); 
    			}
    			
    			imgBuf = outputStream.toByteArray();
    			length = imgBuf.length;
    			out.write(imgBuf, 0, length);
    			out.flush();
    			
    		}catch(IOException e){
    			e.printStackTrace();
    		}finally {
    			outputStream.close();
    			fileInputStream.close();
    			out.close();
    			}
    		}
    }
	
	
	
	
	
	
	
	
	
	
	
	
}
