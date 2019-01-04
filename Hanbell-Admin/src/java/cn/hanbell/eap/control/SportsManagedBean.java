/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.SportsBean;
import cn.hanbell.eap.entity.Sports;
import cn.hanbell.eap.lazy.SportsModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "sportsManagedBean")
@SessionScoped
public class SportsManagedBean extends SuperSingleBean<Sports> {

    @EJB
    private SportsBean sportsBean;

    private Calendar c = Calendar.getInstance();

    public SportsManagedBean() {
        super(Sports.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setY(c.get(Calendar.YEAR));
    }

    @Override
    protected boolean doBeforeUpdate() throws Exception {
        if (super.doBeforeUpdate()) {
            int s = 0;
            s += currentEntity.getS01();
            s += currentEntity.getS02();
            s += currentEntity.getS03();
            s += currentEntity.getS04();
            s += currentEntity.getS05();
            s += currentEntity.getS11();
            s += currentEntity.getS12();
            s += currentEntity.getS13();
            s += currentEntity.getS14();
            s += currentEntity.getS15();
            s += currentEntity.getS21();
            s += currentEntity.getS22();
            s += currentEntity.getS23();
            s += currentEntity.getS24();
            s += currentEntity.getS25();
            s += currentEntity.getS31();
            s += currentEntity.getS32();
            s += currentEntity.getS33();
            s += currentEntity.getS34();
            s += currentEntity.getS35();
            s += currentEntity.getS41();
            s += currentEntity.getS42();
            s += currentEntity.getS43();
            s += currentEntity.getS44();
            s += currentEntity.getS45();
            currentEntity.setScore(s);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void init() {
        superEJB = sportsBean;
        model = new SportsModel(sportsBean);
        super.init();
    }

    @Override
    public String view(String path) {
        setCurrentEntity(null);
        return path;
    }

}
