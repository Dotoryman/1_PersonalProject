package parking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ParkMain {
	public static void main(String[] args) {

		ParkDao dao = new ParkDao();
		Scanner scn = new Scanner(System.in);
		ParkProc proc = new ParkProc();
		int menu = 0;
		boolean runUser = false;
		boolean runMng = false;
		boolean run = true;
		int maxSpace = 30;

//		시작메뉴
		while (run) {
			
			int space = (maxSpace - dao.list().size());
			if(space <= 0) {
				System.out.println("현재 모든 주차공간이 만차입니다.");
			} else {
				System.out.println("주차공간은 " + space +" 자리 남았습니다.");
			}
			System.out.println("환영합니다 우당탕탕 주차장입니다.");
			System.out.println("사용자를 선택하세요. \n1. 주차장 이용자입니다. \n2. 주차장 관리자입니다.");
			int num = scn.nextInt();
			scn.nextLine();
			if (num == 1) {
				runUser = true;
			} else if (num == 2) {
				System.out.println("관리자 계정을 입력하세요");
				String id = scn.nextLine();
				System.out.println("비밀번호를 입력하세요");
				String pw = scn.nextLine();
				if (dao.loginCheck(id, pw)) {
					runMng = true;
				} else {
					run = true;
				}
			} else {
				System.out.println("올바른 메뉴번호를 입력해주세요");
			}

//			사용자 메뉴
			while (runUser) {
				System.out.println("유저입니다.");
				System.out.println("사용하실 메뉴를 선택하세요");
				System.out.println("1.입차하기  2.내차 찾기  3.출차하기  4.시작페이지로 돌아가기");
				try {
					menu = Integer.parseInt(scn.nextLine());
					switch (menu) {
					case 1:
						proc.carIn();
						break;
					case 2:
						proc.carSearch();
						break;
					case 3:
						proc.carOut();
						break;
					case 4:
						runUser = false;
						break;
					}
				} catch (Exception e) {
					System.out.println("올바른 메뉴번호를 입력해주세요");
					e.printStackTrace();
				}
			}

//			관리자 메뉴
			while (runMng) {
				System.out.println("관리자입니다.");
				System.out.println("사용하실 메뉴를 선택하세요");
				System.out.println("1.차량목록보기  2.차량조회  3.출차 처리하기  4.주차정보 수정 5.시작페이지로 돌아가기 6. 시스템종료");
				try {
					menu = Integer.parseInt(scn.nextLine());
					switch (menu) {
					case 1:
						proc.carList();
						break;
					case 2:
						proc.carSearch();
						break;
					case 3:
						proc.carOut();
						break;
					case 4:
						proc.carModi();
						break;
					case 5:
						runMng = false;
						break;
					case 6:
						runMng = false;
						run = false;
						break;

					}
				} catch (Exception e) {
					System.out.println("올바른 메뉴번호를 입력해주세요");
					e.printStackTrace();
				}
		
			}

		}
		System.out.println("시스템이 정상적으로 종료되었습니다.");
	}
}
