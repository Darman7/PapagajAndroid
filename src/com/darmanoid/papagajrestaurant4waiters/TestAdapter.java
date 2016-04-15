package com.darmanoid.papagajrestaurant4waiters;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestAdapter
{
    protected static final String TAG = "DataAdapter";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }
    /*
     * U nastavku pravim upite nad bazom
     */
     public Cursor getImenaKorisnika()
     {
         try
         {
             String sql ="SELECT * FROM korisnik";

              Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException)
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     public Cursor getStoloviPoRegionima(String region)
     {
         try
         {
             String sql ="SELECT * FROM sto WHERE region="+region+" ";

              Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException)
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     public Cursor getImeStolaPoIDu(String id)
     {
         try
         {
             String sql ="SELECT * FROM sto WHERE _id="+id;

              Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException)
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     /**
      * 
      * @Vracem grupe iz baze
      */
     public Cursor getGrupe()
     {
         try
         {
             String sql ="SELECT * FROM grupa WHERE aktivna=1";

              Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException)
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     public ArrayList<String> getAllGrupe()
	   {
	      ArrayList<String> array_list = new ArrayList<String>();
	      
	      //hp = new HashMap();
	      
	      Cursor res = getGrupe();
	      res.moveToFirst();
	      
	      while(res.isAfterLast() == false){
	         array_list.add(res.getString(1));
	         res.moveToNext();
	      }
	   return array_list;
	   }
     /*
      * 
      * Vracem jela iz odabrane grupe
      */
     public Cursor getJela(int grupaId)
     {
         try
         {
             String sql ="SELECT * FROM artikal WHERE grupa_id="+grupaId;

              Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException)
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     public ArrayList<String> getAllJela(int grupa_id)
	   {
	      ArrayList<String> array_list = new ArrayList<String>();
	      
	      //hp = new HashMap();
	      
	      Cursor res = getJela(grupa_id);
	      res.moveToFirst();
	      
	      while(res.isAfterLast() == false){
	         array_list.add(res.getString(res.getColumnIndex("naziv")));
	         res.moveToNext();
	      }
	   return array_list;
	   }
     
   
     //Iskopaj mi torke regiona koji su aktivni
     public Cursor getRegioni()
     {
         try
         {
             String sql ="SELECT * FROM region WHERE aktivan=1";

              Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException)
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     
     public Cursor getImeGrupePoIDu(int idGrupe)
     {
         try
         {
             String sql ="SELECT * FROM grupa WHERE _id="+idGrupe;

              Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException)
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
}