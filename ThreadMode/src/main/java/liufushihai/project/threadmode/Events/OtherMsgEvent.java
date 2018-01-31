package liufushihai.project.threadmode.Events;

/**
 * Welcome to email to me anytime !
 * Author         :   liufushihai
 * Time             :  2018/1/26 21:07
 * Email            :  liufushihai@163.com
 * Description  :  EventBus事件类
 */

public class OtherMsgEvent {
    private String msg;

    public OtherMsgEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
