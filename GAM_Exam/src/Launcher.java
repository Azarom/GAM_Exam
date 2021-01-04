import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.naming.NamingException;

import JFX.mote.Frame;
import JFX.mote.layout.Popup;
import gui.page.FormulaireExam;
import gui.page.Login;
import gui.page.MainApp;
import gui.page.TimeExamElement;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import liaisonappliBDopta.Authentification;
import liaisonappliBDopta.Connexion;
import liaisonappliBDopta.CreationFich;
import liaisonappliBDopta.Examen;

public class Launcher {
	private static int statuActuel ;
	public static void main(String[] args) throws NumberFormatException, NamingException {
		PrepFich po = new PrepFich();
		CreationFich pa = po.Recup("D:/exam/exam_import.exam");
		pa.creerFile();
		
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
				statuActuel = a.getStatut();
			}
			else {
				login.setErrorMessage("Identifiant ou Mot de passe non valide");
			}
		});
		
		FormulaireExam b = new FormulaireExam("Nouveau EXAMEN");
		if (statuActuel == 1 || statuActuel == 3 )//secretariat or admin
			{
		b.setSubmitAction(event->{
			int duree = Integer.parseInt(b.getDuree());
			int type = Integer.parseInt(b.getType());
			try {
				new Examen(b.getNom(), b.getMat(), duree, type, b.getMateriel());
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		}
		
		
		lectureBD a = new lectureBD();
		
		 
		List<List<TimeExamElement>> diaries = Arrays.asList(
				a.execute()
				);
		MainApp maz = new MainApp("app");
		System.out.println(diaries);
		maz.getCalendar().setDiaries(diaries);
		login.setNext("app");
		frame.setPanel(login);
		frame.show();
	}
	


}
