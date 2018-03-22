import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;

import org.mariuszgromada.math.mxparser.*;
import static java.lang.System.out;

public class CalculatorView implements Runnable,PropertyChangeListener {
    private JTextField formulaInput;
    private static JFrame frame;
    private JScrollPane scrollContainerPane;
    private JTextArea historyTextArea;
    private JButton evalButton;
    private JPanel panelMain;
    private JScrollPane scrollContainerPanel;
    private JList functionList;
    private String lastInput;
    private String lastResult;

    public CalculatorView() {
        functionList.setModel(createFunctionList());
        evalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    lastResult=evaluateFormula(formulaInput.getText());
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "MessageError", + JOptionPane.ERROR_MESSAGE);
                }
                String printableResult = MessageFormat.format("{0} \n \t = {1} \n ************\n",formulaInput.getText(),lastResult);
                if(lastResult!="empty")
                {
                    historyTextArea.setText(historyTextArea.getText() + printableResult);
                    lastInput = formulaInput.getText();
                    formulaInput.setText("");
                }
                formulaInput.setText("");
            }
        });
        functionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int braceIndex=0;
                if(e.getClickCount()%2==0 && e.getClickCount()>0)
                {
                    switch(functionList.getSelectedIndex()){
                        case 0:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"sin()",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            braceIndex = formulaInput.getText().lastIndexOf("(");
                            formulaInput.setCaretPosition(braceIndex+1);
                            break;
                        case 1:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"cos()",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            braceIndex = formulaInput.getText().lastIndexOf("(");
                            formulaInput.setCaretPosition(braceIndex+1);
                            break;
                        case 2:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"tan()",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            braceIndex = formulaInput.getText().lastIndexOf("(");
                            formulaInput.setCaretPosition(braceIndex+1);
                            break;
                        case 3:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"log(,)",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            braceIndex = formulaInput.getText().lastIndexOf("(");
                            formulaInput.setCaretPosition(braceIndex+1);
                            break;
                        case 4:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"mod(,)",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            braceIndex = formulaInput.getText().lastIndexOf("(");
                            formulaInput.setCaretPosition(braceIndex+1);
                            break;
                        case 5:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"round(,)",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            braceIndex = formulaInput.getText().lastIndexOf("(");
                            formulaInput.setCaretPosition(braceIndex+1);
                            break;
                        case 6:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"pi",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case 7:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"e",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case 8:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),"[gam]",null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case 9:
                            try {
                                formulaInput.getDocument().insertString(formulaInput.getCaretPosition(),lastResult,null);
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                            break;
                    }
                    formulaInput.requestFocus();
                }
            }
        });
        formulaInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                    switch(e.getExtendedKeyCode())
                    {
                        case KeyEvent.VK_UP:
                            formulaInput.setText(lastInput);
                            break;
                        case KeyEvent.VK_ENTER:
                            try {
                                lastResult=evaluateFormula(formulaInput.getText());
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "MessageError", + JOptionPane.ERROR_MESSAGE);
                            }
                            String printableResult = MessageFormat.format("{0} \n \t = {1} \n ************\n",formulaInput.getText(),lastResult);
                            if(lastResult!="empty")
                            {
                                historyTextArea.setText(historyTextArea.getText() + printableResult);
                                lastInput = formulaInput.getText();
                                formulaInput.setText("");
                            }

                            break;
                    }
            }
        });
    }

    public void propertyChange(PropertyChangeEvent evt) {
        String itemName = evt.getPropertyName();
    }

    public void run() {
        initialize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CalculatorView());
    }

    private void initialize() {
        frame = new JFrame("View");
        frame.setContentPane(this.panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setMenuBar(createMenu());
        frame.setVisible(true);
    }

    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");
        createMenuItems(menu);
        menuBar.add(menu);
        return menuBar;
    }

    private void createMenuItems(Menu menu) {
        MenuItem reset = new MenuItem("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                historyTextArea.setText("");
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        menu.add(reset);
        menu.add(exit);
    }

    private DefaultListModel createFunctionList(){
        Function sin = new Function("sinus","sin()");
        Function cos = new Function("cosinus","cos()");
        Function tan = new Function("tanges","tan()");
        Function log = new Function("logarithm ","log(,)");
        Function mod = new Function("modulo","mod(,)");
        Function round = new Function("round","round(,)");
        Function pi = new Function("pi","pi");
        Function e = new Function("e","e");
        Function eulerMascheroni = new Function("Euler-Mascheroni","[gam]");
        Function lastResult = new Function("lastResult","ls");
        DefaultListModel<Function> listModel = new DefaultListModel<>();
        listModel.addElement(sin);
        listModel.addElement(cos);
        listModel.addElement(tan);
        listModel.addElement(log);
        listModel.addElement(mod);
        listModel.addElement(round);
        listModel.addElement(pi);
        listModel.addElement(e);
        listModel.addElement(eulerMascheroni);
        listModel.addElement(lastResult);
        return listModel;
    }

    private String evaluateFormula(String formula) throws Exception {
        Expression expression = new Expression(formula);
        Double results=0.0;
        if (expression.checkSyntax()) {
            results=expression.calculate();
            return results.toString();
        }
        else if(!expression.getExpressionString().isEmpty()) {
            String errorMessage = expression.getErrorMessage();
            throw new Exception(errorMessage);
        }
        return "empty";
    }
}