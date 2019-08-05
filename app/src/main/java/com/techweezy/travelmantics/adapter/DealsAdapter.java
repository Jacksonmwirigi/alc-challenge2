package com.techweezy.travelmantics.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.techweezy.travelmantics.R;
import com.techweezy.travelmantics.model.TravelDeals;
import com.techweezy.travelmantics.utils.FirebaseUtil;

import java.util.ArrayList;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.MyViewHolder> {
    ArrayList<TravelDeals> travelDeals;
    FirebaseDatabase fireDb;
    DatabaseReference mRef;
    ChildEventListener childEventListener;
    Context context;
    ImageView imageView;
    ProgressDialog dialog;
    public DealsAdapter(final Context context){

        this.context = context;
        fireDb = FirebaseUtil.database;
        mRef = FirebaseUtil.mReff;
        travelDeals = FirebaseUtil.travelDeals;
        travelDeals.clear();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.show();
        childEventListener = new ChildEventListener(){
            @Override
            public void onChildAdded( DataSnapshot dataSnapshot, String s) {
                TravelDeals traveldeals = dataSnapshot.getValue(TravelDeals.class);
                traveldeals.setId(dataSnapshot.getKey());
                travelDeals.add(traveldeals);
                notifyItemInserted(travelDeals.size()-1);
                dialog.hide();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                travelDeals.clear();
                notifyDataSetChanged();
                dialog.hide();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                travelDeals.clear();
                notifyDataSetChanged();
                dialog.hide();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRef.addChildEventListener(childEventListener);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    dialog.hide();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("No Records.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.hide();
            }
        },3000);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.travels_list, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int position) {
        TravelDeals deals = travelDeals.get(position);
        myViewHolder.place_name.setText(deals.getPlace_name());
        myViewHolder.description.setText(deals.getDescription());
        myViewHolder.price.setText(deals.getPrice());
        displayImage(deals.getImageUrl());

    }

    @Override
    public int getItemCount() {
        return travelDeals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView place_name, description, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            place_name = itemView.findViewById(R.id.placeTV);
            description = itemView.findViewById(R.id.descriptionTV);
            price = itemView.findViewById(R.id.priceTV);
            imageView = itemView.findViewById(R.id.deal_imageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    private void displayImage(String url){
        if (url != null && !url.isEmpty()){
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.beach)
                    .into(imageView);
        }
    }
}
