import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.util.List;

class UserInterface extends JFrame implements ActionListener
{
    //initialize button, panel, label, and text field
    JButton b1, b2, b3;
    JPanel newPanel, buttonPanel;
    JTextField textField1, textField2;
    JLabel firstLabel, endLabel;
    boolean first, second;
    int firstSize, secondSize;
    long timeElapsed1, timeElapsed2;

    //calling constructor
    UserInterface()
    {
        JPanel instructions = new JPanel();

        final JTextArea instr = new JTextArea("Welcome to BaconPedia! \n\n"
                + "Insert in a starting link and ending link (make sure it is the ENTIRE wikipedia link) \n" +
                "We will then construct a path from the first link to the ending link using only " +
                "other wikipedia links accessible from the page you are on!\n\n" +
                "Try with BFS will do this task with BFS and Try with DFS will do this task with DFS.\n" +
                "You can compare the results of the two by clicking Compare.\n" +
                "There is a time limit of 3 minutes on each - just like a typical wikiracer game!", 10, 50);
        JScrollPane scrollPane = new JScrollPane(instr);
        instructions.add(scrollPane);

        JButton start = new JButton("Start"); //set label to button
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

        setTitle("BaconPedia");         //set title to the login form
    }

    public void addComponentsToScreen(JPanel mainPanel) {
        firstLabel = new JLabel();
        firstLabel.setText("Starting Link");      //set label value for textField1


        textField1 = new JTextField(15);    //set length of the text

        endLabel = new JLabel();
        endLabel.setText("Ending Link");      //set label value for textField2

        textField2 = new JTextField(15);

        b1 = new JButton("Try with BFS!"); //set label to button
        b2 = new JButton("Try with DFS!"); //set label to button
        b3 = new JButton("Compare!");

        //create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(firstLabel);    //set username label to panel
        newPanel.add(textField1);   //set text field to panel
        newPanel.add(endLabel);    //set password label to panel
        newPanel.add(textField2);   //set text field to panel
        buttonPanel = new JPanel(new GridLayout(1, 3));
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);  //set button to panel

        //set border to panel
        mainPanel.add(newPanel);
        mainPanel.add(buttonPanel);


        //perform action on button click
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this); //add action listener to button

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

    public void testToActivateButton() {
        boolean enable = !textField1.getText().trim().isEmpty() && !textField2.getText().trim().isEmpty();
        boolean startsWith = textField1.getText().trim().startsWith("https://en.wikipedia.org") &&
                textField2.getText().trim().startsWith("https://en.wikipedia.org");
        b1.setEnabled(enable && startsWith);
        b2.setEnabled(enable && startsWith);
        b3.setEnabled(enable && startsWith);
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter
    {
        String firstLink = textField1.getText();        //get user entered first link from the textField1
        String secondLink = textField2.getText();

            if (ae.getSource() == b1) {
                DisplayResultsBFS d = new DisplayResultsBFS();
                d.setVisible(true);
                first = true;
                BFS b = new BFS();
                long start = System.currentTimeMillis();
                List<String> linksBFS = b.runBFS(firstLink, secondLink);
                long finish = System.currentTimeMillis();
                timeElapsed1 = (finish - start);


                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                JLabel heading = new JLabel("We ran BFS to go from the starting link to the ending link");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 3;
                panel.add(heading, c);

                if (linksBFS.size() > 0) {
                    firstSize = linksBFS.size();
                    JLabel title = new JLabel("It took " + linksBFS.size() + " links to get from the start to the end!");
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 1;
                    panel.add(title, c);
                    JLabel s = new JLabel("It took " + timeElapsed1 + " ms to get there");
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 2;
                    panel.add(s, c);


                    int y = 3;

                    for (String l : linksBFS) {
                        JLabel res = new JLabel(l);
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.gridx = 0;
                        c.gridy = y;

                        y += 1;
                        panel.add(res, c);
                    }
                } else {
                    JLabel title = new JLabel("We could not find the link in the given constraints");
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 1;
                    c.gridwidth = 200;
                    panel.add(title, c);
                }

                d.getContentPane().add(panel);


            } else if (ae.getSource() == b2) {
                DisplayResultsDFS d = new DisplayResultsDFS();
                d.setVisible(true);
                second = true;
                long start = System.currentTimeMillis();
                DFS dfs = new DFS();
                List<String> linksDFS = dfs.runDFS(firstLink, secondLink);
                long finish = System.currentTimeMillis();
                timeElapsed2 = (finish - start);

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                JLabel heading = new JLabel("We ran DFS to go from the starting link to the ending link");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 3;
                panel.add(heading, c);

                if (linksDFS.size() > 0) {
                    secondSize = linksDFS.size();
                    JLabel title = new JLabel("It took " + linksDFS.size() + " links to get from the start to the end!");
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 1;
                    panel.add(title, c);
                    JLabel s = new JLabel("It took " + timeElapsed2 + " ms to get there");
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 2;
                    panel.add(s, c);


                    int y = 3;

                    for (String l : linksDFS) {
                        JLabel res = new JLabel(l);
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.gridx = 0;
                        c.gridy = y;

                        y += 1;
                        panel.add(res, c);
                    }
                } else {
                    JLabel title = new JLabel("We could not find the link in the given constraints");
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 1;
                    c.gridwidth = 200;
                    panel.add(title, c);
                }
                d.getContentPane().add(panel);

            } else {
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

                    JLabel heading2 = new JLabel("BFS took " + timeElapsed1 + " ms. DFS took " + timeElapsed2 + " ms");
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

                    JLabel heading = new JLabel("Must try with BFS AND DFS first before comparing!");
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridwidth = 3;
                    panel.add(heading, c);
                }
                d.getContentPane().add(panel);
            }

        }


//create the main class

    //main() method start
    public static void main(String arg[])
    {
        try
        {
            UserInterface form = new UserInterface();
            form.setSize(800,250);  //set size of the frame
            form.setVisible(true);  //make form visible to the user
        }
        catch(Exception e)
        {
            //handle exception
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

//TODO: make the result page scrollable
//TODO: test the time limit
//TODO: can't compare if the link changes