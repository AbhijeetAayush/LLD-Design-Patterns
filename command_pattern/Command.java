// Command interface

public interface Command
{
    public void execute();
}



// Receiver class
class Light
{
    public void turnOn()
    {
        System.out.println("The light is on");
    }
    public void turnOff()
    {
        System.out.println("The light is off");
    }
}



// Concrete Command classes
class LightOnCommand implements Command
{
    Light light;
    public LightOnCommand(Light light)
    {
        this.light = light;
    }   
    @Override
    public void execute()
    {
        light.turnOn();
    }
}


// Concrete Command classes
class LightOffCommand implements Command
{
    Light light;
    public LightOffCommand(Light light)
    {
        this.light = light;
    }   
    @Override
    public void execute()
    {
        light.turnOff();
    }
}



// Invoker class    
class RemoteControl
{
    Command slot;

    public RemoteControl(Command slot)
    {
        this.slot = slot;
    }

    public void setCommand(Command command)
    {
        this.slot = command;
    }

    public void pressButton()
    {
        slot.execute();
    }
}




// Client class-wires up everything
class Client
{
    public static void main(String args[])
    {
        Light light = new Light();

        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        RemoteControl remote = new RemoteControl(lightOn);
        remote.pressButton();

        remote.setCommand(lightOff);
        remote.pressButton();
    }
}