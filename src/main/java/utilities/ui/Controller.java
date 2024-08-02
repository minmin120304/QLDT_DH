/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utilities.ui;

import javax.swing.JPanel;

public interface Controller {
    void resetState();
    
    void setVisible(boolean aFlag);
    
    JPanel getPanel();
}
