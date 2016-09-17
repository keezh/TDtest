package com.huyue.tdtest.createtower;

import android.graphics.Canvas;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.tdactive.Tower;
import com.huyue.tdtest.tower.AeraOfEffectTower;

public class CreateAeraOfEffectTower implements TowerCreatorObject
{
    @Override
    public void draw(Canvas canvas, int ix, int iy)
    {
        AeraOfEffectTower.testdraw(canvas, ix, iy);
    }

    @Override
    public int cost()
    {
        return AeraOfEffectTower.cost[0];
    }

    @Override
    public float getRange()
    {
        return Tower.getDisplayRange(AeraOfEffectTower.ranges[0]);
    }

    @Override
    public void act(int ix, int iy)
    {
        if (GameSurfaceView.moneymanager.isEnough(cost()))
        {
            GameSurfaceView.towermanager.addTower(ix, iy, 4);
        }
    }

    @Override
    public String getString()
    {
        return "造价" + cost() + "  对周围怪物造成伤害";
    }
}
