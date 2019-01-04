/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.query;

import cn.hanbell.eap.entity.SystemProgram;
import cn.hanbell.eap.lazy.SystemProgramModel;
import cn.hanbell.eap.web.SuperQueryBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "sysprgQueryBean")
@ViewScoped
public class SysprgQueryBean extends SuperQueryBean<SystemProgram> {

    private int moduleid = -1;

    public SysprgQueryBean() {
        super(SystemProgram.class);
    }

    @Override
    public void init() {
        setSuperEJB(sysprgBean);
        setModel(new SystemProgramModel(sysprgBean));
        params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (params != null) {
            if (params.containsKey("moduleid")) {
                moduleid = Integer.parseInt(params.get("moduleid")[0]);
                this.model.getFilterFields().put("systemModule.id", moduleid);
            }
        }
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null) {
            this.model.getFilterFields().clear();
            if (this.moduleid > -1) {
                this.model.getFilterFields().put("sysmodule.id", moduleid);
            }
        }
    }

}
