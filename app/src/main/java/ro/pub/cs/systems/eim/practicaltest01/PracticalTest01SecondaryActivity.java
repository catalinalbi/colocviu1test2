package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.xml.transform.Result;

public class PracticalTest01SecondaryActivity extends Activity {

    Button okButton, cancelButton;
    EditText totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);
        totalText = findViewById(R.id.total_editText);

        Intent intent = getIntent();
        Integer total = intent.getIntExtra("total", 0);
        totalText.setText(total.toString());

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.OK);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.CANCEL);
                finish();
            }
        });

    }

}
