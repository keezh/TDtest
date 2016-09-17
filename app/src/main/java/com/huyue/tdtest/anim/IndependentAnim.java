package com.huyue.tdtest.anim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.tdactive.TDActiveObject;

public class IndependentAnim extends TDActiveObject
{
    /*
     * animObject
     */
    private float width;
    private float height;
    private Anim.AnimObject animObject;
    private Bitmap bitmap;
    
    public IndependentAnim(Anim.AnimObject animObject,float x,float y,float width,float height)
    {
        this.x = x;
        this.y =y ;
        this.width = width / 60 * CONST.MAP.SIZE;
        this.height = height / 60 * CONST.MAP.SIZE;
        this.animObject = animObject;
        animObject.start();
        bitmap = animObject.getFirstBitmap();
    }
    
    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        int halfwidth =(int) width /2;
        int halfheight =(int) height /2;
        Rect src = CONST.getSrcRect(bitmap);
        Rect dst = new Rect((int)x - halfwidth, (int)y - halfheight, 
                (int)x + halfwidth, (int)y + halfheight);
        canvas.drawBitmap(bitmap, src , dst, paint);        
    }
    
    @Override
    public boolean act()
    {
        bitmap = animObject.getBitmap();
        if(bitmap == null)
        {
            remove();
            return true;
        }
        return false;
    }
    
    @Override
    public void remove()
    {
        GameSurfaceView.animManager.removeAnim(this);
    }
}
