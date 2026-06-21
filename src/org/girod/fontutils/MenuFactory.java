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
package org.girod.fontutils;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import org.girod.fontutils.model.ResolutionPanel;
import org.mdi.app.swing.AbstractMDIMenuFactory;
import org.mdiutil.io.FileUtilities;
/**
 * This class creates the Menus for the application.
 *
 * @version 0.4
 */
public class MenuFactory extends AbstractMDIMenuFactory {
   private final JMenu fileMenu = new JMenu("File");
   private JMenu helpMenu = new JMenu("Help");
   private JMenu toolsMenu = new JMenu("Tools");
   private final Configuration conf;
   private AbstractAction openAction; 
   private AbstractAction saveAction;
   private AbstractAction aboutAction;
   private AbstractAction refreshAction;   
   private final FontUtils win;
   private ResolutionPanel resolPanel = null;

   /**
    * Constructor.
    *
    * @param win the XML Editor
    */
   public MenuFactory(FontUtils win) {
      conf = Configuration.getInstance();
      this.win = win;
   }
   
   private void save() {
      File dir = new File(System.getProperty("user.dir"));
      JFileChooser chooser = new JFileChooser(dir);
      chooser.setDialogTitle("Select HTML File");
      chooser.setDialogType(JFileChooser.SAVE_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setFileFilter(conf.htmlfilter);
      int ret = chooser.showOpenDialog(win.getApplicationWindow());
      if (ret == JFileChooser.APPROVE_OPTION) {
         File file = chooser.getSelectedFile();
         file = FileUtilities.getCompatibleFile(file, "html");
         win.save(file);
      }      
   }   
   
   private void open() {
      File dir = new File(System.getProperty("user.dir"));
      JFileChooser chooser = new JFileChooser(dir);
      chooser.setDialogTitle("Select Font");
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setFileFilter(conf.fontfilter);
      int ret = chooser.showOpenDialog(win.getApplicationWindow());
      if (ret == JFileChooser.APPROVE_OPTION) {
         File file = chooser.getSelectedFile();
         win.openFont(file);
      }      
   }
   
   public void refresh() {
      win.refresh();
   }

   /**
    * construct the application internal menus.
    */
   @Override
   protected void initMenus() {
      exitAction = new AbstractAction("Exit") {
         @Override
         public void actionPerformed(ActionEvent ae) {
            win.dispose();
         }
      };

      openAction = new AbstractAction("Open") {
         @Override
         public void actionPerformed(ActionEvent e) {
            open();
         }
      };
      
      saveAction = new AbstractAction("Save") {
         @Override
         public void actionPerformed(ActionEvent e) {
            save();
         }
      };              
      
      refreshAction = new AbstractAction("Refresh") {
         @Override
         public void actionPerformed(ActionEvent e) {
            refresh();
         }
      };      

      aboutAction = new AbstractAction("About") {
         @Override
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "FontUtils version " + conf.getVersion() + "\n" + "building date: " + conf.getDate(), "About",
               JOptionPane.INFORMATION_MESSAGE);
         }
      };

      JMenuItem openItem = new JMenuItem(openAction);
      JMenuItem saveItem = new JMenuItem(saveAction);
      JMenuItem exitItem = new JMenuItem(exitAction);
      JMenuItem aboutItem = new JMenuItem(aboutAction);
      JMenuItem refreshItem = new JMenuItem(refreshAction);

      // create file menu
      fileMenu.add(openItem);
      fileMenu.add(saveItem);
      fileMenu.addSeparator();
      fileMenu.add(exitItem);
      
      registerMenus();

      // create help menu
      helpMenu.add(aboutItem);
      
      // create tools menu
      toolsMenu.add(refreshItem);      

      // create Menu bar
      Mbar.add(fileMenu);
      Mbar.add(toolsMenu);
      Mbar.add(helpMenu);
      
      JToolBar tbar = new JToolBar("ToolBar");
      getToolBarPanel().add(tbar);
      resolPanel = new ResolutionPanel();
      tbar.add(resolPanel);
   }     
}
