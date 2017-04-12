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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class OrderPalletPane extends BasicPane {

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
	private static final int CUSTOMERNAME = 3;
	private static final int DESTINATION = 2;
	private static final int DELIVERYDATE = 1;
	private static final int QUANTITY = 0;
//	private int ingridientCount=0;

	/**
	 * The total number of fields in the fields array.
	 */
	private static final int NBR_FIELDS = 4;

	/**
	 * Create the login pane.
	 * 
	 * @param db
	 *            The database object.
	 */
	public OrderPalletPane(Database db) {
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
		fields[DELIVERYDATE] = new JTextField(10);
		fields[DESTINATION] = new JTextField(20);
		fields[QUANTITY] = new JTextField(5);
		fields[CUSTOMERNAME] = new JTextField(20);
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

		JButton createButton = new JButton("Order new pallet");
		createButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					int quantity = Integer.parseInt(fields[QUANTITY].getText());
					String customer = fields[CUSTOMERNAME].getText();
					String destination = fields[DESTINATION].getText();
					String cookieName = (String)productList.getSelectedItem();
					SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
					Date deldate = parser.parse(fields[DELIVERYDATE].getText());
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					if(customer.equals("")){
						messageLabel.setText("Error, customer name needs a value");
						return;
					}
					if(destination.equals("")){
						messageLabel.setText("Error, destination needs a value");
						return;
					}
				//	Date prodate = parser.parse(fields[PRODUCTIONDATE].getText());
					//querry update new pallet with productlistname and quantity: quantity at date Date.
					mainpanel.revalidate();
					mainpanel.repaint();
					System.out.println("ADD!");
					messageLabel.setText("succesfully added " + quantity + " pallets of " + cookieName + " to be delivered at " + 
							fields[DELIVERYDATE].getText() + "to location " + destination);
					String res = db.tryCreateCustomer(customer, destination);
					if(res!=null){
						if(!confirmUpdateDialog(res,customer,destination)){
							return;
						}
					}
					//update rest
					db.placeOrder(customer, df.format(deldate),cookieName,quantity);
					db.updateOrders(cookieName);
					fields[QUANTITY].setText("");
					fields[DELIVERYDATE].setText("");
					fields[CUSTOMERNAME].setText("");
					fields[DESTINATION].setText("");
				}catch(NumberFormatException ex){
					System.out.println("ERROR");
					messageLabel.setText("Error, quantity must be an int");
				} catch (ParseException e1) {
					messageLabel.setText("Error, wrong format of date, please use yyyy-MM-dd");
				}
			}
		});
		mainpanel.add(createButton,c);
		c.gridx=2;
		c.gridy=0;
		mainpanel.add(new JLabel("Nbr of pallets"),c);
		c.gridx=3;
		mainpanel.add(new JLabel("Destination"),c);
		c.gridy=2;
		c.gridx=2;
		mainpanel.add(new JLabel("Delivery date"),c);
		c.gridx=3;
		mainpanel.add(new JLabel("Customer name"),c);
		c.gridy=1;
		c.gridx=3;
		mainpanel.add(fields[DESTINATION],c);
		c.gridx=2;
		mainpanel.add(fields[QUANTITY],c);
		c.gridy=3;
		mainpanel.add(fields[DELIVERYDATE],c);
		c.gridx=3;
		mainpanel.add(fields[CUSTOMERNAME],c);
	}
	
	public boolean confirmUpdateDialog(String message,String customer,String address){
		Object[] options = {"Yes", "No"};
		int i = JOptionPane.showOptionDialog(null, message, "Update product",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
		if(i==0){
			db.updateCustomer(customer,address);
			return true;
		}
		return false;
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
	//	fields[PRODUCT_NAME].addActionListener(actHand);
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
				System.out.println("save");
				break;
			case 1:
				System.out.println("cancel");
				refresh();
				break;
			default:
				break;
			}
		}
	}

}
