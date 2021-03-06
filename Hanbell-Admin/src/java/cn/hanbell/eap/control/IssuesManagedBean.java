/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

//import cn.hanbell.eap.ejb.IssuesBean;
import cn.hanbell.eap.ejb.IssuesBean;
import cn.hanbell.eap.entity.CustomerComplaintMaterial;
import cn.hanbell.eap.entity.Department;
import cn.hanbell.eap.entity.Demands;
import cn.hanbell.eap.entity.Issues;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.eap.lazy.IssuesModel;
import cn.hanbell.eap.web.FormSingleBean;
import com.lightshell.comm.BaseLib;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author C1749
 */
@ManagedBean(name = "issuesManagedBean")
@SessionScoped
public class IssuesManagedBean extends FormSingleBean<Issues> {

    //static final org.apache.logging.log4j.Logger log4j = LogManager.getLogger(IssuesManagedBean.class);
    Logger log4j = LogManager.getLogger(IssuesManagedBean.class);

    @EJB
    private IssuesBean issuesBean;
    private String formid;
    private String status;
    private String systemType;
    private String issuestype;
    private String neederid;
    private String needername;
    // private int id;

    @Override
    public void reset() {
        formid = "";
        status = "";
        systemType = "";
        issuestype = "";
        neederid = "";
        needername = "";
        super.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void persist() {
        super.persist(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        formid = "";
        status = "ALL";
        systemType = "";
        issuestype = "";
        neederid = "";
        needername = "";
        this.superEJB = issuesBean;
        this.newEntity.setFormdate(new Date());
        model = new IssuesModel(issuesBean);
        this.newEntity.setSchedule("0");
        this.currentEntity.setSchedule("0");
        super.init(); //
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            model.getFilterFields().clear();
            if (this.formid != null && !"".equals(this.formid)) {
                model.getFilterFields().put("formid", formid);
            }
            if (this.status != null && !"".equals(this.status)) {
                if ("N".equals(this.getStatus()) || "Y".equals(this.getStatus()) || "D".equals(this.status) || "W".equals(this.status) || "Z".equals(this.status)) {
                    model.getFilterFields().put("status", this.status);
                }
            }
            if (this.systemType != null && !"".equals(this.systemType)) {
                model.getFilterFields().put("systemtype", this.systemType);
            }
            if (this.issuestype != null && !"".equals(this.issuestype)) {
                if ("表单问题".equals(this.issuestype) || "操作问题".equals(this.issuestype) || "程式问题".equals(this.issuestype)
                        || "程序问题".equals(this.issuestype) || "设置问题".equals(this.issuestype)
                        || "系统问题".equals(this.issuestype) || "资料问题".equals(this.issuestype) || "作业问题".equals(this.issuestype) || "其他问题".equals(this.issuestype)) {
                    model.getFilterFields().put("issuestype", this.issuestype);
                }
            }
            if (this.neederid != null && !"".equals(this.neederid)) {
                model.getFilterFields().put("neederid", this.neederid);
            }
            if (this.needername != null && !"".equals(this.needername)) {
                model.getFilterFields().put("needername", this.needername);
            }
        }

    }

    public IssuesManagedBean() {
        super(Issues.class);
    }

    public IssuesManagedBean(Class<Demands> entityClass) {
        super(Issues.class);
    }

    public IssuesBean getIssuesBean() {
        return issuesBean;
    }

    public void setIssuesBean(IssuesBean issuesBean) {
        this.issuesBean = issuesBean;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getIssuestype() {
        return issuestype;
    }

    public void setIssuestype(String issuestype) {
        this.issuestype = issuestype;
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

    @Override
    public void handleDialogReturnWhenNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Department e = (Department) event.getObject();
            newEntity.setDeptname(e.getDept());
            newEntity.setDeptno(e.getDeptno());
        }
    }

    @Override
    public void handleDialogReturnWhenEdit(SelectEvent event) {
        if (event.getObject() != null && currentEntity != null) {
            Department e = (Department) event.getObject();
            currentEntity.setDeptname(e.getDept());
            currentEntity.setDeptno(e.getDeptno());
        }
    }
    //需求者

    public void handleDialogReturnUserWhenDetailNeederNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            newEntity.setNeedername(user.getUsername());
            newEntity.setNeederid(user.getUserid());
        }
    }

    public void handleDialogReturnUserWhenDetailNeederEdit(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            currentEntity.setNeedername(user.getUsername());
            currentEntity.setNeederid(user.getUserid());
        }
    }
    //负责人

    public void handleDialogReturnUserWhenDetailprincipalNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            newEntity.setPrincipalname(user.getUsername());
            newEntity.setPrincipalid(user.getUserid());
        }
    }

    public void handleDialogReturnUserWhenDetailprincipalEdit(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            currentEntity.setPrincipalname(user.getUsername());
            currentEntity.setPrincipalid(user.getUserid());
        }
    }

