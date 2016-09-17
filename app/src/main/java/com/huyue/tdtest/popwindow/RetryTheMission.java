package com.huyue.tdtest.popwindow;

import com.huyue.tdtest.GameSurfaceView;

public class RetryTheMission implements MockDialog.PopWindowListener
{

    @Override
    public void onTouch(MockDialog mockDialog)
    {
        GameSurfaceView.missionManager.reset();
    }
}
