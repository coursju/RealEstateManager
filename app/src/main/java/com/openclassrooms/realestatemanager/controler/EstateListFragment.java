package com.openclassrooms.realestatemanager.controler;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.EstateRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.controler.dummy.DummyContent;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.FromCursorToEstateList;
import com.openclassrooms.realestatemanager.utils.GetEstateListCallback;
import com.openclassrooms.realestatemanager.utils.ImagesSQLiteConverter;

import java.util.List;


public class EstateListFragment extends Fragment {

    private static final String TAG = "EstateListFragment";

    private ContentResolver mContentResolver;
    private GetEstateListCallback mGetEstateListCallback;
//    private List<Estate> mEstateList;
    private RecyclerView recyclerView;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureGetEstateListCallback();
        mContentResolver = getContext().getContentResolver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.estate_fragment_item_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        new FromCursorToEstateList(mContentResolver, mGetEstateListCallback).execute();

        return view;
    }

    private void configureGetEstateListCallback(){
        this.mGetEstateListCallback = new GetEstateListCallback() {
            @Override
            public void updateEstateList(List<Estate> estateList) {
//                mEstateList = estateList;
                Log.d(TAG, "into configureGetEstateListCallback ");
                recyclerView.setAdapter(new EstateRecyclerViewAdapter(estateList));

            }
        };
    }
}