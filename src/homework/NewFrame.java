package homework;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class NewFrame extends JFrame{

    JPanel tab1=new JPanel();
    JPanel tab2=new JPanel();
    JPanel tab3=new JPanel();
    JPanel spr1=new JPanel();
    JPanel spr2=new JPanel();

    JTabbedPane tab=new JTabbedPane();


    public NewFrame() {
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        tab.add(tab1, "Tab 1");
        tab.add(tab2, "Tab 2");
        tab.add(tab3, "Tab 3");
        tab.add(spr1, "Spr 1");
        tab.add(spr2, "Spr 2");

        this.add(tab);

        this.setVisible(true);
    }

}
