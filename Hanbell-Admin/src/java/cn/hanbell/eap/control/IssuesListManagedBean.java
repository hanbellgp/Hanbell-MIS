/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.IssuesListBean;
import cn.hanbell.eap.entity.IssuesList;
import cn.hanbell.eap.lazy.IssuesListModel;
import cn.hanbell.eap.web.SuperSingleBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author C1749
 */
@ManagedBean(name = "issuesListManagedBean")
@SessionScoped
public class IssuesListManagedBean extends SuperSingleBean<IssuesList> {

    @EJB
    private IssuesListBean issuesListBean;
    String person;//需求人
    String head;//资讯责任人

    public IssuesListManagedBean() {
        super(IssuesList.class);
    }

    @Override
    public void construct() {
        super.construct();
    }

    @Override
    public void init() {
        this.superEJB = issuesListBean;
        model = new IssuesListModel(issuesListBean);
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (queryFormId != null && !"".equals(queryFormId)) {
                this.model.getFilterFields().put("id", queryFormId);
            }
            if (queryName != null && !"".equals(queryName)) {
                this.model.getFilterFields().put("name", queryName);
            }
            if (person != null && !"".equals(person)) {
                this.model.getFilterFields().put("person", person);
            }
            if (head != null && !"".equals(head)) {
                this.model.getFilterFields().put("person", head);
            }
            if (queryDateBegin != null) {
                this.model.getFilterFields().put("plantime", queryDateBegin);
            }
            if (queryState != null && !"".equals(queryState)) {
                this.model.getFilterFields().put("status", queryState);
            }
        }
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public void handleFileUploadWhenNew(FileUploadEvent event) {
        super.handleFileUploadWhenNew(event); 
        if(this.fileName != null && this.newEntity != null){
             this.newEntity.setFile(fileName);
        }
    }
    
    

}
