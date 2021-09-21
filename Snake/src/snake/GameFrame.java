
package snake;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

class GameFrame extends JFrame{
    ImageIcon icon = new ImageIcon("se.jpg");
    
    public GameFrame() {
        add(new Game());
        setTitle("Snake");
        pack();
        setIconImage(icon.getImage());
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        }
    
}
