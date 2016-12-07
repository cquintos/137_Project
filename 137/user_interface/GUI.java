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
    static boolean isServer = false;
    static JPanel ball = new JPanel(null);
    static JPanel winner = new JPanel(null);
    static JPanel loser = new JPanel(null);
    static JPanel waiting = new JPanel(null);
    static JPanel three = new JPanel(null);
    static JPanel two = new JPanel(null);
    static JPanel one = new JPanel(null);
    static Client client;
    static Socket socket;
    static String serverName;
    static Server server;
    static int port;
    static JTextArea chat = new JTextArea("Welcome! Please be friendly in conversing with others! :)\n");
    static JTextArea inGameChat = new JTextArea();
    static JButton tugButton = new JButton("TUG");
    static JButton restartGameButton = new JButton("REMATCH");
    static JButton quitGameButton = new JButton("RAGE QUIT");
    static boolean gameStart = false;
    static JLabel score = new JLabel("0");
    static int playerScore = 0;
    
    static JFrame frame = new JFrame("Thug of War - Main Menu");
    final static Container contentPane = frame.getContentPane();
    final static CardLayout cardLayout = new CardLayout();

    public static JPanel addMenu() {
        JPanel menu = new JPanel(null);
        final JPanel b1 = new JPanel();
        final JPanel b2 = new JPanel();
        final JPanel b3 = new JPanel();
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

    public static JPanel addStart() {
        JPanel menu = new JPanel(null);
        final JPanel b1 = new JPanel();
        final JPanel b2 = new JPanel();
        JLabel l1 = new JLabel("CREATE LOBBY");
        JLabel l2 = new JLabel("JOIN LOBBY");
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

        b1.add(l1);
        b2.add(l2);

        //setBounds(x, y, width, height)
        b1.setBackground(new Color(150, 0, 0, 64));
        b2.setBackground(new Color(0, 0, 150, 64));
        
        menu.add(b1);
        menu.add(b2);
        menu.add(bg);

      

        b1.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) { 
                if(!isServer){
                    JTextField portNumber = new JTextField();
                    JTextField playerNumber = new JTextField();
                    Object[] message = {
                        "Number of Players", playerNumber,
                        "Port:", portNumber
                    };
                    int confirm = JOptionPane.showConfirmDialog(null, message, "Server Creation", JOptionPane.OK_CANCEL_OPTION);
                    if(playerNumber.getText() != null && portNumber.getText() != null){
                        server = new Server(Integer.parseInt(playerNumber.getText()), Integer.parseInt(portNumber.getText()));
                        JOptionPane.showMessageDialog(frame, "Game created!");
                        isServer = true;             
                    }
                }
                else
                    JOptionPane.showMessageDialog(frame, "You have a running server!");    
            }
            public void mouseEntered(MouseEvent e) { 
                b2.setBackground(new Color(0, 0, 150, 34));
            }
            public void mouseExited(MouseEvent e) { 
                b2.setBackground(new Color(0, 0, 150, 64));
            } 
        });

        b2.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e) {
                
                JTextField serverName = new JTextField();
                JTextField portNumber = new JTextField();
                JTextField playerName = new JTextField();
                Object[] message = {
                    "Player Name:", playerName,
                    "Server:", serverName,
                };
                String nameToUse = "";
                String serverToJoin = "";
                int portToUse = 0;
                int confirm = JOptionPane.CANCEL_OPTION;
                while(confirm != JOptionPane.OK_OPTION){
                   confirm = JOptionPane.showConfirmDialog(null, message, "Server Credentials", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == JOptionPane.OK_OPTION) {
                        nameToUse = playerName.getText();
                        serverToJoin = serverName.getText();
                    }
                }
                Object[] options = {"One", "Two"};
                int teamToJoin = JOptionPane.showOptionDialog(frame, "Team", "Team Inquiry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) +1;
                joinLobby(serverToJoin, nameToUse, teamToJoin);
                contentPane.add(addWaitingRoom(), "Waiting Room");
                cardLayout.show(contentPane, "Main Menu");
                
            }
            public void mouseEntered(MouseEvent e) { 
                b1.setBackground(new Color(150, 0, 0, 34));
            }
            public void mouseExited(MouseEvent e) { 
                b1.setBackground(new Color(150, 0, 0, 64));
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

        newMessage.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        String toSend = newMessage.getText();
                        toSend.substring(0, toSend.length()-1);
                        OutputStream outToServer = socket.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        String message = client.name + ": " + toSend + "\n";
                        newMessage.setText("");
                        out.writeUTF(message);
                    } catch (Exception f){
                      System.out.println("Error");
                    };
                }
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
                  System.out.println(f);
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
                      System.out.println(f);
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
        waiting.setBackground(Color.orange);
        three.setBackground(Color.red);
        two.setBackground(Color.yellow);
        one.setBackground(Color.green);
        game.setVisible(true);
        game.add(tugButton);
        game.add(scroll);
        game.add(sendBtn);
        game.add(newMessage);
        game.add(ball);
        game.add(winner);
        game.add(loser);
        game.add(waiting);
        game.add(three);
        game.add(two);
        game.add(one);
        game.add(restartGameButton);
        game.add(quitGameButton);
        //game.add(countdown);

        tugButton.setEnabled(false);

        JLabel winnerLabel = new JLabel("WINNER!");
        winnerLabel.setFont(new Font("Verdana",1,20));
        JLabel loserLabel = new JLabel("LOSER!");
        loserLabel.setFont(new Font("Verdana",1,20));
        JLabel waitingLabel = new JLabel("WAITING");
        waitingLabel.setFont(new Font("Verdana",1,20));
        JLabel threeLabel = new JLabel("THREE");
        threeLabel.setFont(new Font("Verdana",1,20));
        JLabel twoLabel = new JLabel("TWO");
        twoLabel.setFont(new Font("Verdana",1,20));
        JLabel oneLabel = new JLabel("ONE");
        oneLabel.setFont(new Font("Verdana",1,20));

        //setBounds(x, y, width, height)
        scroll.setBounds(340, 350, 400, 250);
        newMessage.setBounds(340, 610, 310, 50);
        sendBtn.setBounds(660, 610, 80, 50);
        tugButton.setBounds(500, 100, 80, 80);
        ball.setBounds(510, 240, 60, 60);
        winner.setBounds(510, 240, 300, 100);
        loser.setBounds(510, 240, 300, 100);
        waiting.setBounds(480, 100, 120, 80);
        three.setBounds(480, 100, 120, 80);
        two.setBounds(480, 100, 120, 80);
        one.setBounds(480, 100, 120, 80);
        score.setBounds(500, 50, 20, 20);
        restartGameButton.setBounds(300, 250, 100, 80);
        quitGameButton.setBounds(600, 250, 100, 80);


        winner.add(winnerLabel);
        loser.add(loserLabel);
        waiting.add(waitingLabel);
        three.add(threeLabel);
        two.add(twoLabel);
        one.add(oneLabel);

        winner.validate();
        loser.validate();
        waiting.validate();
        three.validate();
        two.validate();
        one.validate();

        game.add(waiting);
        game.add(three);
        game.add(two);
        game.add(one);
        game.add(score);

        waiting.setVisible(true);
        winner.setVisible(false);
        loser.setVisible(false);
        three.setVisible(false);
        two.setVisible(false);
        one.setVisible(false);
        tugButton.setVisible(false);
        restartGameButton.setVisible(false);
        quitGameButton.setVisible(false);
        score.setVisible(true);


        tugButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                client.send("CLICK "+client.name+" "+client.team);
                playerScore++;
            } 
        });

        restartGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                client.send("REMATCH "+client.name);
                restartGameButton.setVisible(false);
                quitGameButton.setVisible(false);
                tugButton.setVisible(false);
                waiting.setVisible(true);
                winner.setVisible(false);
                loser.setVisible(false);
                playerScore = 0;
                score.setText(Integer.toString(playerScore));
                ball.setBounds(510, 240, 60, 60);
            } 
        });

        quitGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                client.send("QUIT");
                cardLayout.show(contentPane, "Start Menu");
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
        contentPane.add(addStart(), "Start Menu");
        contentPane.add(addMenu(), "Main Menu");
        contentPane.add(addGuide(), "Guide");                        
        
        contentPane.add(addGame(), "Game");
        
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chat.setEnabled(false);
                inGameChat.setEnabled(false);
                /*if (args.length != 4){
                    System.out.println("<server> <player name> <team 1 or 2> <port no>");
                    System.exit(1);
                }
                else if(Integer.parseInt(args[2]) < 1 || Integer.parseInt(args[2]) > 2){
                    System.out.println("team 1 or 2 only");
                    System.exit(1);
                }*/
                
                createAndShowGUI();

            }
        });
    }

    public static void returnToLobby(){
        JOptionPane.showMessageDialog(frame, "Return to first menu.");
        cardLayout.show(contentPane, "Start Menu");

    }

    private static void joinLobby(String server,String name, int team){
        try {
            client = new Client(server, name, team);
            serverName = server; //get IP address of server from first param
            port = client.getServerPort();
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
                    } 
                    catch (Exception e){
                        System.out.println(e+"jaja");
                    };
                }
            }.start();
        } catch (Exception e) {
            System.out.println(e+"jajaf");
        }
    }
}
