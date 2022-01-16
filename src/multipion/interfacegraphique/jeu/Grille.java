package multipion.interfacegraphique.jeu;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;

import multipion.interfacegraphique.Case;
import multipion.interfacegraphique.Case.Etat;
import multipion.utils.Coordonnee;



/**
 * Conteneur qui contient toutes les cases du jeu
 * @see Case
 */
public class Grille extends JComponent{
	
	/**
	 * Taille de la grille choix de 3 � 10 
	 */
	
	public static int TailleGrille=multipion.interfacegraphique.Menu.taillegrille;
	
	/**
	 * Instance de la fenetre qui contient la grille
	 */
	private Fenetre fenetre;
	
	/**
	 * Afficher ou non les cases de deplacement possibles
	 */
	private boolean affichageAideDeplacement;

	/**
	 * Constructeur
	 * @param fenetre reference de la fenetre
	 */
	public Grille(Fenetre fenetre){
		super();
		this.fenetre = fenetre;
		this.affichageAideDeplacement = true;
		initGrille();
		updateGrille();
	}
	
	/**
	 * Ajoute les Cases a la grille
	 */
	private void initGrille(){
		this.setLayout(new GridLayout(TailleGrille,TailleGrille));
		Color backgroundColor = Color.WHITE;
		for(int i = TailleGrille-1; i >= 0; i--){
			for(int j = 0; j < TailleGrille; j++){
				CaseJeu c = new CaseJeu(j, i, backgroundColor, fenetre);
				this.add(c);
			}
		}
	}
	
	/**
	 * Met a jour toutes les Cases de la grille pour que celle-ci corresponde au plateau courant
	 */
	public void updateGrille(){

		Component[] contenu = this.getComponents();
		for(int i = 0; i < contenu.length; i++){
			if(contenu[i].getClass().equals(CaseJeu.class)){
				CaseJeu c = (CaseJeu)contenu[i];
				c.updateCase(fenetre.getJeu().getPlateau().getCase(c.getXTableau(), c.getYTableau()));
			}
		}
	}
	
	/**
	 * Remet l'etat de toutes les cases a RIEN
	 */
	public void resetEtatCases(){
		Component[] contenu = this.getComponents();
		for(int i = 0; i < contenu.length; i++){
			if(contenu[i].getClass().equals(CaseJeu.class)){
				CaseJeu c = (CaseJeu)contenu[i];
				if(c.getEtat() != Etat.RIEN){
					c.setEtat(Case.Etat.RIEN);
				}
			}
		}
	}
	
	/**
	 * Reset l'etat des cases en Selectione ou Deplacement_Possible a Rien
	 */
	public void resetSelectedCases(){
		Component[] contenu = this.getComponents();
		for(int i = 0; i < contenu.length; i++){
			if(contenu[i].getClass().equals(CaseJeu.class)){
				CaseJeu c = (CaseJeu)contenu[i];
				if(c.getEtat().equals(Case.Etat.DEPLACEMENT_POSSIBLE) || c.getEtat().equals(Case.Etat.SELECTIONE)){
					c.setEtat(Case.Etat.RIEN);
				}
			}
		}
	}
	
	
	/**
	 * Passe l'etat de toutes les Cases, correspondant aux Pieces de la liste, a DEPLACEMENT_POSSIBLE
	 * @param arrayList une liste de toute les Pieces ou l'on peut deplacer la piece courante.
	 */
	public void ajouterDeplacementPossible(ArrayList<Coordonnee> arrayList){
		if(this.affichageAideDeplacement){
			if(arrayList == null || !affichageAideDeplacement){
				return;
			}
			
			Component[] contenu = this.getComponents();
			for(int i = 0; i < contenu.length; i++){
				if(!arrayList.isEmpty()){
					for(int j = 0; j < arrayList.size(); j++){
						CaseJeu c = (CaseJeu)contenu[i];
						if(c.getXTableau() == arrayList.get(j).x && c.getYTableau() == arrayList.get(j).y){
							c.setEtat(Case.Etat.DEPLACEMENT_POSSIBLE);
							arrayList.remove(j);
							break;
						
						}
					}
				}
			}
		}
	}
	
}
