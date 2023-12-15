import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerClient {

    public String realizarVenta(int puerto, String parametros) throws IOException {
        URL url = new URL("http://10.1.18.202:" + puerto + "/realizar_venta");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = parametros.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            System.out.println("se esta recibiendo la data");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } finally {
            connection.disconnect();
        }
    }

    public String consultarExistencias(int puerto, int idProducto) throws IOException {
        URL url = new URL("http://10.1.18.202:" + puerto + "/consulta_existencia/" + idProducto);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } finally {
            connection.disconnect();
        }
    }
}