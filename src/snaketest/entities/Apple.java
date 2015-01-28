package snaketest.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Apple {
    
        public int PosX, PosY, Width, Height;
        private int _xMax, _yMax;
        private Random _rng;
        
        public Apple(int xMax, int yMax, int w, int h)
        {
            _rng = new Random();
            _xMax = xMax;
            _yMax = yMax;
            randomizePosition();
            Width = w;
            Height = h;
        }
        
        public void randomizePosition()
        {
            this.PosX = _rng.nextInt(_xMax);
            this.PosY = _rng.nextInt(_yMax);
        }
        
        public void draw(Graphics g)
        {
            g.setColor(Color.black);
            g.fillRect(PosX * Width, PosY * Height, Width, Height);
        }
}
