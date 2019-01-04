/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.BookingBean;
import cn.hanbell.eap.ejb.BookingDetailBean;
import cn.hanbell.eap.ejb.MeetingScheduleBean;
import cn.hanbell.eap.entity.Booking;
import cn.hanbell.eap.entity.BookingDetail;
import cn.hanbell.eap.entity.MeetingSchedule;
import cn.hanbell.eap.lazy.BookingModel;
import cn.hanbell.eap.web.FormMultiBean;
import com.lightshell.comm.BaseLib;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "bookingManagedBean")
@SessionScoped
public class BookingManagedBean extends FormMultiBean<Booking, BookingDetail> {

    @EJB
    private BookingBean bookingBean;

    @EJB
    private BookingDetailBean bookingDetailBean;

    @EJB
    private MeetingScheduleBean meetingScheduleBean;

    private String queryKind;

    public BookingManagedBean() {
        super(Booking.class, BookingDetail.class);
    }

    @Override
    public void createDetail() {
        super.createDetail();
        currentDetail = newDetail;
    }

    @Override
    protected boolean doAfterPersist() throws Exception {
        this.detailList.clear();
        return super.doAfterPersist();
    }

    @Override
    protected boolean doBeforeDelete(Booking entity) throws Exception {
        if (entity != null) {
            MeetingSchedule ms = meetingScheduleBean.findByBookingId(entity.getFormid());
            if (ms != null) {
                meetingScheduleBean.delete(ms);
            }
        }
        return super.doBeforeDelete(entity);
    }

    @Override
    public void init() {
        superEJB = this.bookingBean;
        detailEJB = this.bookingDetailBean;
        setModel(new BookingModel(bookingBean));
        model.getSortFields().put("status", "ASC");
        model.getSortFields().put("formid", "DESC");
        super.init();
    }

    @Override
    public void query() {
        this.model.getFilterFields().clear();
        if (this.queryFormId != null && !"".equals(this.queryFormId)) {
            this.model.getFilterFields().put("formid", this.queryFormId);
        }
        if (this.queryKind != null && !"".equals(this.queryKind)) {
            this.model.getFilterFields().put("bookingKind.name", this.queryKind);
        }
        if (this.queryName != null && !"".equals(this.queryName)) {
            this.model.getFilterFields().put("name", this.queryName);
        }
        if (this.queryState != null && !"ALL".equals(this.queryState)) {
            this.model.getFilterFields().put("status", this.queryState);
        }
        if (this.queryDateBegin != null) {
            this.model.getFilterFields().put("startDateBegin", this.queryDateBegin);
        }
        if (this.queryDateEnd != null) {
            this.model.getFilterFields().put("endDateEnd", this.queryDateEnd);
        }
    }

    @Override
    public void print() throws Exception {
        if (getCurrentPrgGrant() != null && getCurrentPrgGrant().getDoprt()) {
            HashMap<String, Object> reportParams = new HashMap<>();
            reportParams.put("JNDIName", this.getCurrentPrgGrant().getSysprg().getRptjndi());
            if (!this.model.getFilterFields().isEmpty()) {
                reportParams.put("filterFields", BaseLib.convertMapToStringWithClass(this.model.getFilterFields()));
            } else {
                reportParams.put("filterFields", "");
            }
            if (!this.model.getSortFields().isEmpty()) {
                reportParams.put("sortFields", BaseLib.convertMapToString(this.model.getSortFields()));
            } else {
                reportParams.put("sortFields", "");
            }
            //设置报表名称
            String reportFormat;
            if (this.getCurrentPrgGrant().getSysprg().getRptformat() != null) {
                reportFormat = this.getCurrentPrgGrant().getSysprg().getRptformat();
            } else {
                reportFormat = reportOutputFormat;
            }
            this.fileName = this.getCurrentPrgGrant().getSysprg().getApi() + BaseLib.formatDate("yyyyMMddHHss", this.getDate()) + "." + reportFormat;
            String reportName = reportPath + this.getCurrentPrgGrant().getSysprg().getRptdesign();
            String outputName = reportOutputPath + this.fileName;
            this.reportViewPath = reportViewContext + this.fileName;
            try {
                if (this.getCurrentPrgGrant().getSysprg().getRptclazz() != null) {
                    reportClassLoader = Class.forName(this.getCurrentPrgGrant().getSysprg().getRptclazz()).getClassLoader();
                }
                //初始配置
                this.reportInitAndConfig();
                //生成报表
                this.reportRunAndOutput(reportName, reportParams, outputName, reportFormat, null);
                //预览报表
                this.preview();
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    /**
     * @return the queryKind
     */
    public String getQueryKind() {
        return queryKind;
    }

    /**
     * @param queryKind the queryKind to set
     */
    public void setQueryKind(String queryKind) {
        this.queryKind = queryKind;
    }

}
