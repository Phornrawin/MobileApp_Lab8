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

public class MainActivity extends AppCompatActivity {
    private List<Person> list;
    private ListView listView;
    int swipe = 0;
    private GestureDetector gestureDetector;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        list.add(new Person("Phornrawin", "Chitsoonthorn", "Poy"));
        listView = (ListView) findViewById(R.id.listView);
        adapter = new PersonAdapter(this,0,list);
        listView.setAdapter(adapter);
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });


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


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event))
            return true;
        else
            return false;
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {

            Toast.makeText(getApplicationContext(), "You double tapped", Toast.LENGTH_SHORT).show();

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("My App", "Fling");
            final int y = (int) e1.getY();
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Are you sure to delete?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int index = listView.pointToPosition(0, y);
                            list.remove(index);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
        @Override
        public void onLongPress(MotionEvent motionEvent) {
            Log.d("My App", "Long press");
            final int y = (int) motionEvent.getY();
            final int index = listView.pointToPosition(0, y);
            Person person = list.get(index);

            final View personDialog = getLayoutInflater().inflate(R.layout.alert_layout, null);
            ((TextView) personDialog.findViewById(R.id.addFirstName)).setText(person.getFirstname());
            ((TextView) personDialog.findViewById(R.id.addLastName)).setText(person.getLastname());
            ((TextView) personDialog.findViewById(R.id.addNickName)).setText(person.getNickname());

            new AlertDialog.Builder(MainActivity.this)
                    .setView(personDialog)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String nickname = ((EditText) personDialog.findViewById(R.id.addNickName)).getText().toString();
                            String firstname = ((EditText) personDialog.findViewById(R.id.addFirstName)).getText().toString();
                            String lastname = ((EditText) personDialog.findViewById(R.id.addFirstName)).getText().toString();

                            Person person = new Person(nickname, firstname, lastname);
                            list.set(index, person);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .create()
                    .show();
        }
    }

}
