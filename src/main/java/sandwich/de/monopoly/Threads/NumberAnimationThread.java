package sandwich.de.monopoly.Threads;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class NumberAnimationThread extends Thread {

    private final int ANIMATION_SPEED = 20;
    private int length;
    private final Label animationRow;

    public NumberAnimationThread(Label animationRow) {
        length = 0;
        this.animationRow = animationRow;
    }

    public void setLength(int randomLength) {
        if ((randomLength % 2) != 0)
            length = randomLength - 1;
        else length = randomLength;
    }

    public int getLastNumber() {
        int n = Integer.parseInt(String.valueOf(animationRow.getText().charAt(16)));
        for (int i = 0; i < length / 2; i++) {
            if (n >= 6)
                n = 1;
            else n = n + 1;
        }
        return n;
    }

    @Override
    public void run() {
        for (int i = 0; i < length; i++) {
            try {Thread.sleep(ANIMATION_SPEED);} catch (InterruptedException ignored) {}

            Platform.runLater(() -> {

                String rowText = animationRow.getText();
                StringBuilder rowNewText = new StringBuilder();

                rowNewText.append(rowText.substring(1));
                if (!rowText.endsWith("-")) {
                    rowNewText.append("-");
                } else {
                    int rowLastNumber = Integer.parseInt(String.valueOf(rowText.charAt(rowText.length() - 2)));
                    if (rowLastNumber >= 6) {
                        rowNewText.append("1");
                    } else {
                        rowNewText.append(rowLastNumber + 1);
                    }
                }
                animationRow.setText(rowNewText.toString());
            });
        }
    }
}
