package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.JToolBar;
import javax.imageio.ImageIO;
import java.util.*;
import product.IceCreamFlavor;
import product.Item;
import product.MixIn;
import product.MixInAmount;
import product.MixInFlavor;
import product.Scoop;
import product.Container;
import product.Serving;
import product.Order;
import person.Customer;
import emporium.Emporium;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainWin extends JFrame
{
	private Emporium emporium = new	Emporium();
	private JLabel display;
	private String NAME = "Mice";
	private File filename = new File("emporium.mice");
	
	public MainWin()
	{	
		
		super("Mavs Ice Cream Emporium");
		Font fontMenu = new Font(Font.SERIF, Font.BOLD, 15);
		Font fontMenuItem = new Font(Font.SERIF, Font.BOLD, 14);
		Color color = new Color(255, 153, 0);
		Color colorB = new Color(240, 130, 0);
		Color white = new Color(255, 255, 255);
		try{
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("logo.jpg")))));
			setBackground(white);
		}
		
		catch(IOException e){}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(550, 200);
		setSize(900, 600);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(color);
		
		JMenu file = new JMenu("File");
		file.setFont(fontMenu);
		file.setPreferredSize(new Dimension(60, 30));
		file.setOpaque(true);
		file.setBackground(color);
		file.setForeground(white);
		
		JMenu view = new JMenu("View");
		view.setFont(fontMenu);
		view.setPreferredSize(new Dimension(60, 30));
		view.setOpaque(true);
		view.setBackground(color);
		view.setForeground(white);
		
		JMenu create = new JMenu("Create");
		create.setFont(fontMenu);
		create.setPreferredSize(new Dimension(80, 30));
		create.setOpaque(true);
		create.setBackground(color);
		create.setForeground(white);
		
		JMenu help = new JMenu("Help");
		help.setFont(fontMenu);
		help.setPreferredSize(new Dimension(60, 30));
		help.setOpaque(true);
		help.setBackground(color);
		help.setForeground(white);
		
        JMenuItem quit = new JMenuItem("Quit");
		quit.setFont(fontMenuItem);
		quit.setPreferredSize(new Dimension(60, 30));
		quit.setOpaque(true);
		quit.setBackground(colorB);
		quit.setForeground(white);
		
        JMenuItem viewIceCreamFlavor = new JMenuItem("Ice Cream Flavor");
		viewIceCreamFlavor.setFont(fontMenuItem);
		viewIceCreamFlavor.setPreferredSize(new Dimension(150, 30));
		viewIceCreamFlavor.setOpaque(true);
		viewIceCreamFlavor.setBackground(colorB);
		viewIceCreamFlavor.setForeground(white);
		
        JMenuItem viewMixInFlavor = new JMenuItem("Mix In Flavor");
		viewMixInFlavor.setFont(fontMenuItem);
		viewMixInFlavor.setPreferredSize(new Dimension(150, 30));
		viewMixInFlavor.setOpaque(true);
		viewMixInFlavor.setBackground(color);
		viewMixInFlavor.setForeground(white);
		
		JMenuItem viewOrder = new JMenuItem("Order");
		viewOrder.setFont(fontMenuItem);
		viewOrder.setPreferredSize(new Dimension(70, 30));
		viewOrder.setOpaque(true);
		viewOrder.setBackground(colorB);
		viewOrder.setForeground(white);
		
		JMenuItem createIceCreamFlavor = new JMenuItem("Ice Cream Flavor");
		createIceCreamFlavor.setFont(fontMenuItem);
		createIceCreamFlavor.setPreferredSize(new Dimension(150, 30));
		createIceCreamFlavor.setOpaque(true);
		createIceCreamFlavor.setBackground(colorB);
		createIceCreamFlavor.setForeground(white);
		
        JMenuItem createMixInFlavor = new JMenuItem("Mix In Flavor");
		createMixInFlavor.setFont(fontMenuItem);
		createMixInFlavor.setPreferredSize(new Dimension(150, 30));
		createMixInFlavor.setOpaque(true);
		createMixInFlavor.setBackground(color);
		createMixInFlavor.setForeground(white);
		
		JMenuItem createOrder = new JMenuItem("Order");
		createOrder.setFont(fontMenuItem);
		createOrder.setPreferredSize(new Dimension(70, 30));
		createOrder.setOpaque(true);
		createOrder.setBackground(colorB);
		createOrder.setForeground(white);
		
		JMenuItem createContainer = new JMenuItem("Container");
		createContainer.setFont(fontMenuItem);
		createContainer.setPreferredSize(new Dimension(70, 30));
		createContainer.setOpaque(true);
		createContainer.setBackground(colorB);
		createContainer.setForeground(white);
		
		JMenuItem createCustomer = new JMenuItem("Customer");
		createCustomer.setFont(fontMenuItem);
		createCustomer.setPreferredSize(new Dimension(70, 30));
		createCustomer.setOpaque(true);
		createCustomer.setBackground(colorB);
		createCustomer.setForeground(white);
		
		JMenuItem about = new JMenuItem("About");
		about.setFont(fontMenuItem);
		about.setPreferredSize(new Dimension(70, 30));
		about.setOpaque(true);
		about.setBackground(colorB);
		about.setForeground(white);
		
		quit.addActionListener(event -> onQuitClick());
		createIceCreamFlavor.addActionListener(event -> onCreateIceCreamFlavorClick());
		createMixInFlavor.addActionListener(event -> onCreateMixInFlavorClick());
		createOrder.addActionListener(event -> onCreateOrderClick());
		createContainer.addActionListener(event -> onCreateContainerClick());
		createCustomer.addActionListener(event -> onCreateCustomerClick());
		viewIceCreamFlavor.addActionListener(event -> view(Screen.ICE_CREAM_FLAVORS));
		viewMixInFlavor.addActionListener(event -> view(Screen.MIX_IN_FLAVORS));
		viewOrder.addActionListener(event -> view(Screen.ORDERS));
		about.addActionListener(event -> onAboutClick());
		
        file.add(quit);
		view.add(viewIceCreamFlavor);
		view.add(viewMixInFlavor);
		view.add(viewOrder);
		create.add(createIceCreamFlavor);
		create.add(createMixInFlavor);
		create.add(createOrder);
		create.add(createContainer);
		create.add(createCustomer);
		help.add(about);
		menuBar.add(file);
		menuBar.add(view);
		menuBar.add(create);
		menuBar.add(help);
		
        setJMenuBar(menuBar);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setSize(2000, 100);
		
		JButton saveIceCream = new JButton(new ImageIcon("saveIceCream.jpg"));
		saveIceCream.setActionCommand("Save");
		saveIceCream.setToolTipText("Save now");
		saveIceCream.setBorder(null);
		saveIceCream.setBackground(white);
		toolBar.setBorder(BorderFactory.createLineBorder(Color.orange));
		
		JButton saveAsIceCream = new JButton(new ImageIcon("saveAsIceCream.jpg"));
		saveAsIceCream.setActionCommand("Save As");
		saveAsIceCream.setToolTipText("Save As");
		saveAsIceCream.setBorder(null);
		
		JButton openAs = new JButton(new ImageIcon("openAs.jpg"));
		openAs.setActionCommand("Open");
		openAs.setToolTipText("Open");
		openAs.setBorder(null);
		
		JButton toolbarCreateIceCream = new JButton(new ImageIcon("toolbarCreateIceCream.jpg"));
		toolbarCreateIceCream.setActionCommand("Create Ice Cream Flavor");
		toolbarCreateIceCream.setToolTipText("Create Ice Cream Flavor");
		toolbarCreateIceCream.setBorder(null);
		
		JButton toolbarCreateMixIn = new JButton(new ImageIcon("toolbarCreateMixIn.jpg"));
		toolbarCreateMixIn.setActionCommand("Create Mix In Flavor");
		toolbarCreateMixIn.setToolTipText("Create Mix In Flavor");
		toolbarCreateMixIn.setBorder(null);
		
		JButton toolbarCreateOrder = new JButton(new ImageIcon("toolbarCreateScoop.jpg"));
		toolbarCreateOrder.setActionCommand("Create Order");
		toolbarCreateOrder.setToolTipText("Create Order");
		toolbarCreateOrder.setBorder(null);
		
		JButton toolbarCreateContainer = new JButton(new ImageIcon("toolbarCreateScoop.jpg"));
		toolbarCreateContainer.setActionCommand("Create Container");
		toolbarCreateContainer.setToolTipText("Create Container");
		toolbarCreateContainer.setBorder(null);
		
		JButton toolbarCreateCustomer= new JButton(new ImageIcon("customer.jpg"));
		toolbarCreateCustomer.setActionCommand("Create Customer");
		toolbarCreateCustomer.setToolTipText("Create Customer");
		toolbarCreateCustomer.setBorder(null);
		
		JButton toolbarViewIceCream = new JButton(new ImageIcon("toolbarViewIceCream.jpg"));
		toolbarViewIceCream.setActionCommand("View Ice Cream Flavor");
		toolbarViewIceCream.setToolTipText("View Ice Cream Flavor");
		toolbarViewIceCream.setBorder(null);
		
		JButton toolbarViewMixIn = new JButton(new ImageIcon("toolbarViewMixIn.jpg"));
		toolbarViewMixIn.setActionCommand("View Mix In Flavor");
		toolbarViewMixIn.setToolTipText("View Mix In Flavor");
		toolbarViewMixIn.setBorder(null);
		
		JButton toolbarViewOrder = new JButton(new ImageIcon("toolbarViewScoop.jpg"));
		toolbarViewOrder.setActionCommand("View Order");
		toolbarViewOrder.setToolTipText("View Order");
		toolbarViewOrder.setBorder(null);
		
		toolBar.add(saveIceCream);
		saveIceCream.addActionListener(event->onSaveClick());
		
		toolBar.add(saveAsIceCream);
		saveAsIceCream.addActionListener(event->onSaveAsClick());
		
		toolBar.add(openAs);
		openAs.addActionListener(event->onOpenClick());
		
		toolBar.add(Box.createHorizontalStrut(25));
		
		
		toolBar.add(toolbarCreateIceCream);
		toolbarCreateIceCream.addActionListener(event->onCreateIceCreamFlavorClick());
		
		toolBar.add(toolbarCreateMixIn);
		toolbarCreateMixIn.addActionListener(event->onCreateMixInFlavorClick());

		toolBar.add(toolbarCreateOrder);
		toolbarCreateOrder.addActionListener(event->onCreateOrderClick());
		
		toolBar.add(toolbarCreateContainer);
		toolbarCreateContainer.addActionListener(event->onCreateContainerClick());
		
		toolBar.add(toolbarCreateCustomer);
		toolbarCreateCustomer.addActionListener(event->onCreateCustomerClick());

		toolBar.add(Box.createHorizontalStrut(25));
		
		toolBar.add(toolbarViewIceCream);
		toolbarViewIceCream.addActionListener(event->view(Screen.ICE_CREAM_FLAVORS));

		toolBar.add(toolbarViewMixIn);
		toolbarViewMixIn.addActionListener(event->view(Screen.MIX_IN_FLAVORS));
		
		toolBar.add(toolbarViewOrder);
		toolbarViewOrder.addActionListener(event->view(Screen.ORDERS));

		
		toolBar.setBackground(white);
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		
		setVisible(true);
	}
	
	public void onOpenClick()
	{
		final JFileChooser fc = new JFileChooser(filename); 
        FileFilter miceFiles = new FileNameExtensionFilter("Mice Files", "mice");
        fc.addChoosableFileFilter(miceFiles);         
        fc.setFileFilter(miceFiles);                 
        
        int result = fc.showOpenDialog(this);        
        if (result == JFileChooser.APPROVE_OPTION) 
		{
            filename = fc.getSelectedFile();       
            
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
			{
                emporium = new Emporium(br);                  
                view(Screen.ICE_CREAM_FLAVORS);
            } 
			catch (Exception e)
			{
                JOptionPane.showMessageDialog(this,"Unable to open " + filename + '\n' + e, 
                    "Failed", JOptionPane.ERROR_MESSAGE); 
             }
        }
	}
	
	public void onSaveClick()
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
		{
            emporium.save(bw);
        } 
		catch (Exception e) 
		{
            JOptionPane.showMessageDialog(this, "Unable to open " + filename + '\n' + e,
                "Failed", JOptionPane.ERROR_MESSAGE); 
        }
	}
	
	public void onSaveAsClick() 
	{    
        final JFileChooser fc = new JFileChooser(filename); 
        FileFilter miceFiles = new FileNameExtensionFilter("Mice Files", "mice");
        fc.addChoosableFileFilter(miceFiles);         
        fc.setFileFilter(miceFiles);                 
        
        int result = fc.showSaveDialog(this);   
        if (result == JFileChooser.APPROVE_OPTION) 
		{ 
            filename = fc.getSelectedFile();        
            if(!filename.getAbsolutePath().endsWith(".mice"))  
                filename = new File(filename.getAbsolutePath() + ".mice");
            onSaveClick();                    
        }
    }
	
	public void onQuitClick()
	{
		System.exit(0);
	}
	
	public void onCreateIceCreamFlavorClick()
	{
		Font fontMenu = new Font(Font.SERIF, Font.PLAIN, 15);
		Font fontMenuItem = new Font(Font.SERIF, Font.PLAIN, 14);
		
		JTextField name = new JTextField();
		name.setPreferredSize(new Dimension(200, 30));
		name.setFont(fontMenuItem);
		
		JTextField description = new JTextField();
		description.setPreferredSize(new Dimension(200, 30));
		description.setFont(fontMenuItem);
		
		JTextField cost = new JTextField();
		cost.setPreferredSize(new Dimension(200, 30));
		cost.setFont(fontMenuItem);
		
		JTextField price = new JTextField();
		price.setPreferredSize(new Dimension(200, 30));
		price.setFont(fontMenuItem);
		
		
		Object[] fields = {
			"Name", name,
			"Description", description,
			"Cost", cost,
			"Price", price
		};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int response = JOptionPane.CANCEL_OPTION;
		try
		{
			ImageIcon icon = new ImageIcon("iceCreamFlavors.jpg");
			response = JOptionPane.showConfirmDialog(this, fields, "Create Your Ice Cream Flavor", JOptionPane.OK_CANCEL_OPTION, 1, icon);
		}
		catch (Exception e){}
		if(response == JOptionPane.OK_OPTION)
		{
			try{
				IceCreamFlavor flavor = new IceCreamFlavor(name.getText(), description.getText(), Integer.parseInt(cost.getText()), Integer.parseInt(price.getText()));
				emporium.addIceCreamFlavor(flavor);
			}
			catch(Exception e){
				JLabel labelViewYes = new JLabel("OOPS! Please enter all fields with proper format!");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				JOptionPane.showMessageDialog(this, labelViewYes);
				onCreateIceCreamFlavorClick();
			}
		}
	}
	
	public void onCreateMixInFlavorClick()
	{
		Font fontMenu = new Font(Font.SERIF, Font.PLAIN, 15);
		Font fontMenuItem = new Font(Font.SERIF, Font.PLAIN, 14);
		
		JTextField name = new JTextField();
		name.setPreferredSize(new Dimension(200, 30));
		name.setFont(fontMenuItem);
		
		JTextField description = new JTextField();
		description.setPreferredSize(new Dimension(200, 30));
		description.setFont(fontMenuItem);
		
		JTextField cost = new JTextField();
		cost.setPreferredSize(new Dimension(200, 30));
		cost.setFont(fontMenuItem);
		
		JTextField price = new JTextField();
		price.setPreferredSize(new Dimension(200, 30));
		price.setFont(fontMenuItem);
		
		Object[] fields = {
			"Name", name,
			"Description", description,
			"Cost", cost,
			"Price", price
		};
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int response = JOptionPane.CANCEL_OPTION;
		try
		{
			ImageIcon icon = new ImageIcon("mixInFlavor.jpg");
			response = JOptionPane.showConfirmDialog(this, fields, "Create Your Mix-In Flavor", JOptionPane.OK_CANCEL_OPTION, 1, icon);

		}
		catch(Exception e){}
		if(response == JOptionPane.OK_OPTION)
		{
			try
			{
				MixInFlavor flavor = new MixInFlavor(name.getText(), description.getText(), Integer.parseInt(cost.getText()), Integer.parseInt(price.getText()));
				emporium.addMixInFlavor(flavor);
			}
			catch(Exception e){
				JLabel labelViewYes = new JLabel("OOPS! Please enter all fields with proper format!");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				JOptionPane.showMessageDialog(this, labelViewYes);
				onCreateMixInFlavorClick();
			}
		}
		
	}
	
	public void onCreateContainerClick()
	{
		Font fontMenu = new Font(Font.SERIF, Font.PLAIN, 15);
		Font fontMenuItem = new Font(Font.SERIF, Font.PLAIN, 14);
		
		JTextField name = new JTextField();
		name.setPreferredSize(new Dimension(200, 30));
		name.setFont(fontMenuItem);
		
		JTextField description = new JTextField();
		description.setPreferredSize(new Dimension(200, 30));
		description.setFont(fontMenuItem);
		
		JTextField maxScoops = new JTextField();
		maxScoops.setPreferredSize(new Dimension(200, 30));
		maxScoops.setFont(fontMenuItem);
		
		Object[] fields = {
			"Name", name,
			"Description", description,
			"Max Scoops", maxScoops
		};
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int response = JOptionPane.CANCEL_OPTION;
		try
		{
			ImageIcon icon = new ImageIcon("mixInFlavor.jpg");
			response = JOptionPane.showConfirmDialog(this, fields, "Create Your Container", JOptionPane.OK_CANCEL_OPTION, 1, icon);

		}
		catch(Exception e){}
		if(response == JOptionPane.OK_OPTION)
		{
			try
			{
				Container container = new Container(name.getText(), description.getText(), Integer.parseInt(maxScoops.getText()));
				emporium.addContainer(container);
			}
			catch(Exception e){
				JLabel labelViewYes = new JLabel("OOPS! Please enter all fields with proper format!");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				JOptionPane.showMessageDialog(this, labelViewYes);
				onCreateContainerClick();
			}
		}
	}
	
	public void onCreateCustomerClick()
	{
		Font fontMenu = new Font(Font.SERIF, Font.PLAIN, 15);
		Font fontMenuItem = new Font(Font.SERIF, Font.PLAIN, 14);
		
		JTextField name = new JTextField();
		name.setPreferredSize(new Dimension(200, 30));
		name.setFont(fontMenuItem);
		
		JTextField phone = new JTextField();
		phone.setPreferredSize(new Dimension(200, 30));
		phone.setFont(fontMenuItem);
		
		Object[] fields = {
			"Name", name,
			"Phone (xxx-xxx-xxxx)", phone
		};
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int response = JOptionPane.CANCEL_OPTION;
		try
		{
			ImageIcon icon = new ImageIcon("customerAB.jpg");
			response = JOptionPane.showConfirmDialog(this, fields, "New Customer", JOptionPane.OK_CANCEL_OPTION, 1, icon);

		}
		catch(Exception e){}
		if(response == JOptionPane.OK_OPTION)
		{
			try
			{
				Customer customer = new Customer(name.getText(), phone.getText());
				emporium.addCustomer(customer);
			}
			catch(Exception e){
				JLabel labelViewYes = new JLabel("OOPS! Please enter all fields with proper format!");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				JOptionPane.showMessageDialog(this, labelViewYes);
				onCreateCustomerClick();
			}
		}
	}
	
	public Scoop onCreateScoopClick()
	{
		if(emporium.iceCreamFlavors().length == 0 || emporium.mixInFlavors().length == 0)
		{
			try
			{
				JLabel labelViewNo = new JLabel("OOPS! Please first create both ice cream flavor and mix in flavor to create a scoop!");
				labelViewNo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				ImageIcon icon = new ImageIcon("viewScoop.png");
				JOptionPane.showMessageDialog(this, labelViewNo, "Scoop", 1, icon);

			}
			catch(Exception e){}
			return null;
		}
		else
		{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Object response = null;
			try
			{
				JLabel labelViewYes = new JLabel("Please select your ice cream flavor.");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				ImageIcon icon = new ImageIcon("scoops.jpg");
				response = JOptionPane.showInputDialog(this, labelViewYes, "Scoop", JOptionPane.OK_CANCEL_OPTION, icon, emporium.iceCreamFlavors(), null);
			}
			catch(Exception e){}
			if(response != null)
			{
				Scoop scoop = new Scoop((IceCreamFlavor) response);
				Object responseForMixIn = null;
				Object responseForMixInAmount = null;
				do
				{
					responseForMixIn = null;
					try
					{
						JLabel labelViewYes = new JLabel("Please select your mix in flavor.");
						labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
						ImageIcon icon = new ImageIcon("mixIn.png");
						responseForMixIn = JOptionPane.showInputDialog(this, labelViewYes, "Scoop", JOptionPane.OK_CANCEL_OPTION, icon, emporium.mixInFlavors(), null);
					}
					catch(Exception e){}
					if(responseForMixIn != null)
					{
						try
						{
							JLabel labelViewYes = new JLabel("Please select your mixin amount.");
							labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
							ImageIcon icon = new ImageIcon("mixInAmount.png");
							responseForMixInAmount = JOptionPane.showInputDialog(this, labelViewYes, "Scoop", JOptionPane.OK_CANCEL_OPTION, icon, MixInAmount.values(), null);
						}
						catch(Exception e){}
						if(responseForMixInAmount != null)
						{
							scoop.addMixIn(new MixIn((MixInFlavor) responseForMixIn, (MixInAmount) responseForMixInAmount));
						}
					}
					
				}while(responseForMixIn != null);
				//emporium.addScoop(scoop);
				return scoop;
			}
			return null;
		}
	}
	
	public Serving onCreateServingClick(Customer customer)
	{
		if(emporium.containers().length == 0)
		{
			/*try
			{
				JLabel labelViewNo = new JLabel("OOPS! Please first create container to create a serving!");
				labelViewNo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				ImageIcon icon = new ImageIcon("viewScoop.png");
				JOptionPane.showMessageDialog(this, labelViewNo, "Serving", 1, icon);

			}
			catch(Exception e){}*/
			return null;
		}
		else
		{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Object[] obj = emporium.favoriteServings(customer);
			Object response = null;
			if(obj.length != 0)
			{
				try
				{
					JLabel labelViewYes = new JLabel("This is from your past servings.");
					labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
					ImageIcon icon = new ImageIcon("customerAB.jpg");
					response = JOptionPane.showInputDialog(this, labelViewYes, "Favorites", JOptionPane.OK_CANCEL_OPTION, icon, obj, null);
				}
				catch(Exception e){}
				if(response != null)
					return (Serving)response;
			}
			
			
			response = null;
			try
			{
				JLabel labelViewYes = new JLabel("Please select your container.");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				ImageIcon icon = new ImageIcon("scoops.jpg");
				response = JOptionPane.showInputDialog(this, labelViewYes, "Container", JOptionPane.OK_CANCEL_OPTION, icon, emporium.containers(), null);
			}
			catch(Exception e){}
			if(response != null)
			{
				Serving serv = new Serving((Container) response);
				Object createScoop = null;
				do
				{
					createScoop = null;
					createScoop = onCreateScoopClick();
					if(createScoop != null)
						serv.addScoop((Scoop)createScoop);
				}while(createScoop != null && serv.numScoops() <= (((Container)response).maxScoops()));

				if(serv.scoops().length == 0)
				{
					try
					{
						JLabel labelViewNo = new JLabel("OOPS! Please first create at least one scoop to create a serving!");
						labelViewNo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
						ImageIcon icon = new ImageIcon("viewScoop.png");
						JOptionPane.showMessageDialog(this, labelViewNo, "Serving", 1, icon);
					}
					catch(Exception e){}
					return null;
				}
				else
				{
					Object responseForMixIn = null;
					Object responseForMixInAmount = null;
					do
					{
						responseForMixIn = null;
						try
						{
							JLabel labelViewYes = new JLabel("Please select your toppings flavor.");
							labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
							ImageIcon icon = new ImageIcon("mixIn.png");
							responseForMixIn = JOptionPane.showInputDialog(this, labelViewYes, "Toppings", JOptionPane.OK_CANCEL_OPTION, icon, emporium.mixInFlavors(), null);
						}
						catch(Exception e){}
						if(responseForMixIn != null)
						{
							try
							{
								JLabel labelViewYes = new JLabel("Please select your topping flavor amount amount.");
								labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
								ImageIcon icon = new ImageIcon("mixInAmount.png");
								responseForMixInAmount = JOptionPane.showInputDialog(this, labelViewYes, "Toppings", JOptionPane.OK_CANCEL_OPTION, icon, MixInAmount.values(), null);
							}
							catch(Exception e){}
							if(responseForMixInAmount != null)
							{
								serv.addTopping(new MixIn((MixInFlavor) responseForMixIn, (MixInAmount) responseForMixInAmount));
							}
						}
						
					}while(responseForMixIn != null);
					return serv;					
				}
			}
		}
		return null;
	}
	
	public void onCreateOrderClick()
	{
		//Serving serving = onCreateServingClick();
		Object response = null;
		Serving serving = null;
		//if(serving != null)
		//{
			try
			{
				JLabel labelViewYes = new JLabel("Please select your customer.");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				ImageIcon icon = new ImageIcon("customerAB.jpg");
				response = JOptionPane.showInputDialog(this, labelViewYes, "Customer", JOptionPane.OK_CANCEL_OPTION, icon, emporium.customers(), null);
				serving = onCreateServingClick((Customer) response);
			}
			catch(Exception e){}
		//}
		
		if(serving != null && response != null)
		{
			Order order = new Order((Customer) response);
			order.addServing(serving);
			emporium.addOrder(order);
			view(Screen.ORDERS);
		}
		else if(serving == null)
		{
			try
			{
				JLabel labelViewYes = new JLabel("Please create serving and customer before you make an order.");
				labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				ImageIcon icon = new ImageIcon("mixIn.png");
				JOptionPane.showMessageDialog(this, labelViewYes, "Order", JOptionPane.OK_CANCEL_OPTION, icon);
			}
			catch(Exception e){}
		}
	}
	
	public void onAboutClick()
	{
        JFrame about = new JFrame("About");
        about.setLocation(550, 200);
		
		Canvas logo = new Canvas();
		about.add(logo);
        
        about.setSize(800,600);
        about.setVisible(true);
		
		JButton ok =new JButton("OK");  
		ok.setBounds(50,100,95,30);  
        about.add(ok);  
		ok.setLocation(350, 500);
		ok.addActionListener(event -> about.setVisible(false));
        about.setLayout(null);  
        about.setVisible(true); 
     }
	
	private void view(Screen screen)
	{
		if(screen == Screen.ICE_CREAM_FLAVORS)
		{
			JLabel labelViewNo = new JLabel("Please create an ice cream flavor first!");
			labelViewNo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			JLabel labelViewYes = new JLabel(Arrays.toString(emporium.iceCreamFlavors()));
			labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			
			if(emporium.iceCreamFlavors().length == 0)
			{
				try
				{
					ImageIcon icon = new ImageIcon("viewIceCream.jpg");
					JOptionPane.showMessageDialog(this, labelViewNo,"No ice cream flavors to show!", 1, icon);
				}
				catch(Exception e){}
			}
			else
			{		
				try
				{
					ImageIcon icon = new ImageIcon("viewIceCreamYes.jpg");
					JOptionPane.showMessageDialog(this, labelViewYes, "Available Ice Cream Flavors", 1, icon);

				}
				catch(Exception e){}
			}
		}
		
		if(screen == Screen.MIX_IN_FLAVORS)
		{
			JLabel labelViewNo = new JLabel("Please create a mix in flavor first!");
			labelViewNo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			JLabel labelViewYes = new JLabel(Arrays.toString(emporium.mixInFlavors()));
			labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));	
			
			if(emporium.mixInFlavors().length == 0)
			{
				try
				{
					ImageIcon icon = new ImageIcon("viewMixIn.jpg");
					JOptionPane.showMessageDialog(this, labelViewNo, "Available Mix In Flavors", 1, icon);
				}
				catch(Exception e){}
			}
			else
			{
				try
				{
					ImageIcon icon = new ImageIcon("mixIn.png");
				    JOptionPane.showMessageDialog(this, labelViewYes, "Available Mix In Flavors", 1, icon);

				}
				catch(Exception e){}
			}
		}
		
		if(screen == Screen.CONTAINERS)
		{
			JLabel labelViewNo = new JLabel("Please create a container first!");
			labelViewNo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			JLabel labelViewYes = new JLabel(Arrays.toString(emporium.containers()));
			labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			if(emporium.containers().length == 0)
			{
				try
				{
					ImageIcon icon = new ImageIcon("viewScoop.png");
					JOptionPane.showMessageDialog(this, labelViewNo, "Available Containers", 1, icon);
				}
				catch(Exception e){}
			}
			else
			{
				try
				{
					ImageIcon icon = new ImageIcon("viewScoopYes.jpg");
					JOptionPane.showMessageDialog(this, labelViewYes, "Available Containers", 1, icon);
				}
				catch(Exception e){}
				
			}
		}
		
		if(screen == Screen.ORDERS)
		{
			JLabel labelViewNo = new JLabel("Please create an order first!");
			labelViewNo.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			JLabel labelViewYes = new JLabel(Arrays.toString(emporium.orders()));
			labelViewYes.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			if(emporium.orders().length == 0)
			{
				try
				{
					ImageIcon icon = new ImageIcon("viewScoop.png");
					JOptionPane.showMessageDialog(this, labelViewNo, "Available Orders", 1, icon);
				}
				catch(Exception e){}
			}
			else
			{
				try
				{
					ImageIcon icon = new ImageIcon("viewScoopYes.jpg");
					JOptionPane.showMessageDialog(this, labelViewYes, "Available Orders", 1, icon);
				}
				catch(Exception e){}
				
			}
		}
	}
}