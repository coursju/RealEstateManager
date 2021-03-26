package com.openclassrooms.realestatemanager.controler;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.EstateRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.utils.FromCursorToEstateList;
import com.openclassrooms.realestatemanager.utils.GetEstateListCallback;

import java.util.List;


public class EstateListFragment extends Fragment {

    private static final String TAG = "EstateListFragment";

    private ContentResolver mContentResolver;
    private GetEstateListCallback mGetEstateListCallback;
//    private List<Estate> mEstateList;
    private RecyclerView recyclerView;
    private Context context;
    private FloatingActionButton estatesListFloatingBtn;

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
        estatesListFloatingBtn = view.findViewById(R.id.estatesListFloatingBtn);
        configureListenerFloatingBtn();
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

    private void configureListenerFloatingBtn(){
        this.estatesListFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateEstateActivity.class);
                startActivity(intent);
            }
        });
    }
}