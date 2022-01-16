package multipion;

import java.awt.Image;
import javax.imageio.ImageIO;

import multipion.interfacegraphique.Menu;

/**
 * Main qui lance le menu du jeu
 */
public class MultiPion {
	
	/**
	 * Chemin des images du jeu
	 */
	public static final String RES_PATH = "/res/";
	
	/**
	 * Icone des fenetres
	 */ 
	public static Image ICON = null;

	/**
	 * Main
	 * @param args
	 */
    public static void main(String[] args){
    	
    	//Chargement de l'icon
    	try{
			ICON = ImageIO.read(MultiPion.class.getResource(MultiPion.RES_PATH+"logo.png"));
		}catch(Exception e){
			System.out.println("Impossible de charger l'image du logo");
		}
    	
    	new Menu();
    }


}
