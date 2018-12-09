package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.RegisterBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class RegisterAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String username = null, password = null;



    @Override
    public String execute() {
        // any username is accepted without confirmation (should check using RMI)
        if(this.username != null && !username.equals("")  ) {
            this.getRegisterBean().setUsername(this.username);
            this.getRegisterBean().setPassword(this.password);

            try {
                if(this.getRegisterBean().registo()) {
                    session.put("username", username);
                    //session.put("loggedin", true); // this marks the user as logged in
                    return SUCCESS;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            // deve ser login ou fail aqui?
            return LOGIN;
        }
        else
            return LOGIN;
    }

    public void setUsername(String username) {
        this.username = username; // will you sanitize this input? maybe use a prepared statement?
    }

    public String ver (){
        return SUCCESS;
    }

    public void setPassword(String password) {
        this.password = password; // what about this input?
    }

    public RegisterBean getRegisterBean() {
        if(!session.containsKey("registerBean"))
            this.setRegisterBean(new RegisterBean());
        return (RegisterBean) session.get("registerBean");
    }

    public void setRegisterBean(RegisterBean registerBean) {
        this.session.put("registerBean", registerBean);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
