package multipion.interfacegraphique.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import multipion.MultiPion;
import multipion.interfacegraphique.Case;
import multipion.jeu.Jeu;
import multipion.jeu.IA.ValeursEvaluation;

public class Fenetre extends JFrame implements ActionListener{
	
	/**
	 * Reference du jeu
	 */
	private Jeu jeu;
	
	/**
	 * Reference de la grille
	 */
	private Grille grille;
	
	/**
	 * Reference du content panel de la fenetre
	 */
	private JPanel conteneurGeneral;
	
	/**
	 * Reference de l'affichage des pieces prises blanches
	 */
	private PionPris prisesBlanches;
	
	/**
	 * Reference de l'affichage des pieces prises noires
	 */
	private PionPris prisesNoires;
	
	/**
	 * Zone de text pour l'affichage d'informations sur la partie
	 */
	private JTextArea logsPartie;
	
	/**
	 * Affichage du joueur courant
	 */
	private JoueurCourant joueurCourant;
	
	/**
	 * Affichage des coordonnees abscisses du plateau
	 */
	private JPanel coordAbscisse;
	
	/**
	 * Affichage d'un effet de mise en attente pendant le tour de l'IA
	 */
	private JPanel tourIA;
	
	/**
	 * Affichage des coordonnees ordonnees du plateau
	 */
	private JPanel coordOrdonnee;
	
	/**
	 * Taille de la grille choix de 3 � 10 
	 */	
	public static int taillegrille=multipion.interfacegraphique.Menu.taillegrille;
	
	/**
	 * Lettre de A � J  
	 */	
	public char lettre='A';

