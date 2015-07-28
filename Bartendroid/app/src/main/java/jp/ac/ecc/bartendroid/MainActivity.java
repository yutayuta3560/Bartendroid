package jp.ac.ecc.bartendroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    static protected CocatailDB cocktailDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cocktailDB = new CocatailDB(getApplicationContext());

        setExampleData();
        ListView listview1 = (ListView)findViewById(R.id.listview1);

        ArrayList<Cocktail> cocktailArrayList = makeHaveCocktail();

        Log.d("TEST", cocktailArrayList.get(0).getCocktailName());

        listview1.setAdapter(setArrayAdapter(cocktailArrayList));
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView listView = (ListView) adapterView;
                String item = (String) listView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, CocktaiShow.class);
                intent.putExtra("cocktail", item);
                startActivity(intent);
            }
        });
    }

    public ArrayAdapter<String> setArrayAdapter(ArrayList<Cocktail> list){

        ArrayList<String> cocktail_name = new ArrayList<String>();

        for(int i = 0; i < list.size(); i++){
            cocktail_name.add(list.get(i).getCocktailName());
        }
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cocktail_name );
    }

    public ArrayList<Cocktail> makeHaveCocktail(){
        ArrayList<Material> materials = new ArrayList<>();
        ArrayList<Cocktail> cocktails = cocktailDB.getCocktailList();
        for(MaterialBring brings : cocktailDB.getHaveMaterial()){
           for(Cocktail cocktail : cocktails){
                int amount = cocktailDB.getMaterialAmount(cocktail, brings.material);

                //if(amount != 0 && brings.tryuseMaterial(amount)){
                   materials.add(brings.material);
               // }
            }
        }
        return cocktailDB.getMakableCaktail(materials);
    }
    public void initialData() {
        List<Material> list = new ArrayList<Material>();
        List<Cocktail> cocktails = new ArrayList<>();
        list.add(new Material("JIN", 3, 3, 3, 3, 3, true, "ml"));
        list.add(new Material("Tonic", 1, 4, 5, 4, 3, true, "ml"));
        list.add(new Material("Orange", 5, 2, 3, 5, 3, false, "ml"));
        createDatabase(list);
    }

    public void createDatabase(List<Material> list) {
        ArrayList<String> material_names = new ArrayList<String>();
        for (Material material : list) {
            cocktailDB.setMaterial(material, material.unit);

        }
    }

    public void setExampleData(){

        Material material1 = new Material("JIN", 3, 3, 3, 3, 3);
        Material material2 = new Material("Tonic", 1,4,5,4,3);
        Material material3 = new Material("Orange", 5,2,3,5,3);
        cocktailDB.setMaterial(material1, "ml");
        cocktailDB.setMaterial(material2, "ml");
        cocktailDB.setMaterial(material3, "ml");
        ArrayList<String> material_names = new ArrayList<>();
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(material1);
        materials.add(material2);
        materials.add(material3);
        material_names.add(material1.getMaterialName());
        material_names.add(material2.getMaterialName());

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] image = baos.toByteArray();
        Cocktail jinTonic = new Cocktail("JIN TONIC");
        jinTonic.addMaterial(material1);
        jinTonic.addMaterial(material2);
        cocktailDB.setCaktail(jinTonic, image);

        ArrayList<Cocktail> cocktailArrayList = cocktailDB.getCocktailList();

        if(cocktailArrayList != null){
            cocktailDB.setMaterialAmount(cocktailArrayList.get(0), cocktailArrayList.get(0).getMaterial().get(0), 10);
        }
        cocktailDB.setHaveMaterial(material1, 100);
        cocktailDB.setHaveMaterial(material2, 100);
        cocktailDB.setHaveMaterial(material3, 100);


        ArrayList<MaterialBring> bring_list = new ArrayList<>();
        bring_list.add(new MaterialBring(material1, 100, "ml"));
        bring_list.add(new MaterialBring(material3, 100, "ml"));
        bring_list.add(new MaterialBring(material2, 100, "ml"));

        try {
            CocktailCounter counter = new CocktailCounter(CocktailTaste.SWEET , 3);
            counter.addMaterial(bring_list);
            counter.createNewCocktail(true);

        }catch (Exception e){

            Log.d("Error", e.getMessage());
        }
    }




}
