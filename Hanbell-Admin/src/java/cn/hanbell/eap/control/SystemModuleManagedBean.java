/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.SystemModuleBean;
import cn.hanbell.eap.ejb.SystemNameBean;
import cn.hanbell.eap.entity.SystemModule;
import cn.hanbell.eap.entity.SystemName;
import cn.hanbell.eap.lazy.SystemModuleModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "systemModuleManagedBean")
@SessionScoped
public class SystemModuleManagedBean extends SuperSingleBean<SystemModule> {

    @EJB
    private SystemNameBean systemNameBean;
    @EJB
    private SystemModuleBean sysmoduleBean;

    private List<SystemName> systemNameList;

    public SystemModuleManagedBean() {
        super(SystemModule.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setSortid(1);
    }

    @Override
    public void init() {
        this.superEJB = sysmoduleBean;
        setModel(new SystemModuleModel(sysmoduleBean));
        model.getSortFields().put("sortid", "ASC");
        systemNameList = systemNameBean.findAll();
        super.init();
        if (currentEntity == null) {
            currentEntity = newEntity;
        }
    }

    @Override
    public void query() {
        this.model.getFilterFields().clear();
        if (this.queryName != null && !"".equals(this.queryName)) {
            this.model.getFilterFields().put("name", this.queryName);
        }
        if (this.queryState != null && !"ALL".equals(this.queryState)) {
            this.model.getFilterFields().put("status", this.queryState);
        }
    }

    @Override
    protected void setToolBar() {
        if (currentEntity != null && currentEntity.getStatus() != null) {
            switch (currentEntity.getStatus()) {
                case "V":
                    this.doEdit = false;
                    this.doDel = false;
                    this.doCfm = false;
                    this.doUnCfm = true;
                    break;
                default:
                    this.doEdit = true;
                    this.doDel = true;
                    this.doCfm = true;
                    this.doUnCfm = false;
            }
        } else {
            this.doEdit = false;
            this.doDel = false;
            this.doCfm = false;
            this.doUnCfm = false;
        }
    }

    /**
     * @return the systemNameList
     */
    public List<SystemName> getSystemNameList() {
        return systemNameList;
    }

    /**
     * @param systemNameList the systemNameList to set
     */
    public void setSystemNameList(List<SystemName> systemNameList) {
        this.systemNameList = systemNameList;
    }

}
