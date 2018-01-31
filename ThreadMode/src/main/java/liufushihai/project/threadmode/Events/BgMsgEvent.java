package liufushihai.project.threadmode.Events;

/**
 * Welcome to email to me anytime !
 * Author         :  liufushihai
 * Time             :  2018/1/24 21:03
 * Email            :  liufushihai@163.com
 * Description  :  线程模式【BACKGROUND】事件类
 */

public class BgMsgEvent {
    private String msg;

    public BgMsgEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
