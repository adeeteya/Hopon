package com.aditya.hopon;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class patternsActivity extends AppCompatActivity {
    private DBManager dbManager;
    private ListView listView;
    private Custom_Adapter adapter;
    private final Handler handler = new Handler();
    private String id, name, modeTxt, sequence;
    private int pno;
    private Character tileChar;
    private Cursor cursor;
    private Boolean isAnimation = false;
    Runnable finishAnimationRunnable = () -> isAnimation = false;
    private ImageView img3, img4, img5, img6, img7, img8, img9, imgA, imgB, imgC, imgD, imgE;
    private TextView txt3, txt4, txt5, txt6, txt7, txt8, txt9, txtA, txtB, txtC, txtD, txtE;
    private TextView patternNameTxt, patternModeTxt;
    private Button patternPlayButton, patternDeleteButton;
    private ImageView patternModeIcon;
    final String[] from = new String[]{DatabaseHelper._ID,
            DatabaseHelper.NAME, DatabaseHelper.SEQUENCE, DatabaseHelper.MODE};
    final int[] to = new int[]{R.id.patternid, R.id.patternname, R.id.patternsequence, R.id.patternmode};
    private Animation patternAnimation;
    Runnable img3Runnable = new Runnable() {
        @Override
        public void run() {
            img3.startAnimation(patternAnimation);
            txt3.setText(String.valueOf(++pno));
            img3.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable img4Runnable = new Runnable() {
        @Override
        public void run() {
            img4.startAnimation(patternAnimation);
            txt4.setText(String.valueOf(++pno));
            img4.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable img5Runnable = new Runnable() {
        @Override
        public void run() {
            img5.startAnimation(patternAnimation);
            txt5.setText(String.valueOf(++pno));
            img5.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable img6Runnable = new Runnable() {
        @Override
        public void run() {
            img6.startAnimation(patternAnimation);
            txt6.setText(String.valueOf(++pno));
            img6.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable img7Runnable = new Runnable() {
        @Override
        public void run() {
            img7.startAnimation(patternAnimation);
            txt7.setText(String.valueOf(++pno));
            img7.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable img8Runnable = new Runnable() {
        @Override
        public void run() {
            img8.startAnimation(patternAnimation);
            txt8.setText(String.valueOf(++pno));
            img8.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable img9Runnable = new Runnable() {
        @Override
        public void run() {
            img9.startAnimation(patternAnimation);
            txt9.setText(String.valueOf(++pno));
            img9.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable imgARunnable = new Runnable() {
        @Override
        public void run() {
            imgA.startAnimation(patternAnimation);
            txtA.setText(String.valueOf(++pno));
            imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable imgBRunnable = new Runnable() {
        @Override
        public void run() {
            imgB.startAnimation(patternAnimation);
            txtB.setText(String.valueOf(++pno));
            imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable imgCRunnable = new Runnable() {
        @Override
        public void run() {
            imgC.startAnimation(patternAnimation);
            txtC.setText(String.valueOf(++pno));
            imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable imgDRunnable = new Runnable() {
        @Override
        public void run() {
            imgD.startAnimation(patternAnimation);
            txtD.setText(String.valueOf(++pno));
            imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };
    Runnable imgERunnable = new Runnable() {
        @Override
        public void run() {
            imgE.startAnimation(patternAnimation);
            txtE.setText(String.valueOf(++pno));
            imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor((sequence.charAt(pno * 2 - 2) == '2') ? "#2274A5" : "#EE6C4D")));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Patterns");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dbManager = new DBManager(this);
        dbManager.open();
        cursor = dbManager.fetch();
        listView = findViewById(R.id.patterns_list);
        listView.setEmptyView(findViewById(R.id.emptytextpattern));
        adapter = new Custom_Adapter(this, R.layout.pattern_view_layout, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        GridLayout patternGrid = findViewById(R.id.patternGrid);
        img3 = findViewById(R.id.pattgrid3);
        img4 = findViewById(R.id.pattgrid4);
        img5 = findViewById(R.id.pattgrid5);
        img6 = findViewById(R.id.pattgrid6);
        img7 = findViewById(R.id.pattgrid7);
        img8 = findViewById(R.id.pattgrid8);
        img9 = findViewById(R.id.pattgrid9);
        imgA = findViewById(R.id.pattgridA);
        imgB = findViewById(R.id.pattgridB);
        imgC = findViewById(R.id.pattgridC);
        imgD = findViewById(R.id.pattgridD);
        imgE = findViewById(R.id.pattgridE);
        txt3 = findViewById(R.id.patttxt3);
        txt4 = findViewById(R.id.patttxt4);
        txt5 = findViewById(R.id.patttxt5);
        txt6 = findViewById(R.id.patttxt6);
        txt7 = findViewById(R.id.patttxt7);
        txt8 = findViewById(R.id.patttxt8);
        txt9 = findViewById(R.id.patttxt9);
        txtA = findViewById(R.id.patttxtA);
        txtB = findViewById(R.id.patttxtB);
        txtC = findViewById(R.id.patttxtC);
        txtD = findViewById(R.id.patttxtD);
        txtE = findViewById(R.id.patttxtE);
        patternNameTxt = findViewById(R.id.patternNameTxt);
        patternModeTxt = findViewById(R.id.patternModeTxt);
        patternModeIcon = findViewById(R.id.patternModeIcon);
        patternPlayButton = findViewById(R.id.patternPlayButton);
        patternDeleteButton = findViewById(R.id.patternDeleteButton);
        //Button Handling
        patternGrid.setOnClickListener(view -> {
            if (isAnimation) {
                return;
            }
            isAnimation = true;
            pno = 0;
            clearPatternDisplay();
            patternAnimation = AnimationUtils.loadAnimation(patternsActivity.this, R.anim.zoom_out);
            patternAnimation.setDuration(750);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 15);
            patternAnimation.setInterpolator(interpolator);
            int j = 100;//time delay
            for (int i = 0; i < sequence.length(); i++) {
                tileChar = sequence.charAt(i);
                if (tileChar == '1') {
                    i++;
                    tileChar = sequence.charAt(i);
                    switch (tileChar) {
                        case '3':
                            handler.postDelayed(img3Runnable, j);
                            break;
                        case '4':
                            handler.postDelayed(img4Runnable, j);
                            break;
                        case '5':
                            handler.postDelayed(img5Runnable, j);
                            break;
                        case '6':
                            handler.postDelayed(img6Runnable, j);
                            break;
                        case '7':
                            handler.postDelayed(img7Runnable, j);
                            break;
                        case '8':
                            handler.postDelayed(img8Runnable, j);
                            break;
                        case '9':
                            handler.postDelayed(img9Runnable, j);
                            break;
                        case 'A':
                            handler.postDelayed(imgARunnable, j);
                            break;
                        case 'B':
                            handler.postDelayed(imgBRunnable, j);
                            break;
                        case 'C':
                            handler.postDelayed(imgCRunnable, j);
                            break;
                        case 'D':
                            handler.postDelayed(imgDRunnable, j);
                            break;
                        case 'E':
                            handler.postDelayed(imgERunnable, j);
                            break;
                    }
                    j += 1000;
                } else if (tileChar == '2') {
                    i++;
                    tileChar = sequence.charAt(i);
                    switch (tileChar) {
                        case '3':
                            handler.postDelayed(img3Runnable, j);
                            break;
                        case '4':
                            handler.postDelayed(img4Runnable, j);
                            break;
                        case '5':
                            handler.postDelayed(img5Runnable, j);
                            break;
                        case '6':
                            handler.postDelayed(img6Runnable, j);
                            break;
                        case '7':
                            handler.postDelayed(img7Runnable, j);
                            break;
                        case '8':
                            handler.postDelayed(img8Runnable, j);
                            break;
                        case '9':
                            handler.postDelayed(img9Runnable, j);
                            break;
                        case 'A':
                            handler.postDelayed(imgARunnable, j);
                            break;
                        case 'B':
                            handler.postDelayed(imgBRunnable, j);
                            break;
                        case 'C':
                            handler.postDelayed(imgCRunnable, j);
                            break;
                        case 'D':
                            handler.postDelayed(imgDRunnable, j);
                            break;
                        case 'E':
                            handler.postDelayed(imgERunnable, j);
                            break;
                    }
                    i += 2;
                    tileChar = sequence.charAt(i);
                    switch (tileChar) {
                        case '3':
                            handler.postDelayed(img3Runnable, j);
                            break;
                        case '4':
                            handler.postDelayed(img4Runnable, j);
                            break;
                        case '5':
                            handler.postDelayed(img5Runnable, j);
                            break;
                        case '6':
                            handler.postDelayed(img6Runnable, j);
                            break;
                        case '7':
                            handler.postDelayed(img7Runnable, j);
                            break;
                        case '8':
                            handler.postDelayed(img8Runnable, j);
                            break;
                        case '9':
                            handler.postDelayed(img9Runnable, j);
                            break;
                        case 'A':
                            handler.postDelayed(imgARunnable, j);
                            break;
                        case 'B':
                            handler.postDelayed(imgBRunnable, j);
                            break;
                        case 'C':
                            handler.postDelayed(imgCRunnable, j);
                            break;
                        case 'D':
                            handler.postDelayed(imgDRunnable, j);
                            break;
                        case 'E':
                            handler.postDelayed(imgERunnable, j);
                            break;
                    }
                    j += 1000;
                }
            }
            handler.postDelayed(finishAnimationRunnable, j);
        });
        patternDeleteButton.setOnClickListener(view -> {
            if (isAnimation) {
                return;
            }
            MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(patternsActivity.this);
            alert.setTitle("Delete " + name + " ?");
            alert.setMessage("Are you sure you want to Delete this pattern?").setNegativeButton("Cancel", (dialogInterface, i) -> {
            });
            alert.setPositiveButton("Yes", (dialogInterface, i) -> {
                dbManager.delete(Integer.parseInt(id));
                Toast.makeText(patternsActivity.this, "Pattern Removed Successfully", Toast.LENGTH_SHORT).show();
                cursor = dbManager.fetch();
                adapter = new Custom_Adapter(patternsActivity.this, R.layout.pattern_view_layout, cursor, from, to, 0);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                int noofpatternscreated;
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
                noofpatternscreated--;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noofpatternscreated", noofpatternscreated);
                editor.apply();
                id = "1";
                name = "Regular";
                sequence = "1426281A2C2E";
                modeTxt = "Normal Mode";
                patternDisplay();
            }).show();
        });
        //Button Handling Ends here
        //Default Values
        id = "1";
        name = "Regular";
        sequence = "1426281A2C2E";
        modeTxt = "Normal Mode";
        patternDisplay();
        //OnCLickListener for List
        listView.setOnItemClickListener((parent, view, position, viewId) -> {
            if (isAnimation) {
                return;
            }
            TextView idTextView = view.findViewById(R.id.patternid);
            TextView nameTextView = view.findViewById(R.id.patternname);
            TextView sequenceTextView = view.findViewById(R.id.patternsequence);
            TextView patternmodetxt = view.findViewById(R.id.patternmodetxt);
            id = idTextView.getText().toString();
            name = nameTextView.getText().toString();
            modeTxt = patternmodetxt.getText().toString();
            sequence = sequenceTextView.getText().toString();
            //pattern display
            patternDisplay();
            //pattern display ends here
        });
    }

    void patternDisplay() {
        int pno = 0;
        //setting name and mode with mode icon
        patternNameTxt.setText(name);
        patternModeTxt.setText(modeTxt);
        if (modeTxt.equals("Normal Mode")) {
            patternModeIcon.setImageResource(R.drawable.ic_baseline_videogame_asset_24);
        } else {
            patternModeIcon.setImageResource(R.drawable.ic_baseline_timer_24);
        }
        //ending here
        //first pattern cannot be deleted
        if (id.equals("1")) {
            patternDeleteButton.setEnabled(false);
            patternDeleteButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            patternDeleteButton.setAlpha(.5f);
        } else {
            patternDeleteButton.setEnabled(true);
            patternDeleteButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
            patternDeleteButton.setAlpha(1f);
        }
        //ends here
        clearPatternDisplay();
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
                i += 2;
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

    void clearPatternDisplay() {
        //clearing old sequence
        img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        txt3.setText("");
        txt4.setText("");
        txt5.setText("");
        txt6.setText("");
        txt7.setText("");
        txt8.setText("");
        txt9.setText("");
        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
        txtD.setText("");
        txtE.setText("");
        //clearing ends here
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
                cursor = dbManager.search(s);
                adapter = new Custom_Adapter(patternsActivity.this, R.layout.pattern_view_layout, cursor, from, to, 0);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
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
