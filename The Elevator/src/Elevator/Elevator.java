package Elevator;

import Enums.Directionself;
import JSONImport.JSONImport;
import Person.Person;
import Person.PersonImpl;
import gui.ElevatorDisplay;
import gui.ElevatorDisplay.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Stack;

public interface Elevator {


    Directionself direction = null;
    Directionself directionHolder = null;

    int idleTimeCount = 0;
    boolean Currentdirection = false;
    int currentFloorOfTheElevator = 0;

    ArrayList internalPersonList = new ArrayList<PersonImpl>();
    ArrayList floorRequest = new ArrayList<PersonImpl>();
    ArrayList riderRequest = new ArrayList<PersonImpl>();
    StopWatch timeOut = new StopWatch();
    StopWatch waitTime = new StopWatch();
    StopWatch inElevatorWaitTime = new StopWatch();

    JSONImport readFile = new JSONImport("ElevatorJSONinput.json");
    int numberOfFloors = readFile.getNumberOfFloors();
    int numberOfElevators = readFile.getNumberOfElevators();
    int elevatorCapacity = readFile.getElevatorCapacity();
    long timePerFloor = readFile.getTimePerFloor();
    long doorOpenTime = readFile.getDoorOpenTime();
    long elevatorTimeOut = readFile.getElevatorTimeOut();

    Stack hold = null;

    void floorNumberbuttonpressed(int number);

    void addpersontoelevator(PersonImpl p1);

    void removepersontoelevator(PersonImpl p1);

    void addToStack(Stack holderStackPeopleInfo);

    void floorRequestAdd(PersonImpl tempHold);

    void riderRequestAdd(ArrayList tempHold);

    void operateElevator(int elevNum);

    void move(int elevNum);

    void updateDirection(int elevNum);

}
