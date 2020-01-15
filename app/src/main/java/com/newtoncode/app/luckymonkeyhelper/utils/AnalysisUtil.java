package com.newtoncode.app.luckymonkeyhelper.utils;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class AnalysisUtil {

    public static void logEvent(String tag, String key, Object value) {
        try {
            Map<String, String> dict = new HashMap<String, String>();
            dict.put(key, String.valueOf(value));
//            FlurryAgent.logEvent(tag, dict);
            if (Fabric.isInitialized()) {
                CustomEvent event = new CustomEvent(tag);
                if (key != null && value != null) {
                    event.putCustomAttribute(key, value.toString());
                }
                Answers.getInstance().logCustom(event);
            }
        } catch (Exception e) {
        }
    }

    public static void logEvent(String tag) {
//        FlurryAgent.logEvent(tag);
        if (Fabric.isInitialized()) {
            CustomEvent event = new CustomEvent(tag);
            Answers.getInstance().logCustom(event);
        }
    }

//    public static void endTime(String tag) {
//        FlurryAgent.endTimedEvent(tag);
//    }

    public static void endEvent(String tag, String key, Object value) {
        Map<String, String> dict = new HashMap<String, String>();
        dict.put(key, String.valueOf(value));
//        FlurryAgent.endTimedEvent(tag, dict);
        CustomEvent event = new CustomEvent(tag);
        if (key != null && value != null) {
            event.putCustomAttribute(key, value.toString());
        }
        Answers.getInstance().logCustom(event);
    }

//    public static void onStartSession(Context context) {
//        try {
//            FlurryAgent.onStartSession(context);
//        } catch (Exception e) {
//        }
//    }
//
//    public static void onEndSession(Context context) {
//        try {
//            FlurryAgent.onEndSession(context);
//        } catch (Exception e) {
//        }
//    }
}
