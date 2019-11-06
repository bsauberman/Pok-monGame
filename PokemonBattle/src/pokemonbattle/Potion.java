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
public class Potion extends Item{
    private int healValue;
    
    
    public Potion(String name, int healValue, int price) {
        super(name, price);
        this.healValue = healValue;        
    }
    
    public int getHealValue() {
        return healValue;
    }
    
}
