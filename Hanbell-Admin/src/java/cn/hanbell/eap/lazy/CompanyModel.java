/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.lazy;

import cn.hanbell.eap.entity.Company;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;

/**
 *
 * @author C0160
 */
public class CompanyModel extends BaseLazyModel<Company> {

    public CompanyModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }

}
