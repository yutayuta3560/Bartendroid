package jp.ac.ecc.bartendroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private  CocatailDB cocatailDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cocatailDB = new CocatailDB(getApplicationContext());

        setExampleData();
        /*ListView listview1 = (ListView)findViewById(R.id.listview1);

        ArrayList<Cocktail> cocktailArrayList = cocatailDB.getMakableCaktail(cocatailDB.getHaveMaterial());

        listview1.setAdapter(setArrayAdapter(cocktailArrayList));*/

    }

    public ArrayAdapter<String> setArrayAdapter(ArrayList<Cocktail> list){

        ArrayList<String> cocktail_name = new ArrayList<String>();

        for(int i = 0; i < list.size(); i++){
            cocktail_name.add(list.get(i).getCocktailName());
        }
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cocktail_name );
    }

    public void setExampleData(){

        Material material1 = new Material("JIN", 3, 3, 3, 3, 3);
        Material materia2 = new Material("Tonic", 1,4,5,4,3);
        Material material3 = new Material("Orange", 5,2,3,5,3);
        cocatailDB.setMaterial(material1);
        cocatailDB.setMaterial(materia2);
        cocatailDB.setMaterial(material3);
        ArrayList<String> materials = new ArrayList<>();
        materials.add(material1.getMaterialName());
        materials.add(materia2.getMaterialName());
        /*cocatailDB.setCaktail("JIN Tonic", materials, null);
        materials.add((material3.getMaterialName()));
        cocatailDB.setCaktail("TEST cocktail", materials, null);*/


    }




}
