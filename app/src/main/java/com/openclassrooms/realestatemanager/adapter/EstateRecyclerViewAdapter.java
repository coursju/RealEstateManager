package com.openclassrooms.realestatemanager.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.dummy.DummyContent.DummyItem;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;

import java.util.List;

public class EstateRecyclerViewAdapter extends RecyclerView.Adapter<EstateRecyclerViewAdapter.ViewHolder> {

    private final List<Estate> mValues;

    public EstateRecyclerViewAdapter(List<Estate> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estate_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Bitmap bitmap = ImagesSQLiteConverter.toBitmapListFromJson(mValues.get(position).getPhotosString()).get(0);
        holder.mListImage.setImageBitmap(bitmap);
        holder.mListTypeText.setText(mValues.get(position).getType());
        holder.mListCityText.setText(mValues.get(position).getCity());
        holder.mListPriceText.setText(String.valueOf(mValues.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final LinearLayout mListVerticalLayout;
        public final ImageView mListImage;
        public final TextView mListTypeText;
        public final TextView mListCityText;
        public final TextView mListPriceText;

        public ViewHolder(View view) {
            super(view);
//            mListVerticalLayout = view.findViewById(R.id.list_vertical_layout);
            mListImage = view.findViewById(R.id.list_image);
            mListTypeText = (TextView) view.findViewById(R.id.list_type_text);
            mListCityText = (TextView) view.findViewById(R.id.list_city_text);
            mListPriceText = (TextView) view.findViewById(R.id.list_price_text);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}