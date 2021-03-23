package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class FXMLController {
	
	private Dictionary model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> listLan;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnSpell;

    @FXML
    private TextArea txtErrori;


    @FXML
    private Label txtErrors;

    @FXML
    private Button btnClear;

    @FXML
    private Label txtTime;

    @FXML
    void doClearText(ActionEvent event) {
    	txtErrori.clear();
    	txtInput.clear();
    	model.clear();
    	
   
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	long start = System.nanoTime();
    	model.loadDictionary(listLan.getValue());
    	List<String> input = new ArrayList<>();
    	String parole[] = txtInput.getText().split(" ");
    	for(int i = 0; i < parole.length; i++)
    		input.add(parole[i]);
    	model.spellCheckText(input);
    	List<String> output = model.checkErrori();
    	for(String s : output)
    		txtErrori.appendText(s+"\n");
    	
    	txtErrors.setText("The text contais: "+model.getErr()+" error(s)");
    	if(model.getErr()>0) {
    	txtErrors.setTextFill(Color.RED);
    	}
    	else
    		txtErrors.setTextFill(Color.GREEN);
    	long end = System.nanoTime();
    	long duration = end- start;
    	duration = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS);
    	String tempo = String.valueOf(duration);
    	txtTime.setText("Spell check completed in "+tempo+" ms");

    }

    @FXML
    void initialize() {
        assert listLan != null : "fx:id=\"listLan\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrors != null : "fx:id=\"txtErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    public void setModel(Dictionary model) {
    	this.model = model;
    	List<String> lingue = new ArrayList<>();
    	lingue.add("English");
    	lingue.add("Italian");
    	listLan.getItems().addAll(lingue);
    }
}
