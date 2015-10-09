package com.example.kyoungbae.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class BtnTest extends Activity {
    class ButtonEvent implements View.OnClickListener{
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"Hello world",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn);

        ButtonEvent buttonclick = new ButtonEvent();
        btn.setOnClickListener(buttonclick);

    }

}
