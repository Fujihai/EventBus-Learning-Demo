package liufushihai.project.threadmode.Events;

/**
 * Welcome to email to me anytime !
 * Author         :  liufushihai
 * Time             :  2018/1/24 21:01
 * Email            :  liufushihai@163.com
 * Description  : 线程模式【POSTING】事件类
 */

public class PostingMsgEvent {
    private String msg;

    public PostingMsgEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
