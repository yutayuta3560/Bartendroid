package jp.ac.ecc.bartendroid;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 2130298 on 2015/07/28.
 */
public class DBManager {



    public static void insertMaterial(CocatailDB db, Material material, String unit){

        db.setMaterial(material, unit);


    }

    public static void insertCocktail(CocatailDB db, Cocktail cocktail,ArrayList<Material> material, byte[] img){

        ArrayList<String> material_name = new ArrayList<>();
        for(Material m : material){
            material_name.add(m.getMaterialName());
        }
        db.setCaktail(cocktail.getCocktailName(), material_name, img);

    }
}
