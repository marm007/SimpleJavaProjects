import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class View {

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem resetItem;
    private JMenuItem exitItem;
    private JList<MathAct> list;
    private JTextArea textArea;
    private JTextField textField;
    private JButton jButton;
    private JScrollPane scrollPane;
    private JMenu menu;

    public View(String title) {

        DefaultListModel<MathAct> listModel = new DefaultListModel<>();
        listModel.addElement(new MathAct("sinus", "sin()"));
        listModel.addElement(new MathAct("cosinus", "cos()"));
        listModel.addElement(new MathAct("tangens", "tg()"));
        listModel.addElement(new MathAct("cotangens", "ctg()"));
        listModel.addElement(new MathAct("arcus sinus", "arsin()"));
        listModel.addElement(new MathAct("pi", "pi"));
        listModel.addElement(new MathAct("e", "e"));
        listModel.addElement(new MathAct("golden ratio", "[phi]"));
        listModel.addElement(new MathAct("+", "+"));
        listModel.addElement(new MathAct("-", "-"));
        listModel.addElement(new MathAct("*", "*"));
        listModel.addElement(new MathAct("last", "last"));

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridBagLayout());

        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        menuBar.add(menu);

        resetItem = new JMenuItem("Reset");
        exitItem = new JMenuItem("Exit");

        menu.add(resetItem);
        menu.add(exitItem);

        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(200, 200));

        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);

        textField = new JTextField();

        jButton = new JButton();
        jButton.setText("Evaluate!");

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;

        frame.getContentPane().add(menuBar, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;

        frame.getContentPane().add(scrollPane, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHEAST;

        frame.getContentPane().add(list, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.01;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.SOUTHEAST;

        frame.getContentPane().add(textField, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0.01;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.SOUTHEAST;

        frame.getContentPane().add(jButton, c);

        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JMenuItem getResetItem() {
        return resetItem;
    }

    public void setResetItem(JMenuItem resetItem) {
        this.resetItem = resetItem;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    public void setExitItem(JMenuItem exitItem) {
        this.exitItem = exitItem;
    }

    public JList<MathAct> getList() {
        return list;
    }

    public void setList(JList<MathAct> list) {
        this.list = list;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JButton getjButton() {
        return jButton;
    }

    public void setjButton(JButton jButton) {
        this.jButton = jButton;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JMenu getMenu() {
        return menu;
    }

    public void setMenu(JMenu menu) {
        this.menu = menu;
    }
}
