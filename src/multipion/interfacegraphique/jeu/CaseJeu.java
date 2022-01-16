package multipion.interfacegraphique.jeu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import multipion.interfacegraphique.Case;

/**
 * Une case de la grille
 */
public class CaseJeu extends Case implements MouseListener{
	
	/**
	 * Active/desactive la prise d'input
	 */
	private boolean recoisInput;
	
	/**
	 * Reference de la fenetre
	 */
	private Fenetre fenetre;
	
	/**
	 * Constructeur d'une case vide
	 * @param x coordonnee en abscisse
	 * @param y coordonnee en ordonnee
	 * @param backgroundColor couleur du fond
	 * @param fenetre reference de la fenetre
	 */
	public CaseJeu(int x, int y, Color backgroundColor, Fenetre fenetre){
		super(x, y, backgroundColor);
		this.fenetre = fenetre;
		this.addMouseListener(this);
		this.recoisInput = true;
	}
	
	/**
	 * Test si l'etat de la case est SELECTIONE ou DEPLACEMENT_POSSIBLE
	 */
	public boolean isSelectionee(){
		return etat.equals(Etat.SELECTIONE) || etat.equals(Etat.DEPLACEMENT_POSSIBLE);
	}
	
	/**
	 * Setter de la prise d'input
	 */
	public void setRecoisInput(boolean recoisInput){
		this.recoisInput = recoisInput;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(!recoisInput){
			Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, 0.5f));
	        g2d.setColor(Color.WHITE);
	        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	        g2d.setComposite(AlphaComposite.getInstance(
		            AlphaComposite.SRC_OVER, 1.0f));
		}
	}

	// Gere le clicque de s�lection d'une case en fonction de ou l'on clique
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(recoisInput){
			// Change l'état d'une case en fonction en séléctionné si �a appartient au joueur courant
			if(this.couleur.equals(fenetre.getJeu().getJoueurCourant().getCouleur())){
				fenetre.getGrille().resetSelectedCases();
				this.etat = Etat.SELECTIONE;
				fenetre.getJeu().setPieceSelectionee(x, y);
			}else{
			// Sinon renvoie un message au joueur courant de s�l�ctionner l'un de ses pions
				if(!fenetre.getJeu().aucunePieceSelectionee()){
					fenetre.getJeu().deplacerPiece(x, y);
				}else{
					fenetre.addLogPartie("Vous devez selectionnez un pion de votre couleur : "+fenetre.getJeu().getJoueurCourant().getCouleur().toLowerCase());
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}
