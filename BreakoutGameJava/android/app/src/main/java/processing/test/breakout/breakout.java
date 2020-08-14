package processing.test.breakout;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class breakout extends PApplet {

ArrayList <Ball> balls = new ArrayList<Ball>();
ArrayList<Brick> bricks = new ArrayList<Brick>();
Paddle paddle;

public void setup() {
  
  int brickWidth = width/10; 
  int brickHeight = height/40;
  Ball ball = new Ball(width/2, height/3, 0xff13F05E, 30);
  balls.add(ball);

  for (int i = 0; i < 10; i++) { //counter for each row
    for (int j = 0; j < 10; j++) {  //counter for all bricks in each row
      Brick b = new Brick(i*brickWidth, j*brickHeight + 90, brickWidth+2, brickHeight+2);
      if (i % 2 == 0 && j % 2 == 0) {
        b.brickCol = 0xff001AF7;
      }
      bricks.add(b);
    }
  }
  paddle = new Paddle(width/2, height - 200, width/5, 20);
}

public void draw() {
  background(0xfff0f0f0);
  paddle.display();
  paddle.move();
  //show bricks
  for (int i = 0; i < bricks.size(); i++) {
    Brick b = bricks.get(i);
    b.display();
  }

  //Show balls
  for (int i= 0; i < balls.size(); i++) {
    Ball b = balls.get(i);
    b.display();
    b.move();

    if (balls.get(i).collidesWith(paddle))//Bottom
    {  
      balls.get(i).velocity.y = -1 * abs(balls.get(i).velocity.y);      //make ball go up
    }
    
    for (int k = 0; k < bricks.size(); k++) {
      if (balls.get(i).collidesWith(bricks.get(k)))
      { 
        bricks.remove(k); 
        balls.get(i).velocity.y = -1 * balls.get(i).velocity.y;
      }
    }

    //If balls collide do an elastic collision
    if (balls.size() > 1) {
      for (int j = 0; j < balls.size(); j++) {
        if (i != j) {
          balls.get(i).collidesWith(balls.get(j));
        }
      }
    }

    //remove ball from play if it hits ground
    if (balls.get(i).location.y >= height) {
      balls.remove(i);
    }
  }

  //show lost screen
  if (balls.size() == 0) {
    println(balls.size());
    background(0); //erase everything
    textSize(random(25, 100));
    text("Game Over", random(0, width), random(0, height));
    if (mousePressed) {
        delay(5000);
        setup();
    }
  }

  //show win screen
  if (bricks.size() < 1) {
    background(random(0, 255), random(0, 255), random(0, 255)); //erase everything
    textSize(50);
    text("Congratulation You Win!", width/2, height/2);
    //stop the ball 
    balls.get(0).velocity.x = 0;
    balls.get(0).velocity.y = 0;
    if(mousePressed) {
        delay(5000);
        setup();
    }
  }
}

class Ball {
  PVector location;
  PVector velocity = new PVector(15, 20);
  PVector acceleration = new PVector(0, 0);
  int ballColor;
  float size;          //size is diameter

  Ball(float startX, float startY, int startColor, float startSize) {
    location = new PVector(startX, startY);
    ballColor = startColor;
    size = startSize;
  }

  public void display() {
    fill(ballColor);
    ellipse(location.x, location.y, size, size);
  }

  public void move() {
    velocity.add(acceleration);
    location.add(velocity);
    wallCheck();
  }

  public void collidesWith(Ball other) {  //elastic collision
    float distance = dist(location.x, location.y, other.location.x, other.location.y);
    if (distance < size/2 + other.size/2) {      //if distance is less than both radius combined they are colliding

      double d1 = Math.atan2(velocity.y, velocity.x); //ball 1 direction in angles
      double d2 = Math.atan2(other.velocity.y, other.velocity.x); //ball 2 direction in angles
      double v1 = Math.sqrt(velocity.x*velocity.x+velocity.y*velocity.y); //vector for ball 1
      double v2 = Math.sqrt(other.velocity.x*other.velocity.x+other.velocity.y*other.velocity.y);  //vector for ball 2
      double ang = Math.atan2(size - other.size, size - other.size);    //angle to bounce off

      //******New Velocity********************
      velocity.x = (float)(v1 * Math.cos(d1 - ang));
      velocity.y = (float)(v1 * Math.sin(d1 - ang));
      other.velocity.x = (float)(v2 * Math.cos(d2 - ang)); 
      other.velocity.y = (float) (v2 * Math.sin(d2 - ang));
    }
  }
  
  public boolean collidesWith(Brick other) {  //elastic collision
    if (this.location.x+this.size/2 > other.x //right edge of ball to right of left edge of brick
            && this.location.x-this.size/2 < other.x + other.bwidth //left edge of ball to left of right edge of brick
            && this.location.y + this.size/2 > other.y //ball bottom edge below brick top edge
            && this.location.y - this.size/2 < other.y + other.bheight)
    {
      return true;
    } else {
      return false;
    }
  }

  public boolean collidesWith(Paddle other) {  //elastic collision
    if (this.location.x+this.size/2 > other.x //right edge of ball to right of left edge of brick
            && this.location.x-this.size/2 < other.x + other.pwidth //left edge of ball to left of right edge of brick
            && this.location.y + this.size/2 > other.y
            && this.location.y - this.size/2 < other.y + other.pheight)
    {
      return true;
    } else {
      return false;
    }
  }

  public void wallCheck() {
    
      if (location.x-size/2 < 0 || location.x > width-size/2) {
        velocity.x = velocity.x * -1;
      }
      if (location.y-size/2 < 0) {
        velocity.y = velocity.y * -1;
      }
  }
}
class Brick {
  
    float x; //x position
    float y;  //y position
    float bwidth;  //width of brick
    float bheight; //height of brick
    int brickCol = 0xff982241;
    
    //TODO: constructor    
    Brick(float startx, float starty, float startWidth, float startHeight) {
      x = startx;
      y = starty;
      bwidth = startWidth;
      bheight = startHeight; 
    } 
  
    //TODO: display
    public void display() {
      fill(brickCol);
      stroke(1);
      rect(x, y, bwidth, bheight);
    } 
}
class Paddle {
    float x; //x position 
    float y;  //y position
    float vx = 1;
    float pwidth;  //paddle width
    float pheight; //paddle height
    boolean left;
    boolean right;

   //TODO: constructor
   Paddle(float startx, float starty, float startWidth, float startHeight) {
    x = startx;
    y = starty;
    pwidth = startWidth;
    pheight = startHeight;
   } 
  
   public void display() {
      noStroke();
      fill(0xffFFB700);
      rect(x, y, pwidth, pheight);
   }

   public void move() {
     x = x += vx;
     x = vx + mouseX;
     if (x < 0) {
       x = 0;
     }
     if (pwidth + x > width) {
       x = width-pwidth;
     }
   }

} 
  public void settings() {  size(displayWidth, displayHeight); }
}
