package com.example.hp.beginnerproject2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    private List<ListItemClass> mssgBody = new ArrayList<>();
    RecyclerView recyclerView;
    private MessageUIAdapter messageUIAdapter;
    EditText editTextBox;
    MyDATABase myDATABase;
    String phonenumber;
    Toolbar toolbar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    recyclerView = findViewById(R.id.reyclerview_message_list);
    editTextBox = findViewById(R.id.edittext_chatbox);
    toolbar = findViewById(R.id.my_toolbar2);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(Main2Activity.this,MainActivity.class);
               startActivity(intent);
               onStop();

            }
        });




        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("key");
        toolbar.setTitle(phonenumber);



        myDATABase=new MyDATABase(this);
        mssgBody.addAll(myDATABase.getReceivedMessage(phonenumber));

//        ListItemClass listItemClass = mssgBody.get(pos);
//        String mssbody = listItemClass.getMssg_body();
//       phonenumber = listItemClass.getMssg_from();

       messageUIAdapter=new MessageUIAdapter(this,mssgBody);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageUIAdapter);
        recyclerView.scrollToPosition(mssgBody.size() - 1);








    }


    public void sendMESSAGE(View view) {

    //    String number = editText.getText().toString();
        String sms = editTextBox.getText().toString();
        String currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
                , Locale.ENGLISH).format(Calendar.getInstance().getTime());



        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, sms, null, null);
            Toast.makeText(Main2Activity.this, "Sent!", Toast.LENGTH_LONG).show();
            editTextBox.setText("");



           long id = myDATABase.insertData(phonenumber,sms,currentDateTimeString,1,1);
            if (id < 0) {
                //Toast.makeText(this, "not inserted", Toast.LENGTH_LONG).show();

            } else {
                //Toast.makeText(this, "successfully inserted", Toast.LENGTH_LONG).show();
                prepareData();


            }
        } catch (Exception e) {
            long id = myDATABase.insertData(phonenumber,sms,currentDateTimeString,0,1);
            if (id < 0) {
               // Toast.makeText(this, "not inserted", Toast.LENGTH_LONG).show();
                Log.e("is data inserted", "not inserted" );
            } else {
                //Toast.makeText(this, "successfully inserted", Toast.LENGTH_LONG).show();
                Log.e("is data inserted", "inserted" );
                prepareData();

            }
            Toast.makeText(Main2Activity.this, "Message not send", Toast.LENGTH_LONG).show();
        }

    }
    private void prepareData() {

        mssgBody.clear();


        mssgBody.addAll(myDATABase.getReceivedMessage(phonenumber));

        messageUIAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
