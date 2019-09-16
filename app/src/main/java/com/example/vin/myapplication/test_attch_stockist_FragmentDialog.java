package com.example.vin.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class test_attch_stockist_FragmentDialog extends android.support.v4.app.DialogFragment {

    private Realm mRealm;
    RestService restService;
    Context context;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_Stockiest last_POJO;
    public adapter_test_attch_stockist_list_for_dialog adapter;

    Bundle bundle;
    String UserID = "";

    // Empty constructor required for DialogFragment
    public test_attch_stockist_FragmentDialog() {
    }

    public static test_attch_stockist_FragmentDialog newInstance(String title) {
        test_attch_stockist_FragmentDialog frag = new test_attch_stockist_FragmentDialog();
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
        View view = inflater.inflate(R.layout.test_att_stockist_fragment_dialog, container);

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

            mRealm = Realm.getDefaultInstance();
            final RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).findAll().sort("modified", Sort.DESCENDING);


            if (result_query1 != null) {
                List<POJO_Stockiest> mList = result_query1;
                ListView lv = (ListView) getView().findViewById(R.id.listView);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                  try {
                                                      POJO_Stockiest clickedObj = (POJO_Stockiest) parent.getItemAtPosition(position);
                                                      sendBackTestStockistResult(clickedObj.getName(), clickedObj.getCustomer_name());
                                                  } catch (Exception ex) {
                                                      context = getActivity();
                                                      if (context != null) {

                                                          Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                      }
                                                  }
                                              }
                                          }

                );
                /*ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
                btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Toast.makeText(getContext(), "Please Refresh Stockist List...", Toast.LENGTH_SHORT).show();
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.delete(POJO_Stockiest.class);
                            mRealm.commitTransaction();
                            mRealm.close();

                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("Refreshing Data..");
                            bool_full_update = false;
                            full_update_calc();
                            datafull = false;
                            limitstart = 0;
                            Load_User();
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
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
            } else {
                alert_box();
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void data_fetch() {
        try {
            mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).findAll().sort("customer_name", Sort.ASCENDING);
            if (result_query1.size() > 0) {
                Bind_data_listview();
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            } else {
                alert_box();
                /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("Refreshing Data..");
                bool_full_update = false;
                full_update_calc();
                limitstart = 0;
                datafull = false;
                //Load_User();*/
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Bind_data_listview() {
        try {
            final RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).findAll().sort("customer_name", Sort.ASCENDING);
            List<POJO_Stockiest> mList = result_query1;

            adapter = new adapter_test_attch_stockist_list_for_dialog(getContext(), R.layout.adapter_test_att_stockist_list_for_dialog, mList);

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setAdapter(adapter);
            if (mList.size() == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO STOCKIST FOUND..");
            } else {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface EditTestStockistDialogListener {
        void onFinishTestStockistDialog(String id, String fullname);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackTestStockistResult(String id, String name) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            EditTestStockistDialogListener listener = (EditTestStockistDialogListener) getTargetFragment();
            listener.onFinishTestStockistDialog(id, name);
            dismiss();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void full_update_calc() {
        try {
            if ((bool_full_update == false)) {

                RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).findAll().sort("modified", Sort.DESCENDING);
                if (result_query1.size() > 0) {
                    //last_POJO = result_query1.first();27/07/2017
                } else {
                    bool_full_update = true;
                }
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void alert_box() {
        try {
           /* AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
            builder.setMessage("Not Load Fully Hierarchy. Please Load Hierarchy Fully...");

            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Fragment frag = new fragment_HeadquarterWise_Stockiest_List();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("stockiest_dialog");
                    ft.commit();

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();*/

            sendBackTestStockistResult("stockist_null", "stockist_null");
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}