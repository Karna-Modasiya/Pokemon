package pokemonmain;

interface Pokemon
{
    public String getName();
    public void SetName(String s);
    public String getType();
    public void setHP(int hp);
    public int getHP();
    public void resetHP();
    public void SetMaxhp(int hp);
    public void playsong() throws Exception;
    public int GetLevel();
    public void SetLevel(int i);
    public void Levelup();
    public void lowerAttack();
    public void resetAttack();
    public void lowerDefence();
    public void resetDefence();
    public void takeDamage(int damage);
    public void selectAttack(Pokemon pokemon);
    public void selectAttack(int r, Pokemon plpokemon);
}