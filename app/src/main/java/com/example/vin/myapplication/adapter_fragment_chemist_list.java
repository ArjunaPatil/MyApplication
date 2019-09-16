package com.example.vin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;

/**
 * Created by vin on 31/01/2017.
 */
public class adapter_fragment_chemist_list extends ArrayAdapter<POJO_Chemist> {
    private Realm mRealm;
    public Context _context;

    public adapter_fragment_chemist_list(Context context, int resource, List<POJO_Chemist> POJO_Chemist) {
        super(context, resource, POJO_Chemist);
        this._context = context;
        try {
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        try {

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = (app_preferences.getString("designation", "default"));

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.adapter_chemist_list, parent, false);
            }

            POJO_Chemist POJO_Chemist = getItem(position);

            if (POJO_Chemist != null) {


                TextView chemist_serial_no = (TextView) v.findViewById(R.id.chemist_serial_no);
                TextView tv_chemist_name = (TextView) v.findViewById(R.id.tv_chemist_name);
                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView tv_headquarter = (TextView) v.findViewById(R.id.tv_headquarter);
                TextView tv_city = (TextView) v.findViewById(R.id.tv_city);
                TextView tv_contact_no = (TextView) v.findViewById(R.id.tv_contact_no);


                chemist_serial_no.setText((position + 1) + ".");

                TextView mark = (TextView) v.findViewById(R.id.mark);
                String upperString = POJO_Chemist.getChemist_name().trim().substring(0,1).toUpperCase() ;
                mark.setText(upperString);


                tv_chemist_name.setText(POJO_Chemist.getChemist_name());
                tv_headquarter.setText(POJO_Chemist.getHeadquarter_name());//+"("+POJO_Chemist.getHeadquarter()+")");
                tv_city.setText(POJO_Chemist.getCity());
                tv_contact_no.setText(POJO_Chemist.getContact_no());
                tvname.setText(POJO_Chemist.getName());

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
