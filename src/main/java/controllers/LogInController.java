/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import app.Controllers;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogInController implements utilities.ui.Controller {

    views.LogInView panel;

    JButton logInB;
    JTextField passF;
    JTextField tenF;

    public LogInController() {
        this.panel = new views.LogInView();

        logInB = panel.getLogInB();
        passF = panel.getPassF();
        tenF = panel.getTenF();

        logInB.addActionListener((ActionEvent e) -> logInBActionPerformed(e));
    }

    void logInBActionPerformed(ActionEvent evt) {
        System.out.println("Testing");
        if (passF.getText().equals("admin") && tenF.getText().equals("admin")) {
            Controllers.ChangePanel("mainView");
        } else {
            JOptionPane.showMessageDialog(panel, "Mật khẩu hoặc tên người dùng không hợp lệ.s");
        }
    }

    @Override
    public void resetState() {
        tenF.setText("");
        passF.setText("");
    }

    @Override
    public void setVisible(boolean aFlag) {
        panel.setVisible(aFlag);
        if (aFlag) {
            resetState();
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

}
