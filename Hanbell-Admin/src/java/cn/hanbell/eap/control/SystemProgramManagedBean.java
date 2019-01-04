/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.SystemModuleBean;
import cn.hanbell.eap.ejb.SystemNameBean;
import cn.hanbell.eap.ejb.SystemProgramBean;
import cn.hanbell.eap.entity.SystemModule;
import cn.hanbell.eap.entity.SystemName;
import cn.hanbell.eap.entity.SystemProgram;
import cn.hanbell.eap.lazy.SystemProgramModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "systemProgramManagedBean")
@SessionScoped
public class SystemProgramManagedBean extends SuperSingleBean<SystemProgram> {

    @EJB
    private SystemNameBean systemNameBean;
    @EJB
    private SystemModuleBean sysmoduleBean;

    private List<SystemName> systemNameList;
    private List<SystemModule> systemModuleList;

    private String querySystemName;

    public SystemProgramManagedBean() {
        super(SystemProgram.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setSortid(1);
        newEntity.setNoauto(Boolean.FALSE);
        newEntity.setNochange(Boolean.FALSE);
        newEntity.setDoadd(true);
        newEntity.setDoedit(true);
        newEntity.setDodel(true);
        newEntity.setDoprt(Boolean.FALSE);
        newEntity.setDocfm(true);
        newEntity.setDouncfm(true);
    }

    @Override
    public String edit(String path) {
        systemModuleList = sysmoduleBean.findAll();
        return super.edit(path);
    }

    @Override
    public void init() {
        this.superEJB = sysprgBean;
        setModel(new SystemProgramModel(sysprgBean));
        systemNameList = systemNameBean.findAll();
        systemModuleList = sysmoduleBean.findAll();
        super.init();
        if (currentEntity == null) {
            currentEntity = newEntity;
        }
    }

    @Override
    public void query() {
        this.model.getFilterFields().clear();
        if (this.queryFormId != null && !"".equals(this.queryFormId)) {
            this.model.getFilterFields().put("api", this.queryFormId);
        }
        if (this.queryName != null && !"".equals(this.queryName)) {
            this.model.getFilterFields().put("name", this.queryName);
        }
        if (this.querySystemName != null && !"".equals(this.querySystemName)) {
            this.model.getFilterFields().put("sysname", this.querySystemName);
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

    public void handleSystemNameChangedWhenEdit(SelectEvent event) {
        if (event.getObject() != null && currentEntity != null) {
            Object item = event.getObject();
            systemModuleList.clear();
            systemModuleList = sysmoduleBean.findBySystemName(item.toString());
            currentEntity.setSystemModule(null);
        }
    }

    public void handleSystemNameChangedWhenNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object item = event.getObject();
            systemModuleList.clear();
            systemModuleList = sysmoduleBean.findBySystemName(item.toString());
            newEntity.setSystemModule(null);
        }
    }

    /**
     * @return the systemNameList
     */
    public List<SystemName> getSystemNameList() {
        return systemNameList;
    }

    /**
     * @return the systemModuleList
     */
    public List<SystemModule> getSystemModuleList() {
        return systemModuleList;
    }

    /**
     * @return the querySystemName
     */
    public String getQuerySystemName() {
        return querySystemName;
    }

    /**
     * @param querySystemName the querySystemName to set
     */
    public void setQuerySystemName(String querySystemName) {
        this.querySystemName = querySystemName;
    }

}
