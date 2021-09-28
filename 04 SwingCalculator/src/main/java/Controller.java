import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;

public class Controller {

    private View view;
    private Stack<String> history;

    public Controller(View v) {
        view = v;
        this.history = new Stack<>();
    }

    public void initController() {
        view.getjButton().addActionListener(evaluate);
        view.getTextField().addKeyListener(upKeyClick);
        view.getList().addMouseListener(selectedElement);
        view.getResetItem().addActionListener(reset);
        view.getExitItem().addActionListener(exit);
    }

    private void evaluate() {
        String mathAct = view.getTextField().getText();
        Expression expression = new Expression(mathAct);

        if (expression.checkSyntax()) {
            history.push(mathAct);
            mXparser.consolePrintln(expression.calculate());
            view.getTextArea().append(java.text.MessageFormat.format("{0}\n                    = {1}\n*******\n",
                    expression.getExpressionString(), expression.calculate()));
            view.getTextField().setText("");
        } else {
            JOptionPane.showMessageDialog(null, expression.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private AbstractAction evaluate = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            evaluate();
        }
    };

    private KeyListener upKeyClick = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (!history.empty())
                    view.getTextField().setText(history.pop());
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                evaluate();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    private MouseListener selectedElement = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {

                int caretPosition = view.getTextField().getCaretPosition();

                String selectedValue = view.getList().getSelectedValue().getCode();

                if (selectedValue.equals("last")) {

                    if (history.empty())
                        return;

                    String mathAct = history.pop();
                    Expression expression = new Expression(mathAct);

                    mXparser.consolePrintln(expression.calculate());
                    view.getTextArea()
                            .append(java.text.MessageFormat.format("{0}\n                    = {1}\n*******\n",
                                    expression.getExpressionString(), expression.calculate()));
                    view.getTextField().setText("");

                } else {
                    if (selectedValue.contains("(")) {
                        String textFieldString = view.getTextField().getText();
                        textFieldString = textFieldString.substring(0, caretPosition) + selectedValue
                                + textFieldString.substring(caretPosition);

                        view.getTextField().setText(textFieldString);

                        caretPosition = caretPosition + view.getList().getSelectedValue().getCode().length() - 1;
                    } else {
                        String textFieldString = view.getTextField().getText();
                        textFieldString = textFieldString.substring(0, caretPosition) + selectedValue
                                + textFieldString.substring(caretPosition);

                        view.getTextField().setText(textFieldString);

                        caretPosition = caretPosition + view.getList().getSelectedValue().getCode().length();
                    }
                }

                view.getTextField().setEnabled(true);
                view.getTextField().requestFocus();
                view.getTextField().setCaretPosition(caretPosition);

            }
        }
    };

    private AbstractAction reset = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getTextArea().setText("");
            view.getTextField().setText("");
        }
    };

    private AbstractAction exit = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
}
