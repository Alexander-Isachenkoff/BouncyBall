package bouncy;

import bouncy.model.Categories;
import bouncy.model.Category;
import bouncy.model.GameObject;
import bouncy.model.LevelData;
import bouncy.ui.GameObjectToggleButton;
import bouncy.view.GameObjectNode;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.List;

public class LevelEditor {

    public static final int GRID_SIZE = 40;
    private final Rectangle selector = new Rectangle();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    public Pane levelPane;
    public CheckBox collidersCheckBox;
    public ListView<Category> categoriesList;
    public VBox blocksPane;
    private Level level;

    @FXML
    private void initialize() {
        collidersCheckBox.selectedProperty().bindBidirectional(AppProperties.collidersProperty);
        level = new Level();
        level.getLevelData().setName("Level 1");
        levelPane.getChildren().add(level);

        LevelData levelData = LevelData.load("Level 1.xml");
        for (GameObject gameObject : levelData.getGameObjects()) {
            addGameObject(gameObject);
        }

        initGameObjectsList();

        drawGrid();

        level.getChildren().add(selector);
        level.setOnMouseMoved(event -> {
            double x = Math.floor(event.getX() / GRID_SIZE) * GRID_SIZE;
            double y = Math.floor(event.getY() / GRID_SIZE) * GRID_SIZE;
            selector.setWidth(GRID_SIZE);
            selector.setHeight(GRID_SIZE);
            GameObject selectedItem = getSelectedGameObject();
            if (selectedItem != null) {
                selector.setFill(new GameObjectNode(selectedItem).getObjectRectangle().getFill());
                selector.setOpacity(0.5);
            } else {
                selector.setFill(Color.LIGHTSKYBLUE);
            }
            selector.setX(x);
            selector.setY(y);
        });

        level.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            int xIndex = (int) Math.floor(event.getX() / GRID_SIZE);
            int yIndex = (int) Math.floor(event.getY() / GRID_SIZE);
            double x = xIndex * GRID_SIZE;
            double y = yIndex * GRID_SIZE;
            GameObject selectedItem = getSelectedGameObject();
            if (selectedItem != null) {
                GameObject gameObject;
                try {
                    gameObject = selectedItem.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                gameObject.setX(x);
                gameObject.setY(y);
                gameObject.setWidth(GRID_SIZE);
                gameObject.setHeight(GRID_SIZE);
                gameObject.setImagePath(selectedItem.getImagePath());
                addGameObject(gameObject);
            }
        });
    }

    private FlowPane createGameObjectsFlowPane(Category category) {
        FlowPane pane = new FlowPane();
        pane.getStyleClass().add("gameObjectsFlowPane");
        List<String> fileNames = FileUtils.getPackFileNames(category.getPack());
        for (String fileName : fileNames) {
            try {
                Class<?> aClass = Class.forName("bouncy.model." + category.getClassName());
                GameObject gameObject = (GameObject) aClass.newInstance();
                gameObject.setWidth(GRID_SIZE);
                gameObject.setHeight(GRID_SIZE);
                gameObject.setImagePath(new File(category.getPack(), fileName).getPath());
                addGameObjectToList(pane, gameObject);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return pane;
    }

    private void addGameObjectToList(Pane pane, GameObject gameObject) {
        ToggleButton toggleButton = new GameObjectToggleButton(gameObject);
        toggleButton.setToggleGroup(toggleGroup);
        pane.getChildren().add(toggleButton);
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
            return createGameObjectsFlowPane(category);
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
                FlowPane pane = createGameObjectsFlowPane(newValue);
                vBox.getChildren().set(1, pane);
            });
            subCategoriesList.getSelectionModel().select(0);
            return vBox;
        }
    }

    private void drawGrid() {
        for (int i = 1; i <= 100; i++) {
            Line line = new Line();
            int y = GRID_SIZE * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartY(y);
            line.setEndY(y);
            line.setEndX(3000);
            level.getChildren().add(line);
        }

        for (int i = 1; i <= 100; i++) {
            Line line = new Line();
            int y = GRID_SIZE * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartX(y);
            line.setEndX(y);
            line.setEndY(3000);
            level.getChildren().add(line);
        }
    }

    private void addGameObject(GameObject gameObject) {
        GameObjectNode gameObjectNode = level.add(gameObject);
        gameObjectNode.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                level.remove(gameObject);
            }
        });
    }

    @FXML
    private void onSave() {
        level.getLevelData().save();
    }

    @FXML
    private void onStart() {
        Level level1 = new Level();
        for (GameObject gameObject : level.getLevelData().getGameObjects()) {
            level1.add(gameObject);
        }
        Scene scene = levelPane.getScene();
        scene.setRoot(level1);
        level1.start();
    }

    @FXML
    private void onClear() {
        level.clear();
    }
}
