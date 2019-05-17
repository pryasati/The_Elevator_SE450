package Elevator;

import Exceptions.InvalidInputExcepetion;
import JSONImport.JSONImport;
import gui.*;
import Person.*;
import Enums.*;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Stack;

import static gui.ElevatorDisplay.Direction.*;

public class ElevatorImpl {
    public Directionself direction;
    public Directionself directionHolder;
    public Directionself directionHolderE1;
    public Directionself directionHolderE4;

    int idleTimeCount = 0;

    public int currentFloorOfTheElevator;
    public int currentflooroftheElevatorE4=1;

    ArrayList internalPersonList = new ArrayList<PersonImpl>();
    ArrayList floorRequest = new ArrayList<PersonImpl>();//holds all floor requests
    ArrayList floorRequest1 = new ArrayList<PersonImpl>();//holds floor requests only for floor 4
    ArrayList floorRequest4 = new ArrayList<PersonImpl>();//holds floor requests only for floor 4
    ArrayList riderRequest = new ArrayList<PersonImpl>();
    ArrayList riderRequest1 = new ArrayList<PersonImpl>();
    ArrayList riderRequest4 = new ArrayList<PersonImpl>();
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

    public void addToStack(Stack holderStackPeopleInfo) {
        if (hold != null) {
            PersonImpl tempPrevHold = (PersonImpl) hold.peek();
        }
        hold = holderStackPeopleInfo;

    }



    public void floorRequestE1Add(PersonImpl tempHold) {

        floorRequest1.add(tempHold);
    }
    public void floorRequestE4Add(PersonImpl tempHold) {

        floorRequest4.add(tempHold);
    }


    //need to move the below function to elevator driver as this will determine which elevator to take control
    public void ElevatorDriver(){

//in here i am segerating floor requests for e1 and e4

        temporaryoperateelevator();


    }






    public void temporaryoperateelevator(){
//check which elevators got request and process their request,but dont set elevator display,Set it for all elevators togther
        //check for elevator 1 if exsisting request is there and which direction it should move

        boolean checkforfloorreqele1= checkexsistingrequestforelevator( 1);
        boolean checkforriderreqele1=checkexistingriderreqforele(1);
        boolean checkforfloorreqele4= checkexsistingrequestforelevator( 4);
        boolean checkforriderreqele4=checkexistingriderreqforele(4);
        if(checkforfloorreqele1==true||checkforriderreqele1==true)
        {
            //determine direction of elevator
            int directions=DetermineDirection(riderRequest1,floorRequest1,this.currentFloorOfTheElevator);

            if(directions==0){

                directionHolder=Directionself.DOWN;
            }
            if (directions==1){
                directionHolder=Directionself.UP;
            }
            if (directions==2){
                directionHolder=Directionself.IDLE;
            }

            operateElevator(riderRequest1,floorRequest1,1,directionHolder,this.currentFloorOfTheElevator);


        }
        else
        {
            directionHolderE1=Directionself.IDLE;
        }
        if(checkforfloorreqele4==true||checkforriderreqele4==true)
        {
            int directions=DetermineDirection(riderRequest4,floorRequest4,this.currentflooroftheElevatorE4);

            if(directions==0){

                directionHolder=Directionself.DOWN;
            }
            if (directions==1){
                directionHolder=Directionself.UP;
            }
            if (directions==2){
                directionHolder=Directionself.IDLE;
            }

            operateElevator(riderRequest4,floorRequest4,4,directionHolder,this.currentflooroftheElevatorE4);

//BOTH ELEVATOR GOT EXECUTED

        }
        else {
            directionHolderE4=Directionself.IDLE;
//tick down idle timer
        }
        Updateallelevatorstatus();

    }

