package com.dictionary.Controllers;

import com.dictionary.App;
import com.dictionary.Models.Login.GmailOTP;
import com.dictionary.Models.Login.MD5;
import com.dictionary.Models.Login.User;
import com.dictionary.Models.Model;
import com.dictionary.Views.Effect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Effect.disable(errorLogin);
        password.setFocusTraversable(false);
        username.setFocusTraversable(false);
        Effect.disable(registerPane);
        registerPane.setVisible(false);
    }
    @FXML
    private void untarget(MouseEvent event) {
        if (!Effect.mouseClickInside(errorLogin, event)) {
            Effect.disable(errorLogin);
        }
    }

    @FXML
    public void login() throws ExecutionException, InterruptedException {
        try {
            String userName = username.getText();
            String pass = password.getText();
            if (!User.exists(userName, pass)) {
                errorLogin.setText("Tên đăng nhập hoặc mật khẩu không đúng."); // Sửa thành label
                return;
            }
            App.user.setUser(userName, pass);
            App.user.pullUserData();
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
            errorLogin.setText("Lỗi gmail.");
            Effect.enable(errorLogin);
            return;
        }
        GmailOTP gmailOTP = new GmailOTP(gmailAddress.getText());
        auCode = gmailOTP.getAuthenticationCode();
        System.out.println(auCode);
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
                errorLogin.setText("Chưa nhập họ và tên.");
                return false;
            } else if (userNameSignUp.getText().isEmpty()) {
                errorLogin.setText("Chưa nhập tên đăng nhập.");
                return false;
            } else if (passwordSignUp.getText().isEmpty()) {
                errorLogin.setText("Chưa nhập mật khẩu.");
                return false;
            } else if (rePasswordSignUp.getText().isEmpty()) {
                errorLogin.setText("Chưa nhập nhập lại mật khẩu.");
                return false;
            } else if (dateOfBirth.getValue() == null) {
                errorLogin.setText("Chưa nhập ngày sinh.");
                return false;
            } else if (gmailAddress.getText().isEmpty()) {
                errorLogin.setText("Chưa nhập địa chỉ gmail.");
                return false;
            } else if (!rePasswordSignUp.getText().equals(passwordSignUp.getText())) {
                errorLogin.setText("Nhập lại mật khẩu không đúng.");
                return false;
            } else if (User.exists(userNameSignUp.getText(), passwordSignUp.getText())) {
                errorLogin.setText("Tài khoản đã tồn tại.");
                return false;
            } else if (!auCode.equals(MD5.md5HashString(otpCode.getText()))) {
                errorLogin.setText("Mã otp không đúng");
                return false;
            }
            App.user.setUser(userNameSignUp.getText(), passwordSignUp.getText(), name.getText(), dateOfBirth.toString(), gmailAddress.getText());
            App.user.createNewUserToFSCloud();
            System.out.println("Tạo tài khoản thành công.");
            return true;
        } catch (Exception e) {
            System.out.println("Tạo tài khoản thất bại");
            return false;
        }
    }

    public void closeRegister() {
        Effect.enable(pane);
        Effect.enablePane(pane);
        Effect.disablePane(registerPane);
        Effect.disable(registerPane);
    }
}
