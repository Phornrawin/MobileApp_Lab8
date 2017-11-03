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
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private List<Person> list;
    private Button addHandle;
    private ListView listView;
    private GestureDetector gestureDetector;
    private PersonAdapter adapter;

    private void editPerson(int index, Person person){
        list.set(index, person);
        adapter.notifyDataSetChanged();
    }

    private void refresh(){
        Log.d("poy", "refresh");
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = new ArrayList<>();
        list.add(new Person("Phornrawin", "Chitsoonthorn", "Poy"));
        listView = (ListView) findViewById(R.id.listView);
        adapter = new PersonAdapter(this, R.layout.item_row, list);
        listView.setAdapter(adapter);

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });


        addHandle = (Button) findViewById(R.id.btn_record);
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
                                EditText addLastName = (EditText) alertView.findViewById(R.id.addLastName);
                                list.add(new Person(addFirstName.getText().toString(), addLastName.getText().toString(), addNickName.getText().toString()));
                                adapter.notifyDataSetChanged();
                                refresh();
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
            int y = (int) motionEvent.getY();
            final int index = listView.pointToPosition(0, y);
            Person person = list.get(index);
            final View dialogView = getLayoutInflater().inflate(R.layout.alert_layout, null);
            ((EditText) dialogView.findViewById(R.id.addNickName)).setText(person.getNickname());
            ((EditText) dialogView.findViewById(R.id.addFirstName)).setText(person.getFirstname());
            ((EditText) dialogView.findViewById(R.id.addLastName)).setText(person.getLastname());

            new AlertDialog.Builder(MainActivity.this)
                    .setView(dialogView)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String nickname = ((EditText) dialogView.findViewById(R.id.addNickName)).getText().toString();
                            String firstname = ((EditText) dialogView.findViewById(R.id.addFirstName)).getText().toString();
                            String lastname = ((EditText) dialogView.findViewById(R.id.addLastName)).getText().toString();

                            Person person = new Person(firstname, lastname, nickname);
                            list.remove(index);
                            list.add(index, person);
//                            list.get(index).setFirstname(firstname);
                            adapter.notifyDataSetChanged();
                            refresh();
                            Log.d("poy", "item in adapter " + adapter.getItem(index));
                        }
                    })
                    .create()
                    .show();
        }
    }

}
