package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event  implements Comparable<Event>{
	public enum EventType{ 
		ARRIVE, 
		OUT   
		} ;
private LocalDateTime data;
private LocalDate data2;

private Station station;
private EventType type;
@Override
public int compareTo(Event other) {
	if(this.data.isBefore(other.data))
		return -1;
	if(this.data.isAfter(other.data))
		return 1;
	return 0;
}

public Event(LocalDateTime data, Station station,EventType type) {
	super();
	this.data = data;
	this.station = station;
this.type=type;
}

public LocalDateTime getData() {
	return data;
}
public void setData(LocalDateTime data) {
	this.data = data;
}
public Station getStation() {
	return station;
}
public void setStation(Station station) {
	this.station = station;
}

public EventType getType() {
	return type;
}

public void setType(EventType type) {
	this.type = type;
}

@Override
public String toString() {
	return "Event [data=" + data + ", station=" + station + ", type=" + type + "]";
}
	
	
	
	
}
