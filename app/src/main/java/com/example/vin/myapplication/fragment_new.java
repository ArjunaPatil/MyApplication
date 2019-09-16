package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

public class fragment_new extends Fragment {

    private Realm mRealm;


    private RecyclerView mRecyclerView;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_favorite_black_48dp,
            R.drawable.ic_call_black_48dp,
            R.drawable.ic_account_circle_black_48dp
    };
//https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
//https://stackoverflow.com/questions/35058819/android-tablayout-inside-fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {

            ((DashBord_main) getActivity()).setActionBarTitle("TAB DESIGN");
            View view = inflater.inflate(R.layout.fragment_new, container, false);
            //TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        /*tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("B"));
        tabLayout.addTab(tabLayout.newTab().setText("C"));*/

            tabLayout = (TabLayout) view.findViewById(R.id.tabs);

            final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
            // mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //mRecyclerView.setLayoutManager(mLayoutManager);

       /* viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);*/

            setupViewPager(viewPager);
            //tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();

        /*tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

            return view;
        } catch (Exception ex) {
            Log.i("Error Is:", ex.getMessage().toString());
            return null;
        }
    }


    private void setupTabIcons() {
        try {
            TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
            tabOne.setText("CALL LOC");

            Drawable dr = this.getResources().getDrawable(R.drawable.ic_favorite_black_48dp);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 30, 30, true));
            tabOne.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);

            /*tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_black_48dp, 0, 0);
            tabOne.setCompoundDrawablePadding(5);*/
            tabLayout.getTabAt(0).setCustomView(tabOne);

            TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
            tabTwo.setText("TWO");

            dr = this.getResources().getDrawable(R.drawable.ic_call_black_48dp);
            bitmap = ((BitmapDrawable) dr).getBitmap();
            d = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 30, 30, true));
            tabTwo.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);

            /*tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_call_black_48dp, 0, 0);
            tabTwo.setCompoundDrawablePadding(5);*/
            tabLayout.getTabAt(1).setCustomView(tabTwo);

            TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
            tabThree.setText("LIVE LOC");

            dr = this.getResources().getDrawable(R.drawable.ic_account_circle_black_48dp);
            bitmap = ((BitmapDrawable) dr).getBitmap();
            d = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 30, 30, true));
            tabThree.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);

            /*tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_account_circle_black_48dp, 0, 0);
            tabThree.setCompoundDrawablePadding(5);*/
            tabLayout.getTabAt(2).setCustomView(tabThree);

        } catch (Exception ex) {
            Log.i("Error Is:", ex.getMessage().toString());
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            //ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());//, mActiveProject.getInspiration()
            adapter.addFrag(new OneFragment(), "ONE");
            adapter.addFrag(new TwoFragment(), "TWO");
            adapter.addFrag(new ThreeFragment(), "THREE");
            viewPager.setAdapter(adapter);
        } catch (Exception ex) {
            Log.i("Error Is:", ex.getMessage().toString());
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new OneFragment();
                case 1:
                    return new TwoFragment();
                case 2:
                    return new ThreeFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    RestService restService;
    private ProgressDialog pDialog;

    @Override
    public void onStart() {
        try {

            super.onStart();
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_LatLon_Map.class);
            mRealm.commitTransaction();
            mRealm.close();

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));

            init_control();

            pDialog = new ProgressDialog(getContext());
            Get_LatLon_List();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void Get_LatLon_List() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            pDialog.show();
            restService.getService().getLat_Lon_of_Emp(sid, "2018-05-31", "kasimmevekari@gmail.com", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_LatLon_Map>>() {
                        }.getType();
                        List<POJO_LatLon_Map> POJO = gson.fromJson(j2, type);

                        /*--------*/
                        if (POJO == null || POJO.size() == 0) {
                            pDialog.hide();
                        } else {
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();
                            pDialog.hide();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "ACCESS FORBIDDEN", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            //txt_loading.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    //////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////

    public String designation = "", employeename = "ALL", User_ID_List_String = "";
    LinearLayout select_employee;
    LinearLayout select_employee1;
    TextView select_employee2;
    TextView name_objective_of_employee;
    ImageButton select_employee3;

    LinearLayout select_date;
    LinearLayout select_date1;
    TextView select_date2;
    TextView name_date_of_objective;
    ImageButton select_date3;

    View vw_employee;

    private long mLastClickTime = 0;
    Bundle bundle;

    public void init_control() {
        try {

            //employee spinner
            select_employee = (LinearLayout) getView().findViewById(R.id.select_employee);
            select_employee1 = (LinearLayout) getView().findViewById(R.id.select_employee1);
            select_employee3 = (ImageButton) getView().findViewById(R.id.select_employee3);
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);

            select_employee.setVisibility(View.GONE);
            select_employee1.setVisibility(View.GONE);
            select_employee3.setVisibility(View.GONE);
            select_employee2.setVisibility(View.GONE);
            name_objective_of_employee.setVisibility(View.GONE);

            //date spinner

            select_date1 = (LinearLayout) getView().findViewById(R.id.select_date1);
            select_date3 = (ImageButton) getView().findViewById(R.id.select_date3);
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            name_date_of_objective = (TextView) getView().findViewById(R.id.name_date_of_objective);

           /* select_date1.setVisibility(View.GONE);
            select_date3.setVisibility(View.GONE);
            select_date2.setVisibility(View.GONE);
            name_date_of_objective.setVisibility(View.GONE);*/

            //view (line)
            vw_employee = getView().findViewById(R.id.vw_employee);
           /* vw_employee.setVisibility(View.GONE);*/


            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager"))) {
                select_employee.setVisibility(View.VISIBLE);
                select_employee1.setVisibility(View.VISIBLE);
                select_employee3.setVisibility(View.VISIBLE);
                select_employee2.setVisibility(View.VISIBLE);
                name_objective_of_employee.setVisibility(View.VISIBLE);

                /*select_date.setVisibility(View.VISIBLE);*/
                select_date1.setVisibility(View.VISIBLE);
                select_date3.setVisibility(View.VISIBLE);
                select_date2.setVisibility(View.VISIBLE);
                name_date_of_objective.setVisibility(View.VISIBLE);

                vw_employee.setVisibility(View.VISIBLE);
            } else {

            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}