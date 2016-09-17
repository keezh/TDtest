package com.huyue.tdtest.createtower;

import android.graphics.Canvas;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.tdactive.Tower;
import com.huyue.tdtest.tower.OneTargetTower;

public class CreateOneTargetTower implements TowerCreatorObject
{

    @Override
    public void draw(Canvas canvas, int ix, int iy)
    {
        OneTargetTower.testdraw(canvas, ix, iy);
    }

    @Override
    public int cost()
    {
        return OneTargetTower.cost[0];
    }

    @Override
    public float getRange()
    {
        return Tower.getDisplayRange(OneTargetTower.ranges[0]);
    }

    @Override
    public void act(int ix, int iy)
    {
        if (GameSurfaceView.moneymanager.isEnough(cost()))
        {
            GameSurfaceView.towermanager.addTower(ix, iy, 0);
        }
    }

    @Override
    public String getString()
    {
        return "造价" + cost() + "  对单个怪物造成伤害";
    }
}
