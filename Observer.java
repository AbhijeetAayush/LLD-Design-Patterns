class Order
{
    String oid;
    String pid;
    String uid;
    Order(String oid, String pid, String uid)
    {
        this.oid = oid;
        this.pid = pid;
        this.uid = uid;
    }
}


// Observer interface
public interface Observer
{
    void update(Order order);
}



// Subject interface
public interface Subject
{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Order order);
}



/* Concrete Observer classes */

//InventoryClass
public class InventoryClass implements Observer
{
    @Override
    public void update(Order order)
    {
        System.out.println("InventoryClass notified about Order ID: " + order.oid);
        // Additional logic to update inventory based on the order can be added here
    }
}


//PaymentClass
public class PaymentClass implements Observer
{
    @Override
    public void update(Order order)
    {
        System.out.println("PaymentClass notified about Order ID: " + order.oid);
        // Additional logic to process payment based on the order can be added here
    }
}


//SendEmailClass
public class SendEmailClass implements Observer
{
    String emailprovider;

    public SendEmailClass(String emailprovider)
    {
        this.emailprovider = emailprovider;
    }

    @Override
    public void update(Order order)
    {
        System.out.println("SendEmailClass notified about Order ID: " + order.oid);
        // Additional logic to send email based on the order can be added here
    }
}


//RecordAnalyticsClass
public class RecordAnalyticsClass implements Observer
{
    @Override
    public void update(Order order)
    {
        System.out.println("RecordAnalyticsClass notified about Order ID: " + order.oid);
        // Additional logic to record analytics based on the order can be added here
    }
}



/* Concrete Subject class */
public class SubjectClass implements Subject
{
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Order order)
    {
        for (Observer observer : observers)
        {
            observer.update(order);
        }
    }
}