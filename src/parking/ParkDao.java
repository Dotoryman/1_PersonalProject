package parking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql;

	// 0. close 클래스 check
	private void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 1. 관리자 계정 로그인
	public boolean loginCheck(String id, String pw) {
		sql = "select * from tbl_manager where user_id=? and user_pw=?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				System.out.println("로그인에 성공했습니다");
				return true; // 아이디가 있다는 의미
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		System.out.println("아이디 또는 비밀번호가 틀렸습니다");
		return false;
	}
	
	// 2. 입차 check
	public boolean add(ParkVO park) {
		sql = "insert into tbl_parking (car_incnt, car_no, car_sp, car_ex) " + "values(board_seq.nextval,?, ?, ?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, park.getCarNo());
			psmt.setString(2, park.getCarSp());
			psmt.setString(3, park.getCarEx());

			int r = psmt.executeUpdate();
			if (r > 0) { // 수정사항 반영
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

 	// 3. 차량 1대 조회 check
	public ParkVO search(String no) {
		sql = "select * from tbl_parking where car_no = ?";
		conn = Dao.getConnect();

		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, no);
			rs = psmt.executeQuery();

			if (rs.next()) { // if 한건 조회가 된다면
				ParkVO park = new ParkVO();
				park.setCarIncnt(rs.getString("car_incnt"));
				park.setCarNo(rs.getString("car_no"));
				park.setCarSp(rs.getString("car_sp"));
				park.setCarEx(rs.getString("car_ex"));
				park.setInTime(rs.getDate("car_intime"));
				return park;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;

	}

	// 4. 출차 check
	public boolean remove(String no) {
		String sql1 = " update tbl_parking" + " set car_outtime = sysdate" + " where car_no = ?";
		sql = "delete from tbl_parking" + " where car_no = ?";

		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, no);
			rs = psmt.executeQuery();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, no);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 사용자용 끝

	// 관리자용 추가목록
	
	// 5. 입고된 전체차량 목록
	public List<ParkVO> list() {
		List<ParkVO> list = new ArrayList<>();

		sql = "select * from tbl_parking";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				ParkVO park = new ParkVO();
				park.setCarIncnt(rs.getString("car_incnt"));
				park.setCarNo(rs.getString("car_no"));
				park.setCarSp(rs.getString("car_sp"));
				park.setCarEx(rs.getString("car_ex"));
				park.setInTime(rs.getDate("car_intime"));

				list.add(park);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 6. 차량정보 수정 check
	public boolean modify(ParkVO park) {
		sql = "update tbl_parking " + "set car_sp = nvl(?, car_sp) " + ", car_ex = nvl(?, car_ex)"
				+ " where car_no = ?";

		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, park.getCarSp());
			psmt.setString(2, park.getCarEx());
			psmt.setString(3, park.getCarNo());

			int r = psmt.executeUpdate(); // 쿼리의 실행!
			if (r > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 7. 관리자계정 추가
}