	/**
	 * Constructeur 2 joueur
	 */
	public Fenetre(int x, int y){
		// nom de la JFrame
		super("Jeu MultiPion");
		// Cr�er un nouveau jeux de base donc joueur contre joueur 
		jeu = new Jeu(this);
		// parametre pour la JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x +100 - this.getWidth(), y+100 - this.getHeight());
		this.setVisible(true);
		this.setIconImage(MultiPion.ICON);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Constructeur 1 joueur
	 */
	public Fenetre(int x, int y, boolean couleur, int lvlia){
		// nom de la JFrame
		super("Jeu Multipion");	
		jeu = new Jeu(this, couleur, lvlia);
		// parametre pour la JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x +100 - this.getWidth(), y+100 - this.getHeight());
		this.setVisible(true);
		this.setIconImage(MultiPion.ICON);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Constructeur IA vs IA
	 */
	public Fenetre(int x, int y, int niveauBlanc, int niveauNoir, ValeursEvaluation valeursBlanc, ValeursEvaluation valeursNoir){
		// nom de la JFrame
		super("Jeu MultiPion");
		// Cr�er un nouveau jeux iavsia choix des couleurs et des niveaux des l'ia	
		jeu = new Jeu(this, niveauBlanc, niveauNoir, valeursBlanc, valeursNoir);
		// parametre pour la JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = new Dimension(Case.CASE_LENGTH * 14, Case.CASE_LENGTH * 12);
		this.setSize(dim);
		this.setMinimumSize(dim);
		initFenetre();
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		this.setVisible(true);
		this.setIconImage(MultiPion.ICON);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	

	


	
	/**
	 * Initialise les variables et positionne les elements sur la fenetre
	 */
	private void initFenetre(){
		
		//Creation de tous les conteneurs
		//Conteneur general de la fenetre
		conteneurGeneral = new JPanel();
		conteneurGeneral.setBackground(new Color(33,36,54));
		conteneurGeneral.setPreferredSize(this.getPreferredSize());

		//Affichage du joueur courant
		joueurCourant = new JoueurCourant(this);
		joueurCourant.setBackground(new Color(33,36,54));
		joueurCourant.setPreferredSize(new Dimension(Case.CASE_LENGTH * 4, Case.CASE_LENGTH));
		
		//Grille du plateau de jeu
		grille = new Grille(this);
		grille.setPreferredSize(new Dimension(Case.CASE_LENGTH * 8, Case.CASE_LENGTH * 8));
		
		//Initialisation des conteneur de pieces prises.
		//Prises blanches
		prisesBlanches = new PionPris(jeu, true);
		prisesBlanches.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		JPanel blancPriseConteneur = new JPanel();
		blancPriseConteneur.setBackground(new Color(200,200,200));
		blancPriseConteneur.add(prisesBlanches);
		blancPriseConteneur.setBorder(BorderFactory.createTitledBorder("Prises blanches"));
		blancPriseConteneur.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		
		//Prises noires
		prisesNoires = new PionPris(jeu, false);
		prisesNoires.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		JPanel noirPriseConteneur = new JPanel();
		noirPriseConteneur.setBackground(new Color(200,200,200));
		noirPriseConteneur.add(prisesNoires);
		noirPriseConteneur.setBorder(BorderFactory.createTitledBorder("Prises noires"));
		noirPriseConteneur.setPreferredSize(new Dimension(Case.CASE_LENGTH*2, Case.CASE_LENGTH*8));
		
		//Logs de la partie
		logsPartie = new JTextArea();
		logsPartie.setEditable(false);
		logsPartie.setLineWrap(true);
		logsPartie.setMargin(new Insets(5,5,5,5));
		JScrollPane scrollZone = new JScrollPane(logsPartie);
		scrollZone.setPreferredSize(new Dimension(Case.CASE_LENGTH * 8, Case.CASE_LENGTH * 1));
		scrollZone.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		//Coords
		coordAbscisse = new JPanel();
		coordAbscisse.setBackground(new Color(33,36,54));
		coordAbscisse.setLayout(new GridLayout(1, taillegrille));
		
		// Lettres definissant les coordonn�e du plateau
		if(taillegrille==3) {
			lettre='C';
		} else if(taillegrille==4) {
			lettre='D';
		} else if(taillegrille==5) {
			lettre='E';
		} else if(taillegrille==6) {
			lettre='F';
		} else if(taillegrille==7) {
			lettre='G';
		} else if(taillegrille==8) {
			lettre='H';
		} else if(taillegrille==9) {
			lettre='I';
		} else if(taillegrille==10) {
			lettre='J';
		} 
	
		// Cr�er les indications lettre du plateau
		for(char i = 'A'; i <= lettre; i++){
			JLabel c = new JLabel(i+"", JLabel.CENTER);
			c.setForeground(new Color(220,220,220));
			c.setPreferredSize(new Dimension(Case.CASE_LENGTH/taillegrille*8 , 9));
			coordAbscisse.add(c);
		}
		coordOrdonnee = new JPanel();
		coordOrdonnee.setBackground(new Color(33,36,54));
		coordOrdonnee.setLayout(new GridLayout(taillegrille, 1));
		
		// Cr�er les indications chiffres du plateau
		for(int i = taillegrille; i >= 1; i--){
			JLabel c = new JLabel(i+"");
			c.setForeground(new Color(220,220,220));
			c.setPreferredSize(new Dimension(9, Case.CASE_LENGTH/taillegrille*8 ));
			coordOrdonnee.add(c);
		}
		
		//Tour de l'ia
		tourIA = new JPanel(){
			public void paintComponent(Graphics g){
				
				// image attente IA
				try{
					Image img = ImageIO.read(getClass().getResource(MultiPion.RES_PATH+"sablier.png"));
					g.drawImage(img, 0, 0, this);
				}catch (Exception e){
						e.printStackTrace();
				}
			}
		};
		try{
			Image img = ImageIO.read(getClass().getResource(MultiPion.RES_PATH+"sablier.png"));
			tourIA.setSize(img.getWidth(null), img.getHeight(null));
			tourIA.setBounds(this.getWidth()/2 - tourIA.getWidth()/2, this.getHeight()/2 - tourIA.getHeight()/2, tourIA.getWidth(), tourIA.getHeight());
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		//Positionnement sur le GridBagLayout
		conteneurGeneral.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Placement de affichage joueur courant
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 10, 0, 0);
		conteneurGeneral.add(joueurCourant, gbc);
		
		//Placement prise des pieces blanches
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 10, 0, 0);
		conteneurGeneral.add(blancPriseConteneur, gbc);
		
		//Placement des ordonnees
		gbc.gridx = 1;
		conteneurGeneral.add(coordOrdonnee, gbc);
		
		//Placement de la grille
		gbc.gridx = 2;
		gbc.insets = new Insets(0,10,0,10);
		conteneurGeneral.add(grille, gbc);
		
		//Placement prise des pieces noires
		gbc.gridx = 3;
		gbc.insets = new Insets(0,10,0,10);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		conteneurGeneral.add(noirPriseConteneur, gbc);
		
		//placement des abscisses
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10,10,0,10);
		conteneurGeneral.add(coordAbscisse, gbc);
		
		//Placement des logs de la partie
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridwidth = 1;
		conteneurGeneral.add(scrollZone, gbc);
		
		this.setContentPane(conteneurGeneral);
		
		JPanel gp = (JPanel)this.getGlassPane();
		gp.setLayout(null);
		gp.add(tourIA);
		gp.setVisible(false);
		
		// Demmare le Jeu
		jeu.demarrerPartieIA();
	}
	
	/**
	 * Ajoute un message au logs de la partie
	 */
	public void addLogPartie(String s){
		logsPartie.append(s+"\n");
		logsPartie.setCaretPosition(logsPartie.getDocument().getLength());
	}
	
	/**
	 * Vide les logs de la partie
	 */
	public void resetlog(){
		logsPartie.setText("");
	}
	
	/**
	 * Fenetre de victoire : Cr�e la fen�tre de fin lorsque appel�
	 * @param couleur couleur du gagnant
	 * @param fin fa�on de gagner (Bloque son adversaire ou allez au bout du plateau
	 */
	public void Victoire(String couleur, String fin){
		
		new Fin(this, couleur, fin);
		
		
		
	}
	
	// Repaint la grille pour actualiser les actions prises
	@Override
	public void repaint(){
		if(jeu.isVsIA()){
			if(jeu.getIAThread() != null && jeu.getIAThread().isReflechi()  ){
				return;
			}else if(jeu.getIAThread2() != null && jeu.getIAThread2().isReflechi() ){
				return;
			}
		}
		
		this.grille.updateGrille();
		this.joueurCourant.update();
		super.repaint();
	}
	
	/**
	 * Active ou desactive l'effet de mise en attente pendant le tour de l'IA
	 * @param b active ou desactive
	 */
	public void tourIA(boolean b){
		this.getGlassPane().setVisible(b);
	}
	/**
	 * Get jeu
	 */
	public Jeu getJeu(){
		return jeu;
	}
	
	/**
	 * Get grille
	 */
	public Grille getGrille(){
		return grille;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	

}
