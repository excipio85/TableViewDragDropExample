package sample;

import javafx.beans.property.*;

/**
 * Created by Joachim on 17.08.2015.
 */
public class Animal {

    private IntegerProperty animalId = new SimpleIntegerProperty();
    private StringProperty animalName = new SimpleStringProperty();
    private ObjectProperty<Person> animalOwner = new SimpleObjectProperty<>();

    public Animal(){}

    public Animal(int animalId, String animalName, Person animalOwner){
        setAnimalId(animalId);
        setAnimalName(animalName);
        setAnimalOwner(animalOwner);
    }

    public int getAnimalId(){
        return animalId.get();
    }

    public void setAnimalId(int animalId){
        this.animalId.set(animalId);
    }

    public IntegerProperty animalIdProperty(){
        return animalId;
    }

    public String getAnimalName(){
        return animalName.get();
    }

    public void setAnimalName(String animalName){
        this.animalName.set(animalName);
    }

    public StringProperty animalNameProperty(){
        return animalName;
    }

    public Person getAnimalOwner(){
        return animalOwner.get();
    }

    public void setAnimalOwner(Person animalOwner){
        this.animalOwner.set(animalOwner);
    }

    public ObjectProperty<Person> animalOwnerProperty(){
        return animalOwner;
    }

}
