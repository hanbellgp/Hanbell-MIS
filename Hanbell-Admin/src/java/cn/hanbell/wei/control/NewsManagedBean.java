/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.wei.control;

import cn.hanbell.eap.web.SuperMultiBean;
import cn.hanbell.wei.ejb.NewsBean;
import cn.hanbell.wei.ejb.NewsDetailBean;
import cn.hanbell.wei.entity.News;
import cn.hanbell.wei.entity.NewsDetail;
import cn.hanbell.wei.lazy.NewsModel;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class NewsManagedBean extends SuperMultiBean<News, NewsDetail> {

    @EJB
    private NewsDetailBean newsDetailBean;

    @EJB
    private NewsBean newsBean;

    public NewsManagedBean() {
        super(News.class, NewsDetail.class);
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
        Map<String, Object> filters = new HashMap<>();
        Map<String, String> sorts = new LinkedHashMap<>();
        filters.put("status", "V");
        filters.put("ontop", true);
        sorts.put("ontop", "DESC");
        sorts.put("id", "DESC");
        //首页活动(4个)
        setEntityList(newsBean.findByFilters(filters, 0, 4, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = newsBean.createJsonArrayBuilder(this.entityList);
            this.buildJsonFile(jab.build(), getAppDataPath(), "homenews.json");
        }
        //置顶活动(8个)
        entityList.clear();
        setEntityList(newsBean.findByFilters(filters, 0, 8, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = newsBean.createJsonArrayBuilder(this.entityList);
            this.buildJsonFile(jab.build(), getAppDataPath(), "newstop.json");
        }
        //所有活动(已审核)
        filters.clear();
        filters.put("status", "V");
        entityList.clear();
        setEntityList(newsBean.findByFilters(filters, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = newsBean.createJsonArrayBuilder(this.entityList);
            this.buildJsonFile(jab.build(), getAppDataPath(), "news.json");
        }
        //单个活动(已审核)
        JsonObjectBuilder job;
        if (this.entityList != null && !this.entityList.isEmpty()) {
            for (News entity : entityList) {
                job = newsBean.createJsonObjectBuilder(entity);
                name = "news" + entity.getId().toString() + ".json";
                this.buildJsonFile(job.build(), getAppDataPath(), name);
            }
        }
        showInfoMsg("Info", "发布成功");
    }

    @Override
    public void create() {
        super.create();
        newEntity.setOntop(true);
        newEntity.setHits(0);
        newEntity.setCreator(this.userManagedBean.getCurrentUser().getUsername());
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
        this.superEJB = newsBean;
        this.detailEJB = newsDetailBean;
        setModel(new NewsModel(newsBean));
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
}
