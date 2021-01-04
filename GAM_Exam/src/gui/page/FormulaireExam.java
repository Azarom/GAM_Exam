package gui.page;

import JFX.mote.Component;
import JFX.mote.components.Text;
import JFX.mote.components.Title;
import JFX.mote.controls.Button;
import JFX.mote.controls.PassField;
import JFX.mote.controls.TextField;
import JFX.mote.layout.Panel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FormulaireExam extends Panel<VBox>{
	private TextField nom;
	private TextField mat;
	private TextField duree;
	private TextField type;
	private TextField materiel;
	private Text errormsg;
	private Button submit;
	public FormulaireExam(String name){
		this(name,null);
	}
	public FormulaireExam(String name,EventHandler<ActionEvent> onclick){
		super(new VBox(),name);
		add(new Title("Formulaire"));
		nom = new TextField("Identifiant");
		add(nom);
		mat = new TextField("Matière");
		add(mat);
		duree = new TextField("Durée");
		add(duree);
		type = new TextField("Type");
		add(type);
		materiel = new TextField("Materiel");
		add(materiel);
		if(onclick==null) {
			onclick = x->{this.next();};
		}
		submit = new Button("Name",onclick);
		add(submit);
		layout.setAlignment(Pos.CENTER);
		setAlignment(Pos.CENTER);
	}


	@Override
	protected void updateStyle() {	
	}
	public void  setSubmitAction(EventHandler<ActionEvent> onclick) {
		submit.setOnclick(onclick);
		
	}
	public String getNom(){
		return nom.getText();
	}
	public String getMat(){
		return mat.getText();
	}
	public String getDuree (){
		return duree.getText();
	}
	public String getType (){
		return type.getText();
	}
	public String getMateriel (){
		return materiel.getText();
	}
	
	
	public void setErrorMessage(String value) {
		if(errormsg==null) {
			errormsg = new Text(value);
			errormsg.setColor(Color.RED);
			add(errormsg);
		}else {
			errormsg.setText(value);
		}
	}
}

