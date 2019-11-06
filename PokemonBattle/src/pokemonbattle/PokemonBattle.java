/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.lang.InterruptedException;
/**
 *
 * @author bensauberman
 */
public class PokemonBattle extends JPanel implements ActionListener, KeyListener {
    boolean walk = false;
    URL mainURL = new URL("http://oi63.tinypic.com/2zpip89.jpg"); BufferedImage main = ImageIO.read(mainURL);
    URL openingURL = new URL ("http://oi65.tinypic.com/2wnbh4n.jpg"); BufferedImage opening = ImageIO.read(openingURL);
    URL bridgeURL = new URL ("http://oi68.tinypic.com/2vwv7nm.jpg"); BufferedImage bridge = ImageIO.read(bridgeURL);   
    URL closingURL = new URL ("http://oi67.tinypic.com/25kh05c.jpg"); BufferedImage closing = ImageIO.read(closingURL);   
    URL grassURL = new URL ("http://oi65.tinypic.com/123r060.jpg"); BufferedImage grass = ImageIO.read(grassURL);   
    URL waterURL = new URL ("http://oi68.tinypic.com/2hibfao.jpg"); BufferedImage water = ImageIO.read(waterURL);
    URL hFenceURL = new URL ("http://oi63.tinypic.com/2eao6me.jpg"); BufferedImage horizontalFence = ImageIO.read(hFenceURL); 
    URL vFenceURL = new URL ("http://oi68.tinypic.com/2j3s1sk.jpg"); BufferedImage verticalFence = ImageIO.read(vFenceURL); 
    URL sidewaysBridgeURL = new URL ("http://oi68.tinypic.com/30w9ie8.jpg"); BufferedImage sidewaysBridge = ImageIO.read(sidewaysBridgeURL);
    URL pokeCenterURL = new URL ("http://oi66.tinypic.com/ip06le.jpg"); BufferedImage pokeCenter = ImageIO.read(pokeCenterURL);    
    URL pokeMartURL = new URL ("http://oi68.tinypic.com/149vazd.jpg"); BufferedImage pokeMart = ImageIO.read(pokeMartURL);
    URL smallHorizontalFenceURL = new URL ("http://oi64.tinypic.com/30l29vs.jpg"); BufferedImage smallHorizontalFence = ImageIO.read(smallHorizontalFenceURL);   
    URL smallVerticalFenceURL = new URL ("http://oi64.tinypic.com/30l29vs.jpg"); BufferedImage smallVerticalFence = ImageIO.read(smallVerticalFenceURL);        
    
    URL mainBackWalk1URL = new URL("http://oi66.tinypic.com/2ebwl8w.jpg"); BufferedImage mainBackWalk1 = ImageIO.read(mainBackWalk1URL);
    URL mainBackWalk2URL = new URL("http://oi67.tinypic.com/96j3wm.jpg"); BufferedImage mainBackWalk2 = ImageIO.read(mainBackWalk2URL);
    URL mainFrontWalk1URL = new URL("http://oi68.tinypic.com/w0mhqb.jpg"); BufferedImage mainFrontWalk1 = ImageIO.read(mainFrontWalk1URL);
    URL mainFrontWalk2URL = new URL("http://oi67.tinypic.com/2m7vpsn.jpg"); BufferedImage mainFrontWalk2 = ImageIO.read(mainFrontWalk2URL);
    URL mainLeftWalk1URL = new URL("http://oi63.tinypic.com/34r7fgy.jpg"); BufferedImage mainLeftWalk1 = ImageIO.read(mainLeftWalk1URL);
    URL mainLeftWalk2URL = new URL("http://oi66.tinypic.com/207xffm.jpg"); BufferedImage mainLeftWalk2 = ImageIO.read(mainLeftWalk2URL);
    URL mainRightWalk1URL = new URL("http://oi68.tinypic.com/14dzsr5.jpg"); BufferedImage mainRightWalk1 = ImageIO.read(mainRightWalk1URL);
    URL mainRightWalk2URL = new URL("http://oi64.tinypic.com/2zjhv11.jpg"); BufferedImage mainRightWalk2 = ImageIO.read(mainRightWalk2URL);
    
    Pokemon[] myParty = new Pokemon[6];
    Pokemon[] oppParty = new Pokemon[6];
    ArrayList<Item> myBag = new ArrayList<Item>();
    
    int stoppedLine = 0;
    int wins = 0;
    int losses = 0;
    
    int earnings = 0;
    
    boolean shiftActivated = false;
    static int round = 1;
    static String trainerName = "";
    
    boolean hasReadFile = false; 
    boolean displayMain = true;
    
    int tD = 0;

    int x1 = (int)(bridge.getWidth()/2 + 7);
    int y1 = 720; //-180 is to skip to the end
    
    int up = -318+tD;
    final int left = 15;
    int right = bridge.getWidth() - 15;
    int down = 780;
    ArrayList<Integer> trainerLines = new ArrayList<>();
    ArrayList<Boolean> validTrainerLines = new ArrayList<>();
    ArrayList<Boolean> isWild = new ArrayList<>();
    ArrayList<Trainer> trainers = new ArrayList<>();

    public PokemonBattle() throws IOException                       // set up graphics window
    {
        super();
        readTrainerFile();        

        setBackground(Color.WHITE);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        for(int y = 1; y <= 25; y++) {
            trainerLines.add(600-(34*y) - (bridge.getHeight()/2));  
            validTrainerLines.add(true);
        }
    }
    
   public void paintComponent(Graphics g)  // draw graphics in the panel
    {
       if(y1 <= 200) {
           tD = 500;
           if(!shiftActivated) {
                for(int x = 0; x < trainerLines.size(); x++) {
                    int oldLine = trainerLines.get(x);
                    trainerLines.set(x, oldLine+tD);
                }
           }
           shiftActivated = true;                

       }
       if(y1 > 200) {
           if(shiftActivated) {
                tD = 0;
                for(int x = 0; x < trainerLines.size(); x++) {
                    int oldLine = trainerLines.get(x);
                    trainerLines.set(x, (oldLine-500));
                } 
           }
           shiftActivated = false;
       }
       
        super.paintComponent(g);            // call superclass to make panel display correctly
        g.drawImage(water, 0, 0, this);
        g.drawImage(water, 0, 400, this);
        for(int y = 721; y < 1000; y+= grass.getHeight()) {
            for(int x = 15; x < bridge.getWidth(); x += grass.getWidth()) {
                g.drawImage(grass, x, y+tD, this);
            }
        }    
        g.drawImage(verticalFence, 10, 721+tD, this);
        g.drawImage(verticalFence, 10, 750+tD, this);
        g.drawImage(verticalFence, bridge.getWidth(), 721+tD, this);
        g.drawImage(verticalFence, bridge.getWidth(), 750+tD, this);
        g.drawImage(horizontalFence, 10, 796+tD, this);



        g.drawImage(opening, 7, 650+tD, this); 
        g.drawImage(closing, 7, -318+tD, this);
        for(int y = 1; y <= 28; y++) {
            g.drawImage(bridge, 7, 708-(34*y) - bridge.getHeight() +tD, this);                    
        }
        boolean trainerIsRight = true;        
        for(int y = 1; y <= 25; y++) {        
            if(trainerIsRight) {
                 g.drawImage(trainers.get(y-1).getPic(), bridge.getWidth() - 15 + trainers.get(y-1).getAddX(), 600-(34*y) - (bridge.getHeight()/2) +tD, this);
                 trainers.get(y-1).setX(bridge.getWidth() - 15 + trainers.get(y-1).getAddX());
                 trainerIsRight = false;
            }
            else {
                 g.drawImage(trainers.get(y-1).getPic(), 15 + trainers.get(y-1).getAddX(), 600-(34*y) - (bridge.getHeight()/2) +tD, this);
                 trainers.get(y-1).setX(15 + trainers.get(y-1).getAddX());
                 trainerIsRight = true;  
            }
        }

        for(int x = 0; x < 5; x++) {
            g.drawImage(sidewaysBridge, 67+sidewaysBridge.getWidth()*x -8*x, 620 +tD, this);                                
        }
        for(int x = 0; x < 21; x++) { 
            g.drawImage(smallHorizontalFence, 69+8*x, 650+tD, this);                                           
        }
        for(int x = 0; x < 3; x++) {
            g.drawImage(smallVerticalFence, 73, 648 - 10*x + tD, this);                                                       
        }
        //g.drawImage()
        g.drawImage(pokeMart, 80, 598+tD, this);        
        g.drawImage(pokeCenter, 150, 590+tD, this);
        
        if(displayMain) {
            g.drawImage(main, x1, y1+tD, this);
        }       
    }   
    
   /*public void paintComponent(Graphics g, String s) {
       super.paintComponent(g);
       g.drawString(s, x1, y1);
   }*/
    public void readTrainerFile() throws FileNotFoundException, IOException {
        URL url = new URL("https://pastebin.com/raw/ZvfWnuW3");
        Scanner fr = new Scanner(url.openStream());
        while (fr.hasNextLine()) {
                String picDirectory = fr.next();
                Trainer trainer = new Trainer(picDirectory);
                trainers.add(trainer);
        }
    }        
    
