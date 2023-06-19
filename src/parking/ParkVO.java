package parking;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ParkVO {
	private String carIncnt; // 입고순서
	private String carNo; // 차량번호
	private String carSp; // 입차위치
	private Date inTime; // 입차시간
	private String carEx; // 특이사항
	private String outTime; // 출차시간
	private String userId;
	private String userPw;
	private String userName;


	// 차량 조회
	public String toEx() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(new Date());
		long sec = (System.currentTimeMillis() - getInTime().getTime()) / (1000);
		long min = (sec / 60) % 60;
		long hour = sec / (60 * 60);
		long rate = hour * 6000 + min * 100;
		DecimalFormat df = new DecimalFormat("###,###");
		String money = df.format(rate);

		return "'" + this.carNo + "' 차량의 위치는 '" + this.carSp + "'입니다.\n" + "차량 입고시간은  : " + sdf.format(this.inTime)
				+ "\n현재 조회시간은  : " + str + " 입니다\n" + "총 주차시간은 ' " + hour + " 시간 " + min + " 분 ' 입니다.\n" + "현재 요금은 ' "
				+ money + " 원 '"+ "이며\n" + "남기신 메모사항은 ' " + this.carEx + " '입니다";
	}

	
	// 관리자 차량 일괄 조회
	@Override
	public String toString() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long sec = (System.currentTimeMillis() - getInTime().getTime()) / (1000);
		long min = (sec / 60) % 60;
		long hour = sec / (60 * 60);
		long rate = hour * 6000 + min * 100;
		DecimalFormat df = new DecimalFormat("###,###");
		String money = df.format(rate);
		
		return "입고순서: " + this.carIncnt + " | 차량번호: " + this.carNo +" | 차량위치: " + this.carSp + " | 입차시간: "+ sdf.format(this.inTime) + " | 현재 요금: " + money + "원" + " | 특이사항: " + this.carEx ;
	}
	
	
	// 관리자 ID 조회
	public String mngEx() {
		return "관리자 ID: " + this.userId + " | 관리자 PW: "  + this.userPw + " | 관리자 이름: " + this.userName;
	}

}
