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
		// ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		/*
		 * ��û�� ������ �ؾ��� ��
		 * 1. ����ε� ��û���� Ȯ�� ( ���� ���ε� ��û�� �´��� )
		 * 2. ���� �̸��� DB�� ����
		 * 3. ���޹��� �����͸� ���Ϸ� ����
		 */
		
		// 1. ����ε� ��û���� Ȯ�� (multipart Ÿ�� ��û���� Ȯ��)
		String contentType = request.getContentType();
		System.out.println("ctype : " + contentType);
		
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			// "multipart/" �� �����ϴ� ���ڿ��ΰ�?? ==> ���� ���ε� ��û�� �´�.
			// ���� �̸��� db�� �����ϱ�
			saveFile(request, response);
		}
		
	}
	
	private void saveFile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// ���� ���ε� ��û�ÿ� ���ϵ��� ��Ʈ��� �и��Ǽ� ������ �ȴ�.
		// �� �и��� ��Ʈ���� ��� ��Ƽ� ���Ϸ� ����� ������ �ʿ�
		
		// �����̸��� �ߺ��Ǽ� ���� ��� �����ͺ��̽��� ������ ���� ����
		// ���ε�Ǵ� ���� �̸��� �ߺ����� �ʵ��� ó���� �ؾ��Ѵ�.
		// UUID => ��Ʈ��ũ �󿡼� �������� ����Ǵ� id
		
		Collection<Part> parts = req.getParts();
		for(Part part : parts) {
			if(part.getHeader("Content-Disposition").contains("filename=")) {
				// ����� �˻��ؼ� file ������ ���Ե� ��Ʈ �и�
				
				String fileName = part.getSubmittedFileName();
				// ���� �̸� ��������
				System.out.println(fileName);
				UUID uuid = UUID.randomUUID(); // ���Ƿ� UUID�� ����
				
				// ������ ���� �̸� ����� (�ߺ� ���� ó��)
				String saveName = uuid.toString() + "_" + fileName;
				
				if(part.getSize() > 0) {
					// ������ ����
					part.write(saveName);
					// ���� �̸��� DB�� ����
					service.uploadFile(saveName);
				}
			}
		}
		
	}

}
