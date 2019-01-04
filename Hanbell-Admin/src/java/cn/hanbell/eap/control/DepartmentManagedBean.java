/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hanbell.eap.control;

import cn.hanbell.eap.ejb.DepartmentBean;
import cn.hanbell.eap.entity.Department;
import cn.hanbell.eap.lazy.DepartmentModel;
import cn.hanbell.eap.web.SuperSingleBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "departmentManagedBean")
@SessionScoped
public class DepartmentManagedBean extends SuperSingleBean<Department> {

    @EJB
    private DepartmentBean departmentBean;

    private TreeNode rootNode;
    private TreeNode selectedNode;
    private List<Department> rootDepartment;

    public DepartmentManagedBean() {
        super(Department.class);
    }

    @Override
    protected boolean doAfterDelete() throws Exception {
        initTree();
        return super.doAfterDelete();
    }

    @Override
    protected boolean doAfterPersist() throws Exception {
        initTree();
        return super.doAfterPersist();
    }

    @Override
    protected boolean doAfterUpdate() throws Exception {
        initTree();
        return super.doAfterUpdate();
    }

    @Override
    public void handleDialogReturnWhenEdit(SelectEvent event) {
        if (event.getObject() != null && currentEntity != null) {
            Department e = (Department) event.getObject();
            currentEntity.setParentDept(e);
        }
    }

    @Override
    public void handleDialogReturnWhenNew(SelectEvent event) {
        if (event.getObject() != null && newEntity != null) {
            Department e = (Department) event.getObject();
            newEntity.setParentDept(e);
        }
    }

    @Override
    public void init() {
        superEJB = departmentBean;
        model = new DepartmentModel(departmentBean);
        super.init();
        initTree();
    }

    private void initTree() {
        setRootNode(new DefaultTreeNode(new Department("Root", "Root"), null));
        getRootNode().setExpanded(true);
        rootDepartment = departmentBean.findRoot();
        if (rootDepartment != null && !rootDepartment.isEmpty()) {
            for (Department p : rootDepartment) {
                TreeNode n = new DefaultTreeNode(p, getRootNode());
                //n.setExpanded(true);
                initTree(p, n);
            }
        }
    }

    private void initTree(Department position, TreeNode node) {
        List<Department> departments = departmentBean.findByPId(position.getId());
        if (departments != null && !departments.isEmpty()) {
            for (Department p : departments) {
                TreeNode n = new DefaultTreeNode(p, node);
                //n.setExpanded(true);
                initTree(p, n);
            }
        }
    }

    /**
     * @return the rootNode
     */
    public TreeNode getRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * @return the selectedNode
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * @param selectedNode the selectedNode to set
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
        if (selectedNode != null) {
            currentEntity = (Department) selectedNode.getData();
            setToolBar();
        }
    }

}
