package userInterface.Flow;

import java.util.*;
import dependencies.*;
import services.*;
import stalls.*;
import users.User;

public abstract class Flow {

        public final Scanner scanner;
        public final List<Stall> stalls;
        public final IStallService canteenManager;
        public final List<User> users;
        public final OrderService orderService;
        public final AdminService adminService;
        public final IAccountUpdateService accountUpdateService;

    Flow(DependencyContainer dependencies){
        this.scanner = dependencies.scanner;
        this.stalls = dependencies.getStalls();
        this.canteenManager = dependencies.getCanteenManagerInstance();
        this.users = dependencies.getUsers();
        this.orderService = dependencies.getOrderServiceInstance();
        this.adminService = dependencies.getAdminService();
        this.accountUpdateService = dependencies.getAccountUpdateService();
    }

    abstract void run(User user);
}
