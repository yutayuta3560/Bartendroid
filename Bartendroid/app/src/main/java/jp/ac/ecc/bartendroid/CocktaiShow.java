package jp.ac.ecc.bartendroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class CocktaiShow extends ActionBarActivity {

    private ImageView imageView1;
    private TextView textView1;
    private Button makeButton;
    private Button cancelButton;
    private Cocktail cocktail;
    private CocatailDB cocatailDB = MainActivity.cocatailDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktai_show);
        setView();
        Intent intent = getIntent();
        String cocktailname = intent.getStringExtra("cocktail");
        cocktail = cocatailDB.getCocktail(cocktailname);
        Log.d("name","" + cocktail.getMaterial().size());
        imageView1.setImageBitmap(cocatailDB.getCocktailImage(cocktail));
        textView1.setText(setMessage(cocktail));



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
                    cocatailDB.getMaterialAmount(cocktail, material));
            message.append("\n");
        }

        return message.toString();
    }


}
