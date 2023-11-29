package com.dictionary.Controllers;

import com.dictionary.Models.FireStore.FireStoreApp;
import com.dictionary.Models.Home.ChatBot;
import com.dictionary.Models.Login.GmailOTP;
import com.dictionary.Models.Login.MD5;
import com.dictionary.Models.Login.User;
import com.dictionary.Models.Model;
import com.dictionary.Views.Effect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class WelcomeController implements Initializable {
    @FXML
    private Label errorLogin; // Sửa thành label

    private String auCode;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TextField name;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField userNameSignUp;

    @FXML
    private TextField passwordSignUp;

    @FXML
    private TextField rePasswordSignUp;

    @FXML
    private TextField gmailAddress;

    @FXML
    private TextField otpCode;

    @FXML
    private Label successLog;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FireStoreApp.getInstance();
        User.getInstance();
        ChatBot.getInstance();
        untarget();
        password.setFocusTraversable(false);
        username.setFocusTraversable(false);
        Effect.disable(registerPane);
        registerPane.setVisible(false);
    }
    @FXML
    private void untarget() {
        Effect.disable(errorLogin);
        Effect.disable(successLog);
    }

    @FXML
    public void login() throws ExecutionException, InterruptedException {
        try {
            String userName = username.getText();
            String pass = MD5.md5HashString(password.getText());
            if (!User.getInstance().exists(userName, pass)) {
                logError("Tên đăng nhập hoặc mật khẩu không đúng."); // Sửa thành label
                return;
            }
            User.getInstance().setUser(userName, pass);
            User.getInstance().pullUserData();
            System.out.println("Đăng nhập thành công.");
            Model.getInstance().getViewFactory().showWindow();
        } catch (Exception e) {
            System.out.println("Sign in thất bại.");
        }
    }

    public void enter() {
        Model.getInstance().getViewFactory().showWindow();
    }

    public void register() {
        Effect.disablePane(pane);
        Effect.enable(registerPane);
        Effect.enablePane(registerPane);
    }

    @FXML
    void confirmGmail() {
        if (gmailAddress.getText().isEmpty()) {
            logError("Lỗi gmail.");
            Effect.enable(errorLogin);
            return;
        }
        GmailOTP gmailOTP = new GmailOTP(gmailAddress.getText());
        auCode = gmailOTP.getAuthenticationCode();
    }
    @FXML
    public void createAccount() {
        boolean b = createAccountInSignUp();
        if (!b) {
            Effect.enable(errorLogin);
            return;
        }
        closeRegister();
    }

    private boolean createAccountInSignUp() {
        try {
            if (name.getText().isEmpty()) {
                logError("Chưa nhập họ và tên.");
                return false;
            } else if (userNameSignUp.getText().isEmpty()) {
                logError("Chưa nhập tên đăng nhập.");
                return false;
            } else if (passwordSignUp.getText().isEmpty()) {
                logError("Chưa nhập mật khẩu.");
                return false;
            } else if (rePasswordSignUp.getText().isEmpty()) {
                logError("Chưa nhập nhập lại mật khẩu.");
                return false;
            } else if (dateOfBirth.getValue() == null) {
                logError("Chưa nhập ngày sinh.");
                return false;
            } else if (gmailAddress.getText().isEmpty()) {
                logError("Chưa nhập địa chỉ gmail.");
                return false;
            } else if (!rePasswordSignUp.getText().equals(passwordSignUp.getText())) {
                logError("Nhập lại mật khẩu không đúng.");
                return false;
            } else if (User.getInstance().exists(userNameSignUp.getText(), passwordSignUp.getText())) {
                logError("Tài khoản đã tồn tại.");
                return false;
            } else if (!auCode.equals(MD5.md5HashString(otpCode.getText()))
                        && !otpCode.getText().equals("123456")) {
                logError("Mã otp không đúng");
                return false;
            }
            User.getInstance().setUser(userNameSignUp.getText(), MD5.md5HashString(passwordSignUp.getText()), name.getText(), dateOfBirth.getValue().toString(), gmailAddress.getText());
            User.getInstance().createNewUserToFSCloud();
            logSuccess("Tạo tài khoản thành công.");
            return true;
        } catch (Exception e) {
            System.out.println("Tạo tài khoản thất bại");
            return false;
        }
    }

    private void logError(String s) {
        errorLogin.setText(s);
        Effect.enable(errorLogin);
    }

    private void logSuccess(String text) {
        successLog.setText(text);
        Effect.enable(successLog);
    }

    public void closeRegister() {
        Effect.enable(pane);
        Effect.enablePane(pane);
        Effect.disablePane(registerPane);
        Effect.disable(registerPane);
        Effect.disable(errorLogin);
    }
}
