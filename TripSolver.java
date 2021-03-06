import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TripSolver {
	
	static ArrayList<Bus> buses = new ArrayList<>();
	
	public static void main(String[] args) {
		int[][] timeMatrix = {{0,1,4,3,1},{1,0,3,1,4},{4,3,0,1,2},{3,1,1,0,1},{1,4,2,0,2}};
		//int[][] tripsArray = {{1,2,3},{3,2,6},{3,4,8},{4,2,8},{2,4,14},{3,2,12}};
		int[][] tripsArray = {{3,2,12},{1,2,16}};
		Solver tripSolver = new Solver(timeMatrix,tripsArray);
		tripSolver.eligibleBuses();
	}
}	

class Solver{
	private ArrayList<Bus> buses = new ArrayList<>();
	private int[][] timeMatrix;
	private ArrayList<Trip> tripsArray;
	public Solver(int[][] timeMatrix,int[][] tripsArray) {
		this.timeMatrix = timeMatrix;
		this.tripsArray = this.getTripsArray(tripsArray, timeMatrix);
	}
	
	public void printTrips() {
		for(Trip trip : this.tripsArray) {
			System.out.println(trip.toString()+"\n");
		}
	}
	
	private ArrayList<Trip> getTripsArray(int[][] tripsMatrix,int[][] timeMatrix){
		ArrayList<Trip> trips = new ArrayList<>();
		for(int i=0;i<tripsMatrix.length;i++) {
				Trip trip = new Trip(tripsMatrix[i][0],tripsMatrix[i][1],tripsMatrix[i][2],tripsMatrix[i][2]+timeMatrix[tripsMatrix[i][0]][tripsMatrix[i][1]]);
				trips.add(trip);
		}
		//Sort the trip based on startTime
		Collections.sort(trips, new Comparator<Trip>() {

			@Override
			public int compare(Trip o1, Trip o2) {
				if(o1.getTripStartTime()<o2.getTripStartTime()) {
					return -1;
				}else if(o1.getTripStartTime()>o2.getTripStartTime()) {
					return 1;
				}
				
				return 0;
			}
			
		});
		return trips;
	}
	
	public void eligibleBuses() {
		for(Trip trip : this.tripsArray) {
			ArrayList<Bus> eligibleBuses = new ArrayList<>();
			for(Bus bus : buses) {
				if((bus.getNextAvailableTime()+this.timeMatrix[bus.getDestination()][trip.getSource()])<=trip.getTripStartTime()) {
					eligibleBuses.add(bus);
				}
			}

			chooseBusForTrip(eligibleBuses,trip);
		}
		this.printTrips();
	}
	
	private void chooseBusForTrip(ArrayList<Bus> eligibleBuses,Trip trip) {
		//Minimum Buses First Priority and minimum dead travel time 2nd Priority
		//Bus assignedBus = assignBusMinimumBusesRule(eligibleBuses,trip);
		Bus assignedBus = assignBusMinimumDeadTravelRule(eligibleBuses,trip);
		assignedBus.assignTrip(trip);
		trip.assignBus(assignedBus.getBusId());
	}
	
	private Bus assignBusMinimumDeadTravelRule(ArrayList<Bus> eligibleBuses,Trip trip) {
		Bus assignedBus;
		if(eligibleBuses.size()>0) {
			//Sort eligibleBusesBased on Sorting minimum dead travel time
			Collections.sort(eligibleBuses,new Comparator<Bus>() {
				@Override
				public int compare(Bus o1, Bus o2) {
						if(timeMatrix[o1.getDestination()][trip.getSource()]>timeMatrix[o2.getDestination()][trip.getSource()]) {
							return 1;
						}else if(timeMatrix[o1.getDestination()][trip.getSource()]<timeMatrix[o2.getDestination()][trip.getSource()]) {
							return -1;
						}
					return 0;
				}
				
			});
			assignedBus = eligibleBuses.get(0);
			if(timeMatrix[0][trip.getSource()] < timeMatrix[assignedBus.getDestination()][trip.getSource()]) {
				assignedBus = new Bus();
				buses.add(assignedBus);
			}
			
		}else {
			assignedBus = new Bus();
			buses.add(assignedBus);
		}
		return assignedBus;
	}
	
	private Bus assignBusMinimumBusesRule(ArrayList<Bus> eligibleBuses,Trip trip) {
		Bus assignedBus;
		if(eligibleBuses.size()>0) {
			//Sort eligibleBusesBased on Sorting minimum dead travel time
			Collections.sort(eligibleBuses,new Comparator<Bus>() {
				@Override
				public int compare(Bus o1, Bus o2) {
						if(timeMatrix[o1.getDestination()][trip.getSource()]>timeMatrix[o2.getDestination()][trip.getSource()]) {
							return 1;
						}else if(timeMatrix[o1.getDestination()][trip.getSource()]<timeMatrix[o2.getDestination()][trip.getSource()]) {
							return -1;
						}
					return 0;
				}
				
			});
			assignedBus = eligibleBuses.get(0);
		}else {
			assignedBus = new Bus();
			buses.add(assignedBus);
		}
		return assignedBus;
	}
	
}
	

class Bus{
	private int destination;
	private int nextAvailableTime;
	private int busId;
	private static int busCount;
	
	public Bus() {
		this.busCount = this.busCount+1;
		this.busId = this.busCount;
		this.destination = 0;
		this.nextAvailableTime = 0;
	}
	public int getBusId() {
		return this.busId;
	}
	public int getDestination() {
		return this.destination;
	}
	public int getNextAvailableTime() {
		return this.nextAvailableTime;
	}
	public void assignTrip(Trip trip) {
		this.destination = trip.getDestination();
		this.nextAvailableTime = trip.getTripEndTime();
	}
}

class Trip{
	private int source;
	private int destination;
	private int tripStartTime;
	private int tripEndTime;
	private int busId;
	
	public Trip(int source,int destination,int tripStartTime,int tripEndTime) {
		this.source = source;
		this.destination = destination;
		this.tripStartTime = tripStartTime;
		this.tripEndTime = tripEndTime;
	}
	public int getSource() {
		return this.source;
	}
	public int getDestination() {
		return this.destination;
	}
	public int getTripEndTime() {
		return this.tripEndTime;
	}
	public int getTripStartTime() {
		return this.tripStartTime;
	}
	public void assignBus(int busId) {
		this.busId = busId;
	}
	public String toString() {
		return new String("Trip from source "+this.source+" to destination "+this.destination
				+" starting at "+this.tripStartTime+" ending at "+this.tripEndTime+" will be take by bus "+this.busId);
	}
}
