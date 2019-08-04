//package com.techweezy.travelmantics;
//
//import android.content.Intent;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//
//public class MainActivity extends AppCompatActivity {
//    int RC_SIGN_IN= 1;
//    FirebaseAuth mAuth;
//    Button login_btn;
//    GoogleSignInClient googleSignInClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        login_btn =(Button)findViewById(R.id.emloginBtn);
//        mAuth= FirebaseAuth.getInstance();
//
//        GoogleSignInOptions gsignInOptions=  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        googleSignInClient= GoogleSignIn.getClient(this, gsignInOptions);
//
//    }
//
//    public void loginOptions(View view) {
//        switch (view.getId()){
//            case R.id.signInButton:
//                Intent googleSignIntent= googleSignInClient.getSignInIntent();
//                startActivityForResult(googleSignIntent,RC_SIGN_IN);
//                break;
//            case R.id.emloginBtn:
//                emailLoginOption();
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==RC_SIGN_IN){
//            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount signedccount =  signInTask.getResult(ApiException.class);
//                firebaseAuthWithGoogle(signedccount);
//            }
//
//            catch (ApiException exception){
//                Toast.makeText(this, "Google SignIn Failed" +exception.getMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }
//    private void firebaseAuthWithGoogle(GoogleSignInAccount signedccount) {
//
//    }
//
//    private void emailLoginOption() {
//    }
//
//    private void googleLoginOption(){
//    }
//}
