package service;

import java.util.List;

import dao.MemberDao;
import model.Member;

public class MemberService {
	
	//Dao 객체를 멤버로 가지면서 사용
	MemberDao memberDao;
	
	public MemberService() {
		// 생성자 => 메소드 이름이 클래스 이름과 같음
		// 객체가 생성될때 실행되는 코드
		// new MemberService() ==> 생성자 코드 실행
		memberDao = new MemberDao();
	}
	
	// 로그인 성공 : true 를 리턴함
	// 로그인 실패 : false 를 리턴함
	public boolean login(String id, String pw) {
		//1. 데이터베이스에서 입력받은 아이디와 동일한 회원정보를 조회
		Member member = memberDao.selectOne(id);
		
		if(member != null) {
			// member가 null 이 아니면 해당 아이디를 가진 회원정보는 있다.
			if(member.getPw().equals(pw)) {
				// 로그인 성공
				return true;
			} else {
				// 비밀번호가 틀림 => 로그인 실패
				return false;
			}
		} else {
			// member 가 null ==> 아이디가 존재하지 않음
			// 로그인 실패
			return false;
		}
	}
	
	// 회원 가입 성공 ==> true 리턴
	// 회원 가입 실패 ==> false 리턴
	public boolean join(Member member) {
		int insertResult = memberDao.insertMember(member);
		// 회원가입 성공하면 insertResult 에는 1이 들어가 있다.
		if(insertResult == 1) {
			// 성공
			return true;
		} 
		//실패
		return false;
	}
	
	// 회원 정보수정 성공 ==> true 리턴
	// 회원 정보수정 실패 ==> false 리턴
	public boolean modify(Member member) {
		int updateResult = memberDao.updateMember(member);
		
		boolean result = false; // 리턴해줄 값
		if(updateResult > 0) {
			// 회원정보 수정 성공
			// updateMember() 메소드의 실행 결과는 영향을 받은 행의 개수
			// 수정된 회원 정보의 개수가 0개 보다 크다.
			result = true;
		}
		
		return result;
	}
	
	// 회원 정보 수정을 위해
	public Member getMember(String id) {
		return memberDao.selectOne(id);
	}
	
	// 회원 목록 보여주기 위해
	public List<Member> getAllMembers(){
		return memberDao.selectAll();
	}
	
	// 이메일이 존재하면 String에 email이 있을것
	// 이메일이 존재하지 않으면 null 이 반환될 것
	public String getEmail(String email) {
		return memberDao.selectOneByEmail(email);
	}
}
