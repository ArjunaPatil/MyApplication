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
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_recycler2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_recycler2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //https://stackoverflow.com/questions/32399751/android-how-to-load-more-items-for-recycleview-on-scroll
    //https://stackoverflow.com/questions/36994472/android-recyclerview-load-more-on-scrolltop


    //// add for load doctors
    private Realm mRealm;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    int sleep_wait = 0;

    public EditText txt_search;
    Spinner spinner_chemist;

    public boolean bool_full_update = false;
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

    private List<Contact> contacts;
    private ContactAdapter contactAdapter;
    private Random random;


    public fragment_recycler2() {
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
    public static fragment_recycler2 newInstance(String param1, String param2) {
        fragment_recycler2 fragment = new fragment_recycler2();
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
        return inflater.inflate(R.layout.fragment_view_recycler1, container, false);
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

            contacts = new ArrayList<>();
            random = new Random();

            //set dummy data
            for (int i = 0; i < 10; i++) {
                Contact contact = new Contact();
                contact.setPhone(phoneNumberGenerating());
                contact.setEmail("DevExchanges" + i + "@gmail.com");
                contacts.add(contact);
            }

            //find view by id and attaching adapter for the RecyclerView
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contactAdapter = new ContactAdapter(recyclerView, contacts, getContext());
            recyclerView.setAdapter(contactAdapter);

            //set load more listener for the RecyclerView adapter
            contactAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (contacts.size() <= 130) {
                        contacts.add(null);
                        contactAdapter.notifyItemInserted(contacts.size() - 1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                contacts.remove(contacts.size() - 1);
                                contactAdapter.notifyItemRemoved(contacts.size());

                                //Generating more data
                                int index = contacts.size();
                                int end = index + 10;
                                for (int i = index; i < end; i++) {
                                    Contact contact = new Contact();
                                    contact.setPhone(phoneNumberGenerating());
                                    contact.setEmail("DevExchanges" + i + "@gmail.com");
                                    contacts.add(contact);
                                }
                                contactAdapter.notifyDataSetChanged();
                                contactAdapter.setLoaded();
                            }
                        }, 5000);
                    } else {
                        Toast.makeText(getContext(), "Loading data completed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private String phoneNumberGenerating() {
        int low = 100000000;
        int high = 999999999;
        int randomNumber = random.nextInt(high - low) + low;

        return "0" + randomNumber;
    }


    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };


}