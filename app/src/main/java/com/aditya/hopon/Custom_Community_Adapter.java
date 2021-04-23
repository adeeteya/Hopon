package com.aditya.hopon;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Custom_Community_Adapter extends RecyclerView.Adapter<Custom_Community_Adapter.patternViewHolder> {

    private final OnPatternClickListener monPatternClickListener;
    private final Activity mContext;
    private List<Patterns> patternsList;

    public Custom_Community_Adapter(Activity mContext, List<Patterns> patternsList, OnPatternClickListener onPatternClickListener) {
        this.mContext = mContext;
        this.patternsList = patternsList;
        this.monPatternClickListener = onPatternClickListener;
    }

    @NonNull
    @Override
    public patternViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.pattern_online_layout, parent, false);
        return new patternViewHolder(view, monPatternClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull patternViewHolder holder, int position) {
        Patterns pattern = patternsList.get(position);
        holder.pattername.setText(pattern.getName());
        holder.idTextView.setText(String.valueOf(position + 1));
        if (pattern.getMode() == 1) {
            holder.patternmode.setText(R.string.normalmode);
            holder.patternmodeic.setImageResource(R.drawable.ic_baseline_videogame_asset_24);
        } else {
            holder.patternmode.setText(R.string.timedmode);
            holder.patternmodeic.setImageResource(R.drawable.ic_baseline_timer_24);
        }
        holder.authornametxt.setText(pattern.getAuthor());
        String sequence = pattern.getSequence();
        int pno = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == '1') {
                i++;
                switch (sequence.charAt(i)) {
                    case '3':
                        holder.img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txt3.setText(String.valueOf(++pno));
                        break;
                    case '4':
                        holder.img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txt4.setText(String.valueOf(++pno));
                        break;
                    case '5':
                        holder.img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txt5.setText(String.valueOf(++pno));
                        break;
                    case '6':
                        holder.img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txt6.setText(String.valueOf(++pno));
                        break;
                    case '7':
                        holder.img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txt7.setText(String.valueOf(++pno));
                        break;
                    case '8':
                        holder.img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txt8.setText(String.valueOf(++pno));
                        break;
                    case '9':
                        holder.img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txt9.setText(String.valueOf(++pno));
                        break;
                    case 'A':
                        holder.imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txtA.setText(String.valueOf(++pno));
                        break;
                    case 'B':
                        holder.imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txtB.setText(String.valueOf(++pno));
                        break;
                    case 'C':
                        holder.imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txtC.setText(String.valueOf(++pno));
                        break;
                    case 'D':
                        holder.imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txtD.setText(String.valueOf(++pno));
                        break;
                    case 'E':
                        holder.imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF5252")));
                        holder.txtE.setText(String.valueOf(++pno));
                        break;
                }
            } else {
                i++;
                switch (sequence.charAt(i)) {
                    case '3':
                        holder.img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt3.setText(String.valueOf(++pno));
                        break;
                    case '4':
                        holder.img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt4.setText(String.valueOf(++pno));
                        break;
                    case '5':
                        holder.img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt5.setText(String.valueOf(++pno));
                        break;
                    case '6':
                        holder.img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt6.setText(String.valueOf(++pno));
                        break;
                    case '7':
                        holder.img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt7.setText(String.valueOf(++pno));
                        break;
                    case '8':
                        holder.img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt8.setText(String.valueOf(++pno));
                        break;
                    case '9':
                        holder.img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt9.setText(String.valueOf(++pno));
                        break;
                    case 'A':
                        holder.imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtA.setText(String.valueOf(++pno));
                        break;
                    case 'B':
                        holder.imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtB.setText(String.valueOf(++pno));
                        break;
                    case 'C':
                        holder.imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtC.setText(String.valueOf(++pno));
                        break;
                    case 'D':
                        holder.imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtD.setText(String.valueOf(++pno));
                        break;
                    case 'E':
                        holder.imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtE.setText(String.valueOf(++pno));
                        break;
                }
                i += 2;
                switch (sequence.charAt(i)) {
                    case '3':
                        holder.img3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt3.setText(String.valueOf(++pno));
                        break;
                    case '4':
                        holder.img4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt4.setText(String.valueOf(++pno));
                        break;
                    case '5':
                        holder.img5.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt5.setText(String.valueOf(++pno));
                        break;
                    case '6':
                        holder.img6.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt6.setText(String.valueOf(++pno));
                        break;
                    case '7':
                        holder.img7.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt7.setText(String.valueOf(++pno));
                        break;
                    case '8':
                        holder.img8.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt8.setText(String.valueOf(++pno));
                        break;
                    case '9':
                        holder.img9.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txt9.setText(String.valueOf(++pno));
                        break;
                    case 'A':
                        holder.imgA.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtA.setText(String.valueOf(++pno));
                        break;
                    case 'B':
                        holder.imgB.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtB.setText(String.valueOf(++pno));
                        break;
                    case 'C':
                        holder.imgC.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtC.setText(String.valueOf(++pno));
                        break;
                    case 'D':
                        holder.imgD.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtD.setText(String.valueOf(++pno));
                        break;
                    case 'E':
                        holder.imgE.setImageTintList(ColorStateList.valueOf(Color.parseColor("#536DFE")));
                        holder.txtE.setText(String.valueOf(++pno));
                        break;
                }
            }
        }
    }

    @Override
    public void onViewRecycled(@NonNull patternViewHolder holder) {
        holder.img3.setImageTintList(null);
        holder.img4.setImageTintList(null);
        holder.img5.setImageTintList(null);
        holder.img6.setImageTintList(null);
        holder.img7.setImageTintList(null);
        holder.img8.setImageTintList(null);
        holder.img9.setImageTintList(null);
        holder.imgA.setImageTintList(null);
        holder.imgB.setImageTintList(null);
        holder.imgC.setImageTintList(null);
        holder.imgD.setImageTintList(null);
        holder.imgE.setImageTintList(null);
        holder.hiddendownloadlayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return patternsList.size();
    }

    public void filterlist(List<Patterns> filteredList) {
        patternsList = filteredList;
        notifyDataSetChanged();
    }

    public interface OnPatternClickListener {
        void OnPatternClick(int position, View view);
    }

    public static class patternViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView pattername, authornametxt, idTextView, patternmode, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txtA, txtB, txtC, txtD, txtE;
        final ImageView patternmodeic, img3, img4, img5, img6, img7, img8, img9, imgA, imgB, imgC, imgD, imgE;
        final ConstraintLayout hiddendownloadlayout;
        final OnPatternClickListener onPatternClickListener;

        public patternViewHolder(@NonNull View itemView, OnPatternClickListener onPatternClickListener) {
            super(itemView);
            pattername = itemView.findViewById(R.id.patternname);
            authornametxt = itemView.findViewById(R.id.authornametxt);
            idTextView = itemView.findViewById(R.id.patternid);
            patternmodeic = itemView.findViewById(R.id.patternmodeic);
            patternmode = itemView.findViewById(R.id.patternmode);
            img3 = itemView.findViewById(R.id.pattgrid3);
            img4 = itemView.findViewById(R.id.pattgrid4);
            img5 = itemView.findViewById(R.id.pattgrid5);
            img6 = itemView.findViewById(R.id.pattgrid6);
            img7 = itemView.findViewById(R.id.pattgrid7);
            img8 = itemView.findViewById(R.id.pattgrid8);
            img9 = itemView.findViewById(R.id.pattgrid9);
            imgA = itemView.findViewById(R.id.pattgridA);
            imgB = itemView.findViewById(R.id.pattgridB);
            imgC = itemView.findViewById(R.id.pattgridC);
            imgD = itemView.findViewById(R.id.pattgridD);
            imgE = itemView.findViewById(R.id.pattgridE);
            txt3 = itemView.findViewById(R.id.patttxt3);
            txt4 = itemView.findViewById(R.id.patttxt4);
            txt5 = itemView.findViewById(R.id.patttxt5);
            txt6 = itemView.findViewById(R.id.patttxt6);
            txt7 = itemView.findViewById(R.id.patttxt7);
            txt8 = itemView.findViewById(R.id.patttxt8);
            txt9 = itemView.findViewById(R.id.patttxt9);
            txtA = itemView.findViewById(R.id.patttxtA);
            txtB = itemView.findViewById(R.id.patttxtB);
            txtC = itemView.findViewById(R.id.patttxtC);
            txtD = itemView.findViewById(R.id.patttxtD);
            txtE = itemView.findViewById(R.id.patttxtE);
            hiddendownloadlayout = itemView.findViewById(R.id.hiddendownloadlayout);
            itemView.setOnClickListener(this);
            this.onPatternClickListener = onPatternClickListener;
        }

        @Override
        public void onClick(View view) {
            onPatternClickListener.OnPatternClick(getAdapterPosition(), view);
        }
    }
}
