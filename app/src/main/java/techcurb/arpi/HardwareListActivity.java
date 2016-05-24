package techcurb.arpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import techcurb.lists.HardwareAdapterList;

/**
 * Created by seanx_000 on 5/22/2016.
 *
 */
public class HardwareListActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {

    private List<String> header_label = null;
    private HashMap<String, List<String> > child_label = null;

    private void prepare()
    {
        header_label = new ArrayList<>();
        child_label = new HashMap<>();
        List<String> hardware_list = new ArrayList<>();

        header_label.add("Hardware");

        hardware_list.add("Raspberry Pi");
        hardware_list.add("Arduino");

        child_label.put(header_label.get(0), hardware_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardwarelist);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xFF5492EF);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv_title = (TextView) findViewById(R.id.app_bar_main_toolbar_title);
        if(tv_title != null)
            tv_title.setText("H a r d w a r e");

        prepare();
        ExpandableListView expandablelist_hardwarelist = (ExpandableListView) findViewById(R.id.hardwarelist_expandable_hardware);
        if( expandablelist_hardwarelist != null) {
            HardwareAdapterList adapterlist_hardware = new HardwareAdapterList(this, header_label, child_label);
            expandablelist_hardwarelist.setAdapter(adapterlist_hardware);
            expandablelist_hardwarelist.setOnChildClickListener(this);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String selected_hardware = child_label.get(header_label.get(groupPosition)).get(childPosition);
        Intent data = new Intent();
        data.putExtra("c_hardware", selected_hardware);
        setResult(Activity.RESULT_OK, data);
        finish();
        overridePendingTransition(R.anim.activitytransition_still, R.anim.activitytransition_slideleft_out);

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED, null);
        finish();
        overridePendingTransition(R.anim.activitytransition_still, R.anim.activitytransition_slideleft_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

}
