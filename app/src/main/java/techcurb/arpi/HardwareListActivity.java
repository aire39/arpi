package techcurb.arpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

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

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED, null);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

}
