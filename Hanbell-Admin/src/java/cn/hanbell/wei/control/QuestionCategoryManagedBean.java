/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.wei.control;

import cn.hanbell.eap.web.SuperSingleBean;
import cn.hanbell.wei.ejb.QuestionCategoryBean;
import cn.hanbell.wei.entity.QuestionCategory;
import cn.hanbell.wei.lazy.QuestionCategoryModel;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class QuestionCategoryManagedBean extends SuperSingleBean<QuestionCategory> {

    @EJB
    private QuestionCategoryBean categoryBean;

    public QuestionCategoryManagedBean() {
        super(QuestionCategory.class);
    }

    @Override
    public String getAppImgPath() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext().getInitParameter("cn.hanbell.wx.web.appimgpath");
    }

    @Override
    public String getAppDataPath() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext().getInitParameter("cn.hanbell.wx.web.appdatapath");
    }

    @Override
    protected void buildJsonArray() {
        JsonArrayBuilder jab;
        setEntityList(categoryBean.findAll());
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = categoryBean.createJsonArrayBuilder(this.entityList);
            this.buildJsonFile(jab.build(), getAppDataPath(), "questioncategory.json");
            showInfoMsg("Info", "发布成功");
        }
    }

    @Override
    public void init() {
        this.superEJB = categoryBean;
        setModel(new QuestionCategoryModel(categoryBean));
        super.init();
    }

}
