package com.huyue.tdtest.createtower;

import android.graphics.Canvas;

public interface TowerCreatorObject
{
    void draw(Canvas canvas, int ix, int iy);

    int cost();

    void act(int ix, int iy);
    
    float getRange();
    
    public String getString();
}
