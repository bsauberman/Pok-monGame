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
public class Move {
    private String name;
    private int damage;
    private int accuracy;
    private int rPP;  
    //r stands for regular. This is the attribute before affected by the battle.
    //n stands for new. This is the attribute affected midbattle.
    private int nPP;    
    private int speed;
    private String type;

    
    
    public Move(String name, int PP, int damage, int accuracy, int speed, String type) {
        this.name = name;
        this.rPP = PP; this.nPP = PP;
        this.damage = damage; 
        this.accuracy = accuracy;
        this.speed = speed;
        this.type = type;
    }
    
    public String getName() {
        return name;               
    }
    
    public int getrPP() {
        return rPP;               
    }  
    public int getnPP() {
        return nPP;               
    }    
    public void decnPP() {
        nPP--;
    }
    
    public void resetnPP() {
        nPP = rPP;
    }
    
    
    
    public int getDamage() {
        return damage;               
    }          
    public void setDamage() {
        damage += damage * .1;
    }

    public int getAccuracy() {
        return accuracy;               
    }    

    
    public int getSpeed() {
        return speed;               
    }  
    
    public String getType() {
        return type;
    }

}
