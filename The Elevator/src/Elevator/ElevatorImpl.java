package Elevator;

import Exceptions.InvalidInputExcepetion;
import JSONImport.JSONImport;
import gui.*;
import Person.*;
import Enums.*;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Stack;

public class ElevatorImpl implements Elevator {
    public Directionself direction;
    public Directionself directionHolder;

    int idleTimeCount = 0;
    boolean Currentdirection;
    public int currentFloorOfTheElevator;

    ArrayList internalPersonList = new ArrayList<PersonImpl>();
    ArrayList floorRequest = new ArrayList<PersonImpl>();
    ArrayList riderRequest = new ArrayList<PersonImpl>();
    StopWatch timeOut = new StopWatch();
    StopWatch waitTime = new StopWatch();
    StopWatch inElevatorWaitTime = new StopWatch();

    JSONImport readFile = new JSONImport("ElevatorJSONinput.json");
    private int numberOfFloors = readFile.getNumberOfFloors();
    private int numberOfElevators = readFile.getNumberOfElevators();
    private int elevatorCapacity = readFile.getElevatorCapacity();
    private long timePerFloor = readFile.getTimePerFloor();
    private long doorOpenTime = readFile.getDoorOpenTime();
    private long elevatorTimeOut = readFile.getElevatorTimeOut();

    Stack hold;


    public ElevatorImpl(int currentFloorOfElevator) {
        this.currentFloorOfTheElevator = currentFloorOfElevator;
    }

    public void floorNumberbuttonpressed(int number) {
        if (number == currentFloorOfTheElevator) {
            return;
        }

    }

    public void addpersontoelevator(PersonImpl p1) {
        this.internalPersonList.add(p1);
    }

    public void removepersontoelevator(PersonImpl p1) {
        this.internalPersonList.remove(p1);
    }

    public void addToStack(Stack holderStackPeopleInfo) {
        if (hold != null) {
            PersonImpl tempPrevHold = (PersonImpl) hold.peek();
        }
        hold = holderStackPeopleInfo;

    }

    public void floorRequestAdd(PersonImpl tempHold) {

        floorRequest.add(tempHold);
    }

    public void riderRequestAdd(ArrayList tempHold) {
        riderRequest.add(tempHold);
    }


    //movercode

