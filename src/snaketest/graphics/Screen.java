package snaketest.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import snaketest.entities.BodyPart;
import snaketest.entities.Apple;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Screen extends JPanel implements Runnable {
    
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int HSIZE = 20;
    public static final int VSIZE = 20;
    
    private Thread _thread;
    private Boolean _gameRunning;
    private BodyPart _head;
    private ArrayList<BodyPart> _snake;  
    private Apple _apple;
    private Timer _gameTimer;
    
    private int _xCoord;
    private int _yCoord;
    private int _points = 4;
    
    private boolean[] _direction = {false, true, false, false};
    private int _scheduledDirection = 1;
    private Key _key;
    
    public Screen()
    {   
        setFocusable(true);
        _key = new Key();
        addKeyListener(_key);
        this._xCoord = (int)Math.floor(WIDTH / HSIZE / 2 - 1);
        this._yCoord = (int)Math.floor(HEIGHT / VSIZE / 2 - 1);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        _snake = new ArrayList<BodyPart>();
        _apple = new Apple(WIDTH / HSIZE, HEIGHT / VSIZE, HSIZE, VSIZE);
        start();
    }
    
    public void tick()
    {
    }
       
    public void movement()
    {
        changeDirection(_scheduledDirection);
        
        if (_direction[0]) --_yCoord;
        if (_direction[1]) ++_xCoord;
        if (_direction[2]) ++_yCoord;
        if (_direction[3]) --_xCoord;
        
        //teleportation
        if (_xCoord > WIDTH / HSIZE) _xCoord = 0;
        if (_xCoord < 0) _xCoord = WIDTH / HSIZE;
        if (_yCoord > HEIGHT / VSIZE) _yCoord = 0;
        if (_yCoord < 0) _yCoord = HEIGHT / VSIZE;
                
        _head = new BodyPart(_xCoord, _yCoord, HSIZE, VSIZE);
        _snake.add(_head);
        
        if (_snake.size() > _points)
        {
            _snake.remove(0);
        }
        
        //check for collision with apple
        for (BodyPart block : _snake)
        {
            if (block.PosX == _apple.PosX && block.PosY == _apple.PosY)
            {
                _apple.randomizePosition();
                ++_points;
            }
        }
        
        //check for collision with self
        for (int i=0; i<_snake.size() - 1; ++i)
        {
            if (_snake.get(i).PosX == _head.PosX && _snake.get(i).PosY == _head.PosY)
            {
                _gameRunning = false;
            }
        }
    }
    
    //changes direction of movement
    //0: up, 1: right, 2: down, 3: left
    public void changeDirection(int dir)
    {
        if 
        (
            dir >= 0 && dir <= 3 &&
            !(_direction[0]==true && dir == 2) &&
            !(_direction[1]==true && dir == 3) &&
            !(_direction[2]==true && dir == 0) &&
            !(_direction[3]==true && dir == 1)
        )
        {
            Arrays.fill(_direction, false);
            _direction[dir] = true;
        }
    }
    
    public void start()
    {
        _gameRunning = true;
        _thread = new Thread(this, "gameLoop");
        _thread.start();
        _gameTimer = new Timer();
        _gameTimer.schedule(new TimerTask()  
            { 
                @Override
                public void run(){
                    movement(); 
                }
            }, 0, 100);
    }
    
    public void stop()
    {
        
    }
    
    public void paint(Graphics g)
    {
        g.setColor(new Color(164, 199, 141));
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.gray);
       
        for (int i=0; i < _snake.size(); ++i) 
        {
            _snake.get(i).draw(g);
        }
        _apple.draw(g);
    }
    
    public void run()
    {
        while(_gameRunning)
        {
            tick();
            repaint();
        }
    }
    
    private class Key implements KeyListener 
    {

        @Override
        public void keyTyped(KeyEvent e) 
        {
            char key = e.getKeyChar();
            if (key == 'w') _scheduledDirection = 0;
            if (key == 'd') _scheduledDirection = 1;
            if (key == 's') _scheduledDirection = 2;
            if (key == 'a') _scheduledDirection = 3;            
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
        }

        @Override
        public void keyReleased(KeyEvent e) 
        {
        }
    }
}


