package app.laundrystation.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RealTimeReceiver extends BroadcastReceiver {
    public static MessageReceiverListener messageReceiverListene;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {

            if (intent.getAction().equals("app.laundrystation.receiver")) {
                if (intent.getStringExtra("content") != null)
                    messageReceiverListene.onMessageChanged(intent.getStringExtra("content"));
            }
        } else
            Log.i("onReceive", "onReceive: null");
    }

    public interface MessageReceiverListener {
        void onMessageChanged(String msg);
    }
}
