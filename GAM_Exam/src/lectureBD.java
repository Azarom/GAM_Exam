import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;

import gui.page.TimeExamElement;
import liaisonappliBDopta.Connexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class lectureBD {
	
	 String url="jdbc:mysql://localhost/proj_exam";
		String login="root"; 
		String password="";
		Connection cn=null;
		Connection cn2=null;
		
		private List<TimeExamElement> diary = new ArrayList<TimeExamElement>();
		
	public lectureBD() {
		
	}
	private int i ;
	
	public List<TimeExamElement> execute() {
		
		String requete ="SELECT COUNT(ExamenID) FROM examen";
		i = 0 ;
		Statement st = null;
		ResultSet rsi = null ;
		try {
			cn= DriverManager.getConnection(url, login, password);
			st = cn.createStatement();
			rsi = st.executeQuery(requete);
			if (rsi.next()) {
			i = rsi.getInt("COUNT(ExamenID)");
			}
		}
		catch(SQLException e) {
			System.err.println("Erreur requète SQL");
			e.printStackTrace(); 
		}
		
		for ( int a = 1 ; a<(i+1) ; a++) {
		String requete2 = "SELECT YEAR(c.CreneauxDT),MONTH(c.CreneauxDT),DAY(c.CreneauxDT),HOUR(c.CreneauxDT),MINUTE(c.CreneauxDT),e.ExamenTitre,s.sallesNom FROM creneaux c INNER JOIN liaison l ON c.CreneauxID = l.CreneauxID INNER JOIN Examen e ON e.ExamenID = l.ExamenID INNER JOIN salles s ON s.sallesID = l.sallesID WHERE e.ExamenID ="+a+"";
		System.out.println(requete2);
		
		ResultSet resultats = null ;
		Statement ste = null;
		try {
			System.out.println("ok");
			cn2= DriverManager.getConnection(url, login, password);
			ste = cn2.createStatement();
			resultats = ste.executeQuery(requete2);
			if (resultats.next()) {
				
			diary.add(new TimeExamElement(resultats.getString("s.sallesNom"),resultats.getString("e.ExamenTitre"),LocalDateTime.of(resultats.getInt("YEAR(c.CreneauxDT)"),resultats.getInt("MONTH(c.CreneauxDT)"),resultats.getInt("DAY(c.CreneauxDT)"), resultats.getInt("HOUR(c.CreneauxDT)"), resultats.getInt("MINUTE(c.CreneauxDT)")),LocalTime.of(1, 30),null));
			System.out.println(resultats.getString("e.ExamenTitre"));
			}
			
		}
		catch(SQLException e) {
			System.err.println("Erreur requète SQL");
			e.printStackTrace(); 
		}
		}
		
					
		return  diary;
		
	}


	
}
