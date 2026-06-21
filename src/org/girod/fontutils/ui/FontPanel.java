/*
Copyright (c) 2026 Herve Girod
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.

Alternatively if you have any questions about this project, you can visit
the project website at the project page on https://github.com/hervegirod/FontUtils
 */
package org.girod.fontutils.ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.girod.fontutils.Configuration;
import org.girod.fontutils.model.FontParams;
import org.girod.fontutils.model.Utilities;

/**
 * The panel for one font.
 *
 * @since 0.4
 */
public class FontPanel extends JSplitPane {
   private static final int FONT_SIZE = 0;
   private static final int FONT_ASCENT = 1;
   private static final int FONT_DESCENT = 2;
   private static final int FONT_HEIGHT = 3;
   private static final int FONT_LEADING = 4;
   private static final int FONT_ADVANCE = 5;
   private static final int FONT_TEXTBOX = 6;

   private JTable table = null;
   private final JTextField tf = new JTextField(5);
   private final JTextField tf2 = new JTextField(10);
   private final JTextField tf3 = new JTextField(5);
   private float size = 1f;
   private int divider = 0;
   private final DefaultTableModel model = new MyColumnModel();
   private final FontUIComponent uiComp;
   private final FontParams fontParams;

   public FontPanel(FontUIComponent uiComp) {
      super(JSplitPane.VERTICAL_SPLIT);
      this.uiComp = uiComp;
      this.fontParams = uiComp.getFontParams();
   }

   public FontUIComponent getFontUIComponent() {
      return uiComp;
   }

   public FontParams getFontParams() {
      return fontParams;
   }

   public void setup() {
      Configuration conf = Configuration.getInstance();
      // table
      table = new JTable();
      model.setColumnCount(3);
      table.setModel(model);

      JPanel pane = new JPanel();
      pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
      setTopComponent(new JScrollPane(pane));

      // screen resolution
      JPanel resolPanel = new JPanel();
      resolPanel.setLayout(new BoxLayout(resolPanel, BoxLayout.X_AXIS));
      resolPanel.add(Box.createHorizontalStrut(5));
      resolPanel.add(new JLabel("Screen Resolution"));
      resolPanel.add(Box.createHorizontalStrut(5));
      tf3.setEditable(false);
      tf3.setText(conf.dotsperInch + " pixels per Inch");
      resolPanel.add(tf3);
      resolPanel.add(Box.createHorizontalStrut(15));
      resolPanel.add(Box.createHorizontalGlue());
      pane.add(resolPanel);
      pane.add(Box.createVerticalStrut(5));

      // font size panel
      JPanel sizePanel = new JPanel();
      sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.X_AXIS));
      sizePanel.add(Box.createHorizontalStrut(5));
      sizePanel.add(new JLabel("Font Size"));
      sizePanel.add(Box.createHorizontalStrut(5));
      tf.setText("1");
      sizePanel.add(tf);
      sizePanel.add(Box.createHorizontalStrut(15));
      sizePanel.add(Box.createHorizontalGlue());
      pane.add(sizePanel);
      pane.add(Box.createVerticalStrut(5));

      // text panel
      JPanel textPanel = new JPanel();
      textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
      textPanel.add(Box.createHorizontalStrut(5));
      textPanel.add(new JLabel("Text"));
      textPanel.add(Box.createHorizontalStrut(5));
      tf2.setText(fontParams.text);
      textPanel.add(tf2);
      textPanel.add(Box.createHorizontalStrut(15));
      textPanel.add(Box.createHorizontalGlue());
      pane.add(textPanel);
      pane.add(Box.createVerticalStrut(5));

      JPanel tablePanel = new JPanel();
      tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
      tablePanel.add(Box.createHorizontalStrut(5));
      tablePanel.add(Box.createVerticalStrut(5));
      tablePanel.add(new JScrollPane(table));
      tablePanel.add(Box.createHorizontalStrut(5));
      tablePanel.add(Box.createHorizontalGlue());

      pane.add(tablePanel);
      pane.add(Box.createVerticalStrut(5));
      pane.add(Box.createVerticalGlue());

      JPanel imagePanel = new JPanel();
      setBottomComponent(imagePanel);
      divider = 300;
      setDividerLocation(divider);

      initTable();

      updateParams();
   }

   private void initTable() {
      Vector<String> v = new Vector<>();
      v.add("Size");
      v.add(Float.toString(size));
      model.addRow(v);
      v = new Vector<>();
      v.add("Ascent");
      v.add("");
      v.add("");
      model.addRow(v);
      v = new Vector<>();
      v.add("Descent");
      v.add("");
      v.add("");
      model.addRow(v);
      v = new Vector<>();
      v.add("Height");
      v.add("");
      v.add("");
      model.addRow(v);
      v = new Vector<>();
      v.add("Leading");
      v.add("");
      v.add("");
      model.addRow(v);
      v = new Vector<>();
      v.add("Advance");
      v.add("");
      v.add("");
      model.addRow(v);
      v = new Vector<>();
      v.add("Text Box");
      v.add("");
      v.add("");
      model.addRow(v);
      table.revalidate();
      table.repaint();
   }

   private void createUI(String text) {
      // see https://stackoverflow.com/questions/65631875/how-to-get-the-precise-bounding-box-of-a-character-in-java/65632462?noredirect=1#comment116040908_65632462
      divider = getDividerLocation();
      JPanel ui = new JPanel(new BorderLayout(4, 4));
      ui.setBorder(new EmptyBorder(4, 4, 4, 4));
      Image image = uiComp.getImage(text);
      ui.add(new JLabel(new ImageIcon(image)));
      setBottomComponent(ui);
      setDividerLocation(divider);
   }

   public void updateParams() {
      try {
         size = Float.parseFloat(tf.getText());
      } catch (NumberFormatException ex) {
      }
      String text = tf2.getText();
      fontParams.text = text;
      Utilities.compute(uiComp.getComponent(), text, fontParams, size, fontParams.font);

      model.setValueAt(fontParams.size, FONT_SIZE, 1);
      model.setValueAt(fontParams.sizeMM, FONT_SIZE, 2);

      model.setValueAt(fontParams.ascent, FONT_ASCENT, 1);
      model.setValueAt(fontParams.ascentMM, FONT_ASCENT, 2);

      model.setValueAt(fontParams.descent, FONT_DESCENT, 1);
      model.setValueAt(fontParams.descentMM, FONT_DESCENT, 2);

      model.setValueAt(fontParams.height, FONT_HEIGHT, 1);
      model.setValueAt(fontParams.heightMM, FONT_HEIGHT, 2);

      model.setValueAt(fontParams.leading, FONT_LEADING, 1);
      model.setValueAt(fontParams.leadingMM, FONT_LEADING, 2);

      model.setValueAt(fontParams.advance, FONT_ADVANCE, 1);
      model.setValueAt(fontParams.advanceMM, FONT_ADVANCE, 2);

      model.setValueAt(Utilities.toString(fontParams.textBox), FONT_TEXTBOX, 1);
      model.setValueAt(Utilities.toString(fontParams.textBoxMM), FONT_TEXTBOX, 2);

      this.createUI(text);
      table.revalidate();
      table.repaint();
   }

   private class MyColumnModel extends DefaultTableModel {
      @Override
      public boolean isCellEditable(int row, int column) {
         return false;
      }

      @Override
      public String getColumnName(int column) {
         switch (column) {
            case 0:
               return "Name";
            case 1:
               return "Value (pts)";
            default:
               return "Value (mm)";
         }
      }
   }
}
