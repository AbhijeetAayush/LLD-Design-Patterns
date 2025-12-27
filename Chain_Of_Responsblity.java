// Order.java

public class Order
{
    private final String username;
    private final String password;
    private final String userrole;
    private final String email;
    private final String ipAddress;
    private final String payload;
    String oid;

    public Order(String username, String password, String userrole, String email, String ipAddress, String payload, String oid)
    {
        this.username = username;
        this.password = password;
        this.userrole = userrole;
        this.email = email;
        this.ipAddress = ipAddress;
        this.payload = payload;
        this.oid = oid;
    }

    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    public String getUserrole()
    {
        return userrole;
    }
    public String getEmail()
    {
        return email;
    }
    public String getIpAddress()
    {
        return ipAddress;
    }
    public String getPayload()
    {
        return payload;
    }
    public String getOid()
    {
        return oid;
    }   

}


// Handler interface
public interface Handler
{
    void setNext(Handler handler);
    void handel(Order order);
}




// Base Handler class
public abstract class BaseHandler implements Habdler
{
    private Handler nextHandler;

    @Override
    public void setNext(Handler handler)
    {
        this.nextHandler=handler;
    }

    @Override
    public void handel(Order order)
    {
        if (nextHandler != null)
        {
            nextHandler.handel(order);
        }
        else
        {
            System.out.println("End of chain, no handler available for Order ID: " + order.oid);
        }
    }

    
}

// Concrete Handler classes
public class AuthenticationHandler extends BaseHandler
{
    @Override
    public void handel(Order order)
    {
        if("validUser".equals(Order.getUsername()) && "validPassword".equals(Order.getPassword()))
        {
            System.out.println("Authentication successful for Order ID: " + order.oid);
            
        }
        else
        {
            System.out.println("Authentication failed for Order ID: " + order.oid);
        }
    }
}

public class AuthorizationHandler extends BaseHandler
{
    @Override
    public void handel(Order order)
    {
        if("roleAdmin".equals(order.getUserrole()))
        {
            System.out.println("Authorization successful for Order ID: " + order.oid);
            
        }
        else
        {
            System.out.println("Authorization failed for Order ID: " + order.oid);
        }
    }
}

public class ValidationHandler extends BaseHandler
{
    @Override
    public void handel(Order order)
    {
        if(order.getPayload() != null && !order.getPayload().isEmpty())
        {
            System.out.println("Validation successful for Order ID: " + order.oid);
            
        }
        else
        {
            System.out.println("Validation failed for Order ID: " + order.oid);
        }
    }
}



// RequestProcessor class
public class RequestProcessor
{
    private Handler chain;

    public RequestProcessor()
    {
        // Setting up the chain of responsibility
        this.chain = new AuthenticationHandler();
        Handler authorizationHandler = new AuthorizationHandler();
        Handler validationHandler = new ValidationHandler();

        chain.setNext(authorizationHandler);
        authorizationHandler.setNext(validationHandler);
    }

    public void process(Order order)
    {
        chain.handel(order);
    }
}