    public void Updateallelevatorstatus(){
        //elevator display
        //1
        if(this.directionHolderE1==Directionself.UP) {
            ElevatorDisplay.getInstance().updateElevator(1, this.currentFloorOfTheElevator, riderRequest1.size(), UP);
        }
        if(this.directionHolderE1==Directionself.DOWN) {
            ElevatorDisplay.getInstance().updateElevator(1, this.currentFloorOfTheElevator, riderRequest1.size(), DOWN);
        }
        if(this.directionHolderE1==Directionself.IDLE) {
            ElevatorDisplay.getInstance().updateElevator(1, this.currentFloorOfTheElevator, riderRequest1.size(), IDLE);
        }
        //4
        //elevator display
        if(this.directionHolderE4==Directionself.UP) {
            ElevatorDisplay.getInstance().updateElevator(4, this.currentflooroftheElevatorE4, riderRequest4.size(), UP);
        }
        if(this.directionHolderE4==Directionself.DOWN) {
            ElevatorDisplay.getInstance().updateElevator(4, this.currentflooroftheElevatorE4, riderRequest4.size(), DOWN);
        }
        if(this.directionHolderE4==Directionself.IDLE) {
            ElevatorDisplay.getInstance().updateElevator(4, this.currentflooroftheElevatorE4, riderRequest4.size(), IDLE);
        }

    }

    public boolean checkexistingriderreqforele(int ele){
        switch (ele){
            case 1:
                if (riderRequest1.size()!=0)
                {
                    return true;
                }
                else{
                    return false;
                }
            case 4:
                if (riderRequest4.size()!=0){
                    return true;
                }
                else {
                    return false;
                }
        }


        return false;
    }

    public boolean checkexsistingrequestforelevator(int ele)
    {
        switch (ele){
            case 1:
                if (floorRequest1.size()!=0)
                {
                    return true;
                }
                else {
                    return false;
                }

            case 4:
                if (floorRequest4.size()!=0)
                {
                    return true;
                }
                else
                {
                    return false;
                }



        }
        return false;
    }



    public int DetermineDirection(ArrayList riderRequest1,ArrayList floorRequest1,int passedinelenumber){
        //check any one rider direction as a rider will not be picked if ele is going in different direction
        if(riderRequest1.size()!=0){
            PersonImpl rider = (PersonImpl) riderRequest1.get(0);
            if(passedinelenumber>rider.getDesiredFloor()){
                return 0;//down
            }
            if(passedinelenumber<rider.getDesiredFloor()){
                return 1;//down
            }
        }
        else if(floorRequest1.size()!=0){
            PersonImpl floorguy = (PersonImpl) floorRequest1.get(0);
            if(passedinelenumber>floorguy.getFromFloor()){
                return 0;//down
            }
            if(passedinelenumber<floorguy.getFromFloor()){
                return 1;//down
            }
        }



        return 2;//idler
    }





