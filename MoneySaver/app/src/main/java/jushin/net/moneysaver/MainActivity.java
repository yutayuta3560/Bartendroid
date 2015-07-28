package jushin.net.moneysaver;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    Calendar cal;

    TableRow[] tablerows;
    Button backButton;
    Button forwardButton;
    TextView yearmonthTextView;

    final int MP = TableRow.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextViewの紐付け
        yearmonthTextView = (TextView)findViewById(R.id.yearmonthTextView);

        // テーブル列の初期化
        tablerows = new TableRow[6];
        tablerows[0] = (TableRow) findViewById(R.id.row1);
        tablerows[1] = (TableRow) findViewById(R.id.row2);
        tablerows[2] = (TableRow) findViewById(R.id.row3);
        tablerows[3] = (TableRow) findViewById(R.id.row4);
        tablerows[4] = (TableRow) findViewById(R.id.row5);
        tablerows[5] = (TableRow) findViewById(R.id.row6);

        // 1ヶ月前に戻る
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setText("<<");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                initializeCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
            }
        });

        // 1ヶ月後に進む
        forwardButton = (Button)findViewById(R.id.forwardButton);
        forwardButton.setText(">>");
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, +1);
                initializeCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
            }
        });

        // 起動した日付を取得する
        cal = Calendar.getInstance();

        // その月のカレンダーを表示する
        initializeCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    public void initializeCalendar(int year, int month) {
        // カレンダーの内容を初期化する
        for (TableRow row : tablerows) {
            row.removeAllViews();
        }

        // カレンダーの日付を指定する
        cal.set(year, month - 1, 1);

        // 月の初めの曜日を取得する
        int yobiIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0(sun) ~ 6(sat)

        // その月の日数を取得する
        int daysOfMonth = cal.getActualMaximum(Calendar.DATE);

        // 日数カウント変数
        int day = 1;

        // それぞれのテーブル列
        for (int y = 0; y < tablerows.length; y++) {
            tablerows[y].setWeightSum(7);
            for (int x = 0; x < 7; x++) {

                // 各日付のボタンを生成している
                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(0, MP, 1));

                if ((y == 0 && x >= yobiIndex) || (y > 0 && day <= daysOfMonth)) {

                    // 日付を設定
                    btn.setText(Integer.toString(day++));

                    // 日曜日は文字色を赤色にする
                    if (x == Calendar.SUNDAY - 1) {
                        btn.setTextColor(Color.RED);
                    }

                    // 土曜日は文字色を青色にする
                    if (x == Calendar.SATURDAY - 1) {
                        btn.setTextColor(Color.BLUE);
                    }
                }

                // 日数が指定されていないボタンは見えなくする
                if (btn.getText().toString().equals("")) {
                    btn.setVisibility(View.INVISIBLE);
                }

                // SubActivityへ遷移する。その際、押したボタンの日付を送る
                btn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        Intent intent = new Intent(MainActivity.this, SubActivity.class);
                        intent.putExtra("date", ((Button)v).getText().toString());
                        MainActivity.this.startActivity(intent);

                        return false;
                    }
                });

                // ボタンを追加する
                tablerows[y].addView(btn);
            }
        }

        // 反映させた年月を表示させる
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        yearmonthTextView.setText(sdf.format(cal.getTime()));
    }

}
