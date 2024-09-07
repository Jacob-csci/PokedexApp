import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class PokedexAppGui extends JFrame {

    private JTextField searchTextField;
    private JLabel pokemonImage;
    private JLabel pokemonText;
    private JLabel pokemonId;
    private JLabel pokemonType;
    private JLabel pokemonType2;
    private JLabel firstType;
    private JLabel secondType;

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
        searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 251, 47);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        JButton searchButton = new JButton(loadImageFromFile("C:\\Users\\jacob\\PokedexApp\\src\\assets\\search.png"));
        searchButton.setBounds(275, 15, 53, 47);
        add(searchButton);

        pokemonImage = new JLabel(loadImageFromFile("src/assets/pikachu.png"));
        pokemonImage.setBounds(-45, 75, 450, 250);
        add(pokemonImage);

        pokemonText = new JLabel("Pikachu");
        pokemonText.setBounds(-50, 250, 450, 54);
        pokemonText.setFont(new Font("Dialog", Font.BOLD, 48));
        pokemonText.setHorizontalAlignment(SwingConstants.CENTER);
        add(pokemonText);

        pokemonId = new JLabel("25");
        pokemonId.setBounds(150, 300, 450, 100);
        pokemonId.setFont(new Font("Dialog", Font.BOLD, 48));
        add(pokemonId);
        
        firstType = new JLabel("1st Type");
        firstType.setBounds(50, 375, 450, 100);
        firstType.setFont(new Font("Dialog", Font.BOLD, 20));
        add(firstType);

        secondType = new JLabel("2nd Type");
        secondType.setBounds(200, 375, 450, 100);
        secondType.setFont(new Font("Dialog", Font.BOLD, 20));
        add(secondType);

        pokemonType = new JLabel("Electric");
        pokemonType.setBounds(50, 400, 450, 100);
        pokemonType.setFont(new Font("Dialog", Font.BOLD, 40));
        add(pokemonType);

        pokemonType2 = new JLabel("None");
        pokemonType2.setBounds(200, 400, 450, 100);
        pokemonType2.setFont(new Font("Dialog", Font.BOLD, 40));
        add(pokemonType2);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pokemonName = searchTextField.getText().trim().toLowerCase();
                if (!pokemonName.isEmpty()) {
                    JSONObject pokemon = PokedexApp.getPokemonName(pokemonName);
                    if (pokemon != null) {
                        updatePokemonInfo(pokemon);
                    } else {
                        JOptionPane.showMessageDialog(null, "Pokemon not found!");
                    }
                }
            }
        });
    }

    private void updatePokemonInfo(JSONObject pokemon) {
        String name = (String) pokemon.get("name");
        long id = (long) pokemon.get("id");
  
        // Update the text
        pokemonText.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        pokemonId.setText(String.valueOf(id));
        // Get the sprite URL from the JSON
        JSONObject sprites = (JSONObject) pokemon.get("sprites");
        String spriteUrl = (String) sprites.get("front_default");

        // Load the image from the URL
        pokemonImage.setIcon(loadImageFromUrl(spriteUrl));


        JSONArray types = (JSONArray) pokemon.get("types");
        JSONObject type = (JSONObject) ((JSONObject) types.get(0)).get("type");
        String typeName = (String) type.get("name");

        
       
        pokemonType.setText(typeName.substring(0, 1).toUpperCase() + typeName.substring(1));
        if(types.size() > 1){
            JSONObject type2 = (JSONObject) ((JSONObject) types.get(1)).get("type");
            String typeName2 = (String) type2.get("name");
            pokemonType2.setText(typeName2.substring(0, 1).toUpperCase() + typeName2.substring(1));
        } else {
            pokemonType2.setText("None");
        }


        }


    private ImageIcon loadImageFromFile(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not find resource: " + resourcePath);
        return null;
    }

    private ImageIcon loadImageFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedImage image = ImageIO.read(url);
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not load image from URL: " + urlString);
        return null;
    }
}

