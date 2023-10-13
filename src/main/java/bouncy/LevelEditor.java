package bouncy;

import bouncy.model.Categories;
import bouncy.model.Category;
import bouncy.model.GameObject;
import bouncy.model.LevelData;
import bouncy.ui.EditableLevel;
import bouncy.ui.GameObjectToggleButton;
import bouncy.ui.GameObjectsFlowPane;
import bouncy.ui.PlayingLevel;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class LevelEditor {

    public static final int GRID_SIZE = 40;

    private final ToggleGroup toggleGroup = new ToggleGroup();
    public Pane levelPane;
    public CheckBox collidersCheckBox;
    public ListView<Category> categoriesList;
    public VBox blocksPane;
    public TextField levelNameField;
    private EditableLevel level;

    @FXML
    private void initialize() {
        collidersCheckBox.selectedProperty().bindBidirectional(AppProperties.collidersProperty);
        level = new EditableLevel(GRID_SIZE) {
            @Override
            public GameObject getSelectedGameObject() {
                return LevelEditor.this.getSelectedGameObject();
            }
        };
        levelPane.getChildren().add(level);

        level.getLevelData().setName("New Level");
        levelNameField.setText(level.getLevelData().getName());
        levelNameField.textProperty().addListener((observable, oldValue, newValue) -> level.getLevelData().setName(newValue));

        initGameObjectsList();
    }

    private void loadLevelData(String fileName) {
        LevelData levelData = LevelData.load(fileName);
        level.clear();
        for (GameObject gameObject : levelData.getGameObjects()) {
            level.add(gameObject);
        }
        level.getLevelData().setName(levelData.getName());
        levelNameField.setText(level.getLevelData().getName());
    }

    private GameObject getSelectedGameObject() {
        GameObjectToggleButton selectedToggle = (GameObjectToggleButton) toggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            return selectedToggle.getGameObject();
        } else {
            return null;
        }
    }

    private void initGameObjectsList() {
        List<Category> categories = Categories.load();
        categoriesList.getItems().setAll(categories);
        for (Category category : categories) {
            createCategoryPane(category);
        }

        categoriesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Node node = createCategoryPane(newValue);
            blocksPane.getChildren().set(0, node);
            VBox.setVgrow(node, Priority.ALWAYS);
        });

        categoriesList.getSelectionModel().select(0);

        categoriesList.setCellFactory(param -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Image image = ImageManager.getImage(item.getImagePath());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(30);
                    HBox hBox = new HBox(10, imageView, new Label(item.getName()));
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(hBox);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private Node createCategoryPane(Category category) {
        if (category.getCategories().isEmpty()) {
            return new GameObjectsFlowPane(toggleGroup, category, GRID_SIZE);
        } else {
            ListView<Category> subCategoriesList = new ListView<>();
            subCategoriesList.getStyleClass().add("subcategories-list");
            subCategoriesList.getItems().setAll(category.getCategories());
            subCategoriesList.setCellFactory(param -> new ListCell<Category>() {
                @Override
                protected void updateItem(Category item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        ImageView imageView = new ImageView(ImageManager.getImage(item.getImagePath()));
                        imageView.setFitWidth(25);
                        imageView.setFitHeight(25);
                        setGraphic(imageView);
                    } else {
                        setGraphic(null);
                    }
                }
            });
            HBox vBox = new HBox(subCategoriesList, new VBox());
            subCategoriesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                FlowPane pane = new GameObjectsFlowPane(toggleGroup, newValue, GRID_SIZE);
                vBox.getChildren().set(1, pane);
            });
            subCategoriesList.getSelectionModel().select(0);
            return vBox;
        }
    }

    @FXML
    private void onSave() {
        level.getLevelData().save();
    }

    @FXML
    private void onStart() {
        PlayingLevel playingLevel = new PlayingLevel();
        for (GameObject gameObject : level.getLevelData().getGameObjects()) {
            playingLevel.add(gameObject);
        }
        Scene scene = levelPane.getScene();
        scene.setRoot(playingLevel);
        playingLevel.start();
    }

    @FXML
    private void onClear() {
        level.clear();
    }

    @FXML
    private void onLoad() {
        Stage stage = new Stage();
        ListView<File> levelsList = new ListView<>();
        levelsList.setCellFactory(param -> new ListCell<File>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Button button = new Button("Load");
                    button.setOnAction(event -> {
                        stage.close();
                        loadLevelData(item.getAbsolutePath());
                    });
                    setGraphic(new HBox(new Label(item.getName()), button));
                } else {
                    setGraphic(null);
                }
            }
        });
        File[] levelsFiles = Optional.ofNullable(new File("data/user_levels").listFiles()).orElse(new File[0]);
        levelsList.getItems().setAll(levelsFiles);
        stage.setScene(new Scene(levelsList));
        stage.show();
    }

}
