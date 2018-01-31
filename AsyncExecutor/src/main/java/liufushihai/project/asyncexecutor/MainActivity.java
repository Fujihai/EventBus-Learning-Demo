package liufushihai.project.asyncexecutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.AsyncExecutor;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建AsyncExecutor实例
               AsyncExecutor.create().execute(new AsyncExecutor.RunnableEx() {
                   @Override
                   public void run() throws Exception {
                       Log.e(TAG, "run: " + "Event Running Normally！");
                       EventBus.getDefault().post(new LogEvent("Normal Event!"));
                   }
               });
            }
        });

        findViewById(R.id.btn_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncExecutor.create().execute(new AsyncExecutor.RunnableEx() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "run: " + "Event Running Failed！");
                        Exception exception = new ArrayIndexOutOfBoundsException();
                        throw  exception;   //抛出异常
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleNormalEvent(LogEvent event){
        //do something
        Log.e(TAG, "handleNormalEvent: " + event.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleFailureEvent(ThrowableFailureEvent event){
        Log.e(TAG, "handleFailureEvent: " + event.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
