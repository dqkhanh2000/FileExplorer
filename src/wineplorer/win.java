package wineplorer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.metal.MetalBorders.ToolBarBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.omg.CORBA.PRIVATE_MEMBER;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class win extends JFrame {

	private JPanel contentPane;
	private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	private JTree tree;
	private DefaultTreeModel treeModel;
	private JTable table;
	private File fileHienTai;

    private JLabel date;
    private JLabel type;
    private JLabel size;
    private JRadioButton isDirectory;
    private JRadioButton isFile;
    
    private FileTableModel tb;
    private Desktop desktop;
    private File currentFile = null;
    private String rootPath;
	
	public win() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 995, 639);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 13, 208, 459);
		contentPane.add(scrollPane);
        
        clicktree();
        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        tree.setCellRenderer(new FileTreeCellRenderer());
        tree.expandRow(0);
        scrollPane.setViewportView(tree);
        
        TreeSelectionListener clicktruyen = new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)arg0.getPath().getLastPathComponent();
				showChildren(node);
				File click = (File)node.getUserObject();
				File [] arr = click.listFiles();
				if(arr!=null)
				{
					tb.setFiles(arr);
					File[] ar = click.listFiles();
					  tb.setFiles(ar);
				}else
				{
					JOptionPane.showMessageDialog(null, "Trong", "Trong", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		tree.addTreeSelectionListener(clicktruyen);
        
        JPanel panel = new JPanel();
        panel.setBounds(220, 13, 745, 517);
        contentPane.add(panel);
        panel.setLayout(null);

        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(0, 0, 745, 517);
        panel.add(scrollPane_1);
        
        
        tb = new FileTableModel(fileSystemView.getHomeDirectory().listFiles());
        
        table = new JTable(tb);
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e)
        	{
        		int id = table.getSelectionModel().getLeadSelectionIndex();
    			File f = ((FileTableModel)table.getModel()).getFile(id);
        		if(e.getClickCount()==2)
        		{
        			if(f.isDirectory())
        			{
        				File[] arr = f.listFiles();
        				tb.setFiles(arr);
        				currentFile = f;
        				if(currentFile.getParentFile().listFiles()==null)
        				{
        					rootPath = currentFile.getAbsolutePath();
        				}
        			}
        		}
        		if(e.getClickCount()==1)
        		{
        			
        			currentFile = f;
        		}
        	}
		});
        scrollPane_1.setViewportView(table);
        
        JButton btnBack = new JButton("BACK");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		File path = currentFile.getParentFile();
        		if(path==null) 
        		{
        			tb.setFiles(File.listRoots());
        		}else
        		{
        			File f = currentFile.getParentFile();
        			File[] arr = f.listFiles();
        			currentFile = f;
        			tb.setFiles(arr);
        		}
        	}
        });
        btnBack.setIcon(new ImageIcon(win.class.getResource("/wineplorer/back-arrow.png")));
        btnBack.setBounds(35, 485, 130, 37);
        contentPane.add(btnBack);
        
        JButton btnNewButton = new JButton("EXIT ");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
					 	dispose();
				} catch (Exception e) {
					// TODO: handle exception
				}
        	}
        });
        btnNewButton.setIcon(new ImageIcon(win.class.getResource("/wineplorer/49736825_2129618747058794_2061594511217262592_n.png")));
        btnNewButton.setBounds(795, 543, 146, 37);
        contentPane.add(btnNewButton);
        
        JButton openfile = new JButton("OPEN FILE");
        openfile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(currentFile.isDirectory())
        		{
        			try {
  					  Desktop.getDesktop().open(currentFile);
  				} catch (Exception t) {
  					// TODO: handle exception
  					t.printStackTrace();
  				}
  				}else if(currentFile.isDirectory())
  				{
  					try {
    					  Desktop.getDesktop().open(currentFile);
    				} catch (Exception t) {
    					// TODO: handle exception
    					t.printStackTrace();
    				}
  				}
        		contentPane.repaint();
        	}
        }); 
        
        
        
        openfile.setIcon(new ImageIcon(win.class.getResource("/wineplorer/folder1.png")));
        openfile.setBounds(214, 543, 130, 37);
        contentPane.add(openfile);
        
        JButton btnNewFold = new JButton("NEW FOLDER");
        btnNewFold.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		String tenFolder = JOptionPane.showInputDialog(null, "Nhap ten folder :");
        		File fd = new File(currentFile.getPath()+"\\"+tenFolder);
        		if(fd!=null)
        		{
        			if(fd.mkdir())
        			{
        				File[] arr = currentFile.listFiles();
        				tb.setFiles(arr);
        			}else
        			{
        				JOptionPane.showMessageDialog(null, "Khong the tao folder", "Error", JOptionPane.ERROR_MESSAGE);
        			}
        		}
        	}
        });
        btnNewFold.setIcon(new ImageIcon(win.class.getResource("/wineplorer/folder.png")));
        btnNewFold.setBounds(593, 543, 146, 37);
        contentPane.add(btnNewFold);
        
        JButton btnNewButton_1 = new JButton("DELETE");
        btnNewButton_1.setIcon(new ImageIcon(win.class.getResource("/wineplorer/backspace-arrow.png")));
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		File f = new File(currentFile.getPath());
        		String s = currentFile.getParent();
        		if(f.delete())
        		{
        			currentFile = f.getParentFile();
        			tb.setFiles(new File(s).listFiles());
        		}else
        		{
        			JOptionPane.showMessageDialog(null, "Khong the xoa");
        		}
        	}
        });
        btnNewButton_1.setBounds(404, 543, 130, 37);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("RENAME");
        btnNewButton_2.setIcon(new ImageIcon(win.class.getResource("/wineplorer/checkbox-pen-outline.png")));
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		currentFile.renameTo(new File(currentFile.getParentFile(), JOptionPane.showInputDialog("New name?")));
        	}
        });
        btnNewButton_2.setBounds(35, 543, 130, 37);
        contentPane.add(btnNewButton_2);
	}
	
	private void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground() {
                File file = (File) node.getUserObject();
                if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true);
                    if (node.isLeaf()) {
                        for (File child : files) {
                            if (child.isDirectory()) {
                                publish(child);
                            }
                        }
                    }
