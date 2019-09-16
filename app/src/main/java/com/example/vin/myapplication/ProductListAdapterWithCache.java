package com.example.vin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/*
read more: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
 */
public class ProductListAdapterWithCache extends ArrayAdapter<Product> {
    private Context mContext;
    List<Product> mylist;

    public ProductListAdapterWithCache(Context _context, List<Product> _mylist) {
        super(_context, R.layout.list_item1, _mylist);

        mContext = _context;
        this.mylist = _mylist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        ProductViewHolder holder;

        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            //convertView = vi.inflate(R.layout.list_item, parent, false);
            convertView = vi.inflate(R.layout.grid_item, parent, false);

            //
            holder = new ProductViewHolder(mContext);
            holder.img = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.title);

            //
            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }


        //
        //holder.populate(product, ((MyActivity)mContext).isLvBusy());
        holder.populate(product, ((MyActivityGrid) mContext).isLvBusy());

        //
        return convertView;
    }


    static class ProductViewHolder {
        public ImageView img;
        public TextView title;
        public Context Context;

        ProductViewHolder(Context Context) {
            this.Context = Context;
        }

        void populate(Product p) {
            title.setText(p.title);

            //
            ImageDownloader imageDownloader = new ImageDownloader(Context,"000");
            imageDownloader.download(p.img_url, img);
        }

        void populate(Product p, boolean isBusy) {
            title.setText(p.title);

            if (!isBusy) {
                // download from internet
                ImageDownloader imageDownloader = new ImageDownloader(Context,"000");
                imageDownloader.download(p.img_url, img);
            } else {
                // set default image
                img.setImageResource(R.drawable.spinner);
            }
        }
    }

}