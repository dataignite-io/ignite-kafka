import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.binary.Binarylizable;
import org.apache.ignite.binary.BinaryReader;
import org.apache.ignite.binary.BinaryWriter;

public class CacheMessage  implements Binarylizable {
    /** Street. */
    private String street;

    /** ZIP code. */
    private String zip;

    public CacheMessage() {
        // No-op.
    }

    public CacheMessage(String street, String zip) {
        this.street = street;
        this.zip = zip;
    }

    @Override public void writeBinary(BinaryWriter writer) throws BinaryObjectException {
        writer.writeString("street", street);
        writer.writeString("zip", zip);
    }

    /** {@inheritDoc} */
    @Override public void readBinary(BinaryReader reader) throws BinaryObjectException {
        street = reader.readString("street");
        zip = reader.readString("zip");
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return "Address [street=" + street +
                ", zip=" + zip + ']';
    }
}
