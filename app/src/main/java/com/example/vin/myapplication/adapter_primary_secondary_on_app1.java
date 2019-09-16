package com.example.vin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vin on 31/01/2017.
 */


public class adapter_primary_secondary_on_app1 extends ArrayAdapter<POJO_Primary_Secondary_On_App> {
    public Context _context;

    public adapter_primary_secondary_on_app1(Context context, int resource, List<POJO_Primary_Secondary_On_App> POJO_Primary_Secondary_On_App) {
        super(context, resource, POJO_Primary_Secondary_On_App);
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

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.adapter_primary_secondary_on_app1, parent, false);
            }

            final POJO_Primary_Secondary_On_App POJO = getItem(position);

            if (POJO != null) {

                TextView tv_txt_emp_name = (TextView) v.findViewById(R.id.tv_txt_emp_name);
                TextView tv_txt_emp_abm = (TextView) v.findViewById(R.id.tv_txt_emp_abm);
                TextView tv_txt_emp_rbm = (TextView) v.findViewById(R.id.tv_txt_emp_rbm);
                TextView tv_txt_emp_sm = (TextView) v.findViewById(R.id.tv_txt_emp_sm);
                TextView tv_txt_emp_nbm = (TextView) v.findViewById(R.id.tv_txt_emp_nbm);
                TextView tv_txt_stockist_name = (TextView) v.findViewById(R.id.tv_txt_stockist_name);
                TextView tv_txt_count_emp = (TextView) v.findViewById(R.id.tv_txt_count_emp);
                TextView tv_txt_target_value = (TextView) v.findViewById(R.id.tv_txt_target_value);
                TextView tv_txt_product_tot_sale = (TextView) v.findViewById(R.id.tv_txt_product_tot_sale);
                TextView tv_txt_product_tot_return = (TextView) v.findViewById(R.id.tv_txt_product_tot_return);

                /*if (!POJO.getEmployee().isEmpty()) {
                    tv_txt_emp_name.setText(POJO.getEmployee());
                }*/

                if (!POJO.getFullName().isEmpty()) {
                    tv_txt_emp_name.setText(POJO.getFullName());
                }

                /*if (!POJO.get().isEmpty()) {
                    tv_txt_emp_abm.setText(POJO.get());
                }

                if (!POJO.get().isEmpty()) {
                    tv_txt_emp_rbm.setText(POJO.get());
                }

                if (!POJO.get().isEmpty()) {
                    tv_txt_emp_sm.setText(POJO.get());
                }

                if (!POJO.get().isEmpty()) {
                    tv_txt_emp_nbm.setText(POJO.get());
                }*/


                if (!POJO.getStockist().isEmpty()) {
                    tv_txt_stockist_name.setText(POJO.getStockist() + "  [" + POJO.getTotEmp() + "]");
                }

                /*if (!POJO.getTotEmp().isEmpty()) {
                    tv_txt_count_emp.setText("[" + POJO.getTotEmp() + "]");
                }*/

                if (POJO.getTotSaleValue() != null) {
                    tv_txt_product_tot_sale.setText(POJO.getTotSaleValue().toString() + " [" + POJO.getTotSaleQty().toString() + "]");
                }

                if (POJO.getTotRetValue() != null) {
                    tv_txt_product_tot_return.setText(POJO.getTotRetValue().toString() + " [" + POJO.getTotRetQty().toString() + "]");
                }

                if (POJO.getTotSaleValue() != null && POJO.getTotRetValue() != null) {
                    float ff = POJO.getTotSaleValue() - POJO.getTotRetValue();
                    tv_txt_target_value.setText(String.valueOf(ff));
                }

                List<POJO_Primary_Secondary_On_App_Product_List> pp = (List) POJO.getProductData();//POJO_Primary_Secondary_On_App_Product_List
                adapter_primary_secondary_on_app2 adapter1 = new adapter_primary_secondary_on_app2(this.getContext(), R.layout.adapter_primary_secondary_on_app2, pp);
                ListView lv = (ListView) v.findViewById(R.id.primary_secondary_on_app_list_2);
                lv.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lv);
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /*private void initChildLayoutManager(RecyclerView rv_child, ArrayList<Child> childData) {
        rv_child.setLayoutManager(new NestedRecyclerLinearLayoutManager(ctx));
        ChildAdapter childAdapter = new ChildAdapter(childData);
        rv_child.setAdapter(childAdapter);
    }*/
}



    /*
    //https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
    private ArrayList<POJO_Primary_Secondary_On_App> dataSet;
    Context mContext;

// View lookup cache
private static class ViewHolder {
    TextView tv_txt_emp_name;
    TextView tv_txt_emp_abm;
    TextView tv_txt_emp_rbm;
    TextView tv_txt_emp_sm;
    TextView tv_txt_emp_nbm;
    TextView tv_txt_stockist_name;
    TextView tv_txt_count_emp;
    TextView tv_txt_target_value;
    TextView tv_txt_product_tot_sale;
    TextView tv_txt_product_tot_return;
    ListView primary_secondary_on_app_list_2;
}
    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        POJO_Primary_Secondary_On_App POJO = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_primary_secondary_on_app1, parent, false);

            viewHolder.tv_txt_emp_name = (TextView) convertView.findViewById(tv_txt_emp_name);
            viewHolder.tv_txt_emp_abm = (TextView) convertView.findViewById(tv_txt_emp_abm);
            viewHolder.tv_txt_emp_rbm = (TextView) convertView.findViewById(tv_txt_emp_rbm);
            viewHolder.tv_txt_emp_sm = (TextView) convertView.findViewById(tv_txt_emp_sm);
            viewHolder.tv_txt_emp_nbm = (TextView) convertView.findViewById(tv_txt_emp_nbm);
            viewHolder.tv_txt_stockist_name = (TextView) convertView.findViewById(tv_txt_stockist_name);
            viewHolder.tv_txt_count_emp = (TextView) convertView.findViewById(tv_txt_count_emp);
            viewHolder.tv_txt_target_value = (TextView) convertView.findViewById(tv_txt_target_value);
            viewHolder.tv_txt_product_tot_sale = (TextView) convertView.findViewById(tv_txt_product_tot_sale);
            viewHolder.tv_txt_product_tot_return = (TextView) convertView.findViewById(tv_txt_product_tot_return);
            viewHolder.primary_secondary_on_app_list_2 = (ListView) convertView.findViewById(R.id.primary_secondary_on_app_list_2);
            List<POJO_Primary_Secondary_On_App_Product_List> pp = (List) POJO.getProductData();//POJO_Primary_Secondary_On_App_Product_List

            adapter_primary_secondary_on_app2 adapter1 = new adapter_primary_secondary_on_app2(this.getContext(), R.layout.adapter_primary_secondary_on_app2, pp);
            ListView lv = (ListView) convertView.findViewById(R.id.primary_secondary_on_app_list_2);
            lv.setAdapter(adapter1);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

       //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

           if (!POJO.getEmployee().isEmpty()) {
            viewHolder.tv_txt_emp_name.setText(POJO.getEmployee());
        }

        if (!POJO.getStockist().isEmpty()) {
            viewHolder.tv_txt_stockist_name.setText(POJO.getStockist());
        }

        if (!POJO.getTotEmp().isEmpty()) {
            viewHolder.tv_txt_count_emp.setText("[" + POJO.getTotEmp() + "]");
        }

        if (POJO.getTotSaleValue() != null) {
            viewHolder.tv_txt_product_tot_sale.setText(POJO.getTotSaleValue().toString() + " [" + POJO.getTotSaleQty().toString() + "]");
        }

        if (POJO.getTotRetValue() != null) {
            viewHolder.tv_txt_product_tot_return.setText(POJO.getTotRetValue().toString() + " [" + POJO.getTotRetQty().toString() + "]");
        }

        if (POJO.getTotSaleValue() != null && POJO.getTotRetValue() != null) {
            float ff = POJO.getTotSaleValue() - POJO.getTotRetValue();
            viewHolder.tv_txt_target_value.setText(String.valueOf(ff));
        }

        // Return the completed view to render on screen
        return convertView;
    }
    */