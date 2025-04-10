package dependencies;

import java.util.*;
import canteen.CanteenManager;
import orders.OrderManager;
import queue.*;
import services.*;
import simulated.SimulatedData;
import stalls.Stall;
import transactions.TxnManager;
import users.User;

public class DependencyContainer {

    private final OrderService orderService;
    private final CanteenManager canteenManager;
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
    private final IUserOrderService userOrderService;


    public DependencyContainer() {
        OrderManager orderManager = new OrderManager();
        TxnManager txnManager = new TxnManager();
        IWaitTimeEstimator estimator = new SimpleWaitTimeEstimator();
        QueueService queueService = new QueueService(estimator);
        this.canteenManager = new CanteenManager();
        this.userOrderService = new UserOrderService(queueService);
        this.stallOrderService = new StallOrderService(queueService);
        this.orderService = new OrderService(orderManager, queueService, userOrderService, stallOrderService, txnManager, canteenManager);
        UserInputHandler userInputHandler = new UserInputHandler(scanner);
        this.textInputHandler = userInputHandler;
        this.booleanInputHandler = userInputHandler;
        this.numericInputHandler = userInputHandler;
        this.stalls = SimulatedData.getSampleStalls();
        this.users = SimulatedData.getSampleUsers(canteenManager);
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

    public CanteenManager getCanteenManagerInstance() {
        return this.canteenManager;
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

}