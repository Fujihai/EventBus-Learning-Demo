package liufushihai.project.prioritiesandeventcancellation;

/**
 * Welcome to email to me anytime !
 * Author         :  liufushihai
 * Time             :  2018/1/29 22:56
 * Email            :  liufushihai@163.com
 * Description  : EventBus事件类
 */

public class MessageEvent {
    private String msg;

    public MessageEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
