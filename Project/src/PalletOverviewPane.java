import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class PalletOverviewPane extends BasicPane {

	private static final long serialVersionUID = 1;

	/**
	 * The text field where the user id is entered.
	 */
	private JPanel mainPanel;
	private JTable table;
	private PalletTable myTable;
	private JTextField[] fields;
	private JComboBox<String> blockedList;
	private JScrollPane scroll;
	/**
	 * The number of the field where the user id is entered.
	 */
	private static final int DATETO = 3;
	private static final int DATEFROM = 2;
	private static final int CUSTOMER = 4;
	private static final int PALLETID = 0;
	private static final int COOKIE = 1;
	private static final int BLOCKFROM = 5;
	private static final int BLOCKTO = 6;
	private JComboBox<String> productList;
//	private int ingridientCount=0;

	/**
	 * The total number of fields in the fields array.
	 */
	private static final int NBR_FIELDS = 7;

	/**
	 * Create the login pane.
	 * 
	 * @param db
	 *            The database object.
	 */
	public PalletOverviewPane(Database db) {
		super(db);

	}

	/**
	 * Create the top panel, consisting of the text field.
	 * 
	 * @return The top panel.
	 */
	public JComponent createTopPanel() {

	//	content();
		fields = new JTextField[NBR_FIELDS];
		myTable = new PalletTable(db);
		table = new JTable(myTable);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setPreferredScrollableViewportSize(new Dimension(750, 100));
        table.setFillsViewportHeight(true);
		scroll = new JScrollPane(table);
	//	content();
		mainPanel= new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy=0;
		mainPanel.add(scroll,c);
		c.gridy=1;
		mainPanel.add(content(),c);
		//scroll.setPreferredSize(new Dimension(300,250));
		return mainPanel;
	}

	
	private JPanel content(){
		fields[DATEFROM] = new JTextField(10);
		fields[DATETO] = new JTextField(10);
		fields[BLOCKFROM] = new JTextField(10);
		fields[BLOCKTO] = new JTextField(10);
		fields[CUSTOMER] = new JTextField(20);
		fields[COOKIE] = new JTextField(20);
		fields[PALLETID] = new JTextField(5);
		productList = new JComboBox<String>(db.getCookies());
		JPanel temps = new JPanel();
		temps.setLayout(new GridBagLayout());
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx=0.2;
		c.gridx=1;
		c.gridy=1;
		
		blockedList = new JComboBox<String>(new String[]{"all","blocked","unblocked"});
		searchPanel.add(blockedList,c);
		c.gridx=0;
		c.gridy=1;

		JButton filterButton = new JButton("Filter");
		filterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					//db.clearOrders();
					String palletId="";
					if(!fields[PALLETID].getText().equals("")){
						int temp = Integer.parseInt(fields[PALLETID].getText());
						palletId = ""+temp;
					}
					String customer = fields[CUSTOMER].getText();
					String cookie = fields[COOKIE].getText();
					int blocked = blockedList.getSelectedIndex();
					SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					String dateFrom="";
					String dateTo="";
					if(!fields[DATEFROM].getText().equals("")){
						Date temp = parser.parse(fields[DATEFROM].getText());
						dateFrom = df.format(temp);
					}
					if(!fields[DATETO].getText().equals("")){
						Date temp = parser.parse(fields[DATETO].getText());
						dateTo = df.format(temp);
					}
					
					myTable.filter(palletId,cookie,dateFrom,dateTo,customer,blocked);
					searchPanel.revalidate();
					searchPanel.repaint();
					mainPanel.revalidate();
					mainPanel.repaint();
					scroll.revalidate();
					scroll.repaint();
					//String palletId,String cookieN,String producedFrom, String producedTo,String customer, int blocked

					

				}catch(NumberFormatException ex){
					System.out.println("ERROR");
					messageLabel.setText("Error, quantity must be an int");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					messageLabel.setText("Error, wrong format of date, please use yyyy-MM-dd");
				}
			}
		});
		searchPanel.add(filterButton,c);
		c.gridx=2;
		c.gridy=0;
		searchPanel.add(new JLabel("Pallet id"),c);
		c.gridx=3;
		searchPanel.add(new JLabel("Customer name"),c);
		c.gridy=2;
		c.gridx=2;
		searchPanel.add(new JLabel("Produced from"),c);
		c.gridx=3;
		searchPanel.add(new JLabel("Produced to"),c);
		c.gridy=1;
		c.gridx=3;
		searchPanel.add(fields[CUSTOMER],c);
		c.gridx=2;
		searchPanel.add(fields[PALLETID],c);
		c.gridy=3;
		searchPanel.add(fields[DATEFROM],c);
		c.gridx=3;
		searchPanel.add(fields[DATETO],c);
		c.gridx=4;
		searchPanel.add(fields[COOKIE],c);
		c.gridy=2;
		searchPanel.add(new JLabel("Product name"),c);
		
		JPanel blockPanel = new JPanel();
		blockPanel.setLayout(new GridBagLayout());
		
		c.gridy = 1;
		c.gridx = 1;
		blockPanel.add(productList,c);
		c.gridy = 0;
		c.gridx = 2; 
		blockPanel.add(new JLabel("Produced from"),c);
		c.gridx = 3;
		blockPanel.add(new JLabel("Produced to"),c);
		c.gridy=1;
		blockPanel.add(fields[BLOCKTO],c);
		c.gridx=2;
		blockPanel.add(fields[BLOCKFROM],c);
		JButton blockButton = new JButton("Block");
		blockButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{

					SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					String dateFrom="";
					String dateTo="";
					if(!fields[BLOCKFROM].getText().equals("")){
						Date temp = parser.parse(fields[BLOCKFROM].getText());
						dateFrom = df.format(temp);
					}
					if(!fields[BLOCKTO].getText().equals("")){
						Date temp = parser.parse(fields[BLOCKTO].getText());
						dateTo = df.format(temp);
					}
					
					db.block((String)productList.getSelectedItem(),dateFrom,dateTo);
					myTable.update();
					fields[BLOCKTO].setText("");
					fields[BLOCKFROM].setText("");
					searchPanel.revalidate();
					searchPanel.repaint();
					mainPanel.revalidate();
					mainPanel.repaint();
					scroll.revalidate();
					scroll.repaint();
					//String palletId,String cookieN,String producedFrom, String producedTo,String customer, int blocked

					

				}catch(NumberFormatException ex){
					System.out.println("ERROR");
					messageLabel.setText("Error, quantity must be an int");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					messageLabel.setText("Error, wrong format of date, please use yyyy-MM-dd");
				}
			}
		});
		
		c.gridx=0;
		c.gridy=1;
		blockPanel.add(blockButton,c);
		blockPanel.setPreferredSize(new Dimension(760, 100));
		searchPanel.setPreferredSize(new Dimension(760, 100));
		blockPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		searchPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		c.gridy=0;
		temps.add(searchPanel,c);
		c.gridy=1;
		temps.add(blockPanel,c);
		
		
		
		
		
		return temps;
	}
	
	

	/**
	 * Create the bottom panel, consisting of the login button and the message
	 * line.
	 * 
	 * @return The bottom panel.
	 */
	public JComponent createBottomPanel() {
		JButton[] buttons = new JButton[0];
	/*	buttons[0] = new JButton("Save");
		buttons[0].setActionCommand("0");
		buttons[1] = new JButton("Cancel");
		buttons[1].setActionCommand("1");*/
		ActionHandler actHand = new ActionHandler();
	//	fields[PRODUCT_NAME].addActionListener(actHand);
		return new ButtonAndMessagePanel(buttons, messageLabel, actHand);
	}

	/**
	 * Perform the entry actions of this pane, i.e. clear the message line.
	 */
	public void entryActions() {
		clearMessage();
		myTable.update();
		mainPanel.revalidate();
		mainPanel.repaint();
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
		}
	}

}
