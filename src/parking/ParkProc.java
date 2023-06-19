package parking;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ParkProc {
	private ParkDao dao = new ParkDao();
	private ParkVO park = new ParkVO();
	private Scanner scn = new Scanner(System.in);

	public void carIn() {
		System.out.printf("입차하는 차량의 차량번호를 입력하세요");
		String no = scn.nextLine();

		if (dao.carCk(no)) {
			System.out.println("이미 입고된 차량입니다.");
			return;
		}

		System.out.println("주차하실 구역을 선택하세요. \n1. A 구역 \n2. B 구역 \n3. C 구역 \n4. 장애인 전용 주차구역");
		String sp = ParkMenu.smallMenu();
		System.out.printf("특이사항이 있으시다면 입력하세요");
		String ex = scn.nextLine();

		park.setCarNo(no);
		park.setCarSp(sp);
		park.setCarEx(ex);

		if (dao.add(park)) {
			System.out.println("주차등록이 정상적으로 완료되었습니다");
		} else {
			System.out.println("주차등록에 실패하였습니다");
		}
	}

	public void carSearch() {
		System.out.printf("조회하려는 차량번호를 입력하세요");
		String no = scn.nextLine();
		ParkVO park = dao.search(no);
		if (park == null) {
			System.out.println("해당 번호의 차량은 입차기록이 없습니다.");
		} else {
			System.out.println(park.toEx());
		}
	}

	public void carOut() {
		System.out.printf("출차하려는 차량 번호를 입력하세요");
		String no = scn.nextLine();
		ParkVO p = dao.search(no);

		if (dao.remove(no)) {
			long sec = (System.currentTimeMillis() - p.getInTime().getTime()) / (1000);
			long min = (sec / 60) % 60;
			long hour = sec / (60 * 60);
			long rate = hour * 6000 + min * 100;
			DecimalFormat df = new DecimalFormat("###,###");
			String money = df.format(rate);
			boolean runRcp = true;
			int num = 0;

			System.out.println("' " + p.getCarNo() + " ' 번 차량의 주차시간은 ' " + hour + " 시간 " + min + " ' 분이며,\n"
					+ "주차 요금은 ' " + money + " ' 원입니다.");

			while (runRcp) {
				System.out.println("영수증을 받으시겠습니까?\n1. 네       2. 아니요");
				try {
					num = Integer.parseInt(scn.nextLine());
					switch (num) {
					case 1:
						Scanner scn = new Scanner(System.in);
						String title = "성내 공영주차장 결제 영수증 입니다.";
						System.out.print("영수증을 받으실 메일을 입력하세요 : ");
						String to = scn.nextLine();
						String content = "' " + p.getCarNo() + " ' 번 차량의 주차시간은 ' " + hour + " 시간 " + min + " ' 분이며,\n"
								+ "주차 요금은 ' " + money + " ' 원입니다.";

						StringBuilder sb = new StringBuilder();
						sb.append("<p>성내 공영주차장 결제 영수증 입니다.</p>");
						sb.append("<p>");
						sb.append(content);
						sb.append("</p>");
						sb.append("<b>-- 이용에 감사드립니다. --</b>");

						SendMail mail = new SendMail();
						mail.sendMail(to, title, content);
						runRcp = false;
						break;
					case 2:
						runRcp = false;
						break;

					}
				} catch (Exception e) {
					System.out.println("올바른 메뉴번호를 입력해주세요");
					e.printStackTrace();
				}
			}

			System.out.println("출차처리가 완료되었습니다. 안녕히 가십시오");
		} else {
			System.out.println("해당 번호의 차량은 입차기록이 없습니다.");
		}
	}

	public void carList() {

		List<ParkVO> list = dao.list();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(new Date());
		System.out.println("---------------------------현재시간은 " + str + " 입니다.---------------------------");
		System.out.println("---------------------------------------주차차량 목록---------------------------------------");
		System.out.println("");
		if (list.size() == 0) {
			System.out.println("주차장이 텅텅 비었습니다");
		} else {
			for (ParkVO board : list) {
				System.out.println(board.toString());
			}
		}
		System.out.println("");
	}

	public void carModi() {
		System.out.printf("정보를 수정하려는 차량번호를 입력하세요");
		String no = scn.nextLine();
		System.out.println("수정하실 구역을 선택하세요. \n1. A 구역 \n2. B 구역 \n3. C 구역 \n4. 장애인 전용 주차구역");
		String sp = ParkMenu.smallMenu();
		System.out.printf("선택하신 차량의 수정할 특이사항을 입력하세요");
		String ex = scn.nextLine();

		ParkVO park = new ParkVO();
		park.setCarNo(no);
		park.setCarSp(sp);
		park.setCarEx(ex);

		if (dao.modify(park)) {
			System.out.println("수정이 정상적으로 완료되었습니다");
		} else {
			System.out.println("해당 번호의 차량은 입차기록이 없습니다.");
		}
	}

	public void mngAdd() {
		System.out.printf("추가할 관리자 아이디를 입력하세요");
		String id = scn.nextLine();

		if (dao.mngCk(id)) {
			System.out.println("이미 존재하는 아이디입니다.");
			return;
		}

		System.out.println("비밀번호를 설정하세요");
		String pw = scn.nextLine();
		System.out.printf("관리자 이름을 입력하세요");
		String na = scn.nextLine();

		park.setUserId(id);
		park.setUserPw(pw);
		park.setUserName(na);

		if (dao.mngAdd(park)) {
			System.out.println("관리자 등록이 정상적으로 완료되었습니다");
		} else {
			System.out.println("관리자 등록에 실패하였습니다");
		}
	}

	public void mngRv() {
		System.out.printf("삭제하려는 관리자 ID를 입력하세요");
		String id = scn.nextLine();

		if (dao.mngRv(id)) {
			System.out.println("삭제가 완료되었습니다.");
		} else {
			System.out.println("존재하지 않는 ID 입니다.");
		}
	}

	public void mngList() {

		List<ParkVO> list = dao.mngList();
		System.out.println("---------------------------------------관리자 목록---------------------------------------");
		System.out.println("");
		if (list.size() == 0) {
			System.out.println("등록된 관리자가 없습니다.");
		} else {
			for (ParkVO board : list) {
				System.out.println(board.mngEx());
			}
		}
		System.out.println("");
	}

}
