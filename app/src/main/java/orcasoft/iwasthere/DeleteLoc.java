package orcasoft.iwasthere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Orca on 2/9/2017.
 */
public class DeleteLoc extends Activity{
    Button yesBtn, noBtn;
    String locName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmdeletepopup);
        locName = getIntent().getStringExtra("LocName");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.25));

        yesBtn = (Button) findViewById(R.id.yes);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listScreen = new Intent(getApplicationContext(), MainActivity.class);
                listScreen.putExtra("LocName", locName);
                listScreen.putExtra("YesOrNo", "Yes");
                setResult(1001, listScreen);
                finish();
            }
        });

        noBtn = (Button) findViewById(R.id.no);
        noBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
