package jp.ac.ecc.bartendroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.session.PlaybackState;

import java.util.ArrayList;

public class CocatailDB {

    private SQLiteDatabase db;
    private final CocatailDBOpenHelper caktail;

    public CocatailDB(Context context){

        caktail = new CocatailDBOpenHelper(context);
        db = caktail.getWritableDatabase();
        db.close();
    }

    public boolean setHaveMaterial(String material, int amounnt){

        db = caktail.getWritableDatabase();
        boolean result = false;
        if(getMaterialId(material) == -1){
            setMaterial(material);
        }
        db.beginTransaction();
        try{
            String sql = "UPDATE have_material SET amount = ? WHERE material_id = " + getMaterialId(material);
            SQLiteStatement statement = db.compileStatement(sql);
            try{
                statement.bindLong(1,amounnt);
                statement.executeUpdateDelete();
            }finally {
                statement.close();
            }
            db.setTransactionSuccessful();
            result = true;
        }finally {

            db.endTransaction();
            db.close();
        }

        return result;
    }

    public ArrayList<Material> getHaveMaterial(){

        db = caktail.getReadableDatabase();
        ArrayList<Material> material_list = new ArrayList<Material>();
        String sql = "SELECT m.material_name, m.sweetnes, m.clear, m.bitter, m.sour, m.sibumi From have_material h JOIN material m" +
                        "ON h.material_id = m.l_id WHERE h.amount <> 0";

        try {
            Cursor cursor = db.rawQuery(sql.toString(), null);

            while (cursor.moveToNext()) {

                material_list.add(new Material(cursor.getString(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
                                                cursor.getInt(4),cursor.getInt(5)));

            }
            cursor.close();
        }catch (Exception e){

        } finally {

            db.close();
        }

        return material_list;
    }
    public boolean setMaterial(String material_name){

        boolean result = false;
        db = caktail.getWritableDatabase();
        db.beginTransaction();
        try {
            SQLiteStatement statement
                    = db.compileStatement("INSERT INTO material(material_name) VALUES (?)");
            try {

                statement.bindString(1, material_name);
                statement.executeInsert();


            } finally {
                statement.close();
            }
            db.setTransactionSuccessful();

            // BAN
            SQLiteStatement statement2 = db.compileStatement("INSERT INTO ban_material(material_id) VALUES(?)");

            try{

                statement2.bindLong(1, getMaterialId(material_name));
                statement2.executeInsert();
            }finally {
                statement2.close();
            }

            db.setTransactionSuccessful();

            SQLiteStatement statement3 = db.compileStatement("INSERT INTO have_material(material_id) VALUES(?)");
            try{

                statement3.bindLong(1, getMaterialId(material_name));
                statement3.executeInsert();
            }finally {
                statement3.close();
            }
            db.setTransactionSuccessful();
            result = true;
        } finally {
            db.endTransaction();
            db.close();
        }

        return result;
    }

    public boolean setMaterial(Material material){

        boolean result = false;
        db = caktail.getWritableDatabase();
        db.beginTransaction();
        try {
            SQLiteStatement statement
                    = db.compileStatement("INSERT INTO material(material_name, sweetness, clear, bitter, sour, sibumi) VALUES (?,?,?,?,?,?)");
            try {

                    statement.bindString(1, material.getMaterialName());
                    statement.bindLong(2, material.getSweetness());
                    statement.bindLong(3, material.getClear());
                    statement.bindLong(4, material.getBitterness());
                    statement.bindLong(5, material.getSourness());
                    statement.bindLong(6, material.getShibumi());
                    statement.executeInsert();


            } finally {
                statement.close();
            }
            db.setTransactionSuccessful();

            // BAN
            SQLiteStatement statement2 = db.compileStatement("INSERT INTO ban_material(material_id) VALUES(?)");

            try{

                statement2.bindLong(1, getMaterialId(material.getMaterialName()));
                statement2.executeInsert();

            }finally {
                statement2.close();
            }

            db.setTransactionSuccessful();
            SQLiteStatement statement3 = db.compileStatement("INSERT INTO have_material(material_id) VALUES(?)");
            try{

                statement3.bindLong(1, getMaterialId(material.getMaterialName()));
                statement3.executeInsert();
            }finally {
                statement3.close();
            }
            db.setTransactionSuccessful();
            result = true;
        } finally {
            db.endTransaction();
            db.close();
        }

        return result;
    }

    public boolean setCaktail(String caktail_name, ArrayList<String> material, byte[] image){

        db = caktail.getWritableDatabase();
        boolean result = false;
        int count = material.size();
        if(count < 2){

            return result;

        }
        StringBuilder rows = new StringBuilder("material1,material2.");
        StringBuilder bindrows = new StringBuilder("VALUES(?,?,?,");
        for(int i = 3; i < count; i++){

            rows.append("material" + i + ",");
            bindrows.append("?,");
        }

        bindrows.append("?)");
        db.beginTransaction();
        try{
            String sql = "INSERT INTO caktail(caktail_name, " + rows.toString() + "caktail_image) " + bindrows.toString();
            final SQLiteStatement statement
                    = db.compileStatement(sql);

            try {
                statement.bindString(1, caktail_name);
                int i = 0;
                for(i = 0; i < material.size(); i++){

                    statement.bindLong(i + 2, getMaterialId(material.get(i)));

                }
                if (!image.equals(null)) {
                    statement.bindBlob(i + 2, image);
                }else {
                    statement.bindNull(i + 2);
                }
                statement.executeInsert();
            }finally {
                statement.close();
            }
            db.setTransactionSuccessful();
            result = true;
        }finally {
            db.endTransaction();
            db.close();
        }

        return result;

    }

    public ArrayList<Cocktail> getMakableCaktail(ArrayList<Material> materials) {

        int count = materials.size();
        int id = 0;
        StringBuilder sql = new StringBuilder("SELECT caktail_name FROM caktail WHERE ");
        ArrayList<Cocktail> caktails = new ArrayList<Cocktail>();

        for (int i = 0; i < count; i++) {
            sql.append("material" + (i + 1) + " IN(");
            for (int j = 0; j < count; j++) {


                if ((j + 1) != count) {
                    sql.append(",");
                }
            }

            sql.append(") ");
            if ((i + 1) != count) {
                sql.append("AND ");
            }
        }

        db = caktail.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(sql.toString(), null);

            while (cursor.moveToNext()) {
                 
            }
            cursor.close();
        } finally {
            db.close();
        }

        return caktails;

    }
    private boolean setBanMaterial(String material1, String material2){

        boolean result = false;
        db = caktail.getWritableDatabase();
        db.beginTransaction();
        try{
            int[] ids = getBanMaterialId(material1);
            int i = 0;
            while(ids[i] != -1){
                i++;
            }

            String sql = "UPDATE ban_material SET material" + (i + 1) + " = ? WHERE = " + getMaterialId(material1);
            final SQLiteStatement statement = db.compileStatement(sql);
            try {
                statement.bindLong(1, getMaterialId(material2));
                statement.executeUpdateDelete();
            }finally {
                statement.close();
            }
            db.setTransactionSuccessful();
            result = true;
        }finally {

            db.endTransaction();
            db.close();
        }

        return result;
    }
    private int getMaterialId(String material){

        String sql = "SELECT _id FROM material WHERE = '" + material + "';";
        int id = -1;
        try{
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
            cursor.close();
        }finally{

        }

        if(id == -1){
            setMaterial(material);
            return getMaterialId(material);
        }
        return id;
    }

    public int[] getBanMaterialId(String material){

        db = caktail.getReadableDatabase();
        String sql = "SELECT ban_materia1_id, ban_material2_id, ban_material3_id, bam_material4_id, ban_material5_id FROM ban_material"
                   + " WHERE material_id = " + getMaterialId(material) + ";";
        int[] ban_material_ids = new int[5];
        try{
            Cursor cusor = db.rawQuery(sql, null);

            if(cusor.moveToNext()){
                for(int i = 0; i < cusor.getCount(); i++){

                    ban_material_ids[i] = cusor.getInt(i);

                }

            }
        }finally {
            db.close();
        }

        return ban_material_ids;
    }
}
