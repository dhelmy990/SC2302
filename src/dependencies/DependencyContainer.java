package dependencies;

import java.util.List;
import java.util.Scanner;
import canteen.CanteenManager;
import orders.OrderManager;
import queue.QueueManager;
import services.AdminService;
import services.OrderService;
import services.StallManagementService;
import services.UserManagementService;
import services.UserInputHandler;
import simulated.SimulatedData;
import stalls.IStallService;
import stalls.Stall;
import transactions.TxnManager;
import userInterface.Menu.WelcomeMenu;
import users.User;
import services.UserFactory;

public class DependencyContainer {
    public final Scanner scanner = new Scanner(System.in);
    public final List<Stall> stalls = SimulatedData.getSampleStalls();
    public final OrderManager orderManager = new OrderManager();
    public final TxnManager txnManager = new TxnManager();
    public final IStallService canteenManager = new CanteenManager();
    public final List<User> users = SimulatedData.getSampleUsers(canteenManager);
    public final QueueManager queueManager = new QueueManager();
    public final OrderService orderService = new OrderService(orderManager, queueManager, txnManager, canteenManager);
    public final StallManagementService stallManagementService = new StallManagementService(stalls);
    public final UserManagementService userManagementService = new UserManagementService(users, stallManagementService);
    public final UserInputHandler userInputHandler = new UserInputHandler(scanner);
    public final UserFactory userFactory = new UserFactory(canteenManager);
    public final WelcomeMenu welcomeMenu = new WelcomeMenu();
    public final AdminService adminService = new AdminService(
    userManagementService,
    stallManagementService,
    userInputHandler,
    userFactory
);


}