package Person;

import Enums.Directionself;

public interface Person {

    String id = null;
    int desiredFloor = 0;
    Directionself directionc = null;
    int currentfloor = 0;

    String getId();

    int getDesiredFloor();

    int getFromFloor();

    Directionself getDirection();

}
