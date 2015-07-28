package jp.ac.ecc.bartendroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CocktaiShow extends ActionBarActivity {

    private ImageView imageView1;
    private TextView textView1;
    private Button makeButton;
    private Button cancelButton;
    private Cocktail cocktail;
    private CocatailDB cocatailDB = MainActivity.cocktailDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktai_show);
        setView();
        final Intent intent = getIntent();
        String cocktailname = intent.getStringExtra("cocktail");
        cocktail = cocatailDB.getCocktail(cocktailname);
        Log.d("name","" + cocktail.getMaterial().size());
        imageView1.setImageBitmap(cocatailDB.getCocktailImage(cocktail));
        textView1.setText(setMessage(cocktail));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(CocktaiShow.this, MainActivity.class);
                startActivity(intent2);
            }
        });



    }

    protected void setView(){
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        textView1 = (TextView)findViewById(R.id.textCocktailShow);
        makeButton = (Button)findViewById(R.id.makeButton);
        cancelButton = (Button)findViewById(R.id.makeCancelButton);
    }

    private String setMessage(Cocktail cocktail){

        StringBuilder message = new StringBuilder();
        message.append("カクテル名 : " + cocktail.getCocktailName() + "\n");
        message.append("[素材一覧]\n");
        for(Material material : cocktail.getMaterial()) {
            message.append(material.getMaterialName() + " : " +
                    cocatailDB.getMaterialAmountUnit(cocktail, material));
            message.append("\n");
        }
        return message.toString();
    }

    private boolean cocktailMaking(Cocktail cocktail){
        ArrayList<Material> materials = cocatailDB.getHaveMaterial();
        ArrayList<MaterialBring> bring = new ArrayList<>();
        for(Material material : materials){
            bring.add(new MaterialBring(material,
                    cocatailDB.getMaterialAmount(cocktail, material),
                    cocatailDB.getUnit(material)));
        }

        return false;
    }

}
