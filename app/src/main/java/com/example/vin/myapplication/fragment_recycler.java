package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_recycler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_recycler extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //// add for load doctors
    private Realm mRealm;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    int sleep_wait = 0;

    public EditText txt_search;
    Spinner spinner_chemist;

    public boolean bool_full_update = true;
    public POJO_Chemist last_POJO;
    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;
    /////

    private OnFragmentInteractionListener mListener;

    ListView listView;
    public EditText edit_search;
    public String designation = "", employeename = "ALL", User_ID_List_String = "";

    private ProgressDialog pDialog;

    RestService restService;
    public adapter_fragment_chemist_list customAdapter;
    Bundle bundle;

    LinearLayout select_employee;
    LinearLayout select_employee1;
    TextView select_employee2;
    TextView name_chemist_of_employee;
    ImageButton select_employee3;
    View vw_employee;
    private long mLastClickTime = 0;

    public fragment_recycler() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Doctor_List.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_recycler newInstance(String param1, String param2) {
        fragment_recycler fragment = new fragment_recycler();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        try {
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));
            ((DashBord_main) getActivity()).setActionBarTitle("Recycler View");
            init_control();
            super.onStart();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
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
        return inflater.inflate(R.layout.fragment_view_recycler, container, false);
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

    /*private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;*/

    public void init_control() {
        try {

           /* recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
            recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));

            mAdapter = new RecyclerAdapter(getContext(), movieList);
            //recyclerView.setAdapter(mAdapter);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            prepareMovieData();*/

            tvEmptyView = (TextView) getView().findViewById(R.id.empty_view);
            mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
            studentList = new ArrayList<POJO_Recycler_View>();
            handler = new Handler();
            loadData();

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            // use a linear layout manager
            mRecyclerView.setLayoutManager(mLayoutManager);
            // create an Object for Adapter
            mAdapter = new RecyclerAdapter1(studentList, mRecyclerView);
            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);
            //  mAdapter.notifyDataSetChanged();
            if (studentList.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                tvEmptyView.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                tvEmptyView.setVisibility(View.GONE);
            }

            mAdapter.setOnLoadMoreListener(new RecyclerAdapter1.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //add null , so the adapter will check view_type and show progress bar at bottom
                    studentList.add(null);
                    mAdapter.notifyItemInserted(studentList.size() - 1);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //   remove progress item
                            studentList.remove(studentList.size() - 1);
                            mAdapter.notifyItemRemoved(studentList.size());
                            //add items one by one
                            int start = studentList.size();
                            int end = start + 20;
                            for (int i = start + 1; i <= end; i++) {
                                studentList.add(new POJO_Recycler_View("Student " + i, "AndroidStudent" + i + "@gmail.com"));
                                mAdapter.notifyItemInserted(studentList.size());
                            }
                            mAdapter.setLoaded();
                            //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                        }
                    }, 2000);
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void prepareMovieData() {
        try {
            /*Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
            movieList.add(movie);

            movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
            movieList.add(movie);

            movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
            movieList.add(movie);

            movie = new Movie("Shaun the Sheep", "Animation", "2015");
            movieList.add(movie);

            movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
            movieList.add(movie);

            movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
            movieList.add(movie);

            movie = new Movie("Up", "Animation", "2009");
            movieList.add(movie);

            movie = new Movie("Star Trek", "Science Fiction", "2009");
            movieList.add(movie);

            movie = new Movie("The LEGO Movie", "Animation", "2014");
            movieList.add(movie);

            movie = new Movie("Iron Man", "Action & Adventure", "2008");
            movieList.add(movie);

            movie = new Movie("Aliens", "Science Fiction", "1986");
            movieList.add(movie);

            movie = new Movie("Chicken Run", "Animation", "2000");
            movieList.add(movie);

            movie = new Movie("Back to the Future", "Science Fiction", "1985");
            movieList.add(movie);

            movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
            movieList.add(movie);

            movie = new Movie("Goldfinger", "Action & Adventure", "1965");
            movieList.add(movie);

            movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
            movieList.add(movie);

            mAdapter.notifyDataSetChanged();*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter1 mAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<POJO_Recycler_View> studentList;
    protected Handler handler;

    // load initial data
    private void loadData() {
        try {
            for (int i = 1; i <= 20; i++) {
                studentList.add(new POJO_Recycler_View("Student " + i, "androidstudent" + i + "@gmail.com"));
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };


}