/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author bensauberman
 */
public class Trainer {
    private BufferedImage pic;
    private int x = 0;
    private int addX = 0;
    public Trainer(String picDirectory) throws IOException {
        URL openingURL = new URL (picDirectory);
        pic = ImageIO.read(openingURL);
    }
    
    public void setPic(String newDirectory) throws IOException { 
        URL openingURL = new URL (newDirectory);
        pic = ImageIO.read(openingURL);    
    }
    
    public BufferedImage getPic() {
        return pic;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void addX(int x) {
        this.x += x;
        this.addX += x;
    }    
    public int getAddX() {
        return addX;
    }
    public int getX() {
        return x;
    }
}
