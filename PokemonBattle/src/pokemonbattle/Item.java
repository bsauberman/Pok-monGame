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
public abstract class Item {
    private String name;
    private int ID;
    private int price;
    
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
        switch(name) {
            case "Potion":
                this.ID = 1; break;
            case "Super Potion":
                this.ID = 2; break;
            case "Hyper Potion":
                this.ID = 3; break;
            case "Revive":
                this.ID = 4; break;
            case "Max Revive":
                this.ID = 5; break;
            case "Poke Ball":
                this.ID = 6; break;
            case "Great Ball":   
                this.ID = 7; break;
            case "Ultra Ball":   
                this.ID = 8; break;
            case "Master Ball":   
                this.ID = 9; break;                
        }
    }
    
    public String getName() { 
        return name;
    }
    
    public int getID() { 
        return ID;
    }
    
    public int getPrice() { 
        return price;
    }
    
}

