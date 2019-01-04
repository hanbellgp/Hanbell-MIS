/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.SystemGrantModuleBean;
import cn.hanbell.eap.ejb.SystemGrantPrgBean;
import cn.hanbell.eap.ejb.SystemModuleBean;
import cn.hanbell.eap.ejb.SystemRoleBean;
import cn.hanbell.eap.entity.SystemGrantModule;
import cn.hanbell.eap.entity.SystemGrantPrg;
import cn.hanbell.eap.entity.SystemModule;
import cn.hanbell.eap.entity.SystemProgram;
import cn.hanbell.eap.entity.SystemRole;
import cn.hanbell.eap.lazy.SystemRoleModel;
import cn.hanbell.eap.web.SuperSingleBean;
import com.lightshell.comm.SuperDetailEntity;
import com.lightshell.comm.SuperEJB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "roleGrantManagedBean")
@SessionScoped
public class RoleGrantManagedBean extends SuperSingleBean<SystemRole> {

    @EJB
    private SystemGrantModuleBean detailEJB;

    @EJB
    private SystemGrantPrgBean detailEJB2;

    @EJB
    private SystemModuleBean sysmoduleBean;

    @EJB
    private SystemRoleBean systemRoleBean;

    private List<SystemModule> moduleList;

    private SystemGrantModule newDetail;
    private SystemGrantModule currentDetail;

    private SystemGrantPrg newDetail2;
    private SystemGrantPrg currentDetail2;

    private List<SystemGrantModule> detailList;
    private List<SystemGrantModule> addedDetailList;
    private List<SystemGrantModule> editedDetailList;
    private List<SystemGrantModule> deletedDetailList;

    private List<SystemGrantPrg> detailList2;
    private List<SystemGrantPrg> addedDetailList2;
    private List<SystemGrantPrg> editedDetailList2;
    private List<SystemGrantPrg> deletedDetailList2;

    private HashMap<SuperEJB, List<?>> detailAdded;
    private HashMap<SuperEJB, List<?>> detailEdited;
    private HashMap<SuperEJB, List<?>> detailDeleted;

    public RoleGrantManagedBean() {
        super(SystemRole.class);
    }

    @Override
    public void construct() {
        this.addedDetailList = new ArrayList<>();
        this.editedDetailList = new ArrayList<>();
        this.deletedDetailList = new ArrayList<>();
        this.addedDetailList2 = new ArrayList<>();
        this.editedDetailList2 = new ArrayList<>();
        this.deletedDetailList2 = new ArrayList<>();
        this.detailAdded = new HashMap<>();
        this.detailEdited = new HashMap<>();
        this.detailDeleted = new HashMap<>();
        super.construct();
    }

    @Override
    public void destory() {
        if (this.addedDetailList != null) {
            this.addedDetailList.clear();
        }
        if (this.editedDetailList != null) {
            this.editedDetailList.clear();
        }
        if (this.deletedDetailList != null) {
            this.deletedDetailList.clear();
        }
        if (this.addedDetailList2 != null) {
            this.addedDetailList2.clear();
        }
        if (this.editedDetailList2 != null) {
            this.editedDetailList2.clear();
        }
        if (this.deletedDetailList2 != null) {
            this.deletedDetailList2.clear();
        }
        super.destory();
    }

