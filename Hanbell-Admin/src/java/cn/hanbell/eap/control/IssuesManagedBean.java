/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.IssuesBean;
import cn.hanbell.eap.entity.Department;
import cn.hanbell.eap.entity.Issues;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.eap.lazy.IssuesModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author C1749
 */
@ManagedBean(name = "issuesManagedBean")
@SessionScoped
public class IssuesManagedBean extends SuperSingleBean<Issues> {

    @EJB
    private IssuesBean issuesBean;
    private String issueNumber;//单号
    private String systemType;//所属系统
    private String moduleType;//所属模块
    private String issuesid;//需求名称
    private String deptno;//部门代号
    private String deptname;//部门名称
    private String neederid;//需求id
    private String needername;//需求人名称
    private String status;//状态

    public IssuesManagedBean() {
        super(Issues.class);
    }

    @Override
    public void construct() {
        super.construct();
    }

    @Override
    public void init() {
        this.issueNumber = "";
        this.issuesid = "";
        this.deptno = "";
        this.deptname = "";
        this.neederid = "";
        this.needername = "";
        this.status = "N";
        this.superEJB = issuesBean;
        model = new IssuesModel(issuesBean);
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (issueNumber != null && !"".equals(issueNumber)) {
                this.model.getFilterFields().put("issuenumber", issueNumber);
            }
            if (systemType != null && !"".equals(systemType)) {
                this.model.getFilterFields().put("systemtype", systemType);
            }
            if (moduleType != null && !"".equals(moduleType)) {
                this.model.getFilterFields().put("moduletype", moduleType);
            }
            if (issuesid != null && !"".equals(issuesid)) {
                this.model.getFilterFields().put("issuesid", issuesid);
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
    public void persist() {
        super.persist(); //To change body of generated methods, choose Tools | Templates.
        //暂时自动生成单号
        if (newEntity != null) {
            newEntity.setIssuenumber(getIssueNumberForDate());
        }
    }

    private static String getIssueNumberForDate() {
        DateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        return "PL"+sdf.format(new Date());
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

    public IssuesBean getIssuesBean() {
        return issuesBean;
    }

    public void setIssuesBean(IssuesBean issuesBean) {
        this.issuesBean = issuesBean;
    }

    public String getIssuesid() {
        return issuesid;
    }

    public void setIssuesid(String issuesid) {
        this.issuesid = issuesid;
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

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }
    

}
