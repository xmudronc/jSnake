package cz.mendelu;

import java.util.ArrayList;

public class Map
{
    private ArrayList<ArrayList<String>> map;

    public Map()
    {
        this.map = fillMap();
    }

    public Map(ArrayList<ArrayList<String>> map)
    {
        this.map = map;
    }

    public ArrayList<ArrayList<String>> getMap()
    {
        return this.map;
    }

    public void setMap(ArrayList<ArrayList<String>> map)
    {
        this.map = map;
    }

    public ArrayList<ArrayList<String>> fillMap()
    {
        ArrayList<ArrayList<String>> out = new ArrayList<>();

        ArrayList<String> side = new ArrayList<>();
        ArrayList<String> middle = new ArrayList<>();
        for (int i = 0; i < 82; i++)
        {
            if ((i == 0) || (i == 81))
            {
                middle.add(i, "�");
            }
            else
            {
                middle.add(i, " ");
            } 
        }
        for (int i = 0; i < middle.size(); i++)
        {
            side.add(i, "�");
        }
        for (int i = 0; i < 42; i++)
        {
            if ((i == 0) || (i == 41))
            {
                out.add(i, side);
            }
            else
            {
                out.add(i, middle);
            }
        }

        return out;
    }
}