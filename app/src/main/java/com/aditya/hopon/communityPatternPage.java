package com.aditya.hopon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class communityPatternPage extends AppCompatActivity {
    private DBManager dbManager;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private int noofpatternscreated, mode;
    private String name;
    private String sequence;
    private String pid;
    private Button downloadBtn;
    private ConstraintLayout communityConstraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_pattern_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        sequence = intent.getStringExtra("sequence");
        mode = intent.getIntExtra("mode", 1);
        pid = intent.getStringExtra("pid");
        String author = intent.getStringExtra("author");
        boolean isUser = intent.getBooleanExtra("isUser", false);
        //all values are set
        dbManager = new DBManager(this);
        dbManager.open();
        databaseReference = FirebaseDatabase.getInstance().getReference("Patterns");
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
        //local database initialized
        communityConstraintLayout = findViewById(R.id.communityConstraintLayout);
        downloadBtn = findViewById(R.id.patternDownloadButton);
        Button deleteBtn = findViewById(R.id.patternDeleteButton);
        TextView nameView = findViewById(R.id.nameView);
        TextView authorView = findViewById(R.id.authorNameView);
        ImageView gameModeIcon = findViewById(R.id.gameModeIcon);
        nameView.setText(name);
        authorView.setText(author);
        gameModeIcon.setImageResource((mode == 1) ? R.drawable.ic_baseline_videogame_asset_24 : R.drawable.ic_baseline_timer_24);
        if (isUser) deleteBtn.setVisibility(View.VISIBLE);
        if (dbManager.checkIfPidExists(pid)) {
            downloadBtn.setEnabled(false);
            downloadBtn.setText(R.string.downloaded);
        }
        downloadBtn.setOnClickListener(view -> {
            dbManager.insert(name, sequence, mode, pid);
            noofpatternscreated++;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("noofpatternscreated", noofpatternscreated);
            editor.apply();
            downloadBtn.setEnabled(false);
            downloadBtn.setText(R.string.downloaded);
            //Toast.makeText(communityPatternPage.this, R.string.communityDownloadSuccess, Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(communityConstraintLayout, R.string.communityDownloadSuccess, BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.show();
        });
        deleteBtn.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(communityPatternPage.this);
            alert.setTitle(getString(R.string.delete) + " " + name + " ?");
            alert.setMessage(R.string.deleteAlertDialogBodyTxt).setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            });
            alert.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                DatabaseReference dbDelete = databaseReference.child(pid);
                Task<Void> mTask = dbDelete.removeValue();
                mTask.addOnSuccessListener(unused -> {
                    Snackbar snackbar = Snackbar.make(communityConstraintLayout, R.string.communityDeleteSuccess, BaseTransientBottomBar.LENGTH_SHORT);
                    snackbar.show();
                });
                mTask.addOnFailureListener(e -> {
                    Snackbar snackbar = Snackbar.make(communityConstraintLayout, R.string.communityDeleteFailure, BaseTransientBottomBar.LENGTH_SHORT);
                    snackbar.show();
                });
                finish();
            }).show();
        });
        int pno = 0;
        ImageView img3 = findViewById(R.id.patternImage3);
        ImageView img4 = findViewById(R.id.patternImage4);
        ImageView img5 = findViewById(R.id.patternImage5);
        ImageView img6 = findViewById(R.id.patternImage6);
        ImageView img7 = findViewById(R.id.patternImage7);
        ImageView img8 = findViewById(R.id.patternImage8);
        ImageView img9 = findViewById(R.id.patternImage9);
        ImageView imgA = findViewById(R.id.patternImageA);
        ImageView imgB = findViewById(R.id.patternImageB);
        ImageView imgC = findViewById(R.id.patternImageC);
        ImageView imgD = findViewById(R.id.patternImageD);
        ImageView imgE = findViewById(R.id.patternImageE);
        TextView txt3 = findViewById(R.id.patternTxt3);
        TextView txt4 = findViewById(R.id.patternTxt4);
        TextView txt5 = findViewById(R.id.patternTxt5);
        TextView txt6 = findViewById(R.id.patternTxt6);
        TextView txt7 = findViewById(R.id.patternTxt7);
        TextView txt8 = findViewById(R.id.patternTxt8);
        TextView txt9 = findViewById(R.id.patternTxt9);
        TextView txtA = findViewById(R.id.patternTxtA);
        TextView txtB = findViewById(R.id.patternTxtB);
        TextView txtC = findViewById(R.id.patternTxtC);
        TextView txtD = findViewById(R.id.patternTxtD);
        TextView txtE = findViewById(R.id.patternTxtE);
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == '1') {
                i++;
                switch (sequence.charAt(i)) {
                    case '3':
                        img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txt3.setText(String.valueOf(++pno));
                        break;
                    case '4':
                        img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txt4.setText(String.valueOf(++pno));
                        break;
                    case '5':
                        img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txt5.setText(String.valueOf(++pno));
                        break;
                    case '6':
                        img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txt6.setText(String.valueOf(++pno));
                        break;
                    case '7':
                        img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txt7.setText(String.valueOf(++pno));
                        break;
                    case '8':
                        img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txt8.setText(String.valueOf(++pno));
                        break;
                    case '9':
                        img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txt9.setText(String.valueOf(++pno));
                        break;
                    case 'A':
                        imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txtA.setText(String.valueOf(++pno));
                        break;
                    case 'B':
                        imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txtB.setText(String.valueOf(++pno));
                        break;
                    case 'C':
                        imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txtC.setText(String.valueOf(++pno));
                        break;
                    case 'D':
                        imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txtD.setText(String.valueOf(++pno));
                        break;
                    case 'E':
                        imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                        txtE.setText(String.valueOf(++pno));
                        break;
                }
            } else {
                i++;
                switch (sequence.charAt(i)) {
                    case '3':
                        img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txt3.setText(String.valueOf(++pno));
                        break;
                    case '4':
                        img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txt4.setText(String.valueOf(++pno));
                        break;
                    case '5':
                        img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txt5.setText(String.valueOf(++pno));
                        break;
                    case '6':
                        img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txt6.setText(String.valueOf(++pno));
                        break;
                    case '7':
                        img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txt7.setText(String.valueOf(++pno));
                        break;
                    case '8':
                        img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txt8.setText(String.valueOf(++pno));
                        break;
                    case '9':
                        img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txt9.setText(String.valueOf(++pno));
                        break;
                    case 'A':
                        imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txtA.setText(String.valueOf(++pno));
                        break;
                    case 'B':
                        imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txtB.setText(String.valueOf(++pno));
                        break;
                    case 'C':
                        imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txtC.setText(String.valueOf(++pno));
                        break;
                    case 'D':
                        imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txtD.setText(String.valueOf(++pno));
                        break;
                    case 'E':
                        imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                        txtE.setText(String.valueOf(++pno));
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}