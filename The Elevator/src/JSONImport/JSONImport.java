package JSONImport;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONImport {

    private String fileName;
    private int numberOfFloors;
    private int numberOfElevators;
    private int elevatorCapacity;
    private long timePerFloor;
    private long doorOpenTime;
    private long elevatorTimeOut;

    public JSONImport(String fileName)
    {
        this.fileName = fileName;
        FileReader reader;
        try {
            // Create a FileReader object using your filename
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jObj;

        try {
            // Create a JSONParser using the FileReader
            jObj = (JSONObject) jsonParser.parse(reader);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        }

        Long _numberOfFloors = (Long) jObj.get("numberOfFloors");
        Long _numberOfElevators = (Long) jObj.get("numberOfElevators");
        Long _elevatorCapacity = (Long) jObj.get("elevatorCapacity");
        timePerFloor = (Long) jObj.get("timePerFloor");
        doorOpenTime = (Long) jObj.get("doorOpenTime");
        elevatorTimeOut = (Long) jObj.get("elevatorTimeOut");

        numberOfFloors = Math.toIntExact(_numberOfFloors);
        numberOfElevators = Math.toIntExact(_numberOfElevators);
        elevatorCapacity = Math.toIntExact(_elevatorCapacity);
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }
    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public int getNumberOfElevators() { return numberOfElevators; }
    public void setNumberOfElevators(int numberOfElevators) { this.numberOfElevators = numberOfElevators; }

    public int getElevatorCapacity() { return elevatorCapacity; }
    public void setElevatorCapacity(int elevatorCapacity) { this.elevatorCapacity = elevatorCapacity; }

    public long getTimePerFloor() { return timePerFloor; }
    public void setTimePerFloor(long timePerFloor) { this.timePerFloor = timePerFloor; }

    public long getDoorOpenTime() { return doorOpenTime; }
    public void setDoorOpenTime(long doorOpenTime) { this.doorOpenTime = doorOpenTime; }

    public long getElevatorTimeOut() { return elevatorTimeOut; }
    public void setElevatorTimeOut(long elevatorTimeOut) { this.elevatorTimeOut = elevatorTimeOut; }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
