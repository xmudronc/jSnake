package cz.mendelu;

public class Position
{
    private int x;
    private int y;

    public Position()
    {
        setX(1);
        setY(1);
    }

    public Position(int x, int y)
    {
        setX(x);
        setY(y);
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void moveXL()
    {
        this.x--;
    }

    public void moveXR()
    {
        this.x++;
    }

    public void moveYU()
    {
        this.y--;
    }

    public void moveYD()
    {
        this.y++;
    }
}