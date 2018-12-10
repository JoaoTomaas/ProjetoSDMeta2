package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.MusicBean;
import org.apache.struts2.interceptor.SessionAware;
import java.rmi.RemoteException;
import java.util.Map;



public class MakeEditorAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String newEditor = null;



    @Override
    public String execute() {
        // any username is accepted without confirmation (should check using RMI)
        if(this.newEditor != null && !newEditor.equals("")  ) {

            this.getMusicBean().setUsername((String) session.get("username"));
            this.getMusicBean().setNewEditor(this.newEditor);
            //this.getRegisterBean().setPassword(this.password);

            try {
                if(this.getMusicBean().makeEditor()) {
                    // nao preciso de por na sessao acho eu
                    session.put("newEditor", newEditor);
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



    public String ver (){
        return SUCCESS;
    }

    /*public void setPassword(String password) {
        this.password = password; // what about this input?
    }*/

    public MusicBean getMusicBean() {
        if(!session.containsKey("musicBean"))
            this.setMusicBean(new MusicBean());
        return (MusicBean) session.get("musicBean");
    }

    public void setMusicBean(MusicBean musicBean) {
        this.session.put("musicBean", musicBean);
    }
/*
    public String getNewEditor(){
        return this.newEditor;
    }
*/
    public void setNewEditor(String newEditor){
        this.newEditor=newEditor;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }


}
