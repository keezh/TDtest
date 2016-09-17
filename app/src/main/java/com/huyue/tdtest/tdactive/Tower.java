package com.huyue.tdtest.tdactive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.createtower.CreateTower;
import com.huyue.tdtest.createtower.TowerCreatorObject;

public abstract class Tower extends TDActiveObject
{
    /*
     * ix iy用来表示所处位置 attack表示攻击伤害 gap表示攻击间隔 range表示射程 speed表示炮弹每秒移动速度
     * level表示塔的等级
     */

    public static Bitmap removeBitmap = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.remove);

    public int attack;
    public float gap;
    public float cd;
    public float range;
    public float speed;
    public int level;

    public abstract Bitmap getBitmap();
    public abstract Bitmap getBitmapByLevel(int level);
    public abstract int getCost();
    public abstract int getCostByLevel(int level);
    public abstract void setLevel(int level);
    public abstract void fireBullet(float dx,float dy);
    public abstract float getRangeByLevel(int level);

    /*
     * 升级塔时用于添加删除塔的功能
     */
    public class RemoveTower implements TowerCreatorObject
    {

        @Override
        public void draw(Canvas canvas, int ix, int iy)
        {
            Paint paint = new Paint();
            Rect src = CONST.getSrcRect(getBitmap());
            canvas.drawBitmap(getBitmap(), src, CONST.getRect(ix, iy), paint);
            src = CONST.getSrcRect(removeBitmap);
            canvas.drawBitmap(removeBitmap, src, CONST.getRect(ix, iy), paint);
        }

        @Override
        public int cost()
        {
            return 0;
        }

        @Override
        public float getRange()
        {
            return 0;
        }

        @Override
        public void act(int ix, int iy)
        {
            remove();
        }

        @Override
        public String getString()
        {
            return "拆塔返还造价的80%";
        }
    }

    /*
     * 用于升级塔时添加指定等级塔
     */
    public class UpgradeTower implements TowerCreatorObject
    {

        int level;

        public UpgradeTower(int level)
        {
            this.level = level;
        }

        @Override
        public void draw(Canvas canvas, int ix, int iy)
        {
            Paint paint = new Paint();
            Rect src = CONST.getSrcRect(getBitmapByLevel(level));
            canvas.drawBitmap(getBitmapByLevel(level), src, CONST.getRect(ix, iy), paint);
        }

        @Override
        public int cost()
        {
            int ret = 0;
            for (int i = Tower.this.level + 1; i <= level; i++)
            {
                ret += getCostByLevel(i);
            }
            return ret;
        }

        @Override
        public float getRange()
        {
            return getRangeByLevel(level);
        }

        @Override
        public void act(int ix, int iy)
        {
            if (GameSurfaceView.moneymanager.isEnough(cost()))
            {
                for(int i = Tower.this.level+1;i<=level;i++)
                {
                    setLevel(i);
                }
            }
        }

        @Override
        public String getString()
        {
            return "升级需要:" + cost() + " 提升能力";
        }
    }

    public Tower()
    {
    }

    public CreateTower getUpAndRemove()
    {
        CreateTower ret = new CreateTower(ix, iy , range);
        ret.add(new RemoveTower());
        Log.i("level", ""+level);
        for (int i = level + 1; i < 3; i++)
        {
            ret.add(new UpgradeTower(i));
        }
        return ret;
    }

    public Tower(int ix, int iy, int attack, float range, float gap)
    {
        this.ix = ix;
        this.iy = iy;
        this.attack = attack;
        setRange(range);
        this.cd = 0;
        this.gap = gap;
        Rect rect = CONST.getRect(ix, iy);
        x = rect.left + CONST.MAP.HALFSIZE;
        y = rect.top + CONST.MAP.HALFSIZE;
        GameSurfaceView.map[ix][iy] = CONST.MAP.NOTFREE;
        speed = 800;
    }

    public void setRange(float range)
    {
        this.range = range * CONST.MAP.SIZE;
    }

    public static float getDisplayRange(float range)
    {
        return (range * CONST.MAP.SIZE);
    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        Rect src = CONST.getSrcRect(getBitmap());
        Rect dst = CONST.getRect(ix, iy);
        canvas.drawBitmap(getBitmap(), src, dst, paint);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o;
    }

    @Override
    public void remove()
    {
        GameSurfaceView.map[ix][iy] = CONST.MAP.FREE;
        GameSurfaceView.moneymanager.add(getCost());
        GameSurfaceView.towermanager.removeTower(this);
    }

    public Monster canattack()
    {
        Monster ret = null;
        double mindis = 2000;
        for (Monster monster : GameSurfaceView.monstermanager.getAllMonsters())
        {
            float tx = monster.x;
            float ty = monster.y;
            double dis = Math.sqrt((x - tx) * (x - tx) + (y - ty) * (y - ty));
            if (dis < mindis && dis < range && monster.isactive)
            {
                mindis = dis;
                ret = monster;
            }
        }
        return ret;
    }

    @Override
    public boolean act()
    {
        if (cd > 0)
        {
            cd -= 1f / CONST.FPS;
            return false;
        }
        Monster monster = canattack();
        if (monster == null)
        {
            return false;
        }
        else
        {
            float tx = monster.x;
            float ty = monster.y;

            float dy = ty - y;
            float dx = tx - x;

            float len = (float) Math.sqrt(dy * dy + dx * dx);
            dy = dy / len;
            dx = dx / len;
            dy *= speed / CONST.FPS;
            dx *= speed / CONST.FPS;
            cd = gap;
            fireBullet(dx, dy);
        }
        return false;
    }
}