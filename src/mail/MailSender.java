package mail;

import java.util.Scanner;

public class MailSender {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);

		System.out.print("수신자메일: ");
		String to = scn.nextLine();
		System.out.print("제목: ");
		String title = scn.nextLine();
		System.out.print("내용: ");
		String content = scn.nextLine();

		StringBuilder sb = new StringBuilder();
		sb.append("<p>비밀번호 초기화 안내 메일</p>");
		sb.append("<p>");
		sb.append(content);
		sb.append("</p>");
		sb.append("<b>Good Bye!!</b>");
		content = sb.toString();

		SendMail mail = new SendMail();
		mail.sendMail(to, title, content);

		scn.close();
	}
}
