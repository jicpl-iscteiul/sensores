package Objects;

public class Cultura {
    private Integer idCultura;
    private String nomeCultura;
    private Double limiteinferiortemperatura;
    private Double limitesuperiortemperatura;
    private Double limiteinferiorhumidade;
    private Double limitesuperiorhumidade;

    public Cultura(Integer idCultura, String nomeCultura, Double limiteinferiortemperatura, Double limitesuperiortemperatura, Double limiteinferiorhumidade, Double limitesuperiorhumidade) {
        this.idCultura = idCultura;
        this.nomeCultura = nomeCultura;
        this.limiteinferiortemperatura = limiteinferiortemperatura;
        this.limitesuperiortemperatura = limitesuperiortemperatura;
        this.limiteinferiorhumidade = limiteinferiorhumidade;
        this.limitesuperiorhumidade = limitesuperiorhumidade;
    }

    public Integer getIdCultura() {
        return idCultura;
    }

    public String getNomeCultura() {
        return nomeCultura;
    }

    public Double getLimiteinferiortemperatura() {
        return limiteinferiortemperatura;
    }

    public Double getLimitesuperiortemperatura() {
        return limitesuperiortemperatura;
    }

    public Double getLimiteinferiorhumidade() {
        return limiteinferiorhumidade;
    }

    public Double getLimitesuperiorhumidade() {
        return limitesuperiorhumidade;
    }
}
