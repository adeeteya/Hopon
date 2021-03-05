package com.aditya.hopon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserGenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText nameinput;
    private int gamemodechoice;
    private DBManager dbManager;
    private String sequence="";
    private Button submit;
    private String name="";
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnA,btnB,btnC;
    private int s1=0,s2=0,s3=0,s4=0,s5=0,s6=0,s7=0,s8=0,s9=0,sA=0,sB=0,sC=0,bypass=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gen);
        Toolbar toolbar = findViewById(R.id.usergentoolbar);
        toolbar.setTitle("Create Pattern");
        TextView toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameinput=findViewById(R.id.nameinputtxt);
        submit=findViewById(R.id.submitpatternbtn);
        Spinner spinner=findViewById(R.id.gamemodespinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.gamemodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        dbManager = new DBManager(this);
        dbManager.open();
        nameinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name=nameinput.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn1=findViewById(R.id.usergenbtn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s1==0){
                    sequence+="11";
                    btn1.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s1++;
                }
                else if(s1==1){
                    sequence=sequence.replaceFirst("11","21");
                    btn1.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s1++;
                }
                else{
                    sequence=sequence.replaceFirst("21","");
                    btn1.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s1=0;
                }
            }
        });
        btn2=findViewById(R.id.usergenbtn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s2==0){
                    sequence+="12";
                    btn2.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s2++;
                }
                else if(s2==1){
                    sequence=sequence.replaceFirst("12","22");
                    btn2.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s2++;
                }
                else{
                    sequence=sequence.replaceFirst("22","");
                    btn2.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s2=0;
                }
            }
        });
        btn3=findViewById(R.id.usergenbtn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s3==0){
                    sequence+="13";
                    btn3.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s3++;
                }
                else if(s3==1){
                    sequence=sequence.replaceFirst("13","23");
                    btn3.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s3++;
                }
                else{
                    sequence=sequence.replaceFirst("23","");
                    btn3.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s3=0;
                }
            }
        });
        btn4=findViewById(R.id.usergenbtn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s4==0){
                    sequence+="14";
                    btn4.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s4++;
                }
                else if(s4==1){
                    sequence=sequence.replaceFirst("14","24");
                    btn4.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s4++;
                }
                else{
                    sequence=sequence.replaceFirst("24","");
                    btn4.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s4=0;
                }
            }
        });
        btn5=findViewById(R.id.usergenbtn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s5==0){
                    sequence+="15";
                    btn5.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s5++;
                }
                else if(s5==1){
                    sequence=sequence.replaceFirst("15","25");
                    btn5.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s5++;
                }
                else{
                    sequence=sequence.replaceFirst("25","");
                    btn5.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s5=0;
                }
            }
        });
        btn6=findViewById(R.id.usergenbtn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s6==0){
                    sequence+="16";
                    btn6.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s6++;
                }
                else if(s6==1){
                    sequence=sequence.replaceFirst("16","26");
                    btn6.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s6++;
                }
                else{
                    sequence=sequence.replaceFirst("26","");
                    btn6.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s6=0;
                }
            }
        });
        btn7=findViewById(R.id.usergenbtn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s7==0){
                    sequence+="17";
                    btn7.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s7++;
                }
                else if(s7==1){
                    sequence=sequence.replaceFirst("17","27");
                    btn7.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s7++;
                }
                else{
                    sequence=sequence.replaceFirst("27","");
                    btn7.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s7=0;
                }
            }
        });
        btn8=findViewById(R.id.usergenbtn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s8==0){
                    sequence+="18";
                    btn8.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s8++;
                }
                else if(s8==1){
                    sequence=sequence.replaceFirst("18","28");
                    btn8.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s8++;
                }
                else{
                    sequence=sequence.replaceFirst("28","");
                    btn8.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s8=0;
                }
            }
        });
        btn9=findViewById(R.id.usergenbtn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s9==0){
                    sequence+="19";
                    btn9.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    s9++;
                }
                else if(s9==1){
                    sequence=sequence.replaceFirst("19","29");
                    btn9.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    s9++;
                }
                else{
                    sequence=sequence.replaceFirst("29","");
                    btn9.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    s9=0;
                }
            }
        });
        btnA=findViewById(R.id.usergenbtnA);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sA==0){
                    sequence+="1A";
                    btnA.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    sA++;
                }
                else if(sA==1){
                    sequence=sequence.replaceFirst("1A","2A");
                    btnA.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    sA++;
                }
                else{
                    sequence=sequence.replaceFirst("2A","");
                    btnA.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    sA=0;
                }
            }
        });
        btnB=findViewById(R.id.usergenbtnB);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sB==0){
                    sequence+="1B";
                    btnB.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    sB++;
                }
                else if(sB==1){
                    sequence=sequence.replaceFirst("1B","2B");
                    btnB.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    sB++;
                }
                else{
                    sequence=sequence.replaceFirst("2B","");
                    btnB.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    sB=0;
                }
            }
        });
        btnC=findViewById(R.id.usergenbtnC);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sC==0){
                    sequence+="1C";
                    btnC.setBackgroundTintList(ColorStateList.valueOf(0xFFFF0000));
                    sC++;
                }
                else if(sC==1){
                    sequence=sequence.replaceFirst("1C","2C");
                    btnC.setBackgroundTintList(ColorStateList.valueOf(0xFF0000FF));
                    sC++;
                }
                else{
                    sequence=sequence.replaceFirst("2C","");
                    btnC.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    sC=0;
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.equals("")){
                   Toast.makeText(UserGenActivity.this, "Please Enter the name of the pattern", Toast.LENGTH_SHORT).show();
                }
                else if(sequence.equals("")) {
                    Toast.makeText(UserGenActivity.this, "Please Make The Pattern", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbManager.insert(name, sequence, gamemodechoice);
                    Toast.makeText(UserGenActivity.this, "Custom Pattern Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        /*
        btn2=findViewById(R.id.usergenbtn2);btn2.setOnClickListener(this);btn2
        btn3=findViewById(R.id.usergenbtn3);btn3.setOnClickListener(this);btn3
        btn4=findViewById(R.id.usergenbtn4);btn4.setOnClickListener(this);btn4
        btn5=findViewById(R.id.usergenbtn5);btn5.setOnClickListener(this);btn5
        btn6=findViewById(R.id.usergenbtn6);btn6.setOnClickListener(this);btn6
        btn7=findViewById(R.id.usergenbtn7);btn7.setOnClickListener(this);btn7
        btn8=findViewById(R.id.usergenbtn8);btn8.setOnClickListener(this);btn8
        btn9=findViewById(R.id.usergenbtn9);btn9.setOnClickListener(this);btn9
        btnA=findViewById(R.id.usergenbtnA);btnA.setOnClickListener(this);btnA
        btnB=findViewById(R.id.usergenbtnB);btnB.setOnClickListener(this);btnB
        btnC=findViewById(R.id.usergenbtnC);btnC.setOnClickListener(this);*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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