    public void checkForBattle(int movement) throws InterruptedException, FileNotFoundException, IOException {
        z: for(int x = 0; x < trainerLines.size(); x++) {
        
            if(y1+tD+movement == trainerLines.get(x) && validTrainerLines.get(x) == true) {
                int moveCounter = 0;
                if(x % 2 == 0) {
                    while(trainers.get(x).getX()-10 > x1) {
                        trainers.get(x).addX(-1);
                        moveCounter++;
                        repaint();
                    }
                }
                if(x % 2 != 0) {
                    while(trainers.get(x).getX()+10 < x1) {
                        trainers.get(x).addX(1);
                        moveCounter++;
                        repaint();
                    }
                }
                repaint();
                initiateBattle();
                validTrainerLines.set(x, false);
                break z;
            }       
        }
    }
    
    public void initiateBattle() throws FileNotFoundException, IOException, InterruptedException {
            if(!hasReadFile) {
                readInitFile();
                hasReadFile = true;
            }
            setFocusable(false);
            removeParty(oppParty);
            System.out.println("\nROUND " + round);
            stoppedLine = readFile(myParty, oppParty, stoppedLine);
            int didWin = battle(myParty, oppParty, trainerName, myBag, true);

            resetParty(myParty);
            resetParty(oppParty);            
            if(didWin == 1) {
                reward(myParty, oppParty, myBag);
                wins++;
            } else {
                losses++;
            }
            removeParty(oppParty);
            System.out.println("\nYour Party has been healed.\n");            
            System.out.println("\nRecord: " + wins + " - " + losses);
            round++;
            calcEarnings(true, 0);
            setFocusable(true);
            System.out.println("\nMove using the arrow keys.");
    }
    
