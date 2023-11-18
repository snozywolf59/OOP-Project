package com.dictionary.Controllers.Welcome;

import com.dictionary.App;
import com.dictionary.Controllers.HandleInput;
import com.dictionary.Controllers.User;
import com.dictionary.Models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class WelcomeController implements Initializable {
    private String errorLogin = ""; // Sửa thành label

    private String auCode;

    @FXML
    private MediaView mediaView;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField rePassword;

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
    private Label errorUser;

    @FXML
    private Label errorOTP;


    static {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        password.setFocusTraversable(false);
        username.setFocusTraversable(false);
        HandleInput.disable(registerPane);
        registerPane.setVisible(false);
    }
    @FXML
    public void untarget() {
        pane.requestFocus();
    }

    @FXML
    public void login() throws ExecutionException, InterruptedException {
        try {
            String userName = username.getText();
            String pass = password.getText();
            if (!User.exists(userName, pass)) {
                errorLogin = "Tên đăng nhập hoặc mật khẩu không đúng."; // Sửa thành label
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
        HandleInput.disablePane(pane);
        HandleInput.normalize(registerPane);
        HandleInput.normalizePane(registerPane);
    }

    @FXML
    void confirmGmail(ActionEvent event) {
        if (gmailAddress.getText().isEmpty()) {
            errorUser.setText("Lỗi gmail.");
            return;
        }
        GmailOTP gmailOTP = new GmailOTP(gmailAddress.getText());
        auCode = gmailOTP.getAuthenticationCode();
        System.out.println(auCode);
    }

    @FXML
    public void createAccount() {
        if (!createAccountInSignUp()) {
            return;
        }
        closeRegister();
    }

    private boolean createAccountInSignUp() {
        try {
            if (name.getText().isEmpty()) {
                errorUser.setText("Chưa nhập họ và tên.");
                return false;
            }
            if (userNameSignUp.getText().isEmpty()) {
                errorUser.setText("Chưa nhập tên đăng nhập.");
                return false;
            }
            if (passwordSignUp.getText().isEmpty()) {
                errorUser.setText("Chưa nhập mật khẩu.");
                return false;
            }
            if (rePasswordSignUp.getText().isEmpty()) {
                errorUser.setText("Chưa nhập nhập lại mật khẩu.");
                return false;
            }
            if (dateOfBirth.getValue() == null) {
                errorUser.setText("Chưa nhập ngày sinh.");
                return false;
            }
            if (gmailAddress.getText().isEmpty()) {
                errorUser.setText("Chưa nhập địa chỉ gmail.");
                return false;
            }
            if (!rePasswordSignUp.getText().equals(passwordSignUp.getText())) {
                errorUser.setText("Nhập lại mật khẩu không đúng.");
                return false;
            }
            if (User.exists(userNameSignUp.getText(), passwordSignUp.getText())) {
                errorUser.setText("Tài khoản đã tồn tại.");
                return false;
            }
            if (!auCode.equals(MD5.md5HashString(otpCode.getText()))) {
                errorOTP.setText("Mã otp không đúng");
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
        HandleInput.normalize(pane);
        HandleInput.normalizePane(pane);
        HandleInput.disablePane(registerPane);
        HandleInput.disable(registerPane);
    }
}
