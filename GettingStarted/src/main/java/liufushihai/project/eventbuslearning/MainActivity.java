package liufushihai.project.eventbuslearning;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private int count = 0;
    ContentLoadingProgressBar progressBar;
    TextView textRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbar);
        textRadio = findViewById(R.id.text_radio);

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (count <= 100) {
                            count = (count >= 100) ? 0 : (count += 10);
                            Log.e(TAG, ">>>> count:  "+count );
                            EventBus.getDefault().post(new MessageEvent(count));    //发布消息
                            try {
                                Thread.sleep(250);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        progressBar.setProgress(event.getMsg());
        textRadio.setText(event.getMsg() + "%");
        Log.e(TAG, "onMessageEvent: "+event.getMsg() );

    }

}
