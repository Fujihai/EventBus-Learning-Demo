package liufushihai.project.stickyevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String STICKY_EVENT_MSG1 = "Sticky Event Msg-1";
    private String STICKY_EVENT_MSG2 = "Sticky Event Msg-2";
    private String STICKY_EVENT_MSG3 = "Sticky Event Msg-3";
    private String REGISTER_SUCCESSFUL = "Subscriber Registered Successfully!";
    private String UNREGISTER_SUCCESSFUL = "Subscriber Unregistered Successfully!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_send_sticky_event1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new StickyEventMsg(STICKY_EVENT_MSG1));
                Log.e(TAG, "onClick: " + STICKY_EVENT_MSG1);
            }
        });

        findViewById(R.id.btn_send_sticky_event2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new StickyEventMsg(STICKY_EVENT_MSG2));
                Log.e(TAG, "onClick: " + STICKY_EVENT_MSG2);
            }
        });

        findViewById(R.id.btn_send_sticky_event3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new StickyEventMsg(STICKY_EVENT_MSG3));
                Log.e(TAG, "onClick: " + STICKY_EVENT_MSG3);
            }
        });

        /*  订阅粘性事件 */
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventBus.getDefault().isRegistered(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "You had been registered!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onClick: " + REGISTER_SUCCESSFUL);
                    EventBus.getDefault().register(MainActivity.this);
                }
            }
        });

        /* 解除订阅 */
        findViewById(R.id.btn_unregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventBus.getDefault().isRegistered(MainActivity.this)) {
                    EventBus.getDefault().unregister(MainActivity.this);
                    Log.e(TAG, "onClick: " + UNREGISTER_SUCCESSFUL);
                } else {
                    Toast.makeText(MainActivity.this, "You are not registered!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* 移除最近的粘性事件 ，移除成功后，发布的粘性事件不会转到EventBus中处理了， 订阅者也不会收到粘性事件了*/
        findViewById(R.id.btn_remove_sticky_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StickyEventMsg stickyEventMsg = EventBus.getDefault().removeStickyEvent(StickyEventMsg.class);
                if (stickyEventMsg != null) {
                    EventBus.getDefault().removeStickyEvent(stickyEventMsg);          //移除最近的粘性事件
                    //EventBus.getDefault().removeAllStickyEvents();                              //移除所有的粘性事件
                    //EventBus.getDefault().removeStickyEvent(StickyEventMsg.class);  //移除类型为StickyEventMsg的粘性事件
                    Log.e(TAG, "onClick: " + "last event : " + stickyEventMsg.getMsg());
                    Log.e(TAG, "The latest sticky event had been removed!");
                }
            }
        });
    }

    /**
     * 当点击注册按钮时，此方法就会接收到最近的粘性事件
     *
     * @param eventMsg
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventMsg(StickyEventMsg eventMsg) {
        Log.e(TAG, "onStickyEventMsg: " + eventMsg.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
