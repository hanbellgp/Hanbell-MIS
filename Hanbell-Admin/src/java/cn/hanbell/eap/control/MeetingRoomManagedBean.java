/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.MeetingRoomBean;
import cn.hanbell.eap.ejb.MeetingScheduleBean;
import cn.hanbell.eap.entity.MeetingRoom;
import cn.hanbell.eap.entity.MeetingSchedule;
import cn.hanbell.eap.lazy.MeetingRoomModel;
import cn.hanbell.eap.web.SuperMultiBean;
import cn.hanbell.eap.web.SuperSingleBean;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "meetingRoomManagedBean")
@SessionScoped
public class MeetingRoomManagedBean extends SuperMultiBean<MeetingRoom, MeetingSchedule> {

    @EJB
    private MeetingRoomBean meetingRoomBean;

    @EJB
    private MeetingScheduleBean meetingScheduleBean;

    public MeetingRoomManagedBean() {
        super(MeetingRoom.class, MeetingSchedule.class);
    }

    @Override
    public void deleteDetail(MeetingSchedule entity) {
        if (currentEntity != null && entity != null) {
            this.detailEJB.delete(entity);
            this.detailList = this.detailEJB.findByPId(currentEntity.getId());
            this.showInfoMsg("Info", "删除成功");
        } else {
            this.showErrorMsg("Error", "没有可删除资料");
        }
    }

    @Override
    protected boolean doBeforePersist() throws Exception {
        if (!super.doBeforePersist()) {
            return false;
        }
        if (this.getCurrentPrgGrant() != null) {
            if (this.getCurrentPrgGrant().getSysprg().getNoauto() && !this.getCurrentPrgGrant().getSysprg().getNochange()) {
                String formid = superEJB.getFormId(newEntity.getCredate(), this.getCurrentPrgGrant().getSysprg().getNolead(), this.getCurrentPrgGrant().getSysprg().getNoformat(), this.getCurrentPrgGrant().getSysprg().getNoseqlen(), "name");
                this.newEntity.setName(formid);
            } else {
                Map<String, Object> filters = new HashMap<>();
                filters.put("name", this.newEntity.getName());
                if (superEJB.getRowCount(filters) > 0) {
                    showErrorMsg("Error", "编号已存在无法保存");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void init() {
        superEJB = this.meetingRoomBean;
        detailEJB = this.meetingScheduleBean;
        setModel(new MeetingRoomModel(meetingRoomBean));
        model.getSortFields().put("status", "ASC");
        model.getSortFields().put("sortid", "ASC");
        super.init();
    }

    @Override
    public void query() {
        this.model.getFilterFields().clear();
        if (this.queryName != null && !"".equals(this.queryName)) {
            this.model.getFilterFields().put("name", this.queryName);
        }
        if (this.queryState != null && !"ALL".equals(this.queryState)) {
            this.model.getFilterFields().put("status", this.queryState);
        }
    }

}
