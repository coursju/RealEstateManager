package com.openclassrooms.realestatemanager.controler;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapter.EstateRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.controler.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class EstateListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EstateListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EstateListFragment newInstance(int columnCount) {
        EstateListFragment fragment = new EstateListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.estate_fragment_item_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                    Context context = view.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new EstateRecyclerViewAdapter(DummyContent.ITEMS));

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new EstateRecyclerViewAdapter(DummyContent.ITEMS));
//        }
        return view;
    }
}