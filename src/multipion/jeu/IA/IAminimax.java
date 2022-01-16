package multipion.jeu.IA;

import java.util.ArrayList;
import multipion.jeu.Jeu;
import multipion.jeu.Joueur;
import multipion.jeu.pion.Pion;
import multipion.utils.Coordonnee;

/**
 * Une IA qui joue selon l'algorithme du MiniMax
 * Explication complete <a href="https://fr.wikipedia.org/wiki/Minimax">ICI</a>
 */
public class IAminimax extends Joueur implements IA{
	
	/**
	 * Profondeur maximale de descente dans l'arbre recursif
	 */
	private static int PROFONDEUR = 3;
	
	/**
	 * Compteur du nombre d'evaluation
	 */
	public static long compteur = 0;
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Coordonnee que l'ia joue
	 */
	private Coordonnee coordonneeAJouer;
	
	/**
	 * Reference du Thread dans lequel est l'ia
	 */
	private IAThread iaThread;
	
	/**
	 * Reference des variables d'evaluation
	 */
	private ValeursEvaluation valeurs;
	
	/**
	 * Constructeur
	 * @param couleur couleur du joueur
	 * @param jeu reference du jeu
	 * @param iaThread reference du thread dans lequel est l'ia
	 * @param valeurs reference des varibles d'evaluation
	 */
	public IAminimax(String couleur, Jeu jeu, IAThread iaThread, ValeursEvaluation valeurs){
		super(couleur);
		this.estHumain = false;
		this.jeu = jeu;
		this.iaThread = iaThread;
		this.valeurs = valeurs;
	}
	
	@Override
	public void jouer(){
		// FAis jouer l'IA uniquement si la partie est n'est pas fini
		if(multipion.jeu.Jeu.fin==false) {
			NoeudMiniMax aJouer = minimax();
			jeu.setPieceSelectionee(aJouer.depart.x, aJouer.depart.y);
			this.coordonneeAJouer = aJouer.arrivee;
		}
	}

	/**
	 * Algorithme du MiniMax pour le premier appel
	 * @return le meilleur coup a jouer
	 */
	private NoeudMiniMax minimax(){
		//Recupere tous les coups possibles jouable par le joueur courant (l'ia)
		ArrayList<NoeudMiniMax> noeuds = tousCoupsPossibles();
		
		//Appel recursif
		for(NoeudMiniMax noeud : noeuds){
			minimax(noeud, 1);
			jeu.getPlateau().annulerDernierCoup(true);
		}
		
		//Renvoi l'evaluation max
		NoeudMiniMax noeud = max(noeuds);
		compteur = 0;
		return noeud;
	}
	
	/**
	 * Algorithme du MiniMax pour les appels recurcifs
	 * @param noeudActuel noeud courant dans l'arbre
	 * @param profondeur profondeur actuelle dans l'arbre
	 */
	private void minimax(NoeudMiniMax noeudActuel, int profondeur){
		if(profondeur >= PROFONDEUR){
			jouerCoup(noeudActuel);
			noeudActuel.evaluation.profondeur = profondeur;
			noeudActuel.evaluation.evaluerPlateau();
		}else{
			//Joue le noeud
			if(jouerCoup(noeudActuel)){
				noeudActuel.evaluation.evaluerPlateau();
				noeudActuel.evaluation.profondeur = profondeur;
				return;
			}
			
			//Une fois jouer, recupere tous les coups possibles
			ArrayList<NoeudMiniMax> noeuds = tousCoupsPossibles();
			
			//Si c'est au tour de l'ia de jouer
			if(jeu.getJoueurCourant().equals(couleur)){
				NoeudMiniMax noeudMax = null;
				NoeudMiniMax noeudCourant = null;
				for(int i = 0; i < noeuds.size(); i++){
					noeudCourant = noeuds.get(i);
					minimax(noeudCourant, profondeur+1);
					jeu.getPlateau().annulerDernierCoup(true);
					if(i == 0){
						noeudMax = noeudCourant;
					}else if(noeudMax.evaluation != noeudCourant.evaluation.compare(noeudMax.evaluation)){
						noeudMax = noeudCourant;
					}
				}
				noeudActuel.evaluation.add(noeudMax.evaluation);
			//Si c'est au tour de l'adversaire de l'ia de jouer
			}else{
				NoeudMiniMax noeudMin = null;
				NoeudMiniMax noeudCourant = null;
				for(int i = 0; i < noeuds.size(); i++){
					noeudCourant = noeuds.get(i);
					minimax(noeudCourant, profondeur+1);
					jeu.getPlateau().annulerDernierCoup(true);
					if(i == 0){
						noeudMin = noeudCourant;
					}else if(noeudMin.evaluation != noeudCourant.evaluation.compare(noeudMin.evaluation)){
						noeudMin = noeudCourant;
					}
				}
				if(noeudMin != null){
				noeudActuel.evaluation.add(noeudMin.evaluation);
				}else{
				}
			}
		}
	}
	
