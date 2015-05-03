package com.iiitd.hostel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StudentHomeActivity extends ActionBarActivity implements View.OnClickListener
{

    private
    Button apply;
    private Button editDetails;
    private Button status;
    private String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        emailId=getIntent().getStringExtra("emailId");

        Log.d("emailId in Home", emailId);

        apply=(Button)findViewById(R.id.apply);
        //editDetails=(Button)findViewById(R.id.editDetail);
        status=(Button)findViewById(R.id.status);

        apply.setOnClickListener(this);
        //editDetails.setOnClickListener(this);
        status.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
    public void onClick(View view)
    {
        if(view.getId() == R.id.apply)
        {
            Intent intent=new Intent(this,ApplyActivity.class);
            intent.putExtra("emailId",emailId);
            startActivity(intent);

        }
        /*else if(view.getId() == R.id.editDetail)
        {
            Intent intent=new Intent(this,EditDetailsActivity.class);
            intent.putExtra("emailId",emailId);
            startActivity(intent);

        }*/
        else if(view.getId() == R.id.status)
        {


        }

    }
}
