/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	this.txtResult.clear();
    	int i= this.boxMese.getValue();
    	List<Citta> c= model.trovaSequenza(i);
    	String s="";
    	for(Citta ci: c) {
    		s+= ci.getNome()+"\n";
    	}
    			
    	txtResult.setText(s);
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	this.txtResult.clear();
    	int i= this.boxMese.getValue();
    	txtResult.appendText(this.model.getUmiditaMedia(i));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model m) {
    	this.model=m;
    	
    	List<Integer> l= new LinkedList<>();
    	l.add(1);
    	l.add(2);
    	l.add(3);
    	l.add(4);
    	l.add(5);
    	l.add(6);
    	l.add(7);
    	l.add(8);
    	l.add(9);
    	l.add(10);
    	l.add(11);
    	l.add(12);
    	for(Integer s: l) {
    		boxMese.getItems().add(s);
    	}
    	
    }
}

