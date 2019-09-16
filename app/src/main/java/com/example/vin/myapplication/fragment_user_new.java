package com.example.vin.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.example.vin.myapplication.R.layout.adapter_patch_list_for_dialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_user_new#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_user_new extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    ListView doctor_of_TBM;
    LinearLayout doctor_of_TBM_1;
    TextView doctor_of_TBM_2;
    ImageButton doctor_of_TBM_3;


    ListView select_doctor;
    LinearLayout select_doctor1;
    TextView select_doctor2;
    ImageButton select_doctor3;

    ListView select_patch;
    LinearLayout select_patch_1;
    TextView select_patch_2;
    ImageButton select_patch_3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragment_user_new() {
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
    public static fragment_user_new newInstance(String param1, String param2) {
        fragment_user_new fragment = new fragment_user_new();
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
        View view = inflater.inflate(R.layout.fragment_doctor_call_new, container, false);
        //employee = (TextView) view.getView().findViewById(employee);
        //return inflater.inflate(R.layout.fragment_my_profile2, container, false);
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

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("DOCTOR CALL");
            loaddoctor();


            /* doctor_of_TBM.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_doctor_of_TBM();

                 }
             });
             doctor_of_TBM_1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_doctor_of_TBM();

                 }
             });
             doctor_of_TBM_2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_doctor_of_TBM();

                 }
             });
             doctor_of_TBM_3.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_doctor_of_TBM();

                 }
             });


             select_doctor.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_doctor();

                 }
             });
             select_doctor1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_doctor();

                 }
             });
             select_doctor2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_doctor();

                 }
             });
             select_doctor3.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_doctor();

                 }
             });



             select_patch.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_patch();

                 }
             });
             select_patch_1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_patch();

                 }
             });
             select_patch_2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_patch();

                 }
             });
             select_patch_3.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_dialog_for_select_patch();

                 }
             });

*/


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddoctor() {


    }

    private void show_dialog_for_doctor_of_TBM() {
        try {
            final Dialog dialog = new Dialog(getContext());

            //setting custom layout to dialog
            dialog.setContentView(R.layout.dialog_select_patch);
            // retrieve display dimensions
            Rect displayRectangle = new Rect();
            Window window = getActivity().getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

// inflate and adjust layout
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_select_patch, null);

            layout.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
            layout.setMinimumHeight((int) (displayRectangle.height() * 0.9f));

            //dialog.setView(layout);
            dialog.setContentView(layout);


            Realm mRealm = Realm.getDefaultInstance();

            final RealmResults<POJO_Patch_master> result_query1;
            result_query1 = mRealm.where(POJO_Patch_master.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_Patch_master> POJO = result_query1;
            for (POJO_Patch_master obj : POJO) {
                // Log.i("Contact Details", contact.getName() + "-" + contact.getEmployee() + "-" + contact.getWork_time());
            }
            adapter_patch_list_for_dialog customAdapter = new adapter_patch_list_for_dialog(getContext(), adapter_patch_list_for_dialog, POJO);

            ListView list_patch_list = (ListView) dialog.findViewById(R.id.list_patch_list);
            list_patch_list.setAdapter(customAdapter);

            list_patch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                       @Override
                                                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                           try {
                                                               POJO_Patch_master clickedObj = (POJO_Patch_master) parent.getItemAtPosition(position);
                                                               select_patch_2.setText(clickedObj.getPatch_name());
                                                               //  getPatchName_fromPatchName(clickedObj.getName(), null, total_dr_count);
                                                               // bundle.putString("patch1", clickedObj.getName());
                                                               dialog.dismiss();
                                                           } catch (Exception ex) {
                                                               Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }


            );
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void show_dialog_for_select_doctor() {

    }

    private void show_dialog_for_select_patch() {

    }


    public boolean onBackPressed() {
        return false;
    }


}
