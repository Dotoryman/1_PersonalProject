package parking;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ParkVO {
	private String carIncnt; //입고순서
	private String carNo; // 차량번호
	private String carSp; // 입차위치
	private String inTime; // 입차시간
	private String carEx; // 특이사항
	private String outTime; // 출차시간
	private String price;
	

}
 class TimeExample {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String inTime = sdf.format(now);
}
