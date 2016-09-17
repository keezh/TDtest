package com.huyue.tdtest.createtower;

import android.graphics.Canvas;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.tdactive.Tower;
import com.huyue.tdtest.tower.ThroughTower;

public class CreateThroughTower implements TowerCreatorObject
{

    @Override
    public void draw(Canvas canvas, int ix, int iy)
    {
        ThroughTower.testdraw(canvas, ix, iy);
    }

    @Override
    public int cost()
    {
        return ThroughTower.cost[0];
    }

    @Override
    public float getRange()
    {
        return Tower.getDisplayRange(ThroughTower.ranges[0]);
    }

    @Override
    public void act(int ix, int iy)
    {
        if (GameSurfaceView.moneymanager.isEnough(cost()))
        {
            GameSurfaceView.towermanager.addTower(ix, iy, 2);
        }
    }

    @Override
    public String getString()
    {
        return "造价" + cost() + "  对一条直线上的怪物造成伤害";
    }
}
