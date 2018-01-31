package liufushihai.project.eventbuslearning;

/**
 * Welcome to email to me anytime !
 * Author          :  liufushihai
 * Time             :  2018/1/16 22:42
 * Email            :  liufushihai@163.com
 * Description  :  EventBus事件类
 */

public class MessageEvent {
    private int msg;

    public MessageEvent(int msg) {
        this.msg = msg;
    }

    public int getMsg() {
        return msg;
    }
}
