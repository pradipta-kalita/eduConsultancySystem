package com.pol.user_service.utils;

public class EmailTemplates {
    private static final EmailTemplates INSTANCE = new EmailTemplates();

    private EmailTemplates(){}

    public static EmailTemplates getInstance(){
        return INSTANCE;
    }

    public String getWelcomeTemplate(String username) {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Welcome to Our Service</title>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 5px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
                    h2 { color: #4CAF50; }
                    p { line-height: 1.6; }
                    .button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #4CAF50; text-decoration: none; border-radius: 5px; }
                    .footer { margin-top: 20px; font-size: 12px; color: #888888; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Welcome, %s!</h2>
                    <p>We're excited to have you on board. Get started by clicking the button below to log in to your account:</p>
                    <p>If you have any questions or need help, feel free to reach out to our support team.</p>
                    <p>Thanks for joining us,<br>Your Company Team</p>
                    <div class="footer">
                        <p>&copy; 2024 Your Company. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(username);
    }

    public String getForgotPasswordTemplate(String username, String otp) {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Reset</title>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 5px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
                    h2 { color: #4CAF50; }
                    p { line-height: 1.6; }
                    .button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #4CAF50; text-decoration: none; border-radius: 5px; }
                    .footer { margin-top: 20px; font-size: 12px; color: #888888; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Password Reset Request</h2>
                    <p>Hi %s,</p>
                    <p>We received a request to reset your password. Your OTP is %s</p>
                    <p>If you didn't request a password reset, please ignore this email or contact support if you have any concerns.</p>
                    <p>Thanks,<br>Your Company Team</p>
                    <div class="footer">
                        <p>&copy; 2024 Your Company. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
        """.formatted(username,otp);
    }


}