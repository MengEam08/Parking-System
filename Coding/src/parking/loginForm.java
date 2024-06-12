package parking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class loginForm extends JDialog {
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfEmail;
    private JButton btnLogIn;
    private JPanel loginPanel;
    private JPasswordField pfPassword;
    private JPasswordField pfConform;

    public loginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(600, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        btnLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(loginPanel, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                String firstName = tfFirstName.getText();
//                String lastName = tfLastName.getText();
//                String email = tfEmail.getText();
//                String password = String.valueOf(pfPassword.getPassword());
//                String confirmPassword = String.valueOf(pfConform.getPassword());
//
//                System.out.println("First Name: " + firstName);
//                System.out.println("Last Name: " + lastName);
//                System.out.println("Email: " + email);
//                System.out.println("Password: " + password);
//                System.out.println("Confirm Password: " + confirmPassword);
//
//                if (isAuthenticated(firstName, lastName, email, password, confirmPassword)) {
//                    JOptionPane.showMessageDialog(loginForm.this, "Login Successful!",
//                            "Success", JOptionPane.INFORMATION_MESSAGE);
//                    dispose();
//                } else {
//                    JOptionPane.showMessageDialog(loginForm.this, "Email or Password invalid",
//                            "Try again", JOptionPane.ERROR_MESSAGE);
//                }
            }
        });

    }

//    private boolean isAuthenticated(String firstName, String lastName, String email, String password, String confirmPassword) {
//        return firstName.equals("seng") &&
//                lastName.equals("mengeam") &&
//                email.equals("meng22@gmail.com") &&
//                password.equals("123456") &&
//                confirmPassword.equals("123456");
//    }

    public static void main(String[] args) {
        loginForm frame = new loginForm(null);
    }
}
