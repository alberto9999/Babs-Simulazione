package it.polito.tdp.babs.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.babs.model.Event.EventType;

public class Simulator {
	
private PriorityQueue <Event>queue;
private List<Station>stazioni;
private int prelieviFalliti;
private int poseFallite;
	
	
	
	public Simulator(List<Station> stazioni, double traffico){
		this.queue=new PriorityQueue<Event>();
		this.stazioni=stazioni;
		settaCapienza(traffico);
		this.prelieviFalliti=0;
		this.poseFallite=0;
	}



	private void settaCapienza(double traffico) {
		for(Station s: stazioni){
			s.setCapienza((int) (traffico/100*s.getDockCount()));
		}
		
	}



	public void addEvent(LocalDateTime data, Station s, EventType type) {
       Event e= new Event(data, s, type);
       queue.add(e);
	}



	public void run() {
	while(!queue.isEmpty()){
		Event e= queue.poll();
		switch (e.getType()) {
		case ARRIVE:
			processArriveEvent(e);
			break;
		case OUT:
			processOutEvent(e);
			break;
		
		}			
	}	
	}



	private void processOutEvent(Event e) {
	Station s= e.getStation();
	if(s.getCapienza()>=1){
		s.setCapienza(s.getCapienza()-1);
	}
	else{
		prelieviFalliti++;
	}
		
	}



	private void processArriveEvent(Event e) {
		Station s= e.getStation();
		if(s.getCapienza()<s.getDockCount()){
			s.setCapienza(s.getCapienza()+1);
		}
		else{
		poseFallite++;	
		}
		
	}


	public int getPrelieviFalliti() {
		return prelieviFalliti;
	}



	public int getPoseFallite() {
		return poseFallite;
	}
	

	
	
	
}
