package homework1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A JDialog GUI for choosing a GeoSegment and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    // the RouteDirectionsGUI that this JDialog was opened from
    private RouteFormatterGUI parent;

    // a control contained in this
    private JList<GeoSegment> lstSegments;

    /**
     * Creates a new GeoSegmentsDialog JDialog.
     * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
     * 			owner and parent pnlParent
     */
    public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
        // create a modal JDialog with the an owner Frame (a modal window
        // in one that doesn't allow other windows to be active at the
        // same time).
        super(owner, "Please choose a GeoSegment", true);

        this.parent = pnlParent;

        // TODO Write the body of this method

        //  Create a new instance of ExampleGeoSegments
        ExampleGeoSegments exampleGeoSegments = new ExampleGeoSegments();

        /*  Create a new DefaultListModel because cant add elements to JList
          after definition. Add segments to this list, and JList will act as
          a 'display' for this DefaultListModel.*/
        DefaultListModel<GeoSegment> listModel = new DefaultListModel<>();
        for(int i = 0; i < exampleGeoSegments.segments.length; i++)
        {
            listModel.addElement(exampleGeoSegments.segments[i]);
        }
        lstSegments = new JList<>(listModel);
        lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrlSegments = new JScrollPane(lstSegments);
        scrlSegments.setPreferredSize(new Dimension(600, 200));

        JLabel lblSegments = new JLabel("GeoSegments:");
        lblSegments.setLabelFor(lstSegments);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.equals(btnAdd))
                {
                    int index = lstSegments.getSelectedIndex();
                }
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        // arrange components on grid

        /*The content pane of a frame of dialog is a JPanel, so this must be
        cast to JPanel.*/
        JPanel panel = (JPanel)this.getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gridbag);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(0,0,0,0);
        gridbag.setConstraints(lblSegments, c);
        this.add(lblSegments);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 5;
        c.insets = new Insets(0,0,0,0);
        gridbag.setConstraints(scrlSegments, c);
        this.add(scrlSegments);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(20,0,0,0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        gridbag.setConstraints(btnAdd, c);
        this.add(btnAdd);

        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(20,0,0,0);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        gridbag.setConstraints(btnCancel, c);
        this.add(btnCancel);
    }
}