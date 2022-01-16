package multipion.jeu;

import multipion.interfacegraphique.jeu.Fenetre;
import multipion.jeu.IA.IAThread;
import multipion.jeu.IA.ValeursEvaluation;
import multipion.jeu.pion.Pion;
import multipion.utils.Coordonnee;
import multipion.utils.Historique;

import java.util.ArrayList;

/**
 * instancie une partie
 */
public class Jeu{
	
	/**
	 * fin de jeux true si la partie est fini false sinon. Initalis� � false
	 */
	public static boolean fin = false;
	
	
	public static boolean test_minmax = false;
	/**
	 * Plateau courant
	 */
	protected Plateau plateau;
	
	/**
	 * Liste des pieces prises
	 */
	protected ArrayList<Pion> prises;
	
	/**
	 * Variable vrai si le jeu est en mode joueur vs ia ou ia vs ia
	 */
	private boolean vsIA;
	/**
	 * Instance du Joueur blanc
	 */
	protected Joueur joueurBlanc;
	
	/**
	 * Instance du Joueur noir
	 */
	protected Joueur joueurNoir;
	
	/**
	 * Instance du Joueur courant pendant la partie
	 */
	protected Joueur joueurCourant;
	
	/**
	 * Thread pour l'ia
	 */
	private IAThread iaThread;
	
	/**
	 * Deuxieme thread d'ia si IAvsIA
	 */
	private IAThread iaThread2;
	
	/**
	 * Instance de la piece selectionnee par le joueur
	 */
	protected Pion pieceSelectionee;
	
	/**
	 * Liste de l'historique des coups
	 */
	protected Historique historique;
	
	/**
	 * Instance de la fenetre de jeu
	 */
	protected Fenetre fenetre;
	
	/**
	 * Constructeur
	 */
	public Jeu() {
		super();
		plateau = null;
		prises = null;
		vsIA = false;
		joueurBlanc = null;
		joueurNoir = null;
		joueurCourant = null;
		iaThread = null;
		iaThread2 = null;
		pieceSelectionee = null;
		historique = null;
		fenetre = null;

	}
	
	/**
	 * Constructeur
	 * @param fenetre reference de la fenetre
	 */
	public Jeu(Fenetre fenetre){
		super();
		this.plateau = new Plateau(this);
		this.prises = new ArrayList<Pion>();
		joueurBlanc = new Joueur("BLANC");
		joueurNoir = new Joueur("NOIR");
		joueurCourant = joueurBlanc;
		historique = new Historique();
		this.fenetre = fenetre;
		vsIA = false;
		plateau.miseEnPlacePlateau();
		fin=false;
	}
	
	

	/**
	 * Constructeur pour une partie contre l'ordinateur
	 * @param fenetre reference de la fenetre
	 * @param humainEstBlanc couleur du joueur humain
	 * @param lvlia niveau de l'ia
	 */
	public Jeu(Fenetre fenetre, boolean humainEstBlanc, int lvlia){
		super();
		if(humainEstBlanc){
			joueurBlanc = new Joueur("BLANC");
			joueurNoir = new Joueur("NOIR");
			joueurNoir.setHumain(false);
			iaThread = new IAThread(lvlia, "NOIR", this, new ValeursEvaluation());
		}else{
			joueurBlanc = new Joueur("BLANC");
			joueurBlanc.setHumain(false);
			joueurNoir = new Joueur("NOIR");
			iaThread = new IAThread(lvlia, "BLANC", this, new ValeursEvaluation());
		}
		iaThread.pause(true);
		iaThread.start();
		joueurCourant = joueurBlanc;
		historique = new Historique();
		this.fenetre = fenetre;
		this.plateau = new Plateau(this);
		this.prises = new ArrayList<Pion>();
		vsIA = true;
		plateau.miseEnPlacePlateau();
		fin=false;
	}
	
	/**
	 * Constructeur d'une partie ordinateur contre ordinateur
	 * @param fenetre reference de la fenetre
	 * @param niveauBlanc niveau du joueur blanc
	 * @param niveauNoir niveau du joueur noir
	 * @param valeursBlanc valeurs des constantes du joueur blanc si niveau 3, sinon null
	 * @param valeursNoir valeurs des constantes du joueur noir si niveau 3, sinon null
	 */
	public Jeu(Fenetre fenetre, int niveauBlanc, int niveauNoir, ValeursEvaluation valeursBlanc, ValeursEvaluation valeursNoir){
		super();
		joueurBlanc = new Joueur("BLANC");
		joueurBlanc.setHumain(false);
		joueurNoir = new Joueur("NOIR");
		joueurNoir.setHumain(false);
		
		iaThread = new IAThread(niveauBlanc, "BLANC", this, new ValeursEvaluation());
		iaThread2 = new IAThread(niveauNoir, "NOIR", this, new ValeursEvaluation());
		
		iaThread.pause(true);
		iaThread2.pause(true);
		iaThread.start();
		iaThread2.start();
		
		joueurCourant = joueurBlanc;
		historique = new Historique();
		this.fenetre = fenetre;
		this.plateau = new Plateau(this);
		this.prises = new ArrayList<Pion>();
		this.vsIA = true;
		plateau.miseEnPlacePlateau();
		fin=false;
	}

