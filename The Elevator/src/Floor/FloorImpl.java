package Floor;

import java.util.ArrayList;
import Person.*;

public class FloorImpl implements Floor{
private int floorNum;
private ArrayList<PersonImpl> waitingforElevator=new ArrayList<>();
private ArrayList<PersonImpl>arrivedPerson=new ArrayList<>();
private ArrayList<PersonImpl>pplinelevator=new ArrayList<>();

//public Floors(int floorNum=fn;)
    public void addArrivedPerson(PersonImpl P)
    {
    //CAL THE RIDE TRAVEL
      arrivedPerson.add(P);
    }

    public void addPerson(PersonImpl P)
    {
//addpersonwaitingforelevator
    }
}
