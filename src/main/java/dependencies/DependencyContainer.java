package dependencies;

import java.util.*;
import orders.OrderManager;
import queue.*;
import services.*;
import simulated.SimulatedData;
import stalls.Stall;
import transactions.TxnManager;
import users.User;

public class DependencyContainer {

    private final OrderService orderService;
    private final StallManagementService stallManagementService;
    private final AdminService adminService;
    private final CompletionService completionService;

    private final List<Stall> stalls;
    private final List<User> users;

    public final Scanner scanner = new Scanner(System.in);
    private final ITextInputHandler textInputHandler;
    private final IBooleanInputHandler booleanInputHandler;
    private final INumericInputHandler numericInputHandler;
    private final IDuplicateCheckService duplicateCheckService;
    private final IAccountUpdateService accountUpdateService;
    private final AuthenticationService authenticationService;
    private final IStallOrderService stallOrderService;
    private final UserOrderService userOrderService;


    public DependencyContainer() {
        OrderManager orderManager = new OrderManager();
        TxnManager txnManager = new TxnManager();
        IWaitTimeEstimator estimator = new SimpleWaitTimeEstimator();
        QueueManager queueService = new QueueManager(estimator);
        
        this.userOrderService = new UserOrderService(queueService);
        this.stallOrderService = new StallOrderService(queueService);
        UserInputHandler userInputHandler = new UserInputHandler(scanner);
        this.textInputHandler = userInputHandler;
        this.booleanInputHandler = userInputHandler;
        this.numericInputHandler = userInputHandler;
        this.stalls = SimulatedData.getSampleStalls();
        this.stallManagementService = new StallManagementService(stalls);
        this.orderService = new OrderService(orderManager, queueService, userOrderService, stallOrderService, txnManager, stallManagementService);
        this.users = SimulatedData.getSampleUsers(stallManagementService);
        // Set up adminService
        StallManagementService stallManagementService = new StallManagementService(stalls);
        UserManagementService userManagementService = new UserManagementService(users, stallManagementService);

        this.completionService = new CompletionService(queueService);
        this.duplicateCheckService = new DuplicateCheckService();
        this.accountUpdateService = new AccountUpdateService(textInputHandler, booleanInputHandler,
                duplicateCheckService);
        this.authenticationService = new AuthenticationService(users, textInputHandler, duplicateCheckService);
        this.adminService = new AdminService(userManagementService, stallManagementService, textInputHandler,
                booleanInputHandler, accountUpdateService, users);
    }

    public StallManagementService getStallManagementServiceInstance() {
        return this.stallManagementService;
    }

    public OrderService getOrderServiceInstance() {
        return this.orderService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public IAccountUpdateService getAccountUpdateService() {
        return this.accountUpdateService;
    }

    public CompletionService getCompletionServiceInstance() {
        return completionService;
    }

    public List<Stall> getStalls() {
        return stalls;
    }

    public List<User> getUsers() {
        return users;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    public ITextInputHandler getTextInputHandler(){
        return textInputHandler;
    }
    public INumericInputHandler getNumericInputHandler(){
        return numericInputHandler;
    }

    public UserOrderService getUserOrderService(){
        return userOrderService;
    }

}