package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDao {
	
	// ���ο� ������� ����ϴ� ���
	// �̱��� ����
	// ��ü�� 1���� �����ϴ� ���
	// �޸� ���鿡�� ���� ���� ����. 
	// new FileDao() ===> �Ҷ����� ��ü ���� , ��ü ������ ��ŭ �޸𸮸� �����ϰ� �ȴ�.
	// �����Ҷ����� �޸𸮸� �Ҵ��ϴ� ������ ���� 1���� ����, �ӵ����������� ������ �ִ�.
	// Ŭ�������� ������ ������ ����.
	// ��ü�� 1���� �����. �츮�� ����ϴ� ��� fileDao�� ���ʿ� ������ fileDao�� ����.
	// �츮�� ����ϴ� fileDao �� ��� �ʵ�(����)�� �� ��𼭵� ����Ҽ� �ִ�.
	// �޼ҵ� �Ȱ��� �� ���� ����Ҽ� �ִ�. ======> ���� �ν��Ͻ�
	
	private FileDao() {
		// �����ڴ� private Ÿ��
		// �� Ŭ���� �ۿ����� new �����ڸ� ���� fileDao��ü�� �����Ҽ��� ���� �ȴ�.
	}
	
	// �츮�� 1���� ������ FileDao ��ü 
	private static FileDao instance;
	
	public static FileDao getInstance() {
		if(instance == null) {
			// ���� �츮�� ������ FileDao ��ü�� null �̴� ?
			// �̱��� ������ ��ü�� 1�� ������� �ߴµ� 
			// �� 1���� null �̸� 0���� �ٸ� ���� �Ǵ°�.
			// �׷��� new �����ڷ� ���� �������ش�.
			instance = new FileDao();
		}
		
		return instance;
	}
	
	
	// ���� ���� ���
	public int insertFile(String filename) {
		String sql = "insert into uploadfile values(? ,sysdate)";
		int result = 0; // ���� ����? ����? ���
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. ���� �����ֱ�
			conn = ConnectionProvider.getConnection();
			// 2. sql ������ pstmt ���� ���ؼ� ��������
			pstmt = conn.prepareStatement(sql);
			// 3. ? �ڸ� ä���ֱ�
			pstmt.setString(1, filename);
			// 4. sql �� �����Ű�� ��� result�� ����
			result = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!= null)
					conn.close();
				if(pstmt!=null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// �����ߴ� sql ���� ����� return
		return result;
	}
	
	
	// ���ϸ�� ��ü ��ȸ ��� 
	// model �ȸ��� ���� (���� ��ü)
	// ���� �̸��� �����ɴϴ�.
	public List<String> selectAllFiles() {
		String sql = "select * from uploadfile";
		// �츮�� return ���� �����
		ResultSet rs = null;
		// �츮�� �ܺο��� �� �޼ҵ带 �ҷ����� �� �����
		List<String> result = new ArrayList<String>();
		// � �޼ҵ��� ���� Ÿ���� List Ÿ�� ==> �� Ÿ���� ������
		// �����е��� ����ϰ� ���� ����Ʈ ������ �ٲ㼭 ���� �ֵ��� �ϴ°�
		// ArrayList, LinkedList, MyList (��� List �������̽��� ���� �� Ŭ������ ����)
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("filename"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conn!= null)
					conn.close();
				if(pstmt!=null)
					pstmt.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
}
