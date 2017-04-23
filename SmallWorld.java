import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class SmallWorld implements Runnable{
   
   final int WIDTH = 500;
   final int HEIGHT = 500;
   
   JFrame frame;
   Canvas canvas;
   BufferStrategy bufferStrategy;
   
   public SmallWorld(){
      frame = new JFrame("Blue World");
      
      JPanel panel = (JPanel) frame.getContentPane();
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      panel.setLayout(null);
      
      canvas = new Canvas();
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
      
      panel.add(canvas);
      
      canvas.addMouseListener(new MouseControl());
      canvas.addKeyListener(new KeyControl());
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setResizable(true);
      frame.setVisible(true);
      
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      
      canvas.requestFocus();
   }
   
        
   private class MouseControl extends MouseAdapter{
      public void mousePressed(MouseEvent event){
              if(lives>0){
                  if (clicked==false){
                  clicked=true;
                  updates=true;
                }
            }
            else{
               clicked=false;
               score=0;
               lives=3;
               x = 250;
               y = 150;
               dir = 0;
               momentum=0;
               degrees = 0.0;
               updates=false;
               rotates=false;
               int[][] storage={{1,1,1,0,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}};
               environment=storage;
               offset=0;
               outline=false;
               redDisplacement=0;
               redOffset=0;
               redTarget=x;
            }
        }
   }
   
   private class KeyControl extends KeyAdapter{
       public void keyPressed(KeyEvent e){
           if (e.getKeyChar()=='z'){
               //updates=true;
               dir=-1;
           }
           if (e.getKeyChar()=='c'){
               //updates=true;
               dir=1;
           }
           if (e.getKeyChar()=='x'){
               if(y==150&&clicked==true&&lives>0){
                   momentum=-50;
                   rotates=true;
                }
            }
       }
       public void keyReleased(KeyEvent e){
           if(e.getKeyChar()=='z'||e.getKeyChar()=='c'){
               dir=0;
           }
       }
   }
   
   long desiredFPS = 60;
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
    
   boolean running = true;
   
   public void run(){
      
      long beginLoopTime;
      long endLoopTime;
      long currentUpdateTime = System.nanoTime();
      long lastUpdateTime;
      long deltaLoop;
      
      while(running){
         beginLoopTime = System.nanoTime();
         
         render();
         
         lastUpdateTime = currentUpdateTime;
         currentUpdateTime = System.nanoTime();
         //System.out.println((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));
         update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));
         
         endLoopTime = System.nanoTime();
         deltaLoop = endLoopTime - beginLoopTime;
           
           if(deltaLoop > desiredDeltaLoop){
               //Do nothing. We are already late.
           }else{
               try{
                   Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
               }catch(InterruptedException e){
                   //Do nothing
               }
           }
      }
   }
   
   private void render() {
      Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
      g.clearRect(0, 0, WIDTH, HEIGHT);
      render(g);
      g.dispose();
      bufferStrategy.show();
   }
   
   //TESTING
   boolean clicked=false;
   int score=0;
   int lives=3;
   int x = 250;
   int y = 150;
   int dir = 0;
   int momentum=0;
   double degrees = 0.0;
   Rotater r = new Rotater();
   boolean updates=false;
   boolean rotates=false;
   int[][] environment={{1,1,1,0,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}};
   int offset=0;
   boolean outline=false;
   int redDisplacement=0;
   int redOffset=0;
   int redTarget=x;
   
   /**
    * Rewrite this method for your game
    */
   protected void update(int deltaTime){
      //x += deltaTime * 0.2;
      //System.out.println(x);
      //while(x > 500){
        // x -= 500;
      //}
      if(rotates==true){
          updates=false;
          if(x>=250)degrees+=5;
          if(x<250)degrees-=5;
          if(degrees%90==0){
             degrees=0;
             rotates=false;
             updates=true;
             int[][] holder=new int[4][7];
             if(x>=250){
                offset+=3;
                offset=offset%4;
                redOffset+=3;
                redOffset=redOffset%4;
             }
             if(x<250){
                offset+=1;
                offset=offset%4;
                redOffset+=1;
                redOffset=redOffset%4;
             }
          }
      }
      if(updates==true){
          if(x+(5*dir)>=150&&x+(5*dir)<=350)x+=(5*dir);
          if(redDisplacement+150>redTarget+5){
              redDisplacement-=1;
              if(redDisplacement<0)redDisplacement=0;
              if(redDisplacement+150-x<=10&&redDisplacement+150-x>=-10&&redOffset%4==0){
                  lives--;
                  if (lives<=0){
                      updates=false;
                    }
                  else{
                      clicked=false;
                      x = 250;
                      y = 150;
                      dir = 0;
                      updates=false;
                      offset=0;
                      outline=false;
                      redDisplacement=0;
                      redOffset=0;
                      redTarget=x;
                    }
                }
            }
          else if(redDisplacement+150<redTarget-5){
              redDisplacement+=1;
              if(redDisplacement>200)redDisplacement=200;
              if(redDisplacement+150-x<=10&&redDisplacement+150-x>=-10&&redOffset%4==0){
                  lives--;
                  if (lives<=0){
                      updates=false;
                    }
                  else{
                      clicked=false;
                      x = 250;
                      y = 150;
                      dir = 0;
                      updates=false;
                      offset=0;
                      outline=false;
                      redDisplacement=0;
                      redOffset=0;
                      redTarget=x;
                    }
                }
            }
          else{
              if(redOffset%4==0){
                  /*lives--;
                  if (lives==0){
                      updates=false;
                    }
                  else{
                      boolean clicked=false;
                      int x = 250;
                      int y = 150;
                      int dir = 0;
                      updates=false;
                      int offset=0;
                      Color outline=Color.YELLOW;
                      int redDisplacement=0;
                      int redOffset=0;
                      int redTarget=x;
                    }*/
                    if(y==150){
                      if(redTarget==x){
                          if(x>=250)redTarget=150;
                          else redTarget=350;
                        }
                      else redTarget=x;
                      rotates=true;
                      momentum=-50;
                      outline=true;
                    }
                }
              else{
                  if(y==150){
                      if(redTarget==x){
                          if(x>=250)redTarget=150;
                          else redTarget=350;
                        }
                      else redTarget=x;
                      rotates=true;
                      momentum=-50;
                      outline=true;
                    }
                }
            }
      }
      for(int i=0;i<5;i++){
          if(200+(i*25)-x>=0&&200+(i*25)-x<=10){
              if( environment[(0+offset)%4][i+1]==1){
                  environment[(0+offset)%4][i+1]=0;
                  score++;
                  int tally=0;
                  for(int j=0;j<28;j++){
                      if(environment[j/7][j%7]==0)tally++;
                    }
                  if (tally==28)lives=0;
                }
            }
          else if(200+(i*25)-x<0&&200+(i*25)-x>=-10){
              if(environment[(0+offset)%4][i+1]==1){
                  environment[(0+offset)%4][i+1]=0;
                  score++;
                  int tally=0;
                  for(int j=0;j<28;j++){
                      if(environment[j/7][j%7]==0)tally++;
                    }
                  if (tally==28)lives=0;
                }
            }
        }
      if(175-x>0&&175-x<=10){
          if(environment[(0+offset)%4][0]==1){
              environment[(0+offset)%4][0]=0;
              for (int i=0;i<5;i++)environment[(((0+offset)%4)+3)%4][i+1]=1;
            }
        }
      else if(325-x<0&&325-x>=-10){
          if(environment[(0+offset)%4][6]==1){
              environment[(0+offset)%4][6]=0;
              for (int i=0;i<5;i++)environment[(((0+offset)%4)+1)%4][i+1]=1;
            }
        }
      //TODO:Add Pacman ball interactions
      //TODO: Add enemies
      if(y<=150){
          y+=momentum;
          momentum+=5;
      }
      if(y>150)y=150;
      if(y==150)outline=false;
   }
   
   /**
    * Rewrite this method for your game
    */
   protected void render(Graphics2D g){
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 500, 500);
      g.rotate(Math.toRadians(degrees),250,250);
      r.paintArena(g);
      g.setColor(Color.WHITE);
      for(int h=0;h<4;h++){
          if(h==0){
              for(int i=0;i<5;i++){
                  if(environment[(h+offset)%4][i+1]==1)g.drawOval(200+(i*25)-10, 130, 20, 20);
                }
              for(int i=0;i<2;i++){
                  if(environment[(h+offset)%4][i+(i*5)]==1)g.fillOval(175+(i*150)-10, 130, 20, 20);
                }
            }
          if(h==1){
              for(int i=0;i<5;i++){
                  if(environment[(h+offset)%4][i+1]==1)g.drawOval(350, 200+(i*25)-10, 20, 20);
                }
              for(int i=0;i<2;i++){
                  if(environment[(h+offset)%4][i+(i*5)]==1)g.fillOval(350, 175+(i*150)-10, 20, 20);
                }
            }
          if(h==2){
              for(int i=0;i<5;i++){
                  if(environment[(h+offset)%4][i+1]==1)g.drawOval(300-(i*25)-10, 350, 20, 20);
                }
              for(int i=0;i<2;i++){
                  if(environment[(h+offset)%4][i+(i*5)]==1)g.fillOval(325-(i*150)-10, 350, 20, 20);
                }
            }
          if(h==3){
              for(int i=0;i<5;i++){
                  if(environment[(h+offset)%4][i+1]==1)g.drawOval(130, 300-(i*25)-10, 20, 20);
                }
              for(int i=0;i<2;i++){
                  if(environment[(h+offset)%4][i+(i*5)]==1)g.fillOval(130, 325-(i*150)-10, 20, 20);
                }
            }
        }
      g.setColor(Color.RED);
      if(redOffset%4==0)g.fillOval(150+redDisplacement-10, 130, 20, 20);
      if(redOffset%4==3)g.fillOval(350, 150+redDisplacement-10, 20, 20);
      if(redOffset%4==2)g.fillOval(350-redDisplacement-10, 350, 20, 20);
      if(redOffset%4==1)g.fillOval(130, 350-redDisplacement-10, 20, 20);
      g.rotate(Math.toRadians(-degrees),250,250);
      g.setColor(Color.YELLOW);
      g.fillOval(x-10, y-20, 20, 20);
      for(int i=0;i<lives;i++)g.fillOval(470-(30*i)-10,470,20,20);
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(3));
      if(outline==true)g.drawOval(x-10, y-20, 20, 20);
      g.setStroke(new BasicStroke(1));
      g.setColor(Color.WHITE);
      g.setFont(new Font("TimesRoman", Font.BOLD, 16)); 
      g.drawString("Z = LEFT", 5,100);
      g.drawString("C = RIGHT", 5,125);
      g.drawString("X = JUMP", 5,150);
      g.drawString("HOW MANY OVALS CAN YOU COLLECT?", 100,20);
      g.drawString("OVALS: "+score,5,480);
      if(clicked==false){
          g.drawString("Click anywhere to start", 175,250);
        }
      if(lives<=0){
          g.drawString("GAME OVER", 200,450);
          g.drawString("Click anywhere to try again", 160,250);
        }
   }
   
   public static void main(String [] args){
      SmallWorld ex = new SmallWorld();
      new Thread(ex).start();
   }

}