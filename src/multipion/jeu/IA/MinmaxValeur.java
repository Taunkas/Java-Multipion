package multipion.jeu.IA;

import java.util.ArrayList;

import multipion.interfacegraphique.Menu;
import multipion.jeu.Jeu;
import multipion.jeu.pion.Pion;

/**
 * Evalue un plateau de jeu ou un coup pour l'algorithme minimax
 */
public class MinmaxValeur{
	
	/**
	 * Enum pour les differents event d'une partie
	 */
	public enum Event{
	ERREUR, RIEN
	};
	
	/**
	 * Valeurs des variables d'evaluations
	 */
	public ValeursEvaluation valeurs;
	
	/**
	 * Event de l'evaluation
	 */
	public Event event;
	
	/**
	 * Profondeur auquel a ete faite l'evaluation
	 */
	public int profondeur;
	
	/**
	 * valeur du plateau pour les attaques, defenses, dangers
	 */
	public int scorecoup;
	
	/**
	 * valeur d'evaluation du plateau
	 */
	public int valeurPlateau;
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Representation en string de l'historique joue pour cette evaluation
	 */
	public String historique;
	
	/**
	 * Constructeur
	 * @param jeu reference du jeu
	 * @param valeurs reference des variables d'evaluations
	 */
	public MinmaxValeur(Jeu jeu, ValeursEvaluation valeurs){
		this.jeu = jeu;
		this.event = Event.RIEN;
		this.scorecoup = 0;
		this.valeurPlateau = 0;
		this.profondeur = 0;
		this.valeurs = valeurs;
	}
	
	/**
	 * Compare l'objet a une autre evaluation et retourne la meilleur
	 * @param autre l'autre evaluation a comparer
	 * @return la meilleur evaluation des deux
	 */
	public MinmaxValeur compare(MinmaxValeur autre){
		if(this.event.compareTo(autre.event) > 0){
			return this;
		}else if(this.event.compareTo(autre.event) < 0){
			return autre;
		}
			if(this.scorecoup + this.valeurPlateau < autre.scorecoup + autre.valeurPlateau){
				return autre;
			}else{
				return this;
			}
		
	}
	
	/**
	 * Evalue le jeu selon le nombres d'attaques, defense et mise en danger de chaque piece
	 */
	public void evaluerAttaqueDefense(){
		ArrayList<Pion>[] allPieces = new ArrayList[2];
		allPieces[0] = jeu.getPlateau().getPiecesBlanches();
		allPieces[1] = jeu.getPlateau().getPiecesNoires();
		
		int[] valeurPieces = new int[2];
		
		for(int k = 0; k < allPieces.length; k++){
			ArrayList<Pion> pieces = allPieces[k];
			for(int i = 0; i < pieces.size(); i++){
				Pion piece = pieces.get(i);
				//Attaque et danger de la piece
				int k1 = (k == 0)? 1 : 0;
				ArrayList<Pion> piecesAdverses = allPieces[k1];
				for(int l = 0; l < piecesAdverses.size(); l++){
					Pion pieceAdverse = piecesAdverses.get(l);
					//Piece en danger
					if(pieceAdverse.coupPossible(piece.getX(), piece.getY()) && pieceAdverse.mouvementPossible(piece.getX(), piece.getY())){
							valeurPieces[k] += valeurs.DANGER;
					}
					
					//Piece peut attaquer
					if(piece.coupPossible(pieceAdverse.getX(), pieceAdverse.getY()) && piece.mouvementPossible(pieceAdverse.getX(), pieceAdverse.getY())){
							valeurPieces[k] += valeurs.ATTAQUE;
					}

					if(piece.getY()==0 && piece.getCouleur()=="NOIR" || piece.getY()==Menu.taillegrille && piece.getCouleur()=="BLANC"){
						valeurPieces[k] += valeurs.VICTOIRE;
						
					}
					if(pieceAdverse.getY()==0 && piece.getCouleur()=="NOIR" || piece.getY()==Menu.taillegrille && piece.getCouleur()=="BLANC"){
						valeurPieces[k] += valeurs.DEFAITE;
						
					}
				}
			}
		}
	
		if(jeu.getJoueurCourant().getCouleur().equals("BLANC")){
			this.scorecoup = valeurPieces[1] - valeurPieces[0];
		}else{
			this.scorecoup = valeurPieces[0] - valeurPieces[1];
		}
	}
	
	/**
	 * Evalue le plateau de jeu selon plusieurs criteres
	 */
	public void evaluerPlateau(){
		//Evaluation du nombre de piece
		ArrayList<Pion>[] allPieces = new ArrayList[2];
		allPieces[0] = jeu.getPlateau().getPiecesBlanches();
		allPieces[1] = jeu.getPlateau().getPiecesNoires();
		
		int[] forceNombre = new int[2];
		
		for(int i = 0; i < allPieces.length; i++){
			ArrayList<Pion> pieces = allPieces[i];
			for(int j = 0; j < pieces.size(); j++){
				Pion piece = pieces.get(j);
					forceNombre[i] += 1;
			}
		}
		
		
		if(jeu.getJoueurCourant().getCouleur().equals("BLANC")){
			this.valeurPlateau += forceNombre[1] - forceNombre[0];
		}else{
			this.valeurPlateau += forceNombre[0] - forceNombre[1];
		}
	}
	
	/**
	 * Ajoute une evaluation a l'objet
	 * @param add l'evaluation a ajouter
	 */
	public void add(MinmaxValeur add){
		this.profondeur = add.profondeur;
		this.event = add.event;
		this.scorecoup += add.scorecoup;
		this.valeurPlateau += add.valeurPlateau;
		this.historique = add.historique;
	}
}