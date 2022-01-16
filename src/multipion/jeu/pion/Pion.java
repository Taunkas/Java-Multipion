package multipion.jeu.pion;

import java.util.ArrayList;

import multipion.jeu.Jeu;
import multipion.jeu.Plateau;
import multipion.utils.Coordonnee;
import multipion.utils.CoupSave;

/**
 * Classe mere des differentes pieces
 */
public class Pion {
    /**
     * Reference au plateau
     */
    protected Plateau plateau;
    
    /**
     * La couleur de la piece
     */
    protected String couleur;
    
    /**
     * L'abscisse et l'ordonnee de la piece
     */
    protected int x,y;

    /**
     * Constructeur de Piece
     * @param x La position en abscisse
     * @param y La position en ordonnee
     * @param couleur La couleur de la piece
     */
    public Pion(int x, int y, String couleur, Plateau plateau){
    	this.x = x;
    	this.y = y;
    	this.couleur = couleur;
    	this.plateau = plateau;
    }

    /**
     * Donne la couleur de piece
     * @return La couleur de la piece
     */
    public String getCouleur(){
    	return couleur;
    }
    
    /**
     * Retourne la coordonnee X
     * @return x
     */
    public int getX(){
    	return this.x;
    }
    
    /**
     * Retourne la coordonnee Y
     * @return y
     */
    public int getY(){
    	return this.y;
    }
    
    /**
     * Set x
     */
    public void setX(int x){
    	this.x = x;
    }
    
    /**
     * Set y
     */
    public void setY(int y){
    	this.y = y;
    }
    
    /**
     * Set x et y en même temps
     */
    public void setXY(int x, int y){
    	this.x = x;
    	this.y = y;
    }
    
    /**
     * Deplace la piece (change les coordonnees)
     * @param x Le x d'arrivee
     * @param y Le y d'arrivee
     */
    public boolean deplacer(int x,int y){
        if(coupPossible(x, y)){
            if(mouvementPossible(x,y)){
            	
            	//Coup
            	CoupSave coup = new CoupSave();
            	coup.departMemoire.x = this.x;
            	coup.departMemoire.y = this.y;
            	coup.arrivee.x = x;
            	coup.arrivee.y = y;
            	coup.referencePiece = this;
            	
            	//Prises
	            if(plateau.getCase(x, y) != null){
	            	if(plateau.getCase(x, y).getCouleur() != this.getCouleur()){
	            		this.plateau.getJeu().getPrises().add(plateau.getCase(x, y));
	            		coup.isPrise = true;
	            	}
	            }
	            
	            //on test son premier coup
	            	Pion p = (Pion) this;
	            	p.setPremierCoup(false);
	            //Deplacement de la piece
	        	plateau.setCase(this.x, this.y, null);
	            this.x = x;
	            this.y = y;
	            plateau.setCase(this.x, this.y, this);
	            
	            //Test de fin de partie en allant au bout du plateau 
					if(p.BoutPlateau()){
						if(Jeu.test_minmax == false) {

							multipion.jeu.Jeu.fin=true;
							
							plateau.getJeu().getFenetre().Victoire(p.getCouleur(),"en allant bout du plateau.");
						}
					}
	            plateau.getJeu().getHistorique().addCoupPGN(coup);
	            return true;
            }
            else{

                return false;
            }
        }
        else{
            return false;
        }
        
    }
    
