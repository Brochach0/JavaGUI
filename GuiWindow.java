import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class GuiWindow extends JFrame implements ActionListener
{
private JTextArea ta;
private int count;
private JMenuBar menuBar;
private JMenu FileMenu,EditMenu;
private JScrollPane scrollpane;
private JMenuItem exitI,cutI,Copy,pasteI,selectI,saveI,loadI;
private String pad;
private JToolBar toolBar;
public GuiWindow()
{
    super("Document");
    setSize(1080, 640);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = getContentPane();
    pane.setLayout(new BorderLayout());

    count = 0;
    pad = " ";
    ta = new JTextArea(); 
    menuBar = new JMenuBar(); 
    FileMenu = new JMenu("File"); 
    EditMenu = new JMenu("Edit"); 
    scrollpane = new JScrollPane(ta); 
    exitI = new JMenuItem("Exit");
    cutI = new JMenuItem("Cut");
    Copy = new JMenuItem("Copy");
    pasteI = new JMenuItem("Paste");
    selectI = new JMenuItem("Select All");
    saveI = new JMenuItem("Save"); 
    loadI = new JMenuItem("Load"); 
    toolBar = new JToolBar();
    

    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);

    setJMenuBar(menuBar);
    menuBar.add(FileMenu);
    menuBar.add(EditMenu);

    FileMenu.add(saveI);
    FileMenu.add(loadI);
    FileMenu.add(exitI);

    EditMenu.add(cutI);
    EditMenu.add(Copy);
    EditMenu.add(pasteI);        
    EditMenu.add(selectI);

    saveI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    loadI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
    cutI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    pasteI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    selectI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

    pane.add(scrollpane,BorderLayout.CENTER);
    pane.add(toolBar,BorderLayout.SOUTH);

    saveI.addActionListener(this);
    loadI.addActionListener(this);
    exitI.addActionListener(this);
    cutI.addActionListener(this);
    Copy.addActionListener(this);
    pasteI.addActionListener(this);
    selectI.addActionListener(this);

    setVisible(true);
}
public void actionPerformed(ActionEvent e) 
{
    JMenuItem choice = (JMenuItem) e.getSource();
    if (choice == saveI)
    {
    
    }
    else if (choice == exitI)
        System.exit(0);
    else if (choice == cutI)
    {
        pad = ta.getSelectedText();
        ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
    }
    else if (choice == Copy)
        pad = ta.getSelectedText();
    else if (choice == pasteI)
        ta.insert(pad, ta.getCaretPosition());
    else if (choice == selectI)
        ta.selectAll();
    {
    	
    }
}
public static void main(String[] args) 
{
    new GuiWindow();
}
}
