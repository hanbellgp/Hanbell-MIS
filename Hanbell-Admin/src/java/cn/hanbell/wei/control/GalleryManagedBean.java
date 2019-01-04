/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.wei.control;

import cn.hanbell.eap.web.SuperMultiBean;
import cn.hanbell.wei.ejb.GalleryCategoryBean;
import cn.hanbell.wei.ejb.GalleryBean;
import cn.hanbell.wei.ejb.GalleryDetailBean;
import cn.hanbell.wei.entity.GalleryCategory;
import cn.hanbell.wei.entity.Gallery;
import cn.hanbell.wei.entity.GalleryDetail;
import cn.hanbell.wei.lazy.GalleryModel;
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
public class GalleryManagedBean extends SuperMultiBean<Gallery, GalleryDetail> {

    @EJB
    private GalleryCategoryBean categoryBean;
    @EJB
    private GalleryBean galleryBean;
    @EJB
    private GalleryDetailBean galleryDetailBean;

    protected List<GalleryCategory> categoryList;

    public GalleryManagedBean() {
        super(Gallery.class, GalleryDetail.class);
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
        //产生分类产品
        for (GalleryCategory category : categoryList) {
            setEntityList(null);
            setEntityList(galleryBean.findByCategoryId(category.getId()));
            if (!entityList.isEmpty()) {
                jab = galleryBean.createJsonArrayBuilder(entityList);
                name = category.getClassname() + ".json";
                buildJsonFile(jab.build(), getAppDataPath(), name);
            }
        }
        Map<String, Object> filters = new HashMap<>();
        Map<String, String> sorts = new LinkedHashMap<>();
        sorts.put("id", "DESC");
        //产生首页展示产品
        filters.put("status", "V");
        filters.put("homeadv", true);
        setEntityList(galleryBean.findByFilters(filters, 0, 8, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = galleryBean.createJsonArrayBuilder(this.entityList);
            buildJsonFile(jab.build(), getAppDataPath(), "homeadv.json");
        }
        //产生最新产品(已审核)
        filters.clear();
        filters.put("status", "V");
        filters.put("ontop", true);
        sorts.put("id", "DESC");
        setEntityList(galleryBean.findByFilters(filters, 0, 8, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = galleryBean.createJsonArrayBuilder(this.entityList);
            buildJsonFile(jab.build(), getAppDataPath(), "gallerytop.json");
        }
        //产生全部产品系列(已审核)
        filters.clear();
        filters.put("status", "V");
        setEntityList(galleryBean.findByFilters(filters, sorts));
        if (this.entityList != null && !this.entityList.isEmpty()) {
            jab = galleryBean.createJsonArrayBuilder(this.entityList);
            buildJsonFile(jab.build(), getAppDataPath(), "gallery.json");
        }
        //产生单个产品系列信息(已审核)
        if (this.entityList != null && !this.entityList.isEmpty()) {
            for (Gallery entity : entityList) {
                job = galleryBean.createJsonObjectBuilder(entity);
                name = "gallery" + entity.getId().toString() + ".json";
                buildJsonFile(job.build(), getAppDataPath(), name);
            }
        }
        showInfoMsg("Info", "发布成功");
    }

    @Override
    public void create() {
        super.create();
        newEntity.setOntop(false);
        newEntity.setHomeadv(false);
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
        this.superEJB = galleryBean;
        this.detailEJB = galleryDetailBean;
        setModel(new GalleryModel(galleryBean));
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
    public List<GalleryCategory> getCategoryList() {
        return categoryList;
    }

    /**
     * @param categoryList the categoryList to set
     */
    public void setCategoryList(List<GalleryCategory> categoryList) {
        this.categoryList = categoryList;
    }

}
