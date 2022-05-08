package servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import service.FileService;

public class UploadServlet extends HttpServlet {
	
	FileService service;
	
    public UploadServlet() {
        service = new FileService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		/*
		 * 요청이 들어오면 해야할 일
		 * 1. 제대로된 요청인지 확인 ( 파일 업로드 요청이 맞는지 )
		 * 2. 파일 이름을 DB에 저장
		 * 3. 전달받은 데이터를 파일로 저장
		 */
		
		// 1. 제대로된 요청인지 확인 (multipart 타입 요청인지 확인)
		String contentType = request.getContentType();
		System.out.println("ctype : " + contentType);
		
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			// "multipart/" 로 시작하는 문자열인가?? ==> 파일 업로드 요청이 맞다.
			// 파일 이름을 db에 저장하기
			saveFile(request, response);
		}
		
	}
	
	private void saveFile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 파일 업로드 요청시에 파일들은 파트들로 분리되서 전송이 된다.
		// 그 분리된 파트들을 모두 모아서 파일로 만드는 과정이 필요
		
		// 파일이름이 중복되서 오는 경우 데이터베이스에 저장이 되지 않음
		// 업로드되는 파일 이름이 중복되지 않도록 처리를 해야한다.
		// UUID => 네트워크 상에서 고유성이 보장되는 id
		
		Collection<Part> parts = req.getParts();
		for(Part part : parts) {
			if(part.getHeader("Content-Disposition").contains("filename=")) {
				// 헤더를 검사해서 file 정보가 포함된 파트 분리
				
				String fileName = part.getSubmittedFileName();
				// 파일 이름 가져오기
				System.out.println(fileName);
				UUID uuid = UUID.randomUUID(); // 임의로 UUID를 생성
				
				// 저장할 파일 이름 만들기 (중복 방지 처리)
				String saveName = uuid.toString() + "_" + fileName;
				
				if(part.getSize() > 0) {
					// 파일을 저장
					part.write(saveName);
					// 파일 이름을 DB에 저장
					service.uploadFile(saveName);
				}
			}
		}
		
	}

}
