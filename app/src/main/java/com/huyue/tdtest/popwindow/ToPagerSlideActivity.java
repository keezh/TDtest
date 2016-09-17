package com.huyue.tdtest.popwindow;

import com.huyue.tdtest.PagerSlideActivity;

import android.content.Intent;

public class ToPagerSlideActivity implements MockDialog.PopWindowListener
{
    @Override
    public void onTouch(MockDialog mockDialog)
    {
        Intent intent = new Intent(mockDialog, PagerSlideActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mockDialog.startActivity(intent);
    }
}
