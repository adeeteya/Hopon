package com.aditya.hopon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class communityActivity extends AppCompatActivity {
    private int opened=0,noofpatternscreated;View v=null;
    private SharedPreferences sharedPreferences;
    private TextView toolbartitle;
    private ListView listView;
    private DBManager dbManager;
    private DatabaseReference databaseReference;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private List<Patterns> patternslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        Toolbar toolbar = findViewById(R.id.pattern_toolbar);
        toolbar.setTitle("Community");
        toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.patterns_list);
        listView.setEmptyView(findViewById(R.id.emptytextpattern));
        dbManager = new DBManager(this);
        dbManager.open();
        patternslist=new ArrayList<>();
        sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        noofpatternscreated=sharedPreferences.getInt("noofpatternscreated",3);
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("233640760574-d18montuoivkeukg6umbfmfsh0h3rmkr.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(communityActivity.this,googleSignInOptions);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            //sign-in intent
            Intent intent=googleSignInClient.getSignInIntent();
            startActivityForResult(intent,100);
        }
        else{
            communitydisplay();
        }
    }
    private void communitydisplay(){
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        Toast.makeText(this,"Welcome "+ firebaseUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        databaseReference= FirebaseDatabase.getInstance().getReference("Patterns");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patternslist.clear();
                for(DataSnapshot patternDatasnap:snapshot.getChildren()){
                    Patterns patterns=patternDatasnap.getValue(Patterns.class);
                    patternslist.add(patterns);
                }
                Custom_Community_Adapter adapter=new Custom_Community_Adapter(communityActivity.this,patternslist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView nameTextView = (TextView) view.findViewById(R.id.patternname);
                TextView sequenceTextView = (TextView) view.findViewById(R.id.patternsequence);
                TextView patternmode = (TextView) view.findViewById(R.id.patternmode);
                Button downloadbtn=(Button)view.findViewById(R.id.patterndownloadbutton);
                LinearLayout hiddendownloadlayout=(LinearLayout)view.findViewById(R.id.hiddendownloadlayout);
                TextView patternuid=(TextView)view.findViewById(R.id.patternuid);
                TextView patternpid=(TextView)view.findViewById(R.id.patternpid);
                String uid=patternuid.getText().toString();
                if(uid.equals(firebaseUser.getUid())) {
                    downloadbtn.setText("Delete");
                    downloadbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_delete_24,0,0,0);
                }
                //none of the patterns are opened
                if(opened==0 && hiddendownloadlayout.getVisibility()==View.GONE){
                    v=view;
                    hiddendownloadlayout.setVisibility(View.VISIBLE);opened=1;
                }
                //current pattern is the only pattern opened
                else if(opened==1 && hiddendownloadlayout.getVisibility()==View.VISIBLE){
                    hiddendownloadlayout.setVisibility(View.GONE);opened=0;
                }
                //some other pattern is already open
                else if(opened==1 && hiddendownloadlayout.getVisibility()==View.GONE){
                    v.findViewById(R.id.hiddendownloadlayout).setVisibility(View.GONE);
                    hiddendownloadlayout.setVisibility(View.VISIBLE);
                    v=view;
                }
                downloadbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(uid.equals(firebaseUser.getUid())){
                            downloadbtn.setText("Delete");
                            DatabaseReference dbdelete=databaseReference.child(patternpid.getText().toString());
                            Task<Void> mTask=dbdelete.removeValue();
                            mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    noofpatternscreated--;
                                    Toast.makeText(communityActivity.this, "Deleted From The Community!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            mTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(communityActivity.this, "Unknown Error,Couldn't Delete!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            int gamemode=1;
                            if(patternmode.getText().toString().equals("Timed mode"))gamemode=2;
                            dbManager.insert(nameTextView.getText().toString(),sequenceTextView.getText().toString(),gamemode);
                            noofpatternscreated++;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("noofpatternscreated", noofpatternscreated);
                            editor.apply();
                            Toast.makeText(communityActivity.this, "Pattern Downloaded Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> signInAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            if(signInAccountTask.isSuccessful()){
                try{
                    GoogleSignInAccount googleSignInAccount=signInAccountTask.getResult(ApiException.class);
                    if(googleSignInAccount!=null){
                        AuthCredential authCredential= GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //write logged in code here
                                    communitydisplay();
                                }
                                else{
                                    Toast.makeText(communityActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                catch(ApiException e){
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}