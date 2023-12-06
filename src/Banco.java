
class Banco {
	private int cBanco = 0;
    private static final int CLIENTES_MAXIMOS = 2;

    public synchronized void ingresarBanco(String nCliente) {
        // Espera mientras se alcanza el límite de clientes
        while (cBanco >= CLIENTES_MAXIMOS) {
            try {
                System.out.println(nCliente + " esperando a ingresar al banco");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ingresa al banco
        cBanco++;
        System.out.println(nCliente + " ingresado. Estos son los clientes : " + cBanco);

        // Simulamos el tiempo que el cliente está dentro del banco haciendo alguna operación
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Sale del banco
        cBanco--;
        System.out.println(nCliente + " salio del banco. Estos son los clientes : " + cBanco);

        // Notifica a otros clientes que pueden intentar ingresar
        notifyAll();
    }
}

class cliente extends Thread {
    private Banco ban;
    private String nom;

    public cliente(Banco banco, String nombre) {
        this.ban = banco;
        this.nom = nombre;
    }

    @Override
    public void run() {
        ban.ingresarBanco(nom);
    }
}

public class main {
    public static void main(String[] args) {
        Banco banco = new Banco();

        // Crear e iniciar clientes
        cliente cliente1 = new cliente(banco, "cliente 1");
        cliente cliente2 = new cliente(banco, "cliente 2");
        cliente cliente3 = new cliente(banco, "cliente 3");

        cliente1.start();
        cliente2.start();
        cliente3.start();
    }
}
