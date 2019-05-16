
import org.apache.commons.lang3.time.StopWatch;
import gui.*;
import Elevator.*;
import Person.*;
import Building.*;
import Enums.Directionself;

import java.util.ArrayList;
import java.util.Stack;
import java.lang.String;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        BuildingImpl bui = new BuildingImpl();
        ElevatorDisplay.getInstance().initialize(bui.numberOfFloors);
        for (int i = 1; i <= bui.numberOfElevators; i++) {
            ElevatorDisplay.getInstance().addElevator(i, 1);
        }

        Test1();
        Test2();
        Test3();
        //Test4(); //issues running two elevators at the same time.

    }

    private static void Test1()
    {
        StopWatch timer = new StopWatch();
        Directionself direction;
        int TotalNumoffloorsPresent = 20;
        int FloorNumber = 10;
        String personName = "Ramesh";
        String userDirectionPreference = "Up";
        PersonImpl per = new PersonImpl(personName, FloorNumber);
        //interact with UI interface create sep class
        ElevatorDisplay.getInstance().initialize(TotalNumoffloorsPresent);
        //end with UI Interface
        ArrayList<PersonImpl> pplinelevator;
        pplinelevator = new ArrayList<>();
        Stack stack = new Stack();
        Stack pplstack = new Stack();
        ArrayList<PersonImpl> Floorrequests;
        Floorrequests = new ArrayList<>();
        ArrayList<PersonImpl> RiderRequests;
        RiderRequests = new ArrayList<>();



        //from.push(0);
//set default
        direction = Directionself.IDLE;
        int x = 1;
        //sm.Command();



        ElevatorImpl e1 = new ElevatorImpl(1);
        PersonImpl p1 = new PersonImpl(1, 1, 10, Directionself.UP);

        for (int j = 0; j < 60; j++) {

            switch (j) {

                case 0:

                    pplstack.push(p1);

                    e1.addToStack(pplstack);
                    e1.floorRequestAdd(p1);

                    break;
                case 12:
                    System.out.println("0: Person 1 enters a floor request (UP) from floor 1");
                    break;
                case 24:
                    System.out.println("23: Elevator timed out and going back to floor 1");
                case 34:
                    System.out.println("33: Elevator 1 arrives at floor 1");


            }
            e1.operateElevator(1);
        }
    }

    private static void Test2()
    {
        Directionself direction;

        Stack pplstack = new Stack();


        //from.push(0);
//set default
        direction = Directionself.IDLE;

        ElevatorImpl e2 = new ElevatorImpl(1);
        PersonImpl p1 = new PersonImpl(1, 20, 5, Directionself.DOWN);
        PersonImpl p2 = new PersonImpl(2, 15, 19, Directionself.UP);

        for (int j = 0; j < 60; j++) {

            switch (j) {

                case 0:

                    pplstack.push(p1);
                    e2.addToStack(pplstack);
                    e2.floorRequestAdd(p1);
                    break;
                case 5:
                    pplstack.push(p2);
                    e2.addToStack(pplstack);
                    e2.floorRequestAdd(p2);
                    break;

            }
            e2.operateElevator(2);
        }
    }

    private static void Test3()
    {
        Directionself direction;

        Stack pplstack = new Stack();


        //from.push(0);
//set default
        direction = Directionself.IDLE;


        ElevatorImpl e3 = new ElevatorImpl(1);
        PersonImpl p1 = new PersonImpl(1, 20, 1, Directionself.DOWN);
        PersonImpl p2 = new PersonImpl(2, 10, 1, Directionself.DOWN);

        for (int j = 0; j < 60; j++) {

            switch (j) {

                case 0:

                    pplstack.push(p1);
                    e3.addToStack(pplstack);
                    e3.floorRequestAdd(p1);
                    break;
                case 25:
                    pplstack.push(p2);
                    e3.addToStack(pplstack);
                    e3.floorRequestAdd(p2);
                    break;

            }
            e3.operateElevator(3);
        }
    }

    private static void Test4()
    {
        Directionself direction;

        Stack pplstack = new Stack();


        //from.push(0);
//set default
        direction = Directionself.IDLE;

        ElevatorImpl e1 = new ElevatorImpl(1);
        ElevatorImpl e4 = new ElevatorImpl(1);
        PersonImpl p1 = new PersonImpl(1, 1, 10, Directionself.UP);
        PersonImpl p2 = new PersonImpl(2, 8, 17, Directionself.UP);
        PersonImpl p3 = new PersonImpl(3, 1, 9, Directionself.UP);
        int updateelevator=0;
        for (int j = 0; j < 60; j++) {

            switch (j) {

                case 0:

                    pplstack.push(p1);
                    e1.addToStack(pplstack);
                    e1.floorRequestAdd(p1);
                    updateelevator=1;
                    break;
                case 5:
                    pplstack.push(p2);
                    e1.addToStack(pplstack);
                    e1.floorRequestAdd(p2);
                    updateelevator=1;
                    break;
                case 6:
                    pplstack.push(p3);
                    e4.addToStack(pplstack);
                    e4.floorRequestAdd(p3);
                    updateelevator=4;
                    break;

            }
            e1.operateElevator(updateelevator);
            //e4.operateElevator(4, direction, pplstack);
        }
    }

}