	/**
	 * Demarre une partie si le joueur blanc est un ordinateur
	 */
	public void demarrerPartieIA(){
		if(!joueurCourant.estHumain){
			iaThread.pause(false);
			fenetre.tourIA(true);
		}
	}
	
	/**
	 * Joue le coup de l'ia apres que celui ci est reflechi a son coup a jouer
	 * @param coord coordonnee d'arrivee du coup a jouer
	 */
	public void jouerIA(Coordonnee coord){
		fenetre.tourIA(false);
		if(coord != null){
			if(fin==false) {
			this.deplacerPiece(coord.x, coord.y);
			}
		}else{
			fenetre.repaint();
		}
	}
	
	/**
	 * Regarde si le joueur est bloqu� ou non pour �galit�
	 * @param p piece deplacer
	 * @return 
	 */
	
	public void testBloque() {
		if(test_minmax == false) {
		int tailleplateau=multipion.interfacegraphique.jeu.Grille.TailleGrille;
		int tmp =0;
		String coul = (getJoueurCourant().getCouleur()=="BLANC") ? "NOIR" : "BLANC";
			
			ArrayList<Pion> pions = plateau.getPions(coul);
			for(Pion a : pions){
				for(int u = 0; u<tailleplateau; u++) {
					for(int v = 0; v<tailleplateau; v++) {
					if(a.coupPossible(u, v)==true){
						tmp++;
					}else {	
					}	
			}}}
			if(tmp==0) {
				fin=true;
				plateau.getJeu().getFenetre().Victoire(getJoueurCourant().getCouleur(),"en bloquant votre adversaire.");
			}}
		}

		
		
	
	/**
	 * Designe comme piece selectionnee
	 * @param x coordonnee en abscisse de la futur piece selectionnee
	 * @param y coordonnee en ordonnee de la futur piece selectionnee
	 */
	public void setPieceSelectionee(int x, int y){
		pieceSelectionee = plateau.getCase(x, y);
		if(joueurCourant.estHumain){
			fenetre.getGrille().ajouterDeplacementPossible(pieceSelectionee.casesPossibles());
		}
		
		if(fenetre != null){
			fenetre.repaint();
		}
	}
	
	/**
	 * Renvoi le test boolean de si la piece selectionnee est a null
	 * @return le resultat du test
	 */
	public boolean aucunePieceSelectionee(){
		return pieceSelectionee == null;
	}
	