    public void operateElevator(int elevNum) {


        //new logic
        //check if any type of reqs there
        //check if there are any rider request if yes then check if there are any floor request in the same direction
        //check condition where rider reqs not equals zero
        if (riderRequest.size() == 0) {


            if (floorRequest.size() != 0) {
                //set direction default
                if (directionHolder == null || directionHolder == Directionself.IDLE) {

                    PersonImpl tempop = (PersonImpl) floorRequest.get(0);
                    if (tempop.currentfloor > this.currentFloorOfTheElevator) {
                        directionHolder = Directionself.UP;
                        idleTimeCount = 0;
                    } else if (tempop.currentfloor == this.currentFloorOfTheElevator) {
                        if (tempop.getDesiredFloor() > this.currentFloorOfTheElevator) {
                            directionHolder = Directionself.UP;
                            idleTimeCount = 0;
                        }

                    } else {
                        directionHolder = Directionself.DOWN;

                    }
                }
                //check where this floor guy is as he's the first request and update direction of elevator
                PersonImpl firstreqpreson = new PersonImpl();
                int i = 0;
                while (floorRequest.size() > i) {
                    floorRequest.get(i);
                    firstreqpreson = (PersonImpl) floorRequest.get(i);
                    //TO check for all the floor request given in the same direction and to check if the elevator cfloorislesser
                    //if yes delegate the floor request to rider request
                    if (firstreqpreson.currentfloor == this.currentFloorOfTheElevator && firstreqpreson.getDirection() == directionHolder) {
                        floorRequest.remove(i);
                        ElevatorDisplay.getInstance().openDoors(elevNum);
                        try {
                            riderRequest.add(firstreqpreson);
                            Thread.sleep(doorOpenTime);

                            ElevatorDisplay.getInstance().closeDoors(elevNum);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    i++;

                }


                if (riderRequest.size() == 0) {

                    move(elevNum);
                    updateDirection(elevNum);
                }

            }


            if (floorRequest.size() == 0) {
                updateDirection(elevNum);
                if (idleTimeCount == 10) {
                    move(elevNum);
                }

            }
        }
//to add a big block of code to recheclk
        if (floorRequest.size() != 0 && riderRequest.size() != 0) {
            PersonImpl firstreqpreson = new PersonImpl();
            int i = 0;
            while (floorRequest.size() > i) {
                floorRequest.get(i);
                firstreqpreson = (PersonImpl) floorRequest.get(i);
                //TO check for all the floor request given in the same direction and to check if the elevator cfloorislesser
                //if yes delegate the floor request to rider request
                if (firstreqpreson.currentfloor == this.currentFloorOfTheElevator && firstreqpreson.getDirection() == directionHolder) {
                    floorRequest.remove(i);
                    ElevatorDisplay.getInstance().openDoors(elevNum);
                    try {
                        riderRequest.add(firstreqpreson);
                        Thread.sleep(doorOpenTime);

                        ElevatorDisplay.getInstance().closeDoors(elevNum);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i++;

            }
        }
        //END Code block
        if (riderRequest.size() != 0) {
            updateDirection(elevNum);
            PersonImpl rideperson = new PersonImpl();
            boolean simpleflag = false;
            //CHECK THE RIDE PERSON IN RECURSIVE LOOP
            int i = 0;
            while (riderRequest.size() > i) {
                riderRequest.get(i);
                rideperson = (PersonImpl) riderRequest.get(i);
                //TO check for all the floor request given in the same direction and to check if the elevator cfloorislesser
                //if yes delegate the floor request to rider request
                if (rideperson.getDesiredFloor() == this.currentFloorOfTheElevator && rideperson.getDirection() == directionHolder) {
                    riderRequest.remove(i);
                    simpleflag = true;
                    ElevatorDisplay.getInstance().openDoors(elevNum);
                    try {
                        if (rideperson.getDesiredFloor() == 1) {
                            updateDirection(elevNum);
                        }
                        Thread.sleep(doorOpenTime);

                        ElevatorDisplay.getInstance().closeDoors(elevNum);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i++;

            }

            //DONE CHECKING
            if (simpleflag == true) {
            } else {

                move(elevNum);
                updateDirection(elevNum);
                simpleflag = false;
            }


        }
    }

    public void move(int elevNum) {
        //move depending up on the direction stated by update elevator command.
        if (floorRequest.size() == 0 && riderRequest.size() == 0) {
            if (this.currentFloorOfTheElevator == 1) {
                ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.IDLE);
                directionHolder = Directionself.IDLE;
            }
            if (this.currentFloorOfTheElevator != 1) {

                try {
                    Thread.sleep(timePerFloor);
                    this.currentFloorOfTheElevator--;

                    ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else if (directionHolder == Directionself.DOWN) {
            try {
                Thread.sleep(timePerFloor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.currentFloorOfTheElevator--;
            ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.DOWN);

        } else if (this.currentFloorOfTheElevator == numberOfFloors) {
            if (floorRequest.size() != 0 || riderRequest.size() != 0) {
                if (directionHolder == Directionself.UP) {
                    directionHolder = Directionself.DOWN;

                    try {
                        Thread.sleep(timePerFloor);
                        this.currentFloorOfTheElevator--;
                        ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                        directionHolder = Directionself.UP;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                Thread.sleep(timePerFloor);
                this.currentFloorOfTheElevator++;
                if (this.currentFloorOfTheElevator == numberOfFloors) {
                    if (floorRequest.size() != 0 || riderRequest.size() != 0) {
                        ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                        directionHolder = Directionself.DOWN;
                    } else if (floorRequest.size() == 0 || riderRequest.size() == 0) {
                        ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.IDLE);
                        directionHolder = Directionself.IDLE;
                    }


                } else {
                    ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.UP);
                    directionHolder = Directionself.UP;
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateDirection(int elevNum) {

        //if floor requests-current location of request by rider on the floors above the current location of elevator check 20 floor
//If there are riders do not update direction
        //default set it to up direction

        if (riderRequest.size() == 0) {
            if (floorRequest.size() != 0) {
                int fl = 0;
                while (fl < floorRequest.size()) {
                    PersonImpl temp = (PersonImpl) floorRequest.get(fl);
                    if (temp.currentfloor > this.currentFloorOfTheElevator && temp.getDirection() != directionHolder) {
                        break;
                    }
                    if (temp.currentfloor == this.currentFloorOfTheElevator && temp.getDirection() == directionHolder) {
                        break;
                    }
                    if(temp.currentfloor < this.currentFloorOfTheElevator && temp.getDirection() == directionHolder){
                        break;
                    }
                    else {
                        //flip direction and break?
                        if (directionHolder == Directionself.UP) {
                            ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                            directionHolder = Directionself.DOWN;
                        } else if (directionHolder == Directionself.DOWN) {
                            ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.UP);
                            directionHolder = Directionself.UP;
                        }

                    }
                }


            }
            if (floorRequest.size() == 0) {
                if (idleTimeCount != 10) {
                    idleTimeCount++;
                    ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.IDLE);
                    directionHolder = Directionself.IDLE;
                    try {
                        Thread.sleep(timePerFloor);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (idleTimeCount == 10) {
                    if (this.currentFloorOfTheElevator != 1) {
                        ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                        directionHolder = Directionself.DOWN;

                    }

                }
            }
        }
        if (riderRequest.size() != 0) {
            PersonImpl tempride = (PersonImpl) riderRequest.get(0);
            if (tempride.getDirection().equals(Directionself.UP)) {
                ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.UP);
                directionHolder = Directionself.UP;
            } else if (tempride.getDirection().equals(Directionself.DOWN)) {
                ElevatorDisplay.getInstance().updateElevator(elevNum, this.currentFloorOfTheElevator, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                directionHolder = Directionself.DOWN;
            }
        }
        //if there are riders in the elevator
        //if none for above two condition flip direction

    }

}