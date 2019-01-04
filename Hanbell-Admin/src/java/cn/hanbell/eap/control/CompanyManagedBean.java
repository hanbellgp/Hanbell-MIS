/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.CompanyBean;
import cn.hanbell.eap.entity.Company;
import cn.hanbell.eap.lazy.CompanyModel;
import cn.hanbell.eap.web.SuperSingleBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "companyManagedBean")
@SessionScoped
public class CompanyManagedBean extends SuperSingleBean<Company> {

    @EJB
    private CompanyBean companyBean;

    public CompanyManagedBean() {
        super(Company.class);
    }

    @Override
    public void init() {
        this.superEJB = companyBean;
        this.model = new CompanyModel(companyBean);
        super.init();
    }

}
