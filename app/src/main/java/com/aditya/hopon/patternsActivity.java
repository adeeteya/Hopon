package com.aditya.hopon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class patternsActivity extends AppCompatActivity implements DeleteConfirmationDialog.DeleteConfirmationDialogListener{
    private TextView toolbartitle;
    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private String id;
    private Cursor cursor;
    int opened=0;View v=null;
    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.NAME, DatabaseHelper.SEQUENCE,DatabaseHelper.MODE};
    final int[] to = new int[] { R.id.patternid, R.id.patternname, R.id.patternsequence , R.id.patternmode};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        Toolbar toolbar = findViewById(R.id.pattern_toolbar);
        toolbar.setTitle("Patterns");
        toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        boolean firstStart=sharedPreferences.getBoolean("firstStart",true);
        dbManager = new DBManager(this);
        dbManager.open();
        if(firstStart){
            dbManager.insert("Regular","122426182A2C",1);
            dbManager.insert("Single Jumps","1214181C",2);
            dbManager.insert("Multiplex","212315172A2C",1);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }
        cursor = dbManager.fetch();
        listView = (ListView) findViewById(R.id.patterns_list);
        listView.setEmptyView(findViewById(R.id.emptytextpattern));
        adapter = new SimpleCursorAdapter(this, R.layout.pattern_view_layout, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        //OnCLickListener for List
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.patternid);
                TextView nameTextView = (TextView) view.findViewById(R.id.patternname);
                TextView sequenceTextView = (TextView) view.findViewById(R.id.patternsequence);
                TextView patternmode = (TextView) view.findViewById(R.id.patternmode);
                ImageView patternmodeic=(ImageView)view.findViewById(R.id.patternmodeic);
                id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();
                String sequence = sequenceTextView.getText().toString();

                //pattern display
                ImageView img1=(ImageView)view.findViewById(R.id.pattgrid1);
                ImageView img2=(ImageView)view.findViewById(R.id.pattgrid2);
                ImageView img3=(ImageView)view.findViewById(R.id.pattgrid3);
                ImageView img4=(ImageView)view.findViewById(R.id.pattgrid4);
                ImageView img5=(ImageView)view.findViewById(R.id.pattgrid5);
                ImageView img6=(ImageView)view.findViewById(R.id.pattgrid6);
                ImageView img7=(ImageView)view.findViewById(R.id.pattgrid7);
                ImageView img8=(ImageView)view.findViewById(R.id.pattgrid8);
                ImageView img9=(ImageView)view.findViewById(R.id.pattgrid9);
                ImageView imgA=(ImageView)view.findViewById(R.id.pattgridA);
                ImageView imgB=(ImageView)view.findViewById(R.id.pattgridB);
                ImageView imgC=(ImageView)view.findViewById(R.id.pattgridC);
                for(int i=0;i<sequence.length();i++){
                    if(sequence.charAt(i)=='1'){
                        i++;
                        switch(sequence.charAt(i)){
                            case '1':img1.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '2':img2.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '3':img3.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '4':img4.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '5':img5.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '6':img6.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '7':img7.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '8':img8.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case '9':img9.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case 'A':imgA.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case 'B':imgB.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                            case 'C':imgC.setImageTintList(ColorStateList.valueOf(0xFFFF0000));break;
                        }
                    }
                    else{
                        i++;
                        switch(sequence.charAt(i)){
                            case '1':img1.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '2':img2.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '3':img3.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '4':img4.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '5':img5.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '6':img6.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '7':img7.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '8':img8.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '9':img9.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case 'A':imgA.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case 'B':imgB.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case 'C':imgC.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                        }
                        i+=2;
                        switch(sequence.charAt(i)){
                            case '1':img1.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '2':img2.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '3':img3.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '4':img4.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '5':img5.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '6':img6.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '7':img7.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '8':img8.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case '9':img9.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case 'A':imgA.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case 'B':imgB.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                            case 'C':imgC.setImageTintList(ColorStateList.valueOf(0xFF0000FF));break;
                        }
                    }
                }
                //pattern display ends here
                //button handling starts here
                Button patternplaybutton=(Button) view.findViewById(R.id.patternplaybutton);
                Button patterndeletebutton=(Button)view.findViewById(R.id.patterndeletebutton);
                Button patternmodifybutton=(Button)view.findViewById(R.id.patternmodifybutton);
                if(id.equals("1")){ patterndeletebutton.setEnabled(false);patternmodifybutton.setEnabled(false);}
                patterndeletebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteConfirmationDialog dialog=new DeleteConfirmationDialog();
                        dialog.show(getSupportFragmentManager(),"delete");
                    }
                });
                //button handling ends here
                //hidden layout handling
                LinearLayout hiddenpatternlayout=(LinearLayout)view.findViewById(R.id.hiddenpatternlayout);
                CardView patterncardview=(CardView)view.findViewById(R.id.patterncardview);
                if(opened==0 && hiddenpatternlayout.getVisibility()==View.GONE){
                    v=view;
                    if(patternmode.getText().toString().equals("1"))patternmode.setText("Normal Mode");
                    else if(patternmode.getText().toString().equals("2")){
                        patternmodeic.setImageResource(R.drawable.ic_baseline_timer_24);
                        patternmode.setText("Timed Mode");
                    }
                    patternmode.setVisibility(View.VISIBLE);
                    TransitionManager.beginDelayedTransition(patterncardview, new AutoTransition());
                    hiddenpatternlayout.setVisibility(View.VISIBLE);opened=1;
                }
                else if(opened==1 && hiddenpatternlayout.getVisibility()==View.VISIBLE){
                    if(patternmode.getText().toString().equals("Normal Mode")){patternmode.setText("1");}
                    else{patternmode.setText("2");}
                    patternmode.setVisibility(View.INVISIBLE);
                    TransitionManager.beginDelayedTransition(patterncardview, new AutoTransition());
                    hiddenpatternlayout.setVisibility(View.GONE);opened=0;
                }
                else if(opened==1 && hiddenpatternlayout.getVisibility()==View.GONE){
                    TextView temp=(TextView)v.findViewById(R.id.patternmode);
                    if(temp.getText().toString().equals("Normal Mode")){temp.setText("1");}
                    else{temp.setText("2");}
                    temp.setVisibility(View.INVISIBLE);
                    v.findViewById(R.id.hiddenpatternlayout).setVisibility(View.GONE);
                    if(patternmode.getText().toString().equals("1"))patternmode.setText("Normal Mode");
                    else if(patternmode.getText().toString().equals("2")){
                        patternmodeic.setImageResource(R.drawable.ic_baseline_timer_24);
                        patternmode.setText("Timed Mode");
                    }
                    patternmode.setVisibility(View.VISIBLE);
                    hiddenpatternlayout.setVisibility(View.VISIBLE);
                    v=view;
                }
                //hidden layout handling ends here
            }
        });
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

    @Override
    public void onYesClicked() {
        dbManager.delete(Integer.parseInt(id));
        Toast.makeText(this, "Pattern Removed Successfully", Toast.LENGTH_SHORT).show();
        cursor = dbManager.fetch();
        adapter = new SimpleCursorAdapter(this, R.layout.pattern_view_layout, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
}