    public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(walk) {
                    main = mainLeftWalk1;
                    walk = false;                
                } else {
                    main = mainLeftWalk2;
                    walk = true;
                }
                    repaint();
                if(x1 > 72 && x1 <235 && y1 > 650 && y1 < 683) {
                    right = 235;
                    down = 677;
                    up = 650;
                } else {
                    right = bridge.getWidth() - 15;
                    down = 780;
                    up = -318+tD;  
                }
                if(x1-1 > left) {
                    x1 = x1-1;
                    repaint();
                    try {                    
                        checkForWild();
                    } catch (FileNotFoundException ex) {} catch (IOException ex) {} catch (InterruptedException ex) {}                   
                }
            }


            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(walk) {
                    main = mainRightWalk1;
                    walk = false;                
                } else {
                    main = mainRightWalk2;
                    walk = true;
                }
                if(x1 > 50 && x1 <235 && y1 > 650 && y1 < 680) {
                    right = 235;
                    down = 677;
                    up = 650;
                } else {
                    right = bridge.getWidth() - 15;
                    down = 780;
                    up = -318+tD;  
                }                
                if(x1+1 < right) {
                    x1 = x1+1;
                    repaint();
                    try {                    
                        checkForWild();
                    } catch (FileNotFoundException ex) {} catch (IOException ex) {} catch (InterruptedException ex) {}
                }
            }


            if(e.getKeyCode() == KeyEvent.VK_UP) {
                if(walk) {
                    main = mainBackWalk1;
                    walk = false;                
                } else {
                    main = mainBackWalk2;
                    walk = true;
                }
                if(x1 > 63 && x1 <235 && y1 > 650 && y1 < 683) {
                    right = 235;
                    down = 677;
                    up = 650;
                } else {
                    right = bridge.getWidth() - 15;
                    down = 780;
                    up = -318+tD;  
                }                  
                if(y1-1 > up) {
                    y1 = y1-1;
                    if(x1 >= 178 && x1 <= 186 && y1 <= 651) {
                        setFocusable(false);
                        displayMain = false;
                        repaint();
                        System.out.println("\nWelcome to the Poke Center!");                                                 
                        displayPokeCenter();
                        displayMain = true;
                        setFocusable(true);    
                        System.out.println("\nMove using the arrow keys.");                        
                    }
                    if(x1 >= 108 && x1 <= 116 && y1 <= 651) {
                        setFocusable(false);                                                
                        displayMain = false;  
                        repaint();
                        System.out.println("\nWelcome to the Poke Mart!");                         
                        displayPokeMart();
                        displayMain = true;  
                        setFocusable(true); 
                        System.out.println("\nMove using the arrow keys.");                       
                    }
                    repaint();
                    try {                    
                        checkForWild();
                    } catch (FileNotFoundException ex) {} catch (IOException ex) {} catch (InterruptedException ex) {}
                    try {   
                        checkForBattle(1);
                    } catch (InterruptedException ex) {} catch (FileNotFoundException ex) {} catch (IOException ex) {}
                } 
                if(y1 -1 < -318 + tD) {
                    System.out.println("Congratulations on winning the game! Your final record is " + wins + " - " + losses);
                    System.exit(0);
                } 
            }


            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if(walk) {
                    main = mainFrontWalk1;
                    walk = false;                
                } else {
                    main = mainFrontWalk2;
                    walk = true;
                }  
                 if(x1 > 63 && x1 <235 && y1 > 650 && y1 < 683) {
                    right = 235;
                    down = 677;
                    up = 650;
                } else {
                    right = bridge.getWidth() - 15;
                    down = 780;
                    up = -318+tD;  
                }                 
                if(y1+1 < down) {
                    y1 = y1+1;
                    repaint();
                    try {
                        checkForWild();
                    } catch (FileNotFoundException ex) {} catch (IOException ex) {} catch (InterruptedException ex) {}
                    try {   
                        checkForBattle(1);
                    } catch (InterruptedException ex) {} catch (FileNotFoundException ex) {} catch (IOException ex) {}
                }         
            }        
    }
       
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {        
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }    
    
    public void checkForWild() throws FileNotFoundException, IOException, InterruptedException {
        if(y1 >= 718 && y1 <= 1000 && x1 >= 15 && x1 <= 72) {
            int toHaveWild = (int)(100.0 * Math.random() + 1);
            if(toHaveWild == 1) {
                setUpWildPokemon();
            }        
        }
    }
    
    public void setUpWildPokemon() throws FileNotFoundException, IOException, InterruptedException {
        setFocusable(false);
        if(!hasReadFile) {
            readInitFile();
            hasReadFile = true;
        }
        int maxLevel = 0;
        for(int pSlot = 0; pSlot < 6; pSlot++) {
            if(myParty[pSlot] != null) {
                if(myParty[pSlot].getLevel() > maxLevel) {
                    maxLevel = myParty[pSlot].getLevel();
                }
            }
        }
        if(maxLevel > 50) {
            maxLevel = 50;
        }
        int level = 4 + (int)(Math.random() * ((maxLevel - 3) + 1));
        int adder = -1 + (int)(Math.random() * 3);
        level += adder;
        int HPMultiplier = 3 + (int)(Math.random() * ((5 - 3) + 1));
        int HPAdder = -5 + (int)(Math.random() * ((5 - -5) + 1));
        int HP = level * HPMultiplier + HPAdder;
        
        determineWildPokemonTier(level, HP);
        setFocusable(true);
        System.out.println("\nMove using the arrow keys.");        

    }
    
    public void determineWildPokemonTier(int level, int HP) throws FileNotFoundException, IOException, InterruptedException {
        int tier = 0;
        double randTierNumber = Math.random();
        randTierNumber  = (int)randTierNumber * 100 +1;
        
        int moves = 0;
        double randMovesNumber = Math.random();
        randMovesNumber  = (int)randMovesNumber * 100 +1; 
        
        if(level <= 10) {
            tier = 1;
            randTierNumber  = (int)randTierNumber * 100000 +1;
            if(randTierNumber == 50000) {
                tier = 4;
            }
            if(randMovesNumber <= 50) {
                moves = 1;
            }
            if(randMovesNumber > 50 && randMovesNumber <= 80) {
                moves = 2;
            }
            if(randMovesNumber > 80 && randMovesNumber <= 95) {
                moves = 3;
            }
            if(randMovesNumber > 95) {
                moves = 4;
            }            
        }
        if(level >= 11 && level <= 20) {
            if(randTierNumber <= 45) {
                tier = 1;
            }
            if(randTierNumber > 45 && randTierNumber <= 90) {
                tier = 2;
            }
            if(randTierNumber > 90) {
                tier = 3;
            }          
            randTierNumber  = (int)randTierNumber * 10000 +1;   
            if(randTierNumber == 5000) {
                tier = 4;
            }            
            if(randMovesNumber <= 10) {
                moves = 1;
            }
            if(randMovesNumber > 10 && randMovesNumber <= 45) {
                moves = 2;
            }
            if(randMovesNumber > 45 && randMovesNumber <= 85) {
                moves = 3;
            }
            if(randMovesNumber > 85) {
                moves = 4;
            }                   
        }
        if(level >= 21 && level <= 30) {
            if(randTierNumber <= 25) {
                tier = 1;
            }
            if(randTierNumber > 25 && randTierNumber <= 70) {
                tier = 2;
            }
            if(randTierNumber > 70) {
                tier = 3;
            }  
            randTierNumber  = (int)randTierNumber * 5000 +1;   
            if(randTierNumber == 2500) {
                tier = 4;
            }            
            if(randMovesNumber <= 20) {
                moves = 2;
            }
            if(randMovesNumber > 20 && randMovesNumber <= 45) {
                moves = 3;
            }
            if(randMovesNumber > 45) {
                moves = 4;
            }                   
        }
        if(level >= 31) {
            if(randTierNumber <= 25) {
                tier = 2;
            } 
            if(randTierNumber > 25) {
                tier = 3;
            } 
            randTierNumber  = (int)randTierNumber * 1000 +1;   
            if(randTierNumber == 500) {
                tier = 4;
                
            }            
            if(randMovesNumber <= 25) {
                moves = 3;
            }
            if(randMovesNumber > 25) {
                moves = 4;
            }                 
        }
        if(tier == 4) {
            level = 60;
            HP = 200;      
            moves = 4;
        }
        determineWildMovesTier(tier, moves, level, HP);
    }
    
    public void determineWildMovesTier(int tier, int moves, int level, int HP) throws FileNotFoundException, IOException, InterruptedException {
        ArrayList<Integer> wildMoves = new ArrayList<Integer>();

        if(tier == 1) {
            for(int x = 0; x < moves; x++) {
                double randMovesNumber = Math.random();
                randMovesNumber  = (int)(randMovesNumber * 100 +1); 
                
                if(randMovesNumber <= 70) {
                    wildMoves.add(1);
                }
                if(randMovesNumber > 70) {
                    wildMoves.add(2);
                }                
            }
        }
        if(tier == 2) {
            for(int x = 0; x < moves; x++) {
                double randMovesNumber = Math.random();
                randMovesNumber  = (int)(randMovesNumber * 100 +1); 
                
                if(randMovesNumber <= 30) {
                    wildMoves.add(1);
                }
                if(randMovesNumber > 30 && randMovesNumber <= 70) {
                    wildMoves.add(2);
                }  
                if(randMovesNumber > 70) {
                    wildMoves.add(3);
                }                       
            }
        } 
        if(tier == 3) {
            for(int x = 0; x < moves; x++) {
                double randMovesNumber = Math.random();
                randMovesNumber  = (int)(randMovesNumber * 100 +1); 
                
                if(randMovesNumber <= 20) {
                    wildMoves.add(1);
                }
                if(randMovesNumber > 20 && randMovesNumber <= 50) {
                    wildMoves.add(2);
                }  
                if(randMovesNumber > 50) {
                    wildMoves.add(3);
                }                       
            }
        } 
        if(tier == 4) {
            for(int x = 0; x < moves; x++) {
                wildMoves.add(3);
            }
        }
        createWildPokemon(tier, wildMoves, level, HP);
    }
    
    public void createWildPokemon(int tier, ArrayList<Integer> wildMoves, int level, int HP) throws FileNotFoundException, IOException, InterruptedException {
        boolean foundPokemon = false;
        String line = "";
        String name = "";
        ArrayList<String> types = new ArrayList<String>();
        Pokemon wildPokemon = null;
        while(!foundPokemon) {
            URL url = new URL("https://pastebin.com/raw/v1nrzpae");
            Scanner sc = new Scanner(url.openStream()).useDelimiter(",");

            int lineNumber = (int)(151 * Math.random() +1);  

            for(int i = 0; i < lineNumber; i++) {
                sc.nextLine();
            }
            line = sc.nextLine();
            line = ",".concat(line);
            Scanner sc2 = new Scanner(line).useDelimiter(",");
            if(Integer.parseInt(sc2.next()) == tier) {
                name = sc2.next();
                while(sc2.hasNext()) {
                    String type = sc2.next();
                    if(type != "/" || type != "," || type != "/,") {
                        types.add(type);
                    } else {
                        wildPokemon = new Pokemon(name, HP, level, types.get(0));
                        foundPokemon = true;
                    }
                }
                wildPokemon = new Pokemon(name, HP, level, types.get(0), tier);                
                foundPokemon = true;
            }
        }
        
        if(foundPokemon) {
            createWildMoves(tier, types, wildMoves, wildPokemon);
        }   
    }
    
    public void createWildMoves(int tier, ArrayList<String> types, ArrayList<Integer> wildMoves, Pokemon wildPokemon) throws FileNotFoundException, IOException, InterruptedException {
        for(int x = 0; x < wildMoves.size(); x++) {
            String moveType = determineWildMoveType(types);
            ArrayList< String> lines = new ArrayList<String>()  ;                 
            boolean foundMove = false;
            String line = "";
            int tryCount = 0;
            URL url = new URL("https://pastebin.com/raw/b2uwECNJ");
                Scanner sc = new Scanner(url.openStream()).useDelimiter(",");
                for(int y = 0; y < 180; y++) {
                    String s = sc.nextLine();
                    lines.add(s);
                }            
            while(!foundMove) {            

                int lineNumber = (int)(lines.size() * Math.random());  
                
                line = lines.get(lineNumber); 
                line = line.replace("\t","");
                line = ",".concat(line);    
                Scanner sc2 = new Scanner(line).useDelimiter(",");
                

                String posMoveType = sc2.next();
                if(posMoveType.equals(moveType)) {
                    int posTier = sc2.nextInt();
                    if(tryCount == 2000) {
                        posTier -= 1;
                        wildMoves.set(x, wildMoves.get(x)-1);
                        System.out.println("Recalculating...");
                    }
                    if(tryCount == 4000) {
                       posTier += 2;
                       wildMoves.set(x, wildMoves.get(x)+2); 
                    }
                    if(tryCount == 6000) {
                       moveType = determineWildMoveType(types);
                       posTier -= 1;
                       wildMoves.set(x, wildMoves.get(x)-1); 
                       tryCount = 0;
                    }                    
                    if(posTier == wildMoves.get(x)) {
                        String name = sc2.next();
                        int PP = sc2.nextInt();
                        double damageMultiplier = sc2.nextDouble();
                        int damage = (int)Math.round(damageMultiplier * wildPokemon.getLevel());
                        int accuracy = sc2.nextInt();
                        int speed = sc2.nextInt();
                        boolean sameMove = false;
                        for(int y = 0; y < wildPokemon.getMoves().length; y++) { 
                            if(wildPokemon.getMoves()[y] != null) {
                                if(name.equals(wildPokemon.getMoves()[y].getName())) {
                                    sameMove = true;
                                }    
                            }
                        }
                        if(!sameMove) {
                            Move newMove = new Move(name, PP, damage, accuracy, speed, moveType);
                            wildPokemon.addMove(newMove, x);
                            foundMove = true;
                        }
                    }    
                    tryCount++;
                }

            }
        }
        initiateWildBattle(wildPokemon);
    }
    
    public void initiateWildBattle(Pokemon wildPokemon) throws IOException, FileNotFoundException, InterruptedException {
        if(!hasReadFile) {
            readInitFile();
        }
        resetParty(oppParty);
        oppParty[0] = wildPokemon;
        
        int result = battle(myParty, oppParty, trainerName, myBag, false);
        if(result == 1) {
            calcEarnings(false, wildPokemon.getLevel());
        }
        resetParty(oppParty);
    }
    
    public static String determineWildMoveType(ArrayList<String> types) {
        int n = types.size();
        double total = 0;
        
        for(int i = 0; i < n; i++) {
            total += Math.pow(2,i);
        }
        double magicNum = 100/total;
        double ogMN = magicNum;

        int randNum = (int)(100.0 * Math.random() + 1);
        double sum = 0;
        
        for(int i = 0; i < n; i++) {
            if(randNum <= magicNum) {
                return types.get(n - i -1);
                             
            } else {
                magicNum += Math.pow(2,i+1)*ogMN;
            }
        }
        return types.get(0);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here


        PokemonBattle panel = new PokemonBattle();                            // window for drawing
        JFrame application = new JFrame();                            // the program itself
        
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // set frame to exit
                                                                      // when it is closed
        application.add(panel);           


        application.setSize(260, 1000);         // window is 500 pixels wide, 400 high
        application.setVisible(true); 
        application.setResizable(false);
        
        System.out.println("Welcome to Pokemon Simulator. Please enter your name.");
        Scanner sc = new Scanner(System.in);
        trainerName = "Trainer ".concat(sc.nextLine());
        System.out.println("Welcome " + trainerName + "!");
        System.out.println();        
        System.out.println("There are 25 opponents in the game. ");
        System.out.println("As you progress, the opponents increase in difficulty and your Pokemon gain exp and level up as you defeat enemies. ");
        System.out.println("After each opponent you defeat you get the option of either taking one of their Pokemon to add to your party");
        System.out.println("or receiving a random item. After each battle your Pokemon's moves and HP get restored. ");
        System.out.println("The goal is to win as many games as possible.");
        System.out.println("\nMove using the arrow keys.");
        
       
        
    } 
    
    public void readInitFile() throws FileNotFoundException, MalformedURLException, IOException {
        for(int x = 0; x < 99; x++) {
            Item masterball = new Ball("Master Ball", 100000, 0);
            myBag.add(masterball);
            Item pokeball = new Ball("Poke Ball", 255, 0);
            myBag.add(pokeball);

            Item greatball = new Ball("Great Ball", 200, 0);
            myBag.add(greatball);
            Item ultraball = new Ball("Ultra Ball", 150, 0);
            myBag.add(ultraball);
        }
        //File f = new File("/Users/bensauberman/Desktop/PokemonSprites/MyPokemonFile.txt");
        //Scanner fr = new Scanner(f).useDelimiter(",");
        URL url = new URL("https://pastebin.com/raw/Z3SUakes");
        Scanner fr = new Scanner(url.openStream()).useDelimiter(",");        
        while (fr.hasNextLine()) {
            String vL = fr.next();
            if(vL.equals("||")) {
                fr.nextLine();
                String info = fr.nextLine();
                addPokemon(info, myParty);
            }    
            if(vL.equals("|")) {
                fr.nextLine();
                String info = fr.nextLine();
                addMove(info, myParty);
            }
            if(vL.charAt(0) == ('/')) {
                fr.nextLine();
            }            
        }
    }
    
    public int readFile(Pokemon myParty[], Pokemon oppParty[], int line) throws FileNotFoundException, MalformedURLException, IOException {
     
        URL url = new URL("https://pastebin.com/raw/e3RWk3aN");
        Scanner fr = new Scanner(url.openStream()).useDelimiter(",");        
        int lineCounter = line;

        for(int i = 0; i < line; i++) {
            fr.nextLine();
        }
        while (fr.hasNextLine()) {
            String vL = fr.next();      
            if(vL.equals("||||")) {
                fr.nextLine();
                lineCounter+=2;
                String info = fr.nextLine();
                addPokemon(info, oppParty);
            }    
            if(vL.equals("|||")) {
                fr.nextLine();
                lineCounter+=2;
                String info = fr.nextLine();
                addMove(info, oppParty);
            }   
            if(vL.equals("|||||")) {
                lineCounter++;
                stoppedLine = lineCounter;
                return lineCounter;                
            }   
            if(vL.charAt(0) != ('|') ) {
                fr.nextLine();
                lineCounter++;
            }
        }    
            return lineCounter;                        
    }

    public static void printParty(Pokemon party[], boolean showNumbers) {
        if(showNumbers) {
            System.out.println("Pokemon            HP       Level       Type");            
        } else {
            System.out.println("Pokemon       HP       Level       Type");                        
        }
        for(int x = 0; x < 6; x++) {
            if(party[x] != null) {
                int spaceCount = 0;
                if(showNumbers) {
                    spaceCount = (16 - party[x].getName().length());
                } else {
                    spaceCount = (14 - party[x].getName().length());                    
                }
                StringBuilder spaces = new StringBuilder();
                for(int y = 0; y < spaceCount; y++) {
                    spaces.append(" ");
                }
                if(showNumbers) {
                    System.out.print(x+1 + ": " + party[x].getName() + spaces + party[x].getnHP());                    
                } else {
                    System.out.print(party[x].getName() + spaces + party[x].getnHP());                                        
                }
                if(party[x].getnHP() >= 100) {
                    System.out.print("      " + party[x].getLevel());
                } else if(party[x].getnHP() >= 10 && party[x].getnHP() <= 99) {  
                    System.out.print("       " + party[x].getLevel());
                } else {
                    System.out.print("        " + party[x].getLevel());
                } 
                if(party[x].getLevel() >= 10) {
                    System.out.print("          " + party[x].getType());
                } else {
                    System.out.print("           " + party[x].getType());
                }
                System.out.println();
            }               
        }
    }

    public void addPokemon(String info, Pokemon party[]) {
        Scanner fr = new Scanner(info).useDelimiter(",");
            String pName = fr.next();
            int pHP = fr.nextInt();
            int pLevel = fr.nextInt();
            String pType = fr.next();
            Pokemon newPokemon = new Pokemon(pName, pHP, pLevel, pType);

            for(int pSlot = 0; pSlot < 6; pSlot++) {
                if(party[pSlot] == null || party[pSlot].getName().toUpperCase().equals(pName.toUpperCase())){
                    party[pSlot] = newPokemon;
                    break;
                }         

            }
    }

    public void addMove(String info, Pokemon party[]) {
        Scanner fr = new Scanner(info).useDelimiter(",");
            String pName = fr.next();
            String mName = fr.next();
            int mPP = fr.nextInt();
            int mDamage = fr.nextInt();  
            int mAccuracy = fr.nextInt();  
            int mSpeed = fr.nextInt();  
            String mType = fr.next();

            Move newMove = new Move(mName, mPP, mDamage, mAccuracy, mSpeed, mType); 
            int pSlot = 10;
            y: for(int x = 0; x < 6; x++) {
                if(party[x]!= null) {
                    if(party[x].getName().toUpperCase().equals(pName.toUpperCase())) {
                        pSlot = x;
                        break y;
                    }                        
                }

            }        
            if(pSlot == 10) {
                throw new IllegalArgumentException("The move " + mName + " cannot be added because the "
                        + "Pokemon it belongs to is not currently in the party.");
            }                    

            boolean freeSpot = false;

            y: for(int mSlot = 0; mSlot < 4; mSlot++) {
                    if(party[pSlot].getMoves()[mSlot] == null) {
                        party[pSlot].getMoves()[mSlot] = newMove;
                        freeSpot = true;
                        break y;
                    }
                }


            //REPLACES MOVE IF ALL 4 SLOTS ARE FILLED
            if(!freeSpot) {
                System.out.println("Which move would you like to replace for " + mName);
                party[pSlot].printMoves();
                System.out.println("4: Do not add.");
                Scanner sc = new Scanner(System.in);
                int mSlot = sc.nextInt();

                if(mSlot >= 0 && mSlot <= 3) {
                    party[pSlot].addMove(newMove, mSlot);
                    System.out.println(mName + " added to " + pName);
                }
                else {
                    System.out.println(mName + " not added.");
                }
            }
    }    
    
    public int battle(Pokemon myParty[], Pokemon oppParty[], String trainerName, ArrayList<Item> myBag, boolean isTrainer) throws FileNotFoundException, IOException, InterruptedException {
        if(isTrainer) {

            System.out.println("\n\nWelcome to the battle!");

            int oppPokemonCounter = 0;
            for(int x = 0; x < 6; x++) {
                if(oppParty[x] != null) {
                    oppPokemonCounter++;
                }
            }
            System.out.println("Your opponent has " + oppPokemonCounter + " pokemon.");
        }    

        
        
        if(!isTrainer) {
            System.out.println("A wild pokemon appeared.");
            System.out.println("The pokemon is a level " + oppParty[0].getLevel() + " " + oppParty[0].getName());
        }
        
        Pokemon myPokemon = pickMyPokemon(myParty, null);
        Pokemon oppPokemon = pickOppPokemon(oppParty); 
        System.out.println();       
        
        while(checkForValidPokemon(myParty) && checkForValidPokemon(oppParty)) {
            System.out.println("\n" + trainerName + "'s Party: ");
            printParty(myParty, false);
            System.out.println("\nOpponent's Party: ");            
            printParty(oppParty, false);
            System.out.println();
            
            Move myMove = null;
            Move oppMove = null;
            
            //MY OPTIONS
            boolean validMove = false;
            while(!validMove) {
                System.out.println("\nWhat would you like to do?");            
                int userChoice = chooseOption();
                if(userChoice == 1) {
                    int mSlot = pickMyMove(myPokemon, myParty);
                        if(mSlot == -2) {
                            userChoice = 2;
                            validMove = true;
                        } 
                        else if(mSlot == -1) {
                            validMove = false;
                        } else {
                            myMove = myPokemon.getMoves()[mSlot];
                            validMove = true;                            
                        }
                }
                if(userChoice == 2) {
                    Pokemon oldPokemon = myPokemon;
                    myPokemon = pickMyPokemon(myParty, myPokemon);
                    if(oldPokemon.getName() != (myPokemon.getName())) {
                        validMove = true;
                    }
                    System.out.println("That Pokemon is already in battle.");
                    
                }
                if(userChoice == 3) {
                    if(myBag.size() > 0) {
                        boolean hasBeenUsed = chooseItem(myBag, myParty, oppParty, isTrainer);
                        if(hasBeenUsed) {
                            validMove = true;                            
                        }
                    } else {
                        System.out.println("Bag is empty. Pick another option.\n");
                    }
                }
                if(userChoice == 4) {
                    boolean didRun = run(isTrainer);
                    if(didRun) {
                        validMove = true;
                        return 2;
                    }
                }
            }
            
            //OPPONENTS OPTIONS
            if(!checkForValidPokemon(oppParty)) {
                System.out.println();
                printResult(true);
                return 1; //WIN
            }        
            
            boolean hasMadeTurn = false;
            while(!hasMadeTurn) {

                if(checkForValidMove(oppPokemon) == false) {
                    oppPokemon = pickOppPokemon(oppParty);
                    System.out.println("\n---------------------------");
                    System.out.println("The opponent sends out " + oppPokemon);
                    System.out.println("---------------------------");                    
                    hasMadeTurn = true;
                }
                
                boolean hasPickedValidMove = false;
                int randomMoveCounter = 0;
                while(!hasPickedValidMove) {
                    int randomMove = (int)(4.0 * Math.random());
                    randomMoveCounter++;
                        if(oppPokemon.getMoves()[randomMove] != null) {
                            if(oppPokemon.getMoves()[randomMove].getnPP() > 0) {
                                oppMove = oppPokemon.getMoves()[randomMove];
                                hasPickedValidMove = true;
                                hasMadeTurn = true;
                            }
                        }
                    
                    if(randomMoveCounter >= 200) {
                        oppPokemon = pickOppPokemon(oppParty);
                        System.out.println("\nThe opponent sends out " + oppPokemon);
                        hasMadeTurn = true;
                        hasPickedValidMove = true;
                    }
                }
            }
            
            int checkFaint = useMoves(myMove, oppMove, myPokemon, oppPokemon, isTrainer);
            if(checkFaint == 1) {
                oppPokemon = pickOppPokemon(oppParty);                                
            }
            if(checkFaint == 2) {
                myPokemon = pickMyPokemon(myParty, null);                
            }
        }   
        
        if(!checkForValidPokemon(myParty)) {
            System.out.println();
            printResult(false);
            return 0; //LOSE
        }
        return 1;
            

    }
    
    public static int useMoves(Move myMove, Move oppMove, Pokemon myPokemon, Pokemon oppPokemon, boolean isTrainer) throws FileNotFoundException, IOException, InterruptedException {
        int myMoveSlot = -1;
        int oppMoveSlot = -1;
        boolean checkFaint = false;
        for(int x = 0; x < 4; x++) {
            if(myMove != null) {
                if(myPokemon.getMoves()[x] != null) {
                    if(myMove.getName().toUpperCase().equals(myPokemon.getMoves()[x].getName().toUpperCase())) {
                        myMoveSlot = x;
                    }                    
                }
            }
            if(oppMove != null) {
                if(oppPokemon.getMoves()[x] != null) {
                    if(oppMove.getName().toUpperCase().equals(oppPokemon.getMoves()[x].getName().toUpperCase())) {
                        oppMoveSlot = x;
                    }                    
                }
            }            
        }
        if(myMove == null && oppMove == null) {
            return 0;
        }
        if(myMove != null && oppMove == null) {
            checkFaint = useMove(myMove, myPokemon, oppPokemon, isTrainer);           
            if(checkFaint) {
                return 0;
            } else {
                return 1;
            }
        }
        if(myMove == null && oppMove != null) {
            checkFaint = useMove(oppMove, oppPokemon, myPokemon, isTrainer);           
            if(checkFaint) {
                return 0;
            }  else {
                return 2;
            }     
        }        
        
        if(myMove != null && oppMove != null) {
            if((myMove.getSpeed() * myPokemon.getLevel()) >= (oppMove.getSpeed() * oppPokemon.getLevel())) {
                checkFaint = useMove(myMove, myPokemon, oppPokemon, isTrainer);   
                if(checkFaint) {
                    checkFaint = useMove(oppMove, oppPokemon, myPokemon, isTrainer);
                        if(checkFaint) {
                            return 0;
                        } else {
                            return 2;
                        }
                }
                else {
                    return 1;        
                }
            }
        
            if(((oppMove.getSpeed() * oppPokemon.getLevel())) > ((myMove.getSpeed()) * myPokemon.getLevel())|| oppMoveSlot == -1) {
                checkFaint = useMove(oppMove, oppPokemon, myPokemon, isTrainer);
                if(checkFaint) {
                    checkFaint = useMove(myMove, myPokemon, oppPokemon, isTrainer);
                        if(checkFaint) {
                            return 0;
                        } else {
                            return 1;
                        }
                }
                else {
                    return 2;        
                }
            }
        }
        
        return 0;
    }
    
    public static boolean useMove(Move attMove, Pokemon attPokemon, Pokemon defPokemon, boolean isTrainer) throws FileNotFoundException, IOException, InterruptedException {
        int accuracyGen = (int)(100.0 * Math.random() +1);  
        System.out.println();
            if(attMove.getAccuracy() > accuracyGen) {
                int criticalGen = (int)(100.0 * Math.random() +1);
                boolean isCritical = false;
                int criticalMultiplier = 1;
                if(criticalGen <= 5) {
                    isCritical = true;
                    criticalMultiplier = 2;                    
                }
                int HPBeforeAttack = defPokemon.getnHP();
                attMove.decnPP();
                double effectiveMultiplier = decideEffectiveness(attMove.getType(), defPokemon.getType());
                
                System.out.println("---------------------------");
                defPokemon.setnHP((int)(attMove.getDamage() * (-effectiveMultiplier) * (criticalMultiplier)));
                if(defPokemon.getnHP() <= 0) {
                    System.out.println(attPokemon.getName() + " used " + attMove.getName() + " and " + defPokemon.getName() + " fainted.");
                    if(effectiveMultiplier == 0.5) {
                        System.out.println(attMove.getName() + " was not very effective.");
                    }
                    if(effectiveMultiplier == 2.0) {
                        System.out.println(attMove.getName() + " was super effective!");
                    }
                    if(criticalMultiplier == 2){ 
                        System.out.println(attMove.getName() + " was a critical hit!");                        
                    }
                    calcEXP(attPokemon, defPokemon, isTrainer); 
                    Thread.sleep(750);
                    return false;
                }  else if (effectiveMultiplier == 0) {
                    System.out.println(attMove.getName() + " does not affect " + defPokemon.getName());                    
                }
                else {
                    System.out.println(attPokemon.getName() + " used " + attMove.getName() + " decreasing " + defPokemon.getName() + "'s HP from "
                        + HPBeforeAttack + " to " + defPokemon.getnHP()); 
                    if(effectiveMultiplier == 0.5) {
                        System.out.println(attMove.getName() + " was not very effective.");
                    }
                    if(effectiveMultiplier == 2.0) {
                        System.out.println(attMove.getName() + " was super effective!");
                    }
                    if(criticalMultiplier == 2){ 
                        System.out.println(attMove.getName() + " was a critical hit!");                        
                    }      
                    System.out.println("---------------------------");
                    Thread.sleep(750);
                    return true;
                }
            } else {
                System.out.println("---------------------------");
                System.out.println(attPokemon.getName() + " tried to use " + attMove.getName() + 
                        " but the attack missed!"); 
                System.out.println("---------------------------");
                Thread.sleep(750);                
                return true;
            }
            System.out.println("---------------------------");
            Thread.sleep(750);
            return true;
    }
    
    public static Pokemon pickMyPokemon(Pokemon myParty[], Pokemon myPokemon) {
        System.out.println();
        System.out.println();
        if(checkForValidPokemon(myParty) == false) {
            System.out.println("No more available Pokemon.");
            return null;
        }
        printParty(myParty, true);
        boolean isValid = false;        
        while(!isValid) {
            System.out.println("\nWhich Pokemon would you like to send out to battle?");
            Scanner s = new Scanner(System.in);
            System.out.println();
            printParty(myParty, true);
            String requested = s.next();
            boolean validName = false;
            for(int x = 0; x < 6; x++) {
                if(myParty[x] != null) {
                    if(myParty[x].getName().toUpperCase().equals(requested.toUpperCase()) || Integer.parseInt(requested)-1 == x) {
                        validName = true;
                        if(myParty[x].getnHP() <= 0) {
                            System.out.println(myParty[x].getName() + " has an HP of 0 and can therefore not battle. "
                                    + " Choose another Pokemon.");
                        } 
                        else if (myPokemon != null){
                            if(myPokemon.getName().toUpperCase().equals(requested.toUpperCase()) && myPokemon.getnHP() > 0) {
                                return myParty[x];
                            }
                        }
                        if(myPokemon != null || myPokemon == null) {
                            if(myParty[x].getnHP() > 0) {
                                isValid = true;
                                return myParty[x];
                            }
                        }
                    }
                }
            } 
            if(!validName) {
                System.out.println("The Pokemon entered is not in the party and therefore cannot be sent out."
                    + " Choose another Pokemon.");
            }
        }
        return null;        
    }
    
    public static Pokemon pickOppPokemon(Pokemon oppParty[]) {

        for(int x = 0; x < 6; x++) {
            if(oppParty[x] != null) {
                if(oppParty[x].getnHP() > 0) {
                    if(checkForValidMove(oppParty[x])) {
                        System.out.println("\nThe opponent sends out " + oppParty[x].getName());
                        return oppParty[x];                       
                    }
                }
            }
        }
        return null;
        
    }
    
    public static Boolean checkForValidPokemon(Pokemon party[]) {
        for(int x = 0; x < 6; x++) {
            if(party[x] != null && party[x].getnHP() > 0) {
                return true;
            }
        }
        return false;
    }
    
    public static Boolean checkForValidMove(Pokemon pokemon) {
        for(int x = 0; x < 4; x++) {
            if(pokemon.getMoves()[x] != null) {
                if(pokemon.getMoves()[x].getnPP() > 0) {
                    return true;
                }
            }
        }
        return false;        
    }
    
    public static int pickMyMove(Pokemon myPokemon, Pokemon myParty[]) {
        
        if(!checkForValidMove(myPokemon)) {
            System.out.println("This Pokemon has no PP left in any of his moves. Please change Pokemon.");
            return -2;
        }
        
        System.out.println("\nPick a move. Press '0' to Exit.");
        myPokemon.printMoves();
        
        boolean isValid = false;
        int requestedMove = -1;
        
        while(!isValid) {
            Scanner s = new Scanner(System.in);
            String requestedMoveWord = s.nextLine();
            if(requestedMoveWord.equals("0")) {
                return -1;
            }
            if(((myPokemon.getMoves()[0] != null) && (requestedMoveWord.toUpperCase().equals(myPokemon.getMoves()[0].getName().toUpperCase()))) || (requestedMoveWord.equals("1"))) {
                requestedMove = 1;
            }
            if(((myPokemon.getMoves()[1] != null) && (requestedMoveWord.toUpperCase().equals(myPokemon.getMoves()[1].getName().toUpperCase()))) || (requestedMoveWord.equals("2"))) {
                requestedMove = 2;
            }
            if(((myPokemon.getMoves()[2] != null) && (requestedMoveWord.toUpperCase().equals(myPokemon.getMoves()[2].getName().toUpperCase()))) || (requestedMoveWord.equals("3"))) {
                requestedMove = 3;
            }
            if(((myPokemon.getMoves()[3] != null) && (requestedMoveWord.toUpperCase().equals(myPokemon.getMoves()[3].getName().toUpperCase()))) || (requestedMoveWord.equals("4"))) {
                requestedMove = 4;
            }   
            
            if(requestedMove >= 1 && requestedMove <= 4) {
                if(myPokemon.getMoves()[requestedMove-1] != null) {
                    if(myPokemon.getMoves()[requestedMove-1].getnPP() > 0) {
                        return requestedMove-1;
                    } else {
                        System.out.println("That move has no PP left. Please pick another move.");
                    }
                } else {
                    System.out.println("There is no move in that slot. Please pick a valid move.");
                }
            } else {
                System.out.println("The entered number must be a valid number between 1 and 4.");
            } 
        }
        return -1;
    }
    
    public void printResult(boolean result) {
        if(result) {
            System.out.println("You win the battle!");
        }
        if(!result) {
            System.out.println("You lose the battle.");
            int ogEarnings = earnings;
            earnings = earnings/2;
            System.out.println("You have been sent to the Poke Center to reheal.\nYour Earnings have"
                    + " been cut from " + ogEarnings + " to " + earnings);
            resetParty(myParty);
            x1 = 182; y1 = 651;
            main = mainFrontWalk1;
        }        
    }
    
    public static int chooseOption() {
        System.out.println("1: Pick a move.");
        System.out.println("2: Change Pokemon."); 
        System.out.println("3: Use item."); 
        System.out.println("4: Run Away.");
        
        int userChoice = 0;
        boolean isValid = false;
        while (isValid == false) {
            Scanner s = new Scanner(System.in);
            try {
                userChoice = s.nextInt();
            } catch(InputMismatchException e) { 
                System.out.println("Please enter a valid option.");
                break;
            }
            if(userChoice < 1 || userChoice > 4) {
                System.out.println("Please enter a valid option.");
            } else {
                isValid = true;
            }
        }
        return userChoice;

    }
    
    public static int effectivenessNumber(String type) {
        switch(type) {
            case "Normal":
                return 0;
            case "Fire":
                return 1;
            case "Water":
                return 2;
            case "Electric":
                return 3;
            case "Grass":
                return 4;
            case "Ice":
                return 5;
            case "Fighting":
                return 6;
            case "Poison":
                return 7;
            case "Ground":
                return 8;
            case "Flying":
                return 9;
            case "Psychic":
                return 10;
            case "Bug":
                return 11;
            case "Rock":
                return 12;
            case "Ghost":
                return 13;
            case "Dragon":
                return 14;
            case "Dark":
                return 15;  
            case "Steel":
                return 16;               
        } 
        return -1;
    }

    public static double decideEffectiveness(String moveTypeWord, String defPokemonTypeWord) throws FileNotFoundException, MalformedURLException, IOException {
        int moveType = effectivenessNumber(moveTypeWord);
        int defPokemonType = effectivenessNumber(defPokemonTypeWord);
        
        double[][] effectivenessChart = new double[17][17];
        URL url = new URL("https://pastebin.com/raw/EGtaq20t");
        Scanner fr2 = new Scanner(url.openStream()).useDelimiter(",");   
       for(int x = 0; x < 17; x++) {
           for(int y = 0; y < 17; y++) {
               effectivenessChart[x][y] = fr2.nextDouble();
           }
           fr2.nextLine();
       }
       fr2.close();        
        
       return effectivenessChart[moveType][defPokemonType];
    }
    
    public static void calcEXP(Pokemon attPokemon, Pokemon defPokemon, boolean isTrainer) throws InterruptedException {
        int previousEXP = attPokemon.getEXP();
        int previousMaxEXP = attPokemon.getEXP();
        
        int divider = 1;
        if(!isTrainer) {
            divider = 2;
        }
        attPokemon.addEXP(((defPokemon.getLevel() * 10)+5) / divider);
        
        while(attPokemon.getEXP() >= attPokemon.getMaxEXP()) {
            attPokemon.incLevel();
            attPokemon.setMaxEXP();            
            attPokemon.setEXP(attPokemon.getEXP() - previousMaxEXP);
            Thread.sleep(1300);
            System.out.println("---------------------------");
            System.out.println(attPokemon.getName() + " has grown from Level " + (attPokemon.getLevel()-1) + " to Level " + attPokemon.getLevel());
            
            attPokemon.setrHP((int)(attPokemon.getrHP() * .05));
            Thread.sleep(1300);            
            System.out.println(attPokemon.getName() + "'s Max HP has now increased to " + attPokemon.getrHP() + "\n");            
            for(int x = 0; x < 4; x++) {
                if(attPokemon.getMoves()[x] != null) {
                    attPokemon.getMoves()[x].setDamage();
                    System.out.println(attPokemon.getName() + "'s " + attPokemon.getMoves()[x].getName() + " has increased its damage to " + attPokemon.getMoves()[x].getDamage());
                }
            }
        }        
           System.out.println();
           Thread.sleep(2000);            

           System.out.println(attPokemon.getName() + " now has " + attPokemon.getEXP() + " out of " + attPokemon.getMaxEXP() + " EXP to get from "
                   + "Level " + attPokemon.getLevel() + " to Level " + (attPokemon.getLevel()+1));
           System.out.println("---------------------------");

    }
    
    public void calcEarnings(boolean isTrainer, int level) {
        int ogEarnings = earnings;
        if(isTrainer) {
            int r = (int)(Math.random() * 30 + 1);
            earnings += r * 10 * round;
        } else {
            int r = (int)(Math.random() * 20 + 1);            
            earnings += r * level;
        }
        System.out.println("\nEarnings increased from " + ogEarnings + " to " + earnings +"\n");        
    }
    
    public static void removeParty(Pokemon party[]) {
        for(int x = 0; x < 6; x++) {
            party[x] = null;
        }
    }
    
    public static void resetParty(Pokemon party[]) {
        for(int pSlot = 0; pSlot < 6; pSlot++) {
            if(party[pSlot] != null) {
                party[pSlot].resetnHP();
                for(int mSlot = 0; mSlot < 4; mSlot++) {
                    if(party[pSlot].getMoves()[mSlot] != null) {
                        party[pSlot].getMoves()[mSlot].resetnPP();   
                    }
                }
            }
        }
    }
    
    public static void reward(Pokemon myParty[], Pokemon oppParty[], ArrayList<Item> myBag) {
        System.out.println("Congratulations on the victory!");
        System.out.println("As a reward you get the option of either taking"
                + " an opponent's Pokemon of your choosing or receiving a random item.");
        System.out.println("\nDefeated Opponent's Party:");
        printParty(oppParty, false);
        boolean rewardChosen = false;
        int userChoice = 0;        
        while(!rewardChosen) {
            System.out.println("\n1: Add/Replace Pokemon");
            System.out.println("2: Receive Random Item");

            Scanner sc = new Scanner(System.in);
            String userChoiceWord = sc.next();
            if(userChoiceWord.toUpperCase().equals("Add/Replace Pokemon".toUpperCase())) {
                userChoice = 1;
            }
            if(userChoiceWord.toUpperCase().equals("Receive Random Item".toUpperCase())) {
                userChoice = 2;
            }
            if(userChoiceWord.equals("1")) {
                userChoice = 1;
            }
            if(userChoiceWord.equals("2")) {
                userChoice = 2;
            }

        
        
            if(userChoice == 1) {
                rewardChosen = takeOppPokemon(myParty, oppParty, true);               
            }
            if(userChoice == 2) {
                rewardChosen = takeItem(myBag);
            }
        }
        return;
    }
    
    public static boolean takeOppPokemon(Pokemon myParty[], Pokemon oppParty[], boolean isTrainer) {
        boolean hasTaken = false;
        while(!hasTaken) {
        
            if(!isTrainer) {
                for(int pSlot = 0; pSlot < 6; pSlot++) {
                    if(myParty[pSlot] == null) {
                        myParty[pSlot] = oppParty[0];
                        System.out.println(oppParty[0].getName() + " has been successfully added to your party.");
                        return true;
                    } 
                }
                return replacePokemon(0, myParty, oppParty);                                  
            }
            else {     
                printParty(oppParty, true);            
                System.out.println("\nEnter the name or number of the Pokemon you would like to take.");
                Scanner sc = new Scanner(System.in);
                String desiredName = sc.next();
                for(int x = 0; x < 6; x++) {
                    if(oppParty[x] != null) {
                        if(oppParty[x].getName().toUpperCase().equals(desiredName.toUpperCase()) || Integer.parseInt(desiredName)-1 == x) {
                            for(int pSlot = 0; pSlot < 6; pSlot++) {
                                if(myParty[pSlot] == null) {
                                    myParty[pSlot] = oppParty[x];
                                    System.out.println(oppParty[x].getName() + " has been successfully added to your party.");
                                    return true;
                                } 
                                if(myParty[pSlot].getName().toUpperCase().equals(desiredName.toUpperCase()))  {
                                    System.out.println("This Pokemon already exists in the party. You cannot have more than one of the "
                                            + "same Pokemon in a party. Would you like to switch the two? (Yes or No)?");
                                    String answer = sc.next();
                                    if(answer.toUpperCase().equals("YES")) {  
                                        myParty[pSlot] = oppParty[x];
                                        return true;
                                    } 
                                    else {
                                        return false;
                                    }
                        }
                            }
                            return replacePokemon(x, myParty, oppParty);
                        } 

                    } 
                }
                System.out.println("You must enter a valid Pokemon to capture. Please repick.");
            }
        }
        return false;            
    }
    
    public static boolean replacePokemon(int x, Pokemon myParty[], Pokemon oppParty[]) {
        System.out.println("Your party is full. To add " + oppParty[x].getName() + " you must remove one of your own Pokemon.");
        System.out.println("This is your party.");
        System.out.println();
        printParty(myParty, false);
        System.out.println("\nWould you like to replace?\n1: Yes\n2: No");
        Scanner sc = new Scanner(System.in);
        String answer = sc.next();
        if(answer.toUpperCase().equals("YES") || answer.endsWith("1")) {
            System.out.println("Which Pokemon would you like to remove to make room for " + oppParty[x].getName() + "?");
            printParty(myParty, true);                            
            String desiredChange = sc.next();
            for(int y = 0; y < 6; y++) {
                if(myParty[y] != null) {
                    if(myParty[y].getName().toUpperCase().equals(desiredChange.toUpperCase())) {
                        String oldPokemonName = myParty[y].getName();
                        myParty[y] = oppParty[x];
                        System.out.println(oldPokemonName + " has been successfully switched out for " +  oppParty[x].getName());
                        return true;
                    }
                }
            }
        }
        return false;                      
    }
    
    public static boolean chooseItem(ArrayList<Item> myBag, Pokemon myParty[], Pokemon oppParty[], boolean isTrainer) throws InterruptedException {
        System.out.println();
        printBag(myBag);
        System.out.println();
        int itemID = 0;        
        boolean hasBeenUsed = false;
        boolean validNumber = false;
        while(!hasBeenUsed) {
            while(!validNumber) {
            System.out.println("Enter the number of the item you would like to use or press '0' to Exit.");
            Scanner sc = new Scanner(System.in);
            try {
                itemID = sc.nextInt();
                if(itemID >= 0 && itemID < 10) {
                    validNumber = true;
                }
            } catch(InputMismatchException e){}            
            } 
            z: switch(itemID) {
                case 0:
                    return false;
                case 1: 
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 1) {
                            hasBeenUsed = usePotion(x, myBag, myParty);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z;
                case 2:
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 2) {
                            hasBeenUsed = usePotion(x, myBag, myParty);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }                        
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z;            
                case 3:
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 3) {
                            hasBeenUsed = usePotion(x, myBag, myParty);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }                        
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z;              
                case 4:
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 4) {
                            hasBeenUsed = useRevive(x, myBag, myParty);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }                        
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z;
                case 5:
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 5) {
                            hasBeenUsed = useRevive(x, myBag, myParty);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }                        
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z; 
                case 6: 
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 6) {
                            hasBeenUsed = throwBall(x, myBag, myParty, oppParty, isTrainer);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z;
                case 7:
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 7) {
                            hasBeenUsed = throwBall(x, myBag, myParty, oppParty, isTrainer);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }                        
                        }
                    } System.out.println("There are none of this type of item. Please repick.");  validNumber = false; break z;            
                case 8:
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 8) {
                            hasBeenUsed = throwBall(x, myBag, myParty, oppParty, isTrainer);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }                        
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z;              
                case 9:
                    for(int x = 0; x < myBag.size(); x++) {
                        if(myBag.get(x).getID() == 9) {
                            hasBeenUsed = throwBall(x, myBag, myParty, oppParty, isTrainer);
                            if(hasBeenUsed) {
                                break z;
                            } else {
                                return false;
                            }                        
                        }
                    } System.out.println("There are none of this type of item. Please repick."); validNumber = false; break z; 
                 
            }    
        }
        return true;
    }
    
    public static boolean useRevive(int x, ArrayList<Item> myBag, Pokemon myParty[]) {
        boolean hasBeenUsed = true;        
        for(int pSlot = 0; pSlot < 6; pSlot++) {
            if(myParty[pSlot] != null) {
                if(myParty[pSlot].getnHP() <= 0) {
                    hasBeenUsed = false;
                }
            }
        }
        
        
        while(!hasBeenUsed) {
            System.out.println("Which Pokemon would you like to use " + myBag.get(x).getName() + " on?\n");
            printParty(myParty, true);
            Scanner sc = new Scanner(System.in);
            String pokemon = sc.next();

            for(int pSlot = 0; pSlot < 6; pSlot++) {
                if(myParty[pSlot] != null) {
                    if(myParty[pSlot].getName().toUpperCase().equals(pokemon.toUpperCase())) {
                        Revive someRevive = (Revive)myBag.get(x);
                        int oldHP = myParty[pSlot].getnHP();
                        
                        if(myParty[pSlot].getnHP() <= 0) {
                            myParty[pSlot].setnHP((int)(myParty[pSlot].getrHP()/someRevive.getRevival()));
                            System.out.println(myParty[pSlot].getName() + " has been revived and " + myParty[pSlot].getName() + "'s  HP increased from " + oldHP + 
                                " to " + myParty[pSlot].getnHP()); 
                            hasBeenUsed = true;
                            myBag.remove(x);
                            return true;
                        } else {
                            System.out.println("The Pokemon must be fainted if you wish to revive.");
                        }
                    }
                }
            }
            System.out.println("Please pick another Pokemon.");
        }
        System.out.println("All Pokemon are alive. Cannot use a revive.");
        return false;
    }
    
    public static boolean usePotion(int x, ArrayList<Item> myBag, Pokemon myParty[]) {
        
        boolean hasBeenUsed = true;        
        for(int pSlot = 0; pSlot < 6; pSlot++) {
            if(myParty[pSlot] != null) {
                if((myParty[pSlot].getnHP() < myParty[pSlot].getrHP()) && (myParty[pSlot].getnHP() != 0)) {
                    hasBeenUsed = false;
                }
            }
        }
        
        
        while(!hasBeenUsed) {
            System.out.println("Which Pokemon would you like to use " + myBag.get(x).getName() + " on?\n");
            printParty(myParty, true);
            Scanner sc = new Scanner(System.in);
            String pokemon = sc.next();

            for(int pSlot = 0; pSlot < 6; pSlot++) {
                if(myParty[pSlot] != null) {
                    if(myParty[pSlot].getName().toUpperCase().equals(pokemon.toUpperCase())) {
                        Potion somePotion = (Potion)myBag.get(x);
                        int oldHP = myParty[pSlot].getnHP();
                        myParty[pSlot].setnHP(somePotion.getHealValue());
                        System.out.println(myParty[pSlot].getName() + "'s HP increased from " + oldHP + 
                                " to " + myParty[pSlot].getnHP());
                        hasBeenUsed = true;
                        myBag.remove(x);                        
                        return true;
                    }
                }
            }
            System.out.println("Please pick another Pokemon.");
        }
        System.out.println("All Pokemon are fully healed. Cannot use a potion.");        
        return false;
        
                   
    }
    
    public static boolean throwBall(int x, ArrayList<Item> myBag, Pokemon myParty[], Pokemon oppParty[], boolean isTrainer) throws InterruptedException {
        boolean isCaught = false;
        if(isTrainer) {
            System.out.println("Cannot use ball in trainer battle.");
            return false;
        }
        //for details on algorithm see http://www.dragonflycave.com/mechanics/gen-i-capturing
        Pokemon oppPokemon = oppParty[0];
        Ball ball = (Ball) myBag.get(x); 
        int r = (int)(ball.getCatchValue() * Math.random());
        int f = oppPokemon.getrHP() * 255/12;
        if((oppPokemon.getnHP() / 4) > 0) {
            f = f / (oppPokemon.getnHP() / 4);
        }
        if(f > 255) {
            f = 255;
        }
        int r2 = (int)(255 * Math.random());
        if(r2 < f || ball.getName().equals("Master Ball")) {
            isCaught = true;
        }
        int w = ((100 * (200/oppPokemon.getTier())) / ball.getCatchValue()) * f / 255;
        if(w < 10 && !isCaught) {
            System.out.println("Oh no! The Pokemon broke free");
            myBag.remove(x);
            return true;
        } 
        System.out.println("...");
        Thread.sleep(900);        
        if(w < 30 && !isCaught) {
            System.out.println("Aww! It appeared to be caught");
            myBag.remove(x);
            return true;
        }
        System.out.println("...");
        Thread.sleep(900);
        if(w < 70 && !isCaught) {
            System.out.println("Aargh! Almost had it");
            myBag.remove(x);
            return true; 
        }
        System.out.println("...");
        Thread.sleep(900);
        if(!isCaught) {
            System.out.println("Shoot! It was so close, too!");
            myBag.remove(x);
            return true; 
        }
        else {
            System.out.println("Gotcha! " + oppPokemon.getName() + " was caught!");
            takeOppPokemon(myParty, oppParty, isTrainer);
            removeParty(oppParty);
            myBag.remove(x);
            return true; 
        }
    }    
    
    public static boolean run(boolean isTrainer) {
        if(isTrainer) {
            System.out.println("Cannot run away during trainer battle.");
            return false;
        } else {
            System.out.println("Successfully ran away.");
            return true;
        }
    }
    public static void printBag(ArrayList<Item> myBag) {
        int potionCounter = 0;
        int superPotionCounter = 0;
        int hyperPotionCounter = 0;
        int reviveCounter = 0;
        int maxReviveCounter = 0;
        int pokeBallCounter = 0;
        int greatBallCounter = 0;
        int ultraBallCounter = 0;
        int masterBallCounter = 0;
        
        for(int x = 0; x < myBag.size(); x++) {
            int ID = myBag.get(x).getID();
            switch(ID) {
                case 1: 
                    potionCounter++; break;
                case 2:
                    superPotionCounter++; break;
                case 3:
                    hyperPotionCounter++; break;
                case 4:
                    reviveCounter++; break;
                case 5:
                    maxReviveCounter++; break;
                case 6:
                    pokeBallCounter++; break;
                case 7:
                    greatBallCounter++; break;
                case 8:
                    ultraBallCounter++; break;
                case 9:
                    masterBallCounter++; break;                    
            }
        }
        
        System.out.println("1: Potions: " + potionCounter);
        System.out.println("2: Super Potions: " + superPotionCounter);
        System.out.println("3: Hyper Potions: " + hyperPotionCounter);
        System.out.println("4: Revives: " + reviveCounter);
        System.out.println("6: Poke Balls: " + pokeBallCounter);   
        System.out.println("7: Great Balls: " + greatBallCounter);       
        System.out.println("8: Ultra Balls: " + ultraBallCounter);       
        System.out.println("9: Master Balls: " + masterBallCounter);       
        
    }        
    
    public static boolean takeItem(ArrayList<Item> myBag) {
        int i = -1;
        boolean validCategory = false;
        boolean isHealer = false;
        while(!validCategory) {
            System.out.println("Would you like a ball or a healing item? Or press '0' to Exit.");
            System.out.println("1: Ball");
            System.out.println("2: Healer");
            Scanner sc = new Scanner(System.in);
            String userNumberWord = sc.next();
            try {  
                i = Integer.parseInt(userNumberWord);  
            } catch(NumberFormatException nfe) {System.out.println("Not a valid number.");}  
            if(i == 0) {
                return false;
            }
            if(i == 1) {
                validCategory = true;
            }
            if(i == 2) {
                isHealer = true;
                validCategory = true;
            }
        }
        
        i = -1;
        boolean validNumber = false; 

        while(!validNumber) {
            if(isHealer) {
                System.out.println("There are 100 boxes each containting one healing item in them. Enter the box number you would "
                    + "like to open for your free item. (1-100). Or press '0' to Exit.");
            } else {
                System.out.println("There are 100 boxes each containting one healing item in them. Enter the box number you would "
                    + "like to open for your free item. (1-100). Or press '0' to Exit.");
            }
                    Scanner sc = new Scanner(System.in);
                    String userNumberWord = sc.next();
                      try {  
                        i = Integer.parseInt(userNumberWord);  
                        if(i > 0 && i < 101) {
                            validNumber = true;                        
                        }
                        if(i == 0) {
                            return false;
                        }
                      }  
                      catch(NumberFormatException nfe) {System.out.println("Not a valid number.");}                  
        }          
        
        i = (int)(100.0 * Math.random() + 1);
      
        if(isHealer) {         
            if(i < 45) {
                Item potion = new Potion("Potion", 20, 300);
                myBag.add(potion);
                System.out.println("You won a Potion! It has been added to your bag.");
                return true;
            }
            if(i >= 45 && i < 60) {
                Item superPotion = new Potion("Super Potion", 50, 600);
                myBag.add(superPotion);
                System.out.println("You won a Super Potion! It has been added to your bag.");  
                return true;

            }
            if(i >= 60 && i < 70) {
                Item hyperPotion = new Potion("Hyper Potion", 200, 1200);
                myBag.add(hyperPotion);
                System.out.println("You won a Hyper Potion! It has been added to your bag.");
                return true;
            }     
            if(i >= 70 && i < 95) {
                Item revive = new Revive("Revive", 0.5, 1500);
                myBag.add(revive);
                System.out.println("You won a Revive! It has been added to your bag.");    
                return true;
            } 
            if(i >= 95 && i <= 100) {
                Item maxRevive = new Revive("Max Revive", 1, 3000);
                myBag.add(maxRevive);
                System.out.println("You won a Max Revive! It has been added to your bag.");  
                return true;
            } 
        } else {

            if(i < 50) {
                Item pokeBall = new Ball("Poke Ball", 255, 250);
                myBag.add(pokeBall);
                System.out.println("You won a Poke Ball! It has been added to your bag.");
                return true;
            }
            if(i >= 50 && i < 83) {
                Item greatBall = new Ball("Great Ball", 200, 500);
                myBag.add(greatBall);
                System.out.println("You won a Great Ball! It has been added to your bag.");  
                return true;
            }
            if(i >= 83 && i < 99) {
                Item ultraBall = new Ball("Ultra Ball", 150, 1000);
                myBag.add(ultraBall);
                System.out.println("You won a Ultra Ball! It has been added to your bag.");    
                return true;
            }     
            if(i >= 100) {
                Item masterBall = new Ball("Master Ball", 100000, 0);
                myBag.add(masterBall);
                System.out.println("You won a Master Ball! It has been added to your bag.");     
                return true;
            }         
        }
        return false;       
    }
    
    public void displayPokeCenter() {
        System.out.println("Would you like your Pokemon to be healed?");
        System.out.println("1: Yes");
        System.out.println("2: No");  
        Scanner s = new Scanner(System.in);
        String response = s.next();
        if(response.equals("1") || response.toUpperCase().equals("yes".toUpperCase())) {
            if(myParty[0] != null) {
                resetParty(myParty);
                System.out.println("This is now what your party looks lke.");
                printParty(myParty, false);
                System.out.println();
            } else {
                System.out.println("Your party is empty. Engage in a battle to receive your starter Pokemon.");
            }
        }
        System.out.println("Come again!");                
 
       
    }
    
    public void displayPokeMart() {
        Scanner s = new Scanner(System.in);       
        int cost = 0;
        
        String name = "";
        double itemValue = 0;
        int itemCost = 0;
        
        int number = -1;
        boolean validNumber = false;
        while(!validNumber) {
            System.out.println();
            printMartInventory();
            System.out.println("\n" + "Your Earnings: " + earnings + "\n");
            System.out.println("Enter the number of the item you would like to buy. Press '0' to Exit.");
            String numberWord = s.next();
            try {
                number = Integer.parseInt(numberWord);
                if(number == 0) {
                    System.out.println("Come again!");
                    return;
                }
                if(number >= 1 && number <= 8) {
                    validNumber = true;
                } else {
                    System.out.println("Not a valid number.");
                }
            } catch(NumberFormatException e){System.out.println("Not a valid number.");}    
        }
        int quantity = -1;
        boolean validQuantity = false;
        while(!validQuantity) {
            System.out.println("How many would you like to buy? Press '0' to Exit.");
            String quantityWord = s.next();         
            try {
                quantity = Integer.parseInt(quantityWord);
                if(quantity == 0) {
                    displayPokeMart();
                    return;
                }
                if(quantity > 0) {
                    validQuantity = true;
                }
            } catch(NumberFormatException e){} 
        }    
        
        switch (number) {
            case 1: cost = quantity * 300;  name = "Potion";       itemValue = 20;  itemCost = 300; break;
            case 2: cost = quantity * 600;  name = "Super Potion"; itemValue = 50;  itemCost = 600; break;
            case 3: cost = quantity * 1200; name = "Hyper Potion"; itemValue = 200; itemCost = 1200; break;
            case 4: cost = quantity * 1500; name = "Revive";       itemValue = 0.5; itemCost = 1500; break;
            case 5: cost = quantity * 3000; name = "Max Revive";   itemValue = 1;   itemCost = 3000; break;
            case 6: cost = quantity * 250;  name = "Poke Ball";    itemValue = 255;  itemCost = 250; break;
            case 7: cost = quantity * 500;  name = "Great Ball";   itemValue = 200;  itemCost = 500; break;
            case 8: cost = quantity * 1000; name = "Ultra Ball";   itemValue = 150;  itemCost = 1000; break;    
        }
        if(earnings >= cost) {
            if(quantity == 1) {
                System.out.println("Are you sure you would like to buy " + quantity + " " 
                    + name + " for " + cost + " Earnings?");                    
            } else {
                System.out.println("Are you sure you would like to buy " + quantity + " " 
                    + name + "s for " + cost + " Earnings?");                    
            }

            System.out.println("1: Yes");
            System.out.println("2: No");
            String answer = s.next();
            if(answer.equals("1") || answer.toUpperCase().equals("YES")) {
                for(int x = 0; x < quantity; x++) {
                    if(number >= 1 && number <= 3) {
                        Item somePotion = new Potion(name, (int)itemValue, itemCost);
                        myBag.add(somePotion);
                    }
                    if(number >= 4 && number <= 5) {
                        Item someRevive = new Revive(name, itemValue, itemCost);
                        myBag.add(someRevive);
                    }          
                    if(number >= 6 && number <= 8) {
                        Item someBall = new Ball(name, (int)itemValue, itemCost);
                        myBag.add(someBall);
                    }                                                
                }
                System.out.println("Purchase Successful!");
                validQuantity = true;
                earnings -= cost;
            } else {
                displayPokeMart();
                return;
            }

        } else {
            System.out.println("You do not have enough earnings to purchase " + quantity
                    + " " + name + "s for " + cost + ". Your Earnings: " + earnings);
            validQuantity = true;
            displayPokeMart();
            return;
        }
    
        number = -1;
        itemValue = 0;
        displayPokeMart();
        return;


    }
    
    
    public static void printMartInventory() {
        System.out.println("Number   Item            Price");     
        System.out.println("1        Potion           300");        
        System.out.println("2        Super Potion     600");        
        System.out.println("3        Hyper Potion     1200"); 
        System.out.println("4        Revive           1500");        
        System.out.println("5        Max Revive       3000");                
        System.out.println("6        Poke Ball        250");
        System.out.println("7        Great Ball       500");
        System.out.println("8        Ultra Ball       1000"); 
        
    }
     
} 