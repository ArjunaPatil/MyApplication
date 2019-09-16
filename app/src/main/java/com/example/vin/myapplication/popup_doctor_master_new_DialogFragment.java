package com.example.vin.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class popup_doctor_master_new_DialogFragment extends android.support.v4.app.DialogFragment {


    // Empty constructor required for DialogFragment
    public popup_doctor_master_new_DialogFragment() {
    }

    public static popup_doctor_master_new_DialogFragment newInstance(String title) {
        popup_doctor_master_new_DialogFragment frag = new popup_doctor_master_new_DialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.popup_doctor_master_new_dialogfragment, container);

        Button btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });


        return view;


    }


    @Override
    public void onResume() {
        super.onResume();

        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        TextView txt_test = (TextView) getView().findViewById(R.id.txt_test);
        txt_test.setWidth(width);

    }

    @Override
    public void onStart() {

        super.onStart();
    }


    public interface Idoc_after_save {
        void onFinishSave(String fullname);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {
        try {
            EditText username = (EditText) getView().findViewById(R.id.username);
            Idoc_after_save listener = (Idoc_after_save) getTargetFragment();
            listener.onFinishSave(username.getText().toString());
            dismiss();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }


}