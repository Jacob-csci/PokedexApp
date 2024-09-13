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
    private JLabel height;
    private JLabel weight;
    private JLabel hp;
    private JLabel attack;
    private JLabel defense;
    private JLabel specAtt;
    private JLabel specDef;
    private JLabel speed;
    private JLabel ability;
    private JLabel hiddenAbility;

    public PokedexAppGui() {
        super("Pokedex");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 800);
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

        JButton shinyButton = new JButton("Shiny");
        shinyButton.setBounds(225, 200, 100, 30);
        add(shinyButton);

        pokemonImage = new JLabel(loadImageFromFile("src/assets/pikachu.png"));
        pokemonImage.setBounds(-45, 75, 450, 250);
        add(pokemonImage);

        height = new JLabel("Height: 4");
        height.setBounds(50, 175, 100, 54);
        height.setFont(new Font("Dialog", Font.BOLD, 15));
        add(height);

        weight = new JLabel("Weight: 60");
        weight.setBounds(50, 200, 100, 54);
        weight.setFont(new Font("Dialog", Font.BOLD, 15));
        add(weight);

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

        hp = new JLabel("HP: 60");
        hp.setBounds(50, 500, 100, 54);
        hp.setFont(new Font("Dialog", Font.BOLD, 15));
        add(hp);

        attack = new JLabel("Attack: 60");
        attack.setBounds(50, 550, 100, 54);
        attack.setFont(new Font("Dialog", Font.BOLD, 15));
        add(attack);

        defense = new JLabel("Defense: 60");
        defense.setBounds(50, 600, 100, 54);
        defense.setFont(new Font("Dialog", Font.BOLD, 15));
        add(defense);

        specAtt = new JLabel("SpecAtt: 60");
        specAtt.setBounds(200, 500, 100, 54);
        specAtt.setFont(new Font("Dialog", Font.BOLD, 15));
        add(specAtt);

        specDef = new JLabel("SpecDef: 60");
        specDef.setBounds(200, 550, 100, 54);
        specDef.setFont(new Font("Dialog", Font.BOLD, 15));
        add(specDef);

        speed = new JLabel("Speed: 60");
        speed.setBounds(200, 600, 100, 54);
        speed.setFont(new Font("Dialog", Font.BOLD, 15));
        add(speed);

        ability = new JLabel("<html> Abilities: <br> None </html>");
        ability.setBounds(125, 675, 100, 54);
        ability.setFont(new Font("Dialog", Font.BOLD, 15));
        add(ability);

        hiddenAbility = new JLabel("None");
        hiddenAbility.setBounds(125, 700, 100, 54);
        hiddenAbility.setFont(new Font("Dialog", Font.BOLD, 15));
        add(hiddenAbility);

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

        shinyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                updatePokemonSprite();

            }
        });

    }

    private JSONObject currentPokemon;

    private void updatePokemonInfo(JSONObject pokemon) {
        currentPokemon = pokemon;
        String name = (String) pokemon.get("name");
        long id = (long) pokemon.get("id");
  
        // Update the text
        pokemonText.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        pokemonId.setText(String.valueOf(id));

        long pokemonWeight = (long) pokemon.get("weight");
        long pokemonHeight = (long) pokemon.get("height");

        weight.setText("Weight: " + String.valueOf(pokemonWeight));
        height.setText("Height: " + String.valueOf(pokemonHeight));


        // Get the sprite URL from the JSON
        JSONObject sprites = (JSONObject) pokemon.get("sprites");
        String spriteUrl = (String) sprites.get("front_default");
        // Load the image from the URL
        pokemonImage.setIcon(loadImageFromUrl(spriteUrl));
        

        JSONArray types = (JSONArray) pokemon.get("types");
        JSONObject type = (JSONObject) ((JSONObject) types.get(0)).get("type");
        String typeName = (String) type.get("name");

        JSONArray stats = (JSONArray) pokemon.get("stats");
        long baseHp = (long) ((JSONObject) stats.get(0)).get("base_stat");
        long baseAtt = (long) ((JSONObject) stats.get(1)).get("base_stat");
        long baseDef = (long) ((JSONObject) stats.get(2)).get("base_stat");
        long baseSpecAtt = (long) ((JSONObject) stats.get(3)).get("base_stat");
        long baseSpecDef = (long) ((JSONObject) stats.get(4)).get("base_stat");
        long baseSpd = (long) ((JSONObject) stats.get(5)).get("base_stat");

        hp.setText("HP: " + String.valueOf(baseHp));
        attack.setText("Attack: " + String.valueOf(baseAtt));
        defense.setText("Defense: " + String.valueOf(baseDef));
        specAtt.setText("SpecAtt: " + String.valueOf(baseSpecAtt));
        specDef.setText("SpecDef: " + String.valueOf(baseSpecDef));
        speed.setText("Speed: " + String.valueOf(baseSpd));

        JSONArray abilities = (JSONArray) pokemon.get("abilities");
        JSONObject ability1 = (JSONObject) ((JSONObject) abilities.get(0)).get("ability");
        

        String abil = (String) ability1.get("name");
        

        ability.setText("<html> Abilities: <br>" + abil + "</html>");
        if(abilities.size() > 1){
            JSONObject ability2 = (JSONObject) ((JSONObject) abilities.get(1)).get("ability");
            String abil2 = (String) ability2.get("name");
            hiddenAbility.setText(abil2);
        } else {
            hiddenAbility.setText("None");
        }
        pokemonType.setText(typeName.substring(0, 1).toUpperCase() + typeName.substring(1));
        if(types.size() > 1){
            JSONObject type2 = (JSONObject) ((JSONObject) types.get(1)).get("type");
            String typeName2 = (String) type2.get("name");
            pokemonType2.setText(typeName2.substring(0, 1).toUpperCase() + typeName2.substring(1));
        } else {
            pokemonType2.setText("None");
        }


        }



    private void updatePokemonSprite() {

            if(currentPokemon != null){
                JSONObject sprites = (JSONObject) currentPokemon.get("sprites");
                String shinySpriteUrl = (String) sprites.get("front_shiny");
                pokemonImage.setIcon(loadImageFromUrl(shinySpriteUrl));
            } 

    }

    private void pokemonStats() {

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

