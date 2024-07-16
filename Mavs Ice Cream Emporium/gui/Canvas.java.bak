package gui;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Canvas extends JPanel
{
	Image image = new ImageIcon("logo.jpg").getImage();	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(Color.white);
		g2d.fillRect(0, 0, 2000, 1000);
		
		g2d.drawImage(image, 52, 52, null);
		g2d.setPaint(Color.orange);
		g2d.setFont(new Font("default", Font.BOLD, 16));
		g2d.drawString("Version 1.2J", 355, 340);
		g2d.drawString("Copyright 2022 by Sajan Gautam", 300, 360);
		g2d.drawString("Licensed under Gnu GPL 3.0", 300, 380);
		g2d.drawString("Logo by LoGo, licensed under CC BY-SA 3.0", 300, 400);
		g2d.drawString("All rights reserved under MICE Inc", 300, 420);
		
		
	}
}