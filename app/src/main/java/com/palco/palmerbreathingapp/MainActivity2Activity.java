package com.palco.palmerbreathingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity2Activity extends ActionBarActivity {

    //string used to store and pass new BPM value
    public String BPMValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hides title bar, increases screen size
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main_activity2);

        //button used to set and pass new BPM value
        final Button button = (Button) findViewById(R.id.setButton);
        // text field for entering new BPM
        final EditText BPMEdit = (EditText) findViewById(R.id.BPMTextt);
        // Displays error for invalid BPM values
        final TextView ErrorText = (TextView) findViewById(R.id.errorText);

        //function called when button is pressed
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //retrieves new BPM value
                   BPMValue = BPMEdit.getText().toString();

                    //checks for invalid BPM values before returning value to main activity
                   if (!BPMValue.equals("0") && !BPMValue.equals("")&& !BPMValue.isEmpty()){

                           changeAvtivity();

                   }
                   else {
                       ErrorText.setText("Error: Value must be greater than 0");

                   }


            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
    //function for returning new BPM value and changing activity
    public void changeAvtivity(){
        Intent returnIntent = new Intent(this, MainActivity.class);
        returnIntent.putExtra("result", BPMValue);

        setResult(RESULT_OK, returnIntent);
        finish();

    }
}