//                    setTableData(files);
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }
	
	private void clicktree()
    {
		File f = fileSystemView.getHomeDirectory();
    	DefaultMutableTreeNode re = new DefaultMutableTreeNode(f);
    	treeModel = new DefaultTreeModel(re);
    	File [] filere = f.listFiles();
    	for (File file : filere) {
			if(file.isDirectory()) {
				DefaultMutableTreeNode than = new DefaultMutableTreeNode(file);
				re.add(than);
			}
		}
    }
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					win frame = new win();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

class FileTableModel extends AbstractTableModel {

    private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private String[] columns = {
        "Icon",
        "File",
        "Path/name",
        "Size",
        "Last Modified",
    };

    FileTableModel() {
        this(new File[0]);
    }

    FileTableModel(File[] files) {
        this.files = files;
    }

    public Object getValueAt(int row, int column) {
        File file = files[row];
        switch (column) {
            case 0:
                return fileSystemView.getSystemIcon(file);
            case 1:
                return fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.getPath();
            case 3:
            	return file.length() + " bytes";
            case 4:
                return file.lastModified();
            default:
                System.err.println("Logic Error");
        }
        return "";
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 3:
                return Long.class;
            case 4:
                return Date.class;
            case 5:
                return Boolean.class;
        }
        return String.class;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return files.length;
    }

    public File getFile(int row) {
        return files[row];
    }

    public void setFiles(File[] files) {
        this.files = files;
        fireTableDataChanged();
    }
}


 class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    private FileSystemView fileSystemView;

    private JLabel label;

    FileTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        File file = (File)node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}