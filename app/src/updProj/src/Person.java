/**
 * Abstract class representing a person with a name.
 */
public abstract class Person {
    protected String name;

    /**
     * Constructor to initialize a person with a name.
     *
     * @param name the person's name
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Returns the person's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Displays the person's information.
     */
    public abstract void displayPersonInfo();
}
