import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class CreatePalletPane extends BasicPane {

	private static final long serialVersionUID = 1;

	/**
	 * The text field where the user id is entered.
	 */
	private JTextField[] fields;
	private ArrayList<JLabel> labels;
	private ArrayList<String> ingridients;
	private ArrayList<Integer> quantities;
	private JButton[] buttons;
	private JPanel mainpanel;
	private JComboBox<String> productList;
	/**
	 * The number of the field where the user id is entered.
	 */
//	private static final int PRODUCT_NAME = 0;
//	private static final int PRODUCTIONDATE = 2;
//	private static final int DELIVERYDATE = 1;
	private static final int QUANTITY = 0;
//	private int ingridientCount=0;

	/**
	 * The total number of fields in the fields array.
	 */
	private static final int NBR_FIELDS = 1;

	/**
	 * Create the login pane.
	 * 
	 * @param db
	 *            The database object.
	 */
	public CreatePalletPane(Database db) {
		super(db);

	}

	/**
	 * Create the top panel, consisting of the text field.
	 * 
	 * @return The top panel.
	 */
	public JComponent createTopPanel() {
		labels = new ArrayList<JLabel>();
		ingridients = new ArrayList<String>();
		quantities = new ArrayList<Integer>();
		labels.add(new JLabel("Product name",JLabel.RIGHT));
		labels.add(new JLabel("Ingridient",JLabel.RIGHT));
		fields = new JTextField[NBR_FIELDS];
		//fields[DELIVERYDATE] = new JTextField(10);
		//fields[PRODUCTIONDATE] = new JTextField(10);
		fields[QUANTITY] = new JTextField(5);
		mainpanel = new JPanel();
		content();
		JScrollPane scroll = new JScrollPane(mainpanel);
		scroll.setPreferredSize(new Dimension(700,250));
		return scroll;
	}
	
	private void content(){
		mainpanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx=0.2;
		c.gridx=1;
		c.gridy=1;
		productList = new JComboBox<String>(db.getCookies());
		mainpanel.add(productList,c);
		c.gridx=0;
		c.gridy=1;

		JButton createButton = new JButton("Create new pallet");
		createButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					int quantity = Integer.parseInt(fields[QUANTITY].getText());

					SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
					Date deldate = new Date();
					String cookieName = (String)productList.getSelectedItem();
					String res = db.tryCreatePallets(cookieName, quantity);
					System.out.println("res: " + res);
					if(res == null){
						messageLabel.setText("succesfully created " + quantity + " pallets of " + cookieName + " at " + 
								parser.format(deldate));
						db.updateOrders(cookieName);
					}
					else{
						messageLabel.setText(res);
					}
					//check if enough ingridients
						//check if pending orders
							//create pallet and link to order
						//create pallet in storage
					
					//querry update new pallet with productlistname and quantity: quantity at date Date.
					mainpanel.revalidate();
					mainpanel.repaint();
					//System.out.println("ADD!");
					
			//		messageLabel.setText("succesfully created " + quantity + " pallets of " + (String)productList.getSelectedItem() + " at " + 
				//			parser.format(deldate));
					fields[QUANTITY].setText("");
				//	fields[DELIVERYDATE].setText("");
				//	fields[PRODUCTIONDATE].setText("");
				}catch(NumberFormatException ex){
					System.out.println("ERROR");
					messageLabel.setText("Error, quantity must be an int");
				}
			}
		});
		mainpanel.add(createButton,c);
		c.gridx=2;
		c.gridy=0;
		mainpanel.add(new JLabel("Quantity"),c);
		//c.gridx=3;
		//mainpanel.add(new JLabel("Delivery date"),c);
	//	c.gridx=4;
		//mainpanel.add(new JLabel("Production date"),c);
		c.gridy=1;
	//	mainpanel.add(fields[PRODUCTIONDATE],c);
		//c.gridx=3;
		//mainpanel.add(fields[DELIVERYDATE],c);
		c.gridx=2;
		mainpanel.add(fields[QUANTITY],c);
	}
	
	

	/**
	 * Create the bottom panel, consisting of the login button and the message
	 * line.
	 * 
	 * @return The bottom panel.
	 */
	public JComponent createBottomPanel() {
		buttons = new JButton[0];
		ActionHandler actHand = new ActionHandler();
		return new ButtonAndMessagePanel(buttons, messageLabel, actHand);
	}

	/**
	 * Perform the entry actions of this pane, i.e. clear the message line.
	 */
	public void entryActions() {
		clearMessage();
		refresh();
	}

	private void refresh(){
		mainpanel.removeAll();
		ingridients.clear();
		quantities.clear();
		content();
		mainpanel.revalidate();
		mainpanel.repaint();
	}
	/**
	 * A class which listens for button clicks.
	 */
	class ActionHandler implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			//String userId = fields[PRODUCT_NAME].getText();
			int action = Integer.parseInt(e.getActionCommand());
			switch(action){
			case 0:	
	/*			if(fields[PRODUCT_NAME].getText().equals("")){
					messageLabel.setText("Error, the product needs a name");
					break;
				}
				else if(ingridients.isEmpty()){
					messageLabel.setText("Error, the product needs atleast one ingrident");
					break;
				}*/
				System.out.println("save");
				break;
			case 1:
				System.out.println("cancel");
				refresh();
				break;
			default:
				break;
			}
			/* --- insert own code here --- */
			/*System.out.println(userId); 
            if(db.login(userId)){
            	CurrentUser.instance().loginAs(userId);
            }*/
			//15 cookies per bag
			//10 bags per box
			//36 boxes per pallet
		}
	}

}

