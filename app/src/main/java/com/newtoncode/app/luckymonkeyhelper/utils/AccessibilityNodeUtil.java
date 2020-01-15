package com.newtoncode.app.luckymonkeyhelper.utils;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by jeanboy on 2016/12/5.
 */

public class AccessibilityNodeUtil {

    public static void doClick(AccessibilityNodeInfo nodeInfo) {
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }
}
