package com.aditya.hopon;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserGenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText nameinput;
    private int gamemodechoice, noofpatternscreated;
    private DBManager dbManager;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String sequence = "", name = "";
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnA, btnB, btnC;
    private CheckedTextView uploadcommmunitycheck;
    private int s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0, s6 = 0, s7 = 0, s8 = 0, s9 = 0, sA = 0, sB = 0, sC = 0, twotileno = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Pattern");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        nameinput = findViewById(R.id.nameinputtxt);
        Button submit = findViewById(R.id.submitpatternbtn);
        Spinner spinner = findViewById(R.id.gamemodespinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gamemodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        dbManager = new DBManager(this);
        dbManager.open();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Patterns");
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = UserGenActivity.this.getTheme();
        theme.resolveAttribute(R.attr.colorOnSecondary, typedValue, true);
        @ColorInt int color = typedValue.data;
        uploadcommmunitycheck = findViewById(R.id.uploadcommunitycheck);
        uploadcommmunitycheck.setOnClickListener(view -> uploadcommmunitycheck.setChecked(!uploadcommmunitycheck.isChecked()));
        nameinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = nameinput.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        btn1 = findViewById(R.id.usergenbtn1);
        btn1.setOnClickListener(view -> {
            if (s1 == 0) {
                sequence += "11";
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s1++;
            } else if (s1 == 1) {
                sequence = sequence.replaceFirst("11", "21");
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s1++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("21", "");
                btn1.setBackgroundTintList(ColorStateList.valueOf(color));
                s1 = 0;
                twotileno--;
            }
        });
        btn2 = findViewById(R.id.usergenbtn2);
        btn2.setOnClickListener(view -> {
            if (s2 == 0) {
                sequence += "12";
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s2++;
            } else if (s2 == 1) {
                sequence = sequence.replaceFirst("12", "22");
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s2++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("22", "");
                btn2.setBackgroundTintList(ColorStateList.valueOf(color));
                s2 = 0;
                twotileno--;
            }
        });
        btn3 = findViewById(R.id.usergenbtn3);
        btn3.setOnClickListener(view -> {
            if (s3 == 0) {
                sequence += "13";
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s3++;
            } else if (s3 == 1) {
                sequence = sequence.replaceFirst("13", "23");
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s3++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("23", "");
                btn3.setBackgroundTintList(ColorStateList.valueOf(color));
                s3 = 0;
                twotileno--;
            }
        });
        btn4 = findViewById(R.id.usergenbtn4);
        btn4.setOnClickListener(view -> {
            if (s4 == 0) {
                sequence += "14";
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s4++;
            } else if (s4 == 1) {
                sequence = sequence.replaceFirst("14", "24");
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s4++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("24", "");
                btn4.setBackgroundTintList(ColorStateList.valueOf(color));
                s4 = 0;
                twotileno--;
            }
        });
        btn5 = findViewById(R.id.usergenbtn5);
        btn5.setOnClickListener(view -> {
            if (s5 == 0) {
                sequence += "15";
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s5++;
            } else if (s5 == 1) {
                sequence = sequence.replaceFirst("15", "25");
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s5++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("25", "");
                btn5.setBackgroundTintList(ColorStateList.valueOf(color));
                s5 = 0;
                twotileno--;
            }
        });
        btn6 = findViewById(R.id.usergenbtn6);
        btn6.setOnClickListener(view -> {
            if (s6 == 0) {
                sequence += "16";
                btn6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s6++;
            } else if (s6 == 1) {
                sequence = sequence.replaceFirst("16", "26");
                btn6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s6++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("26", "");
                btn6.setBackgroundTintList(ColorStateList.valueOf(color));
                s6 = 0;
                twotileno--;
            }
        });
        btn7 = findViewById(R.id.usergenbtn7);
        btn7.setOnClickListener(view -> {
            if (s7 == 0) {
                sequence += "17";
                btn7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s7++;
            } else if (s7 == 1) {
                sequence = sequence.replaceFirst("17", "27");
                btn7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s7++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("27", "");
                btn7.setBackgroundTintList(ColorStateList.valueOf(color));
                s7 = 0;
                twotileno--;
            }
        });
        btn8 = findViewById(R.id.usergenbtn8);
        btn8.setOnClickListener(view -> {
            if (s8 == 0) {
                sequence += "18";
                btn8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s8++;
            } else if (s8 == 1) {
                sequence = sequence.replaceFirst("18", "28");
                btn8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s8++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("28", "");
                btn8.setBackgroundTintList(ColorStateList.valueOf(color));
                s8 = 0;
                twotileno--;
            }
        });
        btn9 = findViewById(R.id.usergenbtn9);
        btn9.setOnClickListener(view -> {
            if (s9 == 0) {
                sequence += "19";
                btn9.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                s9++;
            } else if (s9 == 1) {
                sequence = sequence.replaceFirst("19", "29");
                btn9.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                s9++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("29", "");
                btn9.setBackgroundTintList(ColorStateList.valueOf(color));
                s9 = 0;
                twotileno--;
            }
        });
        btnA = findViewById(R.id.usergenbtnA);
        btnA.setOnClickListener(view -> {
            if (sA == 0) {
                sequence += "1A";
                btnA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                sA++;
            } else if (sA == 1) {
                sequence = sequence.replaceFirst("1A", "2A");
                btnA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                sA++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2A", "");
                btnA.setBackgroundTintList(ColorStateList.valueOf(color));
                sA = 0;
                twotileno--;
            }
        });
        btnB = findViewById(R.id.usergenbtnB);
        btnB.setOnClickListener(view -> {
            if (sB == 0) {
                sequence += "1B";
                btnB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                sB++;
            } else if (sB == 1) {
                sequence = sequence.replaceFirst("1B", "2B");
                btnB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                sB++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2B", "");
                btnB.setBackgroundTintList(ColorStateList.valueOf(color));
                sB = 0;
                twotileno--;
            }
        });
        btnC = findViewById(R.id.usergenbtnC);
        btnC.setOnClickListener(view -> {
            if (sC == 0) {
                sequence += "1C";
                btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                sC++;
            } else if (sC == 1) {
                sequence = sequence.replaceFirst("1C", "2C");
                btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                sC++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2C", "");
                btnC.setBackgroundTintList(ColorStateList.valueOf(color));
                sC = 0;
                twotileno--;
            }
        });
        submit.setOnClickListener(view -> {
            if (name.equals("")) {
                Toast.makeText(UserGenActivity.this, "Please Enter the name of the pattern", Toast.LENGTH_SHORT).show();
            } else if (sequence.equals("")) {
                Toast.makeText(UserGenActivity.this, "Please Make The Pattern", Toast.LENGTH_SHORT).show();
            } else if (twotileno % 2 == 1) {
                Toast.makeText(UserGenActivity.this, "Invalid Number of Double Tiles", Toast.LENGTH_SHORT).show();
            } else {
                dbManager.insert(name, sequence, gamemodechoice);
                if (uploadcommmunitycheck.isChecked()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    //write to firebase
                    if (firebaseUser != null) {
                        String pid = databaseReference.push().getKey();
                        Patterns pattern = new Patterns(gamemodechoice, name, sequence, firebaseUser.getDisplayName(), firebaseUser.getUid(), pid);
                        if (pid != null) {
                            databaseReference.child(pid).setValue(pattern);
                        }
                        //databaseReference.child(String.valueOf(maxId+1)).setValue(pattern);
                        Toast.makeText(UserGenActivity.this, "Custom Pattern Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserGenActivity.this, "Custom Pattern Added but couldn't upload to Community", Toast.LENGTH_SHORT).show();
                    }
                }
                noofpatternscreated++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noofpatternscreated", noofpatternscreated);
                editor.apply();
                Toast.makeText(UserGenActivity.this, "Custom Pattern Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String Text=adapterView.getItemAtPosition(i).toString();
        if(Text.equals("Normal Mode")){gamemodechoice=1;}
        else if(Text.equals("Timed Mode")){gamemodechoice=2;}
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}