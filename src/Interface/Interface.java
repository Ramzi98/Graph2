package Interface;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;


import m1graf2020.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.REMAINDER;
import static m1graf2020.Graf.DotFileToGraph;

/***
 * The interface class of our project of graph
 */
public class Interface extends JFrame {
    public JPanel test;
    public JButton createAnEmptyGraphButton;
    public JButton addANodeButton;
    public JButton BFSFormButton;
    public JButton DFSFormButton;
    public JButton showCurrentGraphInButton;
    public JButton transitiveClosureButton;
    public JButton reverseTheGraphButton;
    public JButton DOTFormatButton;
    public JButton removeAnEdgeButton;
    public JButton addAnEdgeButton;
    public JButton removeANodeButton;
    private JLabel MenuPrincipal;
    public JButton backBtn;
    private JButton resetGraphButton;
    public Graf graph = new Graf();
    public String Namegraph = "random";
    public JMenu menu2;
    public JMenu menu3;

    public Interface() {
        this.setTitle("Graph manipulation");
        this.pack();
        this.setContentPane(test);
        this.setSize(700, 550);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setComponent();
        createAnEmptyGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                JLabel graphname = new JLabel("Name of the graph : ");
                JTextField inputname = new JTextField("", 10);
                JRadioButton undirected = new JRadioButton("Undirected", false);
                final JRadioButton weighted = new JRadioButton("Weighted", false);
                JSeparator s = new JSeparator();
                addpane.add(graphname);
                addpane.add(inputname);
                addpane.add(undirected);
                addpane.add(weighted);
                weighted.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
                addpane.add(s);
                JButton next = new JButton("Create");
                JButton cancel = new JButton("Cancel");
                addpane.add(cancel);
                addpane.add(next);
                frame.setTitle("Add a graph");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 130);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                next.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Boolean checked = false;

