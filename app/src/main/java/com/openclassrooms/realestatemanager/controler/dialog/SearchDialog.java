package com.openclassrooms.realestatemanager.controler.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.EstateListFragment;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.provider.EstateContentProvider;
import com.openclassrooms.realestatemanager.utils.ChipgroupUtils;
import com.openclassrooms.realestatemanager.viewmodel.EstateListFilteredViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchDialog extends DialogFragment {

    private static final String TAG = "SearchDialog";
    private EstateListFragment estateListFragment;
    private Boolean addQueryElement;

    View view;
    TextInputEditText searchTypeTxt;
    AutoCompleteTextView searchStatutTxt;
    TextInputEditText searchAgentTxt;
    TextInputEditText searchCityTxt;
    TextInputEditText searchAddressTxt;
    TextInputEditText searchPointOfInterestTxt;
    TextInputEditText searchPublicationDateTxt;
    TextInputEditText searchSoldDateTxt;

    ChipGroup searchPointOfInterestChipGroup;
    RangeSlider searchPriceSlider;
    RangeSlider searchSurfaceSlider;
    RangeSlider searchNumberOfRoomsSlider;

    public SearchDialog(EstateListFragment estateListFragment){
        this.estateListFragment = estateListFragment;
    }
    public SearchDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_search, null);

        bindView();
        configurePointOfKeyListener();
        configureSearchDateListener();
        builder.setView(view)
                .setPositiveButton(R.string.dialog_search_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getEstateWithPhotosListFromCursor();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SearchDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void bindView(){
        searchTypeTxt = view.findViewById(R.id.search_type_text);
        searchStatutTxt = view.findViewById(R.id.search_statut_text);
        searchAgentTxt = view.findViewById(R.id.search_agent_text);
        searchCityTxt = view.findViewById(R.id.search_city_text);
        searchAddressTxt = view.findViewById(R.id.search_address_text);
        searchPointOfInterestTxt = view.findViewById(R.id.search_point_interest_text);
        searchPublicationDateTxt = view.findViewById(R.id.search_publication_date);
        searchSoldDateTxt = view.findViewById(R.id.search_sold_date);

        searchPointOfInterestChipGroup = view.findViewById(R.id.search_point_interest_chipgroup);
        searchPriceSlider = view.findViewById(R.id.search_price_slider);
        searchSurfaceSlider = view.findViewById(R.id.search_surface_slider);
        searchNumberOfRoomsSlider = view.findViewById(R.id.search_rooms_slider);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.search_statut_array, android.R.layout.simple_dropdown_item_1line);
        searchStatutTxt.setAdapter(adapter);
    }

    public String getSQLQueryString(){
        String query = "";
        addQueryElement = false;

        if (!searchTypeTxt.getText().toString().equals("")){
            query += sQLQueryIfTest();
            query += "type = \"" +searchTypeTxt.getText().toString()+"\"";
        }
        if (!searchStatutTxt.getText().toString().equals("")){
            query += sQLQueryIfTest();
            if (searchStatutTxt.getText().toString().equals("On sale")){
                query += " sold = 0";
            } else {
                query += " sold = 1";
            }
        }
        if (!searchAgentTxt.getText().toString().equals("")){
            query += sQLQueryIfTest();
            query += " agentName = \""+searchAgentTxt.getText().toString()+"\"";
        }
        if (!searchCityTxt.getText().toString().equals("")){
            query += sQLQueryIfTest();
            query += " city = \""+searchCityTxt.getText().toString()+"\"";
        }
        if (!searchAddressTxt.getText().toString().equals("")){
            query += sQLQueryIfTest();
            query += " address like \"%"+searchAddressTxt.getText().toString()+"%\"";
        }
        if (!searchPublicationDateTxt.getText().toString().equals("")){
            query += sQLQueryIfTest();
            query += "publicationDate = \""+searchPublicationDateTxt.getText().toString()+"\"";
        }
        if (!searchSoldDateTxt.getText().toString().equals("")){
            query += sQLQueryIfTest();
            query += "soldDate = \""+searchSoldDateTxt.getText().toString()+"\"";
        }
        if (searchPriceSlider.getValues().get(0) != searchPriceSlider.getValueFrom()
        || searchPriceSlider.getValues().get(1) != searchPriceSlider.getValueTo()){
            query += sQLQueryIfTest();
            query += "price >= "+String.valueOf(searchPriceSlider.getValues().get(0))+" AND price <= "+String.valueOf(searchPriceSlider.getValues().get(1));
        }
        if (searchSurfaceSlider.getValues().get(0) != searchSurfaceSlider.getValueFrom()
                || searchSurfaceSlider.getValues().get(1) != searchSurfaceSlider.getValueTo()){
            query += sQLQueryIfTest();
            query += "surface >= "+String.valueOf(searchSurfaceSlider.getValues().get(0))+" AND surface <= "+String.valueOf(searchSurfaceSlider.getValues().get(1));
        }
        if (searchNumberOfRoomsSlider.getValues().get(0) != searchNumberOfRoomsSlider.getValueFrom()
                || searchNumberOfRoomsSlider.getValues().get(1) != searchNumberOfRoomsSlider.getValueTo()){
            query += sQLQueryIfTest();
            query += "roomNumber >= "+String.valueOf(searchNumberOfRoomsSlider.getValues().get(0))+" AND roomNumber <= "+String.valueOf(searchNumberOfRoomsSlider.getValues().get(1));
        }
        if (!ChipgroupUtils.saveSelections(searchPointOfInterestChipGroup).equals("")){
            String[] strTab = ChipgroupUtils.saveSelections(searchPointOfInterestChipGroup).split(",");
            Toast.makeText(getContext(), String.valueOf(strTab[0]), Toast.LENGTH_SHORT).show();
            for (String str : strTab){
                query += sQLQueryIfTest();
                query += "interestingSpots like \"%"+str+"%\"";
            }
        }

        return "SELECT * FROM Estate "+query;
    }

    public void configurePointOfKeyListener(){
        searchPointOfInterestTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    ChipgroupUtils.addNewChip(getActivity(), searchPointOfInterestChipGroup, searchPointOfInterestTxt.getText().toString());
                    searchPointOfInterestTxt.setText("");
                    return  true;
                }else {
                    return false;
                }
            }
        });
    }

    public void configureSearchDateListener(){
        searchPublicationDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment publicationDateDialog = new SearchDatePickerFragment(searchPublicationDateTxt);
                assert getFragmentManager() != null;
                publicationDateDialog.show(getFragmentManager(), "publicationDatePicker");
            }
        });
        searchSoldDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment publicationDateDialog = new SearchDatePickerFragment(searchSoldDateTxt);
                assert getFragmentManager() != null;
                publicationDateDialog.show(getFragmentManager(), "soldDatePicker");
            }
        });
    }

    public static class SearchDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        TextInputEditText dateText;
        public SearchDatePickerFragment(TextInputEditText dateText){
            this.dateText = dateText;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String sYear = String.valueOf(year);
            String sMonth = String.valueOf(month + 1);
            String sDayOfMonth = String.valueOf(dayOfMonth);

            if (dayOfMonth < 10) sDayOfMonth = "0"+sDayOfMonth;
            if (month < 10) sMonth = "0"+sMonth;
            dateText.setText(sDayOfMonth+"/"+sMonth+"/"+sYear);
        }
    }

    private String sQLQueryIfTest(){
        if (addQueryElement){
            return " AND ";
        }else{
            addQueryElement = true;
            return "WHERE ";
        }
    }

    public void getEstateWithPhotosListFromCursor(){
        String sQLQuery = getSQLQueryString();
        Cursor cursor = getContext().getContentResolver().query(EstateContentProvider.URI_ITEM, null, sQLQuery, null, null);
        List<EstateWithPhotos> estateWithPhotosList = new ArrayList<>();
        cursor.moveToFirst();
        int cursorSize = cursor.getCount();

        Log.i(TAG, "Cursor size : "+String.valueOf(cursorSize));

        for (int i = 0; i < cursorSize; i++){
            Estate iterEstate = new Estate(
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getInt(9) == 1,
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12)
            );
            long iDCreator = cursor.getLong(0);
            iterEstate.setEstateId(iDCreator);

            List<Photo> photoList = new ArrayList<>();
            String query = "SELECT * FROM Photo WHERE estateCreatorId = "+iDCreator;
            Cursor cursor2 = getContext().getContentResolver().query(EstateContentProvider.URI_ITEM, null, query, null, null);
            cursor2.moveToFirst();
            int cursor2Size = cursor2.getCount();
            Log.i(TAG, "cursor2size (photos) : "+cursor2Size);
            for (int j = 0 ; j < cursor2Size; j++){
                Photo iterPhoto = new Photo(
                        cursor2.getLong(1),
                        cursor2.getBlob(2),
                        cursor2.getString(3)
                );
                iterPhoto.setPhotoId(cursor2.getLong(0));
                photoList.add(iterPhoto);
                cursor2.moveToNext();
            }
            cursor2.close();
            estateWithPhotosList.add(new EstateWithPhotos(iterEstate, photoList));
            cursor.moveToNext();
        }
        cursor.close();
        Log.i(TAG,"cursor passed!");
        new ViewModelProvider(requireActivity()).get(EstateListFilteredViewModel.class) .setFilteredList(estateWithPhotosList);
    }
}
