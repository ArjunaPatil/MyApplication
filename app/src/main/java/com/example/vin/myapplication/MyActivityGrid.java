package com.example.vin.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MyActivityGrid extends Activity implements AbsListView.OnScrollListener {
    List products;
    GridView gvProducts = null;
    ProductListAdapterWithCache adapterProducts;


    private boolean lvBusy = false;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_grid);

            // populate data
            products = new ArrayList();
            products.add(new Product("Orange", "http://farm5.staticflickr.com/4142/4787427683_3672f1db9a_s.jpg"));
            products.add(new Product("Apple", "http://farm4.staticflickr.com/3139/2780642603_8d2c90e364_s.jpg"));
            products.add(new Product("Pineapple", "http://farm2.staticflickr.com/1008/1420343003_13eeb0f9f3_s.jpg"));
            products.add(new Product("Appricot", "http://farm5.staticflickr.com/4118/4784687474_0eca8226b0_z.jpg"));

            //
            gvProducts = (GridView) findViewById(R.id.grid_products);
            adapterProducts = new ProductListAdapterWithCache(this, products);
            gvProducts.setAdapter(adapterProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        try {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    lvBusy = false;
                    adapterProducts.notifyDataSetChanged();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    lvBusy = true;
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    lvBusy = true;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isLvBusy() {
        return lvBusy;
    }




    /*
    private void getDataFromServer(){
        String s_json =
                "[" +
                        "{\"id\":\"1\",\"title\":\"Orange\",\"img_url\":\"http://farm5.staticflickr.com/4142/4787427683_3672f1db9a_s.jpg\"}," +
                        "{\"id\":\"2\",\"title\":\"Apple\",\"img_url\":\"http://farm4.staticflickr.com/3139/2780642603_8d2c90e364_s.jpg\"}," +
                        "{\"id\":\"3\",\"title\":\"Pineapple\",\"img_url\":\"http://farm2.staticflickr.com/1008/1420343003_13eeb0f9f3_s.jpg\"}" +
                        "]";
        // load list from URL
        //ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        //JSONArray rows = myutils.getJSONArrayfromURL("http://YOUR_SITE.com/some_script.php", postParameters);
        try{
            for(int i=0;i < rows.length();i++){
                JSONObject e = rows.getJSONObject(i);
                products.add(new Product(e.optString("name"), e.optString("img_url")));
            }
        }
        catch (JSONException e) {
            //Log.e("Error", "json "+e.toString());
        }
    }
    */

}