                        try {
                            String.valueOf(inputname.getText());
                            checked = true;
                        } catch (NullPointerException s) {
                            JOptionPane.showMessageDialog(frame, "Input musn't be null");
                            frame.dispose();
                        }
                        if (checked) {
                            if (weighted.isSelected()) {
                                if (undirected.isSelected()) {
                                    graph = new UndirectedGraf(true);
                                } else {
                                    graph = new Graf(true);
                                }
                            } else {
                                if (undirected.isSelected()) {
                                    graph = new UndirectedGraf(false);
                                } else graph = new Graf(false);
                            }
                            frame.dispose();
                            Namegraph = inputname.getText();
                            addANodeButton.setEnabled(true);
                            DFSFormButton.setEnabled(true);
                            BFSFormButton.setEnabled(true);
                            removeAnEdgeButton.setEnabled(true);
                            removeANodeButton.setEnabled(true);
                            reverseTheGraphButton.setEnabled(true);
                            addAnEdgeButton.setEnabled(true);
                            addANodeButton.setEnabled(true);
                            DOTFormatButton.setEnabled(true);
                            transitiveClosureButton.setEnabled(true);
                            showCurrentGraphInButton.setEnabled(true);
                            resetGraphButton.setEnabled(true);
                            createAnEmptyGraphButton.setEnabled(false);
                            menu2.setEnabled(true);
                            menu3.setEnabled(true);
                        }
                    }
                });

                cancel.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
        });
        addANodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame1 = new JFrame();
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                JLabel graphname = new JLabel("Number of the node : ");
                final JTextField inputname = new JTextField("", 10);
                JSeparator s = new JSeparator();
                addpane.add(graphname);
                addpane.add(inputname);
                addpane.add(s);
                JButton next = new JButton("Add");
                JButton cancel = new JButton("Cancel");
                addpane.add(cancel);
                addpane.add(next);
                frame1.setTitle("Add a node");
                frame1.pack();
                frame1.setContentPane(addpane);
                frame1.setSize(300, 130);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame1.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame1.getHeight()) / 2);
                frame1.setLocation(x, y);
                frame1.setVisible(true);
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame1.setResizable(false);
                next.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Boolean checked = false;
                        try {
                            Integer.parseInt(inputname.getText());
                            checked = true;
                        } catch (NumberFormatException s) {
                            JOptionPane.showMessageDialog(frame1, "Input must be an integer");

                        } catch (NullPointerException s) {
                            JOptionPane.showMessageDialog(frame1, "Input musn't be null");
                        }
                        if (checked) {
                            graph.addNode(Integer.parseInt(inputname.getText()));
                            frame1.dispose();
                        }

                    }
                });
                cancel.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame1.dispose();
                    }
                });
            }
        });

        removeANodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                JLabel graphname = new JLabel("Number of the node : ");
                JTextField inputname = new JTextField("", 10);
                addpane.add(graphname);
                addpane.add(inputname);
                JButton next = new JButton("Remove");
                JButton cancel = new JButton("Cancel");
                addpane.add(cancel);
                addpane.add(next);
                frame.setTitle("Remove a node");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 130);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                next.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Boolean checked = false;

                        try {
                            Integer.parseInt(inputname.getText());
                            checked = true;
                        } catch (NullPointerException s) {
                            JOptionPane.showMessageDialog(frame, "Input musn't be null");
                        } catch (NumberFormatException s) {
                            JOptionPane.showMessageDialog(frame, "Input must be an integer");
                        }

                        if (checked) {
                            frame.dispose();
                            graph.removeNode(graph.getNode(Integer.parseInt(inputname.getText())));
                        }
                    }
                });
                cancel.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
        });
        addAnEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                JLabel graphname = new JLabel("Add the node from : ");
                JTextField inputfrom = new JTextField("", 16);
                JLabel graphname1 = new JLabel("Add the node to : ");
                JTextField inputto = new JTextField("", 20);
                final JLabel weight = new JLabel("Weight : ");
                final JTextField weightinput = new JTextField("", 3);
                weightinput.setText("0");
                JSeparator s = new JSeparator();
                weight.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                addpane.add(graphname);
                addpane.add(inputfrom);
                addpane.add(graphname1);
                addpane.add(inputto);
                addpane.add(s);
                addpane.add(weight);
                addpane.add(weightinput);
                JButton next = new JButton("Add");
                JButton cancel = new JButton("Cancel");
                addpane.add(cancel);
                addpane.add(next);
                frame.setTitle("Add an edge");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 180);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                if (graph.isWeighted()) {
                    weightinput.setVisible(true);
                    weight.setVisible(true);
                } else {
                    weightinput.setVisible(false);
                    weight.setVisible(false);
                }

                next.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Boolean checked = false;
                        try {
                            Integer.parseInt(inputfrom.getText());
                            Integer.parseInt(inputto.getText());
                            Integer.parseInt(weightinput.getText());
                            checked = true;
                        } catch (NullPointerException s) {
                            JOptionPane.showMessageDialog(frame, "Input musn't be null");
                        } catch (NumberFormatException s) {
                            JOptionPane.showMessageDialog(frame, "Input must be an integer");
                        }

                        if (checked) {
                            frame.dispose();
                            try {
                                if (1 == 2) {
                                    //Controle type of weight and empty
                                    graph.addEdge(Integer.parseInt(inputfrom.getText()), Integer.parseInt(inputto.getText()), Integer.parseInt(weightinput.getText()));
                                } else {
                                    graph.addEdge(Integer.parseInt(inputfrom.getText()), Integer.parseInt(inputto.getText()));
                                }
                            } catch (Exception ex) {

                            }
                        }
                    }
                });
                cancel.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
        });
        removeAnEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                JLabel graphname = new JLabel("Number of the node from : ");
                JTextField inputfrom = new JTextField("", 10);
                JLabel graphname1 = new JLabel("Number of the node to : ");
                JTextField inputto = new JTextField("", 10);
                addpane.add(graphname);
                addpane.add(inputfrom);
                addpane.add(graphname1);
                addpane.add(inputto);
                JButton next = new JButton("Remove");
                JButton cancel = new JButton("Cancel");
                addpane.add(cancel);
                addpane.add(next);
                frame.setTitle("Remove an edge");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 130);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                next.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Boolean checked = false;
                        try {
                            Integer.parseInt(inputfrom.getText());
                            Integer.parseInt(inputto.getText());
                            checked = true;
                        } catch (NullPointerException s) {
                            JOptionPane.showMessageDialog(frame, "Input musn't be null");
                        } catch (NumberFormatException s) {
                            JOptionPane.showMessageDialog(frame, "Input must be an integer");
                        }

                        if (checked) {
                            frame.dispose();
                            graph.removeEdge(Integer.parseInt(inputfrom.getText()), Integer.parseInt(inputto.getText()));

                        }
                    }
                });
                cancel.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
        });
        DOTFormatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                JLabel graphname = new JLabel("DOT Format created succesfully!");
                addpane.add(graphname);
                JButton next = new JButton("Continue");
                addpane.add(next);
                frame.setTitle("DOT Format");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 80);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                next.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        try {
                            graph.toDotFile(Namegraph);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            }
        });

        resetGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to reset the graph?");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    addANodeButton.setEnabled(false);
                    DFSFormButton.setEnabled(false);
                    BFSFormButton.setEnabled(false);
                    removeAnEdgeButton.setEnabled(false);
                    removeANodeButton.setEnabled(false);
                    reverseTheGraphButton.setEnabled(false);
                    addAnEdgeButton.setEnabled(false);
                    addANodeButton.setEnabled(false);
                    DOTFormatButton.setEnabled(false);
                    transitiveClosureButton.setEnabled(false);
                    showCurrentGraphInButton.setEnabled(false);
                    resetGraphButton.setEnabled(false);
                    createAnEmptyGraphButton.setEnabled(true);
                    menu2.setEnabled(false);
                    menu3.setEnabled(false);
                }
            }

            ;
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Select s = new Select();
                s.setVisible(true);
            }
        });
        reverseTheGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                JLabel graphname = new JLabel("Graph reversed succesfully!");
                addpane.add(graphname);
                JButton next = new JButton("Continue");
                addpane.add(next);
                frame.setTitle("Reversed Graph");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 80);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                next.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        graph = graph.getReverse();
                    }
                });
            }
        });
        showCurrentGraphInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GrafTreeDisplay testJtree = new GrafTreeDisplay(graph);
                testJtree.setVisible(true);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - testJtree.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - testJtree.getHeight()) / 2);
                testJtree.setLocation(x, y);
                testJtree.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                testJtree.setTitle("Current graph display");

            }
        });


        transitiveClosureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final JFrame frame = new JFrame();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    final JPanel addpane = new JPanel();
                    addpane.setBackground(Color.WHITE);
                    frame.setBackground(Color.WHITE);
                    JLabel graphname = new JLabel("The transitive closure of the graph is : ");
                    addpane.setBorder(new EmptyBorder(0, 50, 50, 50));
                    frame.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridwidth = REMAINDER;
                    gbc.fill = HORIZONTAL;
                    JTextArea closure = new JTextArea(graph.getTransitiveClosure().toDotString());
                    closure.setEditable(false);
                    addpane.add(graphname, gbc);
                    addpane.add(closure, gbc);
                    frame.setTitle("Transitive Closure");
                    frame.pack();
                    frame.setContentPane(addpane);
                    frame.setSize(300, 700);
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                    frame.setLocation(x, y);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                } catch (Exception ex) {
                }
            }
        });
        DFSFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                addpane.setLayout(new BoxLayout(addpane, BoxLayout.Y_AXIS));
                JTextArea closure = new JTextArea("[");
                closure.setEditable(false);
                for (Node n : graph.getDFS())
                    closure.append(Integer.toString(n.getId()) + ",");
                closure.append("]");
                addpane.add(closure);
                JButton next = new JButton("Continue");
                addpane.add(next);
                frame.setTitle("DFS Format");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 150);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                next.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
        });
        BFSFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final JPanel addpane = new JPanel();
                addpane.setLayout(new BoxLayout(addpane, BoxLayout.Y_AXIS));
                JTextArea closure = new JTextArea("[");
                closure.setEditable(false);
                for (Node n : graph.getBFS())
                    closure.append(Integer.toString(n.getId()) + ",");
                closure.append("]");
                addpane.add(closure);
                JButton next = new JButton("Continue");
                addpane.add(next);
                frame.setTitle("BFS Format");
                frame.pack();
                frame.setContentPane(addpane);
                frame.setSize(300, 150);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                next.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
        });

    }

    public void setComponent() {
        JMenuBar menuBar = new JMenuBar();
        menu2 = new JMenu("Graphviz");
        JMenuItem graphviz = new JMenuItem("Display Graphviz");
        JMenu menu1 = new JMenu("Generate");
        menu3 = new JMenu("Divers");
        JMenu menu4 = new JMenu("Create Graph");
        JMenuItem directed = new JMenuItem("Directed graph");
        JMenuItem undirected = new JMenuItem("Undirected graph");
        JMenuItem dag = new JMenuItem("DAG graph");
        JMenuItem sparse = new JMenuItem("Sparse graph");
        JMenuItem dense = new JMenuItem("Dense graph");
        JMenuItem connected = new JMenuItem("Connected graph");
        JMenuItem SuccessorArray = new JMenuItem("Successor Array");
        JMenuItem ImportFromFile = new JMenuItem("Import from file");
        JMenuItem succesorToGraph = new JMenuItem("Create graph from Successor Array");

        JMenuItem nodesDisplay = new JMenuItem("Get all nodes");
        JMenuItem edgesDisplay = new JMenuItem("Get all edges");
        menu2.setEnabled(false);
        menu3.setEnabled(false);
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        menu2.add(graphviz);
        menu1.add(directed);
        menu1.add(undirected);
        menu1.add(dag);
        menu1.add(sparse);
        menu1.add(dense);
        menu1.add(connected);
        menu3.add(SuccessorArray);
        menu3.add(nodesDisplay);
        menu3.add(edgesDisplay);
        menu4.add(ImportFromFile);
        menu4.add(succesorToGraph);

        this.setJMenuBar(menuBar);
        menuBar.setVisible(true);
        graphviz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    graph.DotFileToPDF(Namegraph);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImportFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImportEvt(e);
            }
        });
        succesorToGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                succesorToGraphEvt(e);
            }
        });
        SuccessorArray.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SuccessorEvt(evt);
            }
        });
        nodesDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                NodesEvt(evt);
            }
        });
        edgesDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                EdgesEvt(evt);
            }
        });
        directed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                DirectedGraph(evt);
            }
        });
        undirected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                UndirectedGraph(evt);
            }
        });
        dag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RandomDAG(evt);
            }
        });
        sparse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RandomSparse(evt);
            }
        });
        dense.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RandomDense(evt);
            }
        });
        connected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RandomConnected(evt);
            }
        });
    }

    private void succesorToGraphEvt(ActionEvent e) {
        JFrame frame = new JFrame("Create graph from Successor Array");
        String name = JOptionPane.showInputDialog(frame, "Enter the successor array ex: '2,3,0,1,3,0,1,2,0'");
        Namegraph = "generatedSA";
        addANodeButton.setEnabled(true);
        DFSFormButton.setEnabled(true);
        BFSFormButton.setEnabled(true);
        removeAnEdgeButton.setEnabled(true);
        removeANodeButton.setEnabled(true);
        reverseTheGraphButton.setEnabled(true);
        addAnEdgeButton.setEnabled(true);
        addANodeButton.setEnabled(true);
        DOTFormatButton.setEnabled(true);
        transitiveClosureButton.setEnabled(true);
        showCurrentGraphInButton.setEnabled(true);
        resetGraphButton.setEnabled(true);
        createAnEmptyGraphButton.setEnabled(false);
        menu2.setEnabled(true);
        menu3.setEnabled(true);
        frame.dispose();
        String[] split = name.split(",");
        int[] splited = new int[split.length];
        int i = 0;
        for (String a : split) {
            splited[i] = Integer.parseInt(a);
            i++;
        }
        graph = new Graf(splited);
    }

    private void EdgesEvt(ActionEvent evt) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JPanel addpane = new JPanel();
        addpane.setLayout(new BoxLayout(addpane, BoxLayout.Y_AXIS));
        JTextArea closure = new JTextArea();
        closure.setEditable(false);
        for (Edge edge : graph.getAllEdges()) {
            closure.append(edge.getStartnode().toString() + " -> " + edge.getEndnode().toString() + "\n");
        }
        addpane.add(closure);
        frame.setTitle("All Edges");
        frame.pack();
        frame.setContentPane(addpane);
        frame.setSize(300, 500);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

    }

    private void NodesEvt(ActionEvent evt) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JPanel addpane = new JPanel();
        addpane.setLayout(new BoxLayout(addpane, BoxLayout.Y_AXIS));
        JTextArea closure = new JTextArea("[");
        closure.setEditable(false);
        for (Node node : graph.getAllNodes()) {
            closure.append(node.getId().toString() + ", ");
        }

        closure.append("]");
        addpane.add(closure);
        frame.setTitle("All Nodes");
        frame.pack();
        frame.setContentPane(addpane);
        frame.setSize(300, 150);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

    }

    private void ImportEvt(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            graph = DotFileToGraph(path);
            String name = selectedFile.getName().split("\\.")[0];
            Namegraph = name;
            addANodeButton.setEnabled(true);
            DFSFormButton.setEnabled(true);
            BFSFormButton.setEnabled(true);
            removeAnEdgeButton.setEnabled(true);
            removeANodeButton.setEnabled(true);
            reverseTheGraphButton.setEnabled(true);
            addAnEdgeButton.setEnabled(true);
            addANodeButton.setEnabled(true);
            DOTFormatButton.setEnabled(true);
            transitiveClosureButton.setEnabled(true);
            showCurrentGraphInButton.setEnabled(true);
            resetGraphButton.setEnabled(true);
            createAnEmptyGraphButton.setEnabled(false);
            menu2.setEnabled(true);
            menu3.setEnabled(true);
        }

    }

    private void SuccessorEvt(ActionEvent evt) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JPanel addpane = new JPanel();
        addpane.setLayout(new BoxLayout(addpane, BoxLayout.Y_AXIS));
        JTextArea closure = new JTextArea("[");
        closure.setEditable(false);
        int[] sucess = graph.toSuccessorArray();
        for (int i = 0; i < sucess.length; i++) {
            closure.append(String.valueOf(sucess[i] + " , "));
        }
        closure.append("]");
        addpane.add(closure);
        frame.setTitle("Successors Array");
        frame.pack();
        frame.setContentPane(addpane);
        frame.setSize(500, 150);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

    }

    private void RandomConnected(ActionEvent evt) {
        JFrame frame = new JFrame("InputDialog Example #1");
        String name = JOptionPane.showInputDialog(frame, "Enter the number of nodes");
        try {
            int x = Integer.parseInt(name);
            RandomGraph f = new RandomGraph();
            graph = f.RandomConnectedGraph(x);
            Namegraph = "random";
            addANodeButton.setEnabled(true);
            DFSFormButton.setEnabled(true);
            BFSFormButton.setEnabled(true);
            removeAnEdgeButton.setEnabled(true);
            removeANodeButton.setEnabled(true);
            reverseTheGraphButton.setEnabled(true);
            addAnEdgeButton.setEnabled(true);
            addANodeButton.setEnabled(true);
            DOTFormatButton.setEnabled(true);
            transitiveClosureButton.setEnabled(true);
            showCurrentGraphInButton.setEnabled(true);
            resetGraphButton.setEnabled(true);
            createAnEmptyGraphButton.setEnabled(false);
            menu2.setEnabled(true);
            menu3.setEnabled(true);
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Input must be an integer");
        }
    }

    private void RandomDense(ActionEvent evt) {
        JFrame frame = new JFrame("InputDialog Example #1");
        String name = JOptionPane.showInputDialog(frame, "Enter the number of nodes");
        try {
            RandomGraph f = new RandomGraph();
            int x = Integer.parseInt(name);
            graph = f.RandomDanseGraph(x);
            addANodeButton.setEnabled(true);
            DFSFormButton.setEnabled(true);
            BFSFormButton.setEnabled(true);
            removeAnEdgeButton.setEnabled(true);
            removeANodeButton.setEnabled(true);
            reverseTheGraphButton.setEnabled(true);
            addAnEdgeButton.setEnabled(true);
            addANodeButton.setEnabled(true);
            DOTFormatButton.setEnabled(true);
            transitiveClosureButton.setEnabled(true);
            showCurrentGraphInButton.setEnabled(true);
            resetGraphButton.setEnabled(true);
            createAnEmptyGraphButton.setEnabled(false);
            menu2.setEnabled(true);
            menu3.setEnabled(true);
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Input must be an integer");
        }
    }

    private void RandomSparse(ActionEvent evt) {
        JFrame frame = new JFrame("InputDialog Example #1");
        String name = JOptionPane.showInputDialog(frame, "Enter the number of nodes");
        try {
            int x = Integer.parseInt(name);
            RandomGraph f = new RandomGraph();
            graph = f.RandomSpareGraph(x);
            addANodeButton.setEnabled(true);
            DFSFormButton.setEnabled(true);
            BFSFormButton.setEnabled(true);
            removeAnEdgeButton.setEnabled(true);
            removeANodeButton.setEnabled(true);
            reverseTheGraphButton.setEnabled(true);
            addAnEdgeButton.setEnabled(true);
            addANodeButton.setEnabled(true);
            DOTFormatButton.setEnabled(true);
            transitiveClosureButton.setEnabled(true);
            showCurrentGraphInButton.setEnabled(true);
            resetGraphButton.setEnabled(true);
            createAnEmptyGraphButton.setEnabled(false);
            menu2.setEnabled(true);
            menu3.setEnabled(true);
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Input must be an integer");
        }
    }

    private void RandomDAG(ActionEvent evt) {
        JFrame frame = new JFrame("InputDialog Example #1");
        String name = JOptionPane.showInputDialog(frame, "Enter the number of nodes");
        try {
            int x = Integer.parseInt(name);
            RandomGraph f = new RandomGraph();
            graph = f.RandomDagGraph(x);
            addANodeButton.setEnabled(true);
            DFSFormButton.setEnabled(true);
            BFSFormButton.setEnabled(true);
            removeAnEdgeButton.setEnabled(true);
            removeANodeButton.setEnabled(true);
            reverseTheGraphButton.setEnabled(true);
            addAnEdgeButton.setEnabled(true);
            addANodeButton.setEnabled(true);
            DOTFormatButton.setEnabled(true);
            transitiveClosureButton.setEnabled(true);
            showCurrentGraphInButton.setEnabled(true);
            resetGraphButton.setEnabled(true);
            createAnEmptyGraphButton.setEnabled(false);
            menu2.setEnabled(true);
            menu3.setEnabled(true);
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Input must be an integer");
        }
    }

    private void DirectedGraph(ActionEvent evt) {
        JFrame frame = new JFrame("InputDialog Example #1");
        String name = JOptionPane.showInputDialog(frame, "Enter the number of nodes");
        try {
            int x = Integer.parseInt(name);
            RandomGraph f = new RandomGraph();
            graph = f.RandomDirectedGraph(x);
            addANodeButton.setEnabled(true);
            DFSFormButton.setEnabled(true);
            BFSFormButton.setEnabled(true);
            removeAnEdgeButton.setEnabled(true);
            removeANodeButton.setEnabled(true);
            reverseTheGraphButton.setEnabled(true);
            addAnEdgeButton.setEnabled(true);
            addANodeButton.setEnabled(true);
            DOTFormatButton.setEnabled(true);
            transitiveClosureButton.setEnabled(true);
            showCurrentGraphInButton.setEnabled(true);
            resetGraphButton.setEnabled(true);
            createAnEmptyGraphButton.setEnabled(false);
            menu2.setEnabled(true);
            menu3.setEnabled(true);
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Input must be an integer");
        }
    }

    private void UndirectedGraph(ActionEvent evt) {
        JFrame frame = new JFrame("InputDialog Example #1");
        String name = JOptionPane.showInputDialog(frame, "Enter the number of nodes");
        try {
            int x = Integer.parseInt(name);
            RandomGraph f = new RandomGraph();
            graph = f.RandomUndirectedGraph(x);
            addANodeButton.setEnabled(true);
            DFSFormButton.setEnabled(true);
            BFSFormButton.setEnabled(true);
            removeAnEdgeButton.setEnabled(true);
            removeANodeButton.setEnabled(true);
            reverseTheGraphButton.setEnabled(true);
            addAnEdgeButton.setEnabled(true);
            addANodeButton.setEnabled(true);
            DOTFormatButton.setEnabled(true);
            transitiveClosureButton.setEnabled(true);
            showCurrentGraphInButton.setEnabled(true);
            resetGraphButton.setEnabled(true);
            createAnEmptyGraphButton.setEnabled(false);
            menu2.setEnabled(true);
            menu3.setEnabled(true);
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Input must be an integer");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Interface s = new Interface();
            s.setVisible(true);
        });
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        test = new JPanel();
        test.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(9, 7, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-16777216));
        test.add(panel1, BorderLayout.CENTER);
        removeAnEdgeButton = new JButton();
        removeAnEdgeButton.setBackground(new Color(-8026747));
        removeAnEdgeButton.setEnabled(false);
        removeAnEdgeButton.setForeground(new Color(-1));
        removeAnEdgeButton.setText("Remove an edge");
        panel1.add(removeAnEdgeButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        addAnEdgeButton = new JButton();
        addAnEdgeButton.setBackground(new Color(-8026747));
        addAnEdgeButton.setEnabled(false);
        addAnEdgeButton.setForeground(new Color(-1));
        addAnEdgeButton.setText("Add an edge");
        panel1.add(addAnEdgeButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        removeANodeButton = new JButton();
        removeANodeButton.setBackground(new Color(-8026747));
        removeANodeButton.setEnabled(false);
        removeANodeButton.setForeground(new Color(-1));
        removeANodeButton.setText("Remove a node");
        panel1.add(removeANodeButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        MenuPrincipal = new JLabel();
        MenuPrincipal.setBackground(new Color(-16777216));
        Font MenuPrincipalFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 26, MenuPrincipal.getFont());
        if (MenuPrincipalFont != null) MenuPrincipal.setFont(MenuPrincipalFont);
        MenuPrincipal.setForeground(new Color(-1));
        MenuPrincipal.setText("Menu Principal");
        panel1.add(MenuPrincipal, new GridConstraints(0, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createAnEmptyGraphButton = new JButton();
        createAnEmptyGraphButton.setBackground(new Color(-8026747));
        createAnEmptyGraphButton.setEnabled(true);
        createAnEmptyGraphButton.setForeground(new Color(-1));
        createAnEmptyGraphButton.setHideActionText(false);
        createAnEmptyGraphButton.setRequestFocusEnabled(false);
        createAnEmptyGraphButton.setText("Create an empty graph");
        createAnEmptyGraphButton.setVerifyInputWhenFocusTarget(false);
        panel1.add(createAnEmptyGraphButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        showCurrentGraphInButton = new JButton();
        showCurrentGraphInButton.setBackground(new Color(-8026747));
        showCurrentGraphInButton.setEnabled(false);
        showCurrentGraphInButton.setForeground(new Color(-1));
        showCurrentGraphInButton.setText("Show current graph in Tree Form");
        panel1.add(showCurrentGraphInButton, new GridConstraints(7, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        DOTFormatButton = new JButton();
        DOTFormatButton.setAlignmentX(0.0f);
        DOTFormatButton.setBackground(new Color(-8026747));
        DOTFormatButton.setEnabled(false);
        DOTFormatButton.setForeground(new Color(-1));
        DOTFormatButton.setText("DOT Format");
        panel1.add(DOTFormatButton, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        transitiveClosureButton = new JButton();
        transitiveClosureButton.setBackground(new Color(-8026747));
        transitiveClosureButton.setEnabled(false);
        transitiveClosureButton.setForeground(new Color(-1));
        transitiveClosureButton.setText("Transitive closure ");
        panel1.add(transitiveClosureButton, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        reverseTheGraphButton = new JButton();
        reverseTheGraphButton.setBackground(new Color(-8026747));
        reverseTheGraphButton.setEnabled(false);
        reverseTheGraphButton.setForeground(new Color(-1));
        reverseTheGraphButton.setText("Reverse the graph");
        panel1.add(reverseTheGraphButton, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        BFSFormButton = new JButton();
        BFSFormButton.setBackground(new Color(-8026747));
        BFSFormButton.setEnabled(false);
        BFSFormButton.setForeground(new Color(-1));
        BFSFormButton.setText("BFS Form");
        panel1.add(BFSFormButton, new GridConstraints(5, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        DFSFormButton = new JButton();
        DFSFormButton.setBackground(new Color(-8026747));
        DFSFormButton.setEnabled(false);
        DFSFormButton.setForeground(new Color(-1));
        DFSFormButton.setText("DFS Form");
        panel1.add(DFSFormButton, new GridConstraints(6, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
        addANodeButton = new JButton();
        addANodeButton.setBackground(new Color(-8026747));
        addANodeButton.setEnabled(false);
        addANodeButton.setForeground(new Color(-1));
        addANodeButton.setText("Add a node");
        panel1.add(addANodeButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));

        resetGraphButton = new JButton();
        resetGraphButton.setBackground(new Color(-8026747));
        resetGraphButton.setEnabled(false);
        resetGraphButton.setForeground(new Color(-1));
        resetGraphButton.setHideActionText(true);
        resetGraphButton.setText("Reset Graph");
        panel1.add(resetGraphButton, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        backBtn = new JButton();
        backBtn.setBackground(new Color(-8026747));
        backBtn.setEnabled(true);
        backBtn.setForeground(new Color(-1));
        backBtn.setHideActionText(true);
        backBtn.setText("Back");
        panel1.add(backBtn, new GridConstraints(8, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return test;
    }

}
