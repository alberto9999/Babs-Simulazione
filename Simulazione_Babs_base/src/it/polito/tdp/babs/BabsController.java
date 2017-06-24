package it.polito.tdp.babs;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.babs.model.Model;
import it.polito.tdp.babs.model.Station;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class BabsController {


	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker pickData;

	@FXML
	private Slider sliderK;

	@FXML
	private TextArea txtResult;

	@FXML
	void doContaTrip(ActionEvent event) {
   try{
		LocalDate data= pickData.getValue();
    //controlli su data
    List<Station>statistichePerData= model.getStazioniPerData(data);
    for(Station s: statistichePerData){
    	if(s.getTripPartenza()==0&&s.getTripArrivo()==0){
    		txtResult.appendText("ATTENZIONE: STAZIONE "+s +" NON HA MOVIMENTI\n");
    	}
    	else{
    	txtResult.appendText(s+" partenze: "+s.getTripPartenza()+" arrivi: "+s.getTripArrivo()+"\n");
    }}
   }catch(RuntimeException e){
	   txtResult.setText("input errato");
   }
   
    
		
	}

	@FXML
	void doSimula(ActionEvent event) {
		LocalDate data= pickData.getValue();
		if(data.getDayOfWeek()==DayOfWeek.SATURDAY||data.getDayOfWeek()==DayOfWeek.SUNDAY){
			txtResult.setText("selezionare un giorno feriale\n");
		}
		else{
			double traffico= sliderK.getValue();
			model.simula(data,traffico);
			txtResult.appendText("prelievi falliti: "+model.getPrelieviFalliti()+"\n");
			txtResult.appendText("pose fallite: "+model.getPoseFallite()+"\n");
			
		}
		
			
		
		
	}

	@FXML
	void initialize() {
		assert pickData != null : "fx:id=\"pickData\" was not injected: check your FXML file 'Babs.fxml'.";
		assert sliderK != null : "fx:id=\"sliderK\" was not injected: check your FXML file 'Babs.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Babs.fxml'.";

		pickData.setValue(LocalDate.of(2013, 9, 1));
	}

	Model model;
	public void setModel(Model model) {
	this.model= model;
	}
}
