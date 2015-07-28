package jushin.net.moneysaver;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends Activity {

    Button btn;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // MainActivityから渡された日付を受け取る
        Intent intent = getIntent();
        String str = intent.getStringExtra("date");

        btn = (Button)findViewById(R.id.saveButton);
        text = (EditText)findViewById(R.id.saveEditText);

        // テキストに入力されていた文字列を取得して表示する
        SharedPreferences pref = getSharedPreferences("cal", MODE_PRIVATE);
        String report = pref.getString("test", "保存したい文字列を入力してください。");
        text.setText(report);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // テキストに入力している文字列を保存する。
                SharedPreferences pref = SubActivity.this.getSharedPreferences("cal", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("test", text.getText().toString());
                edit.commit();

            }
        });
    }

}
