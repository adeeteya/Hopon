package com.aditya.hopon;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class patternsActivity extends AppCompatActivity implements DeleteConfirmationDialog.DeleteConfirmationDialogListener{
    private DBManager dbManager;
    private ListView listView;
    private Custom_Adapter adapter;
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
        //OnCLickListener for List
        listView.setOnItemClickListener((parent, view, position, viewId) -> {
            TextView idTextView = view.findViewById(R.id.patternid);
            TextView sequenceTextView = view.findViewById(R.id.patternsequence);
            TextView patternmodetxt = view.findViewById(R.id.patternmodetxt);
            id = idTextView.getText().toString();
            String sequence = sequenceTextView.getText().toString();
            //pattern display
            ImageView img3 = view.findViewById(R.id.pattgrid3);
            ImageView img4 = view.findViewById(R.id.pattgrid4);
            ImageView img5 = view.findViewById(R.id.pattgrid5);
            ImageView img6 = view.findViewById(R.id.pattgrid6);
            ImageView img7 = view.findViewById(R.id.pattgrid7);
            ImageView img8 = view.findViewById(R.id.pattgrid8);
            ImageView img9 = view.findViewById(R.id.pattgrid9);
            ImageView imgA = view.findViewById(R.id.pattgridA);
            ImageView imgB = view.findViewById(R.id.pattgridB);
            ImageView imgC = view.findViewById(R.id.pattgridC);
            ImageView imgD = view.findViewById(R.id.pattgridD);
            ImageView imgE = view.findViewById(R.id.pattgridE);
            TextView txt3 = view.findViewById(R.id.patttxt3);
            TextView txt4 = view.findViewById(R.id.patttxt4);
            TextView txt5 = view.findViewById(R.id.patttxt5);
            TextView txt6 = view.findViewById(R.id.patttxt6);
            TextView txt7 = view.findViewById(R.id.patttxt7);
            TextView txt8 = view.findViewById(R.id.patttxt8);
            TextView txt9 = view.findViewById(R.id.patttxt9);
            TextView txtA = view.findViewById(R.id.patttxtA);
            TextView txtB = view.findViewById(R.id.patttxtB);
            TextView txtC = view.findViewById(R.id.patttxtC);
            TextView txtD = view.findViewById(R.id.patttxtD);
            TextView txtE = view.findViewById(R.id.patttxtE);
            int pno = 0;
            for (int i = 0; i < sequence.length(); i++) {
                if (sequence.charAt(i) == '1') {
                    i++;
                    switch (sequence.charAt(i)) {
                        case '3':
                            img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txt3.setText(String.valueOf(++pno));
                            break;
                        case '4':
                            img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txt4.setText(String.valueOf(++pno));
                            break;
                        case '5':
                            img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txt5.setText(String.valueOf(++pno));
                            break;
                        case '6':
                            img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txt6.setText(String.valueOf(++pno));
                            break;
                        case '7':
                            img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txt7.setText(String.valueOf(++pno));
                            break;
                        case '8':
                            img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txt8.setText(String.valueOf(++pno));
                            break;
                        case '9':
                            img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txt9.setText(String.valueOf(++pno));
                            break;
                        case 'A':
                            imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txtA.setText(String.valueOf(++pno));
                            break;
                        case 'B':
                            imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txtB.setText(String.valueOf(++pno));
                            break;
                        case 'C':
                            imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txtC.setText(String.valueOf(++pno));
                            break;
                        case 'D':
                            imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txtD.setText(String.valueOf(++pno));
                            break;
                        case 'E':
                            imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                            txtE.setText(String.valueOf(++pno));
                            break;
                    }
                } else {
                    i++;
                    switch (sequence.charAt(i)) {
                        case '3':
                            img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt3.setText(String.valueOf(++pno));
                            break;
                        case '4':
                            img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt4.setText(String.valueOf(++pno));
                            break;
                        case '5':
                            img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt5.setText(String.valueOf(++pno));
                            break;
                        case '6':
                            img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt6.setText(String.valueOf(++pno));
                            break;
                        case '7':
                            img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt7.setText(String.valueOf(++pno));
                            break;
                        case '8':
                            img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt8.setText(String.valueOf(++pno));
                            break;
                        case '9':
                            img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt9.setText(String.valueOf(++pno));
                            break;
                        case 'A':
                            imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtA.setText(String.valueOf(++pno));
                            break;
                        case 'B':
                            imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtB.setText(String.valueOf(++pno));
                            break;
                        case 'C':
                            imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtC.setText(String.valueOf(++pno));
                            break;
                        case 'D':
                            imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtD.setText(String.valueOf(++pno));
                            break;
                        case 'E':
                            imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtE.setText(String.valueOf(++pno));
                            break;
                    }
                    i += 2;
                    switch (sequence.charAt(i)) {
                        case '3':
                            img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt3.setText(String.valueOf(++pno));
                            break;
                        case '4':
                            img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt4.setText(String.valueOf(++pno));
                            break;
                        case '5':
                            img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt5.setText(String.valueOf(++pno));
                            break;
                        case '6':
                            img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt6.setText(String.valueOf(++pno));
                            break;
                        case '7':
                            img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt7.setText(String.valueOf(++pno));
                            break;
                        case '8':
                            img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt8.setText(String.valueOf(++pno));
                            break;
                        case '9':
                            img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txt9.setText(String.valueOf(++pno));
                            break;
                        case 'A':
                            imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtA.setText(String.valueOf(++pno));
                            break;
                        case 'B':
                            imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtB.setText(String.valueOf(++pno));
                            break;
                        case 'C':
                            imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtC.setText(String.valueOf(++pno));
                            break;
                        case 'D':
                            imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtD.setText(String.valueOf(++pno));
                            break;
                        case 'E':
                            imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                            txtE.setText(String.valueOf(++pno));
                            break;
                    }
                }
            }
            //pattern display ends here
            //button handling starts here
            Button patternplaybutton = view.findViewById(R.id.patternplaybutton);
            Button patterndeletebutton = view.findViewById(R.id.patterndeletebutton);
            if (id.equals("1")) {//first pattern cannot be deleted
                patterndeletebutton.setEnabled(false);
                patterndeletebutton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                patterndeletebutton.setAlpha(.5f);
            }
            patterndeletebutton.setOnClickListener(view1 -> {
                DeleteConfirmationDialog dialog = new DeleteConfirmationDialog();
                dialog.show(getSupportFragmentManager(), "delete");
            });
            //button handling ends here
            //hidden layout handling
            LinearLayout hiddenpatternlayout = view.findViewById(R.id.hiddenpatternlayout);
            CardView patterncardview = view.findViewById(R.id.patterncardview);
            //none of the patterns are opened
            if (opened == 0 && hiddenpatternlayout.getVisibility() == View.GONE) {
                v = view;
                patternmodetxt.setVisibility(View.VISIBLE);
                TransitionManager.beginDelayedTransition(patterncardview, new AutoTransition());
                hiddenpatternlayout.setVisibility(View.VISIBLE);
                opened = 1;
            }
            //this is the only pattern opened
            else if (opened == 1 && hiddenpatternlayout.getVisibility() == View.VISIBLE) {
                patternmodetxt.setVisibility(View.INVISIBLE);
                TransitionManager.beginDelayedTransition(patterncardview, new AutoTransition());
                hiddenpatternlayout.setVisibility(View.GONE);
                opened = 0;
            }
            //some other pattern is already open
            else if (opened == 1 && hiddenpatternlayout.getVisibility() == View.GONE) {
                TextView temp = v.findViewById(R.id.patternmodetxt);
                temp.setVisibility(View.INVISIBLE);
                v.findViewById(R.id.hiddenpatternlayout).setVisibility(View.GONE);
                patternmodetxt.setVisibility(View.VISIBLE);
                hiddenpatternlayout.setVisibility(View.VISIBLE);
                v = view;
            }
            //hidden layout handling ends here
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

    @Override
    public void onYesClicked() {
        dbManager.delete(Integer.parseInt(id));
        Toast.makeText(this, "Pattern Removed Successfully", Toast.LENGTH_SHORT).show();
        cursor = dbManager.fetch();
        adapter=new Custom_Adapter(this, R.layout.pattern_view_layout, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        int noofpatternscreated;
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        noofpatternscreated=sharedPreferences.getInt("noofpatternscreated",3);
        noofpatternscreated--;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("noofpatternscreated",noofpatternscreated);
        editor.apply();
    }
}