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
		while(dao.equals(park)) {
			System.out.println("이미 입차한 차량입니다.");
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
			
			System.out.println(
					"' " + p.getCarNo() + " ' 번 차량의 주차시간은 ' " + hour + " 시간 " + min + " ' 분이며,\n" + "주차 요금은 ' " + money + " ' 원입니다.");
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

}
