package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDao {
	
	// 새로운 방식으로 사용하는 방법
	// 싱글톤 패턴
	// 객체를 1개만 유지하는 방법
	// 메모리 측면에서 아주 이점 많다. 
	// new FileDao() ===> 할때마다 객체 생성 , 객체 생성된 만큼 메모리를 차지하게 된다.
	// 생성할때마다 메모리를 할당하는 과정도 최초 1번만 진행, 속도관점에서도 이점이 있다.
	// 클래스간의 데이터 공유가 쉽다.
	// 객체를 1개만 만든다. 우리가 사용하는 모든 fileDao는 최초에 생성한 fileDao랑 같음.
	// 우리가 사용하는 fileDao 의 모든 필드(변수)는 다 어디서든 사용할수 있다.
	// 메소드 똑같이 다 같이 사용할수 있다. ======> 전역 인스턴스
	
	private FileDao() {
		// 생성자는 private 타입
		// 이 클래스 밖에서는 new 연산자를 통해 fileDao객체를 생성할수가 없게 된다.
	}
	
	// 우리가 1개만 유지할 FileDao 객체 
	private static FileDao instance;
	
	public static FileDao getInstance() {
		if(instance == null) {
			// 현재 우리가 유지할 FileDao 객체가 null 이다 ?
			// 싱글톤 패턴은 객체를 1개 유지라고 했는데 
			// 그 1개가 null 이면 0개나 다름 없게 되는것.
			// 그래서 new 연산자로 새로 생성해준다.
			instance = new FileDao();
		}
		
		return instance;
	}
	
	
	// 파일 삽입 기능
	public int insertFile(String filename) {
		String sql = "insert into uploadfile values(? ,sysdate)";
		int result = 0; // 삽입 실패? 성공? 결과
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 연결 시켜주기
			conn = ConnectionProvider.getConnection();
			// 2. sql 실행할 pstmt 연결 통해서 가져오기
			pstmt = conn.prepareStatement(sql);
			// 3. ? 자리 채워주기
			pstmt.setString(1, filename);
			// 4. sql 문 실행시키고 결과 result에 저장
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
		// 저장했던 sql 실행 결과를 return
		return result;
	}
	
	
	// 파일목록 전체 조회 기능 
	// model 안만든 상태 (파일 객체)
	// 파일 이름만 가져옵니다.
	public List<String> selectAllFiles() {
		String sql = "select * from uploadfile";
		// 우리가 return 해줄 결과물
		ResultSet rs = null;
		// 우리가 외부에서 이 메소드를 불렀을때 그 결과물
		List<String> result = new ArrayList<String>();
		// 어떤 메소드의 리턴 타입이 List 타입 ==> 그 타입을 가지고
		// 여러분들이 사용하고 싶은 리스트 종류로 바꿔서 쓸수 있도록 하는것
		// ArrayList, LinkedList, MyList (대신 List 인터페이스를 구현 한 클래스만 가능)
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
