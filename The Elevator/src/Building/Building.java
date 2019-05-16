package Building;

import JSONImport.JSONImport;
import Person.Person;

public interface Building {

    JSONImport readFile = new JSONImport("ElevatorJSONinput.json");
    int numberOfFloors = 0;
    int numberOfElevators = 0;

    void addperson(Person p, int floorNum);
}