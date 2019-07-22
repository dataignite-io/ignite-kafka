import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

public class server_cluster_run {
    public static void main(String args[] ) {
        IgniteConfiguration cfg = new IgniteConfiguration();

        // Enable client mode.
        cfg.setClientMode(false);

        // Start Ignite in client mode.
        Ignite ignite = Ignition.start(cfg);

    }
}
