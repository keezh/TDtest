package com.huyue.tdtest.popwindow;

public class ExitApp implements MockDialog.PopWindowListener
{

    @Override
    public void onTouch(MockDialog mockDialog)
    {
        System.exit(0);
    }
}
