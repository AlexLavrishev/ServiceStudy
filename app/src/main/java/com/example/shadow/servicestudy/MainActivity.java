package com.example.shadow.servicestudy;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String PARAM_PINTENT = "PendingIntent" ;
    public static final int STATUS_START = 100 ;
    public static final int STATUS_FINISH = 200 ;
    Button btn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        PendingIntent pIntent;
        Intent intent;
        Intent nullIntent = new Intent();

        pIntent = createPendingResult(1, nullIntent, 0);
        intent = new Intent(this, MyService.class).putExtra(PARAM_PINTENT, pIntent);
        startService(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case STATUS_START:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case STATUS_FINISH:
                progressBar.setVisibility(View.INVISIBLE);
                String key1 = data.getStringExtra("Key1");
                String key2 = data.getStringExtra("Key2");
                String key3 = data.getStringExtra("Key3");
                String key4 = data.getStringExtra("Key4");
                System.out.println(key1);
                System.out.println(key2);
                System.out.println(key3);
                System.out.println(key4);

                break;

        }

    }

}
