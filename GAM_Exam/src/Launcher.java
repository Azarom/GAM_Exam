import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import JFX.mote.Frame;
import JFX.mote.layout.Popup;
import gui.page.Login;
import gui.page.MainApp;
import gui.page.TimeExamElement;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import liaisonappliBDopta.Authentification;

public class Launcher {

	public static void main(String[] args) {
		System.out.println("app");
		Frame frame = new Frame("Projet 1");
		Login login = new Login("login");
		login.setErrorMessage("invalid");
		login.setSubmitAction(event->{
			Authentification a = new Authentification(login.getUsername(),login.getPassWord());
			if (a.getAutorise() == true) {
				login.next();
				Popup pop = new Popup("Bienvenue , "+ login.getUsername());
				pop.open();
			}
			else {
				login.setErrorMessage("Identifiant ou Mot de passe non valide");
			}
		});
	
		lectureBD a = new lectureBD();
		List<TimeExamElement> diary = Arrays.asList(
				new TimeExamElement("F 021","Français",LocalDateTime.of(2021, 01, 01, 12, 30),LocalTime.of(1, 30),null)
				);
		 
		List<List<TimeExamElement>> diaries = Arrays.asList(
				diary,a.execute()
				);
		MainApp maz = new MainApp("app");
		System.out.println(diaries);
		maz.getCalendar().setDiaries(diaries);
		login.setNext("app");
		frame.setPanel(login);
		frame.show();
	}
	


}