	/**
	 * Deplace la piece selectionnee sur la coordonnee (x,y)
	 * @param x la coordonnee en abscisse 
	 * @param y la coordonnee en ordonee
	 */
	public void deplacerPiece(int x, int y){
		boolean deplacementSucces = false;
		
		int valeurPrerequis = prerequis(pieceSelectionee, x, y);
		
		//Deplace la piece selectionne
		if(pieceSelectionee.deplacer(x, y)){

			changementjoueur();
			deplacementSucces = true;
		}
		
		pieceSelectionee = null;
		fenetre.getGrille().updateGrille();
		fenetre.getGrille().resetEtatCases();

		fenetre.repaint();
		
		if(!joueurBlanc.estHumain() && !joueurNoir.estHumain() && !iaThread.isReflechi() && !iaThread2.isReflechi()){
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//Si c'est le tour de l'ia
		if(!joueurCourant.estHumain()){
			if(joueurCourant.getCouleur().equals(iaThread.getCouleur())){
				fenetre.tourIA(true);
				iaThread.pause(false);
			}else{
				fenetre.tourIA(true);
				iaThread2.pause(false);
			}
		}
	}
	
	/**
	 * Recherche les prerequis pour un coups
	 * @param p piece sur laquelle il faut rechercher les prerequis
	 * @param x deplacement de la piece en x
	 * @param y deplacement de la piece en y
	 * @return un code de la valeur de prerequis
	 */
	public int prerequis(Pion p, int x, int y){
		int valeur = 1;

		if(p.getClass().equals(Pion.class)){
			ArrayList<Pion> pions = plateau.getPions(p.getCouleur());
			for(Pion a : pions){
				if(a != p){
					if(a.coupPossible(x, y) && a.mouvementPossible(x, y)){
						if(a.getX() == p.getX()){
							valeur += 10;
						}
						if(a.getY() == p.getY()){
							valeur  += 100;
						}else{
							if(valeur %100 < 90) valeur += 10;
						}
					}
				}
			}
		}
	
		
		
		return valeur;
	}
	
	/**
	 * Remet une partie a zero
	 */
	public void reset(){
		if(vsIA){
			this.fermerThreadIA();
			if(!joueurBlanc.estHumain() && !joueurNoir.estHumain()){
				iaThread = new IAThread(iaThread.getNiveau(), "BLANC", this, iaThread.getValeurs());
				iaThread2 = new IAThread(iaThread2.getNiveau(), "NOIR", this, iaThread2.getValeurs());
				iaThread.pause(true);
				iaThread2.pause(true);
				iaThread.start();
				iaThread2.start();
				joueurBlanc = new Joueur("BLANC");
				joueurNoir = new Joueur("NOIR");
				joueurBlanc.setHumain(false);
				joueurNoir.setHumain(false);
			}else if(!joueurBlanc.estHumain()){
				iaThread = new IAThread(iaThread.getNiveau(), "BLANC", this, iaThread.getValeurs());
				iaThread.pause(true);
				iaThread.start();
				joueurBlanc = new Joueur("BLANC");
				joueurNoir = new Joueur("NOIR");
				joueurBlanc.setHumain(false);
			}else{
				iaThread = new IAThread(iaThread.getNiveau(), "NOIR", this, iaThread.getValeurs());
				iaThread.pause(true);
				iaThread.start();
				joueurBlanc = new Joueur("BLANC");
				joueurNoir = new Joueur("NOIR");
				joueurNoir.setHumain(false);
			}
		}else{
			joueurBlanc = new Joueur("BLANC");
			joueurNoir = new Joueur("NOIR");
		}
		
		joueurCourant = joueurBlanc;
        plateau = new Plateau(this);
        prises = new ArrayList<Pion>();
		historique = new Historique();
		pieceSelectionee = null;
		
		plateau.miseEnPlacePlateau();
		
		fenetre.resetlog();
		fenetre.repaint();
		
		this.demarrerPartieIA();
	}
	
	/**
	 * Change le joueur courant (Si c'etait le joueur blanc, joueurCourant devient le joueur noir) egalement verifie si bloquer
	 */
	public void changementjoueur(){
		testBloque();
		joueurCourant = (joueurCourant == joueurBlanc)? joueurNoir : joueurBlanc;
		
	}
	
	public void changementjoueuriA(){
		joueurCourant = (joueurCourant == joueurBlanc)? joueurNoir : joueurBlanc;
		
	}
	
	/**
	 * Getter pour historique
	 * @return la reference de l'historique de Coup
	 */
	public Historique getHistorique(){
		return historique;
	}
	
	/**
	 * Getter pour le joueurCourant
	 * @return la reference du joueurCourant
	 */
	public Joueur getJoueurCourant(){
		return joueurCourant;
	}
	
	/**
	 * Getter pour le joueur blanc
	 * @return la reference du joueurBlanc
	 */
	public Joueur getJoueurBlanc(){
		return joueurBlanc;
	}
	
	/**
	 * Getter pour le joueur noir
	 * @return la reference du joueurNoir
	 */
	public Joueur getJoueurNoir(){
		return joueurNoir;
	}
	
	/**
	 * Setter pour joueurCourant
	 * @param j joueurCourant
	 */
	public void setJoueurCourant(Joueur j){
		this.joueurCourant = j;
	}
	
	/**
	 * Getter pour la fenetre lie au jeu
	 * @return fenetre
	 */
	public Fenetre getFenetre(){
		return this.fenetre;
	}
	
	/**
	 * Getter du plateau
	 * @return Plateau
	 */
	public Plateau getPlateau(){
		return this.plateau;
	}
	
	/**
	 * Getter des pieces prises
	 * @return ArrayList<Piece>
	 */
	public ArrayList<Pion> getPrises(){
		return this.prises;
	}
	
	/**
	 * Test si le jeu est en mode vs IA
	 * @return true si contre IA
	 */
	public boolean isVsIA(){
		return this.vsIA;
	}
	
	/**
	 * Getter du thread traitant les ia
	 * @return IAThread
	 */
	public IAThread getIAThread(){
		return this.iaThread;
	}
	
	/**
	 * Getter du thread 2 pour la deuxieme ia si partie de type ia vs ia
	 * @return
	 */
	public IAThread getIAThread2(){
		return this.iaThread2;
	}
	
	
	/**
	 * Stop les thread des IA
	 */
	public void fermerThreadIA(){
		if(iaThread != null && iaThread.isAlive()){
			iaThread.setVivant(false);
		}
		
		if(iaThread2 != null && iaThread2.isAlive()){
			iaThread2.setVivant(false);
		}
	}
}
