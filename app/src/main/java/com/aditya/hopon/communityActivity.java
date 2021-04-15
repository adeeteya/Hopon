package com.aditya.hopon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
    RelativeLayout relativeLayout;
    private ListView listView;
    private DBManager dbManager;
    private DatabaseReference databaseReference;
    private Custom_Community_Adapter adapter;
    private FirebaseAuth firebaseAuth;
    private List<Patterns> patternslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Community");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        relativeLayout = findViewById(R.id.relativelistlayout);
        listView = findViewById(R.id.patterns_list);
        listView.setEmptyView(findViewById(R.id.emptytextpattern));
        dbManager = new DBManager(this);
        dbManager.open();
        patternslist = new ArrayList<>();
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("233640760574-d18montuoivkeukg6umbfmfsh0h3rmkr.apps.googleusercontent.com")
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(communityActivity.this, googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            //sign-in intent
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 100);
        } else {
            communitydisplay();
        }
    }

    private void communitydisplay() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Snackbar snackbar = Snackbar.make(relativeLayout, "Welcome " + firebaseUser.getDisplayName(), BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.show();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Patterns");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patternslist.clear();
                for (DataSnapshot patternDatasnap : snapshot.getChildren()) {
                    Patterns patterns = patternDatasnap.getValue(Patterns.class);
                    patternslist.add(patterns);
                }
                adapter = new Custom_Community_Adapter(communityActivity.this, patternslist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener((parent, view, position, viewId) -> {
            TextView nameTextView = view.findViewById(R.id.patternname);
            TextView sequenceTextView = view.findViewById(R.id.patternsequence);
            TextView patternmode = view.findViewById(R.id.patternmode);
            Button downloadbtn = view.findViewById(R.id.patterndownloadbutton);
            ConstraintLayout hiddendownloadlayout = view.findViewById(R.id.hiddendownloadlayout);
            TextView patternuid = view.findViewById(R.id.patternuid);
            TextView patternpid = view.findViewById(R.id.patternpid);
            String uid = patternuid.getText().toString();
            assert firebaseUser != null;
            if (uid.equals(firebaseUser.getUid())) {
                downloadbtn.setText(R.string.delete);
                downloadbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_delete_24, 0, 0, 0);
            }
            //none of the patterns are opened
            if (opened == 0 && hiddendownloadlayout.getVisibility() == View.GONE) {
                v = view;
                hiddendownloadlayout.setVisibility(View.VISIBLE);
                opened = 1;
            }
            //current pattern is the only pattern opened
            else if (opened == 1 && hiddendownloadlayout.getVisibility() == View.VISIBLE) {
                hiddendownloadlayout.setVisibility(View.GONE);
                opened = 0;
            }
            //some other pattern is already open
            else if (opened == 1 && hiddendownloadlayout.getVisibility() == View.GONE) {
                v.findViewById(R.id.hiddendownloadlayout).setVisibility(View.GONE);
                hiddendownloadlayout.setVisibility(View.VISIBLE);
                v = view;
            }
            downloadbtn.setOnClickListener(view1 -> {
                if (uid.equals(firebaseUser.getUid())) {
                    downloadbtn.setText(R.string.delete);
                    DatabaseReference dbdelete = databaseReference.child(patternpid.getText().toString());
                    Task<Void> mTask = dbdelete.removeValue();
                    mTask.addOnSuccessListener(aVoid -> {
                        noofpatternscreated--;
                        Toast.makeText(communityActivity.this, "Deleted From The Community!", Toast.LENGTH_SHORT).show();
                    });
                    mTask.addOnFailureListener(e -> Toast.makeText(communityActivity.this, "Unknown Error,Couldn't Delete!", Toast.LENGTH_SHORT).show());
                } else {
                    int gamemode = 1;
                    if (patternmode.getText().toString().equals("Timed mode")) gamemode = 2;
                    dbManager.insert(nameTextView.getText().toString(), sequenceTextView.getText().toString(), gamemode);
                    noofpatternscreated++;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("noofpatternscreated", noofpatternscreated);
                    editor.apply();
                    Toast.makeText(communityActivity.this, "Pattern Downloaded Successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> signInAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            if(signInAccountTask.isSuccessful()){
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                //write logged in code here
                                communitydisplay();
                            } else {
                                Toast.makeText(communityActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (ApiException ignored) {
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patterns_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //firebase search here
                DatabaseReference searchref = FirebaseDatabase.getInstance().getReference("Patterns");
                searchref.orderByChild("name").startAt(s).endAt(s + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        patternslist.clear();
                        for (DataSnapshot patternDatasnap : snapshot.getChildren()) {
                            Patterns patterns = patternDatasnap.getValue(Patterns.class);
                            patternslist.add(patterns);
                        }
                        adapter = new Custom_Community_Adapter(communityActivity.this, patternslist);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}