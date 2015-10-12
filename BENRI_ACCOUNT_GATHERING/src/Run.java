import comparison.Comparator;

import connection.SSH;


public class Run {
    
    public static void main(String[] args) {
        SSH ssh = new SSH();
        ssh.connectRemoteServers();
        
        Comparator com = new Comparator();
        com.findNewAccount();
        com.findRemovedAccount();
    }

}
