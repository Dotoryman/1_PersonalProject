package mail;



import oracle.net.ns.Message;


public class SendMail {
	final String ENCODING = "UTF-8";
	final String PORT = "465";
	final String SMTPHOST = "smtp.naver.com";
	final String FROM = "네이버 이메일";
	final String PASS = "로그인 비밀번호";

	public Session setting() {
		Properties props = new Properties();
		Session session = null;
		try {
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTPHOST);
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.ssl.enable", true);
			props.put("mail.smtp.ssl.trust", SMTPHOST);
			props.put("mail.smtp.starttls.required", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.ssl.protocols", "TLSv1.2");

			props.put("mail.smtp.quit-wait", "false");
			props.put("mail.smtp.socketFactory.port", PORT);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(FROM, PASS);
				}
			});
		} catch (Exception e) {
			System.out.println("session fail");
		}

		return session;
	}

	public void sendMail(String to, String title, String content) {
		Message msg = new MimeMessage(setting());
		try {
			msg.setFrom(new InternetAddress(FROM, "Developer", ENCODING));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(title);
			msg.setContent(content, "text/html;charset=utf-8");
			Transport.send(msg);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("fail");
			e.printStackTrace();
		}
	}
}
