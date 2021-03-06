/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java_网络编程.网络编程三要素;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hasee
 */
@SuppressWarnings("serial")
public class Test_UDP聊天室GUI改进 extends javax.swing.JFrame
{

    /**
     * Creates new form Liaotian
     */
    public Test_UDP聊天室GUI改进()
    {
        initComponents();
        init();
    }

    public void init()
    {
        this.setTitle("聊天程序");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.xinxi.requestFocus();
        try
        {
            dsSend = new DatagramSocket();
        } catch (SocketException e)
        {
            e.printStackTrace();
        }

        DatagramSocket dsReceive = null;
        try
        {
            dsReceive = new DatagramSocket(12306);
        } catch (SocketException ex)
        {
            Logger.getLogger(Test_UDP聊天室GUI改进.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReceiveThread rt = new ReceiveThread(dsReceive);
        new Thread(rt).start();
    }

    public javax.swing.JTextField getTextFile()
    {
        return this.xinxi;
    }

    public javax.swing.JTextArea getLiaotianxinxi()
    {
        return this.liaotianxinxi;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        liaotianxinxi = new javax.swing.JTextArea();
        xinxi = new javax.swing.JTextField();
        fasong = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        liaotianxinxi.setColumns(20);
        liaotianxinxi.setRows(5);
        jScrollPane1.setViewportView(liaotianxinxi);

        fasong.setText("发送");
        fasong.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                fasongActionPerformed(evt);
            }
        });

        jLabel1.setText("聊天信息");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xinxi, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(fasong)))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xinxi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fasong))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>                        

    private void fasongActionPerformed(java.awt.event.ActionEvent evt)                                       
    {                                           
        String str = this.xinxi.getText();
        //if (str.equals("end!"))

        DatagramPacket dp = null;
        try
        {
            dp = new DatagramPacket(str.getBytes(), str.length(), InetAddress.getByName("DESKTOP-AKG2Q86"), 12306);
            dsSend.send(dp);
        } catch (UnknownHostException ex)
        {
            Logger.getLogger(Test_UDP聊天室GUI改进.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Test_UDP聊天室GUI改进.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.xinxi.setText("");
        this.xinxi.requestFocus();
    }                                      

    class ReceiveThread implements Runnable
    {

        private DatagramSocket dsReceive;

        public ReceiveThread()
        {
        }

        public ReceiveThread(DatagramSocket dsReceive)
        {
            this.dsReceive = dsReceive;
        }

        @Override
        public void run()
        {
            while (true)
            {
                byte[] bytes = new byte[1024];
                DatagramPacket dp = new DatagramPacket(bytes, bytes.length);

                try
                {
                    dsReceive.receive(dp);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                String str = new String(dp.getData(), 0, dp.getLength());
                if (str.equals("end!"))
                {
                    break;
                }
                String ip = dp.getAddress().getHostAddress();
                liaotianxinxi.append(ip + "\r\n" + str + "\r\n");
                //System.out.println("from " + ip + " data is: " + str);
            }

            dsReceive.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Test_UDP聊天室GUI改进.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Test_UDP聊天室GUI改进.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Test_UDP聊天室GUI改进.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Test_UDP聊天室GUI改进.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Test_UDP聊天室GUI改进().setVisible(true);
            }
        });
    }

    private DatagramSocket dsSend = null;
    // Variables declaration - do not modify                     
    private javax.swing.JButton fasong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea liaotianxinxi;
    private javax.swing.JTextField xinxi;
    // End of variables declaration                   
}
