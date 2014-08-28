package com.fastbleep.fastbleepnotes;

import org.json.JSONObject;

/**
 * Created by Ian on 16/08/2014.
 */
public interface OnTaskCompleted {
    void onTaskCompleted(JSONObject result);
}