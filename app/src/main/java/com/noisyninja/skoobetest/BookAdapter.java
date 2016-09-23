package com.noisyninja.skoobetest;

import android.content.Context;
import android.content.UriMatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noisyninja.skoobetest.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudiptadutta on 23/09/16.
 */
public class BookAdapter extends BaseAdapter {

    private List<Item> mItems;
    private LayoutInflater mInflater;

    public BookAdapter(Context context, List<Item> data) {
        this.mItems = data;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.cell_book, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(mItems.get(position).getTitle());
        //holder.author.setText(mItems.get(position).getAuthor());
        //String description = Utils.formatDescription(mItems.get(position).getDescription());
        Utils.setHtmlText(holder.description, mItems.get(position).getDescription());
        Utils.glideLoad(convertView.getContext(), holder.imageView, mItems.get(position).getThumbnail());
        Utils.makeAnimation(holder.imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openURL(view.getContext(), mItems.get(position).getLink());
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView author;
        public TextView description;
    }
}

