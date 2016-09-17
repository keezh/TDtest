package com.huyue.tdtest.tower;

import java.util.HashSet;

import android.graphics.Bitmap;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.tdactive.Bullet;
import com.huyue.tdtest.tdactive.Monster;

public class ThroughBullet extends Bullet
{
    private HashSet<Monster> dmged;
    
    public ThroughBullet(Bitmap bitmap, int dmg, float x, float y, float dx, float dy, float size)
    {
        super(bitmap, dmg, x, y, dx, dy, size);
        dmged = new HashSet<Monster>();
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean tryAttack()
    {
        Monster one = null;
        for (Monster monster : GameSurfaceView.monstermanager.getAllMonsters())
        {
            float tx = monster.x;
            float ty = monster.y;
            double dis = Math.sqrt((x - tx) * (x - tx) + (y - ty) * (y - ty));
            if (dis < CONST.MAP.HALFSIZE * 1.4 && monster.isactive)
            {
                one = monster;
                if(!dmged.contains(one))
                {
                    dmged.add(one);
                    attack(one);
                }
            }
        }
        return false;
    }

    @Override
    public void attack(Monster monster)
    {
        GameSurfaceView.animManager.addNewImpact((x + monster.x) / 2, (y + monster.y) / 2);
        monster.damage(dmg);
    }
}
