module org.bilal.debtfree {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;

    opens org.bilal.debtfree to javafx.fxml;
    exports org.bilal.debtfree.Model;
    exports org.bilal.debtfree;
    exports org.bilal.debtfree.Controller;
    opens org.bilal.debtfree.Controller to javafx.fxml;
    opens org.bilal.debtfree.Model to javafx.base;
}