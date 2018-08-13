package sg.edu.rp.c346.smstest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSMS;
    BroadcastReceiver br = new MessageReceiver(); // refer to the java class (AnotherBroadcastReceiver)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        btnSMS = findViewById(R.id.button);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        this.registerReceiver(br, filter);

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("5556", null, "Hello world",null,null);
            }


        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }

    private void checkPermission(){
        int permissionSendSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int permissionRecvSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);
        if (permissionSendSMS != PackageManager.PERMISSION_GRANTED &&
                permissionRecvSMS != PackageManager.PERMISSION_GRANTED) {
            String[] permissionNeeded = new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissionNeeded, 1);

        }
    }
}
