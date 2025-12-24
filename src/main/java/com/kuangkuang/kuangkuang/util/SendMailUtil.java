package com.kuangkuang.kuangkuang.util;

import com.google.common.base.Utf8;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
@Slf4j
public class SendMailUtil {

    public static void sendEmailCode(String targetEmail, String authCode) {
        try {
            HtmlEmail email = new HtmlEmail();

            email.setHostName("smtp.qq.com");
            email.setSmtpPort(465);
            email.setSSLOnConnect(true);
            email.setAuthentication("2860048531@qq.com", "swllommwcfnhdgec");
            email.setFrom("2860048531@qq.com", "哐哐");
            email.addTo(targetEmail);
            email.setSubject("注册验证码");
            email.setMsg("<p>您的验证码为:" + authCode + "(两分钟内有效)</p>");
            email.setCharset("UTF-8");


            email.getMailSession().getProperties().put("mail.smtp.ssl.protocols", "TLSv1.2");
            email.getMailSession().getProperties().put("mail.smtp.ssl.enabledProtocols", "TLSv1.2");
            email.getMailSession().getProperties().put("mail.smtp.ssl.ciphersuites",
                    "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");

            email.getMailSession().getProperties().put("mail.smtp.timeout", "10000");
            email.getMailSession().getProperties().put("mail.smtp.connectiontimeout", "10000");

            email.send();

        } catch (EmailException e) {
            e.printStackTrace();
            log.error("邮件发送失败: " + e.getMessage());
        }
    }
}