/*
Copyright (c) 2021 Herve Girod
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
package org.girod.fontutils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @version 0.2
 */
public class FontUtils extends JFrame {
   private static final int FONT_SIZE = 0;
   private static final int FONT_ASCENT = 1;
   private static final int FONT_DESCENT = 2;
   private static final int FONT_HEIGHT = 3;
   private static final int FONT_LEADING = 4;
   private static final int FONT_ADVANCE = 5;
   private static final int FONT_TEXTBOX = 6;
   private static final int FONT_SHAPEBOX = 7;
   private static final float UI_FONT_SIZE = 150;
   private FontRenderContext ctx = null;
   private JSplitPane splitPane = null;
   private JTable table = null;
   private final JTextField tf = new JTextField(5);
   private final JTextField tf2 = new JTextField(10);
   private final JTextField tf3 = new JTextField(5);
   private float size = 1f;
   private int divider = 0;
   private Font font = null;
   private float dotsperInch = 96f;
   private final DefaultTableModel model = new MyColumnModel();

   public FontUtils() {
      super();
      String version = getVersion();
      this.setTitle("Font Utilities " + version);
      this.setSize(500, 600);
      setup();
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   }

   public static void main(String[] args) {
      FontUtils fontUtils = new FontUtils();
      fontUtils.setVisible(true);
   }

