package Handelsakademin.FarmProgram;

public class Entity {
    private int id;

    protected String name;

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return "ID: " + id + ", Name: " + name;
    }
}