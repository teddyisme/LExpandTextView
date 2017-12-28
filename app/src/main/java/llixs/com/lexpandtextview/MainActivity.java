package llixs.com.lexpandtextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((com.lixs.expandtextview.ExpandTextView) findViewById(R.id.expandview)).setText("敖德萨所大所大所多" +
                "阿萨大所打死打死大所多" +
                "   安达市大所大所大所大所大所大所大所大所大所大所大所多安达市大所大所大所大所大所大所大所大所大所大所大所多安达市大所大所大所大所大所大所大所大所大所大所大所多安达市大所大所大所大所大所大所大所大所大所大所大所多安达市大所大所大所大所大所大所大所大所大所大所大所多安达市大所大所大所大所大所大所大所大所大所大所大所多", true);

    }
}
