package com.aditya.hopon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

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

public class communityActivity extends AppCompatActivity implements Custom_Community_Adapter.OnPatternClickListener {
    View v = null;
    private int opened = 0, noofpatternscreated, noofcommunitypatterns;
    private SharedPreferences sharedPreferences;
    RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private DBManager dbManager;
    private DatabaseReference databaseReference;
    private Custom_Community_Adapter adapter;
    private FirebaseAuth firebaseAuth;
    private List<Patterns> patternslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Community");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        relativeLayout = findViewById(R.id.relativelistlayout);
        recyclerView = findViewById(R.id.communityrecyclerview);
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
                    noofcommunitypatterns = (int) snapshot.getChildrenCount();
                    Patterns patterns = patternDatasnap.getValue(Patterns.class);
                    patternslist.add(patterns);
                }
                adapter = new Custom_Community_Adapter(communityActivity.this, patternslist, communityActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(communityActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
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
                List<Patterns> filteredpatternsList = new ArrayList<>();
                for (Patterns item : patternslist) {
                    if (item.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredpatternsList.add(item);
                    }
                }
                adapter.filterlist(filteredpatternsList);
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

    @Override
    public void OnPatternClick(int position, View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Patterns pattern = patternslist.get(position);
        ConstraintLayout hiddendownloadlayout = view.findViewById(R.id.hiddendownloadlayout);
        CardView patterncardview = view.findViewById(R.id.patterncardview);
        Button downloadbtn = view.findViewById(R.id.patterndownloadbutton);
        if (pattern.getUid().equals(firebaseUser.getUid())) {
            downloadbtn.setText(R.string.delete);
            downloadbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_delete_24, 0, 0, 0);
        } else {
            downloadbtn.setText(R.string.download);
            downloadbtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_download_24, 0, 0, 0);
        }
        //none of the patterns are opened
        if (opened == 0 && hiddendownloadlayout.getVisibility() == View.GONE) {
            v = view;
            TransitionManager.beginDelayedTransition(patterncardview, new AutoTransition());
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
            TransitionManager.beginDelayedTransition(patterncardview, new AutoTransition());
            hiddendownloadlayout.setVisibility(View.VISIBLE);
            v = view;
        }
        downloadbtn.setOnClickListener(view1 -> {
            if (pattern.getUid().equals(firebaseUser.getUid())) {
                downloadbtn.setText(R.string.delete);
                DatabaseReference dbdelete = databaseReference.child(pattern.getPid());
                Task<Void> mTask = dbdelete.removeValue();
                mTask.addOnSuccessListener(aVoid -> Toast.makeText(communityActivity.this, "Deleted From The Community!", Toast.LENGTH_SHORT).show());
                mTask.addOnFailureListener(e -> Toast.makeText(communityActivity.this, "Unknown Error,Couldn't Delete!", Toast.LENGTH_SHORT).show());
            } else {
                dbManager.insert(pattern.getName(), pattern.getSequence(), pattern.getMode());
                noofpatternscreated++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noofpatternscreated", noofpatternscreated);
                editor.apply();
                Toast.makeText(communityActivity.this, "Pattern Downloaded Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("noofcommunitypatterns", noofcommunitypatterns);
        editor.apply();
        super.onStop();
    }
}