package com.land.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("LandSoil System <clarisseuwimana31@gmail.com>");
            helper.setTo(toEmail);
            helper.setSubject("Your LandSoil Verification Code — " + otp);

            String html = """
                <!DOCTYPE html>
                <html>
                <body style="margin:0;padding:0;background:#f1f5f9;font-family:'Segoe UI',Arial,sans-serif;">
                  <table width="100%%" cellpadding="0" cellspacing="0">
                    <tr><td align="center" style="padding:40px 20px;">
                      <table width="520" cellpadding="0" cellspacing="0"
                             style="background:white;border-radius:16px;box-shadow:0 8px 30px rgba(0,0,0,0.08);overflow:hidden;">
                        <!-- Header -->
                        <tr>
                          <td style="background:linear-gradient(135deg,#10b981,#059669);
                                     padding:32px 40px;text-align:center;">
                            <div style="font-size:28px;">🌿</div>
                            <h1 style="color:white;margin:8px 0 0;font-size:22px;font-weight:700;letter-spacing:-0.5px;">
                              LandSoil System
                            </h1>
                          </td>
                        </tr>
                        <!-- Body -->
                        <tr>
                          <td style="padding:40px;">
                            <h2 style="color:#1e293b;font-size:20px;margin:0 0 12px;">
                              Verify your email address
                            </h2>
                            <p style="color:#64748b;font-size:15px;line-height:1.6;margin:0 0 28px;">
                              Use the code below to complete your account registration.
                              This code expires in <strong>10 minutes</strong>.
                            </p>
                            <!-- OTP Box -->
                            <div style="background:#f0fdf4;border:2px dashed #10b981;
                                        border-radius:12px;padding:28px;text-align:center;margin-bottom:28px;">
                              <div style="font-size:42px;font-weight:800;letter-spacing:14px;
                                          color:#059669;font-family:monospace;">
                                %s
                              </div>
                              <p style="color:#64748b;font-size:13px;margin:12px 0 0;">
                                One-Time Password (OTP)
                              </p>
                            </div>
                            <p style="color:#94a3b8;font-size:13px;line-height:1.6;margin:0;">
                              If you did not request this code, you can safely ignore this email.
                              Never share this code with anyone.
                            </p>
                          </td>
                        </tr>
                        <!-- Footer -->
                        <tr>
                          <td style="background:#f8fafc;padding:20px 40px;
                                     border-top:1px solid #e2e8f0;text-align:center;">
                            <p style="color:#94a3b8;font-size:12px;margin:0;">
                              © 2026 LandSoil Management System · All rights reserved
                            </p>
                          </td>
                        </tr>
                      </table>
                    </td></tr>
                  </table>
                </body>
                </html>
                """.formatted(otp);

            helper.setText(html, true); // true = isHtml
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage(), e);
        }
    }
}
