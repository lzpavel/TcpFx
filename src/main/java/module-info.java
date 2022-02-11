module com.pfl.tcpfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pfl.tcpfx to javafx.fxml;
    exports com.pfl.tcpfx;
}