package snaketest.graphics;

import snaketest.entities.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;

public class Screen extends JPanel implements Runnable {
    
    public static int Width;
    public static int Height;
    public static int HSize;
    public static int VSize;
	
    public static int VMax;
    public static int HMax;
    
    private Thread _thread;
    private Boolean _gameRunning;
    private Block _head;
    private ArrayList<Block> _snake;  
    private Block _apple;
    private Timer _gameTimer;
    
    private Point _spawnPoint;
    private int _points;
    
    private boolean[] _direction = {false, true, false, false};
    private int _scheduledDirection = 1;
    private int _currentDirection = 1;
    private Key _key;
    
    public Screen()
    {   
        Width = 600;
        Height = 600;
        HSize = 20;
        VSize = 20;

        HMax = Width / HSize;
        VMax = Height / VSize;
        
	_points = 4;
        setFocusable(true);
        _key = new Key();
        addKeyListener(_key);
        _spawnPoint = new Point((int)Math.floor(HMax / 2 - 1), (int)Math.floor(VMax / 2 - 1));
        setPreferredSize(new Dimension(Width, Height));
        _snake = new ArrayList<Block>();
        _apple = new Block(HMax, VMax, HSize, VSize);
        _apple.randomizePosition(VMax, HMax);
        start();
    }
    
    public void updateDirection()
    {
        if (_direction[0]) --_spawnPoint.y;
        if (_direction[1]) ++_spawnPoint.x;
        if (_direction[2]) ++_spawnPoint.y;
        if (_direction[3]) --_spawnPoint.x;
    }
    
   
    public void teleport()
    {
        if (_spawnPoint.x > HMax) _spawnPoint.x = 0;
        if (_spawnPoint.x < 0) _spawnPoint.x = HMax;
        if (_spawnPoint.y > VMax) _spawnPoint.y = 0;
        if (_spawnPoint.y < 0) _spawnPoint.y = VMax;
    }
    
    public void moveSnake()
    {
        _head = new Block(_spawnPoint.x, _spawnPoint.y, HSize, VSize);
        _snake.add(_head);
        if (_snake.size() > _points) _snake.remove(0);
    }
    
    public void checkAppleCollision()
    {
        for (Block block : _snake)
        {
            if (block.Position.x == _apple.Position.x && block.Position.y == _apple.Position.y)
            {
                _apple.randomizePosition(VMax, HMax);
                ++_points;
            }
        }
    }
    
    public void checkSelfCollision()
    {
        for (int i=0; i<_snake.size() - 1; ++i)
        {
            if (_snake.get(i).Position.x == _head.Position.x && _snake.get(i).Position.y == _head.Position.y)
            {
                _gameRunning = false;
            }
        }
    }
       
    public void update()
    {
        changeDirection(_scheduledDirection);
        updateDirection();
        teleport();
        moveSnake();
        checkAppleCollision();
        checkSelfCollision();
    }
    
    //changes direction of movement
    //0: up, 1: right, 2: down, 3: left
    //do not invoke this anywhere else but the update() method
    //change _scheduledDirection instead
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
            _currentDirection = dir;
        }
    }
    
    private void turnRight() {
        if (_currentDirection == 3) _scheduledDirection = 0;
        else _scheduledDirection = _currentDirection + 1;
    }

    private void turnLeft() {
        if (_currentDirection == 0) _scheduledDirection = 3;
        else _scheduledDirection = _currentDirection - 1;
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
                    update(); 
                }
            }, 0, 100);
    }
    
    public void stop()
    {
        
    }
    
    public void paint(Graphics g)
    {
        g.setColor(new Color(164, 199, 141));
        g.clearRect(0, 0, Width, Height);
        g.fillRect(0, 0, Width, Height);
       
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
            repaint();
        }
    }
    
    private class Key implements KeyListener 
    {

        @Override
        public void keyTyped(KeyEvent e) 
        {
            char key = e.getKeyChar();
            switch (key)
            {
                case 'w': 
                    _scheduledDirection = 0;
                    break;
                case 'd':
                    _scheduledDirection = 1;
                    break;
                case 's': 
                    _scheduledDirection = 2;
                    break;
                case 'a': 
                    _scheduledDirection = 3;
                    break;
                case 'q': 
                    turnLeft();
                    break;
                case 'e':
                    turnRight();
                    break;
            }
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