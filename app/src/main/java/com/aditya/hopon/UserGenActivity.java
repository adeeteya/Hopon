package com.aditya.hopon;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserGenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText nameinput;
    private Menu usergenmenu;
    private SharedPreferences sharedPreferences;
    private int gamemodechoice, noofpatternscreated, noofcommunitypatterns;
    private DBManager dbManager;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String sequence = "", name = "";
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private Button btnE;
    private CheckedTextView uploadcommunitycheck;
    private int s3 = 0, s4 = 0, s5 = 0, s6 = 0, s7 = 0, s8 = 0, s9 = 0, sA = 0, sB = 0, sC = 0, sD = 0, sE = 0, twotileno = 0, pno = 0;
    private Boolean tips;

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
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        noofpatternscreated = sharedPreferences.getInt("noofpatternscreated", 3);
        noofcommunitypatterns = sharedPreferences.getInt("noofcommunitypatterns", 50);
        tips = sharedPreferences.getBoolean("creatingtips", true);
        if (tips) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = UserGenActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.pattern_creatingtips, null);
            builder.setView(view).setTitle("Instructions to create a Pattern")
                    .setNegativeButton("Don't show this again", (dialogInterface, i) -> {
                        tips = false;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        usergenmenu.getItem(0).setChecked(false);
                        editor.putBoolean("creatingtips", tips);
                        editor.apply();
                    })
                    .setPositiveButton("Ok", (dialogInterface, i) -> {

                    }).show();
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Patterns");
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = UserGenActivity.this.getTheme();
        theme.resolveAttribute(R.attr.colorOnSecondary, typedValue, true);
        @ColorInt int color = typedValue.data;
        uploadcommunitycheck = findViewById(R.id.uploadcommunitycheck);
        uploadcommunitycheck.setOnClickListener(view -> uploadcommunitycheck.setChecked(!uploadcommunitycheck.isChecked()));
        //pattern sequence creation starts here
        btn3 = findViewById(R.id.usergenbtn3);
        btn3.setOnClickListener(view -> {
            if (s3 == 0) {
                sequence += "13";
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btn3.setText(String.valueOf(++pno));
                s3++;
            } else if (s3 == 1) {
                sequence = sequence.replaceFirst("13", "23");
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                s3++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("23", "");
                btn3.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                s3 = 0;
                twotileno--;
            }
        });
        btn4 = findViewById(R.id.usergenbtn4);
        btn4.setOnClickListener(view -> {
            if (s4 == 0) {
                sequence += "14";
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btn4.setText(String.valueOf(++pno));
                s4++;
            } else if (s4 == 1) {
                sequence = sequence.replaceFirst("14", "24");
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                s4++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("24", "");
                btn4.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                s4 = 0;
                twotileno--;
            }
        });
        btn5 = findViewById(R.id.usergenbtn5);
        btn5.setOnClickListener(view -> {
            if (s5 == 0) {
                sequence += "15";
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btn5.setText(String.valueOf(++pno));
                s5++;
            } else if (s5 == 1) {
                sequence = sequence.replaceFirst("15", "25");
                btn5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                s5++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("25", "");
                btn5.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                s5 = 0;
                twotileno--;
            }
        });
        btn6 = findViewById(R.id.usergenbtn6);
        btn6.setOnClickListener(view -> {
            if (s6 == 0) {
                sequence += "16";
                btn6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btn6.setText(String.valueOf(++pno));
                s6++;
            } else if (s6 == 1) {
                sequence = sequence.replaceFirst("16", "26");
                btn6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                s6++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("26", "");
                btn6.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                s6 = 0;
                twotileno--;
            }
        });
        btn7 = findViewById(R.id.usergenbtn7);
        btn7.setOnClickListener(view -> {
            if (s7 == 0) {
                sequence += "17";
                btn7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btn7.setText(String.valueOf(++pno));
                s7++;
            } else if (s7 == 1) {
                sequence = sequence.replaceFirst("17", "27");
                btn7.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                s7++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("27", "");
                btn7.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                s7 = 0;
                twotileno--;
            }
        });
        btn8 = findViewById(R.id.usergenbtn8);
        btn8.setOnClickListener(view -> {
            if (s8 == 0) {
                sequence += "18";
                btn8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btn8.setText(String.valueOf(++pno));
                s8++;
            } else if (s8 == 1) {
                sequence = sequence.replaceFirst("18", "28");
                btn8.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                s8++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("28", "");
                btn8.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                s8 = 0;
                twotileno--;
            }
        });
        btn9 = findViewById(R.id.usergenbtn9);
        btn9.setOnClickListener(view -> {
            if (s9 == 0) {
                sequence += "19";
                btn9.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btn9.setText(String.valueOf(++pno));
                s9++;
            } else if (s9 == 1) {
                sequence = sequence.replaceFirst("19", "29");
                btn9.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                s9++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("29", "");
                btn9.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                s9 = 0;
                twotileno--;
            }
        });
        btnA = findViewById(R.id.usergenbtnA);
        btnA.setOnClickListener(view -> {
            if (sA == 0) {
                sequence += "1A";
                btnA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btnA.setText(String.valueOf(++pno));
                sA++;
            } else if (sA == 1) {
                sequence = sequence.replaceFirst("1A", "2A");
                btnA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                sA++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2A", "");
                btnA.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                sA = 0;
                twotileno--;
            }
        });
        btnB = findViewById(R.id.usergenbtnB);
        btnB.setOnClickListener(view -> {
            if (sB == 0) {
                sequence += "1B";
                btnB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btnB.setText(String.valueOf(++pno));
                sB++;
            } else if (sB == 1) {
                sequence = sequence.replaceFirst("1B", "2B");
                btnB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                sB++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2B", "");
                btnB.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                sB = 0;
                twotileno--;
            }
        });
        btnC = findViewById(R.id.usergenbtnC);
        btnC.setOnClickListener(view -> {
            if (sC == 0) {
                sequence += "1C";
                btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btnC.setText(String.valueOf(++pno));
                sC++;
            } else if (sC == 1) {
                sequence = sequence.replaceFirst("1C", "2C");
                btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                sC++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2C", "");
                btnC.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                sC = 0;
                twotileno--;
            }
        });
        btnD = findViewById(R.id.usergenbtnD);
        btnD.setOnClickListener(view -> {
            if (sD == 0) {
                sequence += "1D";
                btnD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btnD.setText(String.valueOf(++pno));
                sD++;
            } else if (sD == 1) {
                sequence = sequence.replaceFirst("1D", "2D");
                btnD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                sD++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2D", "");
                btnD.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                sD = 0;
                twotileno--;
            }
        });
        btnE = findViewById(R.id.usergenbtnE);
        btnE.setOnClickListener(view -> {
            if (sE == 0) {
                sequence += "1E";
                btnE.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                btnE.setText(String.valueOf(++pno));
                sE++;
            } else if (sE == 1) {
                sequence = sequence.replaceFirst("1E", "2E");
                btnE.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                resequence();
                sE++;
                twotileno++;
            } else {
                sequence = sequence.replaceFirst("2E", "");
                btnE.setBackgroundTintList(ColorStateList.valueOf(color));
                resequence();
                sE = 0;
                twotileno--;
            }
        });
        //pattern sequence creation ends here
        submit.setOnClickListener(view -> {
            name = nameinput.getText().toString().trim();
            if (name.equals("")) {
                Toast.makeText(UserGenActivity.this, "Please Enter the name of the pattern", Toast.LENGTH_SHORT).show();
            } else if (sequence.equals("")) {
                Toast.makeText(UserGenActivity.this, "Please Make The Pattern", Toast.LENGTH_SHORT).show();
            } else if (twotileno % 2 == 1) {
                Toast.makeText(UserGenActivity.this, "Invalid Number of Double Tiles", Toast.LENGTH_SHORT).show();
            } else if (doublevalidity()) {
                Toast.makeText(UserGenActivity.this, "Invalid Double Tile Sequence", Toast.LENGTH_SHORT).show();
            } else {
                dbManager.insert(name, sequence, gamemodechoice);
                if (uploadcommunitycheck.isChecked()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    //write to firebase
                    if (firebaseUser != null) {
                        String pid = databaseReference.push().getKey();
                        Patterns pattern = new Patterns(gamemodechoice, name, sequence, firebaseUser.getDisplayName(), firebaseUser.getUid(), pid);
                        if (pid != null) {
                            databaseReference.child(pid).setValue(pattern);
                        }
                        noofcommunitypatterns++;
                        Toast.makeText(UserGenActivity.this, "Custom Pattern Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserGenActivity.this, "Custom Pattern Added but couldn't upload to Community", Toast.LENGTH_SHORT).show();
                    }
                }
                noofpatternscreated++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noofpatternscreated", noofpatternscreated);
                editor.putInt("noofcommunitypatterns", noofcommunitypatterns);
                editor.apply();
                if (!uploadcommunitycheck.isChecked())
                    Toast.makeText(UserGenActivity.this, "Custom Pattern Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view -> {
            sequence = "";
            twotileno = 0;
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
        usergenmenu = menu;
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String Text = adapterView.getItemAtPosition(i).toString();
        if (Text.equals("Normal Mode")) {
            gamemodechoice = 1;
        } else if (Text.equals("Timed Mode")) {
            gamemodechoice = 2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void resequence() {
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

    public boolean doublevalidity() {
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