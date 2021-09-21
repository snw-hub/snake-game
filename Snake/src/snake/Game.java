/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 *
 * @author 20105620
 */
class Game  extends JPanel implements ActionListener{
 static final int SCREEN_WIDTH = 1280 ,SCREEN_HEIGHT = 645, UNIT_SIZE = 40,
                     GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
           final int x[] = new int[GAME_UNITS],
                     y[] = new int[GAME_UNITS];
           final int BadAppleX[] = new int[5],
                    BadAppleY[] = new int[10]; 
                 int AppleX,  AppleY,
                     bodyParts = 6,ApplesEaten = 0,
                     red = 40, green = 180, 
                     blue = 40, pink = 180,
                     delay = 0;
                 char direction = 'R';
                 boolean running = false, paused = false;
    Timer timer;
    Random random;
    
    public Game(){
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        
    }
   
    public void START(){
        NEW_APPLES();
        setBackground(Color.BLACK);
        running = true;
        delay = 85;
        timer = new Timer(delay, this);
        timer.start();
        
    }
    
    @Override
    public void paintComponent(Graphics Graphics){
        super.paintComponent(Graphics);
        DRAW(Graphics);
    }
    
    public void DRAW(Graphics Graphics){
        if(running){
                for(int i = 0; i < bodyParts; i++){
                     switch(i) {
                        case 1:
                          Graphics.setColor(Color.GREEN);
                          Graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                        break;
                            default:
//                                for (int c = 1; c < BadAppleX.length; c++) {
//                                if(x[0] == BadAppleX[c] && y[0] == BadAppleY[c]){
                                Graphics.setColor(Color.YELLOW);
                                Graphics.setColor(Color.ORANGE);
//                                Graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
//                                Graphics.setColor(new Color(red ,green ,0));
                                Graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                               break;
                    }
                  }
            
            //APPLES
            Graphics.setColor(Color.GREEN);
            Graphics.fillRect(AppleX, AppleY, UNIT_SIZE, UNIT_SIZE);
            
            //BAD APPLES
             for (int i = 1; i < BadAppleX.length; i++) {   
             switch (random.nextInt(4)) {
              case 4:
                 Graphics.setColor(Color.YELLOW);
                 Graphics.fillRect(BadAppleX[i], BadAppleY[i], UNIT_SIZE, UNIT_SIZE);
                break;
              case 3:
                 Graphics.setColor(Color.ORANGE);
                 Graphics.fillRect(BadAppleX[i], BadAppleY[i], UNIT_SIZE, UNIT_SIZE);
                break;
              case 2:
                 Graphics.setColor(Color.CYAN);
                 Graphics.fillRect(BadAppleX[i], BadAppleY[i], UNIT_SIZE, UNIT_SIZE);
                break;
              default:
                 Graphics.setColor(Color.BLUE);
                 Graphics.fillRect(BadAppleX[i], BadAppleY[i], UNIT_SIZE, UNIT_SIZE);
                break;
              }
            Graphics.fillRect(BadAppleX[i], BadAppleY[i], UNIT_SIZE, UNIT_SIZE);
            } 

             
             
             //DEATH APPLE
            Graphics.setColor(Color.RED);
            Graphics.fillRect(BadAppleX[0], BadAppleY[0], UNIT_SIZE, UNIT_SIZE);
            
//            Graphics.setColor(new Color(125, 125, 125));
//            
        }else{
            GAME_OVER(Graphics);
        }
        
        if(paused){
            Graphics.setColor(Color.GRAY);
            Graphics.setFont(new Font("monospaced", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(Graphics.getFont());
            Graphics.drawString("Press P to Unpause", (SCREEN_WIDTH - metrics.stringWidth("Press P to Unpause")) / 2, SCREEN_HEIGHT / 2);
        }
        if (running) {
            Graphics.setColor(Color.GREEN);
            Graphics.setFont(new Font("Chiller", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(Graphics.getFont());
            Graphics.drawString(""+ApplesEaten, (SCREEN_WIDTH - metrics.stringWidth(""+ApplesEaten)) / 2, Graphics.getFont().getSize());
            
        }    
    }
    
    public void NEW_APPLES(){
        AppleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        AppleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
       }
    public void NEW_BAD_APPLES(){
        BadAppleX[0] = random.nextInt((int)((SCREEN_WIDTH+UNIT_SIZE)/ UNIT_SIZE)) * UNIT_SIZE;
        BadAppleY[0] = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)+(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        
        
        if (ApplesEaten >= 4) {
             for (int i = 1; i < BadAppleX.length; i++) {
                BadAppleX[i] = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE*i)) * UNIT_SIZE;
                BadAppleY[i] = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE-i)) * UNIT_SIZE;
            }
   }else if (ApplesEaten >= 30) {
        BadAppleX[0] = random.nextInt((int)((SCREEN_WIDTH+UNIT_SIZE)/ UNIT_SIZE)) * UNIT_SIZE;
        BadAppleY[0] = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)+(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    }
      
    }
    
