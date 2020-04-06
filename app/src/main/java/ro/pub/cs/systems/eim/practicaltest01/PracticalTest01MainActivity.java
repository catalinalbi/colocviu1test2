package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button leftButton, rightButton, navigateButton;
    EditText leftText, rightText;
    Context context;
    Intent service;
    IntentFilter filter;
    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        context = this;
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        navigateButton = findViewById(R.id.navigate_button);
        leftText = findViewById(R.id.left_editText);
        rightText = findViewById(R.id.right_editText);
        service = new Intent();
        service.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01", "PracticalTest01Service"));

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer number = Integer.valueOf(leftText.getText().toString());
                number++;
                leftText.setText(number.toString());
                if (number + Integer.valueOf(rightText.getText().toString()) >= 10) {
                    service.putExtra("medieAritmetica", ((double)number + Integer.valueOf(rightText.getText().toString()) / 2));
                    service.putExtra("medieGeometrica", (Math.sqrt((double)number * number + Integer.valueOf(rightText.getText().toString()) * (double)Integer.valueOf(rightText.getText().toString()))));
                    startService(service);
                    Log.v("main", "start service");
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer number = Integer.valueOf(rightText.getText().toString());
                number++;
                rightText.setText(number.toString());
                if (number + Integer.valueOf(leftText.getText().toString()) >= 10) {
                    service.putExtra("medieAritmetica", ((double)number + Integer.valueOf(rightText.getText().toString()) / 2));
                    service.putExtra("medieGeometrica", (Math.sqrt((double)number * number + Integer.valueOf(leftText.getText().toString()) * (double)Integer.valueOf(leftText.getText().toString()))));
                    startService(service);
                    Log.v("main", "start service");
                }
            }
        });

        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PracticalTest01SecondaryActivity.class);
                intent.putExtra("total", Integer.valueOf(leftText.getText().toString()) + Integer.valueOf(rightText.getText().toString()));
                startActivityForResult(intent, 0);
            }
        });

        filter = new IntentFilter();
        filter.addAction(Constants.SEND_DATA);

        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        leftText.setText(((Integer)savedInstanceState.getInt("leftNumber")).toString());
        rightText.setText(((Integer)savedInstanceState.getInt("rightNumber")).toString());
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("leftNumber", Integer.valueOf(leftText.getText().toString()));
        outState.putInt("rightNumber", Integer.valueOf(rightText.getText().toString()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Constants.OK) {
                Toast.makeText(getApplicationContext(),"OK WAS PRESSED", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"CANCEL WAS PRESSED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        // ...
        stopService(service);
    }

    public void onResume() {
        registerReceiver(startedServiceBroadcastReceiver, filter);
        super.onResume();
    }

    public void onPause() {
        unregisterReceiver(startedServiceBroadcastReceiver);
        super.onPause();
    }
}
