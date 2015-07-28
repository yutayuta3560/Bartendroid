package jp.ac.ecc.bartendroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class CocktailSearch extends ActionBarActivity {

    private RadioGroup group;
    private RadioButton alcoholeButton;
    private RadioButton nonalcoholeButton;
    private Spinner basespinner;
    private Spinner tastespinner1;
    private Spinner tasetelevelspinner1;
    private Button searchbutton;
    private ListView searchListView;
    private CocatailDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CocatailDB(getApplicationContext());
        setContentView(R.layout.activity_cocktail_search);
        setView();
    }


    private void setView(){
        group = (RadioGroup)findViewById(R.id.alcoholegroup);
        alcoholeButton = (RadioButton)findViewById(R.id.alcoholebutton);
        nonalcoholeButton = (RadioButton)findViewById(R.id.nonalcoholebutton);
        basespinner = (Spinner)findViewById(R.id.basespinner);
        tastespinner1 = (Spinner)findViewById(R.id.tastespiner1);
        tasetelevelspinner1 = (Spinner)findViewById(R.id.tastelevelspiner1);
        searchbutton = (Button)findViewById(R.id.searchButton);
        searchListView = (ListView)findViewById(R.id.searchListView);

    }
    private void setSpinnerAdapter(){
        ArrayList<Material> materials = db.getMaterials();
        List<String> baselist = new ArrayList<>();
        for(Material material : materials){
            baselist.add(material.getMaterialName());
        }
        basespinner.setAdapter(createSpinnerAdapter(baselist));

    }
    private ArrayAdapter<String> createSpinnerAdapter(List<String> list){
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        for(String item : list){
            adapter.add(item);
        }

        return adapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
