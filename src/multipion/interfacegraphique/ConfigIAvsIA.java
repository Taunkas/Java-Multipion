package multipion.interfacegraphique;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import multipion.MultiPion;
import multipion.interfacegraphique.jeu.Fenetre;
import java.awt.*;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * Fenetre de selection des IA pour un mode de jeu IA contre IA
 */
public class ConfigIAvsIA extends JFrame implements ActionListener{
	
	/**
	 * Reference du menu
	 */
	private Menu menu;
	
	/**
	 * Reference du content panel de la fenetre
	 */
	private JPanel conteneurGeneral;
	
	/**
	 * Boutons des niveaux d'IA blanc
	 */
	private JRadioButton niveau1Blanc, niveau2Blanc, niveau3Blanc;
	
	/**
	 * Boutons des niveaux d'IA noir
	 */
	private JRadioButton niveau1Noir, niveau2Noir, niveau3Noir;
	
	/**
	 * Label des choix de niveaux d'IA blanc
	 */
	private JLabel choixNiveauxBlanc, choixNiveauxNoir;
	/**
	 * Bouton valider et reset
	 */
	private JButton valider;
	
	/**
	 * Constructeur
	 * @param menu reference du menu
	 * @param x coordonnee x de la fenetre
	 * @param y coordonnee y de la fenetre
	 */
	public ConfigIAvsIA(Menu menu, int x, int y){
		super("Parametrage de l'IA vs IA");
		this.menu = menu;
		this.setSize(new Dimension(700, 300));
		this.setIconImage(MultiPion.ICON);
		this.setMinimumSize(this.getSize());
		this.setLocation(x - this.getWidth()/2, y - this.getHeight()/2);
		initFenetre();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Initialise les variables et positionne les elements de la fenetre
	 */
	private void initFenetre(){
		//Conteneur general
		conteneurGeneral = new JPanel();
		conteneurGeneral.setBackground(new Color(33,36,54));
		
		//Label choix niveaux
		choixNiveauxBlanc = new JLabel("Niveau IA blanc");
		choixNiveauxBlanc.setForeground(new Color(255,255,255));
		choixNiveauxBlanc.setFont(new Font("Lucida Fax", Font.BOLD,20));
		choixNiveauxNoir = new JLabel("Niveau IA noir");
		choixNiveauxNoir.setForeground(new Color(255,255,255));
		choixNiveauxNoir.setFont(new Font("Lucida Fax", Font.BOLD,20));

		//niveaux
		niveau1Blanc = new JRadioButton("Niveau 1", true);
		niveau1Blanc.setBackground(new Color(33,36,54));
		niveau1Blanc.setForeground(new Color(255,255,255));
		niveau1Blanc.setPreferredSize(new Dimension(100, 20));
		niveau1Blanc.addActionListener(this);

		niveau2Blanc = new JRadioButton("Niveau 2");
		niveau2Blanc.setBackground(new Color(33,36,54));
		niveau2Blanc.setForeground(new Color(255,255,255));
		niveau2Blanc.setPreferredSize(new Dimension(100, 20));
		niveau2Blanc.addActionListener(this);

		niveau3Blanc = new JRadioButton("Niveau 3");
		niveau3Blanc.setPreferredSize(new Dimension(100, 20));
		niveau3Blanc.setBackground(new Color(33,36,54));
		niveau3Blanc.setForeground(new Color(255,255,255));
		niveau3Blanc.addActionListener(this);

		niveau1Noir = new JRadioButton("Niveau 1", true);
		niveau1Noir.setPreferredSize(new Dimension(100, 20));
		niveau1Noir.setBackground(new Color(33,36,54));
		niveau1Noir.setForeground(new Color(255,255,255));
		niveau1Noir.addActionListener(this);

		niveau2Noir = new JRadioButton("Niveau 2");
		niveau2Noir.setPreferredSize(new Dimension(100, 20));
		niveau2Noir.setBackground(new Color(33,36,54));
		niveau2Noir.setForeground(new Color(255,255,255));
		niveau2Noir.addActionListener(this);

		niveau3Noir = new JRadioButton("Niveau 3");
		niveau3Noir.setPreferredSize(new Dimension(100, 20));
		niveau3Noir.setBackground(new Color(33,36,54));
		niveau3Noir.setForeground(new Color(255,255,255));
		niveau3Noir.addActionListener(this);

		
		//Bouton valider
		valider = new JButton(new ImageIcon(getClass().getResource(MultiPion.RES_PATH+"valider.png")));
		valider.setPreferredSize(new Dimension(150, 50));
		valider.addActionListener(this);
		
		
		//Positionnement
		conteneurGeneral.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		int margeMilieu = 80;
		
		//postionnement choix niveaux
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 6;
		gbc.weightx = 1;
		gbc.insets = new Insets(15,15,15,margeMilieu);
		conteneurGeneral.add(choixNiveauxBlanc, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 15;
		conteneurGeneral.add(choixNiveauxNoir, gbc);
		
		//positionnement niveaux
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0,0,0,0);
		conteneurGeneral.add(niveau1Blanc, gbc);
		gbc.gridx = 2;
		conteneurGeneral.add(niveau2Blanc, gbc);
		gbc.gridx = 4;
		gbc.insets.right = margeMilieu;
		conteneurGeneral.add(niveau3Blanc, gbc);
		gbc.gridx = 6;
		gbc.insets.right = 0;
		conteneurGeneral.add(niveau1Noir, gbc);
		gbc.gridx = 8;
		conteneurGeneral.add(niveau2Noir, gbc);
		gbc.gridx = 10;
		conteneurGeneral.add(niveau3Noir, gbc);
		
		
		//positionnement du bouton
		
		gbc.gridx = 2;
		gbc.gridwidth = 8;
		gbc.insets = new Insets(60,60,60,60);
		gbc.gridy = 15;
		gbc.anchor = GridBagConstraints.CENTER;
		conteneurGeneral.add(valider, gbc);
		
		
		this.setContentPane(conteneurGeneral);
	}

	//Niveau IA
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == niveau1Blanc || source == niveau2Blanc || source == niveau3Blanc){
			niveau1Blanc.setSelected(false);
			niveau2Blanc.setSelected(false);
			niveau3Blanc.setSelected(false);
			JRadioButton radio = (JRadioButton)source;
			radio.setSelected(true);
		}
		
		if(source == niveau1Noir || source == niveau2Noir || source == niveau3Noir){
			niveau1Noir.setSelected(false);
			niveau2Noir.setSelected(false);
			niveau3Noir.setSelected(false);
			JRadioButton radio = (JRadioButton)source;
			radio.setSelected(true);
		}
		
		// Valide les informations lors du click sur valider et lance si tout est bon
		if(source == valider){
				int niveauBlanc = 1;
				if(niveau2Blanc.isSelected()) niveauBlanc = 2;
				else if(niveau3Blanc.isSelected()) niveauBlanc = 3;
				
				int niveauNoir = 1;
				if(niveau2Noir.isSelected()) niveauNoir = 2;
				else if(niveau3Noir.isSelected()) niveauNoir = 3;
				
				new Fenetre(this.getX(), this.getY(), niveauBlanc, niveauNoir, null, null);
				this.setVisible(false);
				this.dispose();
				this.menu.setVisible(false);
				this.menu.dispose();
		}
		
	}

}
