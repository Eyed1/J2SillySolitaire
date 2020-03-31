import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.*;
import javafx.util.Duration;
import java.util.concurrent.TimeUnit;

/**
 * JavaFX stuff that you'll learn about later
 */
public class MainController implements Initializable {

    @FXML
    private Button replaceButton;
    @FXML
    private Button hintButton;
    @FXML
    private Button howToButton;
    @FXML
    private Label statsLabel;
    @FXML
    private ImageView undealt;
    @FXML
    private FlowPane cardPane;
    @FXML
    private ScrollPane cardScrollPane;
    @FXML
    private MenuItem aboutThisGame;
    @FXML
    private Label titleLabel;
    
    private Image[] unselectedCardImages;
    private Image[] selectedCardImages;
    private Image emptyCardImage;
    private ImageView[] cardSlot;
    
    @FXML
    private ImageView loserGraphic;
    @FXML
    private ImageView winnerGraphic;    

    private GameType gameType = GameType.ELEVENS;
    private Board board;
    private List<Integer> selectedIndicies = new ArrayList<Integer>();
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;
    private String aboutTens = "Ten year olds are often quite immature. They still like to watch angry bird videos and sometimes play minecraft";
    private String aboutElevens = "The standard game, this game tests your ability to read a book that is in Japanese";
    private String aboutThirteens = "Thirteens uses a 10-card board. Pairs of cards whose point values add up to 13 are selected and removed. Kings are selected and removed singly.\nUse keys ` to 9 to toggle cards, and escape to deselect everything. Use Enter to replace cards, and use Backspace to reshuffle the deck or restart. Backslash key toggles a hint.";
    
