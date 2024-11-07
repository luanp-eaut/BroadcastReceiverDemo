package eaut.it.mobileappdev.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BroadcastReceiver systemReceiver;
    private BroadcastReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registerSystemReceiver();
        registerLocalReceiver();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(systemReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setAction(BroadcastActions.LOCAL_ACTION);
        intent.putExtra("data", "This is a test message sent from local broadcast manager");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void registerLocalReceiver() {
        IntentFilter filter = new IntentFilter(BroadcastActions.LOCAL_ACTION);
        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BroadcastActions.LOCAL_ACTION)) {
                    String msg = intent.getStringExtra("data");
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, filter);
    }

    private void registerSystemReceiver() {
        IntentFilter intentFilter = new IntentFilter(BroadcastActions.SYSTEM_ACTION);
        systemReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null &&
                        intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                    boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
                    String msg = isAirplaneModeOn ? "Airplane Mode is ON" : "Airplane mode is OFF";
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    Log.i("BroadcastReceiverDemo", msg);
                }
            }
        };
        registerReceiver(systemReceiver, intentFilter);
    }
}