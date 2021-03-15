package chatting.application.Clients;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class UserOne extends JFrame implements ActionListener,Runnable{
    JLabel name;
    static JTextArea textArea;
    JPanel textPanel;
    JTextField textField;
    JButton send;

    DataInputStream din;
    DataOutputStream dout;


    public UserOne(){
        this.setTitle("Chat Frame(Client)");
        this.setLayout(null);
        this.setSize(450,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(400,200);

        name = new JLabel("UserOne");
        name.setBounds(50,0,350,50);

        textArea = new JTextArea();
        textArea.setBounds(50,50,350,500);
        textArea.setText("");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textPanel =  new JPanel();
        textPanel.setBounds(10,600,430,30);
        textPanel.setLayout(null);

        textField = new JTextField();
        textField.setBounds(0,0,300,30);
        textPanel.add(textField);

        send = new JButton("SEND");
        send.setFocusable(false);
        send.setBounds(330,0,100,30);
        send.addActionListener(this);
        textPanel.add(send);


        this.add(name);
        this.add(textArea);
        this.add(textPanel);
        this.setVisible(true);

        try{
            Socket socket = new Socket("localhost",2021);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String str = "UserOne:"+textField.getText();
        try{
            dout.writeUTF(str);
            dout.flush();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        textField.setText("");

    }
    @Override
    public void run() {
        try{
            String msg ="";
            while(true){
                msg = din.readUTF();
                textArea.setText(textArea.getText()+"\n"+msg);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserOne one = new UserOne();
        Thread t1 = new Thread(one);
        t1.start();
    }


}
