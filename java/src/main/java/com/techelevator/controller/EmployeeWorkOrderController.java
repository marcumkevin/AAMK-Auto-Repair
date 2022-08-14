package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.WorkOrderDao;
import com.techelevator.model.Repair;
import com.techelevator.model.User;
import com.techelevator.model.WorkOrder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated() && hasRole('ROLE_EMPLOYEE')")
//@RequestMapping(path = "/employee/workorders")
public class EmployeeWorkOrderController {

    private WorkOrderDao workOrderDao;

    private UserDao userDao;

    public EmployeeWorkOrderController(WorkOrderDao workOrderDao, UserDao userDao) {
        this.workOrderDao = workOrderDao;
        this.userDao = userDao;
    }

    /**
     * Returns the current logged-in user.
     * @param username The principal username,.
     * @return The current logged-in user.
     */
    public User getLoggedInUser(String username)
    {
        return this.userDao.findByUsername(username);
    }

    @RequestMapping(path = "/workorders", method = RequestMethod.GET)
    public List<WorkOrder> getWorkOrders()
    {
        return this.workOrderDao.getWorkOrders();
    }

//    @RequestMapping(path = "/workorders/{userId}", method = RequestMethod.GET)
//    public List<WorkOrder> getWorkOrdersByUser(@PathVariable int userId){return this.workOrderDao.getWorkOrdersByUserId(userId);}

    @RequestMapping(path = "/repairs/{workOrderId}", method = RequestMethod.GET)
    public List<Repair> getRepairsByWorkOrderId(@PathVariable int workOrderId){return this.workOrderDao.getRepairsByWorkOrderId(workOrderId); }

    @RequestMapping(path = "/workorders", method = RequestMethod.POST)
    public WorkOrder create(@RequestBody WorkOrder workOrder) {
        int workOrderId = workOrderDao.createWorkOrder(workOrder);
        workOrder.setWorkOrderId(workOrderId);

        return workOrderDao.getWorkOrder(workOrderId);
    }

    @RequestMapping(path = "/repairs", method = RequestMethod.POST)
    public boolean createRepair(@RequestBody Repair repair) {
        return workOrderDao.createRepair(repair);
    }

    @RequestMapping(path = "/changeorder/{workOrderId}", method = RequestMethod.PUT)
    public void updateWorkOrder(@PathVariable int workOrderId, @RequestBody WorkOrder workOrder){
        workOrderDao.updateWorkOrder(workOrder);
    }

    @RequestMapping(path="/changerepair/{repairItemId}", method = RequestMethod.PUT)
    public void updateRepair(@PathVariable int repairItemId, @RequestBody Repair repair){
    workOrderDao.updateRepair(repair);
    }

}
