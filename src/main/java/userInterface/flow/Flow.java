package userInterface.flow;

import java.util.*;
import dependencies.*;
import services.*;
import stalls.*;
import users.User;

public abstract class Flow {

        private final INumericInputHandler numericInputHandler;
        private final ITextInputHandler textInputHandler;
        public final List<Stall> stalls;
        public final IStallService canteenManager;
        public final List<User> users;
        public final OrderService orderService;
        public final AdminService adminService;
        public final IAccountUpdateService accountUpdateService;

    Flow(DependencyContainer dependencies){
        this.numericInputHandler = dependencies.getNumericInputHandler();
        this.textInputHandler = dependencies.getTextInputHandler();
        this.stalls = dependencies.getStalls();
        this.canteenManager = dependencies.getStallManagementServiceInstance();
        this.users = dependencies.getUsers();
        this.orderService = dependencies.getOrderServiceInstance();
        this.adminService = dependencies.getAdminService();
        this.accountUpdateService = dependencies.getAccountUpdateService();
    }

    protected INumericInputHandler getNumericInputHandler() {
        return numericInputHandler;
    }

    protected ITextInputHandler getTextInputHandler() {
        return textInputHandler;
    }

    abstract void run(User user);
}


