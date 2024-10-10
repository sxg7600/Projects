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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.Border;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import gui.Department;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class POS extends JFrame
{
	private JLabel display;
	private JPanel boxPanel;
	private int secondSetDescriptionY;
	private String NAME = "POS";
	private double subTotal = 0;
	private double tax = 0;
	private double total = 0;
	private double cash = 0;
	private double amountDue = 0;
	private JButton[] departmentButtons = new JButton[40];
	private Department[] departments = new Department[40];
	private int labelY = cmToPixels(5);
	private JLabel labelSubTotal;
	private JLabel labelTax;
	private JLabel labelTotal;
	private JLabel labelCash;
	private JLabel labelAmountDue;
	private Map<JButton, Department> buttonDepartmentMap = new HashMap<>();
	private JLabel label;
	private JLabel labelQty;
	private JLabel labelAmount;
	private ArrayList<JLabel> transactionLabels = new ArrayList<>();
	private JPanel departmentButtonsPanel;
	private JPanel departmentBoxPanel;
	private JLabel clickedButtonsLabel;
	private double prevSumCash = 0;

	public POS()
	{
		super("Point Of Sale System");

        Color mainTheme = new Color(221, 241, 244);
        Color boxColor = new Color(mainTheme.getRed() - 5, mainTheme.getGreen() - 5, mainTheme.getBlue() - 5);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(550, 200);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        setResizable(false); 
        getContentPane().setBackground(mainTheme);
        setLayout(null); 

        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int boxWidth = cmToPixels(10); 
        int boxHeight = windowHeight - cmToPixels(2) - cmToPixels(7); 

        int boxX = cmToPixels(1); 
        int boxY = cmToPixels(2); 

		
		
		boxPanel = new JPanel() 
		{
			@Override
			protected void paintComponent(Graphics g) 
			{
				super.paintComponent(g);
				
				int middleY = getHeight() / 10;
				int lineSpacing = cmToPixels(0.1);
				int descriptionY = middleY + lineSpacing;
				
				int secondSetY = middleY + 2 * lineSpacing + cmToPixels(1);
				secondSetDescriptionY = secondSetY + lineSpacing;
		
				g.setColor(new Color(54, 91, 92));
				g.drawLine(0, middleY, getWidth(), middleY);
				
				g.setColor(new Color(117, 176, 178));
				g.fillRect(0, descriptionY, getWidth(), secondSetY - descriptionY);
		
				g.setColor(new Color(54, 91, 92));
				g.drawLine(0, descriptionY, getWidth(), descriptionY);
				
				g.setColor(new Color(54, 91, 92));
				g.drawLine(0, secondSetY, getWidth(), secondSetY);
				g.drawLine(0, secondSetDescriptionY, getWidth(), secondSetDescriptionY);
				
	
				String descriptionText = "Description                Qty         Amount";
				int textX = 10;
				int textY = secondSetDescriptionY - 20; 
				g.setColor(Color.WHITE); 
				g.setFont(new Font("Arial", Font.BOLD, 16));
				g.drawString(descriptionText, textX, textY);
			}
		};

        boxPanel.setBackground(new Color(172,198,199));
        boxPanel.setBounds(boxX, boxY, boxWidth, boxHeight);

        Border border = BorderFactory.createLineBorder(new Color(54, 91, 92), 1);
        boxPanel.setBorder(border);
		
		clickedButtonsLabel = new JLabel();
		clickedButtonsLabel.setFont(new Font("Helvetica", Font.BOLD, 40)); 
		clickedButtonsLabel.setForeground(new Color(255,255,240));
		clickedButtonsLabel.setText("READY");
		boxPanel.add(clickedButtonsLabel);

        add(boxPanel);

        JPanel secondBoxPanel = new JPanel();
        secondBoxPanel.setBackground(new Color(117,176,178));

        int secondBoxX = boxX;
        int secondBoxY = boxY - cmToPixels(1); 
        int secondBoxHeight = cmToPixels(1); 

        secondBoxPanel.setBounds(secondBoxX, secondBoxY, boxWidth, secondBoxHeight);

        Border secondBoxBorder = BorderFactory.createLineBorder(new Color(54, 91, 92), 1);
        secondBoxPanel.setBorder(secondBoxBorder);

        JLabel transactionLabel = new JLabel("TRANSACTION");
        transactionLabel.setFont(new Font("Arial", Font.BOLD, 20));
		transactionLabel.setForeground(Color.WHITE);

        secondBoxPanel.add(transactionLabel);

        add(secondBoxPanel);
		
        JPanel bottomSalesBoxPanel = new JPanel();
        bottomSalesBoxPanel.setBackground(new Color(172,198,199));

        int bottomBoxX = boxX;
        int bottomBoxY = boxY + boxHeight + cmToPixels(0.1); 
        int bottomBoxHeight = cmToPixels(4); 

        bottomSalesBoxPanel.setBounds(bottomBoxX, bottomBoxY, boxWidth, bottomBoxHeight);

        Border bottomBoxBorder = BorderFactory.createLineBorder(new Color(54, 91, 92), 1);
        bottomSalesBoxPanel.setBorder(bottomBoxBorder);

        add(bottomSalesBoxPanel);
		
		departmentBoxPanel = new JPanel(new BorderLayout());
		departmentBoxPanel.setBackground(new Color(172,198,199));
		
		int departmentBoxX = boxX + cmToPixels(18); 
		int departmentBoxY = cmToPixels(2); 
		int departmentBoxWidth = cmToPixels(30.5); 
		int departmentBoxHeight = cmToPixels(10.5); 
		
		departmentBoxPanel.setBounds(departmentBoxX, departmentBoxY, departmentBoxWidth, departmentBoxHeight);
		
		Border departmentBoxBorder = BorderFactory.createLineBorder(new Color(54, 91, 92), 1);
		departmentBoxPanel.setBorder(departmentBoxBorder);
		
		add(departmentBoxPanel);
			
		departmentButtonsPanel = new JPanel(new GridLayout(0, 8, cmToPixels(0.5), cmToPixels(0.5)));
		departmentButtonsPanel.setOpaque(false); 
		
		Department taxDepartment = new Department("Tax", true, false, new Color(117, 176, 178));
		Department nonTaxDepartment = new Department("Non-Tax", false, false, new Color(117, 176, 178));
		
		if(buttonDepartmentMap.isEmpty())
		{
			createDepartmentButton(taxDepartment);
			createDepartmentButton(nonTaxDepartment);
		}
		
		boolean isTax = true;
		boolean restricAge = false;
		
		JPanel dpsecondBoxPanel = new JPanel();
        dpsecondBoxPanel.setBackground(new Color(117,176,178));
        int dpsecondBoxX = departmentBoxX;
        int dpsecondBoxY = departmentBoxY - cmToPixels(1); 
        int dpsecondBoxHeight = cmToPixels(1); 

        dpsecondBoxPanel.setBounds(dpsecondBoxX, dpsecondBoxY, departmentBoxWidth, departmentBoxHeight);
		
        JLabel departmentLabel = new JLabel("DEPARTMENT");
        departmentLabel.setFont(new Font("Arial", Font.BOLD, 20));
		departmentLabel.setForeground(Color.WHITE);
        dpsecondBoxPanel.add(departmentLabel);

		JButton addDepartmentButton = new JButton("+");
		addDepartmentButton.setFont(new Font("Arial", Font.BOLD, 16));
		addDepartmentButton.setForeground(Color.WHITE);
		addDepartmentButton.setBackground(new Color(54, 91, 92));
		addDepartmentButton.setFocusPainted(false);
	
		int labelXX = cmToPixels(48.0); 
		int labelYY = cmToPixels(1.15);
		addDepartmentButton.setBounds(labelXX, labelYY, addDepartmentButton.getPreferredSize().width, addDepartmentButton.getPreferredSize().height);
		getContentPane().add(addDepartmentButton);
		getContentPane().setComponentZOrder(addDepartmentButton, 0); 
		
		addDepartmentButton.addActionListener(e -> onAddDepartmentButtonClick());
		
		Border dpBoxBorder = BorderFactory.createLineBorder(new Color(54, 91, 92), 1);
		dpsecondBoxPanel.setBorder(dpBoxBorder);
        add(dpsecondBoxPanel);
	
		JPanel keyPadBox = new JPanel();
        keyPadBox.setBackground(new Color(172,198,199));

        int keyPadBoxX = departmentBoxX + departmentBoxWidth - cmToPixels(15);
        int keyPadBoxY = departmentBoxY + departmentBoxHeight + cmToPixels(1); 
        int keyPadBoxWidth = cmToPixels(15); 
        int keyPadBoxHeight = cmToPixels(10.5);

        keyPadBox.setBounds(keyPadBoxX, keyPadBoxY, keyPadBoxWidth, keyPadBoxHeight);

        Border keyPadBoxBorder = BorderFactory.createLineBorder(new Color(54, 91, 92), 1);
        keyPadBox.setBorder(keyPadBoxBorder);

        add(keyPadBox);
				
		JButton[] keyPadButtons = new JButton[20];
		String[] keyPadButtonLabels = {
			"1", "2", "3", "X", "No Sale",
			"4", "5", "6", "Setting", "Report",
			"7", "8", "9", "Void", "Manager",
			"Receipt", "00", "0", "DrCr", "Cash"
		};
		
		int gap = cmToPixels(0.5); 
		
		int buttonWidth = (keyPadBoxWidth - 6 * gap) / 5; 
		int buttonHeight = cmToPixels(2); 
		int buttonY = cmToPixels(2); 
				
		for (int i = 0; i < keyPadButtons.length; i++) 
		{
			keyPadButtons[i] = new JButton(keyPadButtonLabels[i]);
			
			if(i != 0 && i != 1 && i != 2 && i != 3 && i != 5 && i != 6 && i != 7 && i != 10 && i != 11 && i != 12 && i != 16 && i != 17) 
				keyPadButtons[i].setFont(new Font("Helvetica", Font.BOLD, 16)); 
			else
				keyPadButtons[i].setFont(new Font("Helvetica", Font.BOLD, 30));
			if(i == 14)
				keyPadButtons[i].setFont(new Font("Helvetica", Font.BOLD, 14)); 			
			keyPadButtons[i].setForeground(Color.WHITE); 
			if(keyPadButtonLabels[i] == "Cash")
				keyPadButtons[i].setBackground(new Color(81, 181, 42)); 
			else if(keyPadButtonLabels[i] == "Receipt" || keyPadButtonLabels[i] == "Report")
				keyPadButtons[i].setBackground(new Color(201, 199, 46)); 				
			else if(keyPadButtonLabels[i] == "DrCr")
				keyPadButtons[i].setBackground(new Color(41, 77, 207));
			else if(keyPadButtonLabels[i] == "X" || keyPadButtonLabels[i] == "No Sale" || keyPadButtonLabels[i] == "Setting" || keyPadButtonLabels[i] == "Refund" || keyPadButtonLabels[i] == "Void")
				keyPadButtons[i].setBackground(new Color(207, 46, 41)); 
			else
				keyPadButtons[i].setBackground(new Color(117,176,178)); 
			
			keyPadButtons[i].setPreferredSize(new Dimension(buttonWidth + 12, buttonHeight + 15));
			
			int col = i % 5;
			int posX = keyPadBoxX + gap + (buttonWidth + gap) * col;
			
			keyPadButtons[i].setBounds(posX, buttonY, buttonWidth, buttonHeight);
			keyPadButtons[i].setFocusPainted(false);
			
			
			keyPadButtons[i].addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					JButton clickedButton = (JButton) e.getSource();
					onKeyPadButtonClick(clickedButton.getText(), clickedButtonsLabel);
				}
			});
			
			keyPadBox.add(keyPadButtons[i]);
		}
		

		add(keyPadBox);
		boxPanel.add(clickedButtonsLabel);
		setVisible(true);
		
	}

    private int cmToPixels(double cm) 
	{
        return (int) (cm * 96 / 2.54);
    }
	
	private void onKeyPadButtonClick(String buttonLabel, JLabel clickedButtonsLabel) 
	{
		if (buttonLabel.equals("X")) 
		{
			clickedButtonsLabel.setText("READY");
		} 
		else if(buttonLabel.equals("DrCr"))
		{
			if(amountDue == 0)
				displayScreenMessage("Transaction not found!");
			else
				displayScreenMessage("External machine not found!");
			clickedButtonsLabel.setText("READY");
		}
		else if(buttonLabel.equals("Report") || buttonLabel.equals("Setting") || buttonLabel.equals("Manager"))
		{
			if(clickedButtonsLabel.getText() == "READY" && amountDue == 0.00)
				displayScreenMessage("Currently unavailable.");
			else if(amountDue != 0.00)
				displayScreenMessage("Invalid! Transaction in progress");
			else
				displayScreenMessage("Invalid Operation");
			clickedButtonsLabel.setText("READY");
		}
		
		else if(buttonLabel.equals("Receipt"))
		{
			if(clickedButtonsLabel.getText() == "READY" && amountDue == 0.00)
				displayScreenMessage("Printer not detected!");
			else if(amountDue != 0.00)
				displayScreenMessage("Invalid! Transaction in progress");
			else
				displayScreenMessage("Invalid Operation");
			clickedButtonsLabel.setText("READY");
		}
		
		else if(buttonLabel.equals("No Sale"))
		{
			if(clickedButtonsLabel.getText() == "READY" && amountDue == 0.00)
				displayScreenMessage("Close cash register to continue.");
			else if(amountDue != 0.00)
				displayScreenMessage("Invalid! Transaction in progress");

			else
				displayScreenMessage("Invalid Operation");
		}
		else if(buttonLabel.equals("Cash"))
		{
			if(amountDue == 0.00)
			{
				displayScreenMessage("No transaction to cash.");
				clickedButtonsLabel.setText("READY");
			}
			else if(clickedButtonsLabel.getText() == "READY" && cash == 0.0)
			{
				cash = amountDue;
				amountDue = 0.0;
				getContentPane().remove(labelSubTotal);
				getContentPane().remove(labelTax);
				getContentPane().remove(labelTotal);
				getContentPane().remove(labelCash);
				getContentPane().remove(labelAmountDue);
				appendTransactionSummary(subTotal, tax, total, cash, amountDue);
				
				displayScreenMessage("Close cash register to continue.");
				subTotal = 0.00;
				tax = 0.00;
				total = 0.00;
				cash = 0.00;
				amountDue = 0.00;
				prevSumCash = 0;
				labelY = cmToPixels(5);
				
				for(JLabel thisLabel : transactionLabels)
				{
					getContentPane().remove(thisLabel);
				}
				getContentPane().remove(labelSubTotal);
				getContentPane().remove(labelTax);
				getContentPane().remove(labelTotal);
				getContentPane().remove(labelCash);
				getContentPane().remove(labelAmountDue);
				repaint();
				revalidate();
				
			}
			else
			{
				double customerCash = Double.parseDouble(clickedButtonsLabel.getText()) / 100.00;
				cash = customerCash;
				prevSumCash += cash;
				double copyAmountDue = amountDue;
				amountDue = amountDue - customerCash;
				getContentPane().remove(labelSubTotal);
				getContentPane().remove(labelTax);
				getContentPane().remove(labelTotal);
				getContentPane().remove(labelCash);
				getContentPane().remove(labelAmountDue);
				appendTransactionSummary(subTotal, tax, total, prevSumCash, amountDue);
				clickedButtonsLabel.setText("READY");
				
				
				if(customerCash >= copyAmountDue)
				{	
					displayScreenMessage("Close cash register to continue.");
					subTotal = 0.00;
					tax = 0.00;
					total = 0.00;
					cash = 0.00;
					amountDue = 0.00;
					prevSumCash = 0;
					labelY = cmToPixels(5);
					
					for(JLabel thisLabel : transactionLabels)
					{
						getContentPane().remove(thisLabel);
					}
					
					getContentPane().remove(labelSubTotal);
					getContentPane().remove(labelTax);
					getContentPane().remove(labelTotal);
					getContentPane().remove(labelCash);
					getContentPane().remove(labelAmountDue);
					repaint();
					revalidate();
				}
				else
				{
					revalidate();
					repaint();
				}
			}

		}
		else if(buttonLabel.equals("Void"))
		{
			if(subTotal == 0.00)
				displayScreenMessage("No transaction to void.");
			else
			{
				subTotal = 0.00;
				tax = 0.00;
				total = 0.00;
				cash = 0.00;
				amountDue = 0.00;
				prevSumCash = 0;
				labelY = cmToPixels(5);
				getContentPane().remove(labelSubTotal);
				getContentPane().remove(labelTax);
				getContentPane().remove(labelTotal);
				getContentPane().remove(labelCash);
				getContentPane().remove(labelAmountDue);
				
				for(JLabel thisLabel : transactionLabels)
				{
					getContentPane().remove(thisLabel);
				}
				
				repaint();
				revalidate();
				displayScreenMessage("Voided current transaction.");
			}
		}
		else 
		{
			if(clickedButtonsLabel.getText() == "READY")
				clickedButtonsLabel.setText("");
			String currentText = clickedButtonsLabel.getText();
			String newText = currentText + buttonLabel;
			clickedButtonsLabel.setText(newText);
		}
	}
	
	private void displayScreenMessage(String message) 
{
    JDialog messageDialog = new JDialog(this, message, Dialog.ModalityType.APPLICATION_MODAL);
    messageDialog.setUndecorated(true); 

    JLabel messageLabel = new JLabel(message);
    messageLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
    messageLabel.setForeground(Color.WHITE);
    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
    messageDialog.add(messageLabel);

    int dialogWidth = cmToPixels(10); 
    int dialogHeight = cmToPixels(15); 
    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    int dialogX = (screenWidth - dialogWidth) / 2; 
    int dialogY = (screenHeight - dialogHeight) / 2; 
    messageDialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
    
    
    messageDialog.getContentPane().setBackground(new Color(117, 176, 178));
    
    
    messageDialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(0, 105, 106), 2)); 

    Timer timer = new Timer(3000, new ActionListener() 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            messageDialog.dispose(); 
        }
    });
    timer.setRepeats(false); 
    timer.start();

    messageDialog.setVisible(true);
}

	private void createDepartmentButton(Department departmentName)
	{
		int i = 0;
		while(departmentButtons[i] != null)
		{
			i += 1;
		}
		
		departmentButtons[i] = new JButton(departmentName.toString());
		buttonDepartmentMap.put(departmentButtons[i], departmentName);

		departmentButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
		departmentButtons[i].setPreferredSize(new Dimension(cmToPixels(3), cmToPixels(2))); 
		if(departmentName.buttonColor() == Color.WHITE)
			departmentButtons[i].setForeground(Color.BLACK);
		else
			departmentButtons[i].setForeground(Color.WHITE);
		departmentButtons[i].setBackground(departmentName.buttonColor()); 
		departmentButtons[i].setFocusPainted(false);
		
		int j = 0;
		while(departmentButtons[j] != null)
		{
			departmentButtonsPanel.add(departmentButtons[j]);
			j += 1;
		}
		
        departmentButtonsPanel.setBorder(BorderFactory.createEmptyBorder(cmToPixels(0.5), cmToPixels(0.5), 0, cmToPixels(0.5)));

		departmentBoxPanel.add(departmentButtonsPanel, BorderLayout.NORTH);
		
		JButton departmentButton = departmentButtons[i];
				
		departmentButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JButton clickedButton = (JButton) e.getSource();
				String labelText = clickedButtonsLabel.getText();
				
				if (labelText.equals("READY") || cash != 0.0) 
				{
					if(labelText.equals("READY"))
						displayScreenMessage("Invalid Operation");
					else
						displayScreenMessage("Complete current transaction.");		
					clickedButtonsLabel.setText("READY");
				} 
				else 
				{
					
					Department currentDepartment = buttonDepartmentMap.get(departmentButton);
					String departmentName = currentDepartment.toString();
					
					if(currentDepartment.restrictAge())
					{
						String departmentAdd = "Age Verification Required!";
						JDialog messageDialog = new JDialog(null, departmentAdd, Dialog.ModalityType.APPLICATION_MODAL);
						messageDialog.setUndecorated(true);
						
						int dialogWidth = cmToPixels(10);
						int dialogHeight = cmToPixels(5);
						
						JPanel titleBar = new JPanel(new BorderLayout());
						titleBar.setBackground(new Color(117, 176, 178)); 
						titleBar.setPreferredSize(new Dimension(dialogWidth, cmToPixels(1.5))); 
						
						JLabel titleLabel = new JLabel(departmentAdd);
						titleLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
						titleLabel.setForeground(Color.WHITE);
						titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
						titleBar.add(titleLabel, BorderLayout.CENTER);
					
						messageDialog.getContentPane().setLayout(new BorderLayout());
						messageDialog.getContentPane().setBackground(new Color(172, 198, 199));
					
						messageDialog.getContentPane().add(titleBar, BorderLayout.NORTH);
					
						JPanel inputPanel = new JPanel();
						inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, cmToPixels(0.5), cmToPixels(0.5))); // Adjust margins
						inputPanel.setBackground(new Color(172, 198, 199));
						
						JLabel inputLabel = new JLabel("Is customer 21 years or above?");
						inputLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
						inputLabel.setForeground(Color.WHITE);
						
						inputPanel.add(inputLabel);
						
						JPanel buttonsPanel = new JPanel();
						buttonsPanel.setBackground(new Color(172, 198, 199));
						
						JButton applyButton = new JButton("Yes");
						applyButton.setPreferredSize(new Dimension(cmToPixels(2.5), cmToPixels(1))); 
						applyButton.setBackground(new Color(117, 176, 178));
						applyButton.setForeground(Color.WHITE);
						applyButton.setFocusPainted(false); 
		
		
						applyButton.addActionListener(new ActionListener() 
						{
							@Override
							public void actionPerformed(ActionEvent e) 
							{
								messageDialog.dispose();
								int labelValue = Integer.parseInt(labelText);
								double labelValueDouble = labelValue / 100.0;
								
								int intQty = 1;
								String quantity = String.format("%d", intQty);
								
								double amount = labelValueDouble;
								String amountStr = String.format("%.2f", amount);
								subTotal += amount;
								
								if(currentDepartment.taxLevied())
									tax += amount*0.0825;
								
								total = subTotal + tax;
								
								amountDue = total - cash;
								
								appendTransactionDetails(departmentName, quantity, amountStr);
								appendTransactionSummary(subTotal, tax, total, cash, amountDue);
							}
						});
	
		
						JButton cancelButton = new JButton("No");
						cancelButton.setPreferredSize(new Dimension(cmToPixels(2.5), cmToPixels(1))); 
						cancelButton.setBackground(new Color(117, 176, 178));
						cancelButton.setForeground(Color.WHITE);
						cancelButton.setFocusPainted(false); 
						cancelButton.addActionListener(new ActionListener() 
						{
							@Override
							public void actionPerformed(ActionEvent e) 
							{
								messageDialog.dispose();
								displayScreenMessage("Sales Declined!");
							}
						});
					
						buttonsPanel.add(applyButton);
						buttonsPanel.add(Box.createRigidArea(new Dimension(cmToPixels(2), 0))); 
						buttonsPanel.add(cancelButton);
						
						messageDialog.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
					
						messageDialog.getContentPane().add(inputPanel, BorderLayout.CENTER);
					
						int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
						int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
						int dialogX = (screenWidth - dialogWidth) / 2;
						int dialogY = (screenHeight - dialogHeight) / 2;
						messageDialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
					
						messageDialog.setVisible(true);
					}
					
					else
					{
						int labelValue = Integer.parseInt(labelText);
						double labelValueDouble = labelValue / 100.0;
						
						int intQty = 1;
						String quantity = String.format("%d", intQty);
						
						double amount = labelValueDouble;
						String amountStr = String.format("%.2f", amount);
						subTotal += amount;
						
						if(currentDepartment.taxLevied())
							tax += amount*0.0825;
						
						total = subTotal + tax;
						
						amountDue = total - cash;
						
						appendTransactionDetails(departmentName, quantity, amountStr);
						appendTransactionSummary(subTotal, tax, total, cash, amountDue);
					}
					clickedButtonsLabel.setText("READY");
				}
			}
		});
		
		revalidate();
		repaint();
	}

	private void appendTransactionDetails(String departmentName, String quantity, String amount) 
	{
		label = new JLabel(departmentName);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setForeground(new Color(35,43,43));
		
		labelY += cmToPixels(0.6);
	
		int labelX = cmToPixels(1.5); 
		label.setBounds(labelX, labelY, label.getPreferredSize().width, label.getPreferredSize().height);
		getContentPane().add(label);
		getContentPane().setComponentZOrder(label, 0); 
		
		labelQty = new JLabel(quantity);
		labelQty.setFont(new Font("Arial", Font.BOLD, 15));
		labelQty.setForeground(new Color(35,43,43));
	
		labelX = cmToPixels(7); 
		labelQty.setBounds(labelX, labelY, labelQty.getPreferredSize().width, labelQty.getPreferredSize().height);
	
		getContentPane().add(labelQty);
		getContentPane().setComponentZOrder(labelQty, 0); 
		
		labelAmount = new JLabel(amount);
		labelAmount.setFont(new Font("Arial", Font.BOLD, 15));
		labelAmount.setForeground(new Color(35,43,43));
	
		labelX = cmToPixels(9); 
		labelAmount.setBounds(labelX, labelY, labelAmount.getPreferredSize().width, labelAmount.getPreferredSize().height);
		labelY += cmToPixels(0.6);
		getContentPane().add(labelAmount);
		getContentPane().setComponentZOrder(labelAmount, 0);

		transactionLabels.add(label);
		transactionLabels.add(labelQty);
		transactionLabels.add(labelAmount);
	}
	
	private void appendTransactionSummary(double subTotal, double tax, double total, double cash, double amountDue) {
		if (labelSubTotal != null) 
		{
			getContentPane().remove(labelSubTotal);
		}

		String subTotalText = "Sub total    $" + String.valueOf(String.format("%.2f", subTotal));

		labelSubTotal = new JLabel(subTotalText);
		labelSubTotal.setFont(new Font("Arial", Font.BOLD, 21));
		labelSubTotal.setForeground(new Color(35, 43, 43));

		int labelYSummary = cmToPixels(18.5); 
		int subTotalTextWidth = labelSubTotal.getPreferredSize().width;

		int labelSubTotalX = getWidth() - cmToPixels(41) - subTotalTextWidth; 

		labelSubTotal.setBounds(labelSubTotalX, labelYSummary, subTotalTextWidth, labelSubTotal.getPreferredSize().height);

		getContentPane().add(labelSubTotal);
		getContentPane().setComponentZOrder(labelSubTotal, 0);

		if (labelTax != null) 
		{
			getContentPane().remove(labelTax);
		}

		String taxText = "TAX    $" + String.valueOf(String.format("%.2f", tax));

		labelTax = new JLabel(taxText);
		labelTax.setFont(new Font("Arial", Font.BOLD, 21));
		labelTax.setForeground(new Color(35, 43, 43));

		int taxTextWidth = labelTax.getPreferredSize().width;
		int labelTaxX = getWidth() - cmToPixels(41) - taxTextWidth; 

		labelTax.setBounds(labelTaxX, labelYSummary + cmToPixels(0.8), taxTextWidth, labelTax.getPreferredSize().height);

		getContentPane().add(labelTax);
		getContentPane().setComponentZOrder(labelTax, 0);

		if (labelTotal != null) 
		{
			getContentPane().remove(labelTotal);
		}

		String totalText = "Total    $" + String.valueOf(String.format("%.2f", total));

		labelTotal = new JLabel(totalText);
		labelTotal.setFont(new Font("Arial", Font.BOLD, 21));
		labelTotal.setForeground(new Color(35, 43, 43));

		int totalTextWidth = labelTotal.getPreferredSize().width;
		int labelTotalX = getWidth() - cmToPixels(41) - totalTextWidth; 

		labelTotal.setBounds(labelTotalX, labelYSummary + 2 * cmToPixels(0.8), totalTextWidth, labelTotal.getPreferredSize().height);

		getContentPane().add(labelTotal);
		getContentPane().setComponentZOrder(labelTotal, 0);

		if (labelCash != null) 
		{
			getContentPane().remove(labelCash);
		}
		
		String cashText = "Cash    $" + String.valueOf(String.format("%.2f", cash));

		labelCash = new JLabel(cashText);
		labelCash.setFont(new Font("Arial", Font.BOLD, 21));
		labelCash.setForeground(new Color(207, 46, 41));

		int cashTextWidth = labelCash.getPreferredSize().width;
		int labelCashX = getWidth() - cmToPixels(41) - cashTextWidth; 

		labelCash.setBounds(labelCashX, labelYSummary + 3 * cmToPixels(0.8), cashTextWidth, labelCash.getPreferredSize().height);
		
		if(cash != 0.0)
		{
			getContentPane().add(labelCash);
			getContentPane().setComponentZOrder(labelCash, 0);
		}
		

		if (labelAmountDue != null) 
		{
			getContentPane().remove(labelAmountDue);
		}
		
		String amountDueText;
		
		if(amountDue <= 0)
			amountDueText = "Change    $" + String.valueOf(String.format("%.2f", Math.abs(amountDue)));
		else
			amountDueText = "Amount Due    $" + String.valueOf(String.format("%.2f", amountDue));

		labelAmountDue = new JLabel(amountDueText);
		labelAmountDue.setFont(new Font("Arial", Font.BOLD, 21));
		labelAmountDue.setForeground(new Color(41, 77, 207));

		int amountDueTextWidth = labelAmountDue.getPreferredSize().width;
		int labelAmountDueX = getWidth() - cmToPixels(41) - amountDueTextWidth; 

		labelAmountDue.setBounds(labelAmountDueX, labelYSummary + 4 * cmToPixels(1.0), amountDueTextWidth, labelAmountDue.getPreferredSize().height);

		getContentPane().add(labelAmountDue);
		getContentPane().setComponentZOrder(labelAmountDue, 0);

		revalidate();
		repaint();
	}
	
	private void onAddDepartmentButtonClick() 
	{
		String departmentAdd = "Add New Department";
		JDialog messageDialog = new JDialog(this, departmentAdd, Dialog.ModalityType.APPLICATION_MODAL);
		messageDialog.setUndecorated(true);
		messageDialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(0, 105, 106), 2));

		
		int dialogWidth = cmToPixels(10);
		int dialogHeight = cmToPixels(15);
		
		JPanel titleBar = new JPanel(new BorderLayout());
		titleBar.setBackground(new Color(117, 176, 178)); 
		titleBar.setPreferredSize(new Dimension(dialogWidth, cmToPixels(1.5))); 
		
		JLabel titleLabel = new JLabel(departmentAdd);
		titleLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleBar.add(titleLabel, BorderLayout.CENTER);
	
		messageDialog.getContentPane().setLayout(new BorderLayout());
		messageDialog.getContentPane().setBackground(new Color(172, 198, 199));
	
		messageDialog.getContentPane().add(titleBar, BorderLayout.NORTH);
	
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, cmToPixels(0.5), cmToPixels(0.5))); // Adjust margins
		inputPanel.setBackground(new Color(172, 198, 199));

		
		JLabel inputLabel = new JLabel("Enter new department:");
		inputLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
		inputLabel.setForeground(Color.WHITE);
		
		JTextField departmentTextField = new JTextField();
		departmentTextField.setPreferredSize(new Dimension(cmToPixels(7), cmToPixels(1))); 
		
		inputPanel.add(inputLabel);
		inputPanel.add(departmentTextField);
		
		JPanel taxPanel = new JPanel();
		taxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		taxPanel.setOpaque(false);
		
		JRadioButton taxableRadioButton = new JRadioButton("Taxable");
		JRadioButton nonTaxableRadioButton = new JRadioButton("Non-Taxable");
		
		taxableRadioButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		nonTaxableRadioButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		
		taxableRadioButton.setForeground(Color.WHITE);
		nonTaxableRadioButton.setForeground(Color.WHITE);
		
		taxableRadioButton.setBackground(new Color(172, 198, 199));
		nonTaxableRadioButton.setBackground(new Color(172, 198, 199));
		
		taxableRadioButton.setFocusPainted(false);
		nonTaxableRadioButton.setFocusPainted(false);
		
		ButtonGroup taxButtonGroup = new ButtonGroup();
		taxButtonGroup.add(taxableRadioButton);
		taxButtonGroup.add(nonTaxableRadioButton);
		
		taxPanel.add(taxableRadioButton);
		taxPanel.add(nonTaxableRadioButton);
		
		inputPanel.add(taxPanel);
		
		JPanel ageVerificationPanel = new JPanel(new BorderLayout());
		ageVerificationPanel.setOpaque(false);
			
		JLabel ageVerificationLabel = new JLabel("Under 21 age verification:");
		ageVerificationLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
		ageVerificationLabel.setForeground(Color.WHITE);
		ageVerificationPanel.add(ageVerificationLabel, BorderLayout.NORTH);
		
		JPanel agePanel = new JPanel();
		agePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
		agePanel.setOpaque(false); 
		
	
		JRadioButton yesAgeRadioButton = new JRadioButton("Yes");
		JRadioButton noAgeRadioButton = new JRadioButton("No");
		
		yesAgeRadioButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		noAgeRadioButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		
		yesAgeRadioButton.setForeground(Color.WHITE);
		noAgeRadioButton.setForeground(Color.WHITE);
		
		yesAgeRadioButton.setBackground(new Color(172, 198, 199));
		noAgeRadioButton.setBackground(new Color(172, 198, 199));
		
		yesAgeRadioButton.setFocusPainted(false);
		noAgeRadioButton.setFocusPainted(false);
			
		ButtonGroup ageButtonGroup = new ButtonGroup();
		ageButtonGroup.add(yesAgeRadioButton);
		ageButtonGroup.add(noAgeRadioButton);
		
		agePanel.add(yesAgeRadioButton);
		agePanel.add(noAgeRadioButton);
		
		ageVerificationPanel.add(agePanel, BorderLayout.CENTER);
		
		inputPanel.add(ageVerificationPanel);
		
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		colorPanel.setOpaque(false);
	
		JLabel colorLabel = new JLabel("Select color:");
		colorLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
		colorLabel.setForeground(Color.WHITE);
		colorPanel.add(colorLabel);
	
		String[] colorNames = {"Red", "Blue", "Green", "Yellow", "Purple", "Pink", "Orange", "White", "Grey", "Brown", "Black"};
		JComboBox<String> colorComboBox = new JComboBox<>(colorNames);
		colorPanel.add(colorComboBox);
	
		inputPanel.add(colorPanel);
	
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(172, 198, 199));
		
		JButton applyButton = new JButton("Apply");
		applyButton.setPreferredSize(new Dimension(cmToPixels(2.5), cmToPixels(1))); 
		applyButton.setBackground(new Color(117, 176, 178));
		applyButton.setForeground(Color.WHITE);
		applyButton.setFocusPainted(false); 
		
		
		applyButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String departmentName = departmentTextField.getText();
				if (departmentName.isEmpty() || (!taxableRadioButton.isSelected() && !nonTaxableRadioButton.isSelected()) || (!yesAgeRadioButton.isSelected() && !noAgeRadioButton.isSelected())) 
				{
					messageDialog.dispose();
					displayScreenMessage("Enter all fields!");
					onAddDepartmentButtonClick(); // Recall the function
				} 
				else 
				{
					boolean isTaxable = taxableRadioButton.isSelected();
					boolean isUnder21 = yesAgeRadioButton.isSelected();
					String selectedColorName = (String) colorComboBox.getSelectedItem();
					Color selectedColor = getColorFromName(selectedColorName);

					Department newDepartment = new Department(departmentName, isTaxable, isUnder21, selectedColor);
					createDepartmentButton(newDepartment);
					messageDialog.dispose();
				}
			}
		});
	
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(cmToPixels(2.5), cmToPixels(1))); 
		cancelButton.setBackground(new Color(117, 176, 178));
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setFocusPainted(false); 
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				messageDialog.dispose();
			}
		});
	
		buttonsPanel.add(applyButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(cmToPixels(2), 0))); // Create space between buttons
		buttonsPanel.add(cancelButton);
		
		messageDialog.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	
		messageDialog.getContentPane().add(inputPanel, BorderLayout.CENTER);
	
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int dialogX = (screenWidth - dialogWidth) / 2;
		int dialogY = (screenHeight - dialogHeight) / 2;
		messageDialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
	
		messageDialog.setVisible(true);
		
	}
	
	private Color getColorFromName(String colorName) 
	{
		switch (colorName) 
		{
			case "Red":
				return Color.RED;
			case "Blue":
				return Color.BLUE;
			case "Green":
				return Color.GREEN;
			case "Yellow":
				return Color.YELLOW;
			case "Purple":
				return new Color(128, 0, 128);
			case "Pink":
				return Color.PINK;
			case "Orange":
				return Color.ORANGE;
			case "White":
				return Color.WHITE;
			case "Grey":
				return Color.GRAY;
			case "Brown":
				return new Color(165, 42, 42);
			case "Black":
				return Color.BLACK;
			default:
				return Color.BLACK; 
		}
	}
}