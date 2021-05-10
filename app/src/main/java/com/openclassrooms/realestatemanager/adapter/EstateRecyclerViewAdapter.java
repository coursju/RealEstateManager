package com.openclassrooms.realestatemanager.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.MainActivity;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.util.List;

public class EstateRecyclerViewAdapter extends RecyclerView.Adapter<EstateRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "EstateRecyclerViewAdapt";
    private final List<EstateWithPhotos> mValues;
    private MainActivity mActivity;
    private Integer selected;
    private EstateViewModel estateViewModel;

//    public EstateRecyclerViewAdapter(List<EstateWithPhotos> items, Activity activity, Integer itemSelected) {
//        mValues = items;
//        mActivity = (MainActivity) activity;
//        this.selected = itemSelected;
//    }

    public EstateRecyclerViewAdapter(List<EstateWithPhotos> items, Activity activity, Integer itemSelected, EstateViewModel estateViewModel) {
        mValues = items;
        mActivity = (MainActivity) activity;
        this.selected = itemSelected;
        this.estateViewModel = estateViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estate_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Boolean isDollard = estateViewModel.getIsDollard().getValue();
        String str =
                (isDollard)?
                        "USD "+String.valueOf(mValues.get(position).getEstate().getPrice()) :
                        "EUR "+String.valueOf(Utils.convertDollarToEuro(mValues.get(position).getEstate().getPrice()));
        if (mValues.get(position).getPhotoList() != null) {
            if (mValues.get(position).getPhotoList().size() != 0) {
                Bitmap bitmap = mValues.get(position).getPhotoList().get(0).getPhoto();
                holder.mListImage.setImageBitmap(bitmap);
            }
        }
        holder.mListTypeText.setText(mValues.get(position).getEstate().getType());
        holder.mListCityText.setText(mValues.get(position).getEstate().getCity());
        holder.mListPriceText.setText(str);

        if (position == selected && selected != null){
            holder.mListLayout.setBackgroundColor(mActivity.getResources().getColor(R.color.item_list_selected));
        }
        Log.i(TAG, String.valueOf(mActivity.getResources().getConfiguration().screenWidthDp));

        if (mActivity.getResources().getConfiguration().screenWidthDp > 900){
            mActivity.showFragmentDetails(selected);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout mListLayout;
        public final ImageView mListImage;
        public final TextView mListTypeText;
        public final TextView mListCityText;
        public final TextView mListPriceText;

        public ViewHolder(View view) {
            super(view);
            mListLayout = view.findViewById(R.id.list_layout);
            mListImage = view.findViewById(R.id.list_image);
            mListTypeText = (TextView) view.findViewById(R.id.list_type_text);
            mListCityText = (TextView) view.findViewById(R.id.list_city_text);
            mListPriceText = (TextView) view.findViewById(R.id.list_price_text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(EstateRecyclerViewAdapter.TAG,"item clicked!!");
                    mActivity.showFragmentDetails(getAdapterPosition());
                    new ViewModelProvider(mActivity).get(EstateViewModel.class).setSelected(getAdapterPosition());
                }
            });
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}