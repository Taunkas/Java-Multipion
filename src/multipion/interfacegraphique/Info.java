package multipion.interfacegraphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Fenetre A propos
 *
 */
public class Info extends JDialog {
	
	JPanel pan;
	
	/**
	 * Constructeur de la classe About
	 */
	public Info(){
		super();
		this.setTitle("A propos");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		this.setSize(new Dimension(300,400));
		this.setLocationRelativeTo(null);
		this.setContentPane(pan);
		initFenetre();
		this.setVisible(true);
	}
	
	/**
	 * Construction de la fenetre
	 */
	public void initFenetre(){
		String text = "<html>" +
				"<h1>Jeu MultiPions</h1>" +
				"<br /><i>par<br /> " +
				"</i>David RIGAUX <br />" +
				" Etienne GOULPEAU <br /><br /><br />" +
				"<i>M1 - 2021-2022</i>" +
				"</html> ";

		JLabel credit = new JLabel(text);

		credit.setFont(new Font("Dialog", Font.PLAIN,20));
		credit.setHorizontalAlignment(JLabel.CENTER);
		pan.add(credit, BorderLayout.NORTH);
		
	}
}
