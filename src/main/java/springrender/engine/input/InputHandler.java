package springrender.engine.input;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InputHandler {
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public InputHandler(JComponent component) {
        setupKeyBindings(component);
    }

    private void setupKeyBindings(JComponent component) {
        InputMap im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = component.getActionMap();

        // Up (W)
        im.put(KeyStroke.getKeyStroke("W"), "moveUpPress");
        am.put("moveUpPress", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upPressed = true;
            }
        });

        im.put(KeyStroke.getKeyStroke("released W"), "moveUpRelease");
        am.put("moveUpRelease", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upPressed = false;
            }
        });

        // Down (S)
        im.put(KeyStroke.getKeyStroke("S"), "moveDownPress");
        am.put("moveDownPress", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downPressed = true;
            }
        });

        im.put(KeyStroke.getKeyStroke("released S"), "moveDownRelease");
        am.put("moveDownRelease", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downPressed = false;
            }
        });

        // Left (A)
        im.put(KeyStroke.getKeyStroke("A"), "moveLeftPress");
        am.put("moveLeftPress", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed = true;
            }
        });

        im.put(KeyStroke.getKeyStroke("released A"), "moveLeftRelease");
        am.put("moveLeftRelease", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed = false;
            }
        });

        // Right (D)
        im.put(KeyStroke.getKeyStroke("D"), "moveRightPress");
        am.put("moveRightPress", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed = true;
            }
        });

        im.put(KeyStroke.getKeyStroke("released D"), "moveRightRelease");
        am.put("moveRightRelease", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed = false;
            }
        });
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
}
