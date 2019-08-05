package com.techweezy.travelmantics.utils;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.techweezy.travelmantics.ViewDeals;
import com.techweezy.travelmantics.model.TravelDeals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    public static ArrayList<TravelDeals> travelDeals;
    public static FirebaseDatabase database;
    public static DatabaseReference mReff;
    public static FirebaseUtil firebaseUtil;
    public static FirebaseAuth mAuth;
    public static FirebaseAuth.AuthStateListener mAuthstatelistener;
    private static final int RC_SIGN_IN = 102;
    public static ViewDeals caller;
    public static boolean isAdmin;
    public static FirebaseStorage mFiretorage;
    public static StorageReference storageReference;
    private FirebaseUtil(){}

    public static   void openReference(String mref, final ViewDeals callerActivity){
        if(firebaseUtil==null){
            firebaseUtil = new FirebaseUtil();
            database = FirebaseDatabase.getInstance();
            travelDeals = new ArrayList<TravelDeals>();
            caller = callerActivity;
            mAuth = FirebaseAuth.getInstance();
            mAuthstatelistener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(mAuth.getCurrentUser() == null){
                        FirebaseUtil.signIn();
                    } else{
                        String userid = mAuth.getUid();
                        checkAdm(userid);
                    }
                    caller.showMenu();
                }
            };
            initiateStorage();
        }
        mReff = database.getReference().child(mref);
    }
    private static void signIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

    }

    private static void checkAdm(String uid){
        DatabaseReference mreff = database.getReference().child("administrators")
                .child(uid);
        FirebaseUtil.isAdmin=false;
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FirebaseUtil.isAdmin=true;
                caller.showMenu();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mreff.addChildEventListener(listener);

    }

    public static void attachListener(){
        mAuth.addAuthStateListener(mAuthstatelistener);
    }

    public static void detachListener(){
        mAuth.removeAuthStateListener(mAuthstatelistener);
    }

    public static void initiateStorage(){
        mFiretorage = FirebaseStorage.getInstance();
        storageReference  = mFiretorage.getReference();
    }

}
