package com.sointeractive.getresults.app.pebble.receivers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sointeractive.android.kit.PebbleKit;
import com.sointeractive.android.kit.util.PebbleDictionary;
import com.sointeractive.getresults.app.config.Settings;
import com.sointeractive.getresults.app.data.App;
import com.sointeractive.getresults.app.pebble.communication.Request;
import com.sointeractive.getresults.app.pebble.responses.ResponseItem;

import java.util.Collection;

public class PebbleDataReceiver extends PebbleKit.PebbleDataReceiver {
    private static final String TAG = PebbleDataReceiver.class.getSimpleName();

    private final Handler handler = new Handler();

    public PebbleDataReceiver() {
        super(Settings.PEBBLE_APP_UUID);
    }

    @Override
    public void receiveData(final Context context, final int transactionId, final PebbleDictionary data) {
        Log.i(TAG, "Event: Message received");
        Log.d(TAG, "Received data: " + data.toJsonString());
        handler.post(new Runnable() {
            @Override
            public void run() {
                sendAckToPebble(context, transactionId);
                response(data);
            }
        });
    }

    private void sendAckToPebble(final Context context, final int transactionId) {
        Log.i(TAG, "Action: Acknowledgement sent to Pebble, transactionId: " + transactionId);
        PebbleKit.sendAckToPebble(context, transactionId);
    }

    private void response(final PebbleDictionary data) {
        final Collection<ResponseItem> response = Request.getResponse(data);
        App.getPebbleConnector().sendDataToPebble(response);
    }
}