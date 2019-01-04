/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.rpt;

import cn.hanbell.eap.ejb.BookingBean;
import cn.hanbell.eap.entity.Booking;
import com.lightshell.comm.BaseLib;
import com.lightshell.comm.SuperSingleReportBean;
import java.util.List;
import java.util.Map;

/**
 *
 * @author C0160
 */
public class BookingListReport extends SuperSingleReportBean<BookingBean, Booking> {

    @Override
    public Booking getEntity(int i) throws Exception {
        return this.superEJB.findById(i);
    }

}
