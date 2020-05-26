package com.company;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class GUI implements Runnable {
    private JFrame frame;
    private JButton appendBtn;
    private JButton removeBtn;
    private JButton quickBtn;


    private JButton shellBtn;
    private JButton interBtn;

    private JTextField key;
    private JTextField value;
    private JLabel text_key;
    private JLabel text_value;
    private JTable table;
    private DefaultTableModel model;
    private HashWork hash;

    @Override
    public void run() {
        createGUI();
        layoutSelf();
        elementEvent();
    }

    private void elementEvent(){
        appendBtn.addActionListener(actionEvent -> {
            if (key.getText().equals("") || value.getText().equals("")) {
                String message = "Ключ не введён";
                JOptionPane.showMessageDialog(null, message);
            }else{
                if ((hash.inputData(key.getText())) && (hash.valid(value.getText()))) {
                    hash.appendElement(Integer.parseInt(key.getText()), value.getText());
                    Boolean st = true;
                    if (hash.isInitKey(Integer.parseInt(key.getText()))){
                        for (int i = 0; i < model.getRowCount(); i++){
                            if (table.getModel().getValueAt(i, 0).toString().equals(key.getText())){
                                model.setValueAt(value.getText(),i,1);
                                st = false;
                            }
                        }
                    }
                    if (st) {
                        model.addRow(new Object[]{key.getText(), value.getText()});
                    }
                    clearFields();
                }else{
                    JOptionPane.showMessageDialog(null, "Ошибка ввода");
                }
            }
        });

        removeBtn.addActionListener(actionEvent -> {
            try{
                int hover = table.getSelectedRow();
                int column = 0;
                String columnGet = table.getModel().getValueAt(hover, column).toString();
                model.removeRow(hover);
                if (hash.inputData(columnGet)){
                    hash.dropElement(Integer.parseInt(columnGet));
                    clearFields();
                }else{
                    JOptionPane.showMessageDialog(null,"Ключ не целое число");
                }
            }catch (ArrayIndexOutOfBoundsException e){
                JOptionPane.showMessageDialog(null,"Ключ не найден");
            }
        });

        shellBtn.addActionListener(actionEvent -> {
            hash.shellSort();
            showSortTable(hash.result_list);
        });

        interBtn.addActionListener(actionEvent -> {
            hash.iterSort();
            showSortTable(hash.result_list);
        });

        quickBtn.addActionListener(actionEvent -> {
            hash.quicSort();
            showSortTable(hash.result_list);
        });
    }

    private void createGUI(){
        frame = new JFrame("Сортировка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
        //frame.setSize(500, 530);
        frame.setMinimumSize(new Dimension(1100, 480));
        appendBtn  = new JButton("Add");
        removeBtn = new JButton("Delete");
        quickBtn = new JButton("Quick Sort");

        shellBtn = new JButton("Shell Sort");
        interBtn = new JButton("Intro Sort");
        key = new JTextField("",6);
        key.setMaximumSize(new Dimension(400, 20));
        value = new JTextField("", 6);
        value.setMaximumSize(new Dimension(400, 20));
        text_value = new JLabel("Value");
        text_key = new JLabel("Key");
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        table = new JTable(model);
        model.addColumn("Key");
        model.addColumn("Value");
        hash = new HashWork();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void layoutSelf(){
        Container container = frame.getContentPane();
        container.setLayout(new GridLayout());
        JPanel card_fields = new JPanel();
        card_fields.setMaximumSize(new Dimension(1100, 20));

        card_fields.setLayout(new FlowLayout());
        card_fields.add(text_key);
        card_fields.add(key);
        card_fields.add(text_value);
        card_fields.add(value);
        card_fields.add(appendBtn);
        card_fields.add(removeBtn);

        card_fields.add(shellBtn);
        card_fields.add(interBtn);
        card_fields.add(quickBtn);


        JPanel card_table = new JPanel(new GridLayout());
        JScrollPane scroll = new JScrollPane(table);
        card_table.add(scroll);


        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.add(card_fields);
        card.add(card_table);

        container.add(card);
    }

    private void showSortTable(Integer[] sort_arr){
        Integer rowCount = model.getRowCount();
        for (int i= rowCount -1; i >= 0; i--){
            model.removeRow(i);
        }
        for (int i = 0; i < sort_arr.length; i++){
            model.addRow(new Object[]{sort_arr[i], hash.hash.get(sort_arr[i])});
        }

    }


    private void clearFields(){
        key.setText("");
        value.setText("");
    }
}
