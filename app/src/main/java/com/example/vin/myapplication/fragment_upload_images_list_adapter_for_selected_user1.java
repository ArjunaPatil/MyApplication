package com.example.vin.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class fragment_upload_images_list_adapter_for_selected_user1 extends ArrayAdapter<POJO_Upload_Files_For_Selected_User> {
    private Context mContext;
    List<POJO_Upload_Files_For_Selected_User> mylist;
    private ArrayList<HashMap<String, String>> data;

    /*BaseAdapter*/
    public fragment_upload_images_list_adapter_for_selected_user1(Context _context, List<POJO_Upload_Files_For_Selected_User> _mylist) {
        super(_context, R.layout.list_item1, _mylist);
        mContext = _context;
        mylist = _mylist;
    }

   /* public int getCount() {
        return mylist.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }*/

    public View getView(int position, View convertView, ViewGroup parent) {
        SingleAlbumViewHolder holder = null;
        POJO_Upload_Files_For_Selected_User product = getItem(position);
        if (convertView == null) {
            holder = new SingleAlbumViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item2, parent, false);

            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);

            /*------------------*/
            holder.gallery_count = (TextView) convertView.findViewById(R.id.gallery_count);
            holder.gallery_title = (TextView) convertView.findViewById(R.id.gallery_title);
            /*holder.gallery_title1 = (TextView) convertView.findViewById(R.id.gallery_title1);*/
            /*------------------*/

            convertView.setTag(holder);
        } else {
            holder = (SingleAlbumViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);

        /*------------------*/
        holder.gallery_count.setId(position);
        holder.gallery_title.setId(position);
        /*holder.gallery_title1.setId(position);*/
         /*------------------*/

        /*HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);*/
        try {

            if (check_network() == true) {
                holder.gallery_count.setText("");
                holder.gallery_title.setText(product.getCreation().substring(0, 10));
                /*holder.gallery_title1.setText(product.getCreation().substring(0, 10));*/
               /* holder.gallery_count.setText("Test Image Title");*/

                ImageDownloader imageDownloader = new ImageDownloader(getContext(), "000");
                imageDownloader.download("http://139.59.63.181" + product.getFile_url(), holder.galleryImage);

            } else {
                Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                holder.gallery_count.setText("");
                // holder.gallery_count.setText("INTERNET LOST");
                holder.gallery_title.setText("INTERNET LOST");
                holder.galleryImage.setImageResource(R.drawable.no_connection_512);//R.drawable.blank
            }
           /*
            Glide.with(mContext)
                    .load("http://139.59.63.181" + product.getFile_url()) // Uri of the picture
                    .into(holder.galleryImage);*/


        } catch (Exception e) {
        }
        return convertView;
    }

    public boolean check_network() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);//getSystemService
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }


}


class SingleAlbumViewHolder {
    ImageView galleryImage;
    /*----------*/
    TextView gallery_count, gallery_title, gallery_title1;
/*----------*/

}
