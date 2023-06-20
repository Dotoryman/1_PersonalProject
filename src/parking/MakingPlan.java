package parking;

public class MakingPlan {
	
//	* 개발 개요
//	2022년 기준으로 우리나라의 등록 자동차 수는 2500만 대가 넘어섰다. 어림잡아 국민 2명중 1명은 차를 갖고 있는 숫자인데 
//	이에 반해 주차 시설은 발 빠르게 증가하는 차량의 수에 따라 확충되지 못했고 주차 문제를 야기하는 중이다.
//	
//	이와 더불어 무인·카드 주차시설 또는 주차정산 키오스크의 보급은 확대됨에 따라 주차관리 APP을 구상해보았다.
	 

//	주차장 입출고 프로그램 만들기

//	1. 안녕하세요 빵빵주차장입니다. 현재 잔여 주차자리는 ? 자리입니다. ++1,2,3,장애인 주차구역 구별할 수 있으면 해보기
//		-1. 주차장 이용자입니다.
//		-2. 주차장 관리자입니다.(로그인 Id, Pw는 관리자 테이블 새로만들기)
	
//	2. 사용자 계정과 관리자 계정 따로 구분, 
//	   사용자 계정의 경우  1. 입차하기 (차량번호(not null), 주차위치(not null), 입차시간(db에서 들어가게), 특이사항 )
//	   				  2. 내 차량조회(1대) , 조회의 경우차량번호로 차량위치, 입차시간, 현재시간, 주차요금 출력되도록
//	   				  3. 출차하기,  차량위치, 입차시간, 현재시간, 주차요금 출력되도록
//					  4. 시작페이지로 돌아가기
	
//	   관리자 계정의 경우  1. 입고된 차량 목록
//	   				  2. 차량조회(1대) , 조회의 경우차량번호로 차량위치, 입차시간, 현재시간, 주차요금 출력되도록
//	   				  3. 출차하기,  차량위치, 입차시간, 현재시간, 주차요금 출력되도록
//	   				  4. 차량번호로 차량위치 수정, 입차시간 수정, 특이사항 수정
//					  5. 시작페이지로 돌아가기
//					  6. 시스템 종료


//	3. 차량 위치의 경우 (주차하실 구역을 선택하세요 1, 2, 3, 4) 1구역, 2구역, 3구역과 장애인구역 구분 할수있으면 해보기
//	4. 입차,출차에 따라 주차구역의 변화를 콘솔안에서 인터페이스화 해보기
//	5. 구역별 차량수 지정하고 만차, 여유대수 표시해보기
//	6. 출차하면 전자영수증 메일발송
//	7. 관리자 아이디 추가/삭제
//	8. 차량번호 중복시 다시입력시키기
	
	
	
	
	
	
	
	
//
//
//
//	create user proj identified by proj;
//	grant connect, resource to proj;
//
//
//
//	select * from tab;
//
//	create table tbl_parking (
//	car_incnt number,
//	car_no varchar2(200) primary key,
//	car_sp varchar2(200),
//	car_ex varchar2(400),
//	car_intime date default sysdate
//	car_outtime date
//	);
//	create sequence board_seq;
//
//	select * from tbl_parking;
//
//	alter table parking rename to tbl_parking;
//
//	create table tbl_manager (
//	user_id varchar2(100) primary key,
//	user_pw varchar2(100) not null,
//	user_name varchar2(100) not null
//	);
//
//	insert into tbl_manager
//	values ('master', '1111','test');
//	commit;
}
