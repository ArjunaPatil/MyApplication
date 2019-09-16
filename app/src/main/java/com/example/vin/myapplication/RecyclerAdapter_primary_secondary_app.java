package com.example.vin.myapplication;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter_primary_secondary_app extends RecyclerView.Adapter {

    //http://sab99r.com/blog/recyclerview-endless-load-more

    //http://www.devexchanges.info/2017/02/android-recyclerview-dynamically-load.html       best example

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean isLoading;
    private Context activity;
    private List<POJO_Primary_Secondary_On_App> contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private OnLoadMoreListener onLoadMoreListener;
    View view;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public RecyclerAdapter_primary_secondary_app(RecyclerView recyclerView, List<POJO_Primary_Secondary_On_App> contacts, Context activity) {
        this.contacts = contacts;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            //View view = LayoutInflater.from(activity).inflate(R.layout.adapter_primary_secondary_on_app1, parent, false);
            view = LayoutInflater.from(activity).inflate(R.layout.adapter_primary_secondary_on_app1, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            //View view = LayoutInflater.from(activity).inflate(R.layout.layout_loading_item, parent, false);
            view = LayoutInflater.from(activity).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {

            POJO_Primary_Secondary_On_App contact = contacts.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            /*userViewHolder.phone.setText(contact.getName());
            userViewHolder.email.setText(contact.getFull_name());*/

            userViewHolder.tv_txt_emp_name.setText(contact.getEmployee());
            /*userViewHolder.tv_txt_emp_abm;
            userViewHolder.tv_txt_emp_rbm;
            userViewHolder.tv_txt_emp_sm;
            userViewHolder.tv_txt_emp_nbm;*/
            userViewHolder.tv_txt_stockist_name.setText(contact.getStockist());
            userViewHolder.tv_txt_count_emp.setText("[" + contact.getTotEmp() + "]");
            /*userViewHolder.tv_txt_target_value;*/
            userViewHolder.tv_txt_product_tot_sale.setText(contact.getTotSaleValue().toString() + " [" + contact.getTotSaleQty().toString() + "]");
            userViewHolder.tv_txt_product_tot_return.setText(contact.getTotRetValue().toString() + " [" + contact.getTotRetQty().toString() + "]");


            List<POJO_Primary_Secondary_On_App_Product_List> pp = (List) contact.getProductData();
            adapter_primary_secondary_on_app2 adapter2 = new adapter_primary_secondary_on_app2(view.getContext(), R.layout.adapter_primary_secondary_on_app2, pp);
            userViewHolder.lv.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    // "Loading item" ViewHolder
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    // "Normal item" ViewHolder
    private class UserViewHolder extends RecyclerView.ViewHolder {
        /*public TextView phone;
        public TextView email;*/

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
        ListView lv;

        public UserViewHolder(View view) {
            super(view);
            /*phone = (TextView) view.findViewById(R.id.txt_phone);
            email = (TextView) view.findViewById(R.id.txt_email);*/

            tv_txt_emp_name = (TextView) view.findViewById(R.id.tv_txt_emp_name);
            tv_txt_emp_abm = (TextView) view.findViewById(R.id.tv_txt_emp_abm);
            tv_txt_emp_rbm = (TextView) view.findViewById(R.id.tv_txt_emp_rbm);
            tv_txt_emp_sm = (TextView) view.findViewById(R.id.tv_txt_emp_sm);
            tv_txt_emp_nbm = (TextView) view.findViewById(R.id.tv_txt_emp_nbm);
            tv_txt_stockist_name = (TextView) view.findViewById(R.id.tv_txt_stockist_name);
            tv_txt_count_emp = (TextView) view.findViewById(R.id.tv_txt_count_emp);
            tv_txt_target_value = (TextView) view.findViewById(R.id.tv_txt_target_value);
            tv_txt_product_tot_sale = (TextView) view.findViewById(R.id.tv_txt_product_tot_sale);
            tv_txt_product_tot_return = (TextView) view.findViewById(R.id.tv_txt_product_tot_return);
            lv = (ListView) view.findViewById(R.id.primary_secondary_on_app_list_2);
        }
    }

}




/*
//http://sab99r.com/blog/recyclerview-endless-load-more/

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<POJO_User_S> studentList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading, isMoreDataAvailable = true;
    private OnLoadMoreListener onLoadMoreListener;


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public RecyclerAdapter2(List<POJO_User_S> students, RecyclerView recyclerView) {
        studentList = students;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (isMoreDataAvailable && onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return studentList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
            vh = new StudentViewHolder(v);

        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            vh = new ProgressViewHolder(v);

        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {
            POJO_User_S singleStudent = (POJO_User_S) studentList.get(position);

            ((StudentViewHolder) holder).tvName.setText(singleStudent.getName());

            ((StudentViewHolder) holder).tvEmailId.setText(singleStudent.getFull_name());

            ((StudentViewHolder) holder).student = singleStudent;

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return studentList == null ? 0 : studentList.size();
        //return studentList.size();
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmailId;

        public POJO_User_S student;

        public StudentViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvEmailId = (TextView) v.findViewById(R.id.tvEmailId);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "OnClick :" + student.getName() + " \n " + student.getFull_name(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


 */