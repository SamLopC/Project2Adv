package project2adv;

/**
 * The {@code Person} abstract class represents a person with a name. This class provides 
 * a foundation for entities with personal details, such as {@link Customer}. It includes 
 * a name attribute and defines a method to display person-specific information.
 *
 * <p>This class is designed to be extended by more specific types of persons that require 
 * additional attributes or methods.
 */
public abstract class Person {
    /**
     * The name of the person.
     */
    protected String name;

    /**
     * Constructs a new {@code Person} instance with the specified name.
     *
     * @param name the name of the person
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Displays information specific to the person. This method should be implemented 
     * by subclasses to provide details relevant to the particular type of person.
     */
    public abstract void displayPersonInfo();
}
