package com.iiitd.hostel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ApplyActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    String emailId;
    Spinner spinnerFloors,spinnerRoomType;
    private String[] floors= {"0","1","2","3","4","5","6"};
    private String[] roomType= {"SINGLE","DOUBLE","TRIPLE"};
    ArrayAdapter<String> adapter_floors;
    ArrayAdapter<String> adapter_roomType;
    private Button submit;
    private Button reset;

    private ArrayList<String> classMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        emailId=getIntent().getStringExtra("emailId");
        Log.d("emailId in Apply",emailId);

        classMembers=new ArrayList<String>();
        classMembers.add(0,emailId);

        submit=(Button)findViewById(R.id.submitbut);
        submit.setOnClickListener(this);

        spinnerFloors = (Spinner) findViewById(R.id.floorspinner);
        spinnerRoomType= (Spinner) findViewById(R.id.roomspinner);

        adapter_floors = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, floors);
        adapter_roomType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomType);

        spinnerFloors.setAdapter(adapter_floors);
        spinnerRoomType.setAdapter(adapter_roomType);


        spinnerFloors.setOnItemSelectedListener(this);
        spinnerRoomType.setOnItemSelectedListener(this);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apply, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
    {
        if(adapter_floors.equals(adapterView.getAdapter()))
        {
            classMembers.add(spinnerFloors.getSelectedItem().toString());
            //Log.d("State of user", s.getState());
            TextView hai=(TextView)view;
            Toast.makeText(this, "You Selected " + hai.getText(), Toast.LENGTH_LONG).show();
        }

        else if(adapter_roomType.equals(adapterView.getAdapter()))
        {
            classMembers.add(spinnerRoomType.getSelectedItem().toString());
            //Log.d("degree of user", s.getDegree());
            TextView hai=(TextView)view;
            Toast.makeText(this,"You Selected "+hai.getText(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    @Override
    public void onClick(View v)
    {
        new EndPointApply(this).execute(classMembers);

    }
}
