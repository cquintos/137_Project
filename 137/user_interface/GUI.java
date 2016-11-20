import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.net.*;
import java.io.*;

import java.awt.Container;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.io.*;
import sun.audio.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Font;

public class GUI {
    static JPanel ball = new JPanel(null);
    static JPanel winner = new JPanel(null);
    static JPanel loser = new JPanel(null);
    static Client client;
    static Socket socket;
    static JTextArea chat = new JTextArea("Welcome! Please be friendly in conversing with others! :)\n");
    static JTextArea inGameChat = new JTextArea();
    static JButton tugButton = new JButton("TUG");
    static boolean gameStart = false;
    
    static JFrame frame = new JFrame("Thug of War - Main Menu");
    final static Container contentPane = frame.getContentPane();
    final static CardLayout cardLayout = new CardLayout();

    public static JPanel addMenu() {
        JPanel menu = new JPanel(null);
        JPanel b1 = new JPanel();
        JPanel b2 = new JPanel();
        JPanel b3 = new JPanel();
        JLabel l1 = new JLabel("ENTER LOBBY");
        JLabel l2 = new JLabel("READ GUIDE");
        JLabel l3 = new JLabel("BE A PU$$Y");
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File("menu-bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(1080, 700, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        JLabel bg = new JLabel(imageIcon);

        bg.setBounds(0,0, 1080, 700);
        b1.setBounds(240, 310, 600, 80);
        b2.setBounds(240, 410, 600, 80);
        b3.setBounds(240, 510, 600, 80);

        b1.add(l1);
        b2.add(l2);
        b3.add(l3);

        //setBounds(x, y, width, height)
        b1.setBackground(new Color(150, 0, 0, 64));
        b2.setBackground(new Color(0, 0, 150, 64));
        b3.setBackground(new Color(0, 0, 0, 64));
        
        menu.add(b1);
        menu.add(b2);
        menu.add(b3);
        menu.add(bg);

        b1.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) { 
                cardLayout.show(contentPane, "Waiting Room");
            }
            public void mouseEntered(MouseEvent e) { 
                b1.setBackground(new Color(150, 0, 0, 34));
            }
            public void mouseExited(MouseEvent e) { 
                b1.setBackground(new Color(150, 0, 0, 64));
            } 
        });

        b2.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) { 
                cardLayout.show(contentPane, "Guide");
            }
            public void mouseEntered(MouseEvent e) { 
                b2.setBackground(new Color(0, 0, 150, 34));
            }
            public void mouseExited(MouseEvent e) { 
                b2.setBackground(new Color(0, 0, 150, 64));
            } 
        });
        
        b3.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) { 
                 System.exit(0);
            }
            public void mouseEntered(MouseEvent e) { 
                b3.setBackground(new Color(0,0,0,34));
            }
            public void mouseExited(MouseEvent e) { 
                b3.setBackground(new Color(0, 0, 0, 64));
            }  
        });

        return menu;
    }

    public static JPanel addGuide() {
        JPanel guide = new JPanel(null);
        JButton b1 = new JButton("BACK TO MENU");
        
        guide.setVisible(true);
        guide.add(b1);

        //setBounds(x, y, width, height)
        b1.setBounds(50, 50, 200, 80);

        b1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                cardLayout.show(contentPane, "Main Menu");
            } 
        });

        return guide;
    }

    public static JPanel addWaitingRoom() {
        JLabel provokeLabel = new JLabel("PROVOKE ^");
        JLabel getProvoked = new JLabel("OR BE PROVOKED >");
        JPanel newWarSettings = new JPanel(null);
        JPanel wRoom = new JPanel(null);
        JPanel chatPanel = new JPanel(null);
        JButton createBtn = new JButton("CREATE WAR");
        JButton clearBtn = new JButton("CLEAR");
        JButton backBtn = new JButton("BACK TO MENU");

        JButton sendBtn = new JButton("ENTER");
        JTextField newMessage = new JTextField();
        JScrollPane scroll = new JScrollPane();

        provokeLabel.setFont(new Font("Helveltica", Font.BOLD, 50)); 
        getProvoked.setFont(new Font("Helveltica", Font.BOLD, 40)); 

        chatPanel.setBackground(Color.WHITE);
        newWarSettings.setBackground(Color.RED);

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(chat);


        wRoom.setVisible(true);
        wRoom.add(provokeLabel);
        wRoom.add(getProvoked);
        wRoom.add(newWarSettings);
        wRoom.add(createBtn);
        wRoom.add(clearBtn);
        wRoom.add(backBtn);
        wRoom.add(chatPanel);

        chatPanel.add(newMessage);
        chatPanel.add(scroll);
        chatPanel.add(sendBtn);

        //setBounds(x, y, width, height)
        scroll.setBounds(40, 50, 460, 250);
        sendBtn.setBounds(410, 310, 90, 40);
        newMessage.setBounds(40, 310, 360, 40);

        // provokeLabel.setBounds(40, 300, 460, 250);
        // getProvoked.setBounds(40, 500, 460, 250);
        newWarSettings.setBounds(40, 50, 460, 250);
        createBtn.setBounds(60, 310, 200, 40);
        clearBtn.setBounds(280, 310, 200, 40);
        backBtn.setBounds(40, 380, 460, 250);
        chatPanel.setBounds(540, 0, 540, 700);

        createBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                inGameChat.setText("GOODLUCK, HAVE FUN!\n");
                cardLayout.show(contentPane, "Game");
                client.send("JOINEDROOM");
            } 
        });

        //createBtn.setEnabled(false);


        sendBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                try {
                    OutputStream outToServer = socket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    String message = client.name + ": " + newMessage.getText() + "\n";
                    newMessage.setText("");
                    out.writeUTF(message);
               } catch (Exception f){
                  System.out.println("Error");
               };
            } 
        });

        newMessage.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        OutputStream outToServer = socket.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        String message = client.name + ": " + newMessage.getText() + "\n";
                        newMessage.setText("");
                        out.writeUTF(message);
                    } catch (Exception f){
                      System.out.println("Error");
                    };
                }
            }
        });

        backBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                cardLayout.show(contentPane, "Main Menu");
            } 
        });

        return wRoom;
    }

    public static JPanel addGame() {
        JPanel game = new JPanel(null);
        
        JButton backBtn = new JButton("BACK TO MENU");

        JButton sendBtn = new JButton("ENTER");
        JTextField newMessage = new JTextField();
        JScrollPane scroll = new JScrollPane();

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(inGameChat);

        
        ball.setBackground(Color.red);
        winner.setBackground(Color.green);
        loser.setBackground(Color.blue);
        game.setVisible(true);
        game.add(tugButton);
        game.add(scroll);
        game.add(sendBtn);
        game.add(newMessage);
        game.add(ball);
        game.add(winner);
        game.add(loser);

        tugButton.setEnabled(false);

        //setBounds(x, y, width, height)
        scroll.setBounds(340, 350, 400, 250);
        newMessage.setBounds(340, 610, 310, 50);
        sendBtn.setBounds(660, 610, 80, 50);
        tugButton.setBounds(500, 100, 80, 80);
        ball.setBounds(510, 240, 60, 60);
        winner.setBounds(510, 240, 100, 100);
        loser.setBounds(510, 240, 100, 100);

        winner.setVisible(false);
        loser.setVisible(false);

        tugButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                client.send("CLICK "+client.name+" "+client.team);
            } 
        });

        backBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                cardLayout.show(contentPane, "Main Menu");
            } 
        });

        sendBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                try {
                    OutputStream outToServer = socket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    String message = client.name + ": " + newMessage.getText() + "\n";
                    newMessage.setText("");
                    out.writeUTF(message);
               } catch (Exception f){
                  System.out.println("Error");
               };
            } 
        });

        newMessage.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        OutputStream outToServer = socket.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        String message = client.name + ": " + newMessage.getText() + "\n";
                        newMessage.setText("");
                        out.writeUTF(message);
                    } catch (Exception f){
                      System.out.println("Error");
                    };
                }
            }
        });



        return game;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        contentPane.setLayout(cardLayout);
        contentPane.setPreferredSize(new Dimension(1080, 700));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       
        
        
        //Set up the content pane.
        contentPane.add(addMenu(), "Main Menu");

        contentPane.add(addGuide(), "Guide");                        
        contentPane.add(addWaitingRoom(), "Waiting Room");
        contentPane.add(addGame(), "Game");
        
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chat.setEnabled(false);
                inGameChat.setEnabled(false);
                if (args.length != 4){
                    System.out.println("<server> <player name> <team 1 or 2> <port no>");
                    System.exit(1);
                }
                else if(Integer.parseInt(args[2]) < 1 || Integer.parseInt(args[2]) > 2){
                    System.out.println("team 1 or 2 only");
                    System.exit(1);
                }
                try {
                    client = new Client(args[0],args[1], Integer.parseInt(args[2]));
                    String serverName = args[0]; //get IP address of server from first param
                    int port = Integer.parseInt(args[3]); //get port from second param
                    socket = new Socket(serverName, port);

                    new Thread() {
                        public void run(){
                            try {
                                /* Receive data from the ServerSocket */
                                InputStream inFromServer = socket.getInputStream();
                                DataInputStream in = new DataInputStream(inFromServer);
                        
                                while(true) {
                                    /* recieve data from the ServerSocket */
                                    String message = in.readUTF();
                                    chat.append(message);
                                    inGameChat.append(message);
                                }
                            } catch (Exception e){
                                System.out.println("Error");
                            };
                        }
                    }.start();
                } catch (Exception e) {
                    System.out.println("Loser");
                }
                createAndShowGUI();

            }
        });
    }
}