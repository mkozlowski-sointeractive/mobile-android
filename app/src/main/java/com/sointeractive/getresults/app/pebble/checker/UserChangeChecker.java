package com.sointeractive.getresults.app.pebble.checker;

import android.util.Log;

import com.sointeractive.getresults.app.data.App;
import com.sointeractive.getresults.app.pebble.responses.ResponseItem;

import java.util.Collection;
import java.util.LinkedList;

public class UserChangeChecker {
    private static final String TAG = UserChangeChecker.class.getSimpleName();

    public static void check(final ResponseItem oldUser, final ResponseItem newUser) {
        if (!newUser.equals(oldUser)) {
            Log.i(TAG, "Checker: User data changed");
            final Collection<ResponseItem> loginResponse = new LinkedList<ResponseItem>();
            loginResponse.add(newUser);
            App.getPebbleConnector().sendDataToPebble(loginResponse);
        }
    }
}
