//package com.openclassrooms.realestatemanager.utils;
//
//import android.content.ContentResolver;
//import android.database.Cursor;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.openclassrooms.realestatemanager.model.Estate;
//import com.openclassrooms.realestatemanager.provider.EstateContentProvider;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FromCursorToEstateList extends AsyncTask <Void, Void, List<Estate>> {
//
//    private static final String TAG = "FromCursorToEstateList";
//    private WeakReference<ContentResolver> mContentResolverWeakReference;
//    private WeakReference<GetEstateListCallback> mGetEstateListCallbackWeakReference;
//
//
//    public FromCursorToEstateList(ContentResolver contentResolver, GetEstateListCallback getEstateListCallback) {
//        mContentResolverWeakReference = new WeakReference<>(contentResolver);
//        mGetEstateListCallbackWeakReference = new WeakReference<>(getEstateListCallback);
//    }
//
//    @Override
//    protected List<Estate> doInBackground(Void... voids) {
//        List<Estate> mEstateList = new ArrayList<>();
//        final Cursor cursor = mContentResolverWeakReference.get().query(EstateContentProvider.URI_ITEM, null, null, null, null);
//        cursor.moveToFirst();
//        int cursorSize = cursor.getCount();
//        Log.d(TAG,"cursor size :"+String.valueOf(cursorSize));
//
//        for (int i = 0; i< cursorSize; i++){
//            Estate iterEstate = new Estate(
//                    cursor.getString(1),
//                    cursor.getInt(2),
//                    cursor.getInt(3),
//                    cursor.getInt(4),
//                    cursor.getString(5),
//                    cursor.getString(6),
//                    cursor.getString(7),
//                    cursor.getString(8),
//                    (cursor.getInt(9)==1)? true : false,
//                    cursor.getString(10),
//                    cursor.getString(11),
//                    cursor.getString(12)
//            );
//            mEstateList.add(iterEstate);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        Log.d(TAG,"cursor passed!");
//
//        return mEstateList;
//    }
//
//    @Override
//    protected void onPostExecute(List<Estate> estates) {
//        mGetEstateListCallbackWeakReference.get().updateEstateList(estates);
//    }
//}
