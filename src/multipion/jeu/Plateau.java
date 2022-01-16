package multipion.jeu;

import java.util.ArrayList;

import multipion.jeu.pion.Pion;
import multipion.utils.CoupSave;
/**
 * Classe qui va creer le plateau du jeu, elle est compose d'un tableau de Pieces 
 */
public class Plateau {
	
	/**	
	 *  Un tableau de pieces.
	 */
	protected Pion[][] plateau;
	
	/**	
	 *  r�cup�re la taille de la grille
	 */
	public int tailleplateau=multipion.interfacegraphique.jeu.Grille.TailleGrille;
	
	/**
	 * reference du jeu
	 */
	protected Jeu jeu;
	
	/**
	 * Constructeur
	 * @param jeu reference du jeu
	 */
	public Plateau(Jeu jeu){
		this();
		this.jeu = jeu;
	}
	
	/**
	 * Constructeur
	 */
	public Plateau(){
		plateau = new Pion[tailleplateau][tailleplateau];
	}
	
	/**
	 * Mise en place des pieces sur le plateau
	 */
	public void miseEnPlacePlateau(){
		
		for(int i = 0; i < plateau.length; i++){
			for(int j = 0; j < plateau[i].length; j++){
				plateau[i][j] = null;
			}
		}
		
		
		for(char i = 0; i<=tailleplateau-1; i++) {
			setCase(i, 0, new Pion(i, 0,"BLANC", this));
			setCase(i, tailleplateau-1, new Pion(i, tailleplateau-1,"NOIR", this));
			
		}
			
	}
	
	/**
	 * Retourne a la case d'abscisse x et d'ordonnee y.
	 * @param x represente l'abscisse.
	 * @param y represente l'ordonnee.
	 * @return soit la Piece contenu dans la case x,y; soit une erreur dut aux coordonnees.
	 */
	public Pion getCase(int x, int y){
		if (x < 0 || y > tailleplateau-1){
			System.out.println("Erreur dans la coordonnee sur l'axe des abscisse : ("+x+","+y+")");
			return null;
		}
		if (y>tailleplateau-1 || y<0){
			System.out.println("Erreur dans la coordonnee sur l'axe des ordonnees : ("+x+","+y+")");
			return null;
		}
		return plateau[x][y];
	}
	
	/**
	 * Insert une Piece dans la case d'abscisse x et d'ordonnee y.
	 * @param x represente l'abscisse.
	 * @param y represente l'ordonnee.
	 * @param a est la Piece a mettre dans la case d'abscisse x et d'ordonnee y.
	 */
	public void setCase(int x, int y, Pion a){
		if (x < 0 || x > tailleplateau-1){
			System.out.println("Erreur dans la coordonnee sur l'axe des abscisse : ("+x+","+y+")"+" : "+a.toString());
		}
		if (y>tailleplateau-1 || y<0){
			System.out.println("Erreur dans la coordonnee sur l'axe des ordonnees : ("+x+","+y+") : "+a.toString());
		}
		this.plateau[x][y]=a;
		
	}

    /**
     * Regarde si une piece est presente dans une case
     */
    public boolean estVide(int x, int y){
        if(plateau[x][y] == null){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Getter jeu
     * @return reference du jeu
     */
    public Jeu getJeu(){
    	return jeu;
    }
    
    /**
     * Recupere une liste de toutes les pieces
     * @return une liste
     */
    public ArrayList<Pion> getPiece(){
    	ArrayList<Pion> p = new ArrayList<Pion>();
    	for(int i=0; i<plateau.length;i++){
    		for(int j=0; j<plateau.length;j++){
    			if(this.getCase(i, j) != null){
    				p.add(this.getCase(i, j));
    			}
    		}
    	}
    	return p;
    }
    
    /**
     * Recupere une list de l'ensemble des pieces blanches
     * @return la liste
     */
    public ArrayList<Pion> getPiecesBlanches(){
    	ArrayList<Pion> p = new ArrayList<Pion>();
    	for(int i=0; i<plateau.length;i++){
    		for(int j=0; j<plateau.length;j++){
    			if(this.plateau[i][j] != null && this.plateau[i][j].getCouleur().endsWith("BLANC")){
    				p.add(this.getCase(i, j));
    			}
    		}
    	}
    	return p;
    }
    
    /**
     * Recupere une list de l'ensemble des pieces noirs
     * @return la liste
     */
    public ArrayList<Pion> getPiecesNoires(){
    	ArrayList<Pion> p = new ArrayList<Pion>();
    	for(int i=0; i<plateau.length;i++){
    		for(int j=0; j<plateau.length;j++){
    			if(this.plateau[i][j] != null && this.plateau[i][j].getCouleur().endsWith("NOIR")){
    				p.add(this.getCase(i, j));
    			}
    		}
    	}
    	return p;
    }
    
    //permet de revenir en arriere (IA)
   public void annulerDernierCoup(boolean changerDeJoueur){
    	
    	CoupSave coup = jeu.getHistorique().getDernierCoup();
    	Pion pieceDuCoup = plateau[coup.arrivee.x][coup.arrivee.y];
    	plateau[coup.departMemoire.x][coup.departMemoire.y] = pieceDuCoup;
    	if(coup.isPrise){
    		plateau[coup.arrivee.x][coup.arrivee.y] = jeu.getPrises().get(jeu.getPrises().size()-1);
    		jeu.getPrises().remove(jeu.getPrises().size()-1);
    	}else{
    		plateau[coup.arrivee.x][coup.arrivee.y] = null;
    	}
    	pieceDuCoup.setX(coup.departMemoire.x);
    	pieceDuCoup.setY(coup.departMemoire.y);
    	
    	if(changerDeJoueur){
    		jeu.changementjoueur();
    	}
    	jeu.getHistorique().supprimeDernierCoup();
    	if(jeu.getFenetre() != null){
    		jeu.getFenetre().repaint();
    	}
    }
    
    
    /**
     * Getter des pions de la couleur choisi
     * @param couleur couleur des pions
     * @return
     */
    public ArrayList<Pion> getPions(String couleur){
    	ArrayList<Pion> pieces = (couleur.equals("BLANC"))? this.getPiecesBlanches() : this.getPiecesNoires();
    	
    	ArrayList<Pion> pions = new ArrayList<Pion>();
    	
    	for(int i = 0; i < pieces.size(); i++){
    		if(pieces.get(i).getClass().equals(Pion.class)){
    			Pion p = (Pion)pieces.get(i);
    			pions.add(p);
    		}
    	}
    	
    	return pions;
    }
}	
