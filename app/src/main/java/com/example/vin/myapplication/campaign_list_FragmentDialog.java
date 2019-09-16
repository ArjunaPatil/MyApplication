package com.example.vin.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class campaign_list_FragmentDialog extends android.support.v4.app.DialogFragment {

    private EditText mEditText;


    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_Designation_User_Hierarchy last_POJO;
    public adapter_user_hierarchy_list_for_dialog adapter;

    Bundle bundle;
    public String ParameterStockiestForm = "";
    public String designation = "";

    ArrayList<POJO_Designation_User_Hierarchy> list_designation;


    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public campaign_list_FragmentDialog() {
    }

    public static campaign_list_FragmentDialog newInstance(String title) {
        campaign_list_FragmentDialog frag = new campaign_list_FragmentDialog();
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
        View view = inflater.inflate(R.layout.fragment_user_hirarchy_list_dialog, container);

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
        try {
            /*final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = app_preferences.getString("designation", "default");

*/
            list_Class_Method();


            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                              POJO_Designation_User_Hierarchy clickedObj = (POJO_Designation_User_Hierarchy) parent.getItemAtPosition(position);
                                              sendBackDesignationListHirarchyResult(clickedObj.getDesignation());

                                          }
                                      }


            );
           /* ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_designation = null;
                    list_Class_Method();
                    data_fetch();

                }
            });*/
            TextView txt_cancel = (TextView) getView().findViewById(R.id.txt_cancel);
            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    //Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
                }
            });


            data_fetch();

            super.onStart();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void list_Class_Method() {
        try {
            TextView lbl_your_name=(TextView)getView().findViewById(R.id.lbl_your_name);
            lbl_your_name.setText("SELECT CAMPAIGN");
            list_designation = new ArrayList<POJO_Designation_User_Hierarchy>();
            list_designation.add(last_POJO = new POJO_Designation_User_Hierarchy("Mission 1.50 "));
            list_designation.add(last_POJO = new POJO_Designation_User_Hierarchy("150+45 MILTON 10 LITRE"));
            list_designation.add(last_POJO = new POJO_Designation_User_Hierarchy("100+30 RAINCOAT"));
            list_designation.add(last_POJO = new POJO_Designation_User_Hierarchy("50+15 DREAMLINE DELIGHT"));
            list_designation.add(last_POJO = new POJO_Designation_User_Hierarchy("30+9 DREAMLINE HOTMEAL 3"));
            list_designation.add(last_POJO = new POJO_Designation_User_Hierarchy("30+9 NYLON UMBRELLA"));
            list_designation.add(last_POJO = new POJO_Designation_User_Hierarchy("20+6 ORPAT CALCULATOR"));


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void data_fetch() {
        try {
            Bind_data_listview();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Bind_data_listview() {
      /*  final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = app_preferences.getString("name", "default");
        String designation = app_preferences.getString("designation", "default");
        String EmployeeName = "";
        Realm mRealm = Realm.getDefaultInstance();*/

        if (list_designation.size() == 0) {
            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.VISIBLE);
            txt_loading.setText("NO CAMPAIGN FOUND..");
        } else {

            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");

            List<POJO_Designation_User_Hierarchy> mList = list_designation;

            adapter = new adapter_user_hierarchy_list_for_dialog(getContext(), R.layout.adapter_user_hirarchy_list_for_dialog, mList);

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setAdapter(adapter);
            if (mList.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO CAMPAIGN FOUND..");
            } else {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }
        }
    }

    public interface EditDesignationListHirarchyDialogListener {
        void onFinishDesignationListHirarchyDialog(String Designation);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackDesignationListHirarchyResult(String Designation) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditDesignationListHirarchyDialogListener listener = (EditDesignationListHirarchyDialogListener) getTargetFragment();
        listener.onFinishDesignationListHirarchyDialog(Designation);
        dismiss();

/*     UserNameListener listener=(UserNameListener)getTargetFragment();
        listener.onFinishUserDialog("hiii");*/

    }


}
