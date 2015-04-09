
package frontEnd;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.linkedin.R;

/**
 * Created by lanzhang_mini on 15/4/9.
 */
public class SearchJob_activity extends Activity {

    private static final String[] m={"Google","Oracle","LinkedIn","Amazon","Baidu","Microsoft","Twitter","Facebook"};
    private TextView spinnerview;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        spinnerview = (TextView)findViewById(R.id.company_spinnerTest);
        spinner = (Spinner) findViewById(R.id.spinner);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        spinner.setVisibility(View.VISIBLE);


    }
    class SpinnerSelectedListener implements OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
              spinnerview.setText(m[arg2]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}