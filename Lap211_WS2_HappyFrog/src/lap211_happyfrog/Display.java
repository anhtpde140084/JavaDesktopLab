/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lap211_happyfrog;

import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


/**
 *
 * @author tranp
 */
public class Display extends JPanel{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        try {
            Main.frogFly.repaint(g);
        } catch (IOException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
