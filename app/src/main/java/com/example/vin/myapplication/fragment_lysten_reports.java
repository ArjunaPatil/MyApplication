package com.example.vin.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_lysten_reports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_lysten_reports extends Fragment
        implements datewise_reporting_summary_FragmentDialog.EditFromDateToDateDialogListener,
        primary_data_report_FragmentDialog.EditPrimaryDataReportDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ProgressDialog pDialog;
    private long mLastClickTime = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn_call_avg_summary;
    Button btn_call_all_summary;
    Button btn_call_individual_summary;
    Button btn_rpt_plan_detail;
    Button btn_rpt_camp_book_detail;
    Button btn_rpt_primary;
    RestService restService;
    private static final int PERMS_REQUEST_CODE = 123;
    String avg_flag = "";
    private OnFragmentInteractionListener mListener;

    public fragment_lysten_reports() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_DashBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_lysten_reports newInstance(String param1, String param2) {
        fragment_lysten_reports fragment = new fragment_lysten_reports();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lysten_reports, container, false);
        return view;

        //  return inflater.inflate(R.layout.fragment_fragment__dash_board, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            pDialog = new ProgressDialog(getContext());
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("REPORT LIST");

            init_controls();


            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = app_preferences.getString("designation", "default");
            String report_ip = app_preferences.getString("report_ip", "default");

            if (designation.equals("TBM")) {
                btn_call_all_summary.setVisibility(View.GONE);
            } else {
                btn_call_all_summary.setVisibility(View.VISIBLE);
            }
            if (designation.equals("NBM") || designation.equals("HR Manager") || designation.equals("Head of Marketing and Sales") || designation.equals("Admin")) {
                btn_call_avg_summary.setVisibility(View.VISIBLE);
            } else {
                btn_call_avg_summary.setVisibility(View.GONE);
            }

            btn_call_avg_summary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        avg_flag = "avg";
                        show_report_campaign_booking();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_call_all_summary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        show_all_summary_fragment_dialog();
                    /*fragment frag = new fragment_employee_list();
                    fragmenttransaction ft = getfragmentmanager().begintransaction();
                    ft.replace(r.id.content_frame, frag);
                    ft.settransition(fragmenttransaction.transit_fragment_fade);
                    ft.addtobackstack("b");
                    ft.commit();*/
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_call_individual_summary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        show_individual_summary_fragment_dialog();
                    /*Fragment frag = new fragment_Employee_List();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("b");
                    ft.commit();*/
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_rpt_camp_book_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        show_report_campaign_booking();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_rpt_plan_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Fragment frag = new fragment_rpt_plan_detail_list();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("rpt");
                        ft.commit();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_rpt_primary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        show_primary_report_fragment_dialog();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_controls() {
        try {
            btn_call_avg_summary = (Button) getView().findViewById(R.id.btn_call_avg_summary);
            btn_call_all_summary = (Button) getView().findViewById(R.id.btn_call_all_summary);
            btn_call_individual_summary = (Button) getView().findViewById(R.id.btn_call_individual_summary);
            btn_rpt_plan_detail = (Button) getView().findViewById(R.id.btn_rpt_plan_detail);
            btn_rpt_camp_book_detail = (Button) getView().findViewById(R.id.btn_rpt_camp_book_detail);
            btn_rpt_primary = (Button) getView().findViewById(R.id.btn_rpt_primary);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean Validation(POJO_Doctor_Calls POJO) {
        try {
            if (POJO.getDoctor_name().toString().contains("Select") == true) {
                Toast.makeText(getContext(), "PLEASE SELECT DOCTOR", Toast.LENGTH_SHORT).show();
                return false;
            } /*else if (POJO.getPatch_type().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "SELECT ONE PATCH TYPE ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getKm() == null) {
                Toast.makeText(getContext(), "PLEASE ENTER PATCH KM ", Toast.LENGTH_SHORT).show();
                return false;

            } else if (POJO.getEmployee_code().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "NO EMPLOYEE WITH THIS PATCH ..YOU ARE NOT ASSIGNED EMPLOYEE CODE YET" +
                        "PLEASE CONATACT LYSTEN GLOBAL IT SUPPORT ", Toast.LENGTH_SHORT).show();
                return false;
            } else*/
            return true;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void show_all_summary_fragment_dialog() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                datewise_reporting_summary_FragmentDialog dialog = datewise_reporting_summary_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("rpt", "all");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_lysten_reports.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_report_campaign_booking() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                datewise_reporting_summary_FragmentDialog dialog = datewise_reporting_summary_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("rpt", "camp");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_lysten_reports.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_individual_summary_fragment_dialog() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                datewise_reporting_summary_FragmentDialog dialog = datewise_reporting_summary_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("rpt", "ind");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_lysten_reports.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_primary_report_fragment_dialog() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                primary_data_report_FragmentDialog dialog = primary_data_report_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("rpt", "primary");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_lysten_reports.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishPrimaryDataReportDialog(String empid, String designation, String from_date, String to_date, String user_name) {
        try {

            if (!empid.isEmpty() && empid != null && designation != "" && designation != null && from_date != "" && from_date != null && to_date != "" && to_date != null && user_name != "" && user_name != null) {
                if (designation.equals("TBM")) {
                    designation = "name";
                }
                Show_primary_report(empid, designation, from_date, to_date, user_name);
            } else {
                Toast.makeText(getContext(), "Selection Part Is Compulsary", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Show_primary_report(String userID, String designation, String from_date, String to_date, String user_name) {

        try {
            View v = getView();
            pDialog = new ProgressDialog(getContext());

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String report_ip = app_preferences.getString("report_ip", "default");


            pDialog.show();
            ServerAPI api = ServerAPI.retrofit.create(ServerAPI.class);
            String ss = "";
            ss = "http://" + report_ip + "/MYSQLConnTest/?user_id=" + userID + "&designation=" + designation + "&from_date=" + from_date + "&to_date=" + to_date + "&user_name=" + user_name + "&parameter=primary";
///?user_id=hypatil126126@gmail.com&designation=ABM&from_date=2018-07-01&to_date=2018-07-31&user_name=Kasim%20Mevekari&parameter=primary
            api.downlload(ss).enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    try {


                        pDialog.hide();
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                        if (hasPermissions()) {
                            // our app has permissions.
                            // makeFolder();
                            File path = Environment.getExternalStorageDirectory();
                            File file = new File(path, "lysten_summary.pdf");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            IOUtils.write(response.body().bytes(), fileOutputStream);
                            fileOutputStream.close();


                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            Intent intent1 = Intent.createChooser(intent, "Open With");
                            try {
                                startActivity(intent1);
                            } catch (ActivityNotFoundException ex) {
                                // Instruct the user to install a PDF reader here, or something
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            //our app doesn't have permissions, So i m requesting permissions.
                            requestPerms();
                        }


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "REPORTING IP ERROR", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

           /* String myPdfUrl = "http://che.org.il/wp-content/uploads/2016/12/pdf-sample.pdf";
            String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
            // Log.i(TAG, "Opening PDF: " + url);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(url);*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFinishFromDateToDateDialog(String empid, String from_date, String to_date) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String userID = "";//app_preferences.getString("name", "default");
            String designation = app_preferences.getString("designation", "default");
            String param = "";

            if (empid.equals("camp")) {
                userID = app_preferences.getString("name", "default");
                param = "camp";
            } else if (!empid.isEmpty()) {
                userID = empid;
                param = "andi";
            } else {
                userID = app_preferences.getString("name", "default");
                param = "and";
            }
            if (!userID.isEmpty() && userID != null && designation != "" && designation != null && from_date != "" && from_date != null && to_date != "" && to_date != null) {
                Show_summary_report(userID, designation, from_date, to_date, param);

                // Show_summary_report(userID, designation, from_date, to_date, param);
            } else {
                Toast.makeText(getContext(), "Selection Part Is Compulsary", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Show_summary_report(String userID, String designation, String from_date, String to_date, String parameter) {

        try {
            View v = getView();
            pDialog = new ProgressDialog(getContext());

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String report_ip = app_preferences.getString("report_ip", "default");


            pDialog.show();
            ServerAPI api = ServerAPI.retrofit.create(ServerAPI.class);
            String ss = "";
            if (avg_flag.equals("avg")) {
                ss = "http://" + report_ip + "/MYSQLConnTest/?from_date=" + from_date + "&to_date=" + to_date + "&parameter=cntcall";
            } else {
                ss = "http://" + report_ip + "/MYSQLConnTest/?user_id=" + userID + "&designation=" + designation + "&from_date=" + from_date + "&to_date=" + to_date + "&parameter=" + parameter;
            }
            //?from_date=2017-09-01&to_date=2017-09-30&parameter=cntcall
            api.downlload(ss).enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    try {


                        pDialog.hide();
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                        if (hasPermissions()) {
                            // our app has permissions.
                            // makeFolder();
                            File path = Environment.getExternalStorageDirectory();
                            File file = new File(path, "lysten_summary.pdf");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            IOUtils.write(response.body().bytes(), fileOutputStream);
                            fileOutputStream.close();


                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            Intent intent1 = Intent.createChooser(intent, "Open With");
                            try {
                                startActivity(intent1);
                            } catch (ActivityNotFoundException ex) {
                                // Instruct the user to install a PDF reader here, or something
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }

                            /*WebView mWebView = (WebView) getView().findViewById(R.id.webview);
                            mWebView.getSettings().setJavaScriptEnabled(true);
                            String url = "http://docs.google.com/gview?embedded=true&url=file:///"+file.getAbsolutePath();
                            mWebView.loadUrl(url);*/

                        } else {
                            //our app doesn't have permissions, So i m requesting permissions.
                            requestPerms();
                        }


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "REPORTING IP ERROR", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

           /* String myPdfUrl = "http://che.org.il/wp-content/uploads/2016/12/pdf-sample.pdf";
            String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
            // Log.i(TAG, "Opening PDF: " + url);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(url);*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermissions() {
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions) {
            boolean somePermissionsForeverDenied = false;
            res = getContext().checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            } else {
                if (ActivityCompat.checkSelfPermission(getContext(), perms) == PackageManager.PERMISSION_GRANTED) {
                    //allowed
                    // Log.e("allowed", permission);
                } else {
                    //set to never ask again
                    // Log.e("set to never ask again", permission);
                    somePermissionsForeverDenied = true;
                }
            }
            if (somePermissionsForeverDenied) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Permissions Required")
                        .setMessage("You have forcefully denied some of the required permissions " +
                                "for this action. Please open settings, go to permissions and allow them.")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getContext().getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }

//            else
//            {
//                Toast.makeText(getContext(), "Go to settings->Apps->Lysten Global->App permission and enable Storage permission", Toast.LENGTH_SHORT).show();
//            }
        }
        return true;
    }


    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode) {
            case PERMS_REQUEST_CODE:

                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed) {
            //user granted all permissions we can perform our task.
            makeFolder();
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getContext(), "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void makeFolder() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "EDABlogs");

            if (!file.exists()) {
                Boolean ff = file.mkdir();
                if (ff) {
                    Toast.makeText(getContext(), "Folder created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to create folder", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "Folder already exist", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onBackPressed() {
        pDialog.hide();
        return false;
    }


}
