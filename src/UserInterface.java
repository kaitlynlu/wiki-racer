import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.util.List;

//create CreateLoginForm class to create login form
//class extends JFrame to create a window where our component add
//class implements ActionListener to perform an action on button click
class UserInterface extends JFrame implements ActionListener
{
    //initialize button, panel, label, and text field
    JButton b1;
    JPanel newPanel;
    JLabel firstLabel, endLabel;
    final JTextField  textField1, textField2;

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
        b1 = new JButton("GO!"); //set label to button

        //create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(firstLabel);    //set username label to panel
        newPanel.add(textField1);   //set text field to panel
        newPanel.add(endLabel);    //set password label to panel
        newPanel.add(textField2);   //set text field to panel
        newPanel.add(b1);           //set button to panel

        //set border to panel
        add(newPanel, BorderLayout.CENTER);

        //perform action on button click
        b1.addActionListener(this);     //add action listener to button
        setTitle("BaconPedia");         //set title to the login form
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter
    {
        String firstLink = textField1.getText();        //get user entered first link from the textField1
        String secondLink = textField2.getText();        //get user entered second link from the textField2
        BFS b = new BFS();
        List<String> links = b.runBFS(firstLink, secondLink);
        DisplayResults d = new DisplayResults();
        d.setVisible(true);

        JLabel title = new JLabel("It took " + links.size() + " links to get from the start to the end!");

        JPanel panel = new JPanel(new GridLayout(0, 1)); //use gridlayout
        panel.add(title);
        for (String l : links) {
            JLabel res = new JLabel(l);
            panel.add(res);
        }
        d.getContentPane().add(panel);



    }

//create the main class

    //main() method start
    public static void main(String arg[])
    {
        try
        {
            //create instance of the CreateLoginForm
            UserInterface form = new UserInterface();
            form.setSize(300,100);  //set size of the frame
            form.setVisible(true);  //make form visible to the user
        }
        catch(Exception e)
        {
            //handle exception
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}