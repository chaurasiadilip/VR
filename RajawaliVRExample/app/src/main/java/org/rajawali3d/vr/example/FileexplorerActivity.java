package org.rajawali3d.vr.example;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by Dileep on 1/4/2016.
 */
public class FileexplorerActivity extends Activity {

    private static final int REQUEST_PATH = 1;
    String curFileName;
    EditText edittext;
    Button submitBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileexplorer);
        edittext = (EditText)findViewById(R.id.editText);
        submitBtn=(Button)findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !edittext.getText().equals("")) {
                    Intent intent = new Intent(FileexplorerActivity.this, RajawaliVRExampleActivity.class);
                    System.out.println("FileexplorerActivity=" + edittext.getText());
                    String path=edittext.getText().toString();
                    intent.putExtra("paths", path);
                    startActivity(intent);
                }
            }
        });
    }

    public void getfile(View view){
        Intent intent1 = new Intent(this, FileChooser.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }
    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
                curFileName = data.getStringExtra("GetFileName");

                edittext.setText( data.getStringExtra("GetPath")+"/"+curFileName);
            }
        }
    }
}
