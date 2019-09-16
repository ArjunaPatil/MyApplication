package com.example.vin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/*
read more: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
 */
public class fragment_upload_images_list_adapter extends ArrayAdapter<POJO_Upload_Files> {
    private Context mContext;
    List<POJO_Upload_Files> mylist;


    public fragment_upload_images_list_adapter(Context _context, List<POJO_Upload_Files> _mylist) {
        super(_context, R.layout.list_item1, _mylist);
        mContext = _context;
        this.mylist = _mylist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.grid_item, parent, false);//grid_item
            }
            // Product object
            POJO_Upload_Files product = getItem(position);

            //
            TextView mon_year = (TextView) v.findViewById(R.id.mon_year);
            if (product.getDate_flag() == 1) {
                //mon_year.setText("aaaaaa");
                mon_year.setText(product.getCreation().toString().substring(0, 10));
                mon_year.setVisibility(View.VISIBLE);
            } else {
                mon_year.setVisibility(View.GONE);
            }


            ImageButton download = (ImageButton) v.findViewById(R.id.download);

            if (product.getIs_present_in_off_line() == 1) {
                download.setVisibility(View.GONE);
            } else {
                download.setVisibility(View.VISIBLE);
            }

            TextView txtTitle = (TextView) v.findViewById(R.id.title);
            txtTitle.setText(product.getFile_name());
            txtTitle.setVisibility(View.GONE);

            // show image
            ImageView img = (ImageView) v.findViewById(R.id.image);

            // download image
            ImageDownloader imageDownloader = new ImageDownloader(getContext(),"000");
            imageDownloader.download("http://139.59.63.181" + product.getFile_url(), img);
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


    static class POJO_Upload_FilesViewHolder {
        public ImageView img;
        public TextView title;
        public Context Context;

        POJO_Upload_FilesViewHolder(Context Context) {
            this.Context = Context;
        }

        void populate(POJO_Upload_Files p) {
            title.setText(p.getFile_name());

            //
            ImageDownloader imageDownloader = new ImageDownloader(Context,"000");
            imageDownloader.download("http://139.59.63.181" + p.getFile_url(), img);
        }

        void populate(POJO_Upload_Files p, boolean isBusy) {
            title.setText(p.getFile_name());

            if (!isBusy) {
                // download from internet
                ImageDownloader imageDownloader = new ImageDownloader(Context,"000");
                imageDownloader.download("http://139.59.63.181" + p.getFile_url(), img);
            } else {
                // set default image
                img.setImageResource(R.drawable.spinner);
            }
        }
    }

}