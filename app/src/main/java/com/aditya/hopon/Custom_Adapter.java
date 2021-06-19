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
        TextView patternId = view.findViewById(R.id.patternId);
        TextView patternSequence = view.findViewById(R.id.patternSequence);
        TextView patternName = view.findViewById(R.id.patternName);
        TextView patternMode = view.findViewById(R.id.patternMode);
        TextView patternModeTxt = view.findViewById(R.id.patternModeTxt);
        ImageView patternModeIcon = view.findViewById(R.id.patternModeIcon);
        if (patternMode.getText().toString().equals("1")) {
            patternModeIcon.setImageResource(R.drawable.ic_baseline_videogame_asset_24);
            patternModeTxt.setText(R.string.normal_mode);
        } else {
            patternModeIcon.setImageResource(R.drawable.ic_baseline_timer_24);
            patternModeTxt.setText(R.string.timed_mode);
        }
    }

}
