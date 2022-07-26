package classes;

/**
 *
 * @author marcos
 */
public class Data {

    /**
     * @return the total
     */
    public static float getTotal() {
        return total;
    }

    /**
     * @param aTotal the total to set
     */
    public static void setTotal(float aTotal) {
        total = aTotal;
    }

    /**
     * @return the PrecioKilogramo
     */
    public static float getPrecioKilogramo() {
        return PrecioKilogramo;
    }

    /**
     * @param aPrecioKilogramo the PrecioKilogramo to set
     */
    public static void setPrecioKilogramo(float aPrecioKilogramo) {
        PrecioKilogramo = aPrecioKilogramo;
    }

    /**
     * @return the Folio
     */
    public static int getFolio() {
        return Folio;
    }

    /**
     * @param aFolio the Folio to set
     */
    public static void setFolio(int aFolio) {
        Folio = aFolio;
    }

    /**
     * @return the Kilogramos
     */
    public static float getKilogramos() {
        return Kilogramos;
    }

    /**
     * @param aKilogramos the Kilogramos to set
     */
    public static void setKilogramos(float aKilogramos) {
        Kilogramos = aKilogramos;
    }

    /**
     * @return the Descripcion
     */
    public static String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param aDescripcion the Descripcion to set
     */
    public static void setDescripcion(String aDescripcion) {
        Descripcion = aDescripcion;
    }

    /**
     * @return the Remitente
     */
    public static String getRemitente() {
        return Remitente;
    }

    /**
     * @param aRemitente the Remitente to set
     */
    public static void setRemitente(String aRemitente) {
        Remitente = aRemitente;
    }

    /**
     * @return the Destinatario
     */
    public static String getDestinatario() {
        return Destinatario;
    }

    /**
     * @param aDestinatario the Destinatario to set
     */
    public static void setDestinatario(String aDestinatario) {
        Destinatario = aDestinatario;
    }

    /**
     * @return the NoPersonas
     */
    public static String getNoPersonas() {
        return NoPersonas;
    }

    /**
     * @param aNoPersonas the NoPersonas to set
     */
    public static void setNoPersonas(String aNoPersonas) {
        NoPersonas = aNoPersonas;
    }

    /**
     * @return the NoUnidad
     */
    public static String getNoUnidad() {
        return NoUnidad;
    }

    /**
     * @param aNoUnidad the NoUnidad to set
     */
    public static void setNoUnidad(String aNoUnidad) {
        NoUnidad = aNoUnidad;
    }

    /**
     * @return the CostoBoleto
     */
    public static float getCostoBoleto() {
        return CostoBoleto;
    }

    /**
     * @param aCostoBoleto the CostoBoleto to set
     */
    public static void setCostoBoleto(float aCostoBoleto) {
        CostoBoleto = aCostoBoleto;
    }

    /**
     * @return the NombreUsuario
     */
    public static String getNombreUsuario() {
        return NombreUsuario;
    }

    /**
     * @param aNombreUsuario the NombreUsuario to set
     */
    public static void setNombreUsuario(String aNombreUsuario) {
        NombreUsuario = aNombreUsuario;
    }

    /**
     * @return the NombreCliente
     */
    public static String getNombreCliente() {
        return NombreCliente;
    }

    /**
     * @param aNombre the NombreCliente to set
     */
    public static void setNombreCliente(String aNombre) {
        NombreCliente = aNombre;
    }

    /**
     * @return the tipoTransporte
     */
    public static String getTipoTransporte() {
        return tipoTransporte;
    }

    /**
     * @param aTipoTransporte the tipoTransporte to set
     */
    public static void setTipoTransporte(String aTipoTransporte) {
        tipoTransporte = aTipoTransporte;
    }

    /**
     * @return the NoAsiento
     */
    public static int getNoAsiento() {
        return NoAsiento;
    }

    /**
     * @param aNoAsiento the NoAsiento to set
     */
    public static void setNoAsiento(int aNoAsiento) {
        NoAsiento = aNoAsiento;
    }

    /**
     * @return the Date
     */
    public static String getDate() {
        return Date;
    }

    /**
     * @param aDate the Date to set
     */
    public static void setDate(String aDate) {
        Date = aDate;
    }

    /**
     * @return the Hora
     */
    public static String getHora() {
        return Hora;
    }

    /**
     * @param aHora the Hora to set
     */
    public static void setHora(String aHora) {
        Hora = aHora;
    }

    /**
     * @return the Origen
     */
    public static String getOrigen() {
        return Origen;
    }

    /**
     * @param aOrigen the Origen to set
     */
    public static void setOrigen(String aOrigen) {
        Origen = aOrigen;
    }

    /**
     * @return the Destino
     */
    public static String getDestino() {
        return Destino;
    }

    /**
     * @param aDestino the Destino to set
     */
    public static void setDestino(String aDestino) {
        Destino = aDestino;
    }

    private static String tipoTransporte;
    private static String Origen;
    private static String Destino;
    private static String Hora;
    private static String Date;
    private static int NoAsiento;
    private static String NombreCliente;
    private static String NombreUsuario;
    private static float CostoBoleto;
    private static String NoUnidad;
    private static String NoPersonas;
    private static int Folio;
    private static float Kilogramos;
    private static float PrecioKilogramo;
    private static String Descripcion;
    private static String Remitente;
    private static String Destinatario;
    private static float total;

}
