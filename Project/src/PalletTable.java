import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;


public class PalletTable extends AbstractTableModel{
	private String[] columnNames;
	private ArrayList<Object[]> data;
	private Database db;
	public PalletTable(Database db){
		super();
		this.db=db;
		//id,cookieName,location, placedDate,deliveryDate,dateProduced,dateDelivered,destination,orderNbr,isBlocked
		columnNames = new String[]{"Pallet id",
	            "Product Name",
	            "Location",
	            "Placed Date",
	            "Delivery Date",
	            "Date Produced",
	            "Date Delivered",
	            "Destination",
	            "Order Nbr",
	            "Blocked"};
		data = db.getPallets();
/*		data = new Object[][] {
				{new Integer(0), "Butternut Cookie",
					"Freezer", "2017-04-06", "2017-04-08","2017-04-07","Hong Kong", new Boolean(false)},
				{new Integer(1), "Choclate Cookie",
					"Freezer", "2017-04-06", "2017-04-08","2017-04-07","New York", new Boolean(true)},
				{new Integer(2), "Blueberry Cookie",
					"Freezer", "2017-04-06", "2017-04-08","2017-04-07","Kathmandu", new Boolean(true)},
				{new Integer(3), "Muffin",
					"Deliverd", "2017-04-06", "2017-04-08","2017-04-07","Beijing", new Boolean(false)},
				{new Integer(4), "Cheese cake",
					"Deliverd", "2017-04-06", "2017-04-08","2017-04-07","Tokyo", new Boolean(false)},
					};*/
	}
	
	public void update(){
		data=db.getPallets();
	}
	
	public void filter(String palletId,String cookieN,String producedFrom, String producedTo,String customer, int blocked){
		data=db.getfilterdPallets(palletId, cookieN, producedFrom, producedTo, customer, blocked);
	}
	
	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data.get(row)[col];
	}
	
	@Override
	public Class<?> getColumnClass(int col){
		if(col==columnNames.length-1){
			return Boolean.class;
		}
		return super.getColumnClass(col);
	}
	
	
	@Override
	public boolean isCellEditable(int row, int col){
		return col==columnNames.length-1;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col){
		if(col==columnNames.length-1){
			if((boolean)data.get(row)[col]!=(boolean)value){
				db.updatePallet((int)data.get(row)[0], (boolean)value);
				data.get(row)[col] = value;
				fireTableCellUpdated(row,col);
				//update();
			}
		}
	}
	


}
