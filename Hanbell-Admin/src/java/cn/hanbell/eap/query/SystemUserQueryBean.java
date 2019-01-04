/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.query;

import cn.hanbell.eap.ejb.SystemUserBean;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.eap.lazy.SystemUserModel;
import cn.hanbell.eap.web.SuperQueryBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "systemUserQueryBean")
@ViewScoped
public class SystemUserQueryBean extends SuperQueryBean<SystemUser> {

    @EJB
    private SystemUserBean systemUserBean;

    public SystemUserQueryBean() {
        super(SystemUser.class);
    }

    @Override
    public void init() {
        this.superEJB = systemUserBean;
        setModel(new SystemUserModel(systemUserBean));
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null) {
            this.model.getFilterFields().clear();
            if (this.queryFormId != null && !"".equals(this.queryFormId)) {
                this.model.getFilterFields().put("userid", this.queryFormId);
            }
            if (this.queryName != null && !"".equals(this.queryName)) {
                this.model.getFilterFields().put("username", this.queryName);
            }
        }
    }

}
