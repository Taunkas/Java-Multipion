package multipion.jeu.IA;

import java.util.ArrayList;


import multipion.MultiPion;
import multipion.jeu.Jeu;
import multipion.jeu.Joueur;
import multipion.jeu.pion.Pion;
import multipion.utils.Coordonnee;

/**
 * IA qui joue de facon aleatoire
 */
public class IAaleatoire extends Joueur implements IA{
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Coordonnee d'arrivee de la piece a jouer
	 */
	private Coordonnee coordonneeAJouer;

	/**
	 * Constructeur de l'IA
	 * @param c la couleur de l'IA
	 * @param jeu reference du jeu
	 */
	public IAaleatoire(String couleur, Jeu jeu) {
		super(couleur);
		this.jeu = jeu;
		this.estHumain = false;
	}

	@Override
	public void jouer(){
		this.coordonneeAJouer = null;
		
		//Recupere toutes les pieces de la couleurs de l'ia
		ArrayList<Pion> pieces = (this.getCouleur().equals("BLANC"))? jeu.getPlateau().getPiecesBlanches() : jeu.getPlateau().getPiecesNoires();
		
		// Initialisation des param�tres
		boolean tourcoupIA = true;
		int x = -1;
		int y = -1;
		Pion pieceABouger = null;
		
		// Fait jouer l'IA uniquement si la fin == false
		if(multipion.jeu.Jeu.fin==false) {
			
		while(tourcoupIA){

			int index;
			pieceABouger = null;
			do{
				// genere un index al�atoire correspondant � une piece 
				index = (int) (Math.random()*pieces.size());
				pieceABouger = pieces.get(index);
				
				// Si la pi�ce ne peut pas bouger le code ce rexecute 
			}while(pieceABouger.casesPossibles().isEmpty());

			// genere un nombre al�atoire correspondant au diff�rents d�placement de la pi�ce s�lectionner
			int j = (int) (Math.random()*pieceABouger.casesPossibles().size());
			
			// affecte � x et y les position choisit al�atoirement
			x = pieceABouger.casesPossibles().get(j).x;
			y = pieceABouger.casesPossibles().get(j).y;
			
			// Cr�er la nouvelle coordonn�e 
			Coordonnee coordPieceABouger = new Coordonnee(pieceABouger.getX(), pieceABouger.getY());
			
			// Si l'on d�place la pi�ce � la nouvelle position
			if(pieceABouger.deplacer(x, y)){
				
				// le tour du coup de l'IA se termine et ferme la boucle while
				tourcoupIA = false;
				
			}else{
				System.out.println("erreur coup IA");
			}
			
			// Garde le coup alors choisit et set les nouvelles coordonn�es
			jeu.getPlateau().annulerDernierCoup(false);
			jeu.setPieceSelectionee(coordPieceABouger.x, coordPieceABouger.y);
			
			}
		// Affecte les nouvelles coordonn�es � la pi�ce � bouger
		this.coordonneeAJouer = new Coordonnee(x,y);
		}
		
	}

	@Override
	public Coordonnee getCoordonneeAJouer() {
		return coordonneeAJouer;
	}

}
