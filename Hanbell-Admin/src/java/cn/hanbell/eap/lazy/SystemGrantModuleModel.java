/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.lazy;

import cn.hanbell.eap.entity.SystemGrantModule;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author kevindong
 */
public class SystemGrantModuleModel extends BaseLazyModel<SystemGrantModule> {

    public SystemGrantModuleModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }

    @Override
    public List<SystemGrantModule> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.sortFields.put("systemUser.userid", "ASC");
        this.sortFields.put("systemModule.sortid", "ASC");
        return super.load(first, pageSize, sortField, sortOrder, filters); 
    }

  
    
    

}
