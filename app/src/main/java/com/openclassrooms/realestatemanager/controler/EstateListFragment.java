package com.openclassrooms.realestatemanager.controler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.EstateRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.utils.GetEstateListCallback;
import com.openclassrooms.realestatemanager.viewmodel.EstateListFilteredViewModel;
import com.openclassrooms.realestatemanager.viewmodel.EstateListViewModel;
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel;

import java.util.ArrayList;
import java.util.List;


public class EstateListFragment extends Fragment {

    private static final String TAG = "EstateListFragment";

    private RecyclerView recyclerView;
    private Context context;
    private FloatingActionButton estatesListFloatingBtn;
    private Button estateListClearFilter;
    private MainActivity mMainActivity;
    private EstateListViewModel estateListViewModel;
    private EstateListFilteredViewModel estateListFilteredViewModel;
    private EstateViewModel estateViewModel;
    private List<EstateWithPhotos> estateList4Recycler;

    public EstateListFragment(MainActivity mainActivity){
        mMainActivity = mainActivity;
    }

    public EstateListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.estate_fragment_item_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        context = view.getContext();
        estatesListFloatingBtn = view.findViewById(R.id.estatesListFloatingBtn);
        estateListClearFilter = view.findViewById(R.id.estate_list_clear_filter);
        configureListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        configureViewModel();
        return view;
    }

    private void configureListener(){
        this.estatesListFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateEstateActivity.class);
                startActivity(intent);
            }
        });
        this.estateListClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
            }
        });
    }

    public void configureViewModel(){
        this.estateViewModel = new ViewModelProvider(requireActivity()).get(EstateViewModel.class);
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateListViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateListViewModel.class);
        this.estateListFilteredViewModel = new ViewModelProvider(requireActivity()).get(EstateListFilteredViewModel.class);
        this.estateListFilteredViewModel.getFiltered().observe(this, isFiltered ->{
            if (isFiltered){
                estateListFilteredViewModel.getFilteredList().observe(this, filteredList ->{
                    estatesListFloatingBtn.setVisibility(View.GONE);
                    estateListClearFilter.setVisibility(View.VISIBLE);
                    estateList4Recycler = filteredList;
                    Integer i = (filteredList.isEmpty())? null : 0;
                    recyclerView.setAdapter(new EstateRecyclerViewAdapter(estateList4Recycler, mMainActivity, i));
                    Log.i(TAG,"estateListFilteredViewModel.getFilteredList().observe");
                    reloadList();

                });
            }else {
                estateListViewModel.getEstateWithPhotos().observe(this, estates -> {
                    estatesListFloatingBtn.setVisibility(View.VISIBLE);
                    estateListClearFilter.setVisibility(View.GONE);
                    estateList4Recycler = estates;
                    recyclerView.setAdapter(new EstateRecyclerViewAdapter(estateList4Recycler, mMainActivity, estateViewModel.getSelected().getValue()));
                    Log.i(TAG,"estateListViewModel.getEstateWithPhotos().observe");
                    reloadList();

                });
            }
        });
    }

    public void reloadList(){
        estateViewModel.getSelected().observe(this, selected ->{
            recyclerView.setAdapter(new EstateRecyclerViewAdapter(estateList4Recycler, mMainActivity, selected));
        });
    }

    public void clearFilter(){
        estateListFilteredViewModel.setFiltered(false);
        estateViewModel.setSelected(0);
        mMainActivity.configureFragment();
    }
}