package multipion.interfacegraphique.jeu;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import multipion.MultiPion;
import multipion.interfacegraphique.Case;
import multipion.interfacegraphique.PiecesPrises;
import multipion.jeu.Jeu;
import multipion.jeu.pion.Pion;

/**
 * Affiche les pieces prises
 */
public class PionPris extends PiecesPrises{
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Constructeur
	 * @param jeu refenrence du jeu
	 * @param priseBlanc vrai si prises blanches
	 */
	public PionPris(Jeu jeu, boolean priseBlanc){
		super(priseBlanc);
		this.jeu = jeu;
	}
	
	// Affiche sur les  les prises des joueurs 
	@Override
	public void paintComponent(Graphics g){
		ArrayList<Pion> priseTemp = jeu.getPrises();
		String couleur = (priseBlanc) ? "BLANC" : "NOIR";
		int x=0;
		int y=0;
		for(int i=0; i<priseTemp.size(); i++){
			Pion temp = priseTemp.get(i);
			if(couleur.equals(priseTemp.get(i).getCouleur())){
				//Dessine l'image de la piece
				char couleurFile = (couleur.equals("NOIR"))? 'n': 'b';
				Image imgPiece = null;
				try{
					imgPiece = ImageIO.read(getClass().getResource(MultiPion.RES_PATH+"pion_"+couleurFile+".png"));
					g.drawImage(imgPiece, x, y, this);
					x+=Case.CASE_LENGTH;
					if(x>=Case.CASE_LENGTH * 2){
						y+=Case.CASE_LENGTH;
						x=0;
					}
					
				}catch(IOException e){
					System.out.println("erreur chargement image pion ");
				}
			}
			
		}
	}

}
