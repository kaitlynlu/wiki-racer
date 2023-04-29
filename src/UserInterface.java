import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.util.List;

class UserInterface extends JFrame implements ActionListener
{
    //initialize button, panel, label, and text field
    JButton b1, b2, b3;
    JPanel newPanel, buttonPanel;
    JLabel firstLabel, endLabel;
    final JTextField  textField1, textField2;
    boolean first, second;
    int firstSize, secondSize;

    //calling constructor
    UserInterface()
    {

        firstLabel = new JLabel();
        firstLabel.setText("Starting Link");      //set label value for textField1


        textField1 = new JTextField(15);    //set length of the text

        endLabel = new JLabel();
        endLabel.setText("Ending Link");      //set label value for textField2

        textField2 = new JTextField(15);

        //create submit button
        b1 = new JButton("Try with BFS!"); //set label to button
        b2 = new JButton("Try with DFS!"); //set label to button
        b3 = new JButton("Compare!"); //set label to button
        JPanel mainPanel = new JPanel();


        //create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(firstLabel);    //set username label to panel
        newPanel.add(textField1);   //set text field to panel
        newPanel.add(endLabel);    //set password label to panel
        newPanel.add(textField2);   //set text field to panel
        buttonPanel = new JPanel(new GridLayout(1, 3));

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);  //set button to panel

        //set border to panel
        mainPanel.add(newPanel);
        mainPanel.add(buttonPanel);
        add(mainPanel);

        //perform action on button click
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this); //add action listener to button
        setTitle("BaconPedia");         //set title to the login form
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter
    {
        String firstLink = textField1.getText();        //get user entered first link from the textField1
        String secondLink = textField2.getText();
        //get user entered second link from the textField2

        if (ae.getSource() == b1) {
            first = true;
            BFS b = new BFS();
            List<String> linksBFS = b.runBFS(firstLink, secondLink);

            DisplayResultsBFS d = new DisplayResultsBFS();
            d.setVisible(true);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel heading = new JLabel("heading");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 3;
            panel.add(heading, c);

            if (linksBFS.size() > 0) {
                JLabel title = new JLabel("It took " + linksBFS.size() + " links to get from the start to the end!");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;

                panel.add(title, c);
                int y = 2;

                for (String l : linksBFS) {
                    JLabel res = new JLabel(l);
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = y;

                    y += 1;
                    panel.add(res , c);
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

        }

        else if (ae.getSource() == b2) {
            second = true;
//
//            DFS dfs = new DFS();
//            List<String> linksDFS = dfs.runDFS(firstLink, secondLink);

            DisplayResultsDFS d = new DisplayResultsDFS();
            d.setVisible(true);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel heading = new JLabel("heading");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 3;
            panel.add(heading, c);
            d.getContentPane().add(panel);
        } else {
            DisplayComparison d = new DisplayComparison();
            d.setVisible(true);
            if (first && second) {

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                String label = "";
                if (firstSize > secondSize) {
                    label = "DFS performed better";
                } else if (firstSize < secondSize) {
                    label = "BFS performed better";
                } else {
                    label = "they performed the same!";
                }

                JLabel heading = new JLabel("BFS took " + firstSize + " links.\n DFS took " + secondSize + " links\m" +
                        "Therefore, " + label);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 3;
                panel.add(heading, c);
                d.getContentPane().add(panel);
            } else {
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                JLabel heading = new JLabel("Must try with BFS AND DFS first before comparing!");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 3;
                panel.add(heading, c);
                d.getContentPane().add(panel);
            }
        }
    }

//create the main class

    //main() method start
    public static void main(String arg[])
    {
        try
        {

            UserInterface form = new UserInterface();
            form.setSize(500,200);  //set size of the frame
            form.setVisible(true);  //make form visible to the user
        }
        catch(Exception e)
        {
            //handle exception
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}