package com.example.phornrawin.mobileapp_lab8;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private List<Person> list;
    int swipe = 0;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        Button addHandle = (Button) findViewById(R.id.btn_record);
        final View alertView = getLayoutInflater().inflate(R.layout.alert_layout, null);
        addHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setView(alertView)
                        .setTitle("Insert Your Info")
                        .setCancelable(false)
                        .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText addNickName = (EditText) alertView.findViewById(R.id.addNickName);
                                EditText addFirstName = (EditText) alertView.findViewById(R.id.addFirstName);
                                EditText addLastName = (EditText) alertView.findViewById(R.id.addFirstName);
                                Log.d("onClick submit", addNickName.toString());
                                Log.d("onClick submit", addFirstName.toString());
                                Log.d("onClick submit", addLastName.toString());

                                Log.d("onClick submit", addNickName.getText().toString());
                                Log.d("onClick submit", addFirstName.getText().toString());
                                Log.d("onClick submit", addLastName.getText().toString());
                                list.add(new Person(addNickName.getText().toString(), addFirstName.getText().toString() , addLastName.getText().toString()));
                            }
                        })
                        .create()
                        .show();
            }
        });

        class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                Toast.makeText(getApplicationContext(), "You double tapped", Toast.LENGTH_SHORT).show();

                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {

                if (e1.getX() - e2.getX() > 50
                        && Math.abs(velocityX) > 50) {
                    swipe = -1;

                }

                else if (e2.getX() - e1.getX() > 50
                        && Math.abs(velocityX) > 50) {
                    swipe = 1;
                }

                return true;
            }
        }

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new PersonAdapter(this,0,list);
        listView.setAdapter( adapter);
        listView.setOnTouchListener(gestureListener);


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event))
            return true;
        else
            return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(gestureDetector.onTouchEvent(event)) {

            if(swipe == 1) {

                Toast.makeText(this, "left-to-right", Toast.LENGTH_SHORT).show();


                adapter.remove(((TextView)v).getText().toString());

                adapter.notifyDataSetChanged();


            }
        }

        swipe = 0;
        return false;
    }
}
