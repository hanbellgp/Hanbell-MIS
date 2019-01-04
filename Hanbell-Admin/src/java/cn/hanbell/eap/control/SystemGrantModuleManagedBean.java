/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.SystemGrantModuleBean;
import cn.hanbell.eap.ejb.SystemModuleBean;
import cn.hanbell.eap.ejb.SystemUserBean;
import cn.hanbell.eap.entity.SystemGrantModule;
import cn.hanbell.eap.entity.SystemModule;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.eap.lazy.SystemGrantModuleModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "systemGrantModuleManagedBean")
@SessionScoped
public class SystemGrantModuleManagedBean extends SuperSingleBean<SystemGrantModule> {

    @EJB
    private SystemGrantModuleBean sysgrantModuleBean;
    @EJB
    private SystemUserBean systemUserBean;
    @EJB
    private SystemModuleBean sysmoduleBean;

    private List<SystemUser> systemUserList;
    private List<SystemModule> systemModuleList;

    /**
     * Creates a new instance of SystemGrantModuleManagedBean
     */
    public SystemGrantModuleManagedBean() {
        super(SystemGrantModule.class);
    }

    @Override
    protected void buildJsonObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void buildJsonArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        setSuperEJB(sysgrantModuleBean);
        setModel(new SystemGrantModuleModel(sysgrantModuleBean));
        setSystemUserList(systemUserBean.findAll());
        setSystemModuleList(sysmoduleBean.findAll());
        if (currentEntity == null) {
            currentEntity = newEntity;
        }
        super.init();
    }

    @Override
    public void pull() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setToolBar() {

    }

    /**
     * @return the systemUserList
     */
    public List<SystemUser> getSystemUserList() {
        return systemUserList;
    }

    /**
     * @param systemUserList the systemUserList to set
     */
    public void setSystemUserList(List<SystemUser> systemUserList) {
        this.systemUserList = systemUserList;
    }

    /**
     * @return the systemModuleList
     */
    public List<SystemModule> getSystemModuleList() {
        return systemModuleList;
    }

    /**
     * @param systemModuleList the systemModuleList to set
     */
    public void setSystemModuleList(List<SystemModule> systemModuleList) {
        this.systemModuleList = systemModuleList;
    }

}
