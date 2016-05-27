package techcurb.widgets;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import techcurb.arpi.R;
import techcurb.listener.onSwipeTouchListener;

/**
 * Created by seanx_000 on 5/24/2016.
 *
 */
public class WidgetProjectsButton extends Fragment {

    private String name, hardware, timestamp;
    private Integer id;

    private LinearLayout ll_optionbody;
    private RelativeLayout rl_hidden;

    private View.OnClickListener ocl_project = null;
    private View.OnClickListener ocl_delete  = null;
    private View.OnClickListener ocl_edit    = null;

    private boolean inAnimatedState = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /// Inflate the widget layout (widget_btn_project.xml)

        final RelativeLayout ll_widgetbody = (RelativeLayout) inflater.inflate(R.layout.widget_btn_project, container, false);
        rl_hidden     = (RelativeLayout) ll_widgetbody.findViewById(R.id.widget_project_btn_hidden).getParent();
        ll_optionbody = (LinearLayout)   ll_widgetbody.findViewById(R.id.widget_project_ll_option);
        final ImageView iv_delete = (ImageView) ll_widgetbody.findViewById(R.id.widget_project_btn_delete);
        final ImageView iv_edit = (ImageView) ll_widgetbody.findViewById(R.id.widget_project_btn_edit);

        iv_delete.setId(id);
        iv_edit.setId(id);
        ll_widgetbody.setId(id);

        ll_widgetbody.setOnClickListener(ocl_project);
        ll_widgetbody.setOnTouchListener(ostl);
        iv_delete.setOnClickListener(ocl_delete);
        iv_delete.setOnTouchListener(ostl);
        ll_optionbody.setOnTouchListener(ostl);
        iv_edit.setOnClickListener(ocl_edit);

        TextView  tv_projectname = (TextView)  ll_widgetbody.findViewById(R.id.widget_project_tv_projectname);
        TextView  tv_projectdate = (TextView)  ll_widgetbody.findViewById(R.id.widget_project_tv_timestamp);
        TextView  tv_timecreated = (TextView)  ll_widgetbody.findViewById(R.id.widget_project_tv_time);
        ImageView img_hardware   = (ImageView) ll_widgetbody.findViewById(R.id.widget_project_img_hardware);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date dt = sdf.parse(timestamp, new ParsePosition(0));
        Calendar c = new GregorianCalendar();
        c.setTime(dt);

        timestamp = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " ";
        timestamp += Integer.toString(c.get(Calendar.DAY_OF_MONTH))                       + " ";
        timestamp += Integer.toString(c.get(Calendar.YEAR))                               + " ";
        tv_projectdate.setText(timestamp);

        timestamp = String.format(Locale.getDefault(),"%02d", c.get(Calendar.HOUR))        + ":";
        timestamp += String.format(Locale.getDefault(),"%02d", c.get(Calendar.MINUTE))     + " ";
        timestamp += c.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault()) + " ";

        tv_timecreated.setText(timestamp);
        tv_projectname.setText(name);

        if(hardware.equals("Arduino")) {
            img_hardware.setImageResource(R.mipmap.ic_logo_arduino);
        }
        else if(hardware.equals("Raspberry Pi")) {
            img_hardware.setImageResource(R.mipmap.ic_logo_pi);
        }

        return ll_widgetbody;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getView() != null)
            getView().post( new Runnable() {
                @Override
                public void run() {
                    if(ll_optionbody != null) {
                        ll_optionbody.setX(ll_optionbody.getWidth()-rl_hidden.getWidth());
                        ll_optionbody.requestLayout();
                    }
                }
            });


    }

    @SuppressWarnings("unused")
    public Integer getid() {
        return id;
    }

    public void setWidget(String name, String hardware, String timestamp, Integer id) {
        this.name      = name;
        this.hardware  = hardware;
        this.timestamp = timestamp;
        this.id = id;
    }

    public void setPickedProjectListener(View.OnClickListener ocl_project) {
        this.ocl_project = ocl_project;
    }

    public void setDeleteListener(View.OnClickListener ocl_delete) {
        this.ocl_delete = ocl_delete;
    }


    public void setEditListener(View.OnClickListener ocl_edit) {
        this.ocl_edit = ocl_edit;
    }

    private onSwipeTouchListener ostl = new onSwipeTouchListener(getActivity()) {
        @Override
        public void onSwipeLeft() {
            super.onSwipeLeft();

            if(ll_optionbody.getX() > 0f && inAnimatedState) {
                ll_optionbody.requestLayout();
                ll_optionbody.animate().translationX(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        inAnimatedState = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ll_optionbody.setZ(1f);
                        }
                    }
                });
            }
        }

        @Override
        public void onSwipeRight() {
            super.onSwipeRight();

            if(ll_optionbody.getX() < Math.abs(ll_optionbody.getWidth()) && !inAnimatedState) {
                ll_optionbody.animate().translationX(ll_optionbody.getWidth()-rl_hidden.getWidth()).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        inAnimatedState = true;
                        ll_optionbody.requestLayout();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ll_optionbody.setZ(1f);
                        }
                    }
                });
            }
        }
    };
}