    @FXML
    private void handleAboutMenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About SillySolitaire");
        alert.setHeaderText("About SillySolitaire");
        //alert.setContentText("SillySolitaire is a set of solitaire games: tens, elevens and thirteens. They're silly because there's nothing you can do to change the outcome of the game. This is a reimplementation of the CollegeBoard's Elevens lab using JavaFX. For more information or to report any bugs contact dwheadon@ucls.uchicago.edu");
        alert.setContentText("SillySolitaire was created during the 2020 COVID-19 pandemic. Just so you know the creators are dead so there is no one you can contact in case of problems.");
        alert.setWidth(100);
        alert.showAndWait();
    }

    @FXML
    private void handleCloseMenu(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void handleHint(ActionEvent event) {
        selectedIndicies = board.findPlayIsPossible();
        updateAllSlots();
    }

    @FXML
    private void handleAutoPlay(ActionEvent event) {
        if (board.anotherPlayIsPossible()) {
            selectedIndicies = board.findPlayIsPossible();
            updateAllSlots();
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> handleAutoReplace()));
            timeline.play();
        }
    }
    
    private void autoPlay() {
        if (board.anotherPlayIsPossible()) {
            selectedIndicies = board.findPlayIsPossible();
            updateAllSlots();
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> handleAutoReplace()));
            timeline.play();
        }
    }

    @FXML
    private void handleHowTo(ActionEvent event) {
        if (gameType == GameType.TENS) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Tens");
            alert.setHeaderText("About Tens");
            alert.setContentText(aboutTens);
            alert.setWidth(100);
            alert.showAndWait();  
        } else if (gameType == GameType.ELEVENS) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Elevens");
            alert.setHeaderText("About Elevens");
            alert.setContentText(aboutElevens);
            alert.setWidth(100);
            alert.showAndWait();
        } else if (gameType == GameType.THIRTEENS) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Thirteens");
            alert.setHeaderText("About Thirteens");
            alert.setContentText(aboutThirteens);
            alert.setWidth(100);
            alert.showAndWait();
        }
    }

    private void handleAutoReplace() {
        if (board.isLegal(selectedIndicies)) {
            board.replaceSelectedCards(selectedIndicies);
            selectedIndicies.clear();
            updateAllSlots();
            updateStats();
            if (board.gameIsWon()) {
                replaceButton.setText("Winner!");
                replaceButton.setDisable(true);
                hintButton.setText("Winner!");
                hintButton.setDisable(true);
                winnerGraphic.setVisible(true);
                wins++;
                updateStats();
            } else if (! checkLoser()) {
                Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    ae -> handleAutoPlay(null)));
                timeline.play();
            }
        }
    }

    private void doInstantAutoPlay(ActionEvent event) {
        while (board.playIfPossible()) {
            updateAllSlots();
            updateStats();
            if (board.gameIsWon()) {
                replaceButton.setText("Winner!");
                replaceButton.setDisable(true);
                hintButton.setText("Winner!");
                hintButton.setDisable(true);
                winnerGraphic.setVisible(true);
                wins++;
                updateStats();
            } else {
                if (checkLoser()) {
                    restart();
                    doInstantAutoPlay(null);
                }
            }
        }
    }

    private int getCardImageIndex(Suit s, Rank r) {
        return r.getOrdinal() - 1 + (s.getOrdinal() - 1) * Rank.values().length;
    }

    @FXML
    private void handleReplace(ActionEvent event) {
        if (board.isLegal(selectedIndicies)) {
            board.replaceSelectedCards(selectedIndicies);
            selectedIndicies.clear();
            updateAllSlots();
            updateStats();
            if (board.gameIsWon()) {
                replaceButton.setText("Winner!");
                replaceButton.setDisable(true);
                hintButton.setText("Winner!");
                hintButton.setDisable(true);
                winnerGraphic.setVisible(true);
                wins++;
                updateStats();
            } else {
                checkLoser();
            }
        }
    }
    
    @FXML
    private void handleRestartAction(ActionEvent event) {
        restart();
    }
    
    @FXML
    private void handleRestartMouse(MouseEvent event) {
        restart();
    }
    
    private void restart() {
        if (board.anotherPlayIsPossible()) {
            draws++;
            updateStats();
        }
        board.newGame();
        updateAllSlots();
        selectedIndicies = new ArrayList<Integer>();
        replaceButton.setText("Replace");
        replaceButton.setDisable(false);
        hintButton.setText("Hint");
        hintButton.setDisable(false);
        loserGraphic.setFitWidth(1);
        loserGraphic.setFitHeight(1);
        loserGraphic.setVisible(false);
        winnerGraphic.setFitWidth(1);
        winnerGraphic.setFitHeight(1);
        winnerGraphic.setVisible(false);
        updateStats();
        checkLoser();
    }
    
    @FXML
    private void switchToTensGame(ActionEvent event) {
        if (gameType != GameType.TENS) {
            gameType = GameType.TENS;
            initBoard();
        }
    }

    @FXML
    private void switchToElevensGame(ActionEvent event) {
        if (gameType != GameType.ELEVENS) {
           gameType = GameType.ELEVENS;
           initBoard();
        }
    }

    @FXML
    private void switchToThirteensGame(ActionEvent event) {
        if (gameType != GameType.THIRTEENS) {
           gameType = GameType.THIRTEENS;
            initBoard();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load all the images
        emptyCardImage = new Image(getClass().getResource("resources/back1.GIF").toString());
        int numCards = Rank.values().length * Suit.values().length;
        unselectedCardImages = new Image[numCards];
        selectedCardImages = new Image[numCards];
        howToButton.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 28px; " +
                "-fx-min-height: 28px; " +
                "-fx-max-width: 28px; " +
                "-fx-max-height: 28px;"
        );

        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                String fileName = "resources/" + rank.toString() + suit.toString();
                int cardImageIndex = getCardImageIndex(suit, rank);
                unselectedCardImages[cardImageIndex] = new Image(getClass().getResource(fileName + "s.GIF").toString());
                selectedCardImages[cardImageIndex] = new Image(getClass().getResource(fileName + "sS.GIF").toString());
            }
        }
        cardScrollPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
                cardPane.setPrefWidth((double) newWidth);
                loserGraphic.setFitWidth((double) newWidth - 30);
                winnerGraphic.setFitWidth((double) newWidth - 30);
            }
        });
        cardScrollPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                cardPane.setMinHeight((double) newHeight - 2);
                loserGraphic.setFitHeight((double) newHeight - 20);
                winnerGraphic.setFitHeight((double) newHeight - 20);
            }
        });
        cardScrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (!checkLoser()) {
                switch (ke.getCode()) {
                    case ESCAPE:
                        selectedIndicies.clear();
                        break;
                    case BACK_QUOTE:
                        if (selectedIndicies.contains(0)) {
                            selectedIndicies.remove(new Integer(0));
                        } else {
                            selectedIndicies.add(0);
                        }
                        break;
                    case DIGIT1:
                        if (selectedIndicies.contains(1)) {
                            selectedIndicies.remove(new Integer(1));
                        } else {
                            selectedIndicies.add(1);
                        }
                        break;
                    case DIGIT2:
                        if (selectedIndicies.contains(2)) {
                            selectedIndicies.remove(new Integer(2));
                        } else {
                            selectedIndicies.add(2);
                        }
                        break;
                    case DIGIT3:
                        if (selectedIndicies.contains(3)) {
                            selectedIndicies.remove(new Integer(3));
                        } else {
                            selectedIndicies.add(3);
                        }
                        break;
                    case DIGIT4:
                        if (selectedIndicies.contains(4)) {
                            selectedIndicies.remove(new Integer(4));
                        } else {
                            selectedIndicies.add(4);
                        }
                        break;
                    case DIGIT5:
                        if (selectedIndicies.contains(5)) {
                            selectedIndicies.remove(new Integer(5));
                        } else {
                            selectedIndicies.add(5);
                        }
                        break;
                    case DIGIT6:
                        if (selectedIndicies.contains(6)) {
                            selectedIndicies.remove(new Integer(6));
                        } else {
                            selectedIndicies.add(6);
                        }
                        break;
                    case DIGIT7:
                        if (selectedIndicies.contains(7)) {
                            selectedIndicies.remove(new Integer(7));
                        } else {
                            selectedIndicies.add(7);
                        }
                        break;
                    case DIGIT8:
                        if (selectedIndicies.contains(8)) {
                            selectedIndicies.remove(new Integer(8));
                        } else {
                            selectedIndicies.add(8);
                        }
                        break;
                    case DIGIT9:
                        if (gameType != GameType.ELEVENS) {
                            if (selectedIndicies.contains(9)) {
                                selectedIndicies.remove(new Integer(9));
                            } else {
                                selectedIndicies.add(9);
                            }
                        }
                        break;
                    case DIGIT0:
                        if (gameType != GameType.ELEVENS && gameType != GameType.THIRTEENS) {
                            if (selectedIndicies.contains(10)) {
                                selectedIndicies.remove(new Integer(10));
                            } else {
                                selectedIndicies.add(10);
                            }
                        }
                        break;
                    case MINUS:
                        if (gameType != GameType.ELEVENS && gameType != GameType.THIRTEENS) {
                            if (selectedIndicies.contains(11)) {
                                selectedIndicies.remove(new Integer(11));
                            } else {
                                selectedIndicies.add(11);
                            }
                        }
                        break;
                    case EQUALS:
                        if (gameType != GameType.ELEVENS && gameType != GameType.THIRTEENS) {
                            if (selectedIndicies.contains(12)) {
                                selectedIndicies.remove(new Integer(12));
                            } else {
                                selectedIndicies.add(12);
                            }
                        }
                        break;
                    case CLOSE_BRACKET:
                        doInstantAutoPlay(null);
                    case OPEN_BRACKET:
                        autoPlay();
                    case ENTER:
                        handleReplace(null);
                        break;
                    case BACK_SPACE:
                        restart();
                        break;
                    case BACK_SLASH:
                        handleHint(null);
                        break;
                    default:
                        break;
                }
                updateAllSlots();
                } else {
                    if (ke.getCode() == KeyCode.BACK_SPACE) {
                        restart();
                    }
                }
            }
        });
        cardScrollPane.requestFocus();
        initBoard();
    }
    
    public void initBoard() {
        wins = 0;
        losses = 0;
        draws = 0;
        selectedIndicies = new ArrayList<Integer>();
        if (gameType == GameType.TENS) {
            //throw new UnsupportedOperationException();
            board = new TensBoard();
            titleLabel.setText("Tens");
            aboutThisGame.setText("About Tens");
            aboutThisGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("About Tens");
                    alert.setHeaderText("About Tens");
                    alert.setContentText(aboutTens);
                    alert.setWidth(100);
                    alert.showAndWait();
                }    
            });
            
        } else if (gameType == GameType.ELEVENS) {
            board = new ElevensBoard();
            titleLabel.setText("Elevens");
            aboutThisGame.setText("About Elevens");
            aboutThisGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("About Elevens");
                    alert.setHeaderText("About Elevens");
                    alert.setContentText(aboutElevens);
                    alert.setWidth(100);
                    alert.showAndWait();
                }    
            });
        } else if (gameType == GameType.THIRTEENS) {
            //throw new UnsupportedOperationException();
            board = new ThirteensBoard();
            titleLabel.setText("Thirteens");
            aboutThisGame.setText("About Thirteens");
            aboutThisGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("About Thirteens");
                    alert.setHeaderText("About Thirteens");
                    alert.setContentText(aboutThirteens);
                    alert.setWidth(100);
                    alert.showAndWait();
                }    
            });
            
        } else {
            assert false : "No such game type";
        }
        if (cardSlot != null) {
            cardPane.getChildren().clear();
        }
        cardSlot = new ImageView[board.size()];
        for (int slot = 0; slot < board.size(); slot++) {
            Card c = board.cardAt(slot);
            final int slotF = slot;
            if (c != null) {
                int cardImageIndex = getCardImageIndex(c.getSuit(), c.getRank());
                ImageView cardView = new ImageView(unselectedCardImages[cardImageIndex]);
                cardView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int index = selectedIndicies.indexOf(slotF);
                        Card card = board.cardAt(slotF);
                        if (card != null) {
                            int cardImageIndex = getCardImageIndex(card.getSuit(), card.getRank());
                            if (index == -1) {
                                selectedIndicies.add(slotF);
                                cardView.setImage(selectedCardImages[cardImageIndex]);
                            } else {
                                selectedIndicies.remove(index);
                                cardView.setImage(unselectedCardImages[cardImageIndex]);
                            } 
                        }
                    }    
                });
                cardSlot[slot] = cardView;
                cardPane.getChildren().add(cardView);
            }
            else {
                ImageView cardView = new ImageView(emptyCardImage);
                cardSlot[slot] = cardView;
                cardPane.getChildren().add(cardView);
            }
        }
        replaceButton.setText("Replace");
        replaceButton.setDisable(false);
        loserGraphic.setFitWidth(1);
        loserGraphic.setFitHeight(1);
        loserGraphic.setVisible(false);
        winnerGraphic.setFitWidth(1);
        winnerGraphic.setFitHeight(1);
        winnerGraphic.setVisible(false);
        updateStats();
        checkLoser();
    }   
    
    private void updateSlots(List<Integer> slots) {
        for (Integer slot : slots) {
            Card c = board.cardAt(slot);
            if (c != null) {
                int cardImageIndex = getCardImageIndex(c.getSuit(), c.getRank());
                Image cardImage;
                if (selectedIndicies.contains(slot)) {
                    cardImage = selectedCardImages[cardImageIndex];
                } else {
                    cardImage = unselectedCardImages[cardImageIndex];
                }
                cardSlot[slot].setImage(cardImage);
            } else {
                cardSlot[slot].setImage(emptyCardImage);
            }
        }
    }

    private void updateAllSlots() {
        updateSlots(allIndexes());
    }
    
    private List<Integer> allIndexes() {
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < board.size(); i++) {
            indexes.add(i);
        }
        return indexes;
    }
    
    private boolean checkLoser() {
        if (!board.anotherPlayIsPossible() && !board.gameIsWon()) {
            replaceButton.setText("Loser!");
            replaceButton.setDisable(true);
            hintButton.setText("Loser!");
                hintButton.setDisable(true);
            if (cardPane.getWidth() < cardPane.getHeight()) {
                loserGraphic.setFitHeight(0);
                loserGraphic.setFitWidth(cardPane.getWidth() - 20);
            } else {
                loserGraphic.setFitWidth(0);
                loserGraphic.setFitHeight(cardPane.getHeight() - 20);
            }
            loserGraphic.setVisible(true);
            losses++;
            updateStats();
            return true;
        }
        return false;
    }
    
    private void updateStats() {
        // Guard prevents a divide by zero when it's the first game
        int guard = 0;
        if (wins+losses == 0) {
            guard = 1;
        }
        statsLabel.setText(board.deckSize() + " undealt  "+wins+"/"+losses+"/"+draws+": "+ wins*100/(wins+losses+guard) + "%");
    }
}
