/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.lazy;

import cn.hanbell.eap.entity.Sports;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author C0160
 */
public class SportsModel extends BaseLazyModel<Sports> {

    public SportsModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }

    @Override
    public List<Sports> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        super.load(first, pageSize, sortField, sortOrder, filters);
        this.dataList.sort((Sports a, Sports b) -> {
            if (a.getScore() > b.getScore()) {
                return -1;
            } else {
                return 1;
            }
        });
        return this.dataList;
    }

}