    /**
     * Recupere les coordonnees de toutes les cases ou peut aller la piece
     * @return ArrayList<Coordonnee> de tous les coups possibles
     */
    public ArrayList<Coordonnee> casesPossibles(){
    	ArrayList<Coordonnee> coords = new ArrayList<Coordonnee>();	
		if(this.couleur.equals("BLANC")){
			if(((x+1) >= 0 && (y+1) >= 0 && (x+1) < tailleplateau && (y+1) < tailleplateau) && (this.plateau.getCase(x+1,y+1) != null) && this.plateau.getCase(x+1,y+1).couleur.equals("NOIR")){
				coords.add(new Coordonnee(x+1,y+1));
			}
			if(((x-1) >= 0 && (y+1) >= 0 && (x-1) < tailleplateau && (y+1) < tailleplateau) && (this.plateau.getCase(x-1, y+1) != null) && this.plateau.getCase(x-1,y+1).couleur.equals("NOIR")){
				coords.add(new Coordonnee(x-1,y+1));
			}
			if(y <= 5 && premierCoup && plateau.getCase(x, y+1) == null && plateau.getCase(x, y+2) == null){
				coords.add(new Coordonnee(x,y+1));

			}
			if(y < tailleplateau-1 && plateau.getCase(x, y+1) == null){
				coords.add(new Coordonnee(x,y+1));
			}
		}
		else{
			if(((x+1) >= 0 && (y-1) >= 0 && (x+1) < tailleplateau && (y-1) < tailleplateau) && (this.plateau.getCase(x+1,y-1) != null) && this.plateau.getCase(x+1,y-1).couleur.equals("BLANC")){
				coords.add(new Coordonnee(x+1,y-1));
			}
			if(((x-1) >= 0 && (y-1) >= 0 && (x-1) < tailleplateau && (y-1) < tailleplateau) && (this.plateau.getCase(x-1,y-1) != null) && this.plateau.getCase(x-1, y-1).couleur.equals("BLANC")){
				coords.add(new Coordonnee(x-1,y-1));
			}
			if(y >= 2 && premierCoup && plateau.getCase(x, y-1) == null && plateau.getCase(x, y-2) == null){
				coords.add(new Coordonnee(x,y-1));

			}
			if(y > 0 && plateau.getCase(x, y-1) == null){
				coords.add(new Coordonnee(x,y-1));
			}
		}
		return coords;
    }
    
    /**
     * verifie que les conditions de deplacement sont rempli
     */
    public boolean coupPossible(int x, int y){
    	if(x < 0 || x > tailleplateau-1 || y < 0 || y > tailleplateau-1) return false;
    	  
    	if(this.plateau.getCase(x, y) != null){
    		if(this.couleur.equals("BLANC")){
    			
    			if(x == this.x+1 && y == this.y +1  && this.plateau.getCase(x,y).couleur.equals("NOIR")){
        			return true;
        		}
    			if(x == this.x-1 && y == this.y +1  && this.plateau.getCase(x,y).couleur.equals("NOIR")){
        			return true;
        		}
    		}
    		else{
    			if(x == this.x-1 && y == this.y -1  && this.plateau.getCase(x,y).couleur.equals("BLANC")){
        			return true;
        		}
    			if(x == this.x+1 && y == this.y-1  && this.plateau.getCase(x,y).couleur.equals("BLANC")){
        			return true;
        		}
    		}

    	}
    	if(this.plateau.getCase(x, y) == null){
	    	if(this.couleur.equals("BLANC")){
	    		//On détail les coup autorisé)
	    		if(x==this.x && y ==this.y+1){
	    			return true;
	    		}
	    	}
	    	else{
	    		if(x==this.x && y==this.y-1){
	    			return true;
	    		}
	    	}
	    	return false;
	    }
    	return false;
    }
    /**
     * Verifie si il n'y a pas d'obstacle au deplacement
     */
    public boolean mouvementPossible(int x, int y){
       return true;
    }
    
    @Override
    public String toString(){
    	return "PION "+couleur+" ("+x+","+y+")";
    }

    /**  
	 * Récupère la taille de la grille
	 */
	public int tailleplateau=multipion.interfacegraphique.jeu.Grille.TailleGrille;
	
	
	
    /**
     * Verifie si le pion a deja joue
     */
    private boolean premierCoup = true;
    
    /**
     * Detecte si le pion peut allez au bout du plateau
     * @return
     */
    public boolean BoutPlateau(){
    	if(this.couleur.equals("BLANC") && this.y == tailleplateau-1){
    		return true;
    	}
    	if(this.couleur.equals("NOIR") && this.y == 0){
    		return true;
    	}
    	else return false;
    }
    
    
    /**
     * Si le Pion n'as pas encore bouge
     * @return le boolean
     */
    public boolean isPremierCoup(){
    	return this.premierCoup;
    }
    
    /**
     * Setter du premierCoup
     */
    public void setPremierCoup(boolean b){
    	this.premierCoup = b;
    }
}






