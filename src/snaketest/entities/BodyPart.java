package snaketest.entities;

import java.awt.Color;
import java.awt.Graphics;

public class BodyPart {
    
    public int PosX, PosY, Width, Height;
    
    public BodyPart(int xCoord, int yCoord, int tileSizeHorizontal, int tileSizeVertical)
    {
        this.PosX = xCoord;
        this.PosY = yCoord;
        Width = tileSizeHorizontal;
        Height = tileSizeVertical;
    }
    
    public void tick()
    {
        
    }
    
    public void draw(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(PosX * Width, PosY * Height, Width, Height);
    }
}
