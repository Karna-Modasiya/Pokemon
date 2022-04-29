package pokemonmain;
import java.io.File;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class Bulbasaur implements Pokemon
{
    private String type = "Grass";
    private String name = "Bulbasaur"; 
    private int hp = 45;
    private int Level;
    private int maxhp = 45;
    private int def = 49;
    private int maxdef = 45;
    private int tackleDamage = 40;
    private int maxTackleDamage = 40;
    public void playsong() throws Exception
    {
        File file = new File("Bulbasaur.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    public String getName()
    {
        return name;
    }
    public void SetName(String s)
    {
        this.name = s;
    }
    public String getType()
    {
        return type;
    }
    public void setHP(int hp)
    {
        this.hp = hp;
    }
    public int getHP()
    {
        return hp;
    }
    public void resetHP()
    {
        hp = maxhp;
    }
    public void SetMaxhp(int hp)
    {
        this.maxhp=hp;
    }
    public int GetMaxhp()
    {
        int hp = this.maxhp;
        return hp;
    }
    public void resetDefence()
    {
        def = maxdef;
    }
    public void resetAttack()
    {
        tackleDamage = maxTackleDamage;
    }
    public void getAttack()
    {
        tackleDamage = maxTackleDamage;
    }
    public int GetLevel()
    {
        int a = this.Level;
        return a;
    }
    public void SetLevel(int lv)
    {
       this.Level=lv;
    }
    public void growl(Pokemon pokemon)
    {
        pokemon.lowerAttack();
    }
    public void tackle(Pokemon pokemon)
    {
        Random r = new Random();
        int damage = r.nextInt(tackleDamage+1);
        if(pokemon.getType() == "Fire")
        {
            pokemon.takeDamage(damage/2);
        }
        else if(pokemon.getType() == "Water")
        {
            pokemon.takeDamage(damage*2);
        }
        else
        {
            pokemon.takeDamage(damage);
        }
    }
    public void lowerAttack()
    {
        if(tackleDamage >= maxTackleDamage-25)
            tackleDamage -= 5;
    }
    public void lowerDefence()
    {
        if(def > maxdef-25)
            def -= 5;
    }
    public void takeDamage(int damage)
    {
        Random r = new Random();
        int defence = r.nextInt(def);
        int netDamage = damage-defence;
        if(netDamage < 0)
            netDamage = 0;
        hp -= damage;
        if(hp < 0)
            hp = 0;
    }
    public void selectAttack(Pokemon pokemon)
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\t\t> Choose your attack : ");
        System.out.println("\t\t1. Growl");
        System.out.println("\t\t2. Tackle");
        
        int ac = sc.nextInt();
        switch(ac)
        {
            case 1:
                growl(pokemon);
                break;
            case 2:
                tackle(pokemon);
                break;
            default:
                System.out.println("\t\t> Invalid choice");
        }
    }
    public void selectAttack(int r,Pokemon plpokemon)
    {
        switch(r)
        {
            case 1:
                growl(plpokemon);
                break;
            case 2:
                tackle(plpokemon);
                break;
            default:
                System.out.println("> Invalid choice");
        }
    }
    public void Levelup()
    {
        this.SetLevel(this.GetLevel()+1);
        System.out.println("\t\t> Your pokemon "+this.getName()+" has been upgraded to Level "+this.GetLevel());
        if(Level<10)
        {
            this.SetMaxhp(this.GetMaxhp()+1);
            this.setHP(this.getHP()+1);
        }
        else if(Level>10&&Level<20)
        {
            System.out.println("\t\tCongratulation! Your Pokemon "+this.getName()+" crossed Gold Level "+this.GetLevel());
            this.setHP(60);
            this.SetMaxhp(60);
            System.out.println("\t\tFor this Achievement your Pokemon Health has been Upgraded to "+this.getHP());
            System.out.println("\t\tNow Your pokemon "+this.getName()+" is upgraded to new version called as Ivysaur");
            this.SetName("Ivysaur");
        }
        else if(Level>20&&Level<30)
        {
            System.out.println("\t\tCongratulation! Your Pokemon "+this.getName()+" crossed Special Level "+this.GetLevel());
            this.setHP(80);
            this.SetMaxhp(80);
            System.out.println("\t\tFor this Achievement you win new move Named Flamethrower.\n Damage of this Move is "+this.getHP());
            System.out.println("\t\tNow Your pokemon "+this.getName()+" is upgraded to new version called as Venusaur");
            this.SetName("Venusaur");
        }
    }
}