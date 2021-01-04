
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
import liaisonappliBDopta.ContrainteExam;
import liaisonappliBDopta.ContrainteSalle;
import liaisonappliBDopta.CreationFich;
import liaisonappliBDopta.CrenauHoraire;
import liaisonappliBDopta.Etudiant;
import liaisonappliBDopta.Examen;
import liaisonappliBDopta.Salle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepFich {
	 String url="jdbc:mysql://localhost/proj_exam";
		String login="root"; 
		String password="";
		Connection cn=null;

	public PrepFich(){
		
	}
	
	public CreationFich Recup(String doss) throws NamingException{
		
		CreationFich a = new CreationFich(this.RecupExam(), this.RecupCreneaux(), this.RecupSalle(), doss);
		return a;	
	}
	
	public List<Examen> RecupExam(){
		List<Examen> liste = new ArrayList<Examen>();
		String requete ="SELECT * FROM examen";
		Statement st = null;
		ResultSet rsi = null ;
		try {
			cn= DriverManager.getConnection(url, login, password);
			st = cn.createStatement();
			rsi = st.executeQuery(requete);
			while(rsi.next()) {
				Examen a = new Examen(rsi.getString("ExamenTitre") ,"mat" , rsi.getInt("ExamenLenght"), 1, "aucun" );
				int i = rsi.getInt("ExamenID");
				List<Etudiant> b = new ArrayList<Etudiant>(); 
				lectureBD l = new lectureBD();
				b = l.getStudent(i);
				List<ContrainteSalle> c = new ArrayList<ContrainteSalle>(); 
			    c = l.getCtrnSalle(i);
				List<ContrainteExam> d = new ArrayList<ContrainteExam>();
				d = l.getCtrnExam(i);
				a.addListEtudiant(b);
				a.addListContrainteExam(d);
				a.addListContrainteSalle(c);
				
				liste.add(a);
			}
		}
		catch(SQLException e) {
			System.err.println("Erreur requète SQL");
			e.printStackTrace(); 
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return liste;	
	}
	public List<Salle> RecupSalle(){
		List<Salle> liste = new ArrayList<Salle>();
		String requete ="SELECT * FROM salles";
		Statement st = null;
		ResultSet rsi = null ;
		try {
			cn= DriverManager.getConnection(url, login, password);
			st = cn.createStatement();
			rsi = st.executeQuery(requete);
			while(rsi.next()) {
				Salle a = new Salle(rsi.getString("SallesNom"), null, rsi.getInt("SallesCapacite"));
				liste.add(a);
			}
			
			}
			
			catch(SQLException e) {
				System.err.println("Erreur requète SQL");
				e.printStackTrace(); 
			}
		return liste;			
	}
	public List<CrenauHoraire> RecupCreneaux() throws NamingException{
		List<CrenauHoraire> liste = new ArrayList<CrenauHoraire>();
		String requete ="SELECT * FROM creneaux";
		Statement st = null;
		ResultSet rsi = null ;
		try {
			cn= DriverManager.getConnection(url, login, password);
			st = cn.createStatement();
			rsi = st.executeQuery(requete);
			while(rsi.next()) {
				String z = rsi.getString("creneauxDT");
				String c = z.substring(0,9);
				String [] stra = c.split("-");
				String date = stra[0] +":"+ stra[1] +":"+ stra[2];
				String heure = rsi.getString("crenauxDT").substring(11,17);
				CrenauHoraire a = new CrenauHoraire(date,heure, rsi.getInt("CreneauxLenght"));
				liste.add(a);
			}
			}
			catch(SQLException e) {
				System.err.println("Erreur requète SQL");
				e.printStackTrace(); 
			}
		return liste;		
	}
	
}
