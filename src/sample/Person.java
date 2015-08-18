package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Joachim on 17.08.2015.
 */
public class Person {

    private IntegerProperty personId = new SimpleIntegerProperty();
    private StringProperty firstname = new SimpleStringProperty();
    private StringProperty lastname = new SimpleStringProperty();
    private ObservableList<Animal> animals = FXCollections.observableArrayList();

    public Person(){}

    public Person(int personId, String firstname, String lastname){
        setPersonId(personId);
        setFirstname(firstname);
        setLastname(lastname);
    }

    public int getPersonId(){
        return personId.get();
    }

    public void setPersonId(int personId){
        this.personId.set(personId);
    }

    public IntegerProperty personIdProperty(){
        return personId;
    }

    public String getFirstname(){
        return firstname.get();
    }

    public void setFirstname(String firstname){
        this.firstname.set(firstname);
    }

    public StringProperty firstnameProperty(){
        return firstname;
    }

    public String getLastname(){
        return lastname.get();
    }

    public void setLastname(String lastname){
        this.lastname.set(lastname);
    }

    public StringProperty lastnameProperty(){
        return lastname;
    }

    ///Collection
    public ObservableList<Animal> getAnimals(){
        return animals;
    }

    public void addAnimal(Animal animal){
        this.animals.add(animal);
    }


}
