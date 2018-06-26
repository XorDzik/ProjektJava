package sample;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.util.List;

import static javafx.scene.paint.Color.TRANSPARENT;


public class Main extends Application {
    MediaPlayer mediaPlayer;
    @FXML
    JFXListView<Button> listView;
    JFXSlider volumeSlider;
    JFXSlider timeSlider;
    @FXML
    private JFXProgressBar currentTimeIndicator;
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 350, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("MusicPlayer");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(TRANSPARENT);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        listView = new JFXListView<>();
        listView.setLayoutX(350.0);
        listView.setLayoutY(0.0);
        listView.setPrefHeight(600.0);
        listView.setPrefWidth(350.0);


        Label label = new Label("MusicPlayer");
        Label volume = new Label("Volume");
        Label song = new Label("Now playing: ");


        label.setTranslateX(125);
        label.setTranslateY(290);
        volume.setTranslateX(5);
        volume.setTranslateY(550);
        song.setTranslateX(5.0);
        song.setTranslateY(370.0);
        song.setFont(new Font("Comic Sans MS", 12));
        volume.setFont(new Font("Comic Sans MS", 12));
        label.setFont(new Font("Comic Sans MS", 20));


        Image playMusic = new Image(getClass().getResourceAsStream("playMusic.png"));
        Image pauseMusic = new Image(getClass().getResourceAsStream("pauseMusic.png"));
        Image stopMusic = new Image(getClass().getResourceAsStream("stopMusic.png"));
        Image reloadMusic = new Image(getClass().getResourceAsStream("reload.png"));
        Image openMusic = new Image(getClass().getResourceAsStream("open.png"));
        Image closeApp = new Image(getClass().getResourceAsStream("closeApp.png"));
        Image playlist = new Image(getClass().getResourceAsStream("playlist.png"));
        Image music = new Image(getClass().getResourceAsStream("muz.png"));
        ImageView mjuzik = new ImageView(music);
        mjuzik.setImage(music);
        mjuzik.setX(110.0);
        mjuzik.setY(150.0);


        Button close = new Button("CLOSE");
        close.setGraphic(new ImageView(closeApp));



        Button load = new Button("OPEN");
        load.setGraphic(new ImageView(openMusic));



        Button play = new Button("PLAY");
        play.setGraphic(new ImageView(playMusic));

        Button pause = new Button("PAUSE");
        pause.setGraphic(new ImageView(pauseMusic));


        Button stop = new Button("STOP");
        stop.setGraphic(new ImageView(stopMusic));


        Button reload = new Button("RELOAD");
        reload.setGraphic(new ImageView(reloadMusic));

        Button show = new Button("PLAYLIST");
        show.setGraphic(new ImageView(playlist));


        volumeSlider = new JFXSlider();
        volumeSlider.setStyle("-fx-control-inner-background: royalblue;");

        if (mediaPlayer != null) {
            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        }
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });

       timeSlider = new JFXSlider();
       timeSlider.setVisible(false);
       if (mediaPlayer != null) {
          timeSlider.setValue(mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
          }

      timeSlider.valueProperty().addListener(new InvalidationListener() {
         @Override
         public void invalidated(Observable observable) {
            if(timeSlider.isPressed()){
                  mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue()/100));

                }
            }
        });

        root.getChildren().addAll(load, play, pause, stop, volumeSlider, reload, listView, close, show);
        root.getChildren().addAll(timeSlider, mjuzik, label, volume, song);


        AnchorPane.setBottomAnchor(volumeSlider, 10.0);
        AnchorPane.setLeftAnchor(volumeSlider, 5.0);

        AnchorPane.setBottomAnchor(timeSlider, 200.0);


        AnchorPane.setBottomAnchor(play, 150.0);
        AnchorPane.setLeftAnchor(play, 100.0);

        AnchorPane.setBottomAnchor(stop, 150.0);
        AnchorPane.setLeftAnchor(stop, 5.0);

        AnchorPane.setBottomAnchor(pause, 150.0);
        AnchorPane.setLeftAnchor(pause, 200.0);

        AnchorPane.setTopAnchor(close, 5.0);
        AnchorPane.setRightAnchor(close, 5.0);

        AnchorPane.setTopAnchor(load, 5.0);
        AnchorPane.setLeftAnchor(load, 5.0);

        AnchorPane.setBottomAnchor(reload, 10.0);
        AnchorPane.setLeftAnchor(reload, 250.0);

        AnchorPane.setBottomAnchor(show, 10.0);
        AnchorPane.setLeftAnchor(show, 150.0);


        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
                List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

                if(selectedFiles!=null){
                    for(int i=0; i<selectedFiles.size();i++){
                        Button button = new Button(selectedFiles.get(i).getPath());


                        button.setOnMouseClicked((MouseEvent) -> {
                            song.setText(button.getText().substring(button.getText().lastIndexOf('\\')+1));

                            if (mediaPlayer != null)
                                mediaPlayer.stop();

                            mediaPlayer = new MediaPlayer(new Media("file:///" + button.getText().replace("\\", "/")));
                            timeSlider.setVisible(true);



                            mediaPlayer.play();
                        });

                        if (mediaPlayer == null)
                            mediaPlayer = new MediaPlayer(new Media("file:///" + button.getText().replace("\\", "/")));

                        listView.getItems().add(button);
                    }
                }



            }
        });

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mediaPlayer != null)
                    mediaPlayer.play();
            }
        });

        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mediaPlayer != null)
                    mediaPlayer.pause();
            }
        });
        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mediaPlayer != null)
                    mediaPlayer.stop();
            }
        });

        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mediaPlayer != null)
                    mediaPlayer.seek(mediaPlayer.getStartTime());
            }
        });

        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (primaryStage.getWidth() == 350) {
                    primaryStage.setWidth(750);
                    AnchorPane.setRightAnchor(close, 405.0);
                } else {
                    primaryStage.setWidth(350);
                    AnchorPane.setRightAnchor(close, 5.0);
                }
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
