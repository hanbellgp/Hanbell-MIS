/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.DemandscheduleBean;
import cn.hanbell.eap.entity.Demandschedule;
import cn.hanbell.eap.lazy.DemandscheduleModel;
import cn.hanbell.eap.web.SuperSingleBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class DemandscheduleManagedBean extends SuperSingleBean<Demandschedule> {

    @EJB
    private DemandscheduleBean demandscheduleBean;
    protected String queryProname;

    public String getQueryProname() {
        return queryProname;
    }

    public void setQueryProname(String queryProname) {
        this.queryProname = queryProname;
    }

    public DemandscheduleManagedBean() {
        super(Demandschedule.class);
    }

    //   protected List<Demandschedule> entityList;
//    @PostConstruct
//    @Override
//    public void construct(){
//         init();
//    }
    public void init() {
        this.superEJB = demandscheduleBean;
        this.model = new DemandscheduleModel(demandscheduleBean);
    }

//    public List<Demandschedule> getEntityList(){
//         return entityList;
//    }
//    
//    public void setEntityList(List<Demandschedule> entityList){
//         this.entityList=entityList;
//    }
}
