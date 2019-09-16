package com.example.vin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vin on 31/01/2017.
 */


public class adapter_campaign_calls extends ArrayAdapter<POJO_campaign_booking_S> {
    public Context _context;
    public adapter_campaign_calls(Context context, int resource, List<POJO_campaign_booking_S> POJO_campaign_booking_S) {
        super(context, resource, POJO_campaign_booking_S);
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
            v = inflater.inflate(R.layout.adapter_common_list_five_line, parent, false);
        }

        final POJO_campaign_booking_S POJO = getItem(position);

        if (POJO != null) {

            TextView tvname = (TextView) v.findViewById(R.id.name);
            TextView head = (TextView) v.findViewById(R.id.head);
            TextView sub = (TextView) v.findViewById(R.id.sub);
            TextView sub_second_1 = (TextView) v.findViewById(R.id.sub_second_1);
            TextView sub_second = (TextView) v.findViewById(R.id.sub_second);
            TextView sub_third = (TextView) v.findViewById(R.id.sub_third);
            TextView sub_four = (TextView) v.findViewById(R.id.sub_four);



           // String upperString = POJO.getCampaign_name().substring(0,1).toUpperCase() ;
            TextView mark = (TextView) v.findViewById(R.id.mark);
           /* String upperString = POJO.getCampaign_name().toString().trim().substring(0,1).toUpperCase() ;
            mark.setText("-");*/


            head.setText(POJO.getCampaign_name());
           String cname= POJO.getCampaign_name().toString().trim();
            if(cname.contains("1.50")){
                mark.setText("1.50");
            }
            else if(cname.contains("150+45")){
                mark.setText("150+45");
            }
            else if(cname.contains("100+30")){
                mark.setText("100+30");
            }
            else if(cname.contains("50+15")){
                mark.setText("50+15");
            }
            else if(cname.contains("30+9")){
                mark.setText("30+9");
            }
            else if(cname.contains("20+6")){
                mark.setText("20+6");
            }
            else
            {
                mark.setText("-");
            }

           // sub.setText(POJO.getQty() +"/" +POJO.getFree()+"/ "+POJO.getTotal_qty()+"/"+POJO.getTotal_amount());
            sub_second_1.setText("DOCTOR : " +POJO.getDoctor_name());
            sub_second.setText("Chemist : " +POJO.getChemist_name());
            sub_third.setText("Stockist/Invoice: " +POJO.getStockist_name()+" / "+ POJO.getInvoice_number());
            if(cname.contains("Mission"))
            {
                sub.setText("(GOLD C:"+POJO.getQty() +")   (SHIRT:" +POJO.getFree()+")   (GIFT C:"+POJO.getTotal_qty()+")   (CC:"+POJO.getTotal_amount()+")");
                sub_third.setVisibility(View.GONE);
            }
            else {
                sub.setText("(QTY:"+POJO.getQty() +")   (FREE QTY:" +POJO.getFree()+")   (TOTAL QTY:"+POJO.getTotal_qty()+")   (VALUE:"+POJO.getTotal_amount()+")");
                sub_third.setVisibility(View.VISIBLE);
            }
//check doctor exist
            if(sub_second_1.getText().toString().contains("Select") || sub_second_1.getText().toString().contains("null") || sub_second_1.getText().toString().length()<1)
            {

                sub_second_1.setVisibility(View.GONE);
            }
            else {
                sub_second_1.setVisibility(View.VISIBLE);
            }
//check chemist exist
            if(sub_second.getText().toString().contains("Select") || sub_second.getText().toString().contains("null") || sub_second.getText().toString().length()<1)
            {

                sub_second.setVisibility(View.GONE);
            }
            else {
                sub_second.setVisibility(View.VISIBLE);
            }
            sub_four.setText( POJO.getStatus());
            tvname.setText(POJO.getName());







        }

        return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
