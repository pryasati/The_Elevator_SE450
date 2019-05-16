package Person;

import Enums.*;

public class PersonImpl implements Person {
    String id;
    int desiredFloor;
    Directionself directionc;
    public int currentfloor;


    public PersonImpl(int id, int currentfloor, int desiredFloor, Directionself direction) {
        this.desiredFloor = desiredFloor;
        this.currentfloor = currentfloor;
        this.directionc = direction;
    }

    public PersonImpl() {

    }


    public PersonImpl(String idIn, int floorNum) {
        id = idIn;
        desiredFloor = floorNum;
        //  internalPeopleinList=new ArrayList<ElevatorInterface>();

    }

    public String getId() {
        return id;
    }

    public int getDesiredFloor() {
        return desiredFloor;
    }

    public int getFromFloor() {
        return currentfloor;
    }

    public Directionself getDirection() {
        return directionc;
    }


}
