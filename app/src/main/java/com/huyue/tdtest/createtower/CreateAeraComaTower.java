package com.huyue.tdtest.createtower;

import android.graphics.Canvas;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.tdactive.Tower;
import com.huyue.tdtest.tower.AeraComaTower;

public class CreateAeraComaTower implements TowerCreatorObject
{
    @Override
    public void draw(Canvas canvas, int ix, int iy)
    {
        AeraComaTower.testdraw(canvas, ix, iy);
    }

    @Override
    public int cost()
    {
        return AeraComaTower.cost[0];
    }

    @Override
    public float getRange()
    {
        return Tower.getDisplayRange(AeraComaTower.ranges[0]);
    }

    @Override
    public void act(int ix, int iy)
    {
        if (GameSurfaceView.moneymanager.isEnough(cost()))
        {
            GameSurfaceView.towermanager.addTower(ix, iy, 3);
        }
    }

    @Override
    public String getString()
    {
        return "造价" + cost() + "  对某一片怪物造成昏迷效果";
    }

}
