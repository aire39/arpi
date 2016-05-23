package techcurb.lists;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import techcurb.arpi.R;

/**
 * Created by seanx_000 on 5/22/2016.
 *
 */
public class HardwareAdapterList extends BaseExpandableListAdapter {

    private List<String> header_label;
    private HashMap<String, List<String> > child_label;
    private Context context;

    public HardwareAdapterList(Context context, List<String> header_label, HashMap<String, List<String>> child_label) {
        this.header_label = header_label;
        this.child_label  = child_label;
        this.context      = context;
    }

    @Override
    public int getGroupCount() {
        return header_label.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_label.get( header_label.get(groupPosition) ).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_label.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.child_label.get( this.header_label.get(groupPosition) ).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final String headerTitle = (String) getGroup(groupPosition);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listgroup_text, new LinearLayout(context), false);
        }

        TextView tv_header = (TextView) convertView.findViewById(R.id.listgroup_text);
        tv_header.setText( headerTitle );

        // initially have group lists expanded
        ExpandableListView elv = (ExpandableListView) parent;
        elv.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem_icon_text, new LinearLayout(context), false);
        }

        TextView tv_listchild = (TextView) convertView.findViewById(R.id.listitem_icon_text);
        tv_listchild.setText( childText );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if( childText.equals("Raspberry Pi") )
                tv_listchild.setCompoundDrawablesWithIntrinsicBounds( context.getResources().getDrawable(R.mipmap.ic_logo_pi, null) , null, null, null);
            else if( childText.equals("Arduino") )
                tv_listchild.setCompoundDrawablesWithIntrinsicBounds( context.getResources().getDrawable(R.mipmap.ic_logo_arduino, null) , null, null, null);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
