package com.example.vin.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
/*import static com.example.vin.myapplication.R.id.nav_Logout;*/

public class DashBord_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        fragment_DashBoard.OnFragmentInteractionListener,
        fragment_doctor_call.OnFragmentInteractionListener,
        fragment_doctor_call_new.OnFragmentInteractionListener,
        fragment_chemist_call.OnFragmentInteractionListener,
        fragment_chemist_call_new.OnFragmentInteractionListener,
        fragment_booking_call.OnFragmentInteractionListener,
        fragment_booking_call_new.OnFragmentInteractionListener,
        Fragment_Webview_test.OnFragmentInteractionListener,//
        fragment_User_Detail.OnFragmentInteractionListener,
        fragment_Doctor_List.OnFragmentInteractionListener,
        fragment_doctor_master_insert.OnFragmentInteractionListener,
        fragment_objective_list.OnFragmentInteractionListener,
        fragment_Patch_List.OnFragmentInteractionListener,
        fragment_patch_master_insert.OnFragmentInteractionListener,
        fragment_Chemist_List.OnFragmentInteractionListener,
        fragment_chemist_master_insert.OnFragmentInteractionListener,
        fragment_HeadquarterWise_Stockiest_List.OnFragmentInteractionListener,
        fragment_Hierarchy_Users_List.OnFragmentInteractionListener,
        fragment_Hierarchy_Headquarter_List.OnFragmentInteractionListener,
        fragment_Check_For_Update_Version.OnFragmentInteractionListener,
        fragment_testing_for_refresh_list.OnFragmentInteractionListener,
        fragment_lysten_reports.OnFragmentInteractionListener,
        fragment_daily_plan_insert_temp1.OnFragmentInteractionListener,
        fragment_daily_plan_insert_temp2.OnFragmentInteractionListener,
        fragment_rpt_plan_detail_list.OnFragmentInteractionListener,
        fragment_presenty_list.OnFragmentInteractionListener,
        fragment_for_Doctor_active_campaign.OnFragmentInteractionListener,
        fragment_miss_obj_list.OnFragmentInteractionListener,
        fragment_map_location_example.OnFragmentInteractionListener,
        fragment_add_current_location.OnFragmentInteractionListener,
        fragment_user_list.OnFragmentInteractionListener,
        fragment_user_lock.OnFragmentInteractionListener,
        fragment_Upload_Files.OnFragmentInteractionListener,
        fragment_upload_images_list.OnFragmentInteractionListener,
        fragment_upload_images_list1.OnFragmentInteractionListener,
        fragment_upload_images_list1_selected_image.OnFragmentInteractionListener,
        fragment_recycler.OnFragmentInteractionListener,
        fragment_recycler1.OnFragmentInteractionListener,
        fragment_recycler2.OnFragmentInteractionListener,
        fragment_new.OnFragmentInteractionListener,
        OneFragment.OnFragmentInteractionListener,
        TwoFragment.OnFragmentInteractionListener,
        ThreeFragment.OnFragmentInteractionListener,
        fragment_employee_location_show.OnFragmentInteractionListener,
        fragment_primary_secondary_on_app.OnFragmentInteractionListener,
        fragment_primary_secondary_on_app_filter.OnFragmentInteractionListener {
    private TextView emp_name, emp_email_id;

    public static int navItemIndex = 0;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    /* private ImageView imgNavHeaderBg, imgProfile;
     private TextView txtName, txtWebsite;
     private Toolbar toolbar;*/
    private FloatingActionButton fab;
    // public static Async_Class_Load_Live_Work_in_Realm task2;
    // public static Async_Class_Load_Employee_in_Realm task_Employee;
    // public static Async_Class_Load_Doctor_Master_in_Realm task_Doctor_Master;
    // public static Async_Class_Load_Patch_Master_in_Realm task_Patch_Master;
    RestService restService;

    public static Async_Class_Load_AppVersions_in_Realm task_app_evrsion;
    public static Async_Class_Load_User_in_Realm task_user;

    /*----------Arjun code for rotate screen-------*/
    /*private static final String TAG_WORKER_FRAGMENT = "fragment_user_list";
    private fragment_user_list mWorkerFragment;*/

    /*-------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dash_bord_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            //fab = (FloatingActionButton) findViewById(fab);

            navHeader = navigationView.getHeaderView(0);
            emp_name = (TextView) navHeader.findViewById(R.id.emp_name);
            emp_email_id = (TextView) navHeader.findViewById(R.id.emp_email_id);



       /* FloatingActionButton fab = (FloatingActionButton) findViewById(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            Fragment fragment = null;

            //initializing the fragment object which is selected
            fragment = new fragment_DashBoard();

            if (fragment != null) {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);

                ft.addToBackStack("ttt");

                ft.commit();
            }

            DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer1.closeDrawer(GravityCompat.START);

            //////////////////////////////
            loadNavHeader();
            //check_App_old_or_not();
            Load_BackgroundData();

            /*---------- Arjun Code For Rotate Screen-------------*/

            // find the retained fragment on activity restarts
            //FragmentManager fm = getFragmentManager();
            /*FragmentManager fm = getSupportFragmentManager();
            mWorkerFragment = (fragment_user_list) fm.findFragmentByTag(TAG_WORKER_FRAGMENT);

            // create the fragment and data the first time
            if (mWorkerFragment == null) {
                // add the fragment
                mWorkerFragment = new fragment_user_list();
                fm.beginTransaction().add(mWorkerFragment, TAG_WORKER_FRAGMENT).commit();
                // load data from a data source or perform any calculation
                mWorkerFragment.Bind_data_listview();
            }*/


             /*---------------------*/

        } catch (Exception ex) {
            // Log.i("Error Is:", ex.getMessage().toString());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }


    private void loadNavHeader() {
        try {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

            emp_name.setText(app_preferences.getString("full_name", "default"));


            emp_email_id.setText(app_preferences.getString("designation", "default"));
        } catch (Exception ex) {
            //Log.i("Error Is:", ex.getMessage().toString());
        }

    }

    public void Load_BackgroundData() {
        try {
            String versionName = BuildConfig.VERSION_NAME;
            String vv = versionName;

          /*  SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);*/

            /*task_app_evrsion = new Async_Class_Load_AppVersions_in_Realm(this, true, false, false);
            task_app_evrsion.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            task_user = new Async_Class_Load_User_in_Realm(null, this, true, false, false);
            task_user.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
        } catch (Exception ex) {
            //Log.i("Error Is:", ex.getMessage().toString());
        }
    }

    public void check_App_old_or_not() {
       /* try {
            String versionName = BuildConfig.VERSION_NAME;
            Realm mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_AppVersions> version = mRealm.where(POJO_AppVersions.class).findAll();
            List<POJO_AppVersions> app = version;
            for (POJO_AppVersions aaa : app) {
                //Log.i("Contact Details", app.getName() + "-" + contact.getEmployee() + "-" + contact.getWork_time());
                if (aaa.getVersionname().toString().equals(versionName.toString())) {
                    if (aaa.getSupported() == 0) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        try {
                            Toast.makeText(this, "PLEASE UPDATE APP..THIS VERSION IS NOT SUPPORTED FROM NOW", Toast.LENGTH_LONG).show();
                            this.wait(5000);
                            this.finish();
                            System.exit(0);
                        } catch (Exception ex) {

                        }

                        //System.exit(0);
                    }
                }
            }

            mRealm.close();
        } catch (Exception ex) {
            //("Error Is:", ex.getMessage().toString());
        }*/
    }

    @Override
    public void onBackPressed() {
        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    //super.onBackPressed();
                    //this.finish();
                 /*   this.finish();
                    System.exit(0);*/
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                // super.onBackPressed();
            }
        } catch (Exception ex) {
            // Log.i("Error Is:", ex.getMessage().toString());
        }

    }

    @Override
    public void onStop() {
        super.onStop();
       /* if (task2 != null) {
            if (task2.isCancelled() == false) {
                task2.cancel(true);
            }
        }
        if (task_app_evrsion  != null) {
            if (task_app_evrsion.isCancelled() == false) {
                task_app_evrsion.cancel(true);
            }
        }
        if (task_Employee != null) {
            if (task_Employee.isCancelled() == false) {
                task_Employee.cancel(true);
            }
        }

        if (task_Doctor_Master != null) {
            if (task_Doctor_Master.isCancelled() == false) {
                task_Doctor_Master.cancel(true);
            }
        }

        if (task_Patch_Master != null) {
            if (task_Patch_Master.isCancelled() == false) {
                task_Patch_Master.cancel(true);
            }
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_bord_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Toast.makeText(getApplicationContext(),"IN patch master",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            check_App_old_or_not();
            displaySelectedScreen(item.getItemId());
            return true;
        /*Fragment fragment = null;
        if (id == R.id.nav_profile) {
            // Handle the camera action
            fragment = new MyProfile();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;*/
        } catch (Exception ex) {
            //Log.i("Error Is:", ex.getMessage().toString());
            return false;
        }
    }

    private void displaySelectedScreen(int itemId) {
        try {

            //creating fragment object
            Fragment fragment = null;

            //initializing the fragment object which is selected
            switch (itemId) {

                case R.id.nav_home:
                    //fragment = new fragment_DashBoard();
                    FragmentManager manager = getSupportFragmentManager();
                    if (manager.getBackStackEntryCount() > 0) {
                        if (manager.getBackStackEntryCount() - (manager.getBackStackEntryCount() - 1) < 0) {
                            fragment = new fragment_DashBoard();
                        } else {
                            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - (manager.getBackStackEntryCount() - 1));
                            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    }
                    break;
               /* case R.id.nav_my_objective:

                    Bundle bundle = new Bundle();
                    bundle.putString("nav_bar", "nav_bar");

                    FragmentManager fm = getSupportFragmentManager();

                    popup_todays_objective_new_DialogFragment editNameDialogFragment = popup_todays_objective_new_DialogFragment.newInstance("Some Title");
                    editNameDialogFragment.setArguments(bundle);
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                    //show_dialog_today_objective();
                    break;*/

                case R.id.nav_my_profile:
                    fragment = new fragment_User_Detail();
                    break;

                case R.id.nav_doctor_call:
                    fragment = new fragment_doctor_call();
                    break;

                case R.id.nav_chemist_call:
                    fragment = new fragment_chemist_call();
                    break;

                case R.id.nav_campaign_booking:
                    fragment = new fragment_booking_call();
                    break;

                /*case R.id.nav_reports:
                    show_dialog_today_objective();
                    break;*/

                case R.id.nav_doctor_list:
                    fragment = new fragment_Doctor_List();

                    break;
                case R.id.nav_doctor_activity:
                    fragment = new fragment_for_Doctor_active_campaign();
                    break;
                case R.id.nav_patch_list:
                    fragment = new fragment_Patch_List();
                    break;
                case R.id.nav_chemist_list:
                    fragment = new fragment_Chemist_List();
                    break;

                case R.id.nav_stockist_list:
                    fragment = new fragment_HeadquarterWise_Stockiest_List();
                    //fragment = new fragment_Stockiest_Users_List();
                    break;
                case R.id.nav_hierarchy_list:
                    fragment = new fragment_Hierarchy_Users_List();
                    //fragment = new fragment_Report_View();
                    break;
                case R.id.nav_headquarter_list:
                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String designation = app_preferences.getString("designation", "default");
                    if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || designation.equals("NBM") || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager")) || (designation.equals("Administrator"))) {
                        fragment = new fragment_Hierarchy_Headquarter_List();
                    } else {
                        Toast.makeText(getApplicationContext(), "ACCESS DENIEDED", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.nav_location:
                    //fragment = new fragment_add_current_location();
                    fragment = new fragment_map_location_example();
                    break;

                case R.id.nav_user_list:
                    app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String access_flag = app_preferences.getString("allow_user_for_user_form", "0");
                    if (access_flag.equals("1")) {
                        fragment = new fragment_user_list();
                    } else {
                        Toast.makeText(getApplicationContext(), "ACCESS DENIEDED", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.nav_gallery:
                    fragment = new fragment_upload_images_list1();//fragment_upload_images_list fragment_Upload_Files();
                    //fragment = new fragment_user_lock();
                    //fragment = new fragment_Check_For_Update_Version();
                    //fragment=new Fragment_Webview_test();
                    break;

                case R.id.nav_upload:
                    fragment = new fragment_Upload_Files();//fragment_upload_images_list fragment_Upload_Files();
                    //fragment = new fragment_user_lock();
                    //fragment = new fragment_Check_For_Update_Version();
                    //fragment=new Fragment_Webview_test();
                    break;


                case R.id.nav_location_on_map:
                    fragment = new fragment_employee_location_show();
                    break;

                case R.id.nav_primary_on_app:
                    fragment = new fragment_primary_secondary_on_app_filter();//fragment_primary_secondary_on_app();
                    break;

                case R.id.nav_check_update:
                    //fragment = new fragment_new();
                    //fragment = new fragment_recycler1();//
                    fragment = new fragment_Check_For_Update_Version();
                    //fragment=new Fragment_Webview_test();
                    break;

                /*Start Testing Purpose : Refresh List*/
                case R.id.nav_test_list:
                    final SharedPreferences app_preferencess = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    designation = app_preferencess.getString("designation", "default");
                    if ((designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager")) || (designation.equals("Administrator"))) {
                        //   fragment = new fragment_test();
                    } else {
                        Toast.makeText(getApplicationContext(), "ACCESS DENIEDED", Toast.LENGTH_SHORT).show();
                    }
                    break;
                /*End Testing Purpose : Refresh List*/


               /*Vinayak Sir Testing:
                case R.id.nav_stockist_list:
                    fragment = new fragment_DashBoard();
                    //fragment=new Fragment_Webview_test();
                    break;*/

            /*case nav_Logout:
                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putString("status", "0");
                editor.putString("email", "");
                editor.putString("password", "");

                editor.commit();
              *//*  i
                if (task2.isCancelled() == false) {
                    task2.cancel(true);
                }*//*
                Intent k = new Intent(DashBord_main.this, Login.class);
                startActivity(k);
                break;*/
            }

            //replacing the fragment
            if (fragment != null) {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack("ss");
                ft.commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } catch (Exception ex) {
            //Log.i("Error Is:", ex.getMessage().toString());
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    public void show_dialog_today_objective() {
       /* ll_all_patches.setBackgroundColor(0xFFFFFF);
        try {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }
        ll_all_patches.setBackgroundColor(0xefebe9);*/
        final Dialog dialog = new Dialog(this);

        //setting custom layout to dialog
        dialog.setContentView(R.layout.dialog_today_objective);
        // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

// inflate and adjust layout
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_today_objective, null);

        layout.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
        layout.setMinimumHeight((int) (displayRectangle.height() * 0.9f));


        dialog.setContentView(layout);
        TextView txt_date = (TextView) dialog.findViewById(R.id.txt_date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        txt_date.setText(sdf.format(date));

        get_todays_objective(sdf.format(date));
        Realm mRealm = Realm.getDefaultInstance();
        final RealmResults<POJO_objective> result_query1;
        result_query1 = mRealm.where(POJO_objective.class).findAll();

        dialog.show();

    }

    public void get_todays_objective(String dd) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("select_date");
            jsonArray.put("objective");


            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            Filter1.put("Objective");
            Filter1.put("owner");
            Filter1.put("=");
            Filter1.put(emp_email_id.getText());


            Filter2.put("Objective");
            Filter2.put("select_date");
            Filter2.put("=");
            Filter2.put(dd);


            Filters.put(Filter1);
            Filters.put(Filter2);


            restService.getService().getObjective(sid, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    // ListView lv = (ListView) findViewById(R.id.listView);
                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");


                    //    List<LiveWork> LiveWorks=new ArrayList<LiveWork>();

                    // JSONArray jsonArray = new JSONArray(jsonArrayString);
                    //  List<String> list = new ArrayList<String>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_objective>>() {
                    }.getType();
                    List<POJO_objective> cl_objective = gson.fromJson(j2, type);


                }


                @Override
                public void failure(RetrofitError error) {

                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }


                    if (msg.equals("403 FORBIDDEN")) {

                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("status", "0");
                        editor.commit();

                        Intent k = new Intent(getApplicationContext(), Login.class); //got ot login activity
                        getApplicationContext().startActivity(k);


                        // Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();

                        Toast.makeText(getApplicationContext(), "PLEASE WAIT..REFRESHING..", Toast.LENGTH_SHORT).show();
                    } else if (msg.contains("139.59.63.181")) {
                        Toast.makeText(getApplicationContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                    }

                    if (task_app_evrsion != null) {
                        task_app_evrsion.cancel(true);
                    }

                }
            });
        } catch (Exception e) {

        }
    }


}
