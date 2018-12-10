package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.RegisterBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

import static com.opensymphony.xwork2.Action.*;

public class SearchMusicAction extends ActionSupport implements SessionAware{
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    //private String search_type = "music", word = "feels like summer";
    private String search_type = null, word = null;


    @Override
    public String execute() {

        this.getRegisterBean().setSearch_type(this.search_type);
        this.getRegisterBean().setWord(this.word);
        //System.out.println("Mae estou aqui");

        try {
            if (this.getRegisterBean().getSearchInfo() != null) {
                return SUCCESS;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return LOGIN;
    }



    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    public void setWord(String word) {
        this.word = word;
    }




    public RegisterBean getRegisterBean(){
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
