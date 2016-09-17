package com.huyue.tdtest.tdactive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.anim.Anim;
import com.huyue.tdtest.constant.CONST;
import com.example.com.huyue.tdtest.R;

public class Monster extends TDActiveObject
{
    /*
     * x,y表示位置 hp是血量 isactive表示该对象是否已经活跃 若已经执行则跳出 bitmap表示当前绘制图像
     * waittime表示等待入场时间 speed是速度比率
     */
    float speed;
    Bitmap bitmap;
    public int hp;
    public int maxhp;
    public Boolean isactive = false;
    public float waittime;
    int money;
    Anim.AnimObject animobject;
    public static Bitmap freezePic = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.ice2);;

    float freezeTime;
    float slow;
    float comaTime;

    /*
     * 初始化
     */
    public Monster(int ix, int iy, float speed, float waittime, int maxhp, int money, Anim.AnimObject animObject)
    {

        this.ix = ix;
        this.iy = iy;
        Rect rect = CONST.getRect(ix, iy);
        x = rect.left + CONST.MAP.HALFSIZE;
        y = rect.top + CONST.MAP.HALFSIZE;
        this.speed = speed;
        this.maxhp = maxhp;
        this.waittime = waittime;
        this.animobject = animObject;
        freezeTime = 0;
        comaTime = 0;
        slow = 1;
        this.money = money;
        bitmap = animObject.getFirstBitmap();
        creat();
    }

    public void freeze(float dmg, float freezetime, float slow)
    {
        hp -= dmg;
        if (freezetime <= 0)
        {
            this.slow = slow;
            this.freezeTime = freezetime;
            return;
        }
        if (slow <= this.slow)
        {
            this.slow = slow;
            this.freezeTime = freezetime;
        }
    }

    public void coma(float dmg, float comaTime)
    {
        hp -= dmg;
        if (this.comaTime < comaTime)
        {
            this.comaTime = comaTime;
        }
    }

    public void damage(float dmg)
    {
        hp -= dmg;
    }

    /*
     * 调用该线程的draw方法来画出怪物和血条 需要传入画布
     */
    @Override
    public void draw(Canvas canvas)
    {
        if (!isactive)
        {
            return;
        }
        if (hp <= 0)
        {
            hp = 0;
        }

        // 绘制怪物
        Paint paint = new Paint();
        Rect src = CONST.getSrcRect(bitmap);
        Rect dst = new Rect((int) x - CONST.MAP.HALFSIZE, (int) y - CONST.MAP.HALFSIZE, (int) x
                + CONST.MAP.HALFSIZE, (int) y + CONST.MAP.HALFSIZE);
        canvas.drawBitmap(bitmap, src, dst, paint);

        if (freezeTime > 0)
        {
//            paint.setAlpha();
            src = CONST.getSrcRect(freezePic);
            canvas.drawBitmap(freezePic, src, dst, paint);
        }

        if (comaTime > 0)
        {
            //TODO
        }

        // 绘制血条
        paint.setColor(Color.GREEN);
        Rect rect = new Rect((int) x - CONST.MAP.HALFSIZE, (int) y - CONST.MAP.HALFSIZE - 15,
                (int) x + CONST.MAP.HALFSIZE, (int) y - CONST.MAP.HALFSIZE - 5);
        canvas.drawRect(rect, paint);
        rect.left += (int) ((float) hp / maxhp * CONST.MAP.SIZE);
        paint.setColor(Color.RED);
        canvas.drawRect(rect, paint);


    }

    // 怪物初始化 血量初始化
    public void creat()
    {
        hp = maxhp;
    }

    // 怪物死亡后设置
    public void die()
    {
        GameSurfaceView.moneymanager.add(money);
        remove();
        hp = 0;
    }

    public void getTarget()
    {
        if(money >= 150)
        {
            GameSurfaceView.missionManager.stealEgg(5);
        }
        else if(money>=50)
        {
            GameSurfaceView.missionManager.stealEgg(3);
        }
        else
        {
            GameSurfaceView.missionManager.stealEgg(1);
        }
        remove();
    }

    public boolean check()
    {
        Rect rect = CONST.getRect(ix, iy);
        if (Math.abs(x - rect.left - CONST.MAP.HALFSIZE)
                + Math.abs(y - rect.top - CONST.MAP.HALFSIZE) >= CONST.MAP.SIZE)
        {
            int[] tp = CONST.IDIRECTION[GameSurfaceView.map[ix][iy]];
            ix += tp[0];
            iy += tp[1];
            rect = CONST.getRect(ix, iy);
            x = rect.left + CONST.MAP.HALFSIZE;
            y = rect.top + CONST.MAP.HALFSIZE;
        }

        if (GameSurfaceView.map[ix][iy] == CONST.MAP.TARGET)
        {
            getTarget();
            return true;
        }

        if (hp <= 0)
        {
            die();
            return true;
        }
        return false;
    }

    @Override
    public boolean act()
    {
        if (waittime > 0)
        {
            waittime -= 1.0f / CONST.FPS;
            return false;
        }
        if (!isactive)
        {
            isactive = true;
            animobject.start();
        }

        anim();

        float rate = CONST.NORMALSPEED * speed / CONST.FPS;
        if (freezeTime > 0)
        {
            rate *= slow;
            freezeTime -= 1f / CONST.FPS;
        }

        if(comaTime > 0)
        {
            comaTime -= 1f / CONST.FPS;
        }

        if (comaTime <= 0)
        {
            x += CONST.XYDIRECTION[GameSurfaceView.map[ix][iy]][0] * rate;
            y += CONST.XYDIRECTION[GameSurfaceView.map[ix][iy]][1] * rate;
        }
        return check();
    }

    @Override
    public void remove()
    {
        GameSurfaceView.monstermanager.removeMonster(this);
        animobject.remove();
    }

    public void anim()
    {
        bitmap = animobject.getBitmap();
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this;
    }
}
