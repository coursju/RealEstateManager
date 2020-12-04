package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    private RealEstateDatabase mDatabase;
//
//
//    @Before
//    public void initDb() throws Exception {
//        this.mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
//                RealEstateDatabase.class)
//                .allowMainThreadQueries()
//                .build();
//    }
//
//
//    @Test
//    public void databaseSizeTest(){
//        Estate estate = new Estate("Loft",10,20,2,"blabla","ad","spot",false,"","billy");
//        mDatabase.mEstateDao().insertEstate(estate);
//        List<Estate> estateList = mDatabase.mEstateDao().getEstates().getValue();
//        assertTrue(!estateList.isEmpty());
//    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}