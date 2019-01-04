/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import com.lightshell.comm.BaseLib;
import com.lightshell.comm.GraphicCode;
import cn.hanbell.eap.ejb.SystemUserBean;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.eap.lazy.SystemUserModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Pattern;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "systemUserManagedBean")
@SessionScoped
public class SystemUserManagedBean extends SuperSingleBean<SystemUser> {

    @EJB
    private SystemUserBean systemUserBean;
    private GraphicCode graphicCode;
    private String mobile;
    private String username;
    //@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "电子邮件无效")
    private String mail;
    private String pwd;
    private String verifyCode;
    private String verifyInput;
    private StreamedContent graphicContent;
    private String graphicString;
    private int count;//验证码发送次数

    public SystemUserManagedBean() {
        super(SystemUser.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setStatusToNew();
        newEntity.setCreatorToSystem();
        newEntity.setCredateToNow();
        newEntity.setOptuser(null);
        newEntity.setOptdate(null);
        setCurrentEntity(newEntity);
    }

    @Override
    protected boolean doBeforePersist() throws Exception {
        newEntity.setUserid(mobile);
        newEntity.setUsername(username);
        newEntity.setEmail(mail);
        newEntity.setPassword(BaseLib.securityMD5(pwd));
        newEntity.setSuperuser(Boolean.FALSE);
        newEntity.setFailure(0);
        newEntity.setLocked(Boolean.FALSE);
        return true;
    }

    @Override
    protected boolean doBeforeUpdate() throws Exception {
        if (currentEntity != null && this.pwd != null && !"".equals(this.pwd)) {
            currentEntity.setPassword(BaseLib.securityMD5(pwd));
        }
        return super.doBeforeUpdate();
    }

    @Override
    public String edit(String path) {
        if (currentEntity != null) {
            this.mobile = currentEntity.getUserid();
            this.username = currentEntity.getUsername();
        }
        return super.edit(path); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        setSuperEJB(systemUserBean);
        setModel(new SystemUserModel(systemUserBean));
        model.getSortFields().put("userid", "ASC");
        try {
            graphicCode = new GraphicCode();
            graphicCode.build();
            graphicContent = graphicCode.getContent();
        } catch (IOException ex) {
            Logger.getLogger(SystemUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (verifyCode.equals(verifyInput)) {
            return event.getNewStep();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "验证码错误"));
        }
        return event.getOldStep();
    }

    @Override
    public void pull() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void query() {
        if (model != null) {
            model.getFilterFields().clear();
            if (queryFormId != null && !"".equals(queryFormId)) {
                model.getFilterFields().put("userid", queryFormId);
            }
            if (queryName != null && !"".equals(queryName)) {
                model.getFilterFields().put("username", queryName);
            }
        }
    }

    public void sendGraphicCode() {
        if (graphicCode == null) {
            graphicCode = new GraphicCode();
        }
        try {
            graphicCode.build();
            graphicContent = graphicCode.getContent();
        } catch (IOException ex) {
            Logger.getLogger(SystemUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the verifyInput
     */
    public String getVerifyInput() {
        return verifyInput;
    }

    /**
     * @param verifyInput the verifyInput to set
     */
    public void setVerifyInput(String verifyInput) {
        this.verifyInput = verifyInput;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the graphicContent
     */
    public StreamedContent getGraphicContent() {
        return graphicContent;
    }

    /**
     * @return the graphicString
     */
    public String getGraphicString() {
        return graphicString;
    }

}
