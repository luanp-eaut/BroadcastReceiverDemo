package eaut.it.mobileappdev.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the Broadcast Receiver receives a broadcast
        String broadcastAction = intent.getAction();
        if (intent.getAction() != null &&
                intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
            String msg = isAirplaneModeOn ? "Airplane Mode is ON" : "Airplane mode is OFF";
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            Log.i("BroadcastReceiverDemo", msg);
        }
    }
}