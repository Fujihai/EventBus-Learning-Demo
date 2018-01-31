package liufushihai.project.threadmode.Events;

/**
 * Welcome to email to me anytime !
 * Author         :  liufushihai
 * Time             :  2018/1/24 21:02
 * Email            :  liufushihai@163.com
 * Description  : 线程模式【MAIN】事件类
 */

public class MainMsgEvent {

    private String msg;

    public MainMsgEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
