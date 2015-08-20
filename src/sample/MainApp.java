package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import settings.GuiUtils;

import java.util.stream.Collectors;

/**
 * Created by Joachim on 17.08.2015.
 */
public class MainApp extends Application {

    // TODO sorting personTable after update
    // TODO enable filter animalTable
    // TODO own talbe for animals without owner?

    private Stage primaryStage;

    private ObservableList<Person> persons = FXCollections.observableArrayList(
            new Person(1, "Meister", "Eder"),
            new Person(2, "Lila", "Testente")
    );
    private ObservableList<Animal> tmpanimals = FXCollections.observableArrayList();

    private TableView<Person> personTable;
    private TableView<Animal> animalTable;

    private Animal draggedAnimal = null;
    private Person addedPerson = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUserAgentStylesheet("CASPIAN");
        this.primaryStage = primaryStage;
        createDummyData();
        // persons.forEach(person -> person.getAnimals().forEach(animal -> System.out.println(animal.getAnimalName())));
        initTablesAndShowApplication();
    }

    public static void main(String[] args){
        launch(args);
    }

    private void createDummyData(){
        Person p1 = new Person(3, "Meister", "Eder");
        Animal a1 = new Animal(1, "Lisa", p1);
        Animal a2 = new Animal(2, "Fiffi", p1);
        a1.animalOwnerProperty().set(p1);
        a2.animalOwnerProperty().set(p1);
        persons.addAll(p1);
        tmpanimals.addAll(a1,a2);
    }

    private void initTablesAndShowApplication(){
        personTable = new TableView<>();
        animalTable = new TableView<>();

        ObservableList<Animal> animals = FXCollections.observableList(tmpanimals,
                p -> new Observable[] {p.animalOwnerProperty()});

        FilteredList<Animal> animalsPerPerson = new FilteredList<>(animals, p -> false);

        personTable.itemsProperty().set(persons);
        personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Person, Number> personIdCol = new TableColumn<>("Person Id");
        personIdCol.setCellValueFactory(value -> value.getValue().personIdProperty());

        TableColumn<Person, String> firstnamecol =new TableColumn<>("Firstname");
        firstnamecol.setCellValueFactory(value -> value.getValue().firstnameProperty());

        TableColumn<Person, String> lastnameCol = new TableColumn<>("Lastname");
        lastnameCol.setCellValueFactory(value -> value.getValue().lastnameProperty());

        TableColumn<Person, Number> countAnimalsCol = new TableColumn<>("Animals");
        countAnimalsCol.setCellValueFactory(data ->
                        Bindings.createLongBinding(() ->
                                        animals.stream()
                                                .filter(animal -> animal.getAnimalOwner().equals(data.getValue()))
                                                .collect(Collectors.counting()), animals
                        )
        );

        personTable.getColumns().addAll(personIdCol, firstnamecol, lastnameCol, countAnimalsCol);

        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldPerson, newPerson) -> {
            if (newPerson == null) {
                animalsPerPerson.setPredicate(p -> false);
            } else {
                animalsPerPerson.setPredicate(p -> newPerson.equals(p.getAnimalOwner()));
            }
            animalTable.setItems(animalsPerPerson);
        });

        animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Animal, Number> animaldIdCol = new TableColumn<>("Animal Id");
        animaldIdCol.setCellValueFactory(value -> value.getValue().animalIdProperty());

        TableColumn<Animal, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(value -> value.getValue().animalNameProperty());

        TableColumn<Animal, Number> ownerCol = new TableColumn<>("Owner");
        ownerCol.setCellValueFactory(value -> value.getValue().animalOwnerProperty().getValue().personIdProperty());

        animalTable.getColumns().addAll(animaldIdCol, nameCol, ownerCol);

        Button button = new Button("add Person");
        button.setOnAction(event -> {
            addedPerson = new Person();
            addedPerson.setPersonId(4);
            addedPerson.setFirstname("Firstname");
            addedPerson.setLastname("Lastname");
            persons.add(addedPerson);

            personTable.requestFocus();
            personTable.getSelectionModel().select(addedPerson);
            GuiUtils.setAnimationActivated(Boolean.FALSE);
        });

        animalTable.setRowFactory((tableView) -> {
            TableRow<Animal> newRow = new TableRow<Animal>();
            newRow.setOnDragDetected((event) -> {
                if (! newRow.isEmpty()) {
                    Dragboard dragboard = newRow.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString("Animal.class");
                    draggedAnimal = newRow.getItem();
                    dragboard.setContent(cc);
                    dragboard.setDragView(newRow.snapshot(null, null));
                }
            });
            return newRow;
        });

        personTable.setRowFactory((tableView) -> {
            TableRow<Person> newRow = new TableRow<Person>();
            newRow.setOnDragOver((event) -> {
                if (! newRow.isEmpty()) {
                    Dragboard dragboard = event.getDragboard();
                    if ("Animal.class".equals(dragboard.getString()) && draggedAnimal != null) {
                        event.acceptTransferModes(TransferMode.MOVE);
                        newRow.setStyle("-fx-background-color: mediumorchid; " +
                                "-fx-table-cell-border-color: mediumorchid; ");
                    }
                }
            });

            newRow.setOnMouseExited((event) -> {
                newRow.setStyle("");
            });

            newRow.setOnDragExited((event) -> {
                newRow.setStyle("");
            });

            newRow.setOnDragDropped((event) -> {
                if (! newRow.isEmpty()) {
                    Dragboard dragboard = event.getDragboard();
                    if ("Animal.class".equals(dragboard.getString()) && newRow != null && ! (newRow.getItem().getPersonId() == (draggedAnimal.getAnimalOwner().getPersonId()))) {
                        if (GuiUtils.isAnimationActivated()) {
                            TranslateTransition tt = new TranslateTransition(Duration.millis(150), newRow);
                            //tt.interpolatorProperty().set(Interpolator.EASE_BOTH);
                            tt.setByX(- 80f);
                            tt.setCycleCount(2);
                            tt.setAutoReverse(true);
                            tt.play();
                        }
                        draggedAnimal.setAnimalOwner(newRow.getItem());
                        draggedAnimal = null;
                        event.setDropCompleted(true);


                    } else {
                        event.setDropCompleted(false);
                    }
                }
            });

            return newRow;
        });

        HBox root = new HBox(10, personTable, animalTable, button);
        root.setPadding(new Insets(10d));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
