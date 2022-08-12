package com.techelevator.dao;

import com.techelevator.model.Repair;
import com.techelevator.model.WorkOrder;

import java.util.List;

public interface WorkOrderDao {

    List<WorkOrder> getWorkOrders();

    List<WorkOrder> getWorkOrdersByUserId(int userId);

    List<Repair> getRepairsByWorkOrderId(int workOrderId);
}
