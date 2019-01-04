/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.wei.control;

import cn.hanbell.eap.web.SuperMultiBean;
import cn.hanbell.wei.ejb.QuestionCategoryBean;
import cn.hanbell.wei.ejb.QuestionBean;
import cn.hanbell.wei.ejb.QuestionDetailBean;
import cn.hanbell.wei.entity.QuestionCategory;
import cn.hanbell.wei.entity.Question;
import cn.hanbell.wei.entity.QuestionDetail;
import cn.hanbell.wei.lazy.QuestionModel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class QuestionManagedBean extends SuperMultiBean<Question, QuestionDetail> {

    @EJB
    private QuestionCategoryBean categoryBean;
    @EJB
    private QuestionBean questionBean;
    @EJB
    private QuestionDetailBean questionDetailBean;

    protected List<QuestionCategory> categoryList;

    public QuestionManagedBean() {
        super(Question.class, QuestionDetail.class);
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
        String name;
        JsonArrayBuilder jab;
        JsonObjectBuilder job;
        if (categoryList == null || categoryList.isEmpty()) {
            categoryList = categoryBean.findAll();
        }
        //产生分类问题
        for (QuestionCategory category : categoryList) {
            setEntityList(null);
            setEntityList(questionBean.findByCategoryId(category.getId()));
            if (entityList != null && !entityList.isEmpty()) {
                jab = questionBean.createJsonArrayBuilder(entityList);
                name = category.getClassname() + ".json";
                buildJsonFile(jab.build(), getAppDataPath(), name);
            }
        }
        Map<String, Object> filters = new HashMap<>();
        Map<String, String> sorts = new LinkedHashMap<>();
        //产生最新问题(已审核)
        filters.clear();
        filters.put("status", "V");
        filters.put("ontop", true);
        sorts.put("id", "DESC");
        setEntityList(questionBean.findByFilters(filters, 0, 8, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = questionBean.createJsonArrayBuilder(this.entityList);
            buildJsonFile(jab.build(), getAppDataPath(), "questiontop.json");
        }
        //产生全部问题系列(已审核)
        filters.clear();
        sorts.clear();
        filters.put("status", "V");
        sorts.put("ontop", "DESC");
        sorts.put("id", "DESC");
        setEntityList(questionBean.findByFilters(filters, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = questionBean.createJsonArrayBuilder(this.entityList);
            buildJsonFile(jab.build(), getAppDataPath(), "question.json");
        }
        //产生单个问题系列信息(已审核)
        if (this.entityList != null && !this.entityList.isEmpty()) {
            for (Question entity : entityList) {
                job = questionBean.createJsonObjectBuilder(entity);
                name = "question" + entity.getId().toString() + ".json";
                buildJsonFile(job.build(), getAppDataPath(), name);
            }
        }
        showInfoMsg("Info", "发布成功");
    }

    @Override
    public void create() {
        super.create();
        newEntity.setOntop(false);
        newEntity.setHits(0);
    }

    @Override
    public void handleFileUploadWhenNew(FileUploadEvent event) {
        super.handleFileUploadWhenNew(event);
        if (this.fileName != null && this.newEntity != null) {
            this.newEntity.setImgfile(fileName);
        }
    }

    @Override
    public void handleFileUploadWhenEdit(FileUploadEvent event) {
        super.handleFileUploadWhenEdit(event);
        if (this.fileName != null && this.currentEntity != null) {
            this.currentEntity.setImgfile(fileName);
        }
    }

    public void handleFileUploadWhenDetailEdit(FileUploadEvent event) {
        super.handleFileUploadWhenEdit(event);
        if (this.fileName != null && this.currentDetail != null) {
            this.currentDetail.setImgfile(fileName);
        }
    }

    @Override
    public void init() {
        this.superEJB = questionBean;
        this.detailEJB = questionDetailBean;
        setModel(new QuestionModel(questionBean));
        categoryList = categoryBean.findAll();
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null) {
            this.model.getFilterFields().clear();
            if (this.queryName != null && !"".equals(this.queryName)) {
                model.getFilterFields().put("title", queryName);
            }
            if (this.queryState != null && !"ALL".equals(this.queryState)) {
                model.getFilterFields().put("status", queryState);
            }
            if (this.queryDateBegin != null) {
                model.getFilterFields().put("credateBegin", queryDateBegin);
            }
            if (this.queryDateEnd != null) {
                model.getFilterFields().put("credateEnd", queryDateEnd);
            }
        }
    }

    /**
     * @return the categoryList
     */
    public List<QuestionCategory> getCategoryList() {
        return categoryList;
    }

    /**
     * @param categoryList the categoryList to set
     */
    public void setCategoryList(List<QuestionCategory> categoryList) {
        this.categoryList = categoryList;
    }

}
