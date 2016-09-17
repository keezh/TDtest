package com.huyue.tdtest.constant;

import android.graphics.Bitmap;
import android.graphics.Rect;

public final class CONST
{
    /*
     * IDIRECTION和XYDIRECTION分表表示处于路径上某个格子时候的下标行进方向和坐标行进方向
     */

    public static final int[][] IDIRECTION = new int[][]
            {
                    { -1, 0 },
                    { 1, 0 },
                    { 0, -1 },
                    { 0, 1 } };
    public static final int[][] XYDIRECTION = new int[][]
            {
                    { 0, -1 },
                    { 0, 1 },
                    { -1, 0 },
                    { 1, 0 } };

    public static Rect getRect(int i, int j)
    {
        Rect rect = new Rect(CONST.SCREEN.LEFT + j * CONST.MAP.SIZE, CONST.SCREEN.TOP + i
                * CONST.MAP.SIZE, CONST.SCREEN.LEFT + (j + 1) * CONST.MAP.SIZE, CONST.SCREEN.TOP
                + (i + 1) * CONST.MAP.SIZE);
        return rect;
    }

    public static Rect getSrcRect(Bitmap bitmap)
    {
        return new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
    }

    /*
     * 描述地图基本信息 UP DOWN LEFT RIGHT表示地图中行进方向(默认是怪物位置) TARGET是终点的标志 FREE表示可建筑区域
     * SIZE表示怪物尺寸 WIDTH和HEIGHT表示宽高 现在只处理了竖形态 START未使用
     */

    public final static class MAP
    {
        public final static int UP = 0;
        public final static int DOWN = 1;
        public final static int LEFT = 2;
        public final static int RIGHT = 3;

        public final static int START = 5;
        public final static int TARGET = 6;

        public final static int FREE = 4;
        public final static int NOTFREE = 8;

        public static int SIZE = 60;
        public static int HALFSIZE = 30;

        public final static int WIDTH = 9;
        public final static int HEIGHT = 14;
    }

    /*
     * 描述可绘图区域 暂时只使用到 TOP表示地图开始区域 RIGHT 表示屏幕右边界 BOTTOM 表示屏幕底边
     */

    public static class SCREEN
    {
        public static int LEFT = 0;
        public static int RIGHT = 540;// 可改
        public static int TOP = 60;// 可改
        public static int BOTTOM = 960;// 可改
    }

    /*
     * FPS是每秒刷新次数 NORMALSPEED是每秒行进速度
     */
    public static int FPS = 60;
    public static int NORMALSPEED = 100;// 适配可变

}
