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

    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;

    public Custom_Adapter(Context context,int layout, Cursor c,String[] from,int[] to,int flags) {
        super(context,layout,c,from,to,0);
        this.layout=layout;
        this.mContext = context;
        this.inflater=LayoutInflater.from(context);
        this.cr=c;
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView patternid=(TextView)view.findViewById(R.id.patternid);
        TextView patternsequence=(TextView)view.findViewById(R.id.patternsequence);
        TextView patternname=(TextView)view.findViewById(R.id.patternname);
        TextView patternmode=(TextView)view.findViewById(R.id.patternmode);
        ImageView pattternmodeic=(ImageView)view.findViewById(R.id.patternmodeic);
        if(patternmode.getText().toString().equals("1")){pattternmodeic.setImageResource(R.drawable.ic_baseline_videogame_asset_24);}
        else {pattternmodeic.setImageResource(R.drawable.ic_baseline_timer_24);}

    }

}
