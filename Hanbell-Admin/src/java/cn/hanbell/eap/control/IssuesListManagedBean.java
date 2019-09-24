/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.IssuesListBean;
import cn.hanbell.eap.entity.Department;
import cn.hanbell.eap.entity.IssuesList;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.eap.lazy.IssuesListModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author C1749
 */
@ManagedBean(name = "issuesListManagedBean")
@SessionScoped
public class IssuesListManagedBean extends SuperSingleBean<IssuesList> {

    @EJB
    private IssuesListBean issuesListBean;
    private String issuesid;//需求名称
    private String issuesname;//需求名称
    private String deptno;//部门代号
    private String deptname;//部门名称
    private String neederid;//需求id
    private String needername;//需求人名称
    private String status;//状态

    public IssuesListManagedBean() {
        super(IssuesList.class);
    }

    @Override
    public void construct() {
        super.construct();
    }

    @Override
    public void init() {
        this.issuesid = "";
        this.issuesname = "";
        this.deptno = "";
        this.deptname = "";
        this.neederid = "";
        this.needername = "";
        this.status = "N";
        this.superEJB = issuesListBean;
        model = new IssuesListModel(issuesListBean);
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (issuesid != null && !"".equals(issuesid)) {
                this.model.getFilterFields().put("issuesid", issuesid);
            }
            if (issuesname != null && !"".equals(issuesname)) {
                this.model.getFilterFields().put("issuesname", issuesname);
            }
            if (deptno != null && !"".equals(deptno)) {
                this.model.getFilterFields().put("deptno", deptno);
            }
            if (deptname != null && !"".equals(deptname)) {
                this.model.getFilterFields().put("deptname", deptname);
            }
            if (neederid != null && !"".equals(neederid)) {
                this.model.getFilterFields().put("neederid", neederid);
            }
            if (needername != null && !"".equals(needername)) {
                this.model.getFilterFields().put("needername", needername);
            }
            if (status != null && !"".equals(status)) {
                this.model.getFilterFields().put("status", status);
            }
        }
    }

    @Override
    public void handleDialogReturnWhenNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Department e = (Department) event.getObject();
            newEntity.setDeptno(e.getDeptno());
            newEntity.setDeptname(e.getDept());
        }
    }

    @Override
    public void handleDialogReturnWhenEdit(SelectEvent event) {
        if (event.getObject() != null && currentEntity != null) {
            Department e = (Department) event.getObject();
            currentEntity.setDeptno(e.getDeptno());
            currentEntity.setDeptname(e.getDept());
        }
    }

    public void handleDialogReturnUserWhenDetailNeederNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            newEntity.setNeederid(user.getUserid());
            newEntity.setNeedername(user.getUsername());
        }
    }

    public void handleDialogReturnUserWhenDetailNeederEdit(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            currentEntity.setNeederid(user.getUserid());
            currentEntity.setNeedername(user.getUsername());
        }
    }

    public void handleDialogReturnUserWhenDetailprincipalNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            newEntity.setPrincipalid(user.getUserid());
            newEntity.setPrincipalname(user.getUsername());
        }
    }

    public void handleDialogReturnUserWhenDetailprincipalEdit(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            currentEntity.setPrincipalid(user.getUserid());
            currentEntity.setPrincipalname(user.getUsername());
        }
    }

    @Override
    public void handleFileUploadWhenNew(FileUploadEvent event) {
        super.handleFileUploadWhenNew(event);
        if (this.fileName != null && this.newEntity != null) {
            this.newEntity.setFile(fileName);
        }
    }

    @Override
    public void handleFileUploadWhenEdit(FileUploadEvent event) {
        super.handleFileUploadWhenEdit(event);
        if (this.fileName != null && this.currentEntity != null) {
            this.currentEntity.setFile(fileName);
        }
    }

    public IssuesListBean getIssuesListBean() {
        return issuesListBean;
    }

    public void setIssuesListBean(IssuesListBean issuesListBean) {
        this.issuesListBean = issuesListBean;
    }

    public String getIssuesid() {
        return issuesid;
    }

    public void setIssuesid(String issuesid) {
        this.issuesid = issuesid;
    }

    public String getIssuesname() {
        return issuesname;
    }

    public void setIssuesname(String issuesname) {
        this.issuesname = issuesname;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getNeederid() {
        return neederid;
    }

    public void setNeederid(String neederid) {
        this.neederid = neederid;
    }

    public String getNeedername() {
        return needername;
    }

    public void setNeedername(String needername) {
        this.needername = needername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
