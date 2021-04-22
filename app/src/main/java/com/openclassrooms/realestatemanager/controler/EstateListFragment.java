package com.openclassrooms.realestatemanager.controler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.EstateRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.utils.GetEstateListCallback;
import com.openclassrooms.realestatemanager.viewmodel.EstateListViewModel;


public class EstateListFragment extends Fragment {

    private static final String TAG = "EstateListFragment";

    private GetEstateListCallback mGetEstateListCallback;
    private RecyclerView recyclerView;
    private Context context;
    private FloatingActionButton estatesListFloatingBtn;
    private MainActivity mMainActivity;
    private EstateListViewModel estateListViewModel;

    public EstateListFragment(MainActivity mainActivity){
        mMainActivity = mainActivity;
    }

    public EstateListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        configureGetEstateListCallback();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.estate_fragment_item_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        context = view.getContext();
        estatesListFloatingBtn = view.findViewById(R.id.estatesListFloatingBtn);
        configureListenerFloatingBtn();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        reloadEstateList();
        configureViewModel();

        return view;
    }

//    private void configureGetEstateListCallback(){
//        this.mGetEstateListCallback = new GetEstateListCallback() {
//            @Override
//            public void updateEstateList(List<Estate> estateList) {
////                mEstateList = estateList;
//                Log.d(TAG, "into configureGetEstateListCallback ");
//                recyclerView.setAdapter(new EstateRecyclerViewAdapter(estateList, mMainActivity));
//
//            }
//        };
//    }

    private void configureListenerFloatingBtn(){
        this.estatesListFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateEstateActivity.class);
                startActivity(intent);
            }
        });
    }

//    public void reloadEstateList(){
//        recyclerView.setAdapter(new EstateRecyclerViewAdapter(EstateList.getEstateList(), mMainActivity));
//    }

    public void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateListViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateListViewModel.class);
        estateListViewModel.getEstateWithPhotos().observe(this, estates -> {
            recyclerView.setAdapter(new EstateRecyclerViewAdapter(estates, mMainActivity));
            Log.i(TAG,"EstateViewModel observer ");
        });
    }
}