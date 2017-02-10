package orcasoft.iwasthere;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    ListView l;
    Map<String, Location> locationMap;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("IWasThere", "onCreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        l = (ListView) findViewById(R.id.locationList);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        l.setAdapter(adapter);
        // add all location names from database into the application
        l.setOnItemClickListener(this);
        l.setOnItemLongClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputTitleIntent = new Intent(MainActivity.this, InputTitle.class);
                startActivityForResult(inputTitleIntent, 1000);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        locationMap = new HashMap<String, Location>();
        //Get Locations from Database and store them in locationMap
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Toast.makeText(this, "Result Code: " + requestCode, Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1000) {

            String locName = data.getStringExtra("LocName");
            Double longitude = data.getDoubleExtra("Longitude", 0);
            Double latitude = data.getDoubleExtra("Latitude", 0);
            //Snackbar.make(view, locName, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Location loc =  new Location("");
            loc.setLongitude(longitude);
            loc.setLatitude(latitude);
            addNewLocation(locName, loc);
        }
        if(resultCode == 1001){
            String confirmResult = data.getStringExtra("YesOrNo");
            String deletlocName;
            if(confirmResult.equals("Yes")) {
                deletlocName = data.getStringExtra("LocName");
                //Toast.makeText(this, deletlocName +  " was deleted", Toast.LENGTH_SHORT).show();
                locationMap.remove(deletlocName);
                adapter.remove(deletlocName);
                // Remove item with name as locName from database
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("IWasThere", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("IWasThere", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("IWasThere", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("IWasThere", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("IWasThere", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("IWasThere", "onPause");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;
        //Snackbar.make(view, "You clicked on " + temp.getText(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //Toast.makeText(this, temp.getText() +  "" + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("IWasThere", "LongClicked");
        TextView temp = (TextView) view;
        String locationName = temp.getText().toString();
        Intent deleteLocIntent = new Intent(MainActivity.this, DeleteLoc.class);
        deleteLocIntent.putExtra("LocName", locationName);
        startActivityForResult(deleteLocIntent, 1001);

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNewLocation(String locName, Location loc){
        locationMap.put(locName, loc);
        // Connect to database
        // Add location name, longitude and latitude to database
        adapter.add(locName);
        //Toast.makeText(this, locName + ":" + longitude + "," + latitude + " was added to the location list", Toast.LENGTH_LONG).show();
    }
}
