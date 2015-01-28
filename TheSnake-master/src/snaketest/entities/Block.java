package snaketest.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Point;

public class Block {

	public Point Position;
	public int Width, Height;
	private Random _rng;
	
        public Block(int width, int height)
	{
		_rng = new Random();
		Width = width;
		Height = height;
	}
	
	public Block(int x, int y, int width, int height)
	{
		_rng = new Random();
		Position = new Point(x, y);
		Width = width;
		Height = height;
	}
	
	public void randomizePosition(int xMax, int yMax)
	{
		Position.x = _rng.nextInt(xMax);
		Position.y = _rng.nextInt(yMax);
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(Position.x * Width, Position.y * Height, Width, Height);
	}
}