package com.huyue.tdtest.popwindow;

public class NullListener implements MockDialog.PopWindowListener
{

    @Override
    public void onTouch(MockDialog mockDialog)
    {
        mockDialog.finish();
    }

}
