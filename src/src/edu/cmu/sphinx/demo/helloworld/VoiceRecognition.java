package src.edu.cmu.sphinx.demo.helloworld;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * This class accounts for the voice recognition in the program. The teachphrase method is invoked inside the GamePanel
 * when the user steps on a door. The class uses the Java speech API that is imported as an external JAR into the libraries folder.
 * 
 * @author David Tsenter, Carnegie Mellon University
 * @version 2.0
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */
public class VoiceRecognition {
    /**
     * Used to invoke methods in the WordRecognizer class.
     */
    static WordRecognizer wordRecognizer;
    /**
     * Used to store the text of the current sound file.
     */
    static String soundText;
    /**
     * Used to instantiate FlowLayout on the panels.
     */
    static FlowLayout fl;
    /**
     * Used to create a dialog box.
     */
    static JDialog d;
    /**
     * Used to store the number of the sound.
     */
    private static int soundNum;
    /**
     * Used to check if the word was said properly.
     */
    static boolean check = false;
    /**
     * Used to check if continous mode is on (listening to phrase).
     */
    static boolean continuousMode;
    /**
     * Used to keep track of the sound number.
     */
    static int i = 1;
    /**
     * Used to store the phrase said by user.
     */
    static String resultText;
    /**
     * The window that appears when the user steps on a door.
     */
    static JWindow frame;
    /**
     * The JPanel used to put the buttons on to.
     */
    static JPanel panel;
    /**
     * The buttons on the JWindow.
     */
    static JButton play, record;
    /**
     * Boolean used to keep track if it is the first time running the VoiceRecognition utilities.
     */
    static boolean first = true;
    /**
     * Label that appears on the JWindow.
     */
    static JLabel label1;

    /**
     * Used to open a dialog box once the user pronounced a phrase. The if statements in the method determine the message that
     * will appear on the dialog box.
     * @param close determines if the JWindow will be closed.
     */
    public static void dialog(final boolean close) {
       
        fl = new FlowLayout();
        String labelText;
        if (close) {
            labelText = "Congratulations! The altar has been unlocked";
        } else {
            labelText = "You have failed! Please try again...";
        }
        JFrame tmp;
        d = new JDialog(tmp = new JFrame(), "Result");
        record.setEnabled(false);
        tmp.dispose();
        d.setSize(400, 200);
        d.setResizable(false);
        d.setLayout(fl);
        d.requestFocus();
        d.repaint();
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        JButton button = new JButton("Close");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();

                //try{Thread.sleep(1000);}catch (InterruptedException ex){}
                if (close) {
                    frame.dispose();
                    check = false;
                } else {
                    record.setEnabled(true);
                }

            }
        });
        fl.setHgap(100);
        d.add(label);
        d.add(button);
        d.setLocationRelativeTo(frame);
        d.setVisible(true);

    }

    private static void playSound() {

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("Sounds/Sound" + ((Integer) soundNum).toString() + ".wav")));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }

    }

    /**
     * Creates the loading label while the user is waiting for the voice recognition utilities to initialize.
     */
    public static void loadingScreen() {
        label1 = new JLabel("Loading...");
        label1.setFont(new Font("Arial", 1, 24));
        panel.add(label1);
    }

    /**
     * Opens the JWindow containing the buttons for Voice Recognition.
     * @param continuousMode determines if the microphone is in the process of recording
     */
    public static void openDialog(boolean continuousMode) {
        fl = new FlowLayout();
        VoiceRecognition.continuousMode = continuousMode;
        frame = new JWindow();
        frame.setSize(600, 200);
        frame.setLocation(600, 300);
        frame.setVisible(true);
        panel = new JPanel();
        panel.setLayout(fl);
        fl.setHgap(250);
        play = new JButton("Play Sound");

        record = new JButton("Start Speaking");
        frame.add(panel);
        try {
            BufferedReader in = new BufferedReader(new FileReader("Texts/Phrase" + ((Integer) soundNum).toString() + "text.txt"));
            soundText = in.readLine();
            in.close();
        } catch (IOException e) {
        }
        JLabel label = new JLabel(soundText);
        label.setFont(new Font("Arial", 1, 14));
        JLabel label2 = new JLabel("Please speak loudly and clearly!");
        label2.setFont(new Font("Arial", 1, 16));
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Sound playing..");
                playSound();
                record.setEnabled(true);
            }
        });
        record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (record.isEnabled()) {
                    record.setEnabled(false);
                    startListening();


                }
            }
        });
        panel.add(label);
        panel.add(play);
        panel.add(record);
        panel.add(label2);
        record.setEnabled(false);
        panel.updateUI();
    }

    private static void startListening() {
        if (wordRecognizer.microphoneOn()) {
            System.out.println("Speak ...");
        } else {
            System.out.println(
                    "Sorry, can't find the microphone on your computer.");
        }
    }

    /**
     * Initializes the voice recognition utilities and starts up all processes relating to it.
     * The if statement makes sure that it is the first time that the player is attempting voice recognition
     * before initializing the utilities.
     */
    public static void go() {
        try {
            if (first) {
                play.setEnabled(false);
                loadingScreen();

                wordRecognizer = new WordRecognizer(first);

            }
            first = false;

            wordRecognizer.startup();

            play.setEnabled(true);
            wordRecognizer.addZipListener(new ZipListener() {
                @Override
                public void notify(String zip) {

                    wordRecognizer.microphoneOn();

                }
            });

            if (continuousMode) {
                startListening();
            } else {
                System.out.println("VoiceRecognition Version 2.0 - Ready");
                panel.remove(label1);
                panel.updateUI();
            }

        } catch (Throwable e) {
        }
    }

    /**
     * This method is the only method that is referenced in other parts of the
     * code. It constructs a JFrame, and displays a JLabel that says the phrase
     * the user needs to learn in order to pass the altar. The user must then
     * select the Listen option in order to hear the sound and learn it. Then,
     * the user gets the option to attempt and speak the phrase. If the user
     * succeeds, then the window closes and the altar is unlocked. Otherwise, he
     * must attempt to pronounce the word/phrase all over again.
     *
     * @param num The number of the sound/Altar
     * @param firstTime  whether or not this is the first time that the user is using voice recognition
     */
    public static void teachPhrase(int num, boolean firstTime) {

        first = firstTime;
        soundNum = num;


        openDialog(false);
        go();

    }
}