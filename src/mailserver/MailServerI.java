package mailserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MailServerI extends Remote{

    public String sendMessage(EmailI e) throws RemoteException;

    public void deleteMessage(String casellaposta, EmailI e) throws RemoteException;
}
