package multipion.jeu.IA;
import multipion.jeu.Jeu;
import multipion.jeu.Joueur;
import multipion.utils.Coordonnee;

/**
 * IA complete : utilise deux IA, IA ouverture puis IA minimax
 */
public class IAlevel3 extends Joueur implements IA{
	
	/**
	 * Coordonnee d'arrivee de la piece a jouer
	 */
	private Coordonnee coordonneeAJouer;
	
	/**
	 * Reference de l'ia minimax
	 */
	private IAminimax iaMinimax;
	
	/**
	 * Constructeur
	 * @param couleur couleur de l'ia
	 * @param jeu reference du jeu
	 * @param iaThread reference du thread dans lequel est l'ia
	 * @param valeurs reference des variables d'evaluations
	 */
	public IAlevel3(String couleur, Jeu jeu, IAThread iaThread, ValeursEvaluation valeurs){
		super(couleur);
		this.estHumain = false;
		
		iaMinimax = new IAminimax(couleur, jeu, iaThread, valeurs);
	}
	
	@Override
	public void jouer(){
		Jeu.test_minmax = true;
		iaMinimax.jouer();
		this.coordonneeAJouer = iaMinimax.getCoordonneeAJouer();
		
	}

	@Override
	public Coordonnee getCoordonneeAJouer() {
		return this.coordonneeAJouer;
	}
}
