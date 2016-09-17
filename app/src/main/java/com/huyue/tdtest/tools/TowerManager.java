package com.huyue.tdtest.tools;

import java.util.ArrayList;

import com.huyue.tdtest.tdactive.Tower;
import com.huyue.tdtest.tower.AeraComaTower;
import com.huyue.tdtest.tower.AeraOfEffectTower;
import com.huyue.tdtest.tower.FreezeOneTower;
import com.huyue.tdtest.tower.OneTargetTower;
import com.huyue.tdtest.tower.ThroughTower;

import android.graphics.Canvas;

public final class TowerManager
{
    public ArrayList<Tower> towers;

    public TowerManager()
    {
        towers = new ArrayList<Tower>();
    }

    public void addTower(int ix, int iy, int kind)
    {
        switch (kind)
        {
        case 0:
            towers.add(new OneTargetTower(ix, iy, 0));
            break;

        case 1:
            towers.add(new FreezeOneTower(ix, iy, 0));
            break;

        case 2:
            towers.add(new ThroughTower(ix, iy, 0));
            break;
        case 3:
            towers.add(new AeraComaTower(ix,iy,0));
            break;
        case 4:
            towers.add(new AeraOfEffectTower(ix, iy, 0));
            break;
        default:
            break;
        }

    }

    public void allAct()
    {
        for (int i = 0; i < towers.size(); i++)
        {
            if (towers.get(i).act())
            {
                i--;
            }
        }
    }

    public void allDraw(Canvas canvas)
    {
        for (int i = 0; i < towers.size(); i++)
        {
            Tower tower = towers.get(i);
            if (tower != null)
            {
                tower.draw(canvas);
            }
        }
    }

    public Tower getTower(int ix, int iy)
    {
        for (int i = 0; i < towers.size(); i++)
        {
            Tower tower = towers.get(i);
            if (tower != null && tower.getIx() == ix && tower.getIy() == iy)
            {
                return tower;
            }
        }
        return null;
    }

    public void clear()
    {
        towers.clear();
    }

    public void removeTower(Tower tower)
    {
        towers.remove(tower);
    }
}
