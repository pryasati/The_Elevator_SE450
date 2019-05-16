package Building;

import Person.*;
import JSONImport.JSONImport;

public class BuildingImpl
{

    JSONImport readFile = new JSONImport("ElevatorJSONinput.json");
    public final int numberOfFloors = readFile.getNumberOfFloors();
    public int numberOfElevators = readFile.getNumberOfElevators();

    public void addperson(Person p, int floorNum) {
        //adding a person to floor list


    }
}