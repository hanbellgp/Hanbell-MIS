/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.wei.lazy;

import cn.hanbell.wei.entity.News;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author kevindong
 */
public class NewsModel extends BaseLazyModel<News> {

    public NewsModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }

    @Override
    public List<News> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.sortFields.put("status", "ASC");
        this.sortFields.put("ontop", "DESC");
        this.sortFields.put("id", "DESC");
        return super.load(first, pageSize, sortField, sortOrder, filters);
    }

}