    public void operateElevator(ArrayList riderRequest, ArrayList floorRequest, int elevNum, Directionself directionHolder, int passedinelenumber) {


        //new logic
        //check if any type of reqs there
        //check if there are any rider request if yes then check if there are any floor request in the same direction
        //check condition where rider reqs not equals zero
        if (riderRequest.size() == 0) {


            if (floorRequest.size() != 0) {
                //set direction default
                if (directionHolder == null || directionHolder == Directionself.IDLE) {

                    PersonImpl tempop = (PersonImpl) floorRequest.get(0);
                    if (tempop.currentfloor > passedinelenumber) {
                        this.directionHolder = Directionself.UP;

                    } else if (tempop.currentfloor == passedinelenumber) {
                        if (tempop.getDesiredFloor() > passedinelenumber) {
                            this.directionHolder = Directionself.UP;

                        }
                        if (tempop.getDesiredFloor()<passedinelenumber){
                            this.directionHolder = Directionself.DOWN;
                        }

                    } else {
                        this.directionHolder = Directionself.DOWN;

                    }
                }
                //check where this floor guy is as he's the first request and update direction of elevator
                PersonImpl firstreqpreson;
                int i = 0;
                while (floorRequest.size() > i) {
                    floorRequest.get(i);
                    firstreqpreson = (PersonImpl) floorRequest.get(i);
                    //TO check for all the floor request given in the same direction and to check if the elevator cfloorislesser
                    //if yes delegate the floor request to rider request
                    if (firstreqpreson.currentfloor == passedinelenumber && firstreqpreson.getDirection() == this.directionHolder) {
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

                    move(elevNum,passedinelenumber,floorRequest,riderRequest);
                    if(elevNum==1){
                        passedinelenumber=this.currentFloorOfTheElevator;
                    }
                    else if(elevNum==4)
                    {
                        passedinelenumber=this.currentflooroftheElevatorE4;
                    }
                    updateDirection(elevNum,passedinelenumber,riderRequest,floorRequest);
                }

            }


            if (floorRequest.size() == 0) {
                updateDirection(elevNum,passedinelenumber,riderRequest,floorRequest);


            }
        }
//to add a big block of code to recheclk
        if (floorRequest.size() != 0 && riderRequest.size() != 0) {
            PersonImpl firstreqpreson;
            int i = 0;
            while (floorRequest.size() > i) {
                floorRequest.get(i);
                firstreqpreson = (PersonImpl) floorRequest.get(i);
                //TO check for all the floor request given in the same direction and to check if the elevator cfloorislesser
                //if yes delegate the floor request to rider request
                if (firstreqpreson.currentfloor == passedinelenumber && firstreqpreson.getDirection() == this.directionHolder) {
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
            updateDirection(elevNum,passedinelenumber,riderRequest,floorRequest);
            PersonImpl rideperson;
            boolean simpleflag = false;
            //CHECK THE RIDE PERSON IN RECURSIVE LOOP
            int i = 0;
            while (riderRequest.size() > i) {
                riderRequest.get(i);
                rideperson = (PersonImpl) riderRequest.get(i);
                //TO check for all the floor request given in the same direction and to check if the elevator cfloorislesser
                //if yes delegate the floor request to rider request
                if (rideperson.getDesiredFloor() == passedinelenumber && rideperson.getDirection() == this.directionHolder) {
                    riderRequest.remove(i);
                    simpleflag = true;
                    ElevatorDisplay.getInstance().openDoors(elevNum);
                    try {
                        if (rideperson.getDesiredFloor() == 1) {
                            updateDirection(elevNum,passedinelenumber,riderRequest,floorRequest);
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

                move(elevNum,passedinelenumber,floorRequest,riderRequest);
                if (elevNum==1){
                    passedinelenumber=this.currentFloorOfTheElevator;
                }
                else if(elevNum==4){
                    passedinelenumber=this.currentflooroftheElevatorE4;
                }
                updateDirection(elevNum,passedinelenumber,riderRequest,floorRequest);
                simpleflag = false;
            }


        }
        updateDirection(elevNum,passedinelenumber,riderRequest,floorRequest);
    }

    public void move(int elevNum,int passedinele,ArrayList floorRequest,ArrayList riderRequest) {
        //move depending up on the direction stated by update elevator command.
        if (floorRequest.size() == 0 && riderRequest.size() == 0) {
            if (passedinele == 1) {
                // ElevatorDisplay.getInstance().updateElevator(elevNum, passedinele, riderRequest.size(), ElevatorDisplay.Direction.IDLE);
                directionHolder = Directionself.IDLE;
            }
            if (passedinele != 1) {

                try {
                    Thread.sleep(timePerFloor);
                    passedinele--;
                    directionHolder=Directionself.DOWN;
                    //   ElevatorDisplay.getInstance().updateElevator(elevNum, passedinele, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
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
            passedinele--;
            //ElevatorDisplay.getInstance().updateElevator(elevNum, passedinele, riderRequest.size(), ElevatorDisplay.Direction.DOWN);

        } else if (passedinele == numberOfFloors) {
            if (floorRequest.size() != 0 || riderRequest.size() != 0) {
                if (directionHolder == Directionself.UP) {
                    directionHolder = Directionself.DOWN;

                    try {
                        Thread.sleep(timePerFloor);
                        passedinele--;
                        // ElevatorDisplay.getInstance().updateElevator(elevNum, passedinele, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                        directionHolder = Directionself.UP;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                Thread.sleep(timePerFloor);
                passedinele++;
                if (passedinele == numberOfFloors) {
                    if (floorRequest.size() != 0 || riderRequest.size() != 0) {
                        //ElevatorDisplay.getInstance().updateElevator(elevNum, passedinele, riderRequest.size(), ElevatorDisplay.Direction.DOWN);
                        directionHolder = Directionself.DOWN;
                    } else if (floorRequest.size() == 0 || riderRequest.size() == 0) {
                        //ElevatorDisplay.getInstance().updateElevator(elevNum, passedinele, riderRequest.size(), ElevatorDisplay.Direction.IDLE);
                        directionHolder = Directionself.IDLE;
                    }


                } else {

                    directionHolder = Directionself.UP;
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(elevNum==1){
            this.currentFloorOfTheElevator=passedinele;
            this.directionHolderE1=directionHolder;
        }
        else if (elevNum==4){
            this.currentflooroftheElevatorE4=passedinele;
            this.directionHolderE4=directionHolder;
        }

    }

    public void updateDirection(int elevNum,int passedinelenumber,ArrayList riderRequest,ArrayList floorRequest) {

        //if floor requests-current location of request by rider on the floors above the current location of elevator check 20 floor
//If there are riders do not update direction
        //default set it to up direction

        if (riderRequest.size() == 0) {
            if (floorRequest.size() != 0) {
                int fl = 0;
                while (fl < floorRequest.size()) {
                    PersonImpl temp = (PersonImpl) floorRequest.get(fl);
                    if (temp.currentfloor > passedinelenumber && temp.getDirection() != directionHolder) {
                        break;
                    }
                    if (temp.currentfloor == passedinelenumber && temp.getDirection() == directionHolder) {
                        break;
                    }
                    if(temp.currentfloor < passedinelenumber && temp.getDirection() == directionHolder){
                        break;
                    }
                    else {
                        //flip direction and break?
                        if (directionHolder == Directionself.UP) {
                            ElevatorDisplay.getInstance().updateElevator(elevNum, passedinelenumber, riderRequest.size(), DOWN);
                            this.directionHolder = Directionself.DOWN;
                        } else if (directionHolder == Directionself.DOWN) {
                            ElevatorDisplay.getInstance().updateElevator(elevNum, passedinelenumber, riderRequest.size(), UP);
                            this.directionHolder = Directionself.UP;
                        }

                    }
                    fl++;
                }


            }
            if (floorRequest.size() == 0) {
                if (idleTimeCount != 10) {
                    idleTimeCount++;
                    ElevatorDisplay.getInstance().updateElevator(elevNum, passedinelenumber, riderRequest.size(), IDLE);
                    this.directionHolder = Directionself.IDLE;
                    try {
                        Thread.sleep(timePerFloor);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (idleTimeCount == 10) {
                    if (passedinelenumber != 1) {
                        ElevatorDisplay.getInstance().updateElevator(elevNum, passedinelenumber, riderRequest.size(), DOWN);
                        this.directionHolder = Directionself.DOWN;

                    }

                }
            }
        }
        if (riderRequest.size() != 0) {
            PersonImpl tempride = (PersonImpl) riderRequest.get(0);
            if (tempride.getDirection().equals(Directionself.UP)) {
                ElevatorDisplay.getInstance().updateElevator(elevNum, passedinelenumber, riderRequest.size(), UP);
                this.directionHolder = Directionself.UP;
            } else if (tempride.getDirection().equals(Directionself.DOWN)) {
                ElevatorDisplay.getInstance().updateElevator(elevNum, passedinelenumber, riderRequest.size(), DOWN);
                this.directionHolder = Directionself.DOWN;
            }
        }
        //if there are riders in the elevator
        //if none for above two condition flip direction

    }

}