//    //填报人
//    public void handleDialogReturnUserWhenWriteNameNew(SelectEvent event) {
//        if (event.getObject() != null && newEntity != null) {
//            Object o = event.getObject();
//            SystemUser user = (SystemUser) o;
//            newEntity.setWriterName(user.getUsername());
//            newEntity.setWriterID(user.getUserid());
//        }
//    }
//
//    public void handleDialogReturnUserWhenWriteNameEdit(SelectEvent event) {
//        if (event.getObject() != null && newEntity != null) {
//            Object o = event.getObject();
//            SystemUser user = (SystemUser) o;
//            currentEntity.setWriterName(user.getUsername());
//            currentEntity.setWriterID(user.getUserid());
//        }
//    }
//    @Override
//    protected boolean doBeforePersist() throws Exception {
//        if (this.newEntity != null && this.getCurrentPrgGrant() != null) {
//            //id++;
//            this.newEntity.setFormdate(new Date());
//            //this.newEntity.setFormid("12341231212");
//            //this.newEntity.setIssuestype("表单问题");
//            this.newEntity.getAnswer();
//            return true;
//        }
//        return false;
//    }

    @Override
    public void print() {

        entityList = issuesBean.findByFilters(model.getFilterFields(), model.getSortFields());
        if (entityList == null || entityList.isEmpty()) {

            return;
        }

        fileName = "问题报告清单" + BaseLib.formatDate("yyyyMMddHHmmss", BaseLib.getDate()) + ".xls";
        String fileFullName = reportOutputPath + fileName;
        HSSFWorkbook workbook = new HSSFWorkbook();
        //获得表格样式
        Map<String, CellStyle> style = createStyles(workbook);
        // 生成一个表格
        HSSFSheet sheet1 = workbook.createSheet("问题报表");
        // 设置表格宽度
        int[] wt1 = getCustomerComplaintWidth();
        for (int i = 0; i < wt1.length; i++) {
            sheet1.setColumnWidth(i, wt1[i] * 256);
        }
        //创建标题行
        Row row;
        //表格一
        String[] title1 = getCustomerComplaintTitle();
        row = sheet1.createRow(0);
        row.setHeight((short) 800);
        for (int i = 0; i < title1.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style.get("head"));
            cell.setCellValue(title1[i]);
        }
        List<CustomerComplaintMaterial> plaintMaterialLis = new ArrayList<>();
        Field[] f = Issues.class.getDeclaredFields();
        int j = 1;
        for (Issues cp : entityList) {
            row = sheet1.createRow(j);
            j++;
            row.setHeight((short) 400);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style.get("cell"));
            cell0.setCellValue(cp.getFormid()!=null ? cp.getFormid() : "");

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style.get("cell"));
            cell1.setCellValue(cp.getSystemtype()!=null ? cp.getSystemtype() : "");

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style.get("cell"));
            cell2.setCellValue(cp.getModuletype()!=null ? cp.getModuletype() : "");

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style.get("cell"));
            cell3.setCellValue(cp.getIssuestype()!=null ? cp.getIssuestype() : "");
            
            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style.get("cell"));
            cell4.setCellValue(cp.getIssuesContent()!=null ? cp.getIssuesContent() : "");

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style.get("cell"));
            cell5.setCellValue(cp.getIssuesname()!=null ? cp.getIssuesname() : "");

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style.get("cell"));
            cell6.setCellValue(cp.getDeptno()!=null ? cp.getDeptno() : "");

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style.get("cell"));
            cell7.setCellValue(cp.getDeptname()!=null ? cp.getDeptname() : "");
            
             Cell cell8 = row.createCell(8);
            cell8.setCellStyle(style.get("cell"));
            cell8.setCellValue(cp.getNeederid()!=null ? cp.getNeederid() : "");
            
            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(style.get("cell"));
            cell9.setCellValue(cp.getNeedername()!=null ? cp.getNeedername() : "");

            Cell cell10 = row.createCell(10);
            cell10.setCellStyle(style.get("cell"));
            cell10.setCellValue(cp.getFormdate()!=null ? BaseLib.formatDate("yyyy-MM-dd",cp.getFormdate()) : "");

            Cell cell11 = row.createCell(11);
            cell11.setCellStyle(style.get("cell"));
            cell11.setCellValue(cp.getStarttime()!=null ? BaseLib.formatDate("yyyy-MM-dd",cp.getStarttime()) : "");

            Cell cell12 = row.createCell(12);
            cell12.setCellStyle(style.get("cell"));
            cell12.setCellValue(cp.getOvertime()!=null ? BaseLib.formatDate("yyyy-MM-dd",cp.getOvertime()) : "");

            Cell cell13 = row.createCell(13);
            cell13.setCellStyle(style.get("cell"));
            cell13.setCellValue(cp.getPrincipalid()!=null ? cp.getPrincipalid() : "");

            Cell cell14 = row.createCell(14);
            cell14.setCellStyle(style.get("cell"));
            cell14.setCellValue(cp.getPrincipalname()!=null ? cp.getPrincipalname() : "");

            Cell cell15 = row.createCell(15);
            cell15.setCellStyle(style.get("cell"));
            cell15.setCellValue(cp.getSchedule()!=null ? cp.getSchedule()+"%" : "");

            Cell cell16 = row.createCell(16);
            cell16.setCellStyle(style.get("cell"));
            cell16.setCellValue(cp.getUsetime()!=null ? cp.getUsetime() : "");
            
            char a='N';
            Cell cell17 = row.createCell(17);
            cell17.setCellStyle(style.get("cell"));
            cell17.setCellValue(cp.getPostpone()!=null ? cp.getPostpone() : a);

            Cell cell18 = row.createCell(18);
            cell18.setCellStyle(style.get("cell"));
            cell18.setCellValue(cp.getPostponecause()!=null ? cp.getPostponecause() : "");

            Cell cell19 = row.createCell(19);
            cell19.setCellStyle(style.get("cell"));
            cell19.setCellValue(cp.getAnswer()!=null ? cp.getAnswer() : "");

            Cell cell20 = row.createCell(20);
            cell20.setCellStyle(style.get("cell"));
            cell20.setCellValue(cp.getAnswerstate()!=null ? cp.getAnswerstate() : "");

            Cell cell21 = row.createCell(21);
            cell21.setCellStyle(style.get("cell"));
            cell21.setCellValue(cp.getFile()!=null ? cp.getFile() : "");

            Cell cell22 = row.createCell(22);
            cell22.setCellStyle(style.get("cell"));
            cell22.setCellValue(cp.getStatus()!=null ? cp.getStatus() : "");

            Cell cell23 = row.createCell(23);
            cell23.setCellStyle(style.get("cell"));
            cell23.setCellValue(cp.getCreator()!=null ? cp.getCreator() : "");

        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(fileFullName);
            workbook.write(os);
            this.reportViewPath = reportViewContext + fileName;
            this.preview();
        } catch (Exception ex) {
            log4j.error(ex);
        } finally {
            try {
                if (null != os) {
                    os.flush();
                    os.close();
                }
            } catch (IOException ex) {
                log4j.error(ex.getMessage());
            }
        }
    }

    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new LinkedHashMap<>();
        // 文件头样式
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setWrapText(true);//设置自动换行
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//单元格背景颜色
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font headFont = wb.createFont();
        headFont.setFontHeightInPoints((short) 12);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);
        styles.put("head", headStyle);

        // 正文样式
        CellStyle cellStyle = wb.createCellStyle();
        Font cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short) 10);
        cellStyle.setFont(cellFont);
        cellStyle.setWrapText(true);//设置自动换行
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());//单元格背景颜色
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", cellStyle);

        return styles;
    }

    private int[] getCustomerComplaintWidth() {
        return new int[]{20,8, 15,12,52, 50, 15, 14, 14, 14, 14, 14, 14, 14, 15, 20};
    }

    public String[] getCustomerComplaintTitle() {
        return new String[]{"单号", "系统类型", "系统模块","问题类型","问题简述", "问题描述", "需求部门编号", "需求部门", "需求者工号", "需求者名称", "提交时间", "开始时间", "结束时间", "负责人工号", "负责人姓名", "完成情况", "使用时间","是否延迟","延迟原因","回复","回复时间","附件","状态码","创建者"};
    }
     @Override
    public void verify() {
        if (null != getCurrentEntity()) {
            try {
                if (doBeforeVerify()) {
                    //currentEntity.setStatus("V");
                    currentEntity.setCfmuser(getUserManagedBean().getCurrentUser().getUsername());
                    currentEntity.setCfmdateToNow();
                    superEJB.verify(currentEntity);
                    doAfterVerify();
                    showInfoMsg("Info", "更新成功");
                } else {
                    showErrorMsg("Error", "审核前检查失败");
                }
            } catch (Exception ex) {
                showErrorMsg("Error", ex.getMessage());
            }
        } else {
            showWarnMsg("Warn", "没有可更新数据");
        }
    }

}
