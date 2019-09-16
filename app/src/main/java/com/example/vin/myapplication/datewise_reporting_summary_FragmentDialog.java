package com.example.vin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class datewise_reporting_summary_FragmentDialog extends android.support.v4.app.DialogFragment implements
        user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener,
        DatePickerFragment.DateDialogListener {

    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    LinearLayout txt_to_date;
    LinearLayout txt_to_date1;
    TextView txt_to_date2;
    ImageButton txt_to_date3;

    LinearLayout txt_from_date;
    LinearLayout txt_from_date1;
    TextView txt_from_date2;
    ImageButton txt_from_date3;

    String flag = "";

    /*--------------------------*/
    public String designation = "", employeename = "ALL", my_id = "", rpt = "";
    LinearLayout select_employee;
    LinearLayout select_employee1;
    TextView select_employee2;
    TextView name_objective_of_employee;
    ImageButton select_employee3;
    View rpt_view;

    private long mLastClickTime = 0;
    Bundle bundle;

    // Empty constructor required for DialogFragment
    public datewise_reporting_summary_FragmentDialog() {
    }

    public static datewise_reporting_summary_FragmentDialog newInstance(String title) {
        datewise_reporting_summary_FragmentDialog frag = new datewise_reporting_summary_FragmentDialog();
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
        View view = inflater.inflate(R.layout.datewise_reporting_summary_fragment_dialog, container);

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

            init_control();

            /*-------------------*/

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));
            my_id = (app_preferences.getString("name", "default"));
            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
            name_objective_of_employee.setText(my_id);

            bundle = this.getArguments();
            if (bundle != null) {
                rpt = bundle.getString("rpt").toString();
            } else {
                rpt = "";
            }

            if (rpt.equals("ind")) {
                if (designation.equals("TBM")) {
                    select_employee.setVisibility(View.GONE);
                    rpt_view.setVisibility(View.GONE);
                } else {
                    select_employee.setVisibility(View.VISIBLE);
                    rpt_view.setVisibility(View.VISIBLE);
                }
            } else if (rpt.equals("camp")) {
                select_employee.setVisibility(View.GONE);
                rpt_view.setVisibility(View.GONE);
            } else {
                select_employee.setVisibility(View.GONE);
                rpt_view.setVisibility(View.GONE);
            }
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            select_employee2.setText(" SELECT EMPLOYEE");

            select_employee1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_employee();
                }
            });
            select_employee2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_employee();
                }
            });
            select_employee3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_employee();
                }
            });
            /*------------------*/

            txt_from_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "T";
                    show_dialog_for_select_date();
                }
            });
            txt_from_date1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "T";
                    show_dialog_for_select_date();
                }
            });
            txt_from_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "T";
                    show_dialog_for_select_date();
                }
            });
            txt_from_date3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "T";
                    show_dialog_for_select_date();
                }
            });


            txt_to_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "F";
                    show_dialog_for_select_date();
                }
            });
            txt_to_date1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "F";
                    show_dialog_for_select_date();
                }
            });
            txt_to_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "F";
                    show_dialog_for_select_date();
                }
            });
            txt_to_date3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = "F";
                    show_dialog_for_select_date();
                }
            });


            TextView txt_show = (TextView) getView().findViewById(R.id.txt_show);
            txt_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rpt.equals("ind")) {
                        if (designation.equals("TBM")) {
                            sendBackFromDateToDateResult(my_id, txt_from_date2.getText().toString(), txt_to_date2.getText().toString());
                            dismiss();
                        } else {
                            if (!select_employee2.getText().toString().trim().contains("SELECT EMPLOYEE") && !txt_from_date2.getText().toString().trim().contains("SELECT FROM DATE") && !txt_to_date2.getText().toString().trim().contains("SELECT TO DATE")) {
                                sendBackFromDateToDateResult(name_objective_of_employee.getText().toString(), txt_from_date2.getText().toString(), txt_to_date2.getText().toString());
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), "Please Select Employee, From & To Date", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (rpt.equals("camp")) {
                        if (!txt_from_date2.getText().toString().trim().contains("SELECT FROM DATE") && !txt_to_date2.getText().toString().trim().contains("SELECT TO DATE")) {
                            sendBackFromDateToDateResult("camp", txt_from_date2.getText().toString(), txt_to_date2.getText().toString());
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Please Select From & To Date", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!txt_from_date2.getText().toString().trim().contains("SELECT FROM DATE") && !txt_to_date2.getText().toString().trim().contains("SELECT TO DATE")) {
                            sendBackFromDateToDateResult("", txt_from_date2.getText().toString(), txt_to_date2.getText().toString());
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Please Select From & To Date", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            TextView txt_cancel = (TextView) getView().findViewById(R.id.txt_cancel);
            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    //Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
                }
            });

            super.onStart();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void init_control() {
        try {
            /*----------------------*/
            //employee spinner
            select_employee = (LinearLayout) getView().findViewById(R.id.select_employee);
            select_employee1 = (LinearLayout) getView().findViewById(R.id.select_employee1);
            select_employee3 = (ImageButton) getView().findViewById(R.id.select_employee3);
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
            rpt_view = getView().findViewById(R.id.rpt_view);
            /*----------------------*/

            txt_from_date = (LinearLayout) getView().findViewById(R.id.txt_from_date);
            txt_from_date1 = (LinearLayout) getView().findViewById(R.id.txt_from_date1);
            txt_from_date3 = (ImageButton) getView().findViewById(R.id.txt_from_date3);
            txt_from_date2 = (TextView) getView().findViewById(R.id.txt_from_date2);

            txt_to_date = (LinearLayout) getView().findViewById(R.id.txt_to_date);
            txt_to_date1 = (LinearLayout) getView().findViewById(R.id.txt_to_date1);
            txt_to_date3 = (ImageButton) getView().findViewById(R.id.txt_to_date3);
            txt_to_date2 = (TextView) getView().findViewById(R.id.txt_to_date2);

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public interface EditFromDateToDateDialogListener {
        void onFinishFromDateToDateDialog(String empid, String from_date, String to_date);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackFromDateToDateResult(String empid, String from_date, String to_date) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            EditFromDateToDateDialogListener listener = (EditFromDateToDateDialogListener) getTargetFragment();
            listener.onFinishFromDateToDateDialog(empid, from_date, to_date);
            dismiss();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFinishDialog(Date date) {
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String hireDate = sdf.format(date);
            //Toast.makeText(this, "Selected Date :"+ formatDate(date), Toast.LENGTH_SHORT).show();
            //select_date2.setText(hireDate);
            if (flag.toString().trim().equals("T")) {
                txt_from_date2.setText(hireDate);
            } else if (flag.toString().trim().equals("F")) {
                txt_to_date2.setText(hireDate);
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void show_dialog_for_select_date() {

        try {

            DatePickerFragment dialog = new DatePickerFragment();
            dialog.setTargetFragment(datewise_reporting_summary_FragmentDialog.this, 300);

            dialog.show(getFragmentManager(), "fdf");

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*------------------------------------------------------------------------------------------*/

    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname, String ss) {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Doctor_Calls_S.class);
            mRealm.commitTransaction();
            mRealm.close();


            if (id == "ALL") {
                name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
                name_objective_of_employee.setText(my_id);

                select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
                select_employee2.setText("ME");
            } else {
                select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
                select_employee2.setText(fullname.toString());

                name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
                name_objective_of_employee.setText(id.toString());
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_employee() {

        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                // if(dialog1.isVisible()==false) {
                user_list_hierarchy_me_FragmentDialog dialog1 = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "OBJ");
                //dialog.setView(layout);
                dialog1.setArguments(bundle);
                dialog1.setTargetFragment(datewise_reporting_summary_FragmentDialog.this, 300);
                dialog1.show(getFragmentManager(), "fdf");
                // }
            }
            mLastClickTime = SystemClock.elapsedRealtime();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
