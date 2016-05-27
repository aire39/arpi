package techcurb.arpi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import techcurb.database.ProjectModel;
import techcurb.navs.NavDrawer;
import techcurb.widgets.WidgetProjectsButton;

public class ProjectActivity extends NavDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView called from NavDrawer class

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(ocl_fab);
        }

        TextView tv_toolbar_title = (TextView) findViewById(R.id.app_bar_main_toolbar_title);
        if(tv_toolbar_title != null)
            tv_toolbar_title.setText("P r o j e c t s");

        ProjectModel m_db = new ProjectModel(this);
        HashMap<String, ArrayList<String>> table = m_db.getAllProjects();
        for (Map.Entry<String, ArrayList<String>> entry: table.entrySet()) {

            ArrayList<String> data = entry.getValue();
            Integer id     = Integer.valueOf(data.get(0));
            String p_name = data.get(1);
            String h_name = data.get(2);
            String t_name = data.get(3);

            final WidgetProjectsButton widget_btn_project = new WidgetProjectsButton();
            widget_btn_project.setWidget(p_name, h_name, t_name, id);
            widget_btn_project.setPickedProjectListener(ocl_choose_project);
            widget_btn_project.setDeleteListener(ocl_delete_project);
            widget_btn_project.setEditListener(ocl_edit_project);

            getSupportFragmentManager().beginTransaction().add(R.id.project_ll_content_sub, widget_btn_project).commit();
        }
    }

    private View.OnClickListener ocl_choose_project = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String project_name = ((TextView)v.findViewById(R.id.widget_project_tv_projectname)).getText().toString();
            Toast.makeText(ProjectActivity.this, project_name, Toast.LENGTH_LONG).show();

            //Intent intent_projectrun = new Intent(ProjectActivity.this, ProjectRunActivity.class);
            //intent_projectrun.putExtra("project_id", v.getId());
            //startActivity(intent_projectrun);
            //finish();
        }
    };

    private View.OnClickListener ocl_edit_project = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ProjectActivity.this, "editor", Toast.LENGTH_LONG).show();

            //Intent intent_edit = new Intent(ProjectActivity.this, EditorActivity.class);
            //intent_edit.putExtra("project_id", v.getId());
            //startActivity(intent_projectrun);
            //finish();
        }
    };

    private View.OnClickListener ocl_delete_project = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ProjectActivity.this, "delete", Toast.LENGTH_LONG).show();

            final Integer project_id = v.getId();
            AlertDialog.Builder ad_confirm = new AlertDialog.Builder(ProjectActivity.this);
            ad_confirm.setMessage("Are you sure?").setTitle("Delete Project").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ProjectModel m_db = new ProjectModel(ProjectActivity.this);
                    m_db.deleteProject(project_id);

                    Intent intent_project = new Intent(ProjectActivity.this, ProjectActivity.class);
                    startActivity(intent_project);
                    finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();


        }
    };

    private View.OnClickListener ocl_fab = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_project_intent = new Intent(getApplicationContext(), AddProjectActivity.class);
            add_project_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(add_project_intent);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

}
