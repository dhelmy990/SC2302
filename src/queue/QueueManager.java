package queue;

import java.util.Map;

public class QueueManager {
    private static QueueManager instance;
    private final IQueueService queueService;
    private final CompletionService completionService;
    private final UserOrderService userOrderService;
    private final StallOrderService stallOrderService;

    public QueueManager(IQueueService queueService, CompletionService completionService,
            UserOrderService userOrderService, StallOrderService stallOrderService) {
        this.queueService = queueService;
        this.completionService = completionService;
        this.userOrderService = userOrderService;
        this.stallOrderService = stallOrderService;
    }

    public static QueueManager getInstance(IQueueService queueService, CompletionService completionService,
            UserOrderService userOrderService, StallOrderService stallOrderService) {
        if (instance == null) {
            instance = new QueueManager(queueService, completionService, userOrderService, stallOrderService);
        }
        return instance;
    }

    public IQueueService getQueueService() {
        return queueService;
    }

    public CompletionService getCompletionService() {
        return completionService;
    }

    public UserOrderService getUserService() {
        return userOrderService;
    }

    public StallOrderService getStallService() {
        return stallOrderService;
    }
}