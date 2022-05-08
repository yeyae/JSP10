package service;

import java.util.List;

import dao.FileDao;

public class FileService {
	
	FileDao dao;

	public FileService() {
		dao = FileDao.getInstance();
	}
	
	//파일 업로드 ( 파일 데이터 삽입 )
	public boolean uploadFile(String fileName) {
		int result = dao.insertFile(fileName);
		
		if(result > 0) {
			// 결과가 0보다 크면 삽입 성공!
			return true;
		}
		return false;
	}
	
	//파일 이름 모두 가져오기
	public List<String> getAllFileNames() {
		return dao.selectAllFiles();
	}

}
