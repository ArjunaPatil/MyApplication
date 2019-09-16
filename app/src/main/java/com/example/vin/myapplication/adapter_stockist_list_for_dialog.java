package com.example.vin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vin on 31/01/2017.
 */


public class adapter_stockist_list_for_dialog extends ArrayAdapter<POJO_Patch_master> {
    public Context _context;
    public adapter_stockist_list_for_dialog(Context context, int resource, List<POJO_Patch_master> POJO_Patch_master) {
        super(context, resource, POJO_Patch_master);
        this._context=context;
        try {
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        try {

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_patch_list_for_dialog, parent, false);
        }

        final POJO_Patch_master Patch_master = getItem(position);

        if (Patch_master != null) {

            TextView tvname = (TextView) v.findViewById(R.id.name);
            TextView patch_name = (TextView) v.findViewById(R.id.patch_name);
            TextView patch_type = (TextView) v.findViewById(R.id.patch_type);


            String upperString = Patch_master.getPatch_name().substring(0,1).toUpperCase() + Patch_master.getPatch_name().substring(1);

            patch_name.setText(upperString);
            patch_type.setText(Patch_master.getPatch_type());

            tvname.setText(Patch_master.getName());
            // tvmeeting_note.setText(live_work.getmeeting_note());


            Realm mRealm = Realm.getDefaultInstance();
            final RealmResults<POJO_Doctor_Master> result_query1 = mRealm.where(POJO_Doctor_Master.class).equalTo("patch",Patch_master.getName()).findAll();


           /* btn_add_doc.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(),Patch_master.getPatch_name(),Toast.LENGTH_SHORT).show();
                        }
                    }
            );*/


          /*  btn_add_doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(),Patch_master.getName(),Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("Patch_name", Patch_master.getPatch_name()); // use as per your need

                    Fragment frag = new fragment_Doctor_In_patch();
                    FragmentManager manager = ((Activity)_context).getFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    frag.setArguments(bundle);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("patch_to_doctor");

                    ft.commit();
                }
            });*/

        }

        return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
