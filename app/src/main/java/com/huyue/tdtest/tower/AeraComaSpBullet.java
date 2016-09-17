package com.huyue.tdtest.tower;

import com.huyue.tdtest.anim.Anim.AnimObject;
import com.huyue.tdtest.tdactive.Bullet;
import com.huyue.tdtest.tdactive.Monster;

public class AeraComaSpBullet extends AeraOfEffectBullet
{

    float comaTime;

    public AeraComaSpBullet(AnimObject animObject, int dmg, float x, float y, float range,
            float comaTime)
    {
        super(animObject, dmg, x, y, range);
        this.comaTime = comaTime;
    }

    @Override
    public void attack(Monster monster)
    {
        monster.coma(dmg, comaTime);
    }

}
