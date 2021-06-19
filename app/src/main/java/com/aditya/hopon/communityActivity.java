package com.aditya.hopon;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
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
import java.util.Objects;

public class communityActivity extends AppCompatActivity implements Custom_Community_Adapter.OnPatternClickListener {
    private int noofcommunitypatterns;
    RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private Custom_Community_Adapter adapter;
    private FirebaseAuth firebaseAuth;
    private List<Patterns> patternsList;
    private ShimmerFrameLayout shimmerFrameLayout;
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
        relativeLayout = findViewById(R.id.relativeListLayout);
        recyclerView = findViewById(R.id.communityRecyclerView);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        patternsList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("233640760574-d18montuoivkeukg6umbfmfsh0h3rmkr.apps.googleusercontent.com")
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(communityActivity.this, googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                        if (signInAccountTask.isSuccessful()) {
                            try {
                                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                                if (googleSignInAccount != null) {
                                    AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                                    firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            //write logged in code here
                                            communityDisplay();
                                        } else {
                                            Toast.makeText(communityActivity.this, R.string.communityLoginFailure, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }
                            } catch (ApiException ignored) {
                            }
                        }
                    }
                });
        if (firebaseUser == null) {
            Intent intent = googleSignInClient.getSignInIntent();
            someActivityResultLauncher.launch(intent);
        } else {
            communityDisplay();
        }
    }

    private void communityDisplay() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Snackbar snackbar = Snackbar.make(relativeLayout, getString(R.string.welcome) + firebaseUser.getDisplayName(), BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.show();
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Patterns");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                patternsList.clear();
                for (DataSnapshot patternDatasnap : snapshot.getChildren()) {
                    noofcommunitypatterns = (int) snapshot.getChildrenCount();
                    Patterns patterns = patternDatasnap.getValue(Patterns.class);
                    patternsList.add(patterns);
                }
                adapter = new Custom_Community_Adapter(communityActivity.this, patternsList, communityActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(communityActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
                List<Patterns> filteredPatternsList = new ArrayList<>();
                for (Patterns item : patternsList) {
                    if (item.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredPatternsList.add(item);
                    }
                }
                adapter.filterList(filteredPatternsList);
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
        Patterns pattern = patternsList.get(position);
        Boolean currentUser = pattern.getUid().equals(Objects.requireNonNull(firebaseUser).getUid());
        Intent intent = new Intent(communityActivity.this, communityPatternPage.class);
        intent.putExtra("name", pattern.name);
        intent.putExtra("sequence", pattern.sequence);
        intent.putExtra("mode", pattern.mode);
        intent.putExtra("author", pattern.author);
        intent.putExtra("isUser", currentUser);
        intent.putExtra("pid", pattern.getPid());

        //shared element transition
        Pair<View, String> p3 = Pair.create(view.findViewById(R.id.frame_3), "tframe_3");
        Pair<View, String> p4 = Pair.create(view.findViewById(R.id.frame_4), "tframe_4");
        Pair<View, String> p5 = Pair.create(view.findViewById(R.id.frame_5), "tframe_5");
        Pair<View, String> p6 = Pair.create(view.findViewById(R.id.frame_6), "tframe_6");
        Pair<View, String> p7 = Pair.create(view.findViewById(R.id.frame_7), "tframe_7");
        Pair<View, String> p8 = Pair.create(view.findViewById(R.id.frame_8), "tframe_8");
        Pair<View, String> p9 = Pair.create(view.findViewById(R.id.frame_9), "tframe_9");
        Pair<View, String> pA = Pair.create(view.findViewById(R.id.frame_A), "tframe_A");
        Pair<View, String> pB = Pair.create(view.findViewById(R.id.frame_B), "tframe_B");
        Pair<View, String> pC = Pair.create(view.findViewById(R.id.frame_C), "tframe_C");
        Pair<View, String> pD = Pair.create(view.findViewById(R.id.frame_D), "tframe_D");
        Pair<View, String> pE = Pair.create(view.findViewById(R.id.frame_E), "tframe_E");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, p3, p4, p5, p6, p7, p8, p9, pA, pB, pC, pD, pE);
        startActivity(intent, activityOptions.toBundle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("noofcommunitypatterns", noofcommunitypatterns);
        editor.apply();
        super.onStop();
    }
}