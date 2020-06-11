package thamdinh;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.UIManager;


import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.Player;

public class mp3Player implements ActionListener {
    JFrame frame;
    private JTextField pathField;

    private File songFile;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private int totalLength;
    private int lastPosition;
    private Player player;
    private boolean isPlaying = false;
    private boolean isPause = false;
    
    
    private JButton stop;
    private JButton start;
    private JButton pause;
    private JButton resume;
    private JButton downvolume;
    private JButton upvolume;
    private JButton open;
    private JFileChooser chooser;
    
    public mp3Player() {
        initialize();

    }


    private void initialize() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame();
        frame.setIconImage(new ImageIcon("a.jpg").getImage());



        frame.setTitle("MP3 Player");
        frame.setBounds(100,100,550,230);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);


        frame.getContentPane().setBackground(Color.CYAN);




        start = new JButton("PLAY");
        start.addActionListener(this);
        start.setFont(new Font("Tahoma",Font.PLAIN,16));
        start.setBounds(10,50,100,50);
        start.setBackground(Color.BLUE);
        start.setForeground(Color.BLUE);
        frame.getContentPane().add(start);

        stop = new JButton("STOP");
        stop.addActionListener(this);
        stop.setFont(new Font("Tahoma",Font.PLAIN,16));
        stop.setBounds(110,50,100,50);
        stop.setBackground(Color.RED);
        stop.setForeground(Color.RED);
        frame.getContentPane().add(stop);

        pause = new JButton("PAUSE");
        pause.setFont(new Font("Tahoma",Font.PLAIN,16));
        pause.setBounds(210,50,100,50);
        pause.setBackground(Color.PINK);
        pause.setForeground(Color.PINK);
        pause.addActionListener(this);
        frame.getContentPane().add(pause);

        resume = new JButton("RESUME");
        resume.setFont(new Font("Tahoma",Font.PLAIN,16));
        resume.setBounds(310,50,100,50);
        resume.setBackground(Color.GREEN);
        resume.setForeground(Color.GREEN);
        resume.addActionListener(this);
        frame.getContentPane().add(resume);

        

        pathField = new JTextField();
        pathField.setForeground(Color.MAGENTA);
        pathField.setBackground(Color.WHITE);
        pathField.setEditable(false);
        pathField.setText("Song Path");
        pathField.setBounds(10,10,410,40);
        frame.getContentPane().add(pathField);
        pathField.setColumns(10);

        open = new JButton("MENU");
        open.setBackground(Color.GRAY);
        open.setForeground(Color.GRAY);
        open.addActionListener(this);
        open.setBounds(420,10,60,40);
        frame.getContentPane().add(open, BorderLayout.PAGE_START);
    }
    private void open() {
        try {
            chooser = new JFileChooser();
            chooser.setDialogTitle("Choose song to load....");
            chooser.showOpenDialog(null);
            songFile = chooser.getSelectedFile();
            pathField.setText(songFile.getName());
            if(!songFile.getName().endsWith(".mp3")) {
                JOptionPane.showMessageDialog(null, "Invalid File Type Selected!","Error",JOptionPane.ERROR_MESSAGE);
                open();
            }
            System.out.println("File " + songFile.getName() +", Selected! ");

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton action = (JButton) e.getSource();
            if(action == start){
                start();
            } else if(action == stop){
                stop();
            } else if(action == open){
                open();
            } else if(action == pause){
                pause();
            } else if(action == resume){
                resume();
            }
        }
    }

    private void stop(){
        if(isPlaying){
            player.close();
            lastPosition= totalLength;
            isPlaying = false;
        }
    }

    private void pause(){
        if (isPlaying) {
            try {
                lastPosition = fileInputStream.available();
                player.close();
                isPlaying = false;
                isPause = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void resume(){
        if(isPause&&!isPlaying){
            try {
                fileInputStream = new FileInputStream(songFile);
                totalLength = fileInputStream.available();
                fileInputStream.skip(totalLength - lastPosition);
                player = new Player(bufferedInputStream = new BufferedInputStream(fileInputStream));
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
            beginPlaying();
        }
    }

    private void start(){
        if(!isPlaying){
        try {
            fileInputStream = new FileInputStream(songFile);
            totalLength = fileInputStream.available();
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            player = new Player(bufferedInputStream);
            beginPlaying();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JavaLayerException ex) {
            ex.printStackTrace();
        }
        } else{
            stop();
            start();
        }
    }

    private void beginPlaying(){
        new Thread(()->{
            try{
                isPlaying = true;
                player.play();
            }catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }





}
