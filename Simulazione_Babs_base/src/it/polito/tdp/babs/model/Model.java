package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.babs.db.BabsDAO;
import it.polito.tdp.babs.model.Event.EventType;

public class Model {
	private BabsDAO dao;
	private List<Station>stazioni;
	private Map <Integer,Station>mappaStazioni;
	private int prelieviFalliti;
	private int poseFallite;


	public Model(){
		 dao= new BabsDAO();
		 caricaDati();
		 
		 
	}
	
	public void caricaDati(){
		stazioni=dao.getAllStations();
		mappaStazioni= new HashMap<Integer,Station>();
		for(Station s: stazioni){
			mappaStazioni.put(s.getStationID(),s);
		}
	}

	
	public List<Station> getStazioniPerData(LocalDate data) {
	
		for(Station station:stazioni){
			station.setTripArrivo(0);
			station.setTripPartenza(0);
			dao.setCountPartenze(data,station);
			dao.setCountArrivi(data,station);
			System.out.println(station);
		}
	
		
		List<Station>stazioniOrdinate= new ArrayList<Station>(stazioni);
		Collections.sort(stazioniOrdinate,new Comparator<Station>(){

			@Override
			public int compare(Station s1, Station s2) {
				
				return (int)(s1.getLat()-s2.getLat());
			}
			
		});

		return stazioniOrdinate;
	}




	public void simula(LocalDate data, double traffico) {
		Simulator sim= new Simulator(stazioni,traffico);
		for(Trip t:dao.getAllTripsDataArrivo(data)){ 
			
		LocalDateTime dataInizio= t.getStartDate();
		Station partenza= mappaStazioni.get(t.getStartStationID());
		sim.addEvent(dataInizio,partenza,EventType.OUT);
		}
		for(Trip t:dao.getAllTripsDataPartenza(data))  		{ 	
		LocalDateTime dataFine= t.getEndDate();
		Station arrivo= mappaStazioni.get(t.getEndStationID());
		sim.addEvent(dataFine,arrivo, EventType.ARRIVE);
		}
		sim.run();
		prelieviFalliti=sim.getPrelieviFalliti();
		poseFallite=sim.getPoseFallite();
		
	}

	public int getPrelieviFalliti() {
		return prelieviFalliti;
	}

	

	public int getPoseFallite() {
		return poseFallite;
	}

}
