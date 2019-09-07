package com.example.hp.beginnerproject2;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private MssgAdapter mAdapter;
    private List<ListItemClass> viewList = new ArrayList<>();
    private Toolbar toolbar;


    MyDATABase myDATABase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            toolbar.setTitle("Messaging");

        myDATABase = new MyDATABase(this);
        mAdapter = new MssgAdapter(viewList, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareData();






        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessageNumber");
            String message = extras.getString("Message");
            String dataNTIME = extras.getString("current_data_time");

            long id = myDATABase.insertData(address, message, dataNTIME, 0,0);
            if (id < 0) {
               // Toast.makeText(this, "not inserted", Toast.LENGTH_LONG).show();
                Log.e("is data inserted", "not inserted" );
            } else {
               // Toast.makeText(this, "successfully inserted", Toast.LENGTH_LONG).show();
                Log.e("is data inserted", " inserted" );
            }


            prepareData();

        }
    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    private void prepareData() {

        viewList.clear();


        viewList.addAll(myDATABase.getReceivedMessageGroupBy());

        mAdapter.notifyDataSetChanged();


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<ListItemClass> newList = new ArrayList<>();
        for(ListItemClass listItemClass : viewList){
            String name = listItemClass.getMssg_from().toLowerCase();
            if(name.contains(newText)){
                newList.add(listItemClass);
                }

        }
        mAdapter.setFilter(newList);
        return true;
    }
}
