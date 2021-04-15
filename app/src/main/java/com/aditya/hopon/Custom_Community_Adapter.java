package com.aditya.hopon;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Custom_Community_Adapter extends ArrayAdapter<Patterns> {
    final List<Patterns> patternsList;
    private final Activity mContext;

    public Custom_Community_Adapter(Activity mContext, List<Patterns> patternsList) {
        super(mContext, R.layout.pattern_online_layout, patternsList);
        this.mContext = mContext;
        this.patternsList = patternsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listitemView = inflater.inflate(R.layout.pattern_online_layout, null, true);
        Patterns pattern = patternsList.get(position);
        TextView pattername = listitemView.findViewById(R.id.patternname);
        TextView authornametxt = listitemView.findViewById(R.id.authornametxt);
        TextView idTextView = listitemView.findViewById(R.id.patternid);
        TextView sequenceTextView = listitemView.findViewById(R.id.patternsequence);
        ImageView patternmodeic = listitemView.findViewById(R.id.patternmodeic);
        TextView patternmode = listitemView.findViewById(R.id.patternmode);
        TextView patternuid = listitemView.findViewById(R.id.patternuid);
        TextView patternpid = listitemView.findViewById(R.id.patternpid);
        pattername.setText(pattern.getName());
        patternuid.setText(pattern.getUid());
        patternpid.setText(pattern.getPid());
        idTextView.setText(String.valueOf(position + 1));
        if (pattern.getMode() == 1) {
            patternmode.setText(R.string.normalmode);
            patternmodeic.setImageResource(R.drawable.ic_baseline_videogame_asset_24);
        } else {
            patternmode.setText(R.string.timedmode);
            patternmodeic.setImageResource(R.drawable.ic_baseline_timer_24);
        }
        authornametxt.setText(pattern.getAuthor());
        sequenceTextView.setText(pattern.getSequence());
        String sequence = pattern.getSequence();
        ImageView img1 = listitemView.findViewById(R.id.pattgrid1);
        ImageView img2 = listitemView.findViewById(R.id.pattgrid2);
        ImageView img3 = listitemView.findViewById(R.id.pattgrid3);
        ImageView img4 = listitemView.findViewById(R.id.pattgrid4);
        ImageView img5 = listitemView.findViewById(R.id.pattgrid5);
        ImageView img6 = listitemView.findViewById(R.id.pattgrid6);
        ImageView img7 = listitemView.findViewById(R.id.pattgrid7);
        ImageView img8 = listitemView.findViewById(R.id.pattgrid8);
        ImageView img9 = listitemView.findViewById(R.id.pattgrid9);
        ImageView imgA = listitemView.findViewById(R.id.pattgridA);
        ImageView imgB = listitemView.findViewById(R.id.pattgridB);
        ImageView imgC = listitemView.findViewById(R.id.pattgridC);
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == '1') {
                i++;
                switch (sequence.charAt(i)) {
                    case '1':
                        img1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '2':
                        img2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '3':
                        img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '4':
                        img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '5':
                        img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '6':
                        img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '7':
                        img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '8':
                        img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case '9':
                        img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case 'A':
                        imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case 'B':
                        imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                    case 'C':
                        imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        break;
                }
            }
            else{
                i++;
                switch(sequence.charAt(i)){
                    case '1':img1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '2':img2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '3':img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '4':img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '5':img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '6':img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '7':img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '8':img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '9':img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case 'A':imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case 'B':imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case 'C':imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                }
                i+=2;
                switch(sequence.charAt(i)){
                    case '1':img1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '2':img2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '3':img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '4':img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '5':img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '6':img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '7':img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '8':img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case '9':img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case 'A':imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case 'B':imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                    case 'C':imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));break;
                }
            }
        }
        return listitemView;
    }
}
