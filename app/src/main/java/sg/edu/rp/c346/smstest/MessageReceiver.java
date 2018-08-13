package sg.edu.rp.c346.smstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 16039013 on 13/8/2018.
 */

public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras(); // receive the msg
        try {
            if (bundle != null) { // if not == null -> not empty
                Object[] pdusObj = (Object[]) bundle.get("pdus"); // get by refer to thr name "pdus" -- fix
                SmsMessage currentMessage; // create the new variable for the SMS
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0], format);
                } else {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                }
                // Obtain the originating phone number (sender's number)
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                // Obtain the message body
                String message = currentMessage.getDisplayMessageBody();
                // Display in Toast
                Toast.makeText(context, "Sender Number: " + senderNum +
                        ", Message: " + message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Error: " + e);
        }

    }
}
