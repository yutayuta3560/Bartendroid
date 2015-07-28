package jp.ac.ecc.bartendroid;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.PlaybackState;
import android.util.Log;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CocatailDB {

    private SQLiteDatabase db;
    private final CocatailDBOpenHelper caktail;

    public CocatailDB(Context context){

        caktail = new CocatailDBOpenHelper(context);
        db = caktail.getWritableDatabase();
        db.close();
    }

    // 所持素材の数量更新
    public boolean setHaveMaterial(Material material, int amounnt){

        db = caktail.getWritableDatabase();
        boolean result = false;

        db.beginTransaction();
        try{
            String sql = "UPDATE have_material SET amount = ? WHERE material_id = (" + getMaterialIdSql(material.getMaterialName()) + ")";
            SQLiteStatement statement = db.compileStatement(sql);
            try {
                statement.bindLong(1, amounnt);
                statement.executeUpdateDelete();

            }catch (Exception e){

                Log.d("HaveSet", e.getMessage());

            }finally {
                statement.close();
            }
            db.setTransactionSuccessful();
            result = true;

        }catch (Exception e){
            Log.d("HaveSet", e.getMessage());
        }finally {

            db.endTransaction();
            db.close();
        }

        return result;
    }

    // 所持素材
    public ArrayList<Material> getHaveMaterial(){
        db = caktail.getReadableDatabase();
        ArrayList<Material> material_list = new ArrayList<Material>();
        String sql = "SELECT m.material_name, m.sweetness, m.clear, m.bitter, m.sour, m.sibumi From have_material h JOIN material m" +
                        " ON h.material_id = m._id WHERE h.amount <> 0";

        try {
            Cursor cursor = db.rawQuery(sql.toString(), null);

            while (cursor.moveToNext()) {

                material_list.add(new Material(cursor.getString(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
                                                cursor.getInt(4),cursor.getInt(5)));

            }
            cursor.close();
        }catch (Exception e){

            Log.d("Have", e.getMessage());
        } finally {

            db.close();
        }
        return material_list;
    }

    // 素材新規登録
    public boolean setMaterial(Material material, String unit){
        boolean result = false;
        db = caktail.getWritableDatabase();
        db.beginTransaction();
        try {
            SQLiteStatement statement
                    = db.compileStatement("INSERT INTO material " +
                    "(material_name, sweetness, clear, bitter, sour, sibumi,alcohole,unit) VALUES (?,?,?,?,?,?,?,?)");
            try {

                statement.bindString(1, material.getMaterialName());
                statement.bindLong(2, material.getSweetness());
                statement.bindLong(3, material.getClear());
                statement.bindLong(4, material.getBitterness());
                statement.bindLong(5, material.getSourness());
                statement.bindLong(6, material.getShibumi());
                statement.bindLong(7, material.getAlcohol_int());
                statement.bindString(8, unit);
                statement.executeInsert();

            } catch (Exception e) {

                Log.v("material", e.getMessage());
            } finally {
                statement.close();
            }

            // BAN
            SQLiteStatement statement2 = db.compileStatement("INSERT INTO ban_material(material_id) "
                                        + getMaterialIdSql(material.getMaterialName()));
            try {
                statement2.executeInsert();
            } catch (Exception e) {
                Log.d("Statement",e.getMessage());
            } finally {
                statement2.close();
            }
           SQLiteStatement statement3 = db.compileStatement("INSERT INTO have_material(material_id) "
                                                            + getMaterialIdSql(material.getMaterialName()));
            try {
                statement3.executeInsert();
            }catch (Exception e){
                Log.d("Statement",e.getMessage());
            }finally {
                statement3.close();
            }
            db.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
            Log.d("Statement",e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }
    // 素材更新
    public boolean updateMaterialTaste(Material material,CocktailTaste taste){

        boolean result = false;
        StringBuilder sql = new StringBuilder("UPDATE material SET ");
        switch (taste){
            case SWEET: sql.append(" sweetness = " + material.getSweetness());
                break;
            case SOUR: sql.append(" sour = " + material.getSourness());
                break;
            case SHIBUMI: sql.append(" sibumi = " + material.getShibumi());
                break;
            case BITTER: sql.append(" bitter = " + material.getBitterness());
                break;
            case CLEAR: sql.append(" clear = " + material.getClear());
                break;
            default: return result;
        }
        sql.append(" WHERE material_name = '" + material.getMaterialName() + "'");
        db = caktail.getWritableDatabase();
        db.beginTransaction();
        try{
            SQLiteStatement statement = db.compileStatement(sql.toString());
            try{
                statement.executeUpdateDelete();
            }catch (Exception e){
                Log.d("UpdateTaste", e.getMessage());
            }finally {
                statement.close();
            }
            db.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
            Log.d("UpdateTaste", e.getMessage());
        }finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }
    // カクテル新規登録
    public boolean setCaktail(String caktail_name, ArrayList<String> material, byte[] image){

        db = caktail.getWritableDatabase();
        boolean result = false;
        int count = material.size();
        if(count < 2){

            return result;

        }
        StringBuilder rows = new StringBuilder("material1_id,material2_id,");
        StringBuilder bindrows = new StringBuilder("VALUES(?,?,?,");
        for(int i = 2; i < count; i++){

            rows.append("material" + (i + 1) + "_id,");
            bindrows.append("?,");
        }

        bindrows.append("?)");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO caktail(caktail_name, " + rows.toString() + "caktail_image) " + bindrows.toString();
            final SQLiteStatement statement
                    = db.compileStatement(sql);
            try {
                statement.bindString(1, caktail_name);
                int i;
                for (i = 0; i < material.size(); i++) {
                    statement.bindLong(i + 2, getMaterialId(material.get(i)));
                }
                if (image != null) {
                    statement.bindBlob(i + 2, image);
                } else {
                    statement.bindNull(i + 2);
                }
                statement.executeInsert();
            }catch (Exception e){
                Log.d("caktail_statement", e.getMessage());
            } finally {
                statement.close();
            }
            StringBuilder sql2 = new StringBuilder("SELECT _id, material1_id, material2_id ");
            for(int i = 2; i < count; i++){
                sql2.append(",material" + (i + 1) + "_id");
            }
            sql2.append(" FROM caktail WHERE caktail_name = '" + caktail_name + "'");
            try {
                Cursor cursor = db.rawQuery(sql2.toString(), null);
                if(cursor.moveToFirst()){
                    StringBuilder insertSql = new StringBuilder("INSERT INTO material_amount(caktail_id, material_id) VALUES ");
                    for(int i = 1; i < cursor.getColumnCount(); i++){
                        insertSql.append("(" + cursor.getInt(0) + "," + cursor.getInt(i) + ")");
                        if(i != cursor.getColumnCount() - 1){
                            insertSql.append(",");
                        }
                    }

                    final SQLiteStatement statement3 = db.compileStatement(insertSql.toString());
                    try {
                        statement3.executeInsert();
                    }catch (Exception e){
                        Log.d("amount", e.getMessage());
                    }finally {
                        statement3.close();
                    }
                }
            }catch (Exception e) {
                Log.d("amount", e.getMessage());
            }
            db.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
            Log.d("Caktail_Table", e.getMessage());
        }finally {
            db.endTransaction();
            db.close();
        }
        return result;

    }

    // カクテル素材の作成必要量入力
    public boolean setMaterialAmount(Cocktail cocktail, Material material, final int amount){

        boolean result = false;
        db = caktail.getWritableDatabase();
        StringBuilder sql = new StringBuilder("UPDATE material_amount SET amount = ? WHERE caktail_id = ");
        sql.append("(SELECT _id FROM caktail WHERE caktail_name = '" + cocktail.getCocktailName() + "') AND material_id = ");
        sql.append("(" + getMaterialIdSql(material.getMaterialName()) + ")");
        db.beginTransaction();
        try {
            SQLiteStatement statement = db.compileStatement(sql.toString());
            try {
                statement.bindLong(1, amount);
                statement.executeUpdateDelete();
            } catch (Exception e) {
                Log.d("SetmaterialAmount", e.getMessage());
            }finally {
                statement.close();
            }
            db.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
            Log.d("SetmaterialAmount", e.getMessage());
        }finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }

    // カクテルの素材必要量取得
    public String getMaterialAmount(Cocktail cocktail, Material material){
        String amount = "0";
        String unit = "ml";
        String sql = "SELECT a.amount, m.unit FROM material_amount a JOIN material m" +
                " ON a.material_id = m._id" +
                " WHERE a.caktail_id = (SELECT _id FROM caktail " +
                "WHERE caktail_name = '" + cocktail.getCocktailName() + "') AND a.material_id = (SELECT _id FROM material " +
                "WHERE material_name = '" + material.getMaterialName() + "')";

        db = caktail.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                amount = String.valueOf(cursor.getInt(0));
                unit = cursor.getString(1);
            }
            cursor.close();
        }catch (Exception e){
            Log.v("getAmount", e.getMessage());
        }finally {
            db.close();
        }

        return amount + " " + unit;
    }
    // 指定カクテル名の素材
    public Cocktail getCocktail(String cocktail_name){

        Cocktail cocktail = new Cocktail(cocktail_name);
        db = caktail.getReadableDatabase();
        String sql = "SELECT material1_id, material2_id, material3_id, material4_id, material5_id," +
                "material6_id, material7_id, material8_id, material9_id, material10_id FROM caktail WHERE caktail_name = '" + cocktail_name + "'";
        try{
            Cursor cursor = db.rawQuery(sql, null);
            // カクテル格納
            while (cursor.moveToNext()){

                StringBuilder materialSql = new StringBuilder();
                materialSql.append("SELECT material_name, sweetness, clear, bitter, sour, sibumi, alcohole FROM material " +
                        "WHERE _id IN(");
                for(int j = 0; j < cursor.getColumnCount(); j++){
                    materialSql.append(cursor.getInt(j));
                    if(j != cursor.getColumnCount() - 1){
                        materialSql.append(",");
                    }
                }
                materialSql.append(")");
                try{
                    Cursor materialCusor = db.rawQuery(materialSql.toString(), null);
                    // カクテルの素材
                    while (materialCusor.moveToNext()){
                        cocktail.addMaterial(new Material(
                                materialCusor.getString(0),
                                materialCusor.getInt(1),
                                materialCusor.getInt(2),
                                materialCusor.getInt(3),
                                materialCusor.getInt(4),
                                materialCusor.getInt(5),
                                materialCusor.getInt(6)
                        ));
                    }
                    materialCusor.close();
                }catch (Exception e){
                    Log.d("GetCocatail", e.getMessage());
                }

            }
            cursor.close();
        }catch (Exception e){
            Log.d("GetCocatail", e.getMessage());
        }finally {
            db.close();
        }
        return cocktail;
    }
    // 現在登録されているカクテル全て
    public ArrayList<Cocktail> getCocktailList(){

        ArrayList<Cocktail> cocktailArrayList = new ArrayList<>();
        db = caktail.getReadableDatabase();
        String sql = "SELECT caktail_name, material1_id, material2_id, material3_id, material4_id, material5_id," +
                "material6_id, material7_id, material8_id, material9_id, material10_id FROM caktail";
        try{
            Cursor cursor = db.rawQuery(sql, null);
            int i = 0;

            // カクテル格納
            while (cursor.moveToNext()){
                cocktailArrayList.add(new Cocktail(cursor.getString(0)));   // 名前
                StringBuilder materialSql = new StringBuilder();
                materialSql.append("SELECT material_name, sweetness, clear, bitter, sour, sibumi, alcohole FROM material " +
                        "WHERE _id IN(");
                for(int j = 1; j < cursor.getColumnCount(); j++){
                    materialSql.append(cursor.getInt(j));
                    if(j != cursor.getColumnCount() - 1){
                        materialSql.append(",");
                    }
                }
                materialSql.append(")");
                try{
                    Cursor materialCusor = db.rawQuery(materialSql.toString(), null);
                    // カクテルの素材
                    while (materialCusor.moveToNext()){
                        cocktailArrayList.get(i).addMaterial(new Material(
                                materialCusor.getString(0),
                                materialCusor.getInt(i),
                                materialCusor.getInt(2),
                                materialCusor.getInt(3),
                                materialCusor.getInt(4),
                                materialCusor.getInt(5),
                                materialCusor.getInt(6)
                        ));
                    }
                    materialCusor.close();
                }catch (Exception e){
                    Log.d("GetCocatailList", e.getMessage());
                }
                i++;
            }
            cursor.close();
        }catch (Exception e){
            Log.d("GetCocatailList", e.getMessage());
        }finally {
            db.close();
        }
        return cocktailArrayList;
    }

    // 指定素材でできるカクテル
    public ArrayList<Cocktail> getMakableCaktail(ArrayList<Material> materials) {
        db = caktail.getReadableDatabase();
        StringBuilder sql
                = new StringBuilder("SELECT caktail_name FROM caktail WHERE ");
        ArrayList<Cocktail> caktails = new ArrayList<Cocktail>();

        for (int i = 1; i <= 10; i++) {

            sql.append("material" + String.valueOf(i) + "_id IN(0,");

            for(int j = 0; j < materials.size(); j++) {
                sql.append(getMaterialId(materials.get(j).getMaterialName()));
                if(j != materials.size() - 1){
                    sql.append(",");
                }
            }
            sql.append(") ");
            if(i != 10){
                sql.append(" AND ");
            }
        }
        try {
            Cursor cursor = db.rawQuery(sql.toString(), null);

            int i = 0;
            while (cursor.moveToNext()) {

                caktails.add(new Cocktail(cursor.getString(0)));
                StringBuilder sql2 = new StringBuilder();
                sql2.append( "SELECT material_name, sweetness, clear, bitter, sour, sibumi, alcohole FROM material WHERE _id IN(");

                for(int j = 0; j < materials.size(); j++) {
                    sql2.append(getMaterialId(materials.get(j).getMaterialName()));
                    if(j != materials.size() - 1){
                        sql2.append(",");
                    }
                }
                sql2.append(")");
                try {
                    Cursor cursor2 = db.rawQuery(sql2.toString(), null);
                    while (cursor2.moveToNext()){
                        caktails.get(i).addMaterial(new Material(cursor2.getString(0), cursor2.getInt(1), cursor2.getInt(2),
                                cursor2.getInt(3), cursor2.getInt(4), cursor2.getInt(5), cursor2.getInt(6)));
                    }
                    cursor2.close();
                }catch (Exception e){

                    Log.d("Makable",e.getMessage());
                }
                i++;
            }
            cursor.close();
        }catch (Exception e){
            Log.d("Makeable", e.getMessage());
        } finally {
            db.close();
        }
        return caktails;
    }

    // カクテルImage設定
    public boolean setCocktailImage(Cocktail cocktail, byte[] image){
        boolean result = false;
        if(image == null){
            return result;
        }
        String sql = "UPDATE caktail SET caktail_image = ?"
                + " WHERE caktail_name = '" + cocktail.getCocktailName() + "'";
        db = caktail.getWritableDatabase();
        db.beginTransaction();
        try{
            SQLiteStatement statement = db.compileStatement(sql);
            try{
                statement.bindBlob(1, image);
                statement.executeUpdateDelete();
            }catch (Exception e){
                Log.d("UpdateImage", e.getMessage());
            }finally {
                statement.close();
            }
            db.setTransactionSuccessful();
            result = true;

        }catch (Exception e){
            Log.d("UpdateImage", e.getMessage());
        }finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }
    // カクテルImages取得
    public Bitmap getCocktailImage(Cocktail cocktail){

        Bitmap bitmap = null;
        String sql = "SELECT caktail_image FROM caktail WHERE caktail_name = '" + cocktail.getCocktailName() + "'";
        db = caktail.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                byte[] image = null;
                image = cursor.getBlob(0);
                if(image != null){
                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                }
            }
        }catch (Exception e){
            Log.d("GetImage", e.getMessage());
        }finally {
            db.close();
        }

        return bitmap;
    }
    // NGリスト登録
    public boolean setBanMaterial(Material material1, Material material2){

        boolean result = false;
        db = caktail.getWritableDatabase();
        int m1_id = getMaterialId(material1.getMaterialName());
        int m2_id = getMaterialId(material2.getMaterialName());
        db.beginTransaction();
        try {
            int material1_set = -1;
            int material2_set = -1;
            String sql = "SELECT * FROM ban_material WHERE material_id IN("
                    + m1_id + ","
                    + m2_id + ")";
            try {
                Cursor cursor = db.rawQuery(sql, null);

                while (cursor.moveToNext()) {

                    for (int i = 1; i < cursor.getColumnCount(); i++) {

                        if (cursor.getInt(i) == -1 && cursor.getInt(0) == m1_id) {
                            material1_set = i;
                            break;
                        } else if (cursor.getInt(i) == -1 && cursor.getInt(0) == m2_id) {
                            material2_set = i;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("SetBan", e.getMessage());
                return result;
            }


            String sql2 = "UPDATE ban_material SET ban_material" + material1_set + "_id = " +
                    m2_id + " WHERE material_id = " + m1_id;
            final SQLiteStatement statement = db.compileStatement(sql2);
            try {
                statement.executeUpdateDelete();
            } finally {
                statement.close();
            }
            sql2 = "UPDATE ban_material SET ban_material" + material2_set + "_id = " +
                    m1_id + " WHERE material_id = " + m2_id;
            final SQLiteStatement statement2 = db.compileStatement(sql2);
            try {
                statement2.executeUpdateDelete();
            } finally {
                statement2.close();
            }
            db.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
            Log.d("SetBan", e.getMessage());
        }finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }
    // 素材名からidをとるSQL作成
    private String getMaterialIdSql(String material){
        return "SELECT _id FROM material WHERE material_name = '" + material + "'";
    }
    // 素材名からIDを返すSQL実行(副問い合わせができない場合に使用)
    private int getMaterialId(String material){
        String sql = "SELECT _id FROM material WHERE material_name = '" + material + "';";
        int id = -1;
        try {
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }catch (Exception e){

            Log.d("GetID", e.getMessage());
        }
        return id;
    }

    // ID から 素材をとる
    private Material getMaterialById(int id){
        Material material = null;
        String sql = "SELECT material_name, sweetness, clear, bitter, sour, sibumi, alcohole FROM material WHERE _id = " + id;
        try {
            Cursor cursor = db.rawQuery(sql,null);
            if (cursor.moveToFirst()){
                material = new Material(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(6)
                );
            }
        }catch (Exception e){
            Log.d("GetMaterialByid",e.getMessage());
        }
        return material;
    }
    //　素材名からNGリストの素材を返す
    public ArrayList<Material> getBanMaterial(ArrayList<String> materialnames){
        ArrayList<Material> ban_list = new ArrayList<>();
        db = caktail.getReadableDatabase();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT material_id FROM ban_material ban1"
                    + " WHERE  EXISTS (SELECT * FROM ban_material ban2"
                    + " WHERE (ban2.ban_material1_id = ban1.material_id"
                    + " OR ban2.ban_material2_id = ban1.material_id"
                    + " OR ban2.ban_material3_id = ban1.material_id"
                    + " OR ban2.ban_material4_id = ban1.material_id"
                    + " OR ban2.ban_material5_id = ban1.material_id)"
                    + " AND ban1.material_id IN(");
        for(int i = 0; i < materialnames.size(); i++){
            sql.append(getMaterialId(materialnames.get(i)));
            if(i != materialnames.size() - 1){
                sql.append(",");
            }
        }
        sql.append("))");

        try {
            Cursor cusor = db.rawQuery(sql.toString(), null);
            while (cusor.moveToFirst()) {
                ban_list.add(getMaterialById(cusor.getInt(0)));
            }
        }catch (Exception e){
            Log.d("GetBan", e.getMessage());
        }finally {
            db.close();
        }

        return ban_list;
    }
}
