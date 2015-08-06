/********************************************************
 * Breathing Assistant Application                      *
 *                                                      *
 * Author:  Daniel, Palmer                              *
 *                                                      *
 * Purpose:  Provides visual indicator to assist in     *
 * timing of breaths for wellness purposes.             *
 *                                                      *
 * Usage: Allows for setting of breaths per minute      *
 * as well as starting and stopping the the breathing   *
 * protocol                                             *
 *                                                      *
 ********************************************************/



package com.palco.palmerbreathingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    //default number of breaths per minute
    int BPMValue = 6;
    //placeholder for breath indicator height
    int i = 200;
    //acts as loop variable in handler
    int hold = 0;
    //number of milliseconds to hold breath
    int pauseLength = 2000;
    //Placeholder used to interrupt handler
    int interrupt = 0;
    //Calculates the length of delay used in handler.postDelayed() command during inhale and exhale
    int delayCalc = (60000 - (pauseLength * BPMValue)) / (80 * BPMValue);
    //Variable used in handler.postDelayed() command, later modified by delayCalc
    int delay;
    //Later used to hold value passed from MainActivity2Activity
    String resultPassed;
    //String used to display current BPM
    String currentBPM = Integer.toString(BPMValue);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hides title bar, increases screen size
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Start button
        final Button button = (Button) findViewById(R.id.button);
        //breath indicator, not usable
        final Button button2 = (Button) findViewById(R.id.button2);
        //Stop button
        final Button button3 = (Button) findViewById(R.id.button3);
        //Changes activity to set BPM
        final Button setButton = (Button) findViewById(R.id.BPMSetButton);
        //TextView used to display Breaths Per Minute
        final TextView CurrentBPM = (TextView) findViewById(R.id.currentBPM);

        //changes TextView to display Breaths Per Minute
        CurrentBPM.setText("Current BPM: "+ currentBPM);

        //Handler used to run breathing protocol
        final Handler handler = new Handler();

        //Stop button, interrupts handler and resets indicator
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interrupt = 1;
                change(200, "paused");
                button.setVisibility(View.VISIBLE);


            }

        });
        //changes activity to set new breaths per minute
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setActivity();
            }

        });

        //Starts breathing indicator
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Removes button during protocol to prevent multiple instances
                view.setVisibility(View.GONE);
                //resets variables used in breathing protocol
                i = 200;
                hold = 0;
                interrupt = 0;







                handler.post(new Runnable() {
                    @Override
                    public void run() {


                        if (interrupt == 0) {

                            //resets delay after holding breath
                            delay = delayCalc;

                            //Variable used to change indicator text
                            String text;
                            //Selects instruction based on place in protocol
                            if (hold >= 0 && hold <= 10) {
                                text = "inhale 4";
                            } else if (hold >= 10 && hold <= 20) {
                                text = "inhale 3";
                            } else if (hold >= 20 && hold <= 30) {
                                text = "inhale 2";
                            } else if (hold >= 30 && hold <= 40) {
                                text = "inhale 1";
                            } else if (hold >= -40 && hold <= -30) {
                                text = "exhale 4";
                            } else if (hold >= -30 && hold <= -20) {
                                text = "exhale 3";
                            } else if (hold >= -20 && hold <= -10) {
                                text = "exhale 2";
                            } else {
                                text = "exhale 1";
                            }

                            //calls function to change indicator
                            change(i, text);

                            //changes variable used to store height
                            i = i + 20;

                            //increases loop variable
                            hold++;
                            if (hold == 40) {
                                hold = -40;

                            }

                            //changes variable used to store height
                            if (hold <= 0) {
                                i = i - 40;
                            }

                            //initiates pause after exhale
                            if (hold == 0) {
                                delay = pauseLength;
                                button2.setText("hold");


                            }

                            //stops protocol if interrupt is called
                        } else if (interrupt == 1) {
                            return;
                        }

                        //delays handler
                        handler.postDelayed(this, delay);


                    }


                });





                /*for (int i = 200; i <= 700; i++){
                    change(i, text);
                }*/


            }

        });


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

    //function used to change breath indicator
    public void change(int passed, String text) {

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setHeight(passed);
        button2.setText(text);

    }

    //function used to call activity used to set BPM
    public void setActivity() {
        Intent intent = new Intent(this, MainActivity2Activity.class);
        startActivityForResult(intent, 1);


        }

    //returns result from other activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                resultPassed = data.getStringExtra("result");
                BPMValue = Integer.parseInt(resultPassed);
                currentBPM = resultPassed;
                final TextView CurrentBPM = (TextView) findViewById(R.id.currentBPM);
                CurrentBPM.setText("Current BPM: "+ currentBPM);
                delayCalc = (60000 - (pauseLength * BPMValue)) / (80 * BPMValue);




            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }



    }
}