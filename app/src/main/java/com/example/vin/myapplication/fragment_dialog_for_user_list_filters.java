package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.vin.myapplication.MoviesAdapter.context;

public class fragment_dialog_for_user_list_filters extends android.support.v4.app.DialogFragment {

    RestService restService;
    Bundle bundle;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;
    private long mLastClickTime = 0;

    private ProgressDialog pDialog;
    Button btn_edit;
    Button btn_back;

    TextView txt_username;

    Switch switch_user_all;//switch_user_all
    Switch switch_user_active;//switch_user_active
    Switch switch_user_master_unlock;//switch_user_master_unlock
    Switch switch_user_trans_unlock;//switch_user_trans_unlock

    String msg = "";

    public POJO_Doctor_Master last_POJO;


    // Empty constructor required for DialogFragment
    public fragment_dialog_for_user_list_filters() {
    }

    public static fragment_dialog_for_user_list_filters newInstance(String title) {
        try {
            fragment_dialog_for_user_list_filters frag = new fragment_dialog_for_user_list_filters();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCanceledOnTouchOutside(true);
            View view = inflater.inflate(R.layout.fragment_dialog_for_user_list_filters, container);

            return view;
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        try {
            super.onResume();

            /* Screen LANDSCAPE and Portait*/
            Window window = getDialog().getWindow();
            Point size = new Point();

            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);

            int width = size.x;

            window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);//75
            window.setGravity(Gravity.CENTER);

        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        try {
            init_controls();
            load_user_data();

            pDialog = new ProgressDialog(getContext());
            btn_edit.setVisibility(View.VISIBLE);


            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

                        } else {
                            save_user_lock_details();
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

                        } else {
                            sendBackUserListSetFilterResult("N");
                            pDialog.hide();
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            super.onStart();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void init_controls() {
        try {

            //bundle = this.getArguments();
            btn_edit = (Button) getView().findViewById(R.id.btn_edit);
            btn_back = (Button) getView().findViewById(R.id.btn_back);

            txt_username = (TextView) getView().findViewById(R.id.txt_username);

            switch_user_all = (Switch) getView().findViewById(R.id.switch_user_all);
            switch_user_active = (Switch) getView().findViewById(R.id.switch_user_active);
            switch_user_master_unlock = (Switch) getView().findViewById(R.id.switch_user_master_unlock);
            switch_user_trans_unlock = (Switch) getView().findViewById(R.id.switch_user_trans_unlock);

            switch_user_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (switch_user_all.isChecked() == true) {
                        switch_user_active.setChecked(false);
                        switch_user_master_unlock.setChecked(false);
                        switch_user_trans_unlock.setChecked(false);
                    }
                }
            });

            switch_user_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (switch_user_active.isChecked() == true) {
                        switch_user_all.setChecked(false);
                        switch_user_master_unlock.setChecked(false);
                        switch_user_trans_unlock.setChecked(false);
                    }
                }
            });

            switch_user_master_unlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (switch_user_master_unlock.isChecked() == true) {
                        switch_user_all.setChecked(false);
                        switch_user_active.setChecked(false);
                        switch_user_trans_unlock.setChecked(false);
                    }
                }
            });

            switch_user_trans_unlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (switch_user_trans_unlock.isChecked() == true) {
                        switch_user_all.setChecked(false);
                        switch_user_active.setChecked(false);
                        switch_user_master_unlock.setChecked(false);
                    }
                }
            });
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface ParamUserListSetFilterListener {
        void onFinishParamUserListSetFilter(String flag);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackUserListSetFilterResult(String flag) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            ParamUserListSetFilterListener listener = (ParamUserListSetFilterListener) getTargetFragment();
            listener.onFinishParamUserListSetFilter(flag);
            dismiss();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void save_user_lock_details() {
        try {

            String parameter = "0";
            if (switch_user_all.isChecked() == true) {
                parameter = "1";
            }
            if (switch_user_active.isChecked() == true) {
                parameter = "2";
            }
            if (switch_user_master_unlock.isChecked() == true) {
                parameter = "3";
            }
            if (switch_user_trans_unlock.isChecked() == true) {
                parameter = "4";
            }

            update_user_lock(parameter);

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_user_lock(String parameter) {
        try {

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("filter_for_user_list", parameter);
            editor.commit();

            Toast.makeText(getContext(), "SET FILTER SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            sendBackUserListSetFilterResult("Y");

        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void load_user_data() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String parameter = app_preferences.getString("filter_for_user_list", "0");

            if (parameter.equals("1")) {
                switch_user_all.setChecked(true);
            }
            if (parameter.equals("2")) {
                switch_user_active.setChecked(true);
            }
            if (parameter.equals("3")) {
                switch_user_master_unlock.setChecked(true);
            }
            if (parameter.equals("4")) {
                switch_user_trans_unlock.setChecked(true);
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
