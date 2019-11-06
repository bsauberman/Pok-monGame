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
public class Revive extends Item {
    private double revival;
       
    public Revive(String name, double revival, int price) {
        super(name, price);
        this.revival = revival;        
    }
    
    public double getRevival() {
        return revival;
    }    
}
