/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.lazy;

import cn.hanbell.eap.entity.Issues;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;

/**
 *
 * @author C1749
 */
public class IssuesModel extends BaseLazyModel<Issues> {

    public IssuesModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }
}
