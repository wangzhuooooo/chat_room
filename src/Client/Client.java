package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
/**
 * Created by WangZhuo on 2022/6/16.
 */


public class Client {

        public static void main(String[] args) {
            JFrame frame = new JFrame("登录");
            frame.setBounds(550,330,350,200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            frame.setResizable(false);
            frame.add(panel);
            login(panel,frame);
            frame.setVisible(true);
        }

        private static void login(JPanel panel,JFrame frame) {
            panel.setLayout(null);
            JLabel userLabel = new JLabel("请输入昵称:");
            userLabel.setBounds(10,20,80,25);
            panel.add(userLabel);

            JTextField userText = new JTextField(20);
            userText.setBounds(100,20,165,25);
            panel.add(userText);

            JButton loginButton = new JButton("登录");
            loginButton.setBounds(10, 80, 80, 25);
            panel.add(loginButton);
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame chat = new JFrame("你的网名是"+userText.getText());
                    chat.setResizable(false);
                    chat.show();
                    frame.setVisible(false);
                    chat.setBounds(300,200,800,600);
                    JPanel panel=new JPanel();
                    chat.add(panel);
                    try {
                        String username = userText.getText();
                        chatRoom(panel,username);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    chat.setVisible(true);

                }
            });
        }

        private static void chatRoom(JPanel panel ,String userName) throws IOException {



            panel.setLayout(null);
            JTextArea output = new JTextArea();
            output.setBounds(0,0,785,500);
            output.setFont(new Font("黑体",Font.BOLD,20));
            output.setBackground(Color.lightGray);
            output.setLineWrap(true);
            output.setEditable(false);
            JScrollPane scro =new JScrollPane(output);
            scro.setSize(785,500);
            scro.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel.add(scro);
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");


            JTextArea input = new JTextArea();
            input.setFont(new Font("黑体",Font.BOLD,20));
            input.setBounds(0,501,700,200);
            input.setLineWrap(true);
            panel.add(input);




            Socket s1 = new Socket("127.0.0.1",2056);
            DataInputStream dis = new DataInputStream(s1.getInputStream());
            DataOutputStream dos = new DataOutputStream(s1.getOutputStream());
            Thread mrc = new ClientReader(dis,output);
            //Thread mcw = new MyClientWriter(dos,input);
            mrc.start();
            //mcw.start();
            dos.writeUTF(userName);

            JButton send =new JButton("发送");
            send.setBounds(701,500,80,30);
            send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        dos.writeUTF(input.getText());
                        output.append(ft.format(dNow)+" "+"我:"+input.getText()+"\n");
                        input.setText("");

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            });
            panel.add(send);

            JButton clear = new JButton("清空");
            clear.setBounds(701,530,80,30);
            clear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    input.setText("");
                }
            });
            panel.add(clear);
        }


}