    public void createDetail() {
        if (getNewDetail() == null) {
            try {
                setNewDetail(SystemGrantModule.class.newInstance());
                newDetail.setKind("R");
                newDetail.setSystemRole(currentEntity);
                newDetail.setStatus("N");
                newDetail.setCreator(this.userManagedBean.getCurrentUser().getUsername());
                newDetail.setCredateToNow();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SystemGrantModule.class.getName()).log(Level.SEVERE, null, ex);
                showErrorMsg("Error", ex.getMessage());
            }
        }
        setCurrentDetail(getNewDetail());
    }

    public void createDetail2() {
        if (getNewDetail2() == null) {
            try {
                setNewDetail2(SystemGrantPrg.class.newInstance());
                newDetail2.setSeq(this.getMaxSeq(detailList2));
                newDetail2.setKind("R");
                newDetail2.setSystemRole(currentEntity);
                newDetail2.setStatus("N");
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SystemGrantPrg.class.getName()).log(Level.SEVERE, null, ex);
                showErrorMsg("Error", ex.getMessage());
            }
        }
        setCurrentDetail2(getNewDetail2());
    }

    public void deleteDetail() {
        if (currentDetail != null) {
            try {
                deleteDetail(currentDetail);
                setCurrentDetail(null);
            } catch (Exception e) {
                showErrorMsg("Error", e.getMessage());
            }
        } else {
            showWarnMsg("Warn", "没有可删除数据");
        }
    }

    public void deleteDetail(SystemGrantModule entity) {
        if (entity != null) {
            try {
                if (this.addedDetailList.contains(entity)) {
                    this.addedDetailList.remove(entity);
                } else {
                    if (this.editedDetailList.contains(entity)) {
                        this.editedDetailList.remove(entity);
                    }
                    if (!this.deletedDetailList.contains(entity)) {
                        this.deletedDetailList.add(entity);
                    }
                }
                if (this.detailList.contains(entity)) {
                    getDetailList().remove(entity);
                }
                showInfoMsg("Info", "删除成功");
            } catch (Exception e) {
                showErrorMsg("Error", e.getMessage());
            }
        } else {
            showWarnMsg("Warn", "没有可删除数据");
        }
    }

    public void deleteDetail2() {
        if (currentDetail2 != null) {
            try {
                deleteDetail2(currentDetail2);
                setCurrentDetail2(null);
            } catch (Exception e) {
                showErrorMsg("Error", e.getMessage());
            }
        } else {
            showWarnMsg("Warn", "没有可删除数据");
        }
    }

    public void deleteDetail2(SystemGrantPrg entity) {
        if (entity != null) {
            try {
                if (this.addedDetailList2.contains(entity)) {
                    this.addedDetailList2.remove(entity);
                } else {
                    if (this.editedDetailList2.contains(entity)) {
                        this.editedDetailList2.remove(entity);
                    }
                    if (!this.deletedDetailList2.contains(entity)) {
                        this.deletedDetailList2.add(entity);
                    }
                }
                if (this.detailList2.contains(entity)) {
                    getDetailList2().remove(entity);
                }
                showInfoMsg("Info", "删除成功");
            } catch (Exception e) {
                showErrorMsg("Error", e.getMessage());
            }
        } else {
            showWarnMsg("Warn", "没有可删除数据");
        }
    }

    @Override
    protected boolean doAfterUpdate() throws Exception {
        addedDetailList.clear();
        editedDetailList.clear();
        deletedDetailList.clear();
        setNewDetail(null);
        setCurrentDetail(null);
        addedDetailList2.clear();
        editedDetailList2.clear();
        deletedDetailList2.clear();
        setNewDetail2(null);
        setCurrentDetail2(null);
        return super.doAfterUpdate();
    }

    public void doConfirmDetail() {
        if (this.getNewDetail() != null && this.getNewDetail().equals(this.currentDetail)) {
            if (!this.addedDetailList.contains(this.newDetail)) {
                boolean flag = true;
                //检查是否存在相同来源信息
                for (SystemGrantModule detail : this.getDetailList()) {
                    if (detail.equals(this.getNewDetail())) {
                        flag = false;
                    }
                }
                if (flag) {
                    this.addedDetailList.add(this.getNewDetail());
                    this.getDetailList().add(this.getNewDetail());
                    setNewDetail(null);
                    setCurrentDetail(null);
                }
            }
        } else if (this.currentDetail != null && !this.addedDetailList.contains(this.currentDetail)) {
            this.editedDetailList.add(this.currentDetail);
            setCurrentDetail(null);
        }
    }

    public void doConfirmDetail2() {
        if (currentDetail2.getSysprg() == null) {
            showErrorMsg("Error", "请输入程序名称");
            return;
        }
        if (this.newDetail2 != null && this.newDetail2.equals(this.currentDetail2)) {
            if (!this.addedDetailList2.contains(this.newDetail2) && !this.detailList2.contains(this.newDetail2)) {
                this.addedDetailList2.add(this.newDetail2);
                this.detailList2.add(this.newDetail2);
                setNewDetail2(null);
                setCurrentDetail2(null);
            }
        } else if (this.currentDetail2 != null && !this.addedDetailList2.contains(this.currentDetail2)) {
            this.editedDetailList2.add(this.currentDetail2);
            setCurrentDetail2(null);
        }
    }

    protected int getMaxSeq(List<? extends SuperDetailEntity> list) {
        if (list == null || list.isEmpty()) {
            return 1;
        }
        int seq = 0;
        for (SuperDetailEntity entity : list) {
            if (entity.getSeq() > seq) {
                seq = entity.getSeq();
            }
        }
        boolean b = true;
        boolean ret;
        for (int i = 1; i <= seq; i++) {
            ret = true;
            for (SuperDetailEntity entity : list) {
                if (entity.getSeq() == i) {
                    ret = ret && false;
                    break;
                }
            }
            if (ret) {
                return i;
            }
        }
        if (b) {
            return seq + 1;
        } else {
            return 0;
        }
    }

    @Override
    public void handleDialogReturnWhenEdit(SelectEvent event) {
        if (currentDetail2 != null && event.getObject() != null) {
            SystemProgram sysprg = (SystemProgram) event.getObject();
            currentDetail2.setPid(sysprg.getSystemModule().getId());
            currentDetail2.setSysprg(sysprg);
        }
    }

    @Override
    public void init() {
        detailAdded.put(detailEJB, this.addedDetailList);
        detailEdited.put(detailEJB, this.editedDetailList);
        detailDeleted.put(detailEJB, this.deletedDetailList);
        detailAdded.put(detailEJB2, this.addedDetailList2);
        detailEdited.put(detailEJB2, this.editedDetailList2);
        detailDeleted.put(detailEJB2, this.deletedDetailList2);
        moduleList = sysmoduleBean.findAll();
        superEJB = systemRoleBean;
        setModel(new SystemRoleModel(systemRoleBean));
        model.getSortFields().put("roleno", "ASC");
        super.init();
    }

    @Override
    public void openDialog(String view) {
        if (null != view && currentEntity != null) {
            switch (view) {
                case "sysprgSelect":
                    if (currentDetail != null && currentDetail.getSystemModule() != null) {
                        Map<String, List<String>> params = new HashMap<>();
                        List<String> moduleid = new ArrayList<>();
                        moduleid.add(currentDetail.getSystemModule().getId().toString());
                        params.put("moduleid", moduleid);
                        super.openDialog("sysprgSelect", params);
                    } else {
                        super.openDialog("sysprgSelect");
                    }
                    break;
                default:
                    super.openDialog(view);
            }
        }
    }

    @Override
    protected void setToolBar() {
        if (currentEntity != null && currentEntity.getStatus() != null) {
            switch (currentEntity.getStatus()) {
                case "V":
                    this.doEdit = false;
                    this.doDel = false;
                    this.doCfm = false;
                    this.doUnCfm = true;
                    break;
                default:
                    this.doEdit = true;
                    this.doDel = true;
                    this.doCfm = true;
                    this.doUnCfm = false;
            }
        } else {
            this.doEdit = false;
            this.doDel = false;
            this.doCfm = false;
            this.doUnCfm = false;
        }
    }

    @Override
    public void update() {
        if (currentEntity != null) {
            try {
                if (doBeforeUpdate()) {
                    superEJB.update(currentEntity, detailAdded, detailEdited, detailDeleted);
                    doAfterUpdate();
                    showInfoMsg("Info", "更新成功");
                } else {
                    showErrorMsg("Error", "更新前检核失败");
                }
            } catch (Exception e) {
                showErrorMsg("Error", e.toString());
            }
        } else {
            showWarnMsg("Warn", "没有可更新数据");
        }
    }

    /**
     * @return the moduleList
     */
    public List<SystemModule> getModuleList() {
        return moduleList;
    }

    /**
     * @param moduleList the moduleList to set
     */
    public void setModuleList(List<SystemModule> moduleList) {
        this.moduleList = moduleList;
    }

    /**
     * @return the currentDetail
     */
    public SystemGrantModule getCurrentDetail() {
        return currentDetail;
    }

    /**
     * @param currentDetail the currentDetail to set
     */
    public void setCurrentDetail(SystemGrantModule currentDetail) {
        this.currentDetail = currentDetail;
    }

    /**
     * @return the detailList
     */
    public List<SystemGrantModule> getDetailList() {
        return detailList;
    }

    /**
     * @param detailList the detailList to set
     */
    public void setDetailList(List<SystemGrantModule> detailList) {
        this.detailList = detailList;
    }

    /**
     * @return the newDetail
     */
    public SystemGrantModule getNewDetail() {
        return newDetail;
    }

    /**
     * @param newDetail the newDetail to set
     */
    public void setNewDetail(SystemGrantModule newDetail) {
        this.newDetail = newDetail;
    }

    @Override
    public void setCurrentEntity(SystemRole currentEntity) {
        super.setCurrentEntity(currentEntity);
        if (detailList != null) {
            detailList.clear();
        }
        if (detailList2 != null) {
            detailList2.clear();
        }
        if (currentEntity != null && currentEntity.getId() != null) {
            setDetailList(detailEJB.findByRoleId(currentEntity.getId()));
            setDetailList2(detailEJB2.findByRoleId(currentEntity.getId()));
        }
        if (this.detailList == null) {
            this.detailList = new ArrayList<>();
        }
        if (this.detailList2 == null) {
            this.detailList2 = new ArrayList<>();
        }
        addedDetailList.clear();
        editedDetailList.clear();
        deletedDetailList.clear();
        addedDetailList2.clear();
        editedDetailList2.clear();
        deletedDetailList2.clear();
    }

    /**
     * @return the newDetail2
     */
    public SystemGrantPrg getNewDetail2() {
        return newDetail2;
    }

    /**
     * @param newDetail2 the newDetail2 to set
     */
    public void setNewDetail2(SystemGrantPrg newDetail2) {
        this.newDetail2 = newDetail2;
    }

    /**
     * @return the currentDetail2
     */
    public SystemGrantPrg getCurrentDetail2() {
        return currentDetail2;
    }

    /**
     * @param currentDetail2 the currentDetail2 to set
     */
    public void setCurrentDetail2(SystemGrantPrg currentDetail2) {
        this.currentDetail2 = currentDetail2;
    }

    /**
     * @return the detailList2
     */
    public List<SystemGrantPrg> getDetailList2() {
        return detailList2;
    }

    /**
     * @param detailList2 the detailList2 to set
     */
    public void setDetailList2(List<SystemGrantPrg> detailList2) {
        this.detailList2 = detailList2;
    }

}
