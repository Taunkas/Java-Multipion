package multipion.jeu.IA;

import multipion.MultiPion;
import multipion.jeu.Jeu;
import multipion.jeu.Joueur;
import multipion.utils.Coordonnee;

/**
 * IA complete : utilise deux IA, IA ouverture puis IA minimax
 */
public class IAlevel2 extends Joueur implements IA{
	
	/**
	 * Coordonnee d'arrivee de la piece a jouer
	 */
	private Coordonnee coordonneeAJouer;
	
	/**
	 * Reference de l'ia minimax
	 */
	private IAminimaxfaible IAMinimaxfaible;
	
	/**
	 * Constructeur
	 * @param couleur couleur de l'ia
	 * @param jeu reference du jeu
	 * @param iaThread reference du thread dans lequel est l'ia
	 * @param valeurs reference des variables d'evaluations
	 */
	public IAlevel2(String couleur, Jeu jeu, IAThread iaThread, ValeursEvaluation valeurs){
		super(couleur);
		this.estHumain = false;
		
		IAMinimaxfaible = new IAminimaxfaible(couleur, jeu, iaThread, valeurs);
	}
	
	@Override
	public void jouer(){
		Jeu.test_minmax = true;
		IAMinimaxfaible.jouer();
		this.coordonneeAJouer = IAMinimaxfaible.getCoordonneeAJouer();
		
	}

	@Override
	public Coordonnee getCoordonneeAJouer() {
		return this.coordonneeAJouer;
	}
}
