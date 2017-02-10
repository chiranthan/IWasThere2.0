package orcasoft.iwasthere;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Orca on 2/8/2017.
 */
public class InputTitle extends Activity implements LocationListener {

    TextView accuracyText;

    Button cancelBtn;
    Button addBtn;

    EditText locNameEditText;
    LocationManager locationManager;
    LocationListener locationListener;
    Double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        accuracyText = (TextView) findViewById(R.id.accuracy);
        accuracyText.setText("Waiting for GPS...");
        locNameEditText = (EditText) findViewById(R.id.locName);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.50));
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addBtn = (Button) findViewById(R.id.add);
        addBtn.setEnabled(false);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locNameEditText.getText().toString().trim().length() <= 0){
                    showError();
                    return;
                }
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,);
                Intent listScreen = new Intent(getApplicationContext(), MainActivity.class);
                //Sending the data to Activity_A
                String locName = locNameEditText.getText().toString();
                listScreen.putExtra("LocName", locName);
                listScreen.putExtra("Longitude", longitude);
                listScreen.putExtra("Latitude", latitude);
                setResult(1000, listScreen);
                // TODO:
                // This function closes Activity Two
                // Hint: use Context's finish() method
                finish();
            }
        });
    }

    private void showError() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        locNameEditText.startAnimation(shake);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("IWasThere>InputTitle", "onResume");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        float accuracy = location.getAccuracy();

        if (accuracy <= 7) {
            accuracyText.setText("Accuracy: " + "Good");
            accuracyText.setTextColor(Color.GREEN);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            addBtn.setEnabled(true);
        } else {
            addBtn.setEnabled(false);
            accuracyText.setText("Accuracy: " + "Bad");
            accuracyText.setTextColor(Color.RED);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
