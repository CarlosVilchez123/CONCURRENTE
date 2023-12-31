import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ServerClient clienteService = new ServerClient();

        try {
            int puertoServicioAlmacen = 8000; // Reemplaza con el puerto real
            int idProductoConsulta = 8; // Reemplaza con el ID de producto que deseas consultar

            String realizarVenta = clienteService.realizarVenta(puertoServicioAlmacen, "quiero vender esto");
            System.out.println(realizarVenta);
            // Consultar existencias antes de realizar la venta
            /*
             * String existencias =
             * clienteService.consultarExistencias(puertoServicioAlmacen,
             * idProductoConsulta);
             * System.out.println("Existencias del producto: " + existencias);
             */

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
