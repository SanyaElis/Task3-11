package ru.vsu.cs.eliseev;

import ru.vsu.cs.eliseev.util.ArrayUtils;
import ru.vsu.cs.eliseev.util.JTableUtils;
import ru.vsu.cs.eliseev.util.SwingUtils;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainForm extends JFrame {
    private JPanel panelMain;
    private JButton buttonMaze1;
    private JButton buttonMaze2;
    private JButton buttonMaze3;
    private JButton buttonMazeBFS;
    private JTable tableInput;
    private JTable tableOutput;
    private JButton buttonChooseFromFile;
    private JButton buttonWriteToFile;
    private JTextField textFieldStartRow;
    private JTextField textFieldStartCol;
    private JButton buttonMazeBFS2;
    private JTable tableOutput2;

    private final JFileChooser fileChooserOpen;
    private final JFileChooser fileChooserSave;

    public static final Color EMPTY_CELL_COLOR = UIManager.getColor("TextField.background");
    public static final Color WALL_CELL_COLOR = Color.RED;
    public static final Color START_CELL_COLOR = Color.YELLOW;
    public static final Color VISITED_CELL_COLOR = Color.GREEN;
    public static final Color ERROR_CELL_COLOR = Color.RED;

    private static final int[][][] MAZES = {
            {
                    {0, 0, -1, 0, 0},
                    {0, -1, 0, 0, 0},
                    {0, -1, 0, -1, 0},
                    {0, 0, -1, -1, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, -1, 0, 0, 0, 0},
                    {0, -1, -1, 0, -1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, -1, -1, -1, 0, 0, 0},
                    {0, 0, 0, 0, 0, -1, 0, 0},
                    {0, 0, 0, 0, 0, -1, 0, 0}
            },
            {

                    {0, 0, 0, -1, -1, 0, 0},
                    {0, 0, 0, -1, 0, 0, 0},
                    {0, -1, 0, -1, 0, 0, 0},
                    {0, -1, 0, 0, -1, 0, 0},
                    {0, -1, -1, -1, -1, -1, 0},
                    {0, 0, 0, 0, 0, 0, 0}
            }
    };

    public MainForm(String title) throws HeadlessException {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.pack();


        JTableUtils.initJTableForArray(tableInput, 40, true, true, true, true);
        JTableUtils.initJTableForArray(tableOutput, 40, true, true, false, false);
        JTableUtils.initJTableForArray(tableOutput2, 40, true, true, false, false);

        tableInput.setRowHeight(25);
        tableOutput.setRowHeight(25);
        tableOutput2.setRowHeight(25);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");


        class MazeCellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Color color = EMPTY_CELL_COLOR;
                try {
                    int intVal = Integer.parseInt(value.toString());
                    if (intVal < 0) {
                        color = WALL_CELL_COLOR;
                    } else if (intVal == 1) {
                        color = START_CELL_COLOR;
                    } else if (intVal > 0) {
                        color = VISITED_CELL_COLOR;
                    }
                } catch (Exception e) {
                    color = ERROR_CELL_COLOR;
                }
                comp.setBackground(color);
                return comp;
            }
        }

        tableInput.setDefaultRenderer(Object.class, new MazeCellRenderer());
        tableOutput.setDefaultRenderer(Object.class, new MazeCellRenderer());
        tableOutput2.setDefaultRenderer(Object.class, new MazeCellRenderer());


        JTableUtils.writeArrayToJTable(tableInput, MAZES[0]);
        buttonMaze1.addActionListener(e -> JTableUtils.writeArrayToJTable(tableInput, MAZES[0]));
        buttonMaze2.addActionListener(e -> JTableUtils.writeArrayToJTable(tableInput, MAZES[1]));
        buttonMaze3.addActionListener(e -> JTableUtils.writeArrayToJTable(tableInput, MAZES[2]));


        buttonMazeBFS.addActionListener(e -> {
            try {
                int[][] maze = JTableUtils.readIntMatrixFromJTable(tableInput);
                int startRow = Integer.parseInt(textFieldStartRow.getText());
                int startCol = Integer.parseInt(textFieldStartCol.getText());
                assert maze != null;
                BFSFind.bfsPathFoundSimpleQueue(startRow, startCol, maze);
                JTableUtils.writeArrayToJTable(tableOutput, maze);
            } catch (Exception except) {
                SwingUtils.showInfoMessageBox("ERROR", "error");
            }
        });

        buttonMazeBFS2.addActionListener(e -> {
            try {
                int[][] maze = JTableUtils.readIntMatrixFromJTable(tableInput);
                int startRow = Integer.parseInt(textFieldStartRow.getText());
                int startCol = Integer.parseInt(textFieldStartCol.getText());
                assert maze != null;
                BFSFind.bfsPathFoundJavaQueue(startRow, startCol, maze);
                JTableUtils.writeArrayToJTable(tableOutput2, maze);
            } catch (Exception except) {
                SwingUtils.showInfoMessageBox("ERROR", "error");
            }
        });

        buttonChooseFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[][] arr = ArrayUtils.readIntArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput, arr);
                }
            } catch (Exception e) {
                SwingUtils.showInfoMessageBox("Некорректный файл", "ERROR");
            }
        });

        buttonWriteToFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[][] array = JTableUtils.readIntMatrixFromJTable(tableInput);
                    String file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) {
                        file += ".txt";
                    }
                    ArrayUtils.writeArrayToFile(file, array);
                }
            } catch (Exception e) {
                SwingUtils.showInfoMessageBox("Некорректные данные", "Ошибка сохранения");
            }
        });

        tableInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableInput.rowAtPoint(e.getPoint());
                int col = tableInput.columnAtPoint(e.getPoint());
                if (SwingUtilities.isLeftMouseButton(e)) {
                    tableInput.setValueAt(-1, row, col);
                } else if(SwingUtilities.isRightMouseButton(e)){
                    tableInput.setValueAt(0, row, col);
                }
            }
        });
    }
}
