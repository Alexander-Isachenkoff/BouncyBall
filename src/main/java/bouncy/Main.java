package bouncy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Level level = new Level();
//        for (int i = 0; i < 5; i++) {
//            Block block = new Block();
//            block.setPosition(100 + i * block.getWidth(), 200 + -i * block.getHeight());
//            level.add(block);
//            if (i == 4) {
//                Star star = new Star();
//                star.setPosition(block.getX(), block.getY() - star.getHeight());
//                level.add(star);
//            }
//        }
//        Ball ball = new Ball();
//        ball.setPosition(120, 0);
//        level.add(ball);
//        level.start();
//
//        stage.setScene(new Scene(level));

        Parent parent = new FXMLLoader(Main.class.getResource("level_editor.fxml")).load();
        stage.setScene(new Scene(parent));

        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }

}
