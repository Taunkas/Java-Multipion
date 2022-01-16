package multipion.interfacegraphique;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

import multipion.MultiPion;
import multipion.interfacegraphique.jeu.Fenetre;
/**
 * Menu du jeu
 * Acces au different mode de jeu
 */
public class Menu extends JFrame implements ActionListener{
	
	/**
	 * Bouton un joueur
	 */
	private JButton unJoueur;
	
	/**
	 * Bouton deux joueurs
	 */
	private JButton deuxJoueurs;
	
	/**
	 * Bouton a Propos
	 */
	private JButton aPropos;
	
	/**
	 * Bouton quitter
	 */
	private JButton quitter;
	
	/**
	 * Bouton IA contre IA
	 */
	private JButton iavsia;
	
	/**
	 * Textfield taille grille
	 */
	private JTextField dimgrille;
	
	/**
	 * Reference du content panel de la fenetre
	 */
	private JPanel conteneur;
	
	/**
	 * Taille de la grille
	 */
	public static int taillegrille;
	
	
	/**
	 * Constructeur
	 */
	public Menu(){
		super("Jeu MultiPion");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(Case.CASE_LENGTH * 9, Case.CASE_LENGTH * 9);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		initFenetre();
		this.setVisible(true);
		this.setIconImage(MultiPion.ICON);
	}
	
	/**
	 * Instancie et positionne les elements de la fenetre
	 */
	public void initFenetre(){
		//Initialise les boutons
		Dimension taille = new Dimension(150, 50);
		unJoueur = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"1joueur.png")));
		unJoueur.setPreferredSize(taille);
		unJoueur.addActionListener(this);
		
		deuxJoueurs = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"2joueur.png")));
		deuxJoueurs.setPreferredSize(taille);
		deuxJoueurs.addActionListener(this);
		
		aPropos = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"info.png")));
		aPropos.setBorderPainted(false);
		aPropos.setPreferredSize(new Dimension(45, 45));
		aPropos.addActionListener(this);
		
		quitter = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"quit.png")));
		quitter.setPreferredSize(new Dimension(45, 45));
		quitter.setBorderPainted(false);
		quitter.addActionListener(this);
		
		dimgrille = new JTextField("3");
		dimgrille.setBackground(new Color(53,56,74));
		dimgrille.setFont(new Font("Arial", Font.PLAIN, 20));
		dimgrille.setHorizontalAlignment(JTextField.CENTER);
		dimgrille.setForeground(new Color(180,180,180));
		dimgrille.setPreferredSize(taille);
		
		iavsia = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"ia.png")));
		iavsia.setPreferredSize(taille);
		iavsia.addActionListener(this);

		 
		//Initialise le Jpanel principal
		conteneur = new JPanel();
		conteneur.setBackground(new Color(33,36,54));
		conteneur.setLayout(new GridBagLayout());
		this.setContentPane(conteneur);
		
		//Label
		ImageIcon image = new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"Titre.png"));
		JLabel titre = new JLabel(image);
		titre.setPreferredSize(new Dimension(450, 80));
		titre.setFont(new Font("Dialog", Font.BOLD,50));
		titre.setHorizontalAlignment(JLabel.CENTER);
		
		
		//Positionnement
		GridBagConstraints gbc = new GridBagConstraints();
		
		//positionnement titre
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		conteneur.add(titre, gbc);
		
		//positionnement un joueur
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		conteneur.add(unJoueur, gbc);
		
		//postionnement deux joueurs
		gbc.gridy = 2;
		conteneur.add(deuxJoueurs, gbc);
		

		//positionnement iavsia
		gbc.gridy = 3;
		conteneur.add(iavsia, gbc);
		
		
		//positionnement choix de dimesnion de la grille
		gbc.gridy = 4;
		conteneur.add(dimgrille, gbc);
		
		//positionnement a propos
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		conteneur.add(aPropos, gbc);
		
		//positionnement quitter
		gbc.gridx = 3;
		conteneur.add(quitter, gbc);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		if(source == unJoueur){
			//Cree la nouvelle fenetre de selection
			new Selection1Joueur(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2, this);
			
			//Recupere l'info de la taille de la grille
			//Si l'info n'est pas un nombre initialise e 3
			try {
				taillegrille=Integer.parseInt(dimgrille.getText());	
			}catch (Exception 	 g) {
				
				// met la taille sur 5
	             taillegrille=5;
	          }
			
			//Si la taille n'est pas comprise entre 10 et 3 alors renvoie e 10 ou e 3 si au dessus ou en dessous resp
			if(taillegrille>10) {
				taillegrille=10;
			}else if(taillegrille<3) {
				taillegrille=3;
			}
		}
		if(source == deuxJoueurs){
		
			//Recupere l'info de la taille de la grille
			//Si l'info n'est pas un nombre initialise e 3
			try {
				taillegrille=Integer.parseInt(dimgrille.getText());	
			}catch (Exception 	 g) {
				
				// met la taille sur 5
				taillegrille=5;
	          }
			
			//Si la taille n'est pas comprise entre 10 et 3 alors renvoie e 10 ou e 3 si au dessus ou en dessous resp
			if(taillegrille>10) {
				taillegrille=10;
			}else if(taillegrille<3) {
				taillegrille=3;
			}		
			
			this.setVisible(false);
			this.dispose();
			new Fenetre(this.getX() + this.getWidth(), this.getY() + this.getHeight());
			
		}
		if(source == aPropos){
			new Info();
		}
		if(source == quitter){
			setVisible(false);
			this.dispose();
			 System.exit(0);
		}
		
		
		if(source == iavsia){
			
			//Recupere l'info de la taille de la grille
			//Si l'info n'est pas un nombre initialise a 3
			try {
				taillegrille=Integer.parseInt(dimgrille.getText());	
			}catch (Exception 	 g) {
				
				// met la taille sur 5
				taillegrille=5;
	          }
			
			//Si la taille n'est pas comprise entre 10 et 3 alors renvoie a 10 ou a 3 si au dessus ou en dessous resp
			if(taillegrille>10) {
				taillegrille=10;
			}else if(taillegrille<3) {
				taillegrille=3;
			}
			new ConfigIAvsIA(this, this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
}

