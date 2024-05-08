import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.io.IOException;

public class GUI extends JFrame {
    private JPanel contentPane;
    private JTextField techNum;
    private JTextField cleanTime;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */


    public GUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(e -> {
            int TechNum = setTechNum();
            double CleanTime = setCleanTime();
            try {
                BusStation t = new BusStation("src/buses", TechNum, CleanTime);
                t.startDay();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
        btnStart.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        btnStart.setBounds(114, 163, 89, 31);
        btnStart.setForeground(Color.GREEN);
        contentPane.add(btnStart);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> System.exit(0));

        JLabel lblNewLabel = new JLabel("Welcome to Fatma Travel agency");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        lblNewLabel.setBounds(10, 26, 426, 31);
        contentPane.add(lblNewLabel);

        JLabel tech_Number = new JLabel("Number of technical Attendants:");
        tech_Number.setFont(new Font("Tahoma", Font.BOLD, 14));
        tech_Number.setBounds(92, 77, 350, 19);
        contentPane.add(tech_Number);

        JLabel clean_Time = new JLabel("Work time for cleaners:");
        clean_Time.setFont(new Font("Tahoma", Font.BOLD, 14));
        clean_Time.setBounds(92, 112, 350, 19);
        contentPane.add(clean_Time);

        techNum = new JTextField();
        techNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
        techNum.setText("1");
        techNum.setBackground(new Color(255, 255, 255));
        techNum.setBounds(350, 79, 32, 20);
        contentPane.add(techNum);
        techNum.setColumns(10);

        cleanTime = new JTextField();
        clean_Time.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cleanTime.setText("2");
        cleanTime.setBounds(350, 111, 32, 20);
        contentPane.add(cleanTime);
        cleanTime.setColumns(10);

        btnExit.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        btnExit.setBounds(247, 163, 89, 31);
        btnExit.setForeground(Color.RED);
        contentPane.add(btnExit);
    }
    private int setTechNum()
    {
        int TechNum;
        String stringTechNum=techNum.getText();
        if(stringTechNum.matches("\\d+")) {
            TechNum = Integer.parseInt(stringTechNum);
            if(TechNum<=0)
                TechNum=1;
        }
        else
            TechNum=1;
        return TechNum;
    }
    private double setCleanTime()
    {
        double CleanTime;
        String stringCleanTime=cleanTime.getText();
        if (stringCleanTime.matches("\\d+(\\.\\d+)?")) {
            CleanTime = Double.parseDouble(stringCleanTime);
            if (CleanTime <= 0)
                CleanTime = 2;
        }
        else
            CleanTime=2;
        return CleanTime;
    }


}
