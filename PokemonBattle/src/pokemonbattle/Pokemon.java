/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

/**
 *
 * @author bensauberman
 */
public class Pokemon {
    private String name;
    private int nHP;
    private int rHP;    
    private int level;
    private int EXP;
    private int maxEXP;
    private String type; 
    Move[] myMoves = new Move[4];
    private int tier;
    
    public Pokemon(String name, int HP, int level, String type) {
        this.name = name;
        this.rHP = HP;
        this.nHP = HP;
        this.level = level;
        this.EXP = 0;
        maxEXP = (level * 10);
        this.type = type;
    }
    public Pokemon(String name, int HP, int level, String type, int tier) {
        this.name = name;
        this.rHP = HP;
        this.nHP = HP;
        this.level = level;
        this.EXP = 0;
        maxEXP = (level * 10);
        this.type = type;
        this.tier = tier;
    }
                
    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public int getnHP() {
        return nHP;
    }

    public int getrHP() {
        return rHP;
    }

    public void setnHP(int HPchanger) { //MAKE SURE HPCHANGER IS NEGATIVE IF YOU WANT TO DECREASE HP
        nHP += HPchanger;
        if(nHP <= 0) {
            nHP = 0;
        }
        if(nHP > rHP) {
            nHP = rHP;
        }
    }
 
    public void resetnHP() { 
        nHP = rHP;
    }
    
    public void setrHP(int HPchanger) { //MAKE SURE HPCHANGER IS NEGATIVE IF YOU WANT TO DECREASE HP
        rHP += HPchanger;
        if(rHP <= 0) {
            rHP = 0;
            nHP = 0;
        }
    }    

    public int getLevel() {
        return level;
    }       
  
    public int getEXP() {
        return EXP;
    }  
    
    public int getMaxEXP() {
        return maxEXP;
    }        
    public void incLevel() {
        level++;                
    }
    
    public void addEXP(int additionalEXP) {
        EXP += additionalEXP;
    }
 
    public void setEXP(int newEXP) {
        EXP = newEXP;
    }
    public void setMaxEXP() {
        maxEXP = level * 20;
    }
    
    public void addMove(Move m, int slot) {
        myMoves[slot] = m;
    }
    
    public Move[] getMoves() {
        return myMoves;
    }
    
    public int getTier() {
        return tier;
    }
    public void printMoves() {

        System.out.println("   Name           Damage    Accuracy    PP    Speed    Type");
        for(int move = 0; move < 4; move++) {
            if(myMoves[move] != null) {
                int spaceCount = (12 - myMoves[move].getName().length());
                StringBuilder spaces = new StringBuilder();
                for(int y = 0; y < spaceCount; y++) {
                    spaces.append(" ");
                }                   
                    System.out.print(move+1 + ": " + myMoves[move].getName() + spaces + "     " + myMoves[move].getDamage());
                    if(myMoves[move].getDamage() < 10) {
                        System.out.print("         " + myMoves[move].getAccuracy() + "        " + + myMoves[move].getnPP());
                    } else if(myMoves[move].getDamage() < 100){
                        System.out.print("        " + myMoves[move].getAccuracy() + "        " + myMoves[move].getnPP());
                    } else {
                        System.out.print("       " + myMoves[move].getAccuracy() + "        " + myMoves[move].getnPP());
                    }
                    if(myMoves[move].getnPP() < 10) {
                        System.out.print("      " + myMoves[move].getSpeed() + "       " + myMoves[move].getType());
                        System.out.println();
                    } else {
                        System.out.print("     " + myMoves[move].getSpeed() + "      " + myMoves[move].getType());
                        System.out.println();                        
                    }
            }   else {
                System.out.println(move+1 + ": ---");
            }
        }
    }
}
