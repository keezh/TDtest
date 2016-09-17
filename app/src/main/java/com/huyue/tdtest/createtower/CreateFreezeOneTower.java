package com.huyue.tdtest.createtower;

import android.graphics.Canvas;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.tdactive.Tower;
import com.huyue.tdtest.tower.FreezeOneTower;

public class CreateFreezeOneTower implements TowerCreatorObject
{

    @Override
    public void draw(Canvas canvas, int ix, int iy)
    {
        FreezeOneTower.testdraw(canvas, ix, iy);
    }

    @Override
    public int cost()
    {
        return FreezeOneTower.cost[0];
    }

    @Override
    public float getRange()
    {
        return Tower.getDisplayRange(FreezeOneTower.ranges[0]);
    }


    @Override
    public void act(int ix, int iy)
    {
        if (GameSurfaceView.moneymanager.isEnough(cost()))
        {
            GameSurfaceView.towermanager.addTower(ix, iy, 1);
        }
    }

    @Override
    public String getString()
    {
        return "造价" + cost() + "  对单个怪物进行减速";
    }
}
