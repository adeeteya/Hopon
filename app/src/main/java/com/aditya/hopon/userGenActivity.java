package com.aditya.hopon;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class userGenActivity extends AppCompatActivity {
    private TextInputLayout nameInput, gameModeInput;
    private Menu userGenMenu;
    private SharedPreferences sharedPreferences;
    private int gameModeChoice = 1, noofpatternscreated, noofcommunitypatterns;
    private DBManager dbManager;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String sequence = "", name = "";
    private Button btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnA, btnB, btnC, btnD, btnE;
    private int s3 = 0, s4 = 0, s5 = 0, s6 = 0, s7 = 0, s8 = 0, s9 = 0, sA = 0, sB = 0, sC = 0, sD = 0, sE = 0, twoTileNumber = 0, pno = 0;
    private Boolean tips, isUploadChecked = false;
    private LottieAnimationView checkBoxAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.create_pattern);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
        noofcommunitypatterns = sharedPreferences.getInt("noofcommunitypatterns", 50);
        tips = sharedPreferences.getBoolean("creatingtips", true);
        if (tips) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            LayoutInflater inflater = userGenActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.pattern_creatingtips, null);
            builder.setView(view).setTitle(R.string.createPatternTipsDialogTitle)
                    .setNegativeButton(R.string.createPatternNegativeBtnTxt, (dialogInterface, i) -> {
                        tips = false;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        userGenMenu.getItem(0).setChecked(false);
                        editor.putBoolean("creatingtips", tips);
                        editor.apply();
                    })
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {

                    }).show();
        }
        nameInput = findViewById(R.id.nameInputTxt);
        Button submit = findViewById(R.id.submitPatternBtn);
        gameModeInput = findViewById(R.id.gameModeInput);
        AutoCompleteTextView gameModeAutoCompleteTextView = findViewById(R.id.gameModeAutoCompleteTextView);
        gameModeAutoCompleteTextView.setText(getString(R.string.normal_mode), false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gameModes, R.layout.dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        gameModeAutoCompleteTextView.setAdapter(adapter);
        gameModeAutoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                gameModeChoice = 1;
                gameModeInput.setStartIconDrawable(R.drawable.ic_baseline_videogame_asset_24);
            } else if (i == 1) {
                gameModeChoice = 2;
                gameModeInput.setStartIconDrawable(R.drawable.ic_baseline_timer_24);
            }
        });
        dbManager = new DBManager(this);
        dbManager.open();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Patterns");
        @ColorInt int color = getColor(R.color.userGenBtnBg);
        checkBoxAnimation = findViewById(R.id.checkBoxAnimation);
        checkBoxAnimation.setPadding(-48, -48, -48, -48);
        Objects.requireNonNull(nameInput.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameInput.setErrorEnabled(charSequence == null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (nameInput.getEditText().length() > 20) {
                    nameInput.setError(getString(R.string.nameInputMaxCharReachedError));
                    nameInput.setErrorEnabled(true);
                } else {
                    nameInput.setError("");
                    nameInput.setErrorEnabled(false);
                }
            }
        });
        checkBoxAnimation.setOnClickListener(view -> {
            if (!isUploadChecked) {
                checkBoxAnimation.setSpeed(1);
                checkBoxAnimation.playAnimation();
                isUploadChecked = true;
            } else {
                checkBoxAnimation.setSpeed(-1);
                checkBoxAnimation.playAnimation();
                isUploadChecked = false;
            }
        });
        //pattern sequence creation starts here
        btn3 = findViewById(R.id.tileBtn3);
        btn3.setOnClickListener(view -> {
            if (s3 == 0) {
                sequence += "13";
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btn3.setText(String.valueOf(++pno));
                s3++;
            } else if (s3 == 1) {
                sequence = sequence.replaceFirst("13", "23");
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                s3++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("23", "");
                btn3.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                s3 = 0;
                twoTileNumber--;
            }
        });
        btn4 = findViewById(R.id.tileBtn4);
        btn4.setOnClickListener(view -> {
            if (s4 == 0) {
                sequence += "14";
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btn4.setText(String.valueOf(++pno));
                s4++;
            } else if (s4 == 1) {
                sequence = sequence.replaceFirst("14", "24");
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                s4++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("24", "");
                btn4.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                s4 = 0;
                twoTileNumber--;
            }
        });
        btn5 = findViewById(R.id.tileBtn5);
        btn5.setOnClickListener(view -> {
            if (s5 == 0) {
                sequence += "15";
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btn5.setText(String.valueOf(++pno));
                s5++;
            } else if (s5 == 1) {
                sequence = sequence.replaceFirst("15", "25");
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                s5++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("25", "");
                btn5.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                s5 = 0;
                twoTileNumber--;
            }
        });
        btn6 = findViewById(R.id.tileBtn6);
        btn6.setOnClickListener(view -> {
            if (s6 == 0) {
                sequence += "16";
                btn6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btn6.setText(String.valueOf(++pno));
                s6++;
            } else if (s6 == 1) {
                sequence = sequence.replaceFirst("16", "26");
                btn6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                s6++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("26", "");
                btn6.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                s6 = 0;
                twoTileNumber--;
            }
        });
        btn7 = findViewById(R.id.tileBtn7);
        btn7.setOnClickListener(view -> {
            if (s7 == 0) {
                sequence += "17";
                btn7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btn7.setText(String.valueOf(++pno));
                s7++;
            } else if (s7 == 1) {
                sequence = sequence.replaceFirst("17", "27");
                btn7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                s7++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("27", "");
                btn7.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                s7 = 0;
                twoTileNumber--;
            }
        });
        btn8 = findViewById(R.id.tileBtn8);
        btn8.setOnClickListener(view -> {
            if (s8 == 0) {
                sequence += "18";
                btn8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btn8.setText(String.valueOf(++pno));
                s8++;
            } else if (s8 == 1) {
                sequence = sequence.replaceFirst("18", "28");
                btn8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                s8++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("28", "");
                btn8.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                s8 = 0;
                twoTileNumber--;
            }
        });
        btn9 = findViewById(R.id.tileBtn9);
        btn9.setOnClickListener(view -> {
            if (s9 == 0) {
                sequence += "19";
                btn9.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btn9.setText(String.valueOf(++pno));
                s9++;
            } else if (s9 == 1) {
                sequence = sequence.replaceFirst("19", "29");
                btn9.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                s9++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("29", "");
                btn9.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                s9 = 0;
                twoTileNumber--;
            }
        });
        btnA = findViewById(R.id.tileBtnA);
        btnA.setOnClickListener(view -> {
            if (sA == 0) {
                sequence += "1A";
                btnA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btnA.setText(String.valueOf(++pno));
                sA++;
            } else if (sA == 1) {
                sequence = sequence.replaceFirst("1A", "2A");
                btnA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                sA++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("2A", "");
                btnA.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                sA = 0;
                twoTileNumber--;
            }
        });
        btnB = findViewById(R.id.tileBtnB);
        btnB.setOnClickListener(view -> {
            if (sB == 0) {
                sequence += "1B";
                btnB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btnB.setText(String.valueOf(++pno));
                sB++;
            } else if (sB == 1) {
                sequence = sequence.replaceFirst("1B", "2B");
                btnB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                sB++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("2B", "");
                btnB.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                sB = 0;
                twoTileNumber--;
            }
        });
        btnC = findViewById(R.id.tileBtnC);
        btnC.setOnClickListener(view -> {
            if (sC == 0) {
                sequence += "1C";
                btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btnC.setText(String.valueOf(++pno));
                sC++;
            } else if (sC == 1) {
                sequence = sequence.replaceFirst("1C", "2C");
                btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                sC++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("2C", "");
                btnC.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                sC = 0;
                twoTileNumber--;
            }
        });
        btnD = findViewById(R.id.tileBtnD);
        btnD.setOnClickListener(view -> {
            if (sD == 0) {
                sequence += "1D";
                btnD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btnD.setText(String.valueOf(++pno));
                sD++;
            } else if (sD == 1) {
                sequence = sequence.replaceFirst("1D", "2D");
                btnD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                sD++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("2D", "");
                btnD.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                sD = 0;
                twoTileNumber--;
            }
        });
        btnE = findViewById(R.id.tileBtnE);
        btnE.setOnClickListener(view -> {
            if (sE == 0) {
                sequence += "1E";
                btnE.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6C4D")));
                btnE.setText(String.valueOf(++pno));
                sE++;
            } else if (sE == 1) {
                sequence = sequence.replaceFirst("1E", "2E");
                btnE.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2274A5")));
                reSequence();
                sE++;
                twoTileNumber++;
            } else {
                sequence = sequence.replaceFirst("2E", "");
                btnE.setBackgroundTintList(ColorStateList.valueOf(color));
                reSequence();
                sE = 0;
                twoTileNumber--;
            }
        });
        //pattern sequence creation ends here
        submit.setOnClickListener(view -> {
            name = nameInput.getEditText().getText().toString().trim();
            if (name.equals("")) {
                nameInput.setError(getString(R.string.nameInputNoNameEnteredError));
                nameInput.setErrorEnabled(true);
            } else if (sequence.equals("")) {
                Toast.makeText(userGenActivity.this, R.string.no_sequence_error, Toast.LENGTH_SHORT).show();
            } else if (twoTileNumber % 2 == 1) {
                Toast.makeText(userGenActivity.this, R.string.invalid_no_of_double_tiles, Toast.LENGTH_SHORT).show();
            } else if (doubleValidity()) {
                Toast.makeText(userGenActivity.this, R.string.invalid_double_tile_sequence, Toast.LENGTH_SHORT).show();
            } else if (!nameInput.isErrorEnabled()) {
                if (isUploadChecked) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String pid = databaseReference.push().getKey();
                    //write to firebase
                    if (firebaseUser != null && pid != null) {
                        Patterns pattern = new Patterns(gameModeChoice, name, sequence, firebaseUser.getDisplayName(), firebaseUser.getUid(), pid);
                        databaseReference.child(pid).setValue(pattern);
                        noofcommunitypatterns++;
                        Toast.makeText(userGenActivity.this, R.string.submitBtnCommunitySuccess, Toast.LENGTH_SHORT).show();
                        dbManager.insert(name, sequence, gameModeChoice, pid);
                    } else {
                        Toast.makeText(userGenActivity.this, R.string.submitBtnFailure, Toast.LENGTH_SHORT).show();
                        dbManager.insert(name, sequence, gameModeChoice, null);
                    }
                } else {
                    dbManager.insert(name, sequence, gameModeChoice, null);
                    Toast.makeText(userGenActivity.this, R.string.submitBtnSuccess, Toast.LENGTH_SHORT).show();
                }
                noofpatternscreated++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noofpatternscreated", noofpatternscreated);
                editor.putInt("noofcommunitypatterns", noofcommunitypatterns);
                editor.apply();
                finish();
            }
        });
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view -> {
            sequence = "";
            twoTileNumber = 0;
            s3 = 0;
            s4 = 0;
            s5 = 0;
            s6 = 0;
            s7 = 0;
            s8 = 0;
            s9 = 0;
            sA = 0;
            sB = 0;
            sC = 0;
            sD = 0;
            sE = 0;
            pno = 0;
            btn3.setText("");
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            btnA.setText("");
            btnB.setText("");
            btnC.setText("");
            btnD.setText("");
            btnE.setText("");
            btn3.setBackgroundTintList(ColorStateList.valueOf(color));
            btn4.setBackgroundTintList(ColorStateList.valueOf(color));
            btn5.setBackgroundTintList(ColorStateList.valueOf(color));
            btn6.setBackgroundTintList(ColorStateList.valueOf(color));
            btn7.setBackgroundTintList(ColorStateList.valueOf(color));
            btn8.setBackgroundTintList(ColorStateList.valueOf(color));
            btn9.setBackgroundTintList(ColorStateList.valueOf(color));
            btnA.setBackgroundTintList(ColorStateList.valueOf(color));
            btnB.setBackgroundTintList(ColorStateList.valueOf(color));
            btnC.setBackgroundTintList(ColorStateList.valueOf(color));
            btnD.setBackgroundTintList(ColorStateList.valueOf(color));
            btnE.setBackgroundTintList(ColorStateList.valueOf(color));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usergen_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.show_tips_button);
        menuItem.setChecked(tips);
        userGenMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.show_tips_button) {
            if (item.isChecked()) {
                item.setChecked(false);
                tips = false;
            } else {
                item.setChecked(true);
                tips = true;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("creatingtips", tips);
            editor.apply();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void reSequence() {
        //clear texts
        pno = 0;
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
        btnA.setText("");
        btnB.setText("");
        btnC.setText("");
        btnD.setText("");
        btnE.setText("");
        for (int i = 1; i < sequence.length(); i += 2) {
            switch (sequence.charAt(i)) {
                case '3':
                    btn3.setText(String.valueOf(++pno));
                    break;
                case '4':
                    btn4.setText(String.valueOf(++pno));
                    break;
                case '5':
                    btn5.setText(String.valueOf(++pno));
                    break;
                case '6':
                    btn6.setText(String.valueOf(++pno));
                    break;
                case '7':
                    btn7.setText(String.valueOf(++pno));
                    break;
                case '8':
                    btn8.setText(String.valueOf(++pno));
                    break;
                case '9':
                    btn9.setText(String.valueOf(++pno));
                    break;
                case 'A':
                    btnA.setText(String.valueOf(++pno));
                    break;
                case 'B':
                    btnB.setText(String.valueOf(++pno));
                    break;
                case 'C':
                    btnC.setText(String.valueOf(++pno));
                    break;
                case 'D':
                    btnD.setText(String.valueOf(++pno));
                    break;
                case 'E':
                    btnE.setText(String.valueOf(++pno));
                    break;
            }
        }
    }

    public boolean doubleValidity() {
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == '1') i++;
            else {
                i += 2;
                if (sequence.charAt(i) != '2') return true;
                else i++;
            }
        }
        return false;
    }
}