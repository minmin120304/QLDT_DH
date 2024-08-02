package utilities.database;

import java.util.Objects;
import java.util.function.Predicate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Table {
    @XmlAttribute
    protected String id;

    public Table() {
        this("");
    }

    public Table(String id) {
        this.id = id;
    }

    public final String getID() {
        return id;
    }

    public Table export() {
        return new Table(id);
    }

    public boolean isValidate() {
        return !id.isBlank() && !id.isEmpty();
    }

    public boolean isDelete() {
        return false;
    }

     public <T extends Table> Predicate<T> getFilter() {
        return (T i) -> i.getID().equals(id);
    }

    @Override
    public final String toString() {
        return id;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof String) return id.equals(obj);
        if (!(obj instanceof Table)) return false;
        Table temp = (Table) obj;
        return getFilter().test(temp);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
