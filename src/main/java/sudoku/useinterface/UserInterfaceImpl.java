package sudoku.useinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.domain.Coordinates;
import sudoku.domain.Game;

import java.awt.*;
import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterfaceContract.View,
        EventHandler<KeyEvent> {

    private final Stage stage;
    private final Group root;

    // keep track of 81 different text fields with HashMap
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinate;

    private IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_Y = 732d;
    private static final double WINDOW_X = 668d;
    private static final double BOARD_PADDING = 50d;
    private static final double BOARD_X_AND_Y = 576d;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0,150,136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(225,242,241);
    private static final String SUDOKU = "Sudoku Game";

    public UserInterfaceImpl() {
    }

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinate = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        stage.show();
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235,690,SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font (43);
        title.setFont(titleFont);;
        root.getChildren().add(title);
    }

    private void drawBoard(Group root) {
        Rectangle boardBackgroun = new Rectangle();
        boardBackgroun.setX(BOARD_PADDING);
        boardBackgroun.setY(BOARD_PADDING);

        boardBackgroun.setHeight(BOARD_X_AND_Y);
        boardBackgroun.setWidth(BOARD_X_AND_Y);

        boardBackgroun.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackgroun);

    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;

        final int xAndYDelta = 64;

        // Runtime Complexity of O(n^2)

        for (int xIndex = 0; xIndex < 9; xIndex++){
            for (int yIndex = 0; yIndex < 9; yIndex++){
                int x = xOrigin + xIndex + xAndYDelta;
                int y = yOrigin + yIndex + xAndYDelta;

                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);

                styleSudokuTile(tile, x, y);

                tile.setOnKeyPressed(this);

                textFieldCoordinate.put(new Coordinates((xIndex, yIndex), tile);

                root.getChildren().add(tile);
            }
        }

    }

    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);

        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }

    private void drawGridLines(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            if(index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y
                    );


            root.getChildren().
                    addAll(
                            verticalLine,
                            horizontalLine
                    );

            index++;
        }
    }

    private Rectangle getLine(double x,
                              double y,
                              double height,
                              double width){
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);

        return null;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinate.get(new Coordinates(x, y));

        String value = Integer.toString(input);

        if(value.equals("0")) value = "";

        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(Game game) {
        for (int xIndex = 0; xIndex < 9; xIndex++){
            for (int yIndex = 0; yIndex < 9; yIndex++){
                TextField tile = textFieldCoordinate.get(new Coordinates(xIndex, yIndex));

                String value = Integer.toString( game.getCopyOfGameState()[xIndex][yIndex]);

                if(value.equals("0")) value = "";

                tile.setText(value);

                if(game.getGameState() == GameState.NEW){
                    if(value.equals("")){
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    }else{
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(false);
                    }
                }

            }
        }
    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void showError(String message) {

    }
}