	/**
	 * Recupere tous les coups possibles de toutes les pieces du joueur courant
	 * @return la liste de tous les coups possibles pour chaques pieces
	 */
	private ArrayList<NoeudMiniMax> tousCoupsPossibles(){
		ArrayList<NoeudMiniMax> noeuds = new ArrayList<NoeudMiniMax>();
		ArrayList<Pion> piecesPossibles = (jeu.getJoueurCourant().getCouleur().equals("BLANC"))? jeu.getPlateau().getPiecesBlanches() : jeu.getPlateau().getPiecesNoires();
		for(Pion piece : piecesPossibles){
			ArrayList<Coordonnee> casesPossibles = piece.casesPossibles();
			for(Coordonnee coord : casesPossibles){
				noeuds.add(new NoeudMiniMax(piece.getX(), piece.getY(), coord.x, coord.y, jeu, valeurs));
			}
		}
		return noeuds;
	}
	
	/**
	 * Le noeud qui a l'evaluation maximale
	 * @param noeuds une list de noeud a comparer
	 * @return le noeud max
	 */
	private NoeudMiniMax max(ArrayList<NoeudMiniMax> noeuds){
		if(noeuds == null || noeuds.size() == 0){
			return null;
		}
		
		NoeudMiniMax noeudMax = noeuds.get(0);
		for(int i = 1; i < noeuds.size(); i++){
			if(noeudMax.evaluation != noeuds.get(i).evaluation.compare(noeudMax.evaluation)){
				noeudMax = noeuds.get(i);
			}
		}
		return noeudMax;
	}
	
	/**
	 * Joue le coup selon le noeud
	 * @param noeud le noeud du coup a jouer
	 * @return true si un event est survenu, sinon false
	 */
	private boolean jouerCoup(NoeudMiniMax noeud){
		Pion pieceSelect = jeu.getPlateau().getCase(noeud.depart.x, noeud.depart.y);
		
		//Deplace la piece
		if(pieceSelect.deplacer(noeud.arrivee.x, noeud.arrivee.y)){
			jeu.changementjoueur();
			noeud.evaluation.evaluerAttaqueDefense();
		}else{
			jeu.changementjoueur();
			noeud.evaluation.evaluerAttaqueDefense();
			return true;
		}
		return false;
	}

	@Override
	public Coordonnee getCoordonneeAJouer() {
		Jeu.test_minmax=false;
		return this.coordonneeAJouer;
	}
}

/**
 * Stocke une etaque des appels recursifs de l'algorithme MiniMax
 */
class NoeudMiniMax{
	
	/**
	 * La coordonnee de la piece a bouger
	 */
	Coordonnee depart;
	
	/**
	 * La coordonnee de deplacement de la piece
	 */
	Coordonnee arrivee;
	
	/**
	 * Reference de l'evaluation du noeud
	 */
	MinmaxValeur evaluation;
	
	/**
	 * Constructeur
	 */
	public NoeudMiniMax(int xD, int yD, int xA, int yA, Jeu jeu, ValeursEvaluation valeurs){
		depart = new Coordonnee(xD, yD);
		arrivee = new Coordonnee(xA, yA);
		evaluation = new MinmaxValeur(jeu, valeurs);
		IAminimax.compteur++;
	}
}