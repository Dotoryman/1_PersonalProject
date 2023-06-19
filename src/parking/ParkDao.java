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

	// 0. close 클래스 
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

	// 5. 차량 중복체크
	public boolean carCk(String car_no) {
		sql = "select * from tbl_parking where car_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, car_no);

			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	// 사용자용 끝

	// 관리자용 추가목록

	// 6. 입고된 전체차량 목록
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

	// 7. 차량정보 수정 check
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

	// 8. 관리자 계정 추가
	public boolean mngAdd(ParkVO park) {
		{
			sql = "insert into tbl_manager (user_id, user_pw, user_name) " + "values(?, ?, ?)";
			conn = Dao.getConnect();
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, park.getUserId());
				psmt.setString(2, park.getUserPw());
				psmt.setString(3, park.getUserName());

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
	}

	// 9. 관리자 계정 중복체크
	public boolean mngCk(String user_id) {
		sql = "select * from tbl_manager where user_id = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user_id);

			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 10. 관리자 계정 삭제
	public boolean mngRv(String user_id) {

		sql = "delete from tbl_manager" + " where user_id = ?";

		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user_id);
			rs = psmt.executeQuery();

			int r = psmt.executeUpdate();
			if (r > 0) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return true;
	}

	// 11. 관리자 계정 목록보기
	public List<ParkVO> mngList() {
		List<ParkVO> mnglist = new ArrayList<>();

		sql = "select * from tbl_manager";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				ParkVO park = new ParkVO();

				park.setUserId(rs.getString("user_id"));
				park.setUserPw(rs.getString("user_pw"));
				park.setUserName(rs.getString("user_name"));

				mnglist.add(park);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return mnglist;
	}
}
