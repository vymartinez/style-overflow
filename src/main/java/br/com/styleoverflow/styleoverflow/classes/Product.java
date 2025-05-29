package br.com.styleoverflow.styleoverflow.classes;

import javafx.beans.property.*;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

public class Product {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<Size> size = new SimpleObjectProperty<>();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final ObjectProperty<Gender> gender = new SimpleObjectProperty<>();
    private final StringProperty color = new SimpleStringProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();
    private final IntegerProperty quantidade = new SimpleIntegerProperty();
    private final StringProperty imagePath = new SimpleStringProperty();

    public Product() {
        // construtor
    }

    public Product(String name,double price, int quantidade, Gender gender, String imagePath)  {
        this.name.set(name);
        this.price.set(price);
        this.quantidade.set(quantidade);
        this.gender.set(gender);
        this.imagePath.set(imagePath);

    }

    public Product(String nome, int qtd, double preco) {
        this.name.set(nome);
        this.quantidade.set(qtd);
        this.price.set(preco);
    }


    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public Size getSize() { return size.get(); }
    public double getPrice() { return price.get(); }
    public Gender getGender() { return gender.get(); }
    public String getColor() { return color.get(); }
    public int getStock() { return stock.get(); }
    public int getQuantidade() { return quantidade.get(); }
    public String getImagePath() { return imagePath.get(); } // ➕

    public void setId(int id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setSize(Size size) { this.size.set(size); }
    public void setPrice(double price) { this.price.set(price); }
    public void setGender(Gender gender) { this.gender.set(gender); }
    public void setColor(String color) { this.color.set(color); }
    public void setStock(int stock) { this.stock.set(stock); }
    public void setQuantidade(int quantidade) { this.quantidade.set(quantidade); }
    public void setImagePath(String imagePath) { this.imagePath.set(imagePath); } // ➕

    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<Size> sizeProperty() { return size; }
    public DoubleProperty priceProperty() { return price; }
    public ObjectProperty<Gender> genderProperty() { return gender; }
    public StringProperty colorProperty() { return color; }
    public IntegerProperty stockProperty() { return stock; }
    public IntegerProperty quantidadeProperty() { return quantidade; }
    public StringProperty imagePathProperty() { return imagePath; } // ➕

    // Método que calcula o subtotal = quantidade * preço
    public double getSubtotal() {
        return getQuantidade() * getPrice();
    }
}
