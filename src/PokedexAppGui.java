import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import org.json.simple.JSONObject;

public class PokedexAppGui extends JFrame{

    private JSONObject pokeData;

    public PokedexAppGui() {
        super("Pokedex");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(350, 550);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JTextField searchTextField = new JTextField();

        searchTextField.setBounds(15, 15, 251, 35);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        //default pokemon

        JLabel pokemonImage = new JLabel(loadImage("src/assets/pikachu.png"));
        
        pokemonImage.setBounds(-45, 125, 450, 250);

        add(pokemonImage);


    }

    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image = ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}
