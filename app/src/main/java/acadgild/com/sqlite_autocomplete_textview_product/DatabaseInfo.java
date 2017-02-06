package acadgild.com.sqlite_autocomplete_textview_product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by user on 2/2/2017.
 */

public class DatabaseInfo
{

Context context;
    ProductDataBase product;
    public DatabaseInfo(Context context){
        this.context=context;
        product=new ProductDataBase(context);
    }

    // Method to insert data into table

    public long insertDetails(String value,String sno){
        SQLiteDatabase db_insert=product.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ProductDataBase.PRODUCT_NAME,value);
        values.put(ProductDataBase.S_NO,sno);
        // return null map
        /*
        * Hashmap<String,String> map = new HashMap<>();
        * map.put("",value1);
        * map.put("",value2)
        * */

        long id=db_insert.insert(ProductDataBase.TABLE_NAME,null,values);// return long
        return id;
    }

  public String SearchDetails( )
    {

        SQLiteDatabase db_search=product.getWritableDatabase();

        String[] columns={ProductDataBase.PRODUCT_NAME};
      Cursor cursor= db_search.query(ProductDataBase.TABLE_NAME,columns,null,null,null,null,null);
        //Cursor cr=db_search.rawQuery(querry,null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext()){
            int column=cursor.getColumnIndex(ProductDataBase.PRODUCT_NAME);
            String product_name=cursor.getColumnName(column);
            buffer.append(product_name+"\n");
        }
        return buffer.toString();
    }

    class ProductDataBase extends SQLiteOpenHelper{

        private static final String DATABASE_NAME="ProductDataBase";
        private static final String TABLE_NAME="AllProduct";
        private static final String S_NO="_id";
        private static final String PRODUCT_NAME="P_Name";
        private static final int DATABASE_VERSION=6;
        Context context;
        public ProductDataBase(Context context) {

            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context=context;
            Toast.makeText(context,"Constructor Called",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL("CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY_KEY,P_Name VARCHAR(225));");
                Toast.makeText(context, "on Create called", Toast.LENGTH_SHORT).show();
            }
            catch(SQLException e){
                Toast.makeText(context," "+e,Toast.LENGTH_LONG).show();
            }
            db.close();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
            Toast.makeText(context, "On UpGrade is called", Toast.LENGTH_SHORT).show();
        }
    }
}
