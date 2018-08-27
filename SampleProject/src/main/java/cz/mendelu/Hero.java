package cz.mendelu;

public class Hero
{
    private Position position;
    private Position previousPos;
    private String sprite;
    private Hero tail;

    public Hero()
    {
        this.position = new Position();
        setSprite("O");
    }

    public Hero(int x, int y)
    {
        setPosition(x, y);
        setSprite("O");
    }

    public Hero(Position position)
    {
        setPosition(position);
        setSprite("O");
    }

    public Hero(String sprite)
    {
        this.position = new Position();
        setSprite(sprite);
    }

    public Hero(int x, int y, String sprite)
    {
        setPosition(x, y);
        setSprite(sprite);
    }

    public Hero(Position position, String sprite)
    {
        setPosition(position);
        setSprite(sprite);
    }

    public Position getPosition()
    {
        return this.position;
    }

    public void setPosition(int x, int y)
    {
        this.position = new Position(x, y);
    }

    public void setPosition(Position position)
    {
        setPrevPos(this.position);
        this.position = position;
        if (getTail() != null)
        {
            getTail().setPosition(this.previousPos);
        }
    }

    public String getSprite()
    {
        return this.sprite;
    }

    public void setSprite(String sprite)
    {
        this.sprite = sprite.substring(0, 1);
    }

    public Hero getTail()
    {
        return this.tail;
    }

    public void incTail()
    {
        if (getTail() == null)
        {
            this.tail = new Hero(this.getPosition(), "o");
        }
        else
        {
            getTail().incTail();
        }
    }

    public Position getPrevPos()
    {
        return this.previousPos;
    }

    public void setPrevPos(Position position)
    {
        this.previousPos = position;
    }

    public void moveL()
    {
        setPrevPos(new Position(this.position.getX(), this.position.getY()));
        getPosition().moveXL();
        if (getTail() != null)
        {
            getTail().setPosition(this.previousPos);
        }
    }

    public void moveR()
    {
        setPrevPos(new Position(this.position.getX(), this.position.getY()));
        getPosition().moveXR();
        if (getTail() != null)
        {
            getTail().setPosition(this.previousPos);
        }
    }

    public void moveU()
    {
        setPrevPos(new Position(this.position.getX(), this.position.getY()));
        getPosition().moveYU();
        if (getTail() != null)
        {
            getTail().setPosition(this.previousPos);
        }
    }

    public void moveD()
    {
        setPrevPos(new Position(this.position.getX(), this.position.getY()));
        getPosition().moveYD();
        if (getTail() != null)
        {
            getTail().setPosition(this.previousPos);
        }
    }

    public boolean biteTail(int x, int y)
    {
        boolean out = false;

        if (getTail() != null)
        {
            if (getTail().getPosition().getX() == x && getTail().getPosition().getY() == y)
            {
                out = true;
            }
            else
            {
                out = getTail().biteTail(x, y);
            }
        }

        return out;
    }
}