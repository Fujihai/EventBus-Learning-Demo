package liufushihai.project.prioritiesandeventcancellation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSendEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("Hello Subscriber"));
            }
        });
    }

    @Subscribe(priority = 100)
    public void onMessageMsg1(MessageEvent event) {
        Log.e(TAG, "priority : 100 - " + "onMessageMsg1: " + event.getMsg());
    }

    @Subscribe(priority = 50)
    public void onMessageMsg2(MessageEvent event) {
        Log.e(TAG, "priority : 50 - " + "onMessageMsg2: " + event.getMsg());
        /* 取消EventBus中对该事件的分发，比当前方法优先级低的订阅者将收不到该事件 */
        EventBus.getDefault().cancelEventDelivery(event);
    }

    @Subscribe(priority = 30)
    public void onMessageMsg3(MessageEvent event) {
        Log.e(TAG, "priority : 30 - " + "onMessageMsg3: " + event.getMsg());
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
