package multipion.interfacegraphique.jeu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import multipion.MultiPion;
import multipion.interfacegraphique.Menu;

/**
 * Fenetre du formulaire de creation de sauvegarde de partie terminee au format PGN
 */
public class Fin extends JFrame implements ActionListener{
	
	/**
	 * content panel de la fenetre
	 */
	private JPanel conteneurGeneral;
	
	/**
	 * conteneur des meta donnee de la partie
	 */
	private JPanel conteneurMetaDonnee;
	
	/**
	 * conteneur des boutons de validation
	 */
	private JPanel conteneurBoutons;
	
	/**
	 * Reference de la fenetre de jeu
	 */
	private Fenetre fenetre;
	
	/**
	 * Bouton creation sauvegarde
	 */
	private JButton rejouer;
	
	/**
	 * Bouton quitter
	 */
	private JButton quitter;
	
	/**
	 * label message victoire
	 */
	private JLabel victoire;
	
	/**
	 * Label message condition
	 */
	private JLabel victoireinfo;
	
	/**
	 * Label message rejouer
	 */
	private JLabel replay;
	
	
	/**
	 * Resultat de la partie
	 */
	private String resultat;
	
	/**
	 * Condition de Fin de la partie
	 */
	private String Conditionfin;
	
	/**
	 * Constructeur
	 * @param jeu reference du jeu
	 * @param fenetre reference de la fenetre de jeu
	 */
	public Fin(Fenetre fenetre, String resultat, String Conditionfin){
		super("Partie Fini");
		this.fenetre = fenetre;
		this.resultat = resultat;
		this.Conditionfin = Conditionfin;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(fenetre);
		this.setSize(400, 300);
		this.setResizable(false);
		initFenetre();
		this.setVisible(true);
		this.setIconImage(MultiPion.ICON);

	}
	
	/**
	 * Instancie et positionne les elements de la fenetre
	 */
	private void initFenetre(){
		//conteneurs
		conteneurGeneral = new JPanel();
		conteneurMetaDonnee = new JPanel();
		conteneurMetaDonnee.setPreferredSize(new Dimension(200, 200));
		conteneurBoutons = new JPanel();
		conteneurBoutons.setPreferredSize(new Dimension(450, 50));
		
		//Les labels r�cup�re les infos de la la fen�tre pour personaliser le message :
		victoire = new JLabel("Bravo au joueur " +resultat+ ", vous avez gagnez !", JLabel.RIGHT);
		
		victoireinfo = new JLabel("Vous avez remportez la partie " +Conditionfin, JLabel.RIGHT);
		
		replay = new JLabel("Voulez-vous rejouez ? " , JLabel.RIGHT);
	
		rejouer = new JButton("Rejouer");
		rejouer.addActionListener(this);
		quitter = new JButton("Quitter");
		quitter.addActionListener(this);
		
		

		GridBagConstraints gridresult = new GridBagConstraints();
	
		
		//Positionnement metadonnee
		conteneurMetaDonnee.setLayout(new GridBagLayout());
		conteneurMetaDonnee.setBorder(BorderFactory.createTitledBorder("Resultats :"));
		gridresult.gridx = 0;
		gridresult.gridy = 0;
		gridresult.gridwidth = 100;
		gridresult.gridheight = 1;
		gridresult.insets = new Insets(5,5,5,5);
		conteneurMetaDonnee.add(victoire, gridresult);

		gridresult.gridx = 0;
		gridresult.gridy = 3;
		gridresult.fill = GridBagConstraints.NONE;
		gridresult.gridwidth = 1;
		conteneurMetaDonnee.add(victoireinfo, gridresult);

		
		gridresult.gridx = 0;
		gridresult.gridy = 5;
		gridresult.fill = GridBagConstraints.NONE;
		gridresult.gridwidth = 1;
		conteneurMetaDonnee.add(replay, gridresult);
		
		//Positionnement des boutons
		conteneurBoutons.setLayout(new GridBagLayout());
		gridresult.gridx = 0;
		gridresult.gridy = 0;
		gridresult.gridwidth = 1;
		gridresult.gridheight = 1;
		gridresult.weightx = 1;
		gridresult.weighty = 1;
		gridresult.fill = GridBagConstraints.NONE;
		conteneurBoutons.add(quitter, gridresult);
		gridresult.gridx = 1;
		conteneurBoutons.add(rejouer, gridresult);
		
		//Postionnement general
		conteneurGeneral.setLayout(new BoxLayout(conteneurGeneral, BoxLayout.PAGE_AXIS));
		conteneurGeneral.add(conteneurMetaDonnee);
		conteneurGeneral.add(conteneurBoutons);
		
		this.setContentPane(conteneurGeneral);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == quitter){
			this.setVisible(false);

				fenetre.setVisible(false);
				fenetre.dispose();
				new Menu();
			
			this.dispose();
		} else if(source == rejouer){
			this.setVisible(false);

				fenetre.getJeu().reset();
				this.dispose();
				multipion.jeu.Jeu.fin=false;
		

		}
	}

}
