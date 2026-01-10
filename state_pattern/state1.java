// Order.java (Context)
public class Order {
    private OrderState state;
    private final String orderId;

    public Order(String orderId) {
        this.orderId = orderId;
        this.state = new NewState(this);  // Initial state
        System.out.println("Order " + orderId + " created in NEW state");
    }

    // Called by state classes to change state
    public void setState(OrderState state) {
        this.state = state;
        System.out.println("→ Order " + orderId + " transitioned to " + state.getClass().getSimpleName());
    }

    // Delegate actions to current state
    public void pay() {
        state.pay();
    }

    public void ship() {
        state.ship();
    }

    public void deliver() {
        state.deliver();
    }

    public void cancel() {
        state.cancel();
    }

    public String getStatus() {
        return state.getClass().getSimpleName();
    }

    public String getOrderId() {
        return orderId;
    }
}



// OrderState.java
public interface OrderState {
    void pay();
    void ship();
    void deliver();
    void cancel();
}


// NewState.java
public class NewState implements OrderState {
    private final Order order;

    public NewState(Order order) {
        this.order = order;
    }

    @Override
    public void pay() {
        System.out.println("Payment successful");
        order.setState(new PaidState(order));
    }

    @Override
    public void ship() {
        System.out.println("Cannot ship: Order not paid yet");
    }

    @Override
    public void deliver() {
        System.out.println("Cannot deliver: Order not shipped");
    }

    @Override
    public void cancel() {
        System.out.println("Order cancelled");
        order.setState(new CancelledState(order));
    }
}

// PaidState.java
public class PaidState implements OrderState {
    private final Order order;

    public PaidState(Order order) {
        this.order = order;
    }

    @Override
    public void pay() {
        System.out.println("Already paid");
    }

    @Override
    public void ship() {
        System.out.println("Order shipped");
        order.setState(new ShippedState(order));
    }

    @Override
    public void deliver() {
        System.out.println("Cannot deliver yet: Not shipped");
    }

    @Override
    public void cancel() {
        System.out.println("Order cancelled (refund processed)");
        order.setState(new CancelledState(order));
    }
}

// ShippedState.java
public class ShippedState implements OrderState {
    private final Order order;

    public ShippedState(Order order) {
        this.order = order;
    }

    @Override
    public void pay() { System.out.println("Already paid"); }

    @Override
    public void ship() { System.out.println("Already shipped"); }

    @Override
    public void deliver() {
        System.out.println("Order delivered!");
        order.setState(new DeliveredState(order));
    }

    @Override
    public void cancel() {
        System.out.println("Cannot cancel: Already shipped");
    }
}

// DeliveredState.java (Final)
public class DeliveredState implements OrderState {
    private final Order order;

    public DeliveredState(Order order) {
        this.order = order;
    }

    @Override
    public void pay() { System.out.println("Already delivered"); }
    @Override
    public void ship() { System.out.println("Already delivered"); }
    @Override
    public void deliver() { System.out.println("Already delivered"); }
    @Override
    public void cancel() { System.out.println("Cannot cancel: Already delivered"); }
}

// CancelledState.java (Final)
public class CancelledState implements OrderState {
    private final Order order;

    public CancelledState(Order order) {
        this.order = order;
    }

    @Override
    public void pay() { System.out.println("Order cancelled"); }
    @Override
    public void ship() { System.out.println("Order cancelled"); }
    @Override
    public void deliver() { System.out.println("Order cancelled"); }
    @Override
    public void cancel() { System.out.println("Already cancelled"); }
}



// OrderDemo.java
public class OrderDemo {
    public static void main(String[] args) {
        Order order = new Order("ORD-1001");

        order.pay();        // Valid
        order.ship();       // Valid
        order.deliver();    // Valid

        System.out.println("\nTrying invalid actions:");
        order.cancel();     // Invalid now
        order.pay();        // Invalid

        System.out.println("\nCurrent status: " + order.getStatus());
    }
}