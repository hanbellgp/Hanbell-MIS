/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.lazy;

import cn.hanbell.eap.entity.SystemProgram;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;

/**
 *
 * @author kevindong
 */
public class SystemProgramModel extends BaseLazyModel<SystemProgram> {

    public SystemProgramModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
        this.sortFields.put("systemModule.sortid", "ASC");
        this.sortFields.put("sortid", "ASC");
    }

}
