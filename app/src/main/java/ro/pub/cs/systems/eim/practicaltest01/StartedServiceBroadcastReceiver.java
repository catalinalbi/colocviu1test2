package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    private TextView messageTextView;

    // TODO: exercise 10 - default constructor
    public StartedServiceBroadcastReceiver() {
        this.messageTextView = null;
    }

    public StartedServiceBroadcastReceiver(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: exercise 7 - get the action and the extra information from the intent
        // and set the text on the messageTextView
        String message = "";
        if (intent.getAction() == Constants.SEND_DATA) {
            message = intent.getStringExtra(Constants.DATA);
        }

        Log.v(Constants.TAG, message);
    }
}
