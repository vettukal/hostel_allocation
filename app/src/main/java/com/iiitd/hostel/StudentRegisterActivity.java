package com.iiitd.hostel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.util.DateTime;
import com.iiitd.hostel.backend.studentApi.model.Student;

import java.util.Calendar;
import java.util.Date;


public class StudentRegisterActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinnerStates,spinnerGender,spinnerDegree,spinnerYear,spinnerBranch;
    private String[] state = {"Andaman & Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chattisgarh","Dadar & Nagar Haveli","Daman & Diu","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu & Kashmir","Jharkhand","Karnataka","Kerala","Lakshadeep","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","New Delhi","Orissa","Pondicherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttaranchal","Uttar Pradesh","West Bengal"};
    private String[] gender= {"Male","Female"};
    private String[] degree= {"B.Tech","M.Tech","PHd"};
    private String[] year= {"1st","2nd","3rd","4th"};
    private String[] branch= {"CSE","ECE"};
    private Student s;
    ArrayAdapter<String> adapter_gender;
    ArrayAdapter<String> adapter_branch;
    ArrayAdapter<String> adapter_year;
    ArrayAdapter<String> adapter_degree;
    ArrayAdapter<String> adapter_state;
    private String emailId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        emailId=getIntent().getStringExtra("emailId");

        s=new Student();

        Button submit = (Button)findViewById(R.id.button);
        submit.setOnClickListener(this);

        Button reset = (Button)findViewById(R.id.button2);
        reset.setOnClickListener(this);



        spinnerStates = (Spinner) findViewById(R.id.spinner);
        spinnerGender= (Spinner) findViewById(R.id.spinner2);
        spinnerDegree = (Spinner) findViewById(R.id.spinner3);
        spinnerYear = (Spinner) findViewById(R.id.spinner4);
        spinnerBranch = (Spinner) findViewById(R.id.spinner5);

        adapter_gender = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, gender);
        adapter_degree = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, degree);
        adapter_year = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, year);
        adapter_branch = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, branch);
        adapter_state = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, state);

        Log.d("state adapter",adapter_state+"");



        spinnerStates.setAdapter(adapter_state);
        spinnerDegree.setAdapter(adapter_degree);
        spinnerGender.setAdapter(adapter_gender);
        spinnerYear.setAdapter(adapter_year);
        spinnerBranch.setAdapter(adapter_branch);

        spinnerStates.setOnItemSelectedListener(this);
        spinnerBranch.setOnItemSelectedListener(this);
        spinnerYear.setOnItemSelectedListener(this);
        spinnerDegree.setOnItemSelectedListener(this);
        spinnerGender.setOnItemSelectedListener(this);

    }


    // Taking all spinners value
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

        Log.d(adapter_state+"",adapterView.getAdapter()+"");
        if(adapter_state.equals(adapterView.getAdapter()))
        {
            s.setState(spinnerStates.getSelectedItem().toString());
            Log.d("State of user", s.getState());
            TextView hai=(TextView)view;
            Toast.makeText(this, "You Selected " + hai.getText(), Toast.LENGTH_LONG).show();
        }

        else if(adapter_degree.equals(adapterView.getAdapter()))
        {
            s.setDegree(spinnerDegree.getSelectedItem().toString());
            Log.d("degree of user", s.getDegree());
            TextView hai=(TextView)view;
            Toast.makeText(this,"You Selected "+hai.getText(),Toast.LENGTH_LONG).show();
        }
        else if(adapter_gender.equals(adapterView.getAdapter()))
        {
            s.setGender(spinnerGender.getSelectedItem().toString());
            Log.d("Gender of user", s.getGender());
            TextView hai=(TextView)view;
            Toast.makeText(this,"You Selected "+hai.getText(),Toast.LENGTH_LONG).show();
        }
        else if(adapter_year.equals(adapterView.getAdapter()))
        {
            s.setDegreeYear(spinnerYear.getSelectedItem().toString());
            Log.d("Year of user", s.getDegreeYear());
            TextView hai=(TextView)view;
            Toast.makeText(this,"You Selected "+hai.getText(),Toast.LENGTH_LONG).show();
        }
        else if(adapter_branch.equals(adapterView.getAdapter()))
        {
            s.setBranch(spinnerBranch.getSelectedItem().toString());
            Log.d("Branch of user", s.getBranch());
            TextView hai=(TextView)view;
            Toast.makeText(this,"You Selected "+hai.getText(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.button2)
        {
            ViewGroup group = (ViewGroup) findViewById(R.id.Details);
            clearForm(group);
        }
        else if(view.getId() == R.id.button)
        {

            getDetails(view);
        }
    }
    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText)
            {
                ((EditText)view).setText("");
            }
            if (view instanceof Spinner){
                ((Spinner)view).setSelection(0);
            }
            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    private void getDetails(View view)
    {

        s.setName(((EditText) findViewById(R.id.editText)).getText().toString());
        s.setAddress(((EditText) findViewById(R.id.editText2)).getText().toString());
        s.setCity(((EditText) findViewById(R.id.editText3)).getText().toString());
        s.setPinCode(Integer.parseInt(((EditText) findViewById(R.id.editText4)).getText().toString()));
        s.setContactNumber(((EditText) findViewById(R.id.editText5)).getText().toString());

        String dateInString = ((EditText) findViewById(R.id.editText6)).getText().toString();
        String dateParts[]=dateInString.split("/");
        int year=Integer.parseInt(dateParts[2]);
        int month=Integer.parseInt(dateParts[1]);
        int day=Integer.parseInt(dateParts[0]);

        Date date=new Date(year,month-1,day);

        Calendar cal = Calendar.getInstance();

        cal.set(year,month-1,day);

        DateTime datetime=new DateTime(cal.getTime());
        //DateTime date = formatter.parseDateTime(dateInString);
        //System.out.println(date);
        //System.out.println(formatter.format(date));
        s.setDateOfBirth(datetime);
        Log.d("Date Format",s.getDateOfBirth()+"");

        s.setEmailId(emailId);



        s.setRollNumber(((EditText) findViewById(R.id.editText7)).getText().toString());

        Log.d("first edit Text", ((EditText) findViewById(R.id.editText)).getText() + "");
        Log.d("second edit Text",((EditText)findViewById(R.id.editText2)).getText()+"");
        Log.d("third edit Text",((EditText)findViewById(R.id.editText3)).getText()+"");
        Log.d("fourth edit Text",((EditText)findViewById(R.id.editText4)).getText()+"");
        Log.d("fifth edit Text",((EditText)findViewById(R.id.editText5)).getText()+"");
        Log.d("sixth edit Text",((EditText)findViewById(R.id.editText6)).getText()+"");
        Log.d("seventh edit Text",((EditText)findViewById(R.id.editText7)).getText()+"");

        new EndpointStudent(this).execute(s);

        Intent intent=new Intent(this,StudentHomeActivity.class);
        intent.putExtra("emailId",emailId);
        Log.d("emailId in Register",emailId);
        startActivity(intent);




    }

}
