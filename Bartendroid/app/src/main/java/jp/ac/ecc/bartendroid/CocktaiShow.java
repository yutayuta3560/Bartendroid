package jp.ac.ecc.bartendroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktai_show);



    }

    protected void setView(){
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        textView1 = (TextView)findViewById(R.id.textCocktailShow);
        makeButton = (Button)findViewById(R.id.makeButton);
        cancelButton = (Button)findViewById(R.id.makeCancelButton);
    }


}
