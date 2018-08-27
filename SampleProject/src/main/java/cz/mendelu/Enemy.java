package cz.mendelu;

public class Enemy
{
    private Position position;
    private String sprite;

    public Enemy(int x, int y)
    {
        this.position = new Position(x, y);
        setSprite("X");
    }

    public Enemy(Position position)
    {
        this.position = position;
        setSprite("X");
    }

    public Enemy(int x, int y, String sprite)
    {
        this.position = new Position(x, y);
        setSprite(sprite);
    }

    public Enemy(Position position, String sprite)
    {
        this.position = position;
        setSprite(sprite);
    }

    public Position getPosition()
    {
        return this.position;
    }

    public String getSprite()
    {
        return this.sprite;
    }

    public void setSprite(String sprite)
    {
        this.sprite = sprite;
    }
}