    public void SNAKE_MOVE(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
        if (ApplesEaten == 3) {
                delay = 10;
                setBackground(Color.DARK_GRAY); 
               } 
        if (ApplesEaten >= 5) {
                delay = 85;
                 setBackground(Color.BLACK);
        }
    }
    
    public void APPLES_EATEN(){
        if(x[0] == AppleX && y[0] == AppleY){
            bodyParts++;
            ApplesEaten++;
            
            NEW_BAD_APPLES();
            NEW_APPLES();
            
            red -= 10;
            if(red == 0){
                red = 10;
            }
            green -= 10;
            if(green == 0){
                green = 10;
            }
        }
    }
    public void BAD_APPLES_EATEN(){
        for (int i = 1; i < BadAppleX.length; i++) {
            if(x[0] == BadAppleX[i] && y[0] == BadAppleY[i]){
            bodyParts+=5;
           ApplesEaten+=4;
           
           NEW_BAD_APPLES();
            NEW_APPLES();
        }else if(x[0] == BadAppleX[0] && y[0] == BadAppleY[0]) {
                running = false;
            }
        
        
            
            blue -= 10;
            if(blue == 0){
                blue = 10;
            }
            pink -= 10;
            if(pink == 0){
                pink = 10;
            }
        }
    }
    
    public void checkCollision(){
        // head with body
        for(int i = bodyParts; i > 0; i--){
            if(x[0] == x[i] && y[0] == y[i]){
                running = false;
            }
        }
        // head to left boarder
        if(x[0] < 0){
            running = false;
        }
        // head to right boarder
        if(x[0] > SCREEN_WIDTH - UNIT_SIZE){
            running = false;
        }
        //head to top boarder
        if(y[0] < 0){
            running = false;
        }
        //head to bottom boarder
        if(y[0] > SCREEN_HEIGHT - UNIT_SIZE){
            running = false;
        }
        
        if(!running){
            timer.stop();
        }
    }
    
    public void RESET_GAME(){
        START();
        for(int i = 0; i < x.length; i++){
            x[i] = 0;
            y[i] = 0;
        }
        red = 40;
        green = 180;
        blue = 40;
        pink = 180;
        direction = 'R';
        bodyParts = 6;
        ApplesEaten = 0;
        repaint();
    }
    
    public void GAME_OVER(Graphics Graphics){
        setBackground(Color.BLACK);
        
        //Game over print out
        Graphics.setColor(Color.GREEN);
        Graphics.setFont(new Font("Chiller", Font.BOLD, 205));
        FontMetrics metrics = getFontMetrics(Graphics.getFont());
        Graphics.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
        
        //Score print out
        Graphics.setColor(Color.GREEN);
        Graphics.setFont(new Font("monospaced", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(Graphics.getFont());
        Graphics.drawString("Score: " + ApplesEaten, 
                (SCREEN_WIDTH - metrics2.stringWidth("Score: " + ApplesEaten)) / 2, Graphics.getFont().getSize());
        
        
        Graphics.setColor(Color.GREEN);
        Graphics.setFont(new Font("monospaced", Font.BOLD, 20));
        FontMetrics metrics3 = getFontMetrics(Graphics.getFont());
        Graphics.drawString("Press R to restart", 
              (SCREEN_WIDTH - metrics3.stringWidth("Press R to restart")) / 2, (SCREEN_HEIGHT / 4) * 3);       
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(running){
            SNAKE_MOVE();
            APPLES_EATEN();
            BAD_APPLES_EATEN();
            checkCollision();
        }
        repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_A:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
            
            if(!running){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                   START();
                }
            if(!running){
                if(e.getKeyCode() == 'R'){
                   RESET_GAME();
                }
            }
                if (running) {
                    if(e.getKeyCode() == KeyEvent.VK_P){
                      paused = true;
                      repaint();
                      timer.stop();
                }else
                    if(e.getKeyCode() == KeyEvent.VK_P){
                     paused = false;
                     repaint();
                     timer.start();
            }
                }
            
        }
    }
  }
}
