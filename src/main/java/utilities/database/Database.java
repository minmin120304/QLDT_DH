package utilities.database;

import entities.ToHop;
import entities.TruongHoc;
import entities.GioiTinh;
import entities.TinhThanh;
import entities.ThiSinh;
import static utilities.others.Others.*;
import java.util.ArrayList;
import java.util.List;

//import entities.*;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "database")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Database<T extends Table> {
    @XmlElements({
        @XmlElement(name = "GioiTinh", type = GioiTinh.class),
        @XmlElement(name = "ThiSinh", type = ThiSinh.class),
        @XmlElement(name = "TinhThanh", type = TinhThanh.class),
        @XmlElement(name = "ToHop", type = ToHop.class),
        @XmlElement(name = "TruongHoc", type = TruongHoc.class)
    })
    List<T> result;

    @XmlTransient
    Class<T> clazz;

    @XmlTransient
    Map<String, T> data = new HashMap<>();

    @XmlTransient
    String src;

    @XmlTransient
    Marshaller marshaller;

    @XmlTransient
    Unmarshaller unmarshaller;

    Database() {
    }

    public Database(Class<T> clazz) {
        this.src = String.format("database/%s.xml", clazz.getSimpleName());
        this.clazz = clazz;

        try {
            JAXBContext context = JAXBContext.newInstance(Database.class);

            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        read();
    }

    void read() {
        CreateFile(src);

        File file = new File(src);
        if (file.length() == 0) return;
        try {
            List<T> temp = new ArrayList<>(((Database<T>) unmarshaller.unmarshal(file)).getData());

            this.data = new HashMap<>();
            for (T item : temp) this.data.put(item.getID(), item);

        } catch (JAXBException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void updateData() {
        try {
            this.result = new ArrayList<>();

            if (!data.isEmpty()) result.addAll(data.values());
            if (!result.isEmpty()) marshaller.marshal(this, new File(src));
        } catch (JAXBException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    List<T> getData() {
        return result;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    void filterItem() {
        var items = data.values().stream().filter(i -> i.isDelete()).collect(toList());
        DELETE(items);
    }

    public void RESET() {
        updateData();
        read();
    }

    public void SAVE() {
        updateData();
    }

    public List<T> QUERY(Predicate<T> func) {
        return query(func);
    }

    public List<T> QUERY() {
        return query(i -> true);
    }

    List<T> query(Predicate<T> func) {
        filterItem();

        List<T> result = new ArrayList<>();
        var values = data.values();

        for (T item : values) {
            if (!func.test(item)) continue;
            result.add((T) item.export());
        }
        return result;
    }

    public T QUERY_ONE(Predicate<T> func) {
        filterItem();

        for (T item : data.values()) {
            if (!func.test(item)) continue;
            return (T) item.export();
        }

        return null;
    }

    public T QUERY_ID(String id) {
        T res = this.data.get(id);
        if (res == null) return res;
        if (res.isDelete()) {
            DELETE(res);
            return null;
        }
        return (T) res.export();
    }

    public int COUNT(Predicate<T> func) {
        return QUERY(func).size();
    }

    public int COUNT() {
        return QUERY().size();
    }

    public boolean ADD(List<T> items) {
        return add(items.stream());
    }

    public boolean ADD(T... items) {
        return add(Arrays.stream(items));
    }

    boolean add(Stream<T> stream) {
        try {
            int c = COUNT();

            stream.forEach((T item) -> {
                if (COUNT((Predicate<T>) item.getFilter()) > 0 || !item.isValidate()) return;
                data.put(item.getID(), (T) item.export());
            });

            updateData();
            return c < COUNT();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DELETE(Predicate<T> func) {
        try {
            for (String key : data.keySet()) {
                T item = data.get(key);
                if (!func.test(item) && !item.isDelete()) continue;
                data.remove(key);
            }
            updateData();
            return COUNT(func) == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DELETE(T... items) {
        boolean result = true;
        for (T item : items) {

            if (data.get(item.getID()) == null) {
                result = false;
                continue;
            }
            data.remove(item.getID());
        }
        updateData();
        return result;
    }

    public boolean DELETE(List<T> items) {
        boolean result = true;
        for (T item : items) {

            if (data.get(item.getID()) == null) {
                result = false;
                continue;
            }
            data.remove(item.getID());
        }
        updateData();
        return result;
    }

    public boolean DELETE_ID(String... ids) {
        boolean result = true;
        for (String id : ids) {
            if (data.get(id) == null) {
                result = false;
                continue;
            }
            data.remove(id);
        }
        updateData();
        return result;
    }

    public boolean DELETE_ID(List<String> ids) {
        boolean result = true;
        for (String id : ids) {
            if (data.get(id) == null) {
                result = false;
                continue;
            }
            data.remove(id);
        }
        updateData();
        return result;
    }

    public boolean EDIT(List<T> items) {
        return edit(items.stream());
    }

    public boolean EDIT(T... items) {
        return edit(Arrays.stream(items));
    }

    boolean edit(Stream<T> stream) {
        boolean result = stream.map((T item) -> {
            int num = COUNT((Predicate<T>) item.getFilter());
            if (!(item.isValidate() && num == 1)) return false;
            data.replace(item.getID(), (T) item.export());

            return true;
        }).reduce(true, (acc, i) -> acc && i);
        updateData();
        return result;
    }

    public <R> Stream<R> MAP(Function<? extends T, ? extends R> map) {
        filterItem();
        return this.data.values().stream().map((Function<? super T, ? extends R>) map);
    }

}
