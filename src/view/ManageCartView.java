package view;

import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import connection.Connect;
import controller.CartItemController;
import controller.TransactionController;
import helper.Session;
import model.CartItem;
import model.Transaction;

public class ManageCartView extends JFrame {
	private JPanel mainPanel, headerPanel, tablePanel, footerPanel;
	private JTable table;
	private JScrollPane scroll;
	private JButton logout, checkout, back;
	private JLabel cartTotalPrice;
	private JTextField quantity, money;
	private CartItemController cartController;
	private TransactionController transactionController;
	
	String payment;
	
	private static ManageCartView view = null;
	
	private ManageCartView() {}
	
	public static ManageCartView getInstance() {
		if(view == null) view = new ManageCartView();
		return view;
	}

	public void showManageCartForm(Vector<CartItem> cart) {
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Manage Cart Form");
		
		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1,1));
		headerPanel = new JPanel(new GridLayout(1,1));
		footerPanel = new JPanel(new GridLayout(1,2));
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ManageCartView.this.dispose();
				cartController.viewAddToCartForm();
			}
		});
		
		logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Session.getSession().logout(ManageCartView.this);
			}
		});
		checkout = new JButton("Checkout");
		money = new JTextField();
		checkout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Cash", "Credit Card"};
				Object[] moneyInput = {"Your money: ", money};
				Object answer = JOptionPane.showOptionDialog(null, moneyInput, "Payment", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				if(Integer.parseInt(answer.toString()) == 0) payment = "cash";
				else if(Integer.parseInt(answer.toString()) == 1) payment = "credit card";
				
				if(Integer.parseInt(answer.toString()) != -1) {
					int option = JOptionPane.showConfirmDialog(null, "Confirm " + payment + " payment", "Checkout", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION) {
						if(Integer.parseInt(money.getText().toString()) - cartController.cartTotalPrice() < 0) {
							JOptionPane.showMessageDialog(null, "Your money is not enough");
						}
						else {
							transactionController = TransactionController.getInstance();
							transactionController.insertTransaction(payment, cartController.getListCartItem());
							int change = Integer.parseInt(money.getText().toString()) - cartController.cartTotalPrice();
							cartController.clearCartItemList();
							JOptionPane.showMessageDialog(null, "Checkout success with change Rp. " + change);
			        		cartTotalPrice.setText("Total price: Rp. " + cartController.cartTotalPrice());
						}
					}
					money.setText("");
					prepareTableModel(cartController.getListCartItem());
				}
			}
		});
		quantity = new JTextField();
		
		cartController = CartItemController.getInstance();
		cartTotalPrice = new JLabel("Total price: Rp. " + cartController.cartTotalPrice());
		cartTotalPrice.setHorizontalAlignment(JLabel.CENTER);
		
		table = new JTable();
		prepareTableModel(cart);
		
		scroll = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scroll);
		
		footerPanel.add(cartTotalPrice);
		footerPanel.add(checkout);
		
		headerPanel.add(logout);
		
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(footerPanel, BorderLayout.SOUTH);
		add(mainPanel);
		
		setVisible(true);
	}
	
	public void prepareTableModel(Vector<CartItem> cart) {
		MyTableModel model = new MyTableModel();
		CartItemController cartItemController = CartItemController.getInstance();
		for (CartItem c : cart) {
			model.add(new Object[] {
					c.getProductId(),
					cartItemController.getProductById(c.getProductId()).getProductName(),
					cartItemController.getProductById(c.getProductId()).getProductPrice(),
					c.getQuantity(),
			});
		}
		table.setModel(model);
		AddRemoveRenderer renderer = new AddRemoveRenderer();
		table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		table.getColumnModel().getColumn(4).setCellEditor(new AddRemovetEditor());
		table.setRowHeight(renderer.getTableCellRendererComponent(table, null, true, true, 0, 0).getPreferredSize().height * 2);
	}
	
	public class MyTableModel extends AbstractTableModel {

        private List<Object[]> data;

        public MyTableModel() {
            data = new Vector<>();
        }

        @Override
        public String getColumnName(int column) {
            String value = null;
            switch (column) {
                case 0:
                    value = "Id";
                    break;
                case 1:
                    value = "Name";
                    break;
                case 2:
                    value = "Price";
                    break;
                case 3:
                    value = "Quantity";
                    break;
                case 4:
                	value = "Action";
                	break;
            }
            return value;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class value = Object.class;
            switch (columnIndex) {
                case 0:
                    value = Integer.class;
                    break;
                case 1:
                    value = String.class;
                    break;
                case 2:
                    value = Integer.class;
                    break;
                case 3:
                    value = Integer.class;
                    break;
            }
            return value;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Object[] obj = data.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    value = obj[0];
                    break;
                case 1:
                    value = obj[1];
                    break;
                case 2:
                    value = obj[2];
                    break;
                case 3:
                	value = obj[3];
                    break;
            }
            return value;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 4) {
                Object[] value = data.get(rowIndex);
				CartItemController cartController = CartItemController.getInstance();
                if ("add".equals(aValue)) {
    				Object[] quantityInput = {"Quantity: ", quantity};
    				int option = JOptionPane.showConfirmDialog(null, quantityInput, "Buy " + table.getValueAt(rowIndex, 1), JOptionPane.OK_CANCEL_OPTION);
    				if(option == JOptionPane.OK_OPTION) {
    					int qtyInputted = Integer.parseInt(quantity.getText());
    					CartItem itemAdded = cartController.addToCart(Integer.parseInt(table.getValueAt(rowIndex, 0).toString()), qtyInputted);
    					if(itemAdded == null) 
    						JOptionPane.showMessageDialog(null, "Product stock is not enough");
    					else JOptionPane.showMessageDialog(null, qtyInputted + " " + table.getValueAt(rowIndex, 1) + " added to cart");
    					quantity.setText("");
    				}
                } else {
                	int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete " + table.getValueAt(rowIndex, 1), JOptionPane.YES_NO_OPTION);
                	if(option == JOptionPane.YES_OPTION) {
                		boolean removed = cartController.deleteItem(Integer.parseInt(table.getValueAt(rowIndex, 0).toString()));
                		if(removed) JOptionPane.showMessageDialog(null, "Delete successful");
                		else JOptionPane.showMessageDialog(null, "Delete failed");
                	}
                }
        		cartTotalPrice.setText("Total price: Rp. " + cartController.cartTotalPrice());
				prepareTableModel(cartController.getListCartItem());
                fireTableCellUpdated(rowIndex, columnIndex);
                remove(value);

            }
        }

        public void add(Object[] value) {
            int startIndex = getRowCount();
            data.add(value);
            fireTableRowsInserted(startIndex, getRowCount() - 1);
        }

        public void remove(Object[] value) {
            int startIndex = data.indexOf(value);
            data.remove(value);
            fireTableRowsInserted(startIndex, startIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 4;
        }
    }
	
	public class AddRemovePane extends JPanel {
		private JButton add;
        private JButton remove;
        private String state;

        public AddRemovePane() {
            setLayout(new GridBagLayout());
            add = new JButton("Add");
            add.setActionCommand("add");
            remove = new JButton("Remove");
            remove.setActionCommand("remove");

            add(add);
            add(remove);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                }
            };

            add.addActionListener(listener);
            remove.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            add.addActionListener(listener);
            remove.addActionListener(listener);
        }

        public String getState() {
            return state;
        }
	}
	
	public class AddRemoveRenderer extends DefaultTableCellRenderer {

        private AddRemovePane addRemovePane;

        public AddRemoveRenderer() {
        	addRemovePane = new AddRemovePane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
            	addRemovePane.setBackground(table.getSelectionBackground());
            } else {
            	addRemovePane.setBackground(table.getBackground());
            }
            return addRemovePane;
        }
    }

	public class AddRemovetEditor extends AbstractCellEditor implements TableCellEditor {

        private AddRemovePane addRemovePane;

        public AddRemovetEditor() {
            addRemovePane = new AddRemovePane();
            addRemovePane.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            stopCellEditing();
                        }
                    });
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            return addRemovePane.getState();
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                addRemovePane.setBackground(table.getSelectionBackground());
            } else {
                addRemovePane.setBackground(table.getBackground());
            }
            return addRemovePane;
        }
    }
}
