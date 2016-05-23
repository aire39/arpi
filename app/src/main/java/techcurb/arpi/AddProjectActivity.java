package techcurb.arpi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by seanx_000 on 5/22/2016.
 *
 */
public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_hardware_item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        Button btn_next = (Button) findViewById(R.id.addproject_btn_next);
        if (btn_next != null) {
            btn_next.setOnClickListener(this);
        }

        tv_hardware_item = (TextView) findViewById(R.id.addproject_tv_hardware_item);
        if (tv_hardware_item != null) {
            tv_hardware_item.setOnClickListener(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Activity.RESULT_OK == resultCode) {
            if (data.getStringExtra("c_hardware") != null) {
                Log.i("Choose Hardware", data.getStringExtra("c_hardware"));
                Toast.makeText(AddProjectActivity.this, data.getStringExtra("c_hardware"), Toast.LENGTH_LONG).show();
                tv_hardware_item.setText( data.getStringExtra("c_hardware") );

                if( data.getStringExtra("c_hardware").equals("Raspberry Pi"))
                {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Drawable icon = getApplicationContext().getResources().getDrawable(R.mipmap.ic_logo_pi, null);
                        tv_hardware_item.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                    }
                }
                else if( data.getStringExtra("c_hardware").equals("Arduino"))
                {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Drawable icon = getApplicationContext().getResources().getDrawable(R.mipmap.ic_logo_arduino, null);
                        tv_hardware_item.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                    }
                }
            }
        }
        else
        {
            Log.i("Choose Hardware", "Canceled!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent project_intent = new Intent(getApplicationContext(), ProjectActivity.class);
        project_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(project_intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        if( v.getId() == R.id.addproject_btn_next)
        {
            Toast.makeText(AddProjectActivity.this, "To Editor Activity", Toast.LENGTH_LONG).show();
            //Intent editor_intent = new Intent(AddProjectActivity.this, EditorActivity.class);
            //startActivity(editor_intent);
            //finish();
        }
        else if(v.getId() == R.id.addproject_tv_hardware_item)
        {
            Intent hardwarelist_intent = new Intent(AddProjectActivity.this, HardwareListActivity.class);
            startActivityForResult(hardwarelist_intent, 1);
        }
    }
}
