package com.aditya.hopon;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class Custom_Adapter extends SimpleCursorAdapter {

    private Context appContext;
    private final int layout;
    private final LayoutInflater inflater;

    public Custom_Adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, 0);
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView patternid = view.findViewById(R.id.patternid);
        TextView patternsequence = view.findViewById(R.id.patternsequence);
        TextView patternname = view.findViewById(R.id.patternname);
        TextView patternmode = view.findViewById(R.id.patternmode);
        TextView patternmodetxt = view.findViewById(R.id.patternmodetxt);
        ImageView pattternmodeic = view.findViewById(R.id.patternmodeic);
        if (patternmode.getText().toString().equals("1")) {
            pattternmodeic.setImageResource(R.drawable.ic_baseline_videogame_asset_24);
            patternmodetxt.setText(R.string.normalmode);
        } else {
            pattternmodeic.setImageResource(R.drawable.ic_baseline_timer_24);
            patternmodetxt.setText(R.string.timedmode);
        }
    }

}
