/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.lazy;

import cn.hanbell.eap.entity.SystemModule;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;

/**
 *
 * @author kevindong
 */
public class SystemModuleModel extends BaseLazyModel<SystemModule> {

    public SystemModuleModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }

}
