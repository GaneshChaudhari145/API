package com.NSE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class ExecutionFrame.
 */
public class ExecutionFrame {

	 JFrame f;

     JDialog d;

     public boolean t;

     ExecutionFrame()      

     { 

           

            JFrame f=new JFrame("NSE API Execution");

            JLabel l1=new JLabel("Click Start Execution button to run the suite");            
            l1.setBounds(50,50, 250,30);
            JButton showDialog = new JButton("show dialog");

             
            	
            JButton b=new JButton("Start Execution"); 

            b.setBounds(100,100,150,30);

            f.add(l1);

            f.add(b);   

            f.setSize(400,200); 

            f.setLayout(null); 

            f.setVisible(true);                                         

            b.addActionListener(new ActionListener(){                

            public void actionPerformed(ActionEvent e){

              JButton source = (JButton) e.getSource();

                source.setEnabled(false);

             // abc();

                TestEngine StartExe=new TestEngine();
                try {
					StartExe.Start();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        

            } 

            });            

            

            f.addWindowListener(new java.awt.event.WindowAdapter() {

                public void windowClosing(java.awt.event.WindowEvent e) {

                     

                    System.exit(0);

                }

            });

     } 



     

     public static void main(String[] args) { 

            new ExecutionFrame();

           

            

     } 

     

     public void abc() {

        

        //System.out.println("Start Called");

        

         d = new JDialog(f, "Execution Completed");             

         JPanel p = new JPanel();

         JLabel l = new JLabel("Execution Completed");

         JButton b_OK = new JButton("OK");  

          

                  

                   p.add(b_OK);

                   d.add(p);

                   b_OK.addActionListener(new ActionListener(){ 

                   public void actionPerformed(ActionEvent e){ 

                     //xyz();

                     String s = e.getActionCommand();

                       if (s.equals("OK")) {

                            System.out.println("OK Clicked");

                           // create a dialog Box

                           d.dispose();

                       }      

                   } 

                   });



                   // setsize of dialog

                   d.setSize(200, 100);

                   // set visibility of dialog

                   d.setVisible(true);

                   

           

     }
}
