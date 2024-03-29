import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

class UserInterface extends JFrame implements ActionListener {
    JButton b1, b2, b3;
    JPanel newPanel, buttonPanel;
    JTextField textField1, textField2;
    JLabel firstLabel, endLabel;
    boolean first, second;
    int firstSize, secondSize;
    long timeElapsed1, timeElapsed2;

    //calling constructor
    UserInterface() {
        setDefaultCloseOperation(javax.swing.
                WindowConstants.DISPOSE_ON_CLOSE);
        JPanel instructions = new JPanel();

        final JTextArea instr = new JTextArea("Welcome to BaconPedia! \n\n"
                + "Insert in a starting link and ending link (make sure it is the ENTIRE " +
                "wikipedia link) \n" +
                "We will then construct a path from the first link to the ending link using only " +
                "other wikipedia links accessible from the page you are on!\n\n" +
                "Try with BFS will do this task with BFS and Try with DFS will do this task " +
                "with DFS.\n" +
                "You can compare the results of the two by clicking Compare.\n" +
                "There is a time limit of 3 minutes on each - just like a typical wikiracer " +
                "game!", 10, 50);
        JScrollPane scrollPane = new JScrollPane(instr);
        instructions.add(scrollPane);

        JButton start = new JButton("Start");

        //hide instruction page, show main page
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel mainPanel = new JPanel();
                addComponentsToScreen(mainPanel);
                remove(instructions);
                add(mainPanel);
                pack();
            }
        });
        instructions.add(start);
        add(instructions);

        setTitle("BaconPedia");
    }

    /**
     * Adds the main components to the panel. Shown when the user presses start
     *
     * @param mainPanel the panel to add elements to
     */
    public void addComponentsToScreen(JPanel mainPanel) {
        firstLabel = new JLabel();
        firstLabel.setText("Starting Link");
        textField1 = new JTextField(15);

        endLabel = new JLabel();
        endLabel.setText("Ending Link");
        textField2 = new JTextField(15);

        b1 = new JButton("Try with BFS!");
        b2 = new JButton("Try with DFS!");
        b3 = new JButton("Compare!");

        //create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(firstLabel);
        newPanel.add(textField1);
        newPanel.add(endLabel);
        newPanel.add(textField2);
        //button panel to put buttons on
        buttonPanel = new JPanel(new GridLayout(1, 3));
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);

        mainPanel.add(newPanel);
        mainPanel.add(buttonPanel);

        //perform action on button click
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        //add documents listeners to the text fields so we can disable the buttons unless the
        //the appropriate input is put in
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                testToActivateButton();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                testToActivateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                testToActivateButton();
            }
        });

        textField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                testToActivateButton();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                testToActivateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                testToActivateButton();
            }
        });


    }

    /**
     * enables the buttons if both text fields are non empty and are full wikipedia links
     */
    public void testToActivateButton() {
        boolean enable = !textField1.getText().trim().isEmpty() &&
                !textField2.getText().trim().isEmpty();
        boolean startsWith = textField1.getText().trim().startsWith("https://en.wikipedia.org") &&
                textField2.getText().trim().startsWith("https://en.wikipedia.org");
        b1.setEnabled(enable && startsWith);
        b2.setEnabled(enable && startsWith);
        b3.setEnabled(enable && startsWith);
        first = false;
        second = false;
    }


    /**
     * performs an action depending on which button is pressed
     *
     * @param ae the action event
     */
    public void actionPerformed(ActionEvent ae) {
        String firstLink = textField1.getText();
        String secondLink = textField2.getText();

        if (ae.getSource() == b1) {
            DisplayResultsBFS d = new DisplayResultsBFS();
            d.setVisible(true);

            first = true;

            //runs BFS
            BFS b = new BFS();
            long start = System.currentTimeMillis();
            List<String> linksBFS = b.runBFS(firstLink, secondLink);
            long finish = System.currentTimeMillis();
            timeElapsed1 = (finish - start);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel heading = new JLabel("We ran BFS to go from the " +
                    "starting link to the ending link");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 3;
            panel.add(heading, c);

            //if BFS found a result
            if (linksBFS.size() > 0) {
                firstSize = linksBFS.size();
                JLabel title = new JLabel("It took " + linksBFS.size() + " links to get " +
                        "from the start to the end!");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                panel.add(title, c);
                JLabel s = new JLabel("It took " + timeElapsed1 + " ms to get there");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 2;
                panel.add(s, c);
                JLabel div = new JLabel("-----------------------------------------");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 3;
                panel.add(div, c);

                int y = 4;

                for (String l : linksBFS) {
                    JLabel res = new JLabel(l);
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = y;

                    y += 1;
                    panel.add(res, c);
                }
                //adds it to a scroll pane so the entire result can be shown
                JScrollPane scrollPane = new JScrollPane(panel);
                scrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                d.getContentPane().add(scrollPane);
            } else {
                JLabel title = new JLabel("We could not find the link in the given time limit");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 200;
                panel.add(title, c);
                d.getContentPane().add(panel);
            }

        } else if (ae.getSource() == b2) {
            DisplayResultsDFS d = new DisplayResultsDFS();
            d.setVisible(true);
            second = true;
            long start = System.currentTimeMillis();

            //runs the DFS
            DFS dfs = new DFS();
            List<String> linksDFS = dfs.runDFS(firstLink, secondLink);
            long finish = System.currentTimeMillis();
            timeElapsed2 = (finish - start);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel heading = new JLabel("We ran DFS to go from the " +
                    "starting link to the ending link");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 3;
            panel.add(heading, c);

            if (linksDFS.size() > 0) { //if DFS found a path
                secondSize = linksDFS.size();
                JLabel title = new JLabel("It took " + linksDFS.size() + " links to get " +
                        "from the start to the end!");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                panel.add(title, c);
                JLabel s = new JLabel("It took " + timeElapsed2 + " ms to get there");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 2;
                panel.add(s, c);
                JLabel div = new JLabel("-----------------------------------------");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 3;
                panel.add(div, c);

                int y = 4;

                for (String l : linksDFS) {
                    JLabel res = new JLabel(l);
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = y;

                    y += 1;
                    panel.add(res, c);
                }
                JScrollPane scrollPane = new JScrollPane(panel);
                scrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                d.getContentPane().add(scrollPane);
            } else {
                JLabel title = new JLabel("We could not find the link in the " +
                        "given time limit");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 200;
                panel.add(title, c);
                d.getContentPane().add(panel);
            }


        } else { //comparison button was hit
            DisplayComparison d = new DisplayComparison();
            d.setVisible(true);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            if (first && second) {

                String label = "";
                if ((firstSize > secondSize) && secondSize > 0) {
                    label = "DFS performed better";
                } else if ((firstSize < secondSize) && firstSize > 0) {
                    label = "BFS performed better";
                } else {
                    label = "they performed the same!";
                }
                String numBFS = "";
                if (firstSize > 0) {
                    numBFS = "BFS took " + firstSize + " links.";
                } else {
                    numBFS = "BFS could not find the solution.";
                }

                String numDFS = "";
                if (secondSize > 0) {
                    numDFS = "DFS took " + secondSize + " links.";
                } else {
                    numDFS = "DFS could not find the solution.";
                }

                JLabel heading = new JLabel(numBFS + " " + numDFS);

                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 3;
                panel.add(heading, c);

                JLabel res = new JLabel("Therefore, " + label);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                panel.add(res, c);

                JLabel blank = new JLabel("   ");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 2;
                panel.add(blank, c);

                String label2 = "";
                if (timeElapsed1 > timeElapsed2) {
                    label2 = "DFS performed better";
                } else if (timeElapsed1 < timeElapsed2) {
                    label2 = "BFS performed better";
                } else {
                    label2 = "they performed the same!";
                }

                JLabel heading2 = new JLabel("BFS took " + timeElapsed1 +
                        " ms. DFS took " + timeElapsed2 + " ms");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 3;
                c.gridwidth = 3;
                panel.add(heading2, c);
                JLabel res2 = new JLabel("Therefore, " + label2);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 4;
                c.gridwidth = 3;
                panel.add(res2, c);

            } else {
                JLabel heading = new JLabel("Must try with BFS AND DFS first " +
                        "(with the same set of links) before comparing!");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 3;
                panel.add(heading, c);
            }
            d.getContentPane().add(panel);
        }

    }

    public static void main(String[] arg) {
        try {
            UserInterface form = new UserInterface();
            form.setSize(800,250);  //set size of the frame
            form.setVisible(true);  //make form visible to the user

        } catch (Exception e) {
            //handle exception
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
}

