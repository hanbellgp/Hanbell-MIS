/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.lazy;

import cn.hanbell.eap.entity.Demandschedule;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;

/**
 *
 * @author Administrator
 */
public class DemandscheduleModel extends BaseLazyModel<Demandschedule>{
    
    public DemandscheduleModel(SuperEJB superEJB){
          this.superEJB=superEJB;
    }
    
}
