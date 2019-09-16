package com.example.vin.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;


/*
read more: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
 */
public class PhotoAdapter extends ArrayAdapter<File> {
    private Context mContext;
    List<File> mylist;


    public PhotoAdapter(Context _context, List<File> _mylist) {
        super(_context, R.layout.list_item1, _mylist);
        mContext = _context;
        this.mylist = _mylist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.grid_item1, parent, false);//grid_item
            }
            // Product object
            File product = getItem(position);

            //
            /*TextView mon_year = (TextView) v.findViewById(R.id.mon_year);
            mon_year.setText("aaaaaa");*/

            TextView txtTitle = (TextView) v.findViewById(R.id.title);
            //txtTitle.setText(product.getFile_name());
            txtTitle.setVisibility(View.GONE);

            // show image
            ImageView img = (ImageView) v.findViewById(R.id.image);


            // download image
            //ImageDownloader imageDownloader = new ImageDownloader(getContext());
            //imageDownloader.download("http://139.59.63.181" + product.getFile_url(), img);

            Bitmap bmp = BitmapFactory.decodeFile(product.toString());
            img.setImageBitmap(bmp);
            //img.setTag("Hi Img");

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }

        /*POJO_Upload_Files pojo_upload = getItem(position);

        POJO_Upload_FilesViewHolder holder;

        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            //convertView = vi.inflate(R.layout.list_item, parent, false);
            convertView = vi.inflate(R.layout.grid_item, parent, false);

            //
            holder = new POJO_Upload_FilesViewHolder(mContext);
            holder.img = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.title);

            //
            convertView.setTag(holder);
        } else {
            holder = (POJO_Upload_FilesViewHolder) convertView.getTag();
        }


        //
        //holder.populate(product, ((MyActivity)mContext).isLvBusy());
        holder.populate(pojo_upload, ((MyActivityGrid) mContext).isLvBusy());

        //
        return convertView;*/
    }


}