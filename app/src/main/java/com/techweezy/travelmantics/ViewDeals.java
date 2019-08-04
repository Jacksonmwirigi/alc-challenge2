package com.techweezy.travelmantics;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techweezy.travelmantics.adapter.DealsAdapter;
import com.techweezy.travelmantics.model.TravelDeals;
import com.techweezy.travelmantics.utils.FirebaseUtil;

import java.util.ArrayList;

public class ViewDeals extends AppCompatActivity {

    ArrayList<TravelDeals> travelDeals;
    FirebaseDatabase mDatabase;
    DatabaseReference mDbRef;
    ChildEventListener childEventListener;
    private static final int SIGN_IN_REQ = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deals);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_option_menu, menu);
        MenuItem insertM = menu.findItem(R.id.add_deal);
        if(FirebaseUtil.isAdmin){
            insertM.setVisible(true);
        }else{
            insertM.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_deal:
                startActivity(new Intent(getApplicationContext(), AdminPanel.class));
                return true;
            case R.id.logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete( Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Logging Out!", Toast.LENGTH_SHORT).show();
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openReference("traveldeals", this);
        RecyclerView recyclerView = findViewById(R.id.dealsRecyclerView);
        DealsAdapter dealsAdapter  = new DealsAdapter(this);
        recyclerView.setAdapter(dealsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseUtil.attachListener();
    }

    public void showMenu(){
        invalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQ) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showMenu();
                    }
                },5000);
            } else {

            }
        }
    }
}
