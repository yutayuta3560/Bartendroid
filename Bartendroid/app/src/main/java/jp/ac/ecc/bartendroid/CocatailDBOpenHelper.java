package jp.ac.ecc.bartendroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CocatailDBOpenHelper extends SQLiteOpenHelper{

    // コンストラクタ
    public CocatailDBOpenHelper(Context context){

        super( context, "caktailDB", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String caktail_table = "CREATE TABLE caktail ("
                            + "_id integer primary key autoincrement not null,"
                            + "caktail_name text not null unique,"
                            + "material1_id integer not null,"
                            + "material2_id integer not null,"
                            + "material3_id integer default 0,"
                            + "material4_id integer default 0,"
                            + "material5_id integer default 0,"
                            + "material6_id integer default 0,"
                            + "material7_id integer default 0,"
                            + "material8_id integer default 0,"
                            + "material9_id integer default 0,"
                            + "material10_id integer default 0,"
                            + "caktail_image BROB default 0)";

        String material_table = "CREATE TABLE material ("
                            + "_id integer primary key autoincrement not null,"
                            + "material_name text not null,"
                            + "sweetness integer default 0,"
                            + "clear integer default 0,"
                            + "bitter integer default 0,"
                            + "sour integer default 0,"
                            + "sibumi integer default 0"
                            + "alcohole integer default 0)";

        String ban_table = "CREATE TABLE ban_material("
                            + "material_id integer primary key,"
                            + "ban_material1_id integer default -1,"
                            + "ban_material2_id integer default -1,"
                            + "ban_material3_id integer default -1,"
                            + "ban_material4_id integer default -1,"
                            + "ban_material5_id integer default -1)";

        String have_table = "CREATE TABLE have_material("
                            + "material_id integer primary key,"
                            + "amount integer default 0)";
        db.execSQL(caktail_table);
        db.execSQL(material_table);
        db.execSQL(ban_table);
        db.execSQL(have_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
