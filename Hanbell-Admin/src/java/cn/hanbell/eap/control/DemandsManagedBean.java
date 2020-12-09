/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.DemandsBean;
import cn.hanbell.eap.entity.CustomerComplaintMaterial;
import cn.hanbell.eap.entity.Demands;
import cn.hanbell.eap.entity.Department;
import cn.hanbell.eap.entity.Issues;
import cn.hanbell.eap.entity.SystemUser;
import cn.hanbell.eap.lazy.DemandsModel;
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
@ManagedBean(name = "demandsManagedBean")
@SessionScoped
public class DemandsManagedBean extends FormSingleBean<Demands> {

    @EJB
    private DemandsBean demandsBean;
    //日志
    Logger log4j = LogManager.getLogger(IssuesManagedBean.class);
    public String formid;
    private Date formDate;
    private String status;
    private Date realOverDate;
    private String demandName;
    private Integer dataSize;
    private String demandDeptno;
    //完成起始时间和终止时间
    private Date startTime;
    private Date overTime;
    
    private List<Demands> detailList;

    
    public Integer getDataSize() {
        return dataSize;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }

    
    public List<Demands> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Demands> detailList) {
        this.detailList = detailList;
    }

    @Override
    public void create() {
        super.create();
        newEntity.setFormdate(getDate());
    }

    @Override
    protected boolean doAfterPersist() throws Exception {
        this.detailList.clear();
        
        return super.doAfterPersist();
    }

    public DemandsManagedBean() {
        super(Demands.class);
    }

    public DemandsManagedBean(Class<Demands> entityClass) {
        super(entityClass);
    }

    @Override
    public void reset() {
        formid = null;
        //realOverDate = new Date();
        formDate = null;
        status = "All";
        demandName = null;
        demandDeptno = null;
        realOverDate=null;
        overTime=null;
        startTime=null;
        super.reset();
    }

    @Override
    public void init() {
        //数据初始化
        //formid = null;
        //realOverDate = new Date();
        //formDate = null;
        this.superEJB = demandsBean;
        this.model = new DemandsModel(demandsBean);
        super.init();
        //newEntity.setAppendix("1");
        //newEntity.setStatus("Y");
        this.model.getRowCount();
        newEntity.setFormdate(new Date());
        newEntity.setWriteDate(new Date());
        
    }

    @Override
    public void query() {
        if (model != null) {
            model.getFilterFields().clear();
            if (getFormid() != null && !"".equals(getFormid())) {
                //System.out.println("formid=" + formid);
                model.getFilterFields().put("formid", formid);
             }
            if (getStatus() != null && !"".equals(getStatus())) {
                //判断这里传过来的是N还是Y
                if ("Y".equals(getStatus()) || "N".equals(getStatus())) {
                    model.getFilterFields().put("status", status);
                }
            }
            if (getDemandName() != null && !"".equals(getDemandName())) {
                model.getFilterFields().put("demandName", demandName);
            }
            if (getDemandDeptno() != null && !"".equals(getDemandDeptno())) {
                model.getFilterFields().put("demandDeptName", demandDeptno);
            }
            if (getFormDate() != null && !"".equals(getFormDate())) {
                model.getFilterFields().put("formdateBegin", formDate);
            }
            if (getRealOverDate() != null && !"".equals(getRealOverDate())) {
                //System.out.println("formdateEnd" + realOverDate);
                model.getFilterFields().put("formdateEnd", realOverDate);
            }
            if(getStartTime()!=null && !"".equals(getStartTime())){
                model.getFilterFields().put("realOverDateBegin", getStartTime());
            }
            if(getOverTime()!=null && !"".equals(getOverTime())){
                model.getFilterFields().put("realOverDateEnd", getOverTime());
            }
//            reset();
            //this.dataSize=this.model.getRowCount();
        }
        //System.out.print("model"+model);
    }

    public void createDetail() {
    }

    public void deleteDetail() {
    }

    public void doConfirmDetail() {
    }

    public DemandsBean getDemandsBean() {
        return demandsBean;
    }

    public void setDemandsBean(DemandsBean demandsBean) {
        this.demandsBean = demandsBean;
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

    public Date getFormDate() {
        return formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public Date getRealOverDate() {
        return realOverDate;
    }

    public void setRealOverDate(Date realOverDate) {
        this.realOverDate = realOverDate;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getDemandDeptno() {
        return demandDeptno;
    }

    public void setDemandDeptno(String demandDeptno) {
        this.demandDeptno = demandDeptno;
    }

    @Override
    public void handleDialogReturnWhenNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Department e = (Department) event.getObject();
            newEntity.setDemanderDeptID(e.getDeptno());
            newEntity.setDemanderDeptName(e.getDept());
        }
    }

    @Override
    public void handleDialogReturnWhenEdit(SelectEvent event) {
        if (event.getObject() != null && currentEntity != null) {
            Department e = (Department) event.getObject();
            currentEntity.setDemanderDeptID(e.getDeptno());
            currentEntity.setDemanderDeptName(e.getDept());
        }
    }
    //需求者

    public void handleDialogReturnUserWhenDetailNeederNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            newEntity.setDemanderID(user.getUserid());
            newEntity.setDemanderName(user.getUsername());
        }
    }

    public void handleDialogReturnUserWhenDetailNeederEdit(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            currentEntity.setDemanderID(user.getUserid());
            currentEntity.setDemanderName(user.getUsername());
        }
    }
    //负责人

    public void handleDialogReturnUserWhenDetailprincipalNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            newEntity.setDirectorID(user.getUserid());
            newEntity.setDirectorName(user.getUsername());
        }
    }

    public void handleDialogReturnUserWhenDetailprincipalEdit(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            currentEntity.setDirectorID(user.getUserid());
            currentEntity.setDirectorName(user.getUsername());
        }
    }

    //填报人
    public void handleDialogReturnUserWhenWriteNameNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            newEntity.setWriterName(user.getUsername());
            newEntity.setWriterID(user.getUserid());
        }
    }

    public void handleDialogReturnUserWhenWriteNameEdit(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Object o = event.getObject();
            SystemUser user = (SystemUser) o;
            currentEntity.setWriterName(user.getUsername());
            currentEntity.setWriterID(user.getUserid());
        }
    }

    //打印
    @Override
    public void print() {

        entityList = demandsBean.findByFilters(model.getFilterFields(), model.getSortFields());
        if (entityList == null || entityList.isEmpty()) {

            return;
        }

        fileName = "需求优化清单" + BaseLib.formatDate("yyyyMMddHHmmss", BaseLib.getDate()) + ".xls";
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
        for (Demands cp : entityList) {
            row = sheet1.createRow(j);
            j++;
            row.setHeight((short) 400);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style.get("cell"));
            cell0.setCellValue(cp.getFormid() != null ? cp.getFormid() : "");

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style.get("cell"));
            //cell1.setCellValue(cp.getFormdate()!=null ? cp.getFormdate() : "");
            cell1.setCellValue(cp.getFormdate() != null ? BaseLib.formatDate("yyyy-MM-dd", cp.getFormdate()) : "");

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style.get("cell"));
            cell2.setCellValue(cp.getSystemName() != null ? cp.getSystemName() : "");

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style.get("cell"));
            cell3.setCellValue(cp.getModuleName() != null ? cp.getModuleName() : "");

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style.get("cell"));
            cell4.setCellValue(cp.getDemandResume() != null ? cp.getDemandResume() : "");

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style.get("cell"));
            cell5.setCellValue(cp.getDemandContent() != null ? cp.getDemandContent() : "");

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style.get("cell"));
            cell6.setCellValue(cp.getAppendix() != null ? cp.getAppendix() : "");

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style.get("cell"));
            cell7.setCellValue(cp.getDemanderDeptID() != null ? cp.getDemanderDeptID() : "");

            Cell cell8 = row.createCell(8);
            cell8.setCellStyle(style.get("cell"));
            cell8.setCellValue(cp.getDemanderDeptName() != null ? cp.getDemanderDeptName() : "");

            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(style.get("cell"));
            cell9.setCellValue(cp.getDemanderID() != null ? cp.getDemanderID() : "");

            Cell cell10 = row.createCell(10);
            cell10.setCellStyle(style.get("cell"));
            cell10.setCellValue(cp.getDemanderName() != null ? cp.getDemanderName() : "");

            Cell cell11 = row.createCell(11);
            cell11.setCellStyle(style.get("cell"));
            cell11.setCellValue(cp.getDirectorID() != null ? cp.getDirectorID() : "");

            Cell cell12 = row.createCell(12);
            cell12.setCellStyle(style.get("cell"));
            cell12.setCellValue(cp.getDirectorName() != null ? cp.getDirectorName() : "");

            Cell cell13 = row.createCell(13);
            cell13.setCellStyle(style.get("cell"));
            cell13.setCellValue(cp.getStatus() != null ? cp.getStatus() : "");

            Cell cell14 = row.createCell(14);
            cell14.setCellStyle(style.get("cell"));
            cell14.setCellValue(cp.getRealStartDate() != null ? BaseLib.formatDate("yyyy-MM-dd", cp.getRealStartDate()) : "");

            Cell cell15 = row.createCell(15);
            cell15.setCellStyle(style.get("cell"));
            cell15.setCellValue(cp.getRealOverDate() != null ? BaseLib.formatDate("yyyy-MM-dd", cp.getRealOverDate()) : "");

            Cell cell16 = row.createCell(16);
            cell16.setCellStyle(style.get("cell"));
            cell16.setCellValue(cp.getPlanStartDate() != null ? BaseLib.formatDate("yyyy-MM-dd", cp.getPlanStartDate()) : "");

            Cell cell17 = row.createCell(17);
            cell17.setCellStyle(style.get("cell"));
            cell17.setCellValue(cp.getPlanOverDate() != null ? BaseLib.formatDate("yyyy-MM-dd", cp.getRealOverDate()) : "");

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
        return new int[]{20, 13, 8, 7, 10, 50, 8, 14, 18, 14, 14, 14, 14, 14, 15, 20,13,13};
    }

    public String[] getCustomerComplaintTitle() {
        return new String[]{"单号", "登记时间", "系统类型", "系统模块", "问题简述", "问题描述","附件", "需求部门编号", "需求部门", "需求者工号", "需求者名称", "负责人工号", "负责人姓名", "状态码","实际开始时间", "实际完成时间", "计划开始时间", "计划完成时间"};
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
