package parking;

import java.util.Random;
import java.util.Scanner;

public class ParkMenu {
	ParkDao dao = new ParkDao();
	static Scanner scn = new Scanner(System.in);
	static ParkProc proc = new ParkProc();
	static int menu = 0;
	boolean runUser = false;
	boolean runMng = false;
	boolean run = true;
	static boolean runSpc = true;
	static boolean runMm = true;
	int maxSpace = 20;

//	메인메뉴
	public void Menu() {
//		시작메뉴

		while (run) {
//			최대 주차 대수  
			int space = (maxSpace - dao.list().size());
//		    주차장 크기 설정
			int width = 10;
			int height = 2; 
			String[][] list = new String[height][width];

//			주차 공간
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					list[i][j] = "O";
				}
			}
//			최대 주차값
			int maxCars = Math.min(dao.list().size(), width * height);

//			주차된 차량의 위치 설정
			Random random = new Random();
			for (int count = 0; count < maxCars; count++) {
				int row, col;
				do {
					row = random.nextInt(height); 
					col = random.nextInt(width);
				} while (!list[row][col].equals("O")); 
				list[row][col] = "X"; //do while문을 이용해 이미 주차 배치된 공간일땐 재배치하도록
			}
			// 주차장 모양 출력
			System.out.println("------------------- 성내공영주차장 주차현황 ---------------------");
			if (space <= 0) {
				System.out.println("현재 모든 주차공간이 만차입니다.");
			} else {
				System.out.println("          주차공간은 총 20 자리 중 ' " + space + " ' 자리 남았습니다");
			}
			System.out.println("===========================================================");
			System.out.println("            A 구역           |            B 구역         ");
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					System.out.print("[" + list[i][j] + "]");
					if (j < width - 1) {
						// 가로 공백
						for (int k = 0; k < 3; k++) {
							System.out.print(" ");
						}
					}
				}
				System.out.println(" ");
				if (i < height - 1) {
					// 세로 공백 및 가운데 화살표 출력
					System.out.println();
					System.out.print("입구 "); // 왼쪽에 "입구" 출력
					for (int k = 0; k < width * 5 - 4; k++) {
						if (k == (width * 2 - 1) / 2) {
							System.out.print("->");
						} else if (k == (width * 4 - 1) / 2) {
							System.out.print("->");
						} else if (k == (width * 6 - 1) / 2) {
							System.out.print("->");
						} else if (k == (width * 8 - 1) / 2) {
							System.out.print("->");
						} else {
							System.out.print(" ");
						}
					}
					System.out.print("출구 ");
					System.out.println();
					System.out.println();
				}
			}
			System.out.println("            C 구역           |         장애인 주차구역     ");
			System.out.println("===========================================================");
			System.out.println("               환영합니다 성내동 공영 주차장입니다");
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
				System.out.println("고객 메뉴입니다.");
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
				System.out.println("관리자 메뉴입니다.");
				System.out.println("사용하실 메뉴를 선택하세요");
				System.out.println("1.차량목록보기  2.차량조회  3.출차 처리하기  4.주차정보 수정  5.관리자 전용메뉴  6.시작페이지로 돌아가기  7. 시스템종료");
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
						mngMenu();
						runMm = true;
						break;
					case 6:
						runMng = false;
						break;
					case 7:
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

	// 주차구역 선택 메뉴
	public static String smallMenu() {

		while (runSpc) {
			try {
				menu = Integer.parseInt(scn.nextLine());
				switch (menu) {
				case 1:
					return "A 구역";
				case 2:
					return "B 구역";
				case 3:
					return "C 구역";
				case 4:
					return "장애인 주차 구역";
				}
			} catch (Exception e) {
				System.out.println("올바른 메뉴번호를 입력해주세요");
				e.printStackTrace();
			}
		}
		return null;
	}

	// 관리자 페이지 선택 메뉴
	public static String mngMenu() {
		while (runMm) {
			System.out.println("관리자 메뉴입니다. 사용하실 기능을 선택하세요.");
			System.out.println("1. 관리자 ID 생성  2. 관리자 ID 제거  3. 관리자 ID 목록보기  4. 이전페이지로 돌아가기");
			try {
				menu = Integer.parseInt(scn.nextLine());
				switch (menu) {
				case 1:
					proc.mngAdd();
					break;
				case 2:
					proc.mngRv();
					break;
				case 3:
					proc.mngList();
					break;
				case 4:
					runMm = false;
					break;
				}
			} catch (Exception e) {
				System.out.println("올바른 메뉴번호를 입력해주세요");
				e.printStackTrace();
			}
		}
		return null;
	}

	// 시작칸 인터페이스

}
