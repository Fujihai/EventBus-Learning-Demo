package liufushihai.project.threadmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import liufushihai.project.threadmode.Events.AsyncMsgEvent;
import liufushihai.project.threadmode.Events.BgMsgEvent;
import liufushihai.project.threadmode.Events.MainMsgEvent;
import liufushihai.project.threadmode.Events.MainOrderMsgEvent;
import liufushihai.project.threadmode.Events.OtherMsgEvent;
import liufushihai.project.threadmode.Events.PostingMsgEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //打印主线程id
        Log.e(TAG, "Main tid: " + android.os.Process.myTid());

        /* POSTING */
        findViewById(R.id.btnPosting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 直接在主线程中进行事件分发
                 */
//                Log.e(TAG, "Publisher tid: " + android.os.Process.myTid());
//                EventBus.getDefault().post(new PostingMsgEvent("Posting Msg"));

                /**
                 * 创建子线程进行事件分发
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Publisher tid : " + android.os.Process.myTid());
                        EventBus.getDefault().post(new PostingMsgEvent("Posting Msg"));
                    }
                }).start();
            }
        });

        /* MAIN */
        findViewById(R.id.btnMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 直接进行事件分发
                 */
//                Log.e(TAG, "Publisher tid : " + android.os.Process.myTid());
//                EventBus.getDefault().post(new MainMsgEvent("Main Msg"));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Publisher tid : " + android.os.Process.myTid());
                        EventBus.getDefault().post(new MainMsgEvent("Main Msg"));
                    }
                }).start();
            }
        });

        /* MAIN_ORDER */
        findViewById(R.id.btnMainOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 发送MainOrderMsgEvent 事件*/
                EventBus.getDefault().post(new MainOrderMsgEvent("Main Order Msg"));
            }
        });

        /* BACKGROUND */
        findViewById(R.id.btnBackGround).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 开启一个子线程进行发送
                 */
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e(TAG, "Other tid: " + android.os.Process.myTid());
//                        EventBus.getDefault().post(new BgMsgEvent("Bg Msg"));
//                    }
//                }).start();

                /**
                 * 在主线程发送
                 */
                Log.e(TAG, "UI tid: " + android.os.Process.myTid());
                EventBus.getDefault().post(new BgMsgEvent("Bg Msg"));
            }
        });

        /* ASYNC */
        findViewById(R.id.btnAsync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* 订阅者事件处理线程总是独立与事件分发线程与主线程，因此事件处理的tid与事件分发还有主线程必不相同 */

                /* 创建子线程来进行事件分发*/
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e(TAG, "Publisher tid:: " + android.os.Process.myTid() );
//                        EventBus.getDefault().post(new AsyncMsgEvent("Async Msg"));
//                    }
//                }).start();

                /* 直接在主线程进行事件分发 */
                Log.e(TAG, "Publisher tid:: " + android.os.Process.myTid());
                EventBus.getDefault().post(new AsyncMsgEvent("Async Msg"));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostMsgEvent(PostingMsgEvent event) {
        //打印接收到的消息以及订阅者方法所在的线程号
        Log.e(TAG, "Subscriber tid: " + android.os.Process.myTid());
        Log.e(TAG, "onPostMsgEvent: " + event.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainMsgEvent(MainMsgEvent event) {
        Log.e(TAG, "Subscriber tid: " + android.os.Process.myTid());
        Log.e(TAG, "onMainMsgEvent: " + event.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMainOrderMsgEvent(MainOrderMsgEvent event) {
        EventBus.getDefault().post(new OtherMsgEvent("Other Msg"));
        Log.e(TAG, "onMainOrderMsgEvent: " + " finished");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOtherMsgEvent(OtherMsgEvent event) {
        Log.e(TAG, "onOtherMsgEvent: " + " finished");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBgMsgEvent(BgMsgEvent event) {
        Log.e(TAG, "Subscriber tid: " + android.os.Process.myTid());
        Log.e(TAG, "onBgMsgEvent: " + event.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onAsyncMsgEvent(AsyncMsgEvent event) {
        Log.e(TAG, "Subscriber tid: " + android.os.Process.myTid());
        Log.e(TAG, "onAsyncMsgEvent: " + event.getMsg());
    }


    /**
     * 订阅者订阅
     */
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * 订阅者解除订阅
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
