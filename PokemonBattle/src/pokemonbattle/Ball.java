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
public class Ball extends Item {
    private int catchValue;
    
    public Ball(String name, int catchValue, int price) {
        super(name, price);
        this.catchValue = catchValue;    
    }
    
    public int getCatchValue() {
        return catchValue;
    }

}
