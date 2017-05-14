package mailserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MailServer extends UnicastRemoteObject implements MailServerI {

    private ArrayList<String> casellePosta;
    private HashMap<String, ArrayList<EmailI>> messaggiUtenti;
    private MailServerView msView;

    public MailServer() throws RemoteException {
        super();

        msView = new MailServerView();

        casellePosta = new ArrayList<>();
        casellePosta.add("turro@gmail.com");
        casellePosta.add("olivero@gmail.com");
        casellePosta.add("peluso@gmail.com");

        messaggiUtenti = new HashMap<>();
        messaggiUtenti.put(casellePosta.get(0), new ArrayList<>());
        messaggiUtenti.put(casellePosta.get(1), new ArrayList<>());
        messaggiUtenti.put(casellePosta.get(2), new ArrayList<>());

        msView.append("Mail Server creato");
    }

    public String sendMessage(EmailI e) {
        String mittente = e.getMit();
        String destinatari = e.getDes();
        StringTokenizer st = new StringTokenizer(destinatari);
        while (st.hasMoreTokens()) {
            String destinatario = st.nextToken();
            if (!casellePosta.contains(destinatario)) {
                return "Invio fallito, l'indirizzo: " + destinatario + " non esiste";
            }
        }
        st = new StringTokenizer(destinatari);
        while (st.hasMoreTokens()) {
            String destinatario = st.nextToken();
            ArrayList<EmailI> al = messaggiUtenti.get(destinatario);
            al.add(e);
            //stubClient.get(destinatario) .notify(e)
        }

        ArrayList<EmailI> listaMessaggi = messaggiUtenti.get(mittente);
        listaMessaggi.add(e);

        msView.append("Email " + e.toString() + "inviata");

        return "Messaggio inviato con successo";
    }

    public void deleteMessage(String casellaposta, EmailI e) {
        ArrayList<EmailI> al = messaggiUtenti.get(casellaposta);
        al.remove(e);

        msView.append("Email di " + e.toString() + "cancellata");
    }

    public static void lanciaRMIRegistry() {
        try { //special exceptio/* handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
    }

    public static void main(String args[]) {

        lanciaRMIRegistry();

        MailServer mailServer = null;

        try {
            mailServer = new MailServer();
        }catch (RemoteException e){}

        try {
            // Bind this object instance to the name "HelloServ"
            Naming.rebind("rmi:mailserver", mailServer);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
