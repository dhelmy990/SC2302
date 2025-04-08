package userInterface.Flow;

import java.util.List;
import java.util.Scanner;

import dependencies.*;
import orders.OrderManager;
import services.AdminService;
import services.OrderService;
import stalls.IStallService;
import stalls.Stall;
import transactions.TxnManager;
import userInterface.Menu.WelcomeMenu;
import users.User;

public abstract class Flow {

        public final Scanner scanner;
        public final List<Stall> stalls;
        public final OrderManager orderManager;
        public final TxnManager txnManager;
        public final IStallService canteenManager;
        public final List<User> users;
        public final OrderService orderService;
        public final AdminService adminService;
        public final WelcomeMenu welcomeMenu;

    Flow(DependencyContainer dependencies){
        this.scanner = dependencies.scanner;
        this.stalls = dependencies.stalls;
        this.orderManager = dependencies.orderManager;
        this.txnManager = dependencies.txnManager;
        this.canteenManager = dependencies.canteenManager;
        this.users = dependencies.users;
        this.orderService = dependencies.orderService;
        this.adminService = dependencies.adminService;
        this.welcomeMenu = dependencies.welcomeMenu;
    }

    abstract void run(User user);
}