   private void setup() {
      dotsperInch = Toolkit.getDefaultToolkit().getScreenResolution();
      ctx = new FontRenderContext(new AffineTransform(), true, true);
      // table
      table = new JTable();
      model.setColumnCount(3);
      table.setModel(model);
      splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
      this.setContentPane(splitPane);
      JPanel pane = new JPanel();
      pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
      splitPane.setTopComponent(new JScrollPane(pane));

      // screen resolution
      JPanel resolPanel = new JPanel();
      resolPanel.setLayout(new BoxLayout(resolPanel, BoxLayout.X_AXIS));
      resolPanel.add(Box.createHorizontalStrut(5));
      resolPanel.add(new JLabel("Screen Resolution"));
      resolPanel.add(Box.createHorizontalStrut(5));
      tf3.setEditable(false);
      tf3.setText(dotsperInch + " pixels per Inch");
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
      tf2.setText("A");
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
      splitPane.setBottomComponent(imagePanel);
      divider = 300;
      splitPane.setDividerLocation(divider);

      initTable();

      // menu
      JMenuBar mbar = new JMenuBar();
      this.setJMenuBar(mbar);
      JMenu menu = new JMenu("File");
      mbar.add(menu);
      JMenuItem openItem = new JMenuItem("Open");
      menu.add(openItem);
      JMenuItem refreshItem = new JMenuItem("Refresh");
      menu.add(refreshItem);
      refreshItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (font != null) {
               updateParams();
            }
         }
      });
      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(exitItem);
      exitItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      openItem.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            openFont();
         }
      });
   }

   private void createUI(String text) {
      // see https://stackoverflow.com/questions/65631875/how-to-get-the-precise-bounding-box-of-a-character-in-java/65632462?noredirect=1#comment116040908_65632462
      divider = splitPane.getDividerLocation();
      JPanel ui = new JPanel(new BorderLayout(4, 4));
      ui.setBorder(new EmptyBorder(4, 4, 4, 4));
      ui.add(new JLabel(new ImageIcon(getImage(text))));
      this.splitPane.setBottomComponent(ui);
      splitPane.setDividerLocation(divider);
   }

   private Shape getShapeOfText(Font font, String msg) {
      BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

      Graphics2D g = bi.createGraphics();

      FontRenderContext frc = g.getFontRenderContext();
      GlyphVector gv = font.createGlyphVector(frc, msg);

      return gv.getOutline();
   }

   private Shape moveShapeToCenter(Shape shape, int w, int h) {
      Rectangle2D b = shape.getBounds2D();
      double xOff = -b.getX() + ((w - b.getWidth()) / 2d);
      double yOff = -b.getY() + ((h - b.getHeight()) / 2d);
      AffineTransform move = AffineTransform.getTranslateInstance(xOff, yOff);
      return move.createTransformedShape(shape);
   }

   private BufferedImage getImage(String text) {
      Font _font = font.deriveFont(UI_FONT_SIZE);
      Shape shape = getShapeOfText(_font, text);
      Rectangle2D boundsRectangle = shape.getBounds2D();
      double w = boundsRectangle.getWidth();
      double h = boundsRectangle.getHeight();
      int wBig = (int) (w * 1.1);
      int hBig = (int) (h * 2);
      BufferedImage bi = new BufferedImage(wBig, hBig, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = bi.createGraphics();
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, wBig, hBig);

      g.setColor(Color.BLACK);
      g.fill(moveShapeToCenter(shape, wBig, hBig));
      g.setColor(Color.RED);
      g.draw(moveShapeToCenter(boundsRectangle, wBig, hBig));
      g.dispose();

      return bi;
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
      v = new Vector<>();
      v.add("Text Box as Shape");
      v.add("");
      v.add("");
      model.addRow(v);
      table.revalidate();
      table.repaint();
   }

   private void openFont() {
      File dir = new File(System.getProperty("user.dir"));
      JFileChooser chooser = new JFileChooser(dir);
      chooser.setDialogTitle("Select Font file");
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int ret = chooser.showOpenDialog(this);
      if (ret == JFileChooser.APPROVE_OPTION) {
         File file = chooser.getSelectedFile();
         openFontImpl(file);
      }
   }

   private void updateParams() {
      try {
         size = Float.parseFloat(tf.getText());
      } catch (NumberFormatException ex) {
      }
      font = font.deriveFont(size);
      LineMetrics metrics = font.getLineMetrics("TOTO", ctx);
      FontMetrics fontMetrics = this.getFontMetrics(font);
      float _size = font.getSize2D();
      float ascent = metrics.getAscent();
      float descent = metrics.getDescent();
      float height = metrics.getHeight();
      float leading = metrics.getLeading();
      int advance = fontMetrics.charWidth('A');
      String text = tf2.getText();
      Rectangle2D rec = fontMetrics.getStringBounds(text, this.getGraphics());

      // use the realt outline of the caracter, see https://stackoverflow.com/questions/19582502/how-to-get-the-correct-string-width-from-fontmetrics-in-java
      GlyphVector gv = font.createGlyphVector(ctx, text);
      Rectangle2D rec2 = gv.getOutline().getBounds2D();

      model.setValueAt(_size, FONT_SIZE, 1);
      model.setValueAt(getMM(_size), FONT_SIZE, 2);
      model.setValueAt(ascent, FONT_ASCENT, 1);
      model.setValueAt(getMM(ascent), FONT_ASCENT, 2);
      model.setValueAt(descent, FONT_DESCENT, 1);
      model.setValueAt(getMM(descent), FONT_DESCENT, 2);
      model.setValueAt(height, FONT_HEIGHT, 1);
      model.setValueAt(getMM(height), FONT_HEIGHT, 2);
      model.setValueAt(leading, FONT_LEADING, 1);
      model.setValueAt(getMM(leading), FONT_LEADING, 2);
      model.setValueAt(advance, FONT_ADVANCE, 1);
      model.setValueAt(getMM(advance), FONT_ADVANCE, 2);
      model.setValueAt(toString(rec), FONT_TEXTBOX, 1);
      model.setValueAt(toStringMM(rec), FONT_TEXTBOX, 2);
      model.setValueAt(toString(rec2), FONT_SHAPEBOX, 1);
      model.setValueAt(toStringMM(rec2), FONT_SHAPEBOX, 2);

      this.createUI(text);
      table.revalidate();
      table.repaint();
   }

   private String toString(Rectangle2D rec) {
      return (float) rec.getWidth() + " " + (float) rec.getHeight();
   }

   private String toStringMM(Rectangle2D rec) {
      return getMM((float) rec.getWidth()) + " " + getMM((float) rec.getHeight());
   }

   private void openFontImpl(File file) {
      try {
         font = Font.createFont(Font.TRUETYPE_FONT, file);
         updateParams();
      } catch (FontFormatException ex) {
         System.err.println("Font format not supported");
      } catch (IOException ex) {
         System.err.println("Could not open Font");
      }
   }

   private String getVersion() {
      try {
         URL url = this.getClass().getResource("fontutils.properties");
         InputStream stream = url.openStream();
         PropertyResourceBundle prb = new PropertyResourceBundle(stream);
         String version = prb.getString("version");
         return version;
      } catch (IOException ex) {
         return "";
      }
   }

   private float getMM(float point) {
      return point * 25.4f / dotsperInch;
   }

   private class MyColumnModel extends DefaultTableModel {
      @Override
      public String getColumnName(int column) {
         if (column == 0) {
            return "Name";
         } else if (column == 1) {
            return "Value (pts)";
         } else {
            return "Value (mm)";
         }
      }
   }
}
