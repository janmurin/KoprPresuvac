/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;

/**
 *
 * @author Janco1
 */
public class MainForm extends javax.swing.JFrame {

    private int soketov;
    private final File vybranySubor;
    private final String destFolder;
    private boolean beziKopirovanie;
    private SwingWorker<Void, ProgressbarStatus> kopirovanieWorker;
    private boolean chcemeZahodit = true;// musi byt true aby sa na start buttone zacinalo nove kopirovanie

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        pocetSoketovSpinner.setModel(new SpinnerNumberModel(Runtime.getRuntime().availableProcessors(), 1, 10, 1));//Runtime.getRuntime().availableProcessors()
        vybranySubor = Shared.VYBRANY_SUBOR;
        String text = "cesta: \n" + vybranySubor.getAbsolutePath() + "\n velkost: " + vybranySubor.length();
        sourceDetailTextArea.setText(text);
        setLocationRelativeTo(null);
        destFolder = "C:\\Users\\Janco1\\Desktop";
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        sourceDetailTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        destinationDetailTextArea = new javax.swing.JTextArea();
        startButton = new javax.swing.JButton();
        zrusitButton = new javax.swing.JButton();
        kopirovanieProgressBar = new javax.swing.JProgressBar();
        rychlostLabel = new javax.swing.JLabel();
        elapsedLabel = new javax.swing.JLabel();
        remainingLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pocetSoketovSpinner = new javax.swing.JSpinner();
        rychlostLabel1 = new javax.swing.JLabel();
        elapsedLabel1 = new javax.swing.JLabel();
        remainingLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));

        sourceDetailTextArea.setBackground(new java.awt.Color(204, 204, 204));
        sourceDetailTextArea.setColumns(20);
        sourceDetailTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        sourceDetailTextArea.setRows(5);
        jScrollPane1.setViewportView(sourceDetailTextArea);

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));

        destinationDetailTextArea.setBackground(new java.awt.Color(204, 204, 204));
        destinationDetailTextArea.setColumns(20);
        destinationDetailTextArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        destinationDetailTextArea.setRows(5);
        destinationDetailTextArea.setText("cielovy adresar:\nC:\\Users\\Janco1\\Desktop");
        jScrollPane2.setViewportView(destinationDetailTextArea);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        zrusitButton.setText("Zrusit");
        zrusitButton.setEnabled(false);
        zrusitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zrusitButtonActionPerformed(evt);
            }
        });

        kopirovanieProgressBar.setStringPainted(true);

        rychlostLabel.setText("Rýchlosť:");

        elapsedLabel.setText("Elapsed:");

        remainingLabel.setText("Remaining:");

        jLabel3.setText("Pocet soketov:");

        elapsedLabel1.setText("00:00:00");

        remainingLabel1.setText("00:00:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startButton)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pocetSoketovSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zrusitButton)))
                    .addComponent(kopirovanieProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(elapsedLabel)
                            .addComponent(rychlostLabel)
                            .addComponent(remainingLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rychlostLabel1)
                            .addComponent(elapsedLabel1)
                            .addComponent(remainingLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pocetSoketovSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(zrusitButton))
                .addGap(18, 18, 18)
                .addComponent(kopirovanieProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rychlostLabel)
                    .addComponent(rychlostLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(elapsedLabel)
                    .addComponent(elapsedLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remainingLabel)
                    .addComponent(remainingLabel1))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        /*Moznosti:
            1. chceme zacat kopirovat
            2. chceme prerusit kopirovanie
            3. chceme obnovit kopirovanie 
        
        - bezi kopirovanie => mozeme len prerusit
        - nebezi kopirovanie => resume alebo nove, podla toho ci chcemeZahodit je true
         */
        System.out.println("startButtonActionPerformed");
        if (beziKopirovanie) {
            // BEZI kopirovanie a teda chceme prerusit
            startButton.setEnabled(false); // musi byt vypnuty a obnovi sa az ked skoncia procesy
            kopirovanieWorker.cancel(true);
            startButton.setText("RESUME");
            beziKopirovanie = false;
        } else if (chcemeZahodit) {
            // nebezi kopirovanie a chceli sme zahodit predosle kopirovanie, takze teraz musime zacat nove
            beziKopirovanie = true;
            chcemeZahodit = false; // chceme kopirovat takze nechceme po skopirovani subor mazat
            zrusitButton.setEnabled(true);// mozeme zrusit len ak sme zacali kopirovat
            startButton.setText("PAUSE");
            zacniKopirovanie(null);
        } else {
            // nebezi kopirovanie a predosle nebolo zahodene, takze resumujeme
            // TODO nacitat data z prerusenia pre obnovenie
            KopirovanieSession session = loadClientData();
            zacniKopirovanie(session);
            beziKopirovanie = true;
            startButton.setText("PAUSE");
        }

    }//GEN-LAST:event_startButtonActionPerformed

    private void zrusitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zrusitButtonActionPerformed
        /*
        1. zrusenie pocas kopirovania
        2. zrusenie pri prerusenom kopirovani
         */
        System.out.println("zrusitButtonActionPerformed");
        aktivujZrusenieButtony();
        if (kopirovanieWorker != null) {
            //zrusenie pocas kopirovania
            kopirovanieWorker.cancel(true); // worker sa vynulluje ked sa zrusi a nastavi buttony    
        } else {
            // zrusenie pri prerusenom kopirovani
            // kopirovanie worker nebezi takze nebudeme posielat cancel workerovi a teda worker nemohol zmazat ciastocne skopirovany subor
            // zmazeme clientData.txt a ciastocne skopirovany subor
            deleteCiastocnySubor();
            // resetneme progressbar
            kopirovanieProgressBar.setValue(0);
            rychlostLabel1.setText("");
            elapsedLabel1.setText("00:00:00");
            remainingLabel1.setText("00:00:00");
        }
    }//GEN-LAST:event_zrusitButtonActionPerformed

    private void aktivujZrusenieButtony() {
        chcemeZahodit = true; // ked sa prerusi kopirovanie, tak chceme zahodit aktualny stav kopirovania
        zrusitButton.setEnabled(false);
        startButton.setText("START");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea destinationDetailTextArea;
    private javax.swing.JLabel elapsedLabel;
    private javax.swing.JLabel elapsedLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JProgressBar kopirovanieProgressBar;
    private javax.swing.JSpinner pocetSoketovSpinner;
    private javax.swing.JLabel remainingLabel;
    private javax.swing.JLabel remainingLabel1;
    private javax.swing.JLabel rychlostLabel;
    private javax.swing.JLabel rychlostLabel1;
    private javax.swing.JTextArea sourceDetailTextArea;
    private javax.swing.JButton startButton;
    private javax.swing.JButton zrusitButton;
    // End of variables declaration//GEN-END:variables

    private void zacniKopirovanie(KopirovanieSession previousSession) {
        System.out.println("");
        soketov = (int) pocetSoketovSpinner.getValue();
        if (previousSession != null) {
            System.out.println("MainForm: resumujem kopirovanie");
            System.out.println("MainForm: nastavujem pocet soketov na pocet clientov: " + previousSession.clientData.size());
            soketov = previousSession.clientData.size();
            pocetSoketovSpinner.setValue(soketov);
        }
        System.out.println("Zacinam kopirovanie: soketov=[" + soketov + "] subor: " + vybranySubor.getAbsolutePath());

        kopirovanieWorker = new SwingWorker<Void, ProgressbarStatus>() {
            private ExecutorService kopirovacManagerExecutor;

            @Override
            protected Void doInBackground() throws Exception {
                Future<KopirovanieResult> future = null;
                long start = System.currentTimeMillis();
                int prenesenychResume = 0;
                long previousElapsed = 0;
                
                try {
                    kopirovacManagerExecutor = Executors.newSingleThreadExecutor();
                    // init premenne pre resume
                    KopirovacManagerTask task = null;
                    if (previousSession != null) {
                        // resumujeme predchadzajuci session
                        // kopirovac manager nastavi clientom ine hodnoty start offsetov a chunkov
                        // musime si zapametat hodnoty zo stareho kopirovania
                        for (ClientData cd : previousSession.clientData) {
                            prenesenychResume += cd.zapisanychOffset - cd.startOffset;
                        }
                        previousElapsed = previousSession.elapsedTime;
                        task = new KopirovacManagerTask(soketov, destFolder, getCopyOf(previousSession.clientData));
                    } else {
                        // nemame ziaden predosly session
                        task = new KopirovacManagerTask(soketov, destFolder, null);
                    }

                    if (isCancelled()) {
                        return null;
                    }
                    future = kopirovacManagerExecutor.submit(task);

                    // nekonecny cyklus dokym neskonci kopirovacManagerExecutor
                    do {
                        // zistime aktualne stavy u clientov
                        List<ClientData> data = Shared.clientData;
                        int prenesenych = 0;
                        int vsetkych = 0;
                        for (ClientData cd : data) {
                            prenesenych += cd.zapisanychOffset - cd.startOffset;
                            vsetkych += cd.chunkSize;
                        }
                        if (vsetkych != 0) {
                            // ak sa stihol nejaky vysledok tak publish
                            int kopirovanieProgress = (int) ((prenesenych + prenesenychResume) / ((double) vsetkych + prenesenychResume) * 100);
                            publish(new ProgressbarStatus(kopirovanieProgress,
                                    Utils.getElapsedTime(start, previousElapsed),
                                    Utils.getETAtime(start, prenesenych, vsetkych), Utils.getRychlost(start, previousElapsed, prenesenych + prenesenychResume)));
                        }
                        // ak bol worker cancelnuty, tak tu vyhodi interrupted exception
                        Thread.sleep(500);
                    } while (!future.isDone());// future spravne skonci aj ked bude interruptnuty

                    // vrati informaciu o stave kopirovania
                    KopirovanieResult result = future.get(); // mohla nastat divna chyba a teda v resulte nebude ocakavany vysledok
                    // ak sme sa dostali az sem tak by malo byt kopirovanie vzdy uspesne skoncene, ale ak sme prerusili alebo zrusili, tak by vyhodilo vynimku
                    // v cykle na Thread.sleep(500); a skocilo by to do catch bloku
                    if (result.uspesne) {
                        // kopirovanie bolo uspesne
                        publish(new ProgressbarStatus(100,
                                Utils.getElapsedTime(start, previousElapsed),
                                Utils.getETAtime(start, 100, 100), Utils.getRychlost(start, previousElapsed, result.velkostSuboru)));
                    } else // neuspesne skopirovanie
                    // 1. chceli sme pozastavit a treba si zapametat co sme nestihli
                    // 2. chceme na vsetko zabudnut
                    {
                        // ak sme neskocili do interrupted bloku(nebolo kopirovanie prerusene), tak tu by sme sa nemali nikdy dostat
                        JOptionPane.showMessageDialog(rootPane, "KOPIROVANIE NEUSPESNE AJ BEZ PRERUSENIA, NEMALO BY SA NIKDY STAT.");
//                        if (!chcemeZahodit) {
//                            // ulozime do suboru clientdata, nechame progressbar tak ako bol
//                            ulozClientData(result.clientData);
//                        } else {
//                            // zahadzujeme data o kopirovani a progressbar nullujeme 
//                            deleteCiastocnySubor();
//                            // neriesime subor s client datami lebo dalsi start button bude znamenat nove kopirovanie
//                            publish(new ProgressbarStatus(0, "00:00:00", "00:00:00", ""));
//                        }
                    }

                    System.out.println("manager cancelled: " + future.isCancelled() + " isDone=" + future.isDone() + " ");
                    System.out.println("kopirovacManagerExecutor.isShutdown()=" + kopirovacManagerExecutor.isShutdown() + " isTerminated()=" + kopirovacManagerExecutor.isTerminated());
                } catch (InterruptedException interruptedException) {
                    System.out.println("MainForm: interruptedException, shutting down kopirovacManagerExecutor");
                    kopirovacManagerExecutor.shutdownNow();// prerusime managera a future nam skonci
                    System.out.println("MainForm: cakam na future kym skonci");
                    while (!future.isDone()) { // TODO: bude future naozaj done ked bude interruptnuty??????
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            System.out.println("MainForm Thread.sleep(100) InterruptedException");
                        }
                    }
                    System.out.println("MainForm: future skonceny");
                    System.out.println("MainForm: zotavujem sa z prerusenia, ziskavam data o kopirovani");
                    // vrati informaciu o stave kopirovania
                    KopirovanieResult result = future.get();
                    if (result.uspesne) {
                        //JOptionPane.showMessageDialog(rootPane, "KOPIROVANIE USPESNE AJ PO PRERUSENI, NEMALO BY SA NIKDY STAT.");
                        // moze sa kopirovanie stihnut dokoncit predtym nez sa posle interrupt do manager tasku
                        // pri preruseni by sa nikdy nemal stat tento pripad ze je uspesne
                        // kopirovanie bolo uspesne
                        publish(new ProgressbarStatus(100,
                                Utils.getElapsedTime(start, previousElapsed),
                                Utils.getETAtime(start, 100, 100), Utils.getRychlost(start, previousElapsed, result.velkostSuboru)));
                    } else // neuspesne skopirovanie
                    // 1. chceli sme pozastavit a treba si zapametat co sme nestihli (prerusenie pausnutim)
                    // 2. chceme na vsetko zabudnut(prerusenie zrusenim)
                    // po preruseni by sme sa tu mali takmer vzdy dostat
                    {
                        if (!chcemeZahodit) {
                            System.out.println("MainForm: kopirovanie neuspesne a chceme ulozit ciastocne data");
                            // ulozime do suboru clientdata, nechame progressbar tak ako bol
                            long totalElapsed = (System.currentTimeMillis() - start);
                            if (previousSession == null) {
                                ulozClientData(result.clientData, null, totalElapsed);
                            } else {
                                totalElapsed += previousSession.elapsedTime;
                                ulozClientData(result.clientData, previousSession.clientData, totalElapsed);
                            }

                        } else {
                            System.out.println("MainForm: kopirovanie neuspesne a chceme mazat data");
                            // zahadzujeme data o kopirovani a progressbar nullujeme 
                            deleteCiastocnySubor();
                            // neriesime subor s client datami lebo dalsi start button bude znamenat nove kopirovanie
                            publish(new ProgressbarStatus(0, "00:00:00", "00:00:00", ""));
                        }
                    }
                    System.out.println("enablujem start button");
                    startButton.setEnabled(true);
                    System.out.println("manager cancelled: " + future.isCancelled() + " isDone=" + future.isDone() + " ");
                    System.out.println("kopirovacManagerExecutor.isShutdown()=" + kopirovacManagerExecutor.isShutdown() + " isTerminated()=" + kopirovacManagerExecutor.isTerminated());
                } catch (Exception e) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, e);
                    JOptionPane.showMessageDialog(rootPane, "NEOCAKAVANA VYNIMKA: " + e);
                }

                System.out.println("Mainform: kopirovanie skoncene");
                beziKopirovanie = false;
                kopirovanieWorker = null;
                return null;
            }

            @Override
            protected void process(List<ProgressbarStatus> chunks) {
                ProgressbarStatus status = chunks.get(chunks.size() - 1);
                kopirovanieProgressBar.setValue(status.kopirovanieProgress);
                rychlostLabel1.setText("" + status.rychlost);
                elapsedLabel1.setText("" + status.uplynulo);
                remainingLabel1.setText("" + status.ostava);
                // ak bolo kopirovanie uspesne, tak vypneme buttony
                if (status.kopirovanieProgress == 100) {
                    System.out.println("MainForm: vypinam buttony po uspesnom kopirovani");
                    aktivujZrusenieButtony();
                }
            }

            @Override
            protected void done() {

            }

            private void ulozClientData(List<ClientData> clientData, CopyOnWriteArrayList<ClientData> oldClientData, long elapsedTime) {
                System.out.println("KopirovacWorker: ukladam client data");
                // ak oldClientData nie je null, tak musime ulozit povodne offsety a chunky z oldClientData
                // kopirovacManager totiz generuje clientom zmensene chunky, ktore este nie su stiahnute
                // a straca sa tak informacia o povodnych offsetoch a chunkoch
                Writer out = null;
                try {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Shared.DATA_FILE), "UTF-8"));
                    if (clientData == null) {
                        System.out.println("prvy resume, vsetky offsety a chunky su ok");
                    } else {
                        System.out.println("ukladam povodne offsety a chunky");
                    }
                    for (int i = 0; i < clientData.size(); i++) {
                        ClientData cd = clientData.get(i);
                        if (oldClientData == null) {
                            // nemali sme oldClientData, takze toto je prve prerusenie a obsahuje primarne offsety
                            out.write(cd.startOffset + "_" + cd.zapisanychOffset + "_" + cd.chunkSize + "\n");
                        } else {
                            // mali sme oldClientData, takze si nacitame startOffsety a chunky z oldu a z novych ulozime zapisanychOffset
                            out.write(oldClientData.get(i).startOffset + "_" + cd.zapisanychOffset + "_" + oldClientData.get(i).chunkSize + "\n");
                        }
                    }
                    out.write("elapsedTime:" + elapsedTime);
                    out.flush();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            private CopyOnWriteArrayList<ClientData> getCopyOf(CopyOnWriteArrayList<ClientData> clientData) {
                if (clientData == null) {
                    return null;
                }
                CopyOnWriteArrayList<ClientData> copy = new CopyOnWriteArrayList<>();
                for (ClientData cd : clientData) {
                    copy.add(new ClientData(cd.startOffset, cd.zapisanychOffset, cd.chunkSize));
                }
                return copy;
            }
        };
        kopirovanieWorker.execute();
        System.out.println("kopirovanieWorker executed");
    }

    private void deleteCiastocnySubor() {
        System.out.println("mazem ciastocny subor a clientData.txt");
        File ciastocny = new File(destFolder + "\\" + vybranySubor.getName());
        ciastocny.delete();
        File dataFile = new File(Shared.DATA_FILE);
        dataFile.delete();
        System.out.println("subory zmazane");
    }

    private KopirovanieSession loadClientData() {
        File file = new File(Shared.DATA_FILE);
        CopyOnWriteArrayList<ClientData> clientData = new CopyOnWriteArrayList<>();
        long elapsedTime = 0;

        BufferedReader f = null;
        try {
            f = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            while (true) {
                try {
                    String line = f.readLine().trim();
                    if (line == null) {
                        break;
                    }
                    if (line.length() > 0) {
                        if (line.startsWith("elapsedTime:")) {
                            elapsedTime = Long.parseLong(line.replace("elapsedTime:", "").trim());
                        } else {
                            try {
                                String[] parts = line.split("_");
                                int startOffset = Integer.parseInt(parts[0]);
                                int stiahnutychOffset = Integer.parseInt(parts[1]);
                                int chunkSize = Integer.parseInt(parts[2]);
                                clientData.add(new ClientData(startOffset, stiahnutychOffset, chunkSize));
                            } catch (NumberFormatException numberFormatException) {
                                JOptionPane.showConfirmDialog(rootPane, "Data v subore " + Shared.DATA_FILE + " su poskodene");
                                return null;
                            }
                        }
                    }

                } catch (Exception ex) {
                    // Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new KopirovanieSession(elapsedTime, clientData);
    }

}
