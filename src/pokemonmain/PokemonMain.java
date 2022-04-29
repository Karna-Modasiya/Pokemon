package pokemonmain;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.io.File;
import javax.sound.sampled.*;

public class PokemonMain
{
    public static void main(String[] args) throws Exception
    {
        ArrayList player = new ArrayList();
        playMusic();
        startUp(player);
    }
    
    public static void startUp(ArrayList player) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        
        while(true)
        {
            NewScreen();
            
            System.out.println("\t\t1. New Game");
            System.out.println("\t\t2. Resume");
            System.out.println("\t\t");
            int gc = sc.nextInt();
            switch(gc)
            {
                case 1:
                    register(player);
                    break;
                case 2:
                    login(player);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("\t\t> Invalid option");
                    Thread.sleep(2000);
            }
        }
    }
    
    public static void homePage(String user,ArrayList player) throws Exception
    {
        while(true)
        {
            NewScreen();
        
            System.out.println("\t\t**********  POKE-CENTER  **********");
            displayPokemon(player);
            int choice;
            Scanner s=new Scanner(System.in);
            System.out.println("\t\t1. Search wild Pokemons for Battle");
            System.out.println("\t\t2. Restore HP of your Pokemons");
            System.out.println("\t\t3. Exit Game");
            System.out.println("\t\tEnter your choice : ");
            System.out.println("\t\t");
            choice = s.nextInt();
            switch(choice)
            {
                case 1:
                    battle(player);
                    break;
                case 2:
                    restoreHP(player);
                    System.out.println("\t\t> HP of all Pokemons has been restored.");
                    Thread.sleep(2000);
                    break;
                case 3:
                    Exit(player,user);
                    break;
                default:
                    System.out.println("\t\t> Invalid option");
                    Thread.sleep(2000);
            }
        }
    }
    
    public static void displayPokemon(ArrayList player)
    {
        System.out.println("\t\t--->  Your Pokemons  <---");
        for(int i=0;i<player.size();i++)
        {
            Pokemon p=(Pokemon) player.get(i);
            System.out.println("\t\t"+ (i+1) + ". " + p.getName() + "\t hp : " + p.getHP());
        }
    }
    public static void playMusic() throws Exception
    {
        File file = new File("Interstellar Mood - Nico Staf.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    public static void VictoryMusic() throws Exception
    {
        File file = new File("Victory.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    public static void restoreHP(ArrayList player)
    {
        for(int i=0;i<player.size();i++)
        {
            Pokemon p= (Pokemon) player.get(i);
            p.resetHP();
        }
    }
    
    public static void Exit(ArrayList player,String user) throws Exception
    {
        AddToDB(player,user);
        System.out.println("\t\tExited from Game....");
        Thread.sleep(1000);
        System.exit(0);
    }
    
    public static void NewScreen() throws IOException, InterruptedException
    {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("\n\n\n\n\t\t\t\t*********  WELCOME TO POKEVERSE  *********\n\n\n\n\n");
    }
    public static void newIntro(ArrayList player) throws Exception
    {
        int pc;
        do
        {
            NewScreen();
        
            Scanner sc = new Scanner(System.in);
        
            Charmander pCharmander = null;
            Squirtle pSquirtle = null;
            Bulbasaur pBulbasaur = null;
        
            System.out.println("\t\t#-- Hello! Pokemon trainer welcome to the world of pokemons. --#");
            System.out.println("\t\t#-- Trainer you have 3 choices of pokemon you can choose any one for starting your journey. --#");
            System.out.println("\t\t1. Charmander");
            System.out.println("\t\t2. Squirtle");
            System.out.println("\t\t3. Bulbasaur");
            System.out.println("\t\t");
            pc = sc.nextInt();
            switch(pc)
            {
                case 1:
                    pCharmander = new Charmander();
                    pCharmander.playsong();
                    player.add(pCharmander);
                    System.out.println("\t\tYou have obtained your first Pokemon : Charmander");
                    break;
                case 2:
                    pSquirtle = new Squirtle();
                    pSquirtle.playsong();
                    player.add(pSquirtle);
                    System.out.println("\t\tYou have obtained your first Pokemon : Squirtle");
                    break;
                case 3:
                    pBulbasaur = new Bulbasaur();
                    pBulbasaur.playsong();
                    player.add(pBulbasaur);
                    System.out.println("\t\tYou have obtained your first Pokemon : Bulbasaur");
                    break;
                default:
                    System.out.println("\t\t> Invalid choice");
                    Thread.sleep(2000);
            }
        }while(pc!=1 && pc!=2 && pc!=3);
    }
    
    public static Connection getConnection() throws Exception
    {
        try
        {
            String driver="com.mysql.cj.jdbc.Driver";
            String url="jdbc:mysql://localhost:3306/pokemongame";
            String username="Karan";
            String password="pass";
            
            Class.forName(driver);
            
            Connection con = DriverManager.getConnection(url,username,password);
            
            return con;
        }
        catch(Exception e)
        {
            System.out.println("\t\t> Connection failed!");
        }
        return null;
    }
    
    public static void register(ArrayList player) throws Exception
    {
        String name,user,pass,cpass;
        Scanner sc = new Scanner(System.in);
        
        do
        {
            NewScreen();
            
            System.out.print("\n\t> Name : ");
            name = sc.next();
            System.out.print("\n\t> Username : ");
            user = sc.next();
            System.out.print("\n\t> Password : ");
            pass = sc.next();
            System.out.print("\n\t> Confirm Password : ");
            cpass = sc.next();
            if(!pass.equals(cpass))
            {
                System.out.println("\t\tPassword mismatch !");
                System.out.println("\t\tPlease re-enter details.");
                Thread.sleep(2000);
            }
        }while(!pass.equals(cpass));
        if(pass.equals(cpass))
        {
            try
            {
                Connection con = getConnection();
                PreparedStatement reg = con.prepareStatement("INSERT INTO user_acc (Name, Username, Password) VALUES ('"+name+"','"+user+"','"+pass+"')");
                reg.executeUpdate();
                System.out.println("\t\t> Registered");
                newIntro(player);
                homePage(user,player);
            }
            catch(Exception e)
            {
                System.out.println("\t\t# Registration Error");
                System.out.println(e);
            }
        }
        Thread.sleep(1000);
        newIntro(player);
    }
    
    public static void login(ArrayList player) throws Exception
    {
        String ruser;
        String rpass;
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\t> Username :");
        String user = sc.next();
        System.out.print("\n\t> Password : ");
        String pass = sc.next();
        try
        {
            Connection con = getConnection();
            PreparedStatement log = con.prepareStatement("SELECT Username,Password FROM user_acc");
            ResultSet res = log.executeQuery();
            while(res.next())
            {
                ruser = res.getString("Username");
                rpass = res.getString("Password");
                if(user.equals(ruser) && pass.equals(rpass))
                {
                    System.out.println("Logged In");
                    getPokemon(user,player);
                    homePage(user,player);
                    break;
                }
            }
            System.out.println("\t\t> Invalid Username or Password.");
        }
        catch(Exception e){}
    }
    
    public static void getPokemon(String user,ArrayList player)
    {
        String pokemon;
        int health,Lv;
        try
        {
            Connection con = getConnection();
            PreparedStatement log = con.prepareStatement("SELECT Pokemon_name,hp,Level FROM pokemon WHERE username = '"+user+"' ");
            ResultSet res = log.executeQuery();
            while(res.next())
            {
                pokemon = res.getString("Pokemon_name");
                health = res.getInt("hp");
                Lv = res.getInt("Level");
                if(pokemon.equals("Charmander"))
                {
                    Charmander pCharmander = new Charmander();
                    pCharmander.setHP(health);
                    pCharmander.SetLevel(Lv);
                    player.add(pCharmander);
                    System.out.println(pCharmander.getName());
                }
                if(pokemon.equals("Squirtle"))
                {
                    Squirtle pSquirtle = new Squirtle();
                    pSquirtle.setHP(health);
                    pSquirtle.SetLevel(Lv);
                    player.add(pSquirtle);
                    System.out.println(pSquirtle.getName());
                }
                if(pokemon.equals("Bulbasaur"))
                {
                    Bulbasaur pBulbasaur = new Bulbasaur();
                    pBulbasaur.setHP(health);
                    pBulbasaur.SetLevel(Lv);
                    player.add(pBulbasaur);
                    System.out.println(pBulbasaur.getName());
                }
                if(!pokemon.equals("Charmander") && !pokemon.equals("Squirtle") && !pokemon.equals("Bulbasaur"))
                {
                    System.out.println("\t\t> Currently you have not any Pokemon");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("\t\t> Error Occured");
            System.out.println(e);
        }
    }
    
    public static void battle(ArrayList player) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        ArrayList enemy = new ArrayList();
        
        Charmander charmander = new Charmander();
        Squirtle squirtle = new Squirtle();
        Bulbasaur bulbasaur = new Bulbasaur();
        
        enemy.add(charmander);
        enemy.add(squirtle);
        enemy.add(bulbasaur);
        
        while(true)
        {
            int lowhp;
            int ec = r.nextInt(25)%3;
            Pokemon pokemon = (Pokemon) enemy.get(ec);
            
            NewScreen();
            pokemon.playsong();
            System.out.println("\t\tSearching for wild Pokemons...");
            Thread.sleep(2000);
            System.out.println("\t\t#-- "+ pokemon.getName() +" has appeared ! --#");
            System.out.println("\t\t#-- Type : " + pokemon.getType() + " --#");
            System.out.println("\t\t#-- HP : " + pokemon.getHP() + " --#");
            System.out.println("\t\t1. Fight");
            System.out.println("\t\t2. Leave");
            System.out.println("\t\t3. Return to Poke-Center");
            System.out.println("\t\t");
            int bc = sc.nextInt();
            switch(bc)
            {
                case 1:
                    lowhp = fight(player,pokemon);
                    if(lowhp == 1)
                        return;
                    break;
                case 2:
                    System.out.println("\t\t#-- You chose not to fight... --#");
                    Thread.sleep(2000);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("\t\t> Invalid choice");
                    Thread.sleep(1000);
            }
        }
    }
    
    public static int fight(ArrayList player,Pokemon pokemon) throws Exception
    {
        int rattack,pc,caught;
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        Pokemon plpokemon,p;
        
        while(true)
        {
            NewScreen();
            pokemon.playsong();
            System.out.println("\t\t#-- "+ pokemon.getName() +" has appeared ! Type : " + pokemon.getType() + " --#");
            System.out.println("\t\t#-- Type : " + pokemon.getType() + " --#");
            System.out.println("\t\t#-- HP : " + pokemon.getHP() + " --#");
            System.out.println("\t\t#-- Select your pokemon : --#");
            for(int i=0;i<player.size();i++)
            {
                p = (Pokemon) player.get(i);
                System.out.println("\t\t"+ (i+1) + ". " + p.getName() + " ( HP : " + p.getHP() + " )");
            }
            System.out.println("\t\t");
            pc = sc.nextInt();
            if(pc>player.size())
            {
                System.out.println("\t\t> Invalid choice");
                Thread.sleep(2000);
            }
            else
            {
                plpokemon = (Pokemon) player.get(pc-1);
                if(plpokemon.getHP() <= 0)
                {
                    System.out.println("\t\t> Your Pokemon cannot battle, please go back to Poke-Center to heal your Pokemon.");
                    Thread.sleep(2000);
                    return 1;
                }
                break;
            }
        }
        System.out.println("\t\t#-- " + plpokemon.getName() + ", I choose you ! --#");
        plpokemon.playsong();
        while(true)
        {
            NewScreen();
            System.out.println("\n\t\t#-- Chance for Your Attack --#");
            System.out.println("\t\t> Health of Enemy before attack:-"+pokemon.getHP());
            plpokemon.selectAttack(pokemon);
            System.out.println("\t\t> Health of Enemy after attack:-"+pokemon.getHP()+"\n");
            pokemon.getHP();
            if(pokemon.getHP() == 0)
            {
                caught = 0;
                System.out.println("\t\tYou have defeated " + pokemon.getName());
                VictoryMusic();
                plpokemon.Levelup();
                for(int i =0;i<player.size();i++)
                {
                    p = (Pokemon) player.get(i);
                    if(pokemon.getName().equals(p.getName()))
                    {
                        caught++;
                        break;
                    }
                }
                if(caught == 0)
                {
                    if(pokemon.getName().equals("Charmander"))
                    {
                        Charmander pCharmander = new Charmander();
                        player.add(pCharmander);
                    }
                    else if(pokemon.getName().equals("Squirtle"))
                    {
                        Squirtle pSquirtle = new Squirtle();
                        player.add(pSquirtle);
                    }
                    else
                    {
                        Bulbasaur pBulbasaur = new Bulbasaur();
                        player.add(pBulbasaur);
                    }
                }
                pokemon.resetHP();
                pokemon.resetAttack();
                pokemon.resetDefence();
                plpokemon.resetAttack();
                plpokemon.resetDefence();
                Thread.sleep(4000);
                return 0;
            }
            rattack = r.nextInt(2)+1;
            System.out.println("\n\t\t#-- Chance for Enemies Attack --#");
            System.out.println("\t\t> Health of Player Before attack:-"+plpokemon.getHP());
            pokemon.selectAttack(rattack, plpokemon);
            System.out.println("\t\t> Health of Player After attack:-"+plpokemon.getHP()+"\n");
            if(plpokemon.getHP() == 0)
            {
                System.out.println("\t\tYour pokemon " + plpokemon.getName() + " has been defeated by " + pokemon.getName());                
                pokemon.resetHP();
                pokemon.resetAttack();
                pokemon.resetDefence();
                plpokemon.resetAttack();
                plpokemon.resetDefence();
                Thread.sleep(6000);
                return 0;
            }
            Thread.sleep(2000);
        }
    }
    
    public static void AddToDB(ArrayList player,String user)
    {
        RmFromDB(user);
        try
        {
            Connection con = getConnection();
            for(int i=0;i<player.size();i++)
            {
                Pokemon pokemon = (Pokemon) player.get(i);
                PreparedStatement reg = con.prepareStatement("INSERT INTO pokemon (username,Pokemon_name,hp,Level) VALUES ('"+user+"','"+pokemon.getName()+"','"+pokemon.getHP()+"','"+pokemon.GetLevel()+"')");
                reg.executeUpdate();
            }
            System.out.println("\t\tPROGRESS STORED....");
        }
        catch(Exception e)
        {
            System.out.println("\t\tError occured");
            System.out.println(e);
        }
    }
    
    public static void RmFromDB(String user)
    {
        try
        {
            Connection con = getConnection();
            PreparedStatement reg = con.prepareStatement("DELETE FROM pokemon WHERE username = '"+user+"'");
            reg.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println("Error occured");
            System.out.println(e);
        }
    }
}