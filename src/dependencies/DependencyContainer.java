package dependencies;

import java.util.*;
import canteen.CanteenManager;
import orders.OrderManager;
import queue.QueueManager;
import queue.QueueService;
import queue.StallOrderService;
import queue.UserOrderService;
import services.AdminService;
import services.OrderService;
import services.StallManagementService;
import services.UserManagementService;
import services.UserInputHandler;
import simulated.SimulatedData;
import stalls.Stall;
import transactions.TxnManager;
import users.User;
import services.UserFactory;
import queue.CompletionService;


public class DependencyContainer {
    
    private final OrderService orderService;
    private final CanteenManager canteenManager;
    private final AdminService adminService;

    // Simulation Data
    private final List<Stall> stalls;
    private final List<User> users;

    public final Scanner scanner = new Scanner(System.in);
    public final UserInputHandler userInputHandler = new UserInputHandler(scanner);

    public DependencyContainer(){
        OrderManager orderManager = new OrderManager();
        TxnManager txnManager = new TxnManager();
        QueueManager queueManager = new QueueManager();
        this.canteenManager = new CanteenManager();
        this.orderService = new OrderService(orderManager, queueManager, txnManager, canteenManager);
        // Load simulated data
        this.stalls = SimulatedData.getSampleStalls();
        this.users = SimulatedData.getSampleUsers(canteenManager);
        // Set up adminService
        StallManagementService stallManagementService = new StallManagementService(stalls);
        UserManagementService userManagementService = new UserManagementService(users, stallManagementService);
        this.adminService = new AdminService(userManagementService, stallManagementService, userInputHandler);
    }

    public CanteenManager getCanteenManagerInstance(){
        return this.canteenManager;
    }

    public OrderService getOrderServiceInstance(){
        return this.orderService;
    }

    public AdminService getAdminService(){
        return adminService;
    }

    public List<Stall> getStalls(){
        return stalls;
    }

    public List<User> getUsers(){
        return users;
    }


    public final OrderService orderService = new OrderService(
            orderManager, queueService, txnManager, canteenManager);
    public final QueueManager queueManager = new QueueManager(queueService, completionService, userOrderService, stallOrderService
    );

}