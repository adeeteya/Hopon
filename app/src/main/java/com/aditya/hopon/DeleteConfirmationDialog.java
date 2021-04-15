package com.aditya.hopon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteConfirmationDialog extends AppCompatDialogFragment {
    private DeleteConfirmationDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Pattern").setMessage("Are you sure you want to Delete it?").setNegativeButton("Cancel", (dialogInterface, i) -> {

        })
                .setPositiveButton("Yes", (dialogInterface, i) -> listener.onYesClicked());
        return builder.create();
    }
    public interface DeleteConfirmationDialogListener {
        void onYesClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteConfirmationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement DeleteConfirmationDialogListener");
        }
    }
}
