package service;

import java.util.List;

import dao.FileDao;

public class FileService {
	
	FileDao dao;

	public FileService() {
		dao = FileDao.getInstance();
	}
	
	//���� ���ε� ( ���� ������ ���� )
	public boolean uploadFile(String fileName) {
		int result = dao.insertFile(fileName);
		
		if(result > 0) {
			// ����� 0���� ũ�� ���� ����!
			return true;
		}
		return false;
	}
	
	//���� �̸� ��� ��������
	public List<String> getAllFileNames() {
		return dao.selectAllFiles();